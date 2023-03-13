package com.example.EngetoEshop;

import org.springframework.util.Assert;

import java.sql.SQLException;

public class TestMainer {
    public static void main(String[] args) throws SQLException {
        int partNo=1568797;
        String name="vařečka";
        String description="vařečka do kuchyně TOP";
        boolean isForSale=true;
        double price=5788.788;
        EshopItemService eshopItemService = new EshopItemService();
        EshopItem eshopItem = new EshopItem(partNo,name,description,isForSale,price);
        System.out.println(eshopItem);
        eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
        EshopItem eshopItemFromDb=eshopItemService.getItem(eshopItem.getId());
        System.out.println("before\n"+eshopItem);
        System.out.println("after\n"+eshopItemFromDb);
        eshopItemService.deleteItem(eshopItem.getId());
        Assert.isTrue(eshopItem.getId().equals(eshopItemFromDb.getId()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
        Assert.isTrue(eshopItem.getPartNo()==(eshopItemFromDb.getPartNo()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
        Assert.isTrue(eshopItem.getName().equals(eshopItemFromDb.getName()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
        Assert.isTrue(eshopItem.getDescription().equals(eshopItemFromDb.getDescription()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
        Assert.isTrue(eshopItem.getIsForSale().equals(eshopItemFromDb.getIsForSale()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
        Assert.isTrue(eshopItem.getPrice()==(eshopItemFromDb.getPrice()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
    }
}
