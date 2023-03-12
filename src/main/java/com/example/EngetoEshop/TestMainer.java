package com.example.EngetoEshop;

import org.springframework.util.Assert;

import java.sql.SQLException;

public class TestMainer {
    public static void main(String[] args) throws SQLException {
        int partNo=1548797;
        String name="vařečka";
        String description="vařečka do kuchyně TOP";
        boolean isForSale=true;
        double price=5788.788;
        EshopItemService eshopItemService = new EshopItemService();
        EshopItem eshopItem = new EshopItem(partNo,name,description,isForSale,price);
        System.out.println(eshopItem);
        eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
        EshopItem eshopItemFromDb=eshopItemService.getItem(eshopItem.getId());
        Assert.isTrue(eshopItem.equals(eshopItemFromDb),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
    }
}
