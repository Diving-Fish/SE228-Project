package com.diving_fish.ebook.Service;

import com.diving_fish.ebook.Session.Cart;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;

public interface CartService {
    JSONArray getCart(HttpSession httpSession);
    JSONObject cartAdd(HttpSession httpSession, Long bookIsbn, Integer amount);
    JSONObject cartSet(HttpSession httpSession, JSONObject cartJson);
    JSONObject cartDelete(HttpSession httpSession, Long bookIsbn);
    void clear(HttpSession httpSession);
}
