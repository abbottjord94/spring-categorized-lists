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

    /*
        createList(String _listName)
            -Creates a list in the MongoDB database with the listName field specified by the _listName parameter.

            Returns the MongoDB Document that was created
     */
    public Document createList(String _listName) {
        System.out.println("createList" + _listName);
        Document listToCreate = new ItemList(_listName).toDocument();
        listCollection.insertOne(listToCreate);
        return listToCreate;
    }
    /*
        String getListByName(String _listName)
            -Returns a list as a JSON-formatted string with the listName field specified by the _listName parameter. If no list is found, the createList() function is called.

            Returns the JSON-formatted string of the list specified by the _listName parameter.
     */
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

    /*
        addCategoryToList(String _listName, Category _category)
            -Adds a Category _category to the list specified by _listName in the MongoDB database
     */
    public void addCategoryToList(String _listName, Category _category) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$set", new Document("categories." + _category.getCategoryName(), _category.toDocument())));
    }
    /*
        removeCategoryFromList(String _listName, Category _category)
            -Removes a Category _category to the list specified by _listName from the MongoDB database
     */
    public void removeCategoryFromList(String _listName, Category _category) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$unset", new Document("categories." + _category.getCategoryName(), new Document())));
    }
    /*
        addItemToList(String _listName, String _categoryName, Document _item)
            -Adds an Item _item in the Category _category to the list specified by _listName in the MongoDB database
     */
    public void addItemToList(String _listName, String _categoryName, Document _item) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$push", new Document("categories." + _categoryName + ".items", _item)));
    }
    /*
        removeItemFromList(String _listName, String _categoryName, Document _item)
            -Removes an Item _item in the Category _category from the list specified by _listName in the MongoDB database
     */
    public void removeItemFromList(String _listName, String _categoryName, Document _item) {
        listCollection.updateOne(Filters.eq("listName", _listName), new Document("$pull", new Document("categories." + _categoryName + ".items", _item)));
    }
}
