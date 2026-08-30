package com.sachin.vendingmachine;

import com.sachin.vendingmachine.inventory.Inventory;
import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;
import com.sachin.vendingmachine.model.Item;

public class Main {

    public static void main(String[] args) {

        // Create items
        Item coke =
                new Item("1", "Coke", 25);

        Item pepsi =
                new Item("2", "Pepsi", 20);

        Item lays =
                new Item("3", "Lays", 15);


        // Create inventory
        Inventory inventory = new Inventory();

        inventory.addCompartment(
                new Compartment("A1", coke, 5)
        );

        inventory.addCompartment(
                new Compartment("A2", pepsi, 3)
        );

        inventory.addCompartment(
                new Compartment("B1", lays, 4)
        );


        // Create vending machine
        VendingMachine machine =
                new VendingMachine(inventory);


        // ----------------------------------------------------
        // Transaction 1
        // ----------------------------------------------------

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.FIVE);

        machine.selectItem("A1");

        machine.dispenseItem();


        // ----------------------------------------------------
        // Transaction 2
        // ----------------------------------------------------

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);

        machine.selectItem("A2");

        machine.dispenseItem();


        // ----------------------------------------------------
        // Cancel transaction
        // ----------------------------------------------------

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.FIVE);

        machine.cancel();


        // ----------------------------------------------------
        // Restock
        // ----------------------------------------------------

        inventory.restock("A1", 10);
    }
}
