# Vending Machine — LLD

Java implementation of a Vending Machine using **Object-Oriented Design** and the **State Design Pattern**.

## 1. Problem Statement

Design a vending machine that allows a customer to:

* Insert coins
* Select an item
* Purchase an item
* Receive change
* Cancel a transaction and receive a refund

The machine maintains item inventory and coin inventory.

---

## 2. Requirements

### Functional Requirements

* Support multiple items with different prices.
* Each item is stored in a compartment.
* Each compartment maintains item quantity.
* Support ₹1, ₹2, ₹5 and ₹10 coins.
* Maintain the amount inserted for the current transaction.
* Allow item selection using compartment code.
* Validate item availability.
* Validate sufficient payment before dispensing.
* Decrease inventory after successful dispensing.
* Maintain available coins in the machine.
* Return exact change using available coins.
* Return inserted coins when a transaction is cancelled.
* Allow inventory restocking.
* Reject operations that are invalid for the current machine state.

---

## 3. Scope

### In Scope

* Item and compartment management
* Item inventory and restocking
* Coin insertion
* Coin inventory management
* Transaction management
* Item selection
* Payment validation
* Item dispensing
* Exact change calculation and dispensing
* Transaction cancellation and refund
* State-based vending machine behavior

### Out of Scope

* Card / UPI / wallet payments
* Database persistence
* Admin authentication and operations
* Hardware integration
* Concurrent transactions
* Physical dispensing mechanisms

---

## 4. Core Classes

| Class            | Responsibility                                                                   |
| ---------------- | -------------------------------------------------------------------------------- |
| `VendingMachine` | Main controller; manages transaction and delegates behavior to the current state |
| `Inventory`      | Manages vending machine compartments                                             |
| `Compartment`    | Stores an item and maintains its quantity                                        |
| `Item`           | Represents product with id, name and price                                       |
| `Coin`           | Enum containing supported coin denominations                                     |
| `CoinInventory`  | Maintains available coins and handles change/refund                              |

### State Classes

| State                | Responsibility                                  |
| -------------------- | ----------------------------------------------- |
| `IdleState`          | Waiting for customer to insert money            |
| `MoneyInsertedState` | Accepting coins and allowing item selection     |
| `ItemSelectedState`  | Validating payment and initiating dispensing    |
| `DispensingState`    | Preventing customer operations while dispensing |

---

## 5. Relationships

```text
VendingMachine
      |
      +---- has ----> Inventory
      |                  |
      |                  +---- contains ----> Compartment
      |                                           |
      |                                           +---- contains ----> Item
      |
      +---- has ----> CoinInventory
      |
      +---- has ----> VendingMachineState
                            |
                            +-- IdleState
                            +-- MoneyInsertedState
                            +-- ItemSelectedState
                            +-- DispensingState
```

---

## 6. Design Pattern

### State Pattern

The vending machine's behavior depends on its current state.

```text
Idle
  |
  | insert coin
  v
MoneyInserted
  |
  | select item
  v
ItemSelected
  |
  | dispense
  v
Dispensing
  |
  v
Idle
```

Each state implements:

```java
insertCoin()
selectItem()
dispense()
cancel()
```

This keeps state-specific behavior inside the respective state classes instead of putting all state conditions inside `VendingMachine`.

---

## 7. Main Transaction Flow

```text
Insert Coins
     |
     v
Select Item
     |
     v
Check Stock
     |
     v
Check Payment
     |
     v
Dispense Item
     |
     v
Calculate Change
     |
     v
Dispense Change
     |
     v
Reset Transaction
     |
     v
Idle
```

### Coin Inventory

Inserted coins are added to the machine's `CoinInventory`.

When change is required:

```text
Required Change
      |
      v
Check CoinInventory
      |
      v
Find Exact Combination
      |
      v
Remove Change Coins
      |
      v
Return Coins
```

The customer's inserted coins are also considered when determining whether exact change can be made.

### Cancellation

```text
MoneyInserted / ItemSelected
            |
            v
     Return Inserted Coins
            |
            v
      Reset Transaction
            |
            v
           Idle
```

---

## 8. Project Structure

```text
src/com/sachin/vendingmachine/
│
├── Main.java
│
├── model/
│   ├── Coin.java
│   ├── Item.java
│   └── Compartment.java
│
├── inventory/
│   ├── Inventory.java
│   └── CoinInventory.java
│
├── machine/
│   └── VendingMachine.java
│
└── state/
    ├── VendingMachineState.java
    ├── IdleState.java
    ├── MoneyInsertedState.java
    ├── ItemSelectedState.java
    └── DispensingState.java
```

---

## 9. Test Scenarios

The `Main` class demonstrates:

* Exact payment
* Payment with change
* Transaction cancellation and refund
* Insufficient payment
* Invalid state operation
* Invalid compartment code
* Inventory restocking
* Purchase after restocking

---

## 10. Future Improvements

* Support Card / UPI payments using a payment abstraction.
* Add admin operations for inventory and pricing.
* Add database persistence.
* Add JUnit test cases.
* Handle concurrent transactions and thread safety.
* Handle hardware/dispensing failures.
* Improve change-making algorithm for larger coin inventories.

---

## 11. How to Run

### Compile

```bash
rm -rf out
mkdir out
javac -d out $(find src -name "*.java")
```

### Run

```bash
java -cp out com.sachin.vendingmachine.Main
```
