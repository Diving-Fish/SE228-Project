package com.diving_fish.ebook.controller;

import com.diving_fish.ebook.entity.BookEntity;
import com.diving_fish.ebook.repository.BookRepository;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;

import java.awt.print.Book;
import java.io.*;
import java.util.List;

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
}