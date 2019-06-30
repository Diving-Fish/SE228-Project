package com.diving_fish.ebook.ServiceImpl;

import com.diving_fish.ebook.Entity.BookEntity;
import com.diving_fish.ebook.Entity.OrderEntity;
import com.diving_fish.ebook.Repository.OrderRepository;
import com.diving_fish.ebook.Repository.UserRepository;
import com.diving_fish.ebook.Service.BookManageService;
import com.diving_fish.ebook.Service.OrderManageService;
import com.diving_fish.ebook.Service.UserManageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class OrderManageServiceImpl implements OrderManageService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookManageService bookManageService;

    @Autowired
    private UserRepository userRepository;

    public String stampToString(Date date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    @Override
    public void submitOrder(Integer id, JSONObject form) {
        Date date = new Date(System.currentTimeMillis());
        Random r = new Random();
        int r_id, group_id, order_id;
        while (true) {
            r_id = r.nextInt(90000000) + 10000000;
            List<OrderEntity> ue = orderRepository.findAllByGroupid(r_id);
            if (ue.size() == 0) {
                group_id = r_id;
                break;
            }
        }
        JSONArray cart = form.getJSONArray("cart");
        for (Object object : cart) {
            JSONObject jsonObject = (JSONObject) object;
            Integer amount = jsonObject.getInt("amount");
            Long isbn = jsonObject.getLong("isbn");
            while (true) {
                r_id = r.nextInt(90000000) + 10000000;
                OrderEntity o = orderRepository.findById(r_id);
                if (o == null) {
                    order_id = r_id;
                    break;
                }
            }
            OrderEntity o = new OrderEntity(order_id, group_id, id, isbn, amount, date, date);
            JSONObject book = bookManageService.getBook(isbn);
            book.put("stock", book.getInt("stock") - amount);
            bookManageService.modifyBook(book);
            orderRepository.save(o);
        }
    }

    @Override
    public JSONObject getOrders(Integer id) {
        JSONObject response = new JSONObject();
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserid(id);
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
            jsonObject.put("book", bookManageService.getBook(order.getIsbn()));
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

    @Override
    public JSONObject getAllOrders() {
        JSONObject response = new JSONObject();
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        Map<Integer, JSONObject> groupMap = new LinkedHashMap<>();
        for (OrderEntity order: orderEntityList) {
            int groupid = order.getGroupId();
            if (groupMap.get(groupid) == null) {
                JSONObject groupObject = new JSONObject();
                groupObject.put("groupid", groupid);
                groupObject.put("date", stampToString(order.getCreate_date()));
                JSONArray orderArray = new JSONArray();
                groupObject.put("orders", orderArray);
                groupObject.put("user", userRepository.findById(order.getUserid()).getUsername());
                groupMap.put(groupid, groupObject);
            }
            JSONObject groupObject = groupMap.get(groupid);
            JSONArray orderArray = groupObject.getJSONArray("orders");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderid", order.getId());
            jsonObject.put("amount", order.getAmount());
            jsonObject.put("book", bookManageService.getBook(order.getIsbn()));
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
}
