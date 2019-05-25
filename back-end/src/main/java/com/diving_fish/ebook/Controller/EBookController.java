package com.diving_fish.ebook.Controller;

import com.diving_fish.ebook.Entity.BookEntity;
import com.diving_fish.ebook.Service.BookManageService;
import com.diving_fish.ebook.Service.CartService;
import com.diving_fish.ebook.Service.OrderManageService;
import com.diving_fish.ebook.Service.UserManageService;
import com.diving_fish.ebook.Session.Cart;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@EnableAutoConfiguration
public class EBookController {

    /* ================================================================
        Auto Wire Services
       ================================================================ */

    @Autowired
    private BookManageService bookManageService;

    @Autowired
    private OrderManageService orderManageService;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private CartService cartService;

    /* ================================================================
        Book Manage Service
       ================================================================ */

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getbooklist() {
        return bookManageService.getBookList();
    }

    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getbook(@RequestParam("isbn") Long isbn) throws NullPointerException {
        return bookManageService.getBook(isbn);
    }

    @RequestMapping(value = "/modifybook", method = RequestMethod.POST)
    @ResponseBody
    public void modifybook(@RequestBody JSONObject book_info) {
        bookManageService.modifyBook(book_info);
    }

    @RequestMapping(value = "/removebook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removebook(@RequestParam("isbn") Long isbn) {
        return bookManageService.removeBook(isbn);
    }

    /* ================================
       Order Controllers
       ================================ */

    @RequestMapping(value = "/submitorder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submitorder(@RequestBody JSONObject submit_form, Principal principal) {
        int id = userManageService.getUserInfo(principal.getName()).getInt("id");
        orderManageService.submitOrder(id, submit_form);
        JSONObject response = new JSONObject();
        response.put("status", 200);
        return response;

    }

    @RequestMapping(value = "/getorder", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getorder(Principal principal) {
        return orderManageService.getOrders(userManageService.getUserInfo(principal.getName()).getInt("id"));
    }

    /* ================================================================
        User Manage Service
       ================================================================ */

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestBody JSONObject register_form) {
        String username = register_form.getString("username");
        String password = register_form.getString("password");
        String email = register_form.getString("email");
        userManageService.createCustomer(username, password, email);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserinfo(Principal principal) {
        return userManageService.getUserInfo(principal.getName());
    }

    @RequestMapping(value = "/usernamevalid", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject valid(@RequestParam("username") String username) {
        JSONObject response = new JSONObject();
        response.put("valid", userManageService.isUsernameValid(username));
        response.put("status", 200);
        return response;
    }

    /* ================================================================
        Cart Service
       ================================================================ */

    @RequestMapping(value = "/getcart", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getcart(Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (!userManageService.isLogin(principal.getName())) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        response.put("status", 200);
        response.put("cart", cartService.getCart(session));
        return response;
    }

    @RequestMapping(value = "/cartadd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartadd(@RequestParam("isbn") Long isbn, @RequestParam("amount") Integer amount, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (!userManageService.isLogin(principal.getName())) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        return cartService.cartAdd(session, isbn, amount);
    }

    @RequestMapping(value = "/cartset", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartset(@RequestBody JSONObject _cart, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (!userManageService.isLogin(principal.getName())) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        return cartService.cartSet(session, _cart);
    }

    @RequestMapping(value = "/cartdelete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartdel(@RequestParam("isbn") Long isbn, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (!userManageService.isLogin(principal.getName())) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        return cartService.cartDelete(session, isbn);
    }

    @RequestMapping(value = "/emptycart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject emptycart(Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (!userManageService.isLogin(principal.getName())) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        cartService.clear(session);
        response.put("status", 200);
        return response;
    }
}