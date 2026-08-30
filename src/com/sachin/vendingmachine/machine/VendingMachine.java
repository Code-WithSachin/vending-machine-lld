package com.sachin.vendingmachine.machine;

import com.sachin.vendingmachine.inventory.Inventory;
import com.sachin.vendingmachine.model.Compartment;
import com.sachin.vendingmachine.model.Item;
import com.sachin.vendingmachine.state.IdleState;
import com.sachin.vendingmachine.state.VendingMachineState;

public class VendingMachine {

    private final Inventory inventory;

    private VendingMachineState state;

    private int insertedAmount;

    private String selectedCompartment;

    public VendingMachine(Inventory inventory) {
        this.inventory = inventory;
        this.state = new IdleState();
        this.insertedAmount = 0;
    }

    // --------------------------------------------------------
    // Public APIs
    // --------------------------------------------------------

    public void insertCoin(com.sachin.vendingmachine.model.Coin coin) {
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

        // Return change
        if (change > 0) {
            System.out.println(
                    "Returning change: " + change
            );
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

        if (insertedAmount > 0) {

            System.out.println(
                    "Returning money: " + insertedAmount
            );
        }
    }

    public void resetTransaction() {

        insertedAmount = 0;
        selectedCompartment = null;
    }

    // --------------------------------------------------------
    // Inventory
    // --------------------------------------------------------

    public Inventory getInventory() {
        return inventory;
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
    // Selected compartment
    // --------------------------------------------------------

    public void setSelectedCompartment(String code) {
        selectedCompartment = code;
    }

    public String getSelectedCompartment() {
        return selectedCompartment;
    }
}
