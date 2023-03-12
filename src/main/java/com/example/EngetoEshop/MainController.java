package com.example.EngetoEshop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class MainController {

    private boolean ifIdExistValidation(Long id){
        List<EshopItem> collection = (List<EshopItem>) getAllItems();
        for (int i = 0; i < collection.size(); i++) {
            if (Objects.equals(collection.get(i).getId(), id)){
                    return true;
            } else {
                if ((collection.size()-1)==i) {
                    handleError(new Exception("Produkt s id="+id+" nebyl nalezen."));
                }
            }
        }
        return false;
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(Exception e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }


    @GetMapping("/error")
    public EshopItem testError(@RequestParam(value = "hodnota", required = false) String hodnota) throws Exception {
        throw new Exception("Chyba "+hodnota);
    }


    @GetMapping("/eshop")
    public Collection<EshopItem> getAllItems(){
        try {
            EshopItemService eshopItemService = new EshopItemService();
            return eshopItemService.getAllEshopItems();
        } catch (SQLException e) {
            handleError(e);
            return null;
        }
    }

    @GetMapping("/eshop/{id}")
    public EshopItem getItemById(@PathVariable("id") Long id) {
        /*List<EshopItem> collection = (List<EshopItem>) getAllItems();
        for (int i = 0; i < collection.size(); i++) {
            if (Objects.equals(collection.get(i).getId(), id)){
                try {
                    EshopItemService eshopItemService = new EshopItemService();
                    return eshopItemService.getItem(id);
                } catch (SQLException e) {
                    handleError(e);
                }
            } else {
                if ((collection.size()-1)==i) {
                    handleError(new Exception("Nenalezl jsem žádný produkt s tímto ID"));
                    return null;
                }
            }
        }
         */
        if (ifIdExistValidation(id)) {
            try {
                EshopItemService eshopItemService = new EshopItemService();
                return eshopItemService.getItem(id);
            } catch (SQLException e) {
                handleError(e);
            }
        }
        return null;
    }

    @PostMapping("/eshop")
    public EshopItem postItem(@RequestBody EshopItem eshopItem){
        //Nova hodnota ze sekvence
        try{
            EshopItemService eshopItemService = new EshopItemService();
            eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
            return eshopItemService.getItem(eshopItem.getId());
        } catch (SQLException e) {
            handleError(e);
            return null;
        }
//        seq++;
//        Long id = seq;

        //Nastaveni ID pro dalsi pouziti
//        eshopItem.setId(id);
//        eshopItemsMap.put(id, eshopItem);
    }

    @PutMapping("/eshop/{id}")
    public EshopItem updatePrice(@PathVariable("id") Long id, @RequestBody EshopItem eshopItem){
        if (ifIdExistValidation(id)) {
            System.out.println(eshopItem);
            try {
                EshopItemService eshopItemService = new EshopItemService();
                eshopItemService.setItemPrice(id,eshopItem.getPrice());
                return eshopItemService.getItem(id);
            } catch (SQLException e) {
                handleError(e);
            }
        }
        return null;
    }

    @DeleteMapping("/eshop/{id}")
    public void deleteItem(@PathVariable("id") Long id){
        if (ifIdExistValidation(id)) {
            try {
                EshopItemService eshopItemService = new EshopItemService();
                eshopItemService.deleteItem(id);
            } catch (SQLException e) {
                handleError(e);
            }
        }
    }
}
