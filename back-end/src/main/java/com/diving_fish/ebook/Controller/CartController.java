package com.diving_fish.ebook.Controller;

import com.diving_fish.ebook.Service.CartService;
import com.diving_fish.ebook.Service.UserManageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@EnableAutoConfiguration
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserManageService userManageService;

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
