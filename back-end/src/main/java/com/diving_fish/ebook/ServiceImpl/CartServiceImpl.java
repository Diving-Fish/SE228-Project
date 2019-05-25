package com.diving_fish.ebook.ServiceImpl;

import com.diving_fish.ebook.Entity.BookEntity;
import com.diving_fish.ebook.Service.BookManageService;
import com.diving_fish.ebook.Service.CartService;
import com.diving_fish.ebook.Session.Cart;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private BookManageService bookManageService;

    @Override
    public JSONArray getCart(HttpSession httpSession) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        JSONArray jsonArray = new JSONArray();
        for (Long isbn: cart.getAll()) {
            JSONObject jsonObject = bookManageService.getBook(isbn);
            jsonObject.put("amount", cart.get(isbn));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONObject cartAdd(HttpSession httpSession, Long bookIsbn, Integer amount) {
        JSONObject response = new JSONObject();
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        JSONObject jsonObject = bookManageService.getBook(bookIsbn);
        if (jsonObject == null) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + bookIsbn + " not exists.");
            return response;
        }
        Integer i = cart.get(bookIsbn);
        if (i == null) {
            cart.put(bookIsbn, amount);
        } else {
            cart.put(bookIsbn, amount+i);
        }
        httpSession.setAttribute("cart", cart);
        response.put("status", 200);
        return response;
    }

    @Override
    public JSONObject cartDelete(HttpSession httpSession, Long bookIsbn) {
        JSONObject response = new JSONObject();
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        JSONObject bookJson = bookManageService.getBook(bookIsbn);
        if (bookJson == null) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + bookIsbn + " not exists.");
            return response;
        }
        if (!cart.remove(bookIsbn)) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + bookIsbn + " is not in cart.");
            return response;
        }
        response.put("status", 200);
        return response;
    }

    @Override
    public JSONObject cartSet(HttpSession httpSession, JSONObject cartJson) {
        JSONObject response = new JSONObject();
        Cart cart = new Cart();
        JSONArray jsonArray = cartJson.getJSONArray("cart");
        for (Object jsonObject : jsonArray) {
            Long isbn = ((JSONObject) jsonObject).getLong("isbn");
            JSONObject bookJson = bookManageService.getBook(isbn);
            if (bookJson == null) {
                response.put("status", "error");
                response.put("message", "the book with isbn " + isbn + " not exists.");
                return response;
            }
            Integer amount = ((JSONObject) jsonObject).getInt("amount");
            cart.put(isbn, amount);
        }
        httpSession.setAttribute("cart", cart);
        response.put("status", 200);
        return response;
    }

    @Override
    public void clear(HttpSession httpSession) {
        Cart cart = new Cart();
        httpSession.setAttribute("cart", cart);
    }
}
