package com.sachin.vendingmachine.machine;

import com.sachin.vendingmachine.inventory.CoinInventory;
import com.sachin.vendingmachine.inventory.Inventory;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;
import com.sachin.vendingmachine.model.Item;
import com.sachin.vendingmachine.state.IdleState;
import com.sachin.vendingmachine.state.VendingMachineState;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private final Inventory inventory;
    private final CoinInventory coinInventory;

    private VendingMachineState state;

    private int insertedAmount;
    private final List<Coin> insertedCoins;

    private String selectedCompartment;

    public VendingMachine(Inventory inventory) {

        this.inventory = inventory;
        this.coinInventory = new CoinInventory();
        this.state = new IdleState();
        this.insertedAmount = 0;
        this.insertedCoins = new ArrayList<>();
    }

    // --------------------------------------------------------
    // Public APIs
    // --------------------------------------------------------

    public void insertCoin(Coin coin) {
        state.insertCoin(this, coin);
    }

    public void selectItem(String code) {
        state.selectItem(this, code);
    }

    public void dispenseItem() {
        state.dispense(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    // --------------------------------------------------------
    // Actual dispensing logic
    // --------------------------------------------------------

    public void dispense() {

        Compartment compartment =
                inventory.getCompartment(selectedCompartment);

        Item item = compartment.getItem();

        int change =
                insertedAmount - item.getPrice();

        // Remove item from inventory
        compartment.dispense();

        System.out.println(
                "Dispensing: " + item.getName()
        );

        // Customer's coins now become part of machine inventory
        coinInventory.addCoins(insertedCoins);

        // Return change
        if (change > 0) {
            coinInventory.dispenseChange(change);
        }

        // Transaction completed
        resetTransaction();

        setState(new IdleState());

        System.out.println("Transaction completed");
    }

    // --------------------------------------------------------
    // Transaction management
    // --------------------------------------------------------

    public void returnInsertedMoney() {

        for (Coin coin : insertedCoins) {

            System.out.println(
                    "Returning coin: " + coin
            );
        }
    }

    public void resetTransaction() {

        insertedAmount = 0;
        insertedCoins.clear();
        selectedCompartment = null;
    }

    // --------------------------------------------------------
    // Inventory
    // --------------------------------------------------------

    public Inventory getInventory() {
        return inventory;
    }

    public CoinInventory getCoinInventory() {
        return coinInventory;
    }

    // --------------------------------------------------------
    // State
    // --------------------------------------------------------

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    // --------------------------------------------------------
    // Inserted amount
    // --------------------------------------------------------

    public void addInsertedAmount(int amount) {
        insertedAmount += amount;
    }

    public int getInsertedAmount() {
        return insertedAmount;
    }

    // --------------------------------------------------------
    // Inserted coins
    // --------------------------------------------------------

    public void addInsertedCoin(Coin coin) {
        insertedCoins.add(coin);
    }

    public List<Coin> getInsertedCoins() {
        return new ArrayList<>(insertedCoins);
    }

    // --------------------------------------------------------
    // Selected compartment
    // --------------------------------------------------------

    public void setSelectedCompartment(String code) {
        selectedCompartment = code;
    }

    public String getSelectedCompartment() {
        return selectedCompartment;
    }
}