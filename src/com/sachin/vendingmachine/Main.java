package com.sachin.vendingmachine;

import com.sachin.vendingmachine.inventory.Inventory;
import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;
import com.sachin.vendingmachine.model.Item;

public class Main {

    public static void main(String[] args) {

        // ----------------------------------------------------
        // Create items
        // ----------------------------------------------------

        Item coke = new Item("1", "Coke", 25);
        Item pepsi = new Item("2", "Pepsi", 20);
        Item lays = new Item("3", "Lays", 15);


        // ----------------------------------------------------
        // Create inventory
        // ----------------------------------------------------

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


        // ----------------------------------------------------
        // Create vending machine
        // ----------------------------------------------------

        VendingMachine machine =
                new VendingMachine(inventory);


        // ====================================================
        // Test 1: Exact Payment
        // ====================================================

        System.out.println("\n--- Test 1: Exact Payment ---");

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.FIVE);

        machine.selectItem("A1");
        machine.dispenseItem();


        // ====================================================
        // Test 2: Exact Payment
        // ====================================================

        System.out.println("\n--- Test 2: Exact Payment ---");

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);

        machine.selectItem("A2");
        machine.dispenseItem();


        // ====================================================
        // Test 3: Payment with Change
        // ====================================================

        System.out.println("\n--- Test 3: Payment with Change ---");

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);

        machine.selectItem("A1");
        machine.dispenseItem();


        // ====================================================
        // Test 4: Cancel Transaction
        // ====================================================

        System.out.println("\n--- Test 4: Cancel Transaction ---");

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.FIVE);

        machine.cancel();


        // ====================================================
        // Test 5: Insufficient Payment
        // ====================================================

        System.out.println("\n--- Test 5: Insufficient Payment ---");

        machine.insertCoin(Coin.TEN);
        machine.selectItem("A1");

        try {

            machine.dispenseItem();

        } catch (IllegalStateException e) {

            System.out.println(
                    "Expected error: " + e.getMessage()
            );

            machine.cancel();
        }


        // ====================================================
        // Test 6: Invalid State Operation
        // ====================================================

        System.out.println("\n--- Test 6: Invalid State Operation ---");

        try {

            machine.selectItem("A1");

        } catch (IllegalStateException e) {

            System.out.println(
                    "Expected error: " + e.getMessage()
            );
        }


        // ====================================================
        // Test 7: Invalid Item Code
        // ====================================================

        System.out.println("\n--- Test 7: Invalid Item Code ---");

        machine.insertCoin(Coin.TEN);

        try {

            machine.selectItem("INVALID");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Expected error: " + e.getMessage()
            );

            machine.cancel();
        }


        // ====================================================
        // Test 8: Restocking
        // ====================================================

        System.out.println("\n--- Test 8: Restocking ---");

        inventory.restock("A1", 10);

        System.out.println(
                "A1 restocked successfully"
        );


        // ====================================================
        // Test 9: Purchase after Restocking
        // ====================================================

        System.out.println(
                "\n--- Test 9: Purchase after Restocking ---"
        );

        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.TEN);
        machine.insertCoin(Coin.FIVE);

        machine.selectItem("A1");
        machine.dispenseItem();


        // ====================================================
        // End
        // ====================================================

        System.out.println(
                "\n--- All tests completed ---"
        );
    }
}