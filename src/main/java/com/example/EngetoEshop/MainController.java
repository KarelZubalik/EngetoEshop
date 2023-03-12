package com.example.EngetoEshop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(Exception e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }


    @GetMapping("/error")
    public EshopItem testError(@RequestParam(value = "hodnota", required = false) String hodnota) throws Exception {
        throw new Exception("Chyba "+hodnota);
    }

    Map<Long, EshopItem> todoMap = new HashMap<>();
    Long seq = 0L;

    @GetMapping("/todo")
    public Collection<EshopItem> getAllItems(){
        return todoMap.values();
    }

    @GetMapping("/todo/{id}")
    public EshopItem getItemById(@PathVariable("id") Long id) throws Exception {
        if (!todoMap.containsKey(id)){
            throw new Exception("Some Exception");
        }
        return todoMap.get(id);
    }

    @PostMapping("/todo")
    public EshopItem postItem(@RequestBody EshopItem eshopItem){
        //Nova hodnota ze sekvence
        seq++;
        Long id = seq;

        //Nastaveni ID pro dalsi pouziti
        eshopItem.setId(id);
        todoMap.put(id, eshopItem);
        return eshopItem;
    }

    @PutMapping("/todo/{id}")
    public EshopItem putItem(@PathVariable("id") Long id, @RequestBody EshopItem eshopItem){

        eshopItem.setId(id);
        todoMap.put(id, eshopItem);
        return eshopItem;
    }

    @DeleteMapping("/todo/{id}")
    public void deleteItem(@PathVariable("id") Long id){
        todoMap.remove(id);
    }

}
