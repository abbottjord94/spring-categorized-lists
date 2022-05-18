package com.example.spring.shoppinglist.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ListItem {

    private String itemName;
    private float itemQuantity;

    public ListItem(String _itemName, float _itemQuantity) {
        super();
        itemName = _itemName;
        itemQuantity = _itemQuantity;
    }

    public org.bson.Document toDocument() {
        org.bson.Document returnDoc = new org.bson.Document();
        returnDoc.put("itemName", itemName);
        returnDoc.put("itemQuantity", itemQuantity);
        return returnDoc;
    }
}
