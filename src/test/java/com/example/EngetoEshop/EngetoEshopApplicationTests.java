package com.example.EngetoEshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.Objects;

@SpringBootTest
class EngetoEshopApplicationTests {
	@Autowired
	EshopItemService eshopItemService;
	@Test
	void contextLoads() throws SQLException {
		int partNo=1568797;
		String name="vařečka";
		String description="vařečka do kuchyně TOP";
		boolean isForSale=true;
		double price=5788.788;
		EshopItem eshopItem = new EshopItem(99999L,partNo,name,description,isForSale,price,"null");
		System.out.println(eshopItem);
		eshopItem.setId(eshopItemService.saveNewItem(eshopItem));
		EshopItem eshopItemFromDb=eshopItemService.getItem(eshopItem.getId());
		System.out.println("before\n"+eshopItem);
		System.out.println("after\n"+eshopItemFromDb);
		eshopItemService.deleteItem(eshopItem.getId());
		Assert.isTrue(eshopItem.getId().equals(eshopItemFromDb.getId()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		Assert.isTrue(Objects.equals(eshopItem.getPartNo(), eshopItemFromDb.getPartNo()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		Assert.isTrue(eshopItem.getName().equals(eshopItemFromDb.getName()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		Assert.isTrue(eshopItem.getDescription().equals(eshopItemFromDb.getDescription()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		Assert.isTrue(eshopItem.getIsForSale().equals(eshopItemFromDb.getIsForSale()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		Assert.isTrue(Objects.equals(eshopItem.getPrice(), eshopItemFromDb.getPrice()),"Transformace z Db jej změnila, prosím zkontrolujte, jestli máte vše správně.");
		System.out.println("Testy proběhli validně.");
	}
}
