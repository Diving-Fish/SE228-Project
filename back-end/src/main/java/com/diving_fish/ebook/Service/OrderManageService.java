package com.diving_fish.ebook.Service;

import net.sf.json.JSONObject;

public interface OrderManageService {
    void submitOrder(Integer id, JSONObject form);
    JSONObject getOrders(Integer id);
    JSONObject getAllOrders();
}
