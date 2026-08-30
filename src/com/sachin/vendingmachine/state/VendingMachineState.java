package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;

public interface VendingMachineState {

    void insertCoin(VendingMachine machine, Coin coin);

    void selectItem(VendingMachine machine, String code);

    void dispense(VendingMachine machine);

    void cancel(VendingMachine machine);
}
