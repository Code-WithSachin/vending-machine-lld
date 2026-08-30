```java
package com.sachin.vendingmachine.state;

import com.sachin.vendingmachine.machine.VendingMachine;
import com.sachin.vendingmachine.model.Coin;
import com.sachin.vendingmachine.model.Compartment;
import com.sachin.vendingmachine.model.Item;

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

        Compartment compartment =
                machine.getInventory()
                        .getCompartment(machine.getSelectedCompartment());

        Item item = compartment.getItem();

        int insertedAmount =
                machine.getInsertedAmount();

        // Check payment
        if (insertedAmount < item.getPrice()) {

            throw new IllegalStateException(
                    "Insufficient amount. Required: " +
                            item.getPrice() +
                            ", inserted: " +
                            insertedAmount
            );
        }

        int change =
                insertedAmount - item.getPrice();

        // Check whether exact change can be returned
        if (change > 0 &&
                !machine.getCoinInventory()
                        .canDispenseChange(
                                change,
                                machine.getInsertedCoins()
                        )) {

            throw new IllegalStateException(
                    "Unable to return exact change: " + change
            );
        }

        machine.setState(new DispensingState());

        machine.dispense();
    }

    @Override
    public void cancel(VendingMachine machine) {

        machine.returnInsertedMoney();

        machine.resetTransaction();

        machine.setState(new IdleState());
    }
}
```
