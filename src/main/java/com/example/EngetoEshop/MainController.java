package com.example.EngetoEshop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class MainController {

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
            EshopItemService eshopItemService = new EshopItemService();
            return eshopItemService.getAllEshopItems();
    }

    @GetMapping("/eshop/{id}")
    public EshopItem getItemById(@PathVariable("id") Long id) throws Exception {
        ifIdExistValidation(id);
        EshopItemService eshopItemService = new EshopItemService();
        return eshopItemService.getItem(id);
    }

    @PostMapping("/eshop")
    public EshopItem postItem(@RequestBody EshopItem eshopItem) throws SQLException, IOException {
            EshopItemService eshopItemService = new EshopItemService();
            eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
            System.out.println(eshopItem.getFileInBase64().getAbsolutePath());
            File file = new File(eshopItem.getFileInBase64().getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        file.createNewFile();
            return eshopItemService.getItem(eshopItem.getId());
    }

    @PutMapping("/eshop/{id}")
    public EshopItem updatePrice(@PathVariable("id") Long id, @RequestBody EshopItem eshopItem) throws Exception {
        ifIdExistValidation(id);
        EshopItemService eshopItemService = new EshopItemService();
        eshopItemService.setItemPrice(id,eshopItem.getPrice());
        return eshopItemService.getItem(id);
    }

    @DeleteMapping("/eshop/{id}")
    public String deleteItem(@PathVariable("id") Long id) throws Exception {
        ifIdExistValidation(id);
        EshopItemService eshopItemService = new EshopItemService();
        eshopItemService.deleteItem(id);
        try{
            ifIdExistValidation(id);
            throw new Exception("Produkt nebyl smazán, kontaktujte prosím správce aplikace s tímto textem a časem.");
        } catch (IdException e) {
            return "Item s id:"+id+" byl smazán.";
        }
    }
}
