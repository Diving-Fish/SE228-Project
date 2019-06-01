package com.diving_fish.ebook.Controller;

import com.diving_fish.ebook.Service.BookManageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class BookController {
    @Autowired
    private BookManageService bookManageService;

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getbooklist() {
        return bookManageService.getBookList();
    }

    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getbook(@RequestParam("isbn") Long isbn) throws NullPointerException {
        return bookManageService.getBook(isbn);
    }

    @RequestMapping(value = "/modifybook", method = RequestMethod.POST)
    @ResponseBody
    public void modifybook(@RequestBody JSONObject book_info) {
        bookManageService.modifyBook(book_info);
    }

    @RequestMapping(value = "/removebook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removebook(@RequestParam("isbn") Long isbn) {
        return bookManageService.removeBook(isbn);
    }

}
