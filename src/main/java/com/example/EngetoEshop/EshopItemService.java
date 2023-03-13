package com.example.EngetoEshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EshopItemService {
    Connection connection;

    public EshopItemService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eshopengeto",
                "engetoEshopUser",
                "abcd1234"
        );
    }

    public List<EshopItem> getAllEshopItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM engetoproductslist");

        List<EshopItem> resultList = new ArrayList<>();

        while (resultSet.next()) {
            EshopItem EshopItem = extractEshopItemData(resultSet);

            resultList.add(EshopItem);
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
                resultSet.getDouble("price"));
    }

    public Long saveNewItem(EshopItem newItem) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO engetoproductslist(partNo, name, description, isForSale, price) VALUES (" + newItem.getPartNo() +", '" + newItem.getName() +"', '"+ newItem.getDescription() +"'," + newItem.getIsForSale() +", "+ newItem.getPrice() +")",
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

    public void setItemAsDone(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("UPDATE engetoproductslist SET isCompleted = true WHERE id = " + itemId);
    }
    public void setItemPrice(Long itemId,Double price) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE engetoproductslist SET price = "+price+" WHERE id = " + itemId);
    }

    public EshopItem getItem(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM engetoproductslist WHERE id = " + itemId);

        if (resultSet.next()) {
            return extractEshopItemData(resultSet);
        }

        return null;
    }

}
