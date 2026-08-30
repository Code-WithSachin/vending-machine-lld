# Vending Machine — LLD

Java implementation of a Vending Machine using **Object-Oriented Design** and the **State Design Pattern**.

The machine supports item selection, coin-based payments, inventory management, exact change dispensing, transaction cancellation, and restocking.

---

## 1. Problem Statement

Design a vending machine that allows a customer to:

* Select an item
* Insert coins
* Purchase an item
* Receive exact change
* Cancel a transaction and receive a refund

The machine maintains both **item inventory** and **coin inventory**.

---

## 2. Requirements

### Functional Requirements

* Support multiple items with different prices.
* Each item is stored in a compartment.
* Each compartment maintains item quantity.
* Support ₹1, ₹2, ₹5 and ₹10 coins.
* Allow item selection using compartment code.
* Validate item availability.
* Maintain the amount inserted for the current transaction.
* Validate sufficient payment before dispensing.
* Maintain available coins in the machine.
* Return exact change using available coins.
* Consider inserted customer coins when determining whether exact change can be made.
* Decrease item inventory after successful dispensing.
* Return inserted coins when a transaction is cancelled.
* Allow inventory restocking.
* Reject operations that are invalid for the current machine state.

---

## 3. Scope

### In Scope

* Item and compartment management
* Item inventory and restocking
* Item selection
* Coin insertion
* Coin inventory management
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

| Class            | Responsibility                                                                          |
| ---------------- | --------------------------------------------------------------------------------------- |
| `VendingMachine` | Main controller; maintains transaction data and delegates behavior to the current state |
| `Inventory`      | Manages vending machine compartments                                                    |
| `Compartment`    | Stores an item and maintains its quantity                                               |
| `Item`           | Represents a product with id, name and price                                            |
| `Coin`           | Enum containing supported coin denominations                                            |
| `CoinInventory`  | Maintains available coins and handles exact change dispensing                           |

### State Classes

| State                | Responsibility                                                  |
| -------------------- | --------------------------------------------------------------- |
| `IdleState`          | Waiting for the customer to select an item                      |
| `ItemSelectedState`  | Item has been selected; accepts coins and validates payment     |
| `MoneyInsertedState` | Continues accepting coins and allows dispensing or cancellation |
| `DispensingState`    | Prevents customer operations while the item is being dispensed  |

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
                            +-- ItemSelectedState
                            +-- MoneyInsertedState
                            +-- DispensingState
```

---

## 6. Design Pattern

### State Pattern

The vending machine's behavior depends on its current state.

Instead of putting all state-specific conditions inside `VendingMachine`, each state handles the operations that are valid for that state.

The state interface provides:

```java
insertCoin()
selectItem()
dispense()
cancel()
```

### State Transition Flow

```text
                 select item
Idle ------------------------------> ItemSelected
                                      |
                                      | insert coin
                                      v
                               MoneyInserted
                                      |
                                      | insert coin
                                      | insert coin
                                      | ...
                                      |
                                      | dispense
                                      v
                                  Dispensing
                                      |
                                      v
                                    Idle
```

### Cancellation

Both `ItemSelectedState` and `MoneyInsertedState` allow cancellation:

```text
ItemSelected / MoneyInserted
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

## 7. Main Transaction Flow

The vending machine follows a **select-first** flow:

```text
Select Item
     |
     v
Check Stock
     |
     v
Insert Coins
     |
     v
Check Payment
     |
     v
Check Change Availability
     |
     v
Dispense Item
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

### Example

For a ₹25 Coke:

```text
Select Coke
    |
    v
Insert ₹10
    |
    v
Insert ₹10
    |
    v
Insert ₹10
    |
    v
Total = ₹30
    |
    v
Coke = ₹25
    |
    v
Change = ₹5
    |
    v
Dispense Coke
    |
    v
Return ₹5 coin
```

---

## 8. Coin Inventory

`CoinInventory` maintains the number of coins available inside the vending machine.

Supported denominations:

```text
₹1
₹2
₹5
₹10
```

When a customer inserts coins, those coins are tracked during the current transaction.

After a successful purchase, the inserted coins become part of the machine's coin inventory.

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
Return Change
```

The customer's inserted coins are also considered when checking whether the machine can make exact change.

If exact change cannot be made, the transaction is rejected before dispensing.

---

## 9. Transaction Cancellation

A customer can cancel a transaction after selecting an item and before dispensing.

```text
Select Item
     |
     v
Insert Coins
     |
     v
Cancel
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

The inserted coins are returned individually.

Example:

```text
Inserted: ₹10 + ₹5

Cancel

Returning coin: TEN
Returning coin: FIVE
```

---

## 10. Project Structure

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
    ├── ItemSelectedState.java
    ├── MoneyInsertedState.java
    └── DispensingState.java
```

---

## 11. Test Scenarios

The `Main` class demonstrates the following scenarios:

1. Exact payment
2. Payment with change
3. Transaction cancellation and refund
4. Insufficient payment
5. Invalid state operation
6. Invalid compartment code
7. Inventory restocking
8. Purchase after restocking

Example output:

```text
--- Test 1: Exact Payment ---
Selected item: Coke
Inserted coin: TEN, current amount: 10
Inserted coin: TEN, current amount: 20
Inserted coin: FIVE, current amount: 25
Dispensing: Coke
Transaction completed
```

---

## 12. Future Improvements

* Support Card / UPI payments using a payment abstraction.
* Add admin operations for inventory and pricing.
* Add database persistence.
* Add JUnit test cases.
* Handle concurrent transactions and thread safety.
* Handle hardware and dispensing failures.
* Improve the change-making algorithm for larger coin inventories.
* Add configurable coin denominations.
* Add transaction/payment receipts.

---

## 13. How to Run

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

---

## 14. Key Design Decisions

### Select Item Before Payment

The machine follows the realistic flow:

```text
Select Item → Insert Coins → Dispense
```

This allows the machine to know the required price before accepting payment.

### State Pattern

State-specific behavior is separated into individual classes, making the design easier to extend and maintain.

### Separate Item and Coin Inventories

`Inventory` manages products while `CoinInventory` manages coins.

This follows the **Single Responsibility Principle** and keeps product management independent from payment/change management.

### Exact Change Validation

Before dispensing an item, the machine verifies that the required change can be produced using available coins.

This prevents the machine from dispensing an item when it cannot return the required change.

```
```
