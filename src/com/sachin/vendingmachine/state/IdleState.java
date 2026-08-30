package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;

public class IdleState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {

        throw new IllegalStateException(
                "Please select an item before inserting money"
        );
    }

    @Override
    public void selectItem(VendingMachine machine, String code) {

        Compartment compartment =
                machine.getInventory().getCompartment(code);

        if (compartment.getQuantity() <= 0) {
            throw new IllegalStateException(
                    "Item is out of stock"
            );
        }

        machine.setSelectedCompartment(code);

        System.out.println(
                "Selected item: " +
                        compartment.getItem().getName()
        );

        machine.setState(new ItemSelectedState());
    }

    @Override
    public void dispense(VendingMachine machine) {

        throw new IllegalStateException(
                "Please select an item and insert money"
        );
    }

    @Override
    public void cancel(VendingMachine machine) {

        throw new IllegalStateException(
                "No active transaction"
        );
    }
}
