package com.diving_fish.ebook.Controller;

import com.diving_fish.ebook.Service.BookManageService;
import com.diving_fish.ebook.Service.OrderManageService;
import com.diving_fish.ebook.Service.UserManageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@EnableAutoConfiguration
public class OrderController {

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private OrderManageService orderManageService;

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
}
