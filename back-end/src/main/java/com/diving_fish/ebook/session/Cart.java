package com.diving_fish.ebook.session;

import java.util.*;

public class Cart {
    private Map<Long, Integer> data;

    public Cart() {
        data = new LinkedHashMap<>();
    }

    public void put(Long isbn, Integer amount) {
        data.put(isbn, amount);
    }

    public boolean remove(Long isbn) {
        if (data.get(isbn) == null) return false;
        data.remove(isbn);
        return true;
    }

    public Integer get(Long isbn) { return data.get(isbn); }

    public Set<Long> getAll() { return data.keySet();}
}
