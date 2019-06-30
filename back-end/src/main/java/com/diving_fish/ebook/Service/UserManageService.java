package com.diving_fish.ebook.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface UserManageService {
    void createUser(String username, String password, String email, int role);
    void createAdmin(String username, String password, String email);
    void createCustomer(String username, String password, String email);
    void disableCustomer(String username);
    void enableCustomer(String username);
    JSONObject getUserInfo(String username);
    JSONArray getUserList();
    boolean isLogin(String username);
    boolean isUsernameValid(String username);
}
