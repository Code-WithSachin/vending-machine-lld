package com.sachin.vendingmachine.model;

public class Compartment {

    private final String code;
    private final Item item;
    private int quantity;

    public Compartment(String code, Item item, int quantity) {
        this.code = code;
        this.item = item;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void dispense() {
        if (quantity <= 0) {
            throw new IllegalStateException("Item is out of stock");
        }

        quantity--;
    }

    public void restock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Restock quantity must be positive"
            );
        }

        this.quantity += quantity;
    }
}
