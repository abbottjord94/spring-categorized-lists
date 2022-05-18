package com.example.spring.shoppinglist.ApplicationClasses;

import com.example.spring.shoppinglist.Entities.Category;
import com.example.spring.shoppinglist.Entities.ItemList;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoDatabaseController {

    private final MongoCollection<Document> listCollection;
    public MongoDatabaseController() {
        MongoClient mc = MongoClients.create();
        MongoDatabase db = mc.getDatabase("shoppinglist-test-db");
        listCollection = db.getCollection("listItems");
    }

    public Document createList(String _listName) {
        System.out.println("createList" + _listName);
        Document listToCreate = new ItemList(_listName).toDocument();
        listCollection.insertOne(listToCreate);
        return listToCreate;
    }

    public String getListByName(String _listName) {
        System.out.println("getListByName: " + _listName);
        FindIterable listDocQueryResult = listCollection.find(Filters.eq("listName", _listName));
        MongoCursor<Document> resultIterator = listDocQueryResult.iterator();
        if(resultIterator.hasNext()) {
            String jsonList = resultIterator.tryNext().toJson();
            return jsonList;
        }
        else {
            return createList(_listName).toJson();
        }
    }

    public void addCategoryToList(String _listName, Category _category) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$set", new Document("categories." + _category.getCategoryName(), _category.toDocument())));
    }

    public void removeCategoryFromList(String _listName, Category _category) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$unset", new Document("categories." + _category.getCategoryName(), new Document())));
    }

    public void addItemToList(String _listName, String _categoryName, Document _item) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$push", new Document("categories." + _categoryName + ".items", _item)));
    }

    public void removeItemFromList(String _listName, String _categoryName, Document _item) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$pull", new Document("categories." + _categoryName + ".items", _item)));
    }
}
