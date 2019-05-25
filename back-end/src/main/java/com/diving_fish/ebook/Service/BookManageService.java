package com.diving_fish.ebook.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BookManageService {
    JSONArray getBookList();
    JSONObject getBook(Long isbn);
    void modifyBook(JSONObject book);
    JSONObject removeBook(Long isbn);
}
