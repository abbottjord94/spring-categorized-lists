package com.example.spring.shoppinglist.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class ItemList {
    private String listName;
    private ArrayList<Category> categoryList;

    public ItemList(String _listName) {
        super();
        listName = _listName;
        categoryList = new ArrayList<Category>();
    }

    public org.bson.Document toDocument() {
        org.bson.Document doc = new org.bson.Document();
        doc.put("listName", this.listName);
        doc.put("categories", this.categoryList);
        return doc;
    }
}
