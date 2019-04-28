package com.diving_fish.ebook.controller;

import com.diving_fish.ebook.entity.BookEntity;
import com.diving_fish.ebook.entity.OrderEntity;
import com.diving_fish.ebook.entity.UserEntity;
import com.diving_fish.ebook.repository.BookRepository;
import com.diving_fish.ebook.repository.OrderRepository;
import com.diving_fish.ebook.repository.UserRepository;
import com.diving_fish.ebook.session.Cart;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.catalina.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.io.*;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

@RestController
@EnableAutoConfiguration
public class EBookController {

    /* ================================
        Book Controllers
       ================================ */
    @Autowired
    private BookRepository br;

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getbooklist() {
        JSONArray jsonArray = new JSONArray();
        List<BookEntity> lb = br.findAll();
        for (BookEntity e : lb) {
            jsonArray.add(e.to_json());
        }
        return jsonArray;
    }

    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getbook(@RequestParam("isbn") Long isbn) throws NullPointerException {
        return JSONObject.fromObject(br.findByIsbn(isbn).to_json());
    }

    @RequestMapping(value = "/modifybook", method = RequestMethod.POST)
    @ResponseBody
    public void modifybook(@RequestBody JSONObject book_info) {
        BookEntity book = new BookEntity(
                Long.parseLong(book_info.get("isbn").toString()),
                book_info.get("title").toString(),
                Double.parseDouble(book_info.get("price").toString()),
                book_info.get("author").toString(),
                book_info.get("intro").toString(),
                Integer.parseInt(book_info.get("stock").toString()),
                Integer.parseInt(book_info.get("category").toString()),
                true
        );
        br.save(book);
    }

    @RequestMapping(value = "/removebook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removebook(@RequestParam("isbn") Long isbn) {
        JSONObject jsonObject = new JSONObject();
        try {
            br.deleteById(isbn);
            jsonObject.put("status", "200");
            jsonObject.put("message", "delete successfully");
        } catch (EmptyResultDataAccessException e) {
            jsonObject.put("status", "error");
            jsonObject.put("message", "the book with isbn " + isbn + " not exists.");
        }
        return jsonObject;
    }

    /* ================================
       Order Controllers
       ================================ */

    @Autowired
    private OrderRepository or;

