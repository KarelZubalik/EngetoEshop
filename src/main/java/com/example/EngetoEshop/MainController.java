package com.example.EngetoEshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class MainController {
    @Autowired
    EshopItemService eshopItemService;

    private void ifIdExistValidation(Long id) throws IdException, SQLException {
        List<EshopItem> collection = (List<EshopItem>) getAllItems();
        if (collection.isEmpty()) {
            throw new IdException(id);
        }
        for (int i = 0; i < collection.size(); i++) {
            if (Objects.equals(collection.get(i).getId(), id)){
                    break;
            } else {
                    if ((collection.size() - 1) == i) {
                        throw new IdException(id);
                }
            }
        }
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(Exception e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }

    @GetMapping("/eshop")
    public Collection<EshopItem> getAllItems() throws SQLException {
            return eshopItemService.getAllEshopItems();
    }

    @GetMapping("/eshop/{id}")
    public EshopItem getItemById(@PathVariable("id") Long id) throws Exception {
        ifIdExistValidation(id);
        return eshopItemService.getItem(id);
    }

    @PostMapping("/eshop")
    public EshopItem postItem(@RequestBody EshopItem eshopItem) throws SQLException, IOException {
            eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
            return eshopItemService.getItem(eshopItem.getId());
    }

    @PutMapping("/eshop/{id}")
    public EshopItem updatePrice(@PathVariable("id") Long id, @RequestBody EshopItem eshopItem) throws Exception {
        ifIdExistValidation(id);
        eshopItemService.setItemPrice(id,eshopItem.getPrice());
        return eshopItemService.getItem(id);
    }

    @DeleteMapping("/eshop/{id}")
    public String deleteItem(@PathVariable("id") Long id) throws Exception {
        ifIdExistValidation(id);
        eshopItemService.deleteItem(id);
        try{
            ifIdExistValidation(id);
            throw new Exception("Produkt nebyl smazán, kontaktujte prosím správce aplikace s tímto textem a časem.");
        } catch (IdException e) {
            return "Item s id:"+id+" byl smazán.";
        }
    }
    @DeleteMapping("/eshop/deleteAllNotSaleItems")
    public String deleteAllNotSaleItems() throws Exception {
        return "Ids of deleted items: "+eshopItemService.deleteAllNotSaleItems();
        }
}
