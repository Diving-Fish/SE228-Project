package com.diving_fish.ebook.Controller;

import com.diving_fish.ebook.Service.UserManageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@EnableAutoConfiguration
public class UserController {
    @Autowired
    private UserManageService userManageService;

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
        JSONObject jsonObject;
        try {
            jsonObject = userManageService.getUserInfo(principal.getName());
            return jsonObject;
        }
        catch (NullPointerException e) {
            jsonObject = new JSONObject();
            jsonObject.put("status", "error");
            return jsonObject;
        }
    }

    @RequestMapping(value = "/usernamevalid", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject valid(@RequestParam("username") String username) {
        JSONObject response = new JSONObject();
        response.put("valid", userManageService.isUsernameValid(username));
        response.put("status", 200);
        return response;
    }

    @PostMapping(value = "/disableuser")
    @ResponseBody
    public JSONObject disableuser(@RequestParam("username") String username) {
        JSONObject response = new JSONObject();
        response.put("status", 200);
        userManageService.disableCustomer(username);
        return response;
    }

    @PostMapping(value = "/enableuser")
    @ResponseBody
    public JSONObject enableuser(@RequestParam("username") String username) {
        JSONObject response = new JSONObject();
        response.put("status", 200);
        userManageService.enableCustomer(username);
        return response;
    }

    @GetMapping(value = "/userlist")
    @ResponseBody
    public JSONArray userlist() {
        return userManageService.getUserList();
    }
}
