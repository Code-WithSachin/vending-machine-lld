package com.sachin.vendingmachine.inventory;

import com.sachin.vendingmachine.model.Coin;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CoinInventory {

    private final Map<Coin, Integer> coins;

    public CoinInventory() {
        coins = new EnumMap<>(Coin.class);

        for (Coin coin : Coin.values()) {
            coins.put(coin, 0);
        }
    }

    // --------------------------------------------------------
    // Add coins
    // --------------------------------------------------------

    public void addCoins(Coin coin, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Coin quantity must be positive"
            );
        }

        coins.put(
                coin,
                coins.get(coin) + quantity
        );
    }

    public void addCoins(List<Coin> insertedCoins) {

        for (Coin coin : insertedCoins) {
            addCoins(coin, 1);
        }
    }

    // --------------------------------------------------------
    // Dispense change
    // --------------------------------------------------------

    public boolean canDispenseChange(int amount) {

        int remaining = amount;

        for (Coin coin : getCoinsByDescendingValue()) {

            int availableCoins = coins.get(coin);

            int coinsNeeded =
                    remaining / coin.getValue();

            int coinsToUse =
                    Math.min(coinsNeeded, availableCoins);

            remaining -=
                    coinsToUse * coin.getValue();

            if (remaining == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean canDispenseChange(
            int amount,
            java.util.List<Coin> additionalCoins) {

        int remaining = amount;

        // Create a temporary copy of the machine's inventory
        Map<Coin, Integer> availableCoins =
                new EnumMap<>(coins);

        // Add the customer's inserted coins
        for (Coin coin : additionalCoins) {

            availableCoins.put(
                    coin,
                    availableCoins.get(coin) + 1
            );
        }

        // Try to make the required change
        for (Coin coin : getCoinsByDescendingValue()) {

            int availableCount =
                    availableCoins.get(coin);

            int coinsNeeded =
                    remaining / coin.getValue();

            int coinsToUse =
                    Math.min(coinsNeeded, availableCount);

            remaining -=
                    coinsToUse * coin.getValue();

            if (remaining == 0) {
                return true;
            }
        }

        return false;
    }

    public void dispenseChange(int amount) {

        int remaining = amount;

        for (Coin coin : getCoinsByDescendingValue()) {

            while (
                    remaining >= coin.getValue()
                            && coins.get(coin) > 0
            ) {

                remaining -= coin.getValue();

                coins.put(
                        coin,
                        coins.get(coin) - 1
                );

                System.out.println(
                        "Returning change coin: " + coin
                );
            }
        }

        if (remaining > 0) {

            throw new IllegalStateException(
                    "Unable to return exact change. Remaining: "
                            + remaining
            );
        }
    }

    // --------------------------------------------------------
    // Helpers
    // --------------------------------------------------------

    private List<Coin> getCoinsByDescendingValue() {

        List<Coin> result =
                new ArrayList<>(List.of(Coin.values()));

        result.sort(
                (a, b) ->
                        Integer.compare(
                                b.getValue(),
                                a.getValue()
                        )
        );

        return result;
    }
}