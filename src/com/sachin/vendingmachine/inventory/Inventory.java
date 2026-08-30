package com.sachin.vendingmachine.inventory;

import com.sachin.vendingmachine.model.Compartment;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Compartment> compartments;

    public Inventory() {
        compartments = new HashMap<>();
    }

    public void addCompartment(Compartment compartment) {

        if (compartments.containsKey(compartment.getCode())) {
            throw new IllegalArgumentException(
                    "Compartment already exists: " + compartment.getCode()
            );
        }

        compartments.put(compartment.getCode(), compartment);
    }

    public Compartment getCompartment(String code) {

        Compartment compartment = compartments.get(code);

        if (compartment == null) {
            throw new IllegalArgumentException(
                    "Invalid compartment code: " + code
            );
        }

        return compartment;
    }

    public boolean isAvailable(String code) {
        return getCompartment(code).isAvailable();
    }

    public void restock(String code, int quantity) {
        getCompartment(code).restock(quantity);
    }
}
