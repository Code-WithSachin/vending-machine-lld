package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;

public class MoneyInsertedState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {

        machine.addInsertedAmount(coin.getValue());
        machine.addInsertedCoin(coin);

        System.out.println(
                "Inserted coin: " + coin +
                        ", current amount: " + machine.getInsertedAmount()
        );
    }

    @Override
    public void selectItem(VendingMachine machine, String code) {

        Compartment compartment =
                machine.getInventory().getCompartment(code);

        if (!compartment.isAvailable()) {
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
                "Please select an item first"
        );
    }

    @Override
    public void cancel(VendingMachine machine) {

        machine.returnInsertedMoney();

        machine.resetTransaction();

        machine.setState(new IdleState());
    }
}