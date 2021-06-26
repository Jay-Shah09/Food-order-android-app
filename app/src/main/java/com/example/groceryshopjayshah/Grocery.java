package com.example.groceryshopjayshah;

public class Grocery
{
    String gid;
    String imageUri;
    String gName;
    int price,stock;
    String measure;

    public Grocery(){
    }

    public Grocery(String gid, String imageUri, String gName, int price, int stock, String measure) {
        this.gid = gid;
        this.imageUri = imageUri;
        this.gName = gName;
        this.price = price;
        this.stock = stock;
        this.measure = measure;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
