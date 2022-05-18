package com.example.spring.shoppinglist.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document
public class Category {
    private String categoryName;

    private ArrayList<ListItem> itemsList;

    public Category(String _categoryName) {
        categoryName = _categoryName;
        itemsList = new ArrayList<ListItem>();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public org.bson.Document toDocument() {
        org.bson.Document returnDoc = new org.bson.Document();
        returnDoc.put("items", itemsList);
        return returnDoc;
    }
}
