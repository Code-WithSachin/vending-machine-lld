package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;

public class DispensingState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {

        throw new IllegalStateException(
                "Currently dispensing item"
        );
    }

    @Override
    public void selectItem(VendingMachine machine, String code) {

        throw new IllegalStateException(
                "Currently dispensing item"
        );
    }

    @Override
    public void dispense(VendingMachine machine) {

        throw new IllegalStateException(
                "Already dispensing"
        );
    }

    @Override
    public void cancel(VendingMachine machine) {

        throw new IllegalStateException(
                "Cannot cancel while dispensing"
        );
    }
}
