/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Admin
 */
public class BookDTO {
    private String bookID;
    private String title;
    private String author;
    private int publishyear;
    private double price;
    private int quantity;

    public BookDTO() {
    }

    public BookDTO(String bookID, String title, String author, int publishyear, double price, int quantity) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publishyear = publishyear;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishyear() {
        return publishyear;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishyear(int publishyear) {
        this.publishyear = publishyear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
