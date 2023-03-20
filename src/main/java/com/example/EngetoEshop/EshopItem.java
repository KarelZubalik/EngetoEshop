package com.example.EngetoEshop;

import java.io.File;

public class EshopItem {
    private Long id;
    private Integer partNo;
    private String name;
    private String description;
    private Boolean isForSale;
    private Double price;

    private File fileInBase64;

    public EshopItem() {
    }

    public EshopItem(Integer partNo, String name, String description, Boolean isForSale, Double price) {
        this.partNo = partNo;
        this.name = name;
        this.description = description;
        this.isForSale = isForSale;
        this.price = price;
    }

    public EshopItem(Long id, Integer partNo, String name, String description, Boolean isForSale, Double price) {
        this.id = id;
        this.partNo = partNo;
        this.name = name;
        this.description = description;
        this.isForSale = isForSale;
        this.price = price;
    }

    public EshopItem(Long id, Integer partNo, String name, String description, Boolean isForSale, Double price, File fileInBase64) {
        this.id = id;
        this.partNo = partNo;
        this.name = name;
        this.description = description;
        this.isForSale = isForSale;
        this.price = price;
        this.fileInBase64 = fileInBase64;
    }

    public File getFileInBase64() {
        return fileInBase64;
    }

    public void setFileInBase64(File fileInBase64) {
        this.fileInBase64 = fileInBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPartNo() {
        return partNo;
    }

    public void setPartNo(Integer partNo) {
        this.partNo = partNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsForSale() {
        return isForSale;
    }

    public void setForSale(Boolean forSale) {
        isForSale = forSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "EshopItem{" +
                "id=" + id +
                ", partNo=" + partNo +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isForSale=" + isForSale +
                ", price=" + price +
                '}';
    }
}