    public String stampToString(Timestamp timestamp) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(timestamp);
    }

    @RequestMapping(value = "/submitorder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submitorder(@RequestBody JSONObject submit_form, Principal principal) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Random r = new Random();
        int r_id, group_id, order_id;
        while (true) {
            r_id = r.nextInt(90000000) + 10000000;
            List<OrderEntity> ue = or.findAllByGroupid(r_id);
            if (ue.size() == 0) {
                group_id = r_id;
                break;
            }
        }
        JSONObject response = new JSONObject();
        Integer userid = getUserinfo(principal).getInt("id");
        JSONArray cart = submit_form.getJSONArray("cart");
        for (Object object : cart) {
            JSONObject jsonObject = (JSONObject) object;
            Integer amount = jsonObject.getInt("amount");
            Long isbn = jsonObject.getLong("isbn");
            while (true) {
                r_id = r.nextInt(90000000) + 10000000;
                OrderEntity o = or.findById(r_id);
                if (o == null) {
                    order_id = r_id;
                    break;
                }
            }
            OrderEntity o = new OrderEntity(order_id, group_id, userid, isbn, amount, ts, ts);
            or.save(o);
        }
        response.put("status", 200);
        return response;

    }


    @RequestMapping(value = "/getorder", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getorder(Principal principal) {
        JSONObject response = new JSONObject();
        Integer userid = getUserinfo(principal).getInt("id");
        List<OrderEntity> orderEntityList = or.findAllByUserid(userid);
        Map<Integer, JSONObject> groupMap = new LinkedHashMap<>();
        for (OrderEntity order: orderEntityList) {
            int groupid = order.getGroupId();
            if (groupMap.get(groupid) == null) {
                JSONObject groupObject = new JSONObject();
                groupObject.put("groupid", groupid);
                groupObject.put("date", stampToString(order.getCreate_date()));
                JSONArray orderArray = new JSONArray();
                groupObject.put("orders", orderArray);
                groupMap.put(groupid, groupObject);
            }
            JSONObject groupObject = groupMap.get(groupid);
            JSONArray orderArray = groupObject.getJSONArray("orders");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderid", order.getId());
            jsonObject.put("amount", order.getAmount());
            jsonObject.put("book", br.findByIsbn(order.getIsbn()).to_json());
            orderArray.add(jsonObject);
            groupObject.put("orders", orderArray);
            groupMap.put(groupid, groupObject);
        }
        JSONArray all = new JSONArray();
        for (Integer groupid : groupMap.keySet()) {
            all.add(groupMap.get(groupid));
        }
        response.put("status", 200);
        response.put("groups", all);
        return response;
    }



    /* ================================
        User Controllers
       ================================ */

    @Autowired
    private UserRepository ur;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestBody JSONObject register_form) {
        Random r = new Random();
        int r_id;
        while (true) {
            r_id = r.nextInt(90000000) + 10000000;
            UserEntity ue = ur.findById(r_id);
            if (ue == null) break;
        }
        String username = register_form.get("username").toString();
        String password = new BCryptPasswordEncoder().encode(register_form.get("password").toString());
        String email = register_form.get("email").toString();
        UserEntity ue = new UserEntity(r_id, username, password, email, 1);
        ur.save(ue);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserinfo(Principal principal) {
        JSONObject o = new JSONObject();
        try {
            UserEntity user = ur.findByUsername(principal.getName());
            o.put("status", 200);
            o.put("id", user.getId());
            o.put("username", user.getUsername());
            o.put("role", user.getRole());
            o.put("enabled", user.isEnabled());
            return o;
        } catch (NullPointerException e) {
            o.put("status", "error");
            return o;
        }
    }

    @RequestMapping(value = "/usernamevalid", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject valid(@RequestParam("username") String username) {
        JSONObject response = new JSONObject();
        if (ur.findByUsername(username) != null) {
            response.put("valid", false);
            response.put("message", "username has been used.");
        } else {
            response.put("valid", true);
        }
        response.put("status", 200);
        return response;
    }

    @RequestMapping(value = "/getcart", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getcart(Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (getUserinfo(principal).getString("status").equals("error")) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        response.put("status", 200);
        JSONArray jsonArray = new JSONArray();
        for (Long isbn: cart.getAll()) {
            BookEntity bookEntity = br.findByIsbn(isbn);
            JSONObject jsonObject = bookEntity.to_json();
            jsonObject.put("amount", cart.get(isbn));
            jsonArray.add(jsonObject);
        }
        response.put("cart", jsonArray);
        return response;
    }

    @RequestMapping(value = "/cartadd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartadd(@RequestParam("isbn") Long isbn, @RequestParam("amount") Integer amount, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (getUserinfo(principal).getString("status").equals("error")) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        BookEntity bookEntity = br.findByIsbn(isbn);
        if (bookEntity == null) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + isbn + " not exists.");
            return response;
        }
        Integer i = cart.get(isbn);
        if (i == null) {
            cart.put(isbn, amount);
        } else {
            cart.put(isbn, amount+i);
        }
        session.setAttribute("cart", cart);
        response.put("status", 200);
        return response;
    }

    @RequestMapping(value = "/cartset", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartset(@RequestBody JSONObject _cart, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (getUserinfo(principal).getString("status").equals("error")) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        Cart cart = new Cart();
        JSONArray jsonArray = _cart.getJSONArray("cart");
        for (Object jsonObject : jsonArray) {
            Long isbn = ((JSONObject) jsonObject).getLong("isbn");
            BookEntity bookEntity = br.findByIsbn(isbn);
            if (bookEntity == null) {
                response.put("status", "error");
                response.put("message", "the book with isbn " + isbn + " not exists.");
                return response;
            }
            Integer amount = ((JSONObject) jsonObject).getInt("amount");
            cart.put(isbn, amount);
        }
        session.setAttribute("cart", cart);
        response.put("status", 200);
        return response;
    }

    @RequestMapping(value = "/cartdelete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cartdel(@RequestParam("isbn") Long isbn, Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (getUserinfo(principal).getString("status").equals("error")) {
            response.put("status", "unlogin");
            return response;
        }
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        BookEntity bookEntity = br.findByIsbn(isbn);
        if (bookEntity == null) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + isbn + " not exists.");
            return response;
        }
        if (!cart.remove(isbn)) {
            response.put("status", "error");
            response.put("message", "the book with isbn " + isbn + " is not in cart.");
            return response;
        }
        response.put("status", 200);
        return response;
    }

    @RequestMapping(value = "/emptycart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject emptycart(Principal principal, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        HttpSession session = request.getSession();
        Cart cart = new Cart();
        session.setAttribute("cart", cart);
        response.put("status", 200);
        return response;
    }
}