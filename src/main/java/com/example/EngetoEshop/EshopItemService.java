package com.example.EngetoEshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EshopItemService {
    Connection connection;

    private EshopItem photoValidator(EshopItem eshopItem){
        if (eshopItem.getFileInBase64().equals("null")){
            eshopItem.setFileInBase64("noPhotoImg.jpg");
        }
        return eshopItem;
    }

    public EshopItemService(
            @Value("${mysql.url}") String mysqlUrl,
            @Value("${mysql.user}") String mysqlUser,
            @Value("${mysql.password}") String mysqlPassword
    ) throws SQLException {
        connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
    }

    public List<EshopItem> getAllEshopItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM engetoproductslist");

        List<EshopItem> resultList = new ArrayList<>();

        while (resultSet.next()) {
            EshopItem eshopItem = extractEshopItemData(resultSet);
            resultList.add(photoValidator(eshopItem));
        }

        return resultList;
    }

    private EshopItem extractEshopItemData(ResultSet resultSet) throws SQLException {
        return new EshopItem(
                resultSet.getLong("id"),
                resultSet.getInt("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getDouble("price"),
                resultSet.getString("image"));
    }

    public Long saveNewItem(EshopItem newItem) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO engetoproductslist(partNo, name, description, isForSale, price, image) VALUES (" + newItem.getPartNo() +", '" + newItem.getName() +"', '"+ newItem.getDescription() +"'," + newItem.getIsForSale() +", "+ newItem.getPrice() +", '"+ newItem.getFileInBase64()+"')",
                Statement.RETURN_GENERATED_KEYS);
        ResultSet set =statement.getGeneratedKeys();
        if (set.next()) {
            return set.getLong(1);
        }else {
            return null;
        }
    }

    public void deleteItem(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM engetoproductslist WHERE id = " + itemId);
    }

    public void setItemPrice(Long itemId,Double price) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE engetoproductslist SET price = "+price+" WHERE id = " + itemId);
    }

    public EshopItem getItem(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM engetoproductslist WHERE id = " + itemId);

        if (resultSet.next()) {
            return photoValidator(extractEshopItemData(resultSet));
        }

        return null;
    }

    public List<String> deleteAllNotSaleItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM engetoproductslist WHERE isForSale=false");

        List<String> idsOfDeletedItems = new ArrayList<>();

        while (resultSet.next()) {
            EshopItem eshopItem = extractEshopItemData(resultSet);
            idsOfDeletedItems.add(eshopItem.getId().toString());
            deleteItem(eshopItem.getId());
        }
        return idsOfDeletedItems;
    }

}
