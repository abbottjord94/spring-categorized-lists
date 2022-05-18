package com.example.spring.shoppinglist.ApplicationClasses;

import com.example.spring.shoppinglist.Entities.Category;
import com.example.spring.shoppinglist.Entities.ListItem;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@EnableAutoConfiguration
@RestController
public class ShoppingListRestController {

    private final MongoDatabaseController mongoDatabaseController;

    public ShoppingListRestController() {
        mongoDatabaseController = new MongoDatabaseController();
    }

    /*
        /getList endpoint (POST)
        Returns a list requested by the user

        Data accepted:
            -listName (JSON string)
     */
    @PostMapping(value = "/getList",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  String getList(@RequestBody Map<String, String> _requestData) {
        System.out.println("GetList Request Data: " + _requestData.get("listname"));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
    /*
        /removeItems endpoint (POST)
        Removes items from the list as requested by the user, and then returns the list after removal.

        Data accepted:
            -listName (JSON string)
            -items(JSON array of Objects {category: categoryName, item: itemName} )
     */
    @PostMapping(value = "/removeItems",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String removeItems(@RequestBody Map<String, String> _requestData) {

        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
    /*
    /createCategory endpoint (POST)
    Creates a category as specified by the user, then returns the list after adding the new category

    Data accepted:
        -listName (JSON string)
        -category (JSON string)
 */
    @PostMapping(value = "/createCategory",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String createCategory(@RequestBody Map<String, String> _requestData) {
        System.out.println("CreateCategory: " + _requestData.get("category"));
        mongoDatabaseController.addCategoryToList(_requestData.get("listname"), new Category(_requestData.get("category")));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
    /*
        /addItem endpoint (POST)
        Adds items as specified by the user, and then returns the list with the items added

        Data accepted:
            -listName (JSON string)
            -itemName (JSON string)
     */
    @PostMapping(value = "/addItem",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String addItem(@RequestBody Map<String, String> _requestData) {
        System.out.println("AddItem: " + _requestData.get("item"));
        mongoDatabaseController.addItemToList(_requestData.get("listname"), _requestData.get("category"), (new ListItem(_requestData.get("item"), 1.0f).toDocument()));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
    /*
        /removeCategory endpoint (POST)
        Removes a category specified by the user, and then returns the list with the category removed

        Data accepted:
            -listName (JSON string)
            -category (JSON string)
     */
    @PostMapping(value = "/removeCategory",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String removeCategory(@RequestBody Map<String, String> _requestData) {
        System.out.println("RemoveCategory: " + _requestData.get("category"));
        mongoDatabaseController.removeCategoryFromList(_requestData.get("listname"), new Category(_requestData.get("category")));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
}