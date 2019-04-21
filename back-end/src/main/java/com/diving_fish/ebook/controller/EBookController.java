package com.diving_fish.ebook.controller;

import com.diving_fish.ebook.entity.BookEntity;
import com.diving_fish.ebook.entity.UserEntity;
import com.diving_fish.ebook.repository.BookRepository;
import com.diving_fish.ebook.repository.UserRepository;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;

import java.awt.print.Book;
import java.io.*;
import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

@RestController
@EnableAutoConfiguration
public class EBookController {

    @Autowired
    private BookRepository br;
    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray gettest() {
        String jsons = "[";
        List<BookEntity> lb = br.findAll();
        for (BookEntity e : lb) {
            jsons += e.to_json() + ",";
        }
        jsons += "]";
        return JSONArray.fromObject(jsons);
    }

    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getbook(@RequestParam("isbn") Long isbn) throws NullPointerException {
        return JSONObject.fromObject(br.findByIsbn(isbn).to_json());
    }

    @RequestMapping(value = "/modifybook", method = RequestMethod.POST)
    @ResponseBody
    public void modifybook(@RequestBody JSONObject book_info) {
        BookEntity book = new BookEntity();
        book.set(
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
        UserEntity ue = new UserEntity();
        ue.set(r_id, username, password, email, 1);
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
}