package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;

public class ItemSelectedState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {

        machine.addInsertedAmount(coin.getValue());
        machine.addInsertedCoin(coin);

        System.out.println(
                "Inserted coin: " + coin +
                        ", current amount: " + machine.getInsertedAmount()
        );

        machine.setState(new MoneyInsertedState());
    }

    @Override
    public void selectItem(VendingMachine machine, String code) {

        throw new IllegalStateException(
                "Item already selected"
        );
    }

    @Override
    public void dispense(VendingMachine machine) {

        throw new IllegalStateException(
                "Please insert enough money first"
        );
    }

    @Override
    public void cancel(VendingMachine machine) {

        machine.returnInsertedMoney();

        machine.resetTransaction();

        machine.setState(new IdleState());
    }
}
