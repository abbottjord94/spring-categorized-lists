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

    @PostMapping(value = "/getList",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  String getList(@RequestBody Map<String, String> _requestData) {
        System.out.println("GetList Request Data: " + _requestData.get("listname"));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }

    @PostMapping("/removeItems")
    String removeItems(@RequestBody Map<String, String> _requestData) {

        return "Remove Items";
    }

    @PostMapping("/createCategory")
    public @ResponseBody String createCategory(@RequestBody Map<String, String> _requestData) {
        System.out.println("CreateCategory: " + _requestData.get("category"));
        mongoDatabaseController.addCategoryToList(_requestData.get("listname"), new Category(_requestData.get("category")));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }

    @PostMapping("/addItem")
    String addItem(@RequestBody Map<String, String> _requestData) {
        System.out.println("AddItem: " + _requestData.get("item"));
        mongoDatabaseController.addItemToList(_requestData.get("listname"), _requestData.get("category"), (new ListItem(_requestData.get("item"), 1.0f).toDocument()));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }

    @PostMapping("/removeCategory")
    String removeCategory(@RequestBody Map<String, String> _requestData) {
        System.out.println("RemoveCategory: " + _requestData.get("category"));
        mongoDatabaseController.removeCategoryFromList(_requestData.get("listname"), new Category(_requestData.get("category")));
        String jsonList = mongoDatabaseController.getListByName(_requestData.get("listname"));
        return jsonList;
    }
}