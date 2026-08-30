package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;

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

        throw new IllegalStateException(
                "Item already selected"
        );
    }

    @Override
    public void dispense(VendingMachine machine) {

        // Payment validation and change validation
        // are handled by ItemSelectedState.
        machine.setState(new ItemSelectedState());

        machine.dispense();
    }

    @Override
    public void cancel(VendingMachine machine) {

        machine.returnInsertedMoney();

        machine.resetTransaction();

        machine.setState(new IdleState());
    }
}
