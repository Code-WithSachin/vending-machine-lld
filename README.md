# Vending Machine — LLD

Java implementation of a Vending Machine using **Object-Oriented Design** and the **State Design Pattern**.

## 1. Problem Statement

Design a vending machine that allows a customer to:

- Insert coins
- Select an item
- Purchase an item
- Receive change
- Cancel a transaction and receive a refund

The machine should maintain item inventory and support restocking.

---

## 2. Requirements

### Functional Requirements

- Support multiple items with different prices.
- Each item is stored in a compartment.
- Each compartment maintains item quantity.
- Support ₹1, ₹2, ₹5 and ₹10 coins.
- Maintain the amount inserted for the current transaction.
- Allow item selection using compartment code.
- Validate item availability.
- Validate sufficient payment before dispensing.
- Decrease inventory after successful dispensing.
- Calculate and return change.
- Allow transaction cancellation before dispensing.
- Allow inventory restocking.
- Reject operations that are invalid for the current machine state.

---

## 3. Out of Scope

- Physical coin inventory.
- Actual change dispensing using available coins.
- Card / UPI / wallet payments.
- Database persistence.
- Admin authentication and operations.
- Hardware integration.
- Concurrent transactions.

> Currently, change is only calculated and printed; the machine does not maintain actual coin inventory.

---

## 4. Core Classes

| Class | Responsibility |
|---|---|
| `VendingMachine` | Main controller; manages transaction and delegates behavior to current state |
| `Inventory` | Manages vending machine compartments |
| `Compartment` | Stores an item and maintains its quantity |
| `Item` | Represents product with id, name and price |
| `Coin` | Enum containing supported denominations |

### State Classes

| State | Responsibility |
|---|---|
| `IdleState` | Waiting for customer to insert money |
| `MoneyInsertedState` | Accepting coins and allowing item selection |
| `ItemSelectedState` | Validating payment and initiating dispensing |
| `DispensingState` | Preventing customer operations while dispensing |

---

## 5. Relationships

```text
VendingMachine
      |
      | has
      v
  Inventory
      |
      | contains
      v
 Compartment
      |
      | contains
      v
    Item
```

The `VendingMachine` also maintains the current state:

```text
VendingMachine
      |
      v
VendingMachineState
      |
      +-- IdleState
      +-- MoneyInsertedState
      +-- ItemSelectedState
      +-- DispensingState
```

---

## 6. Design Pattern

### State Pattern

Used because the behavior of the vending machine depends on its current state.

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

This avoids putting all state-specific conditions inside `VendingMachine`.

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
Reset Transaction
     |
     v
Idle
```

### Cancellation

```text
MoneyInserted / ItemSelected
            |
            v
       Return Money
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
│   └── Inventory.java
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

## 9. Future Improvements

- Maintain physical coin inventory.
- Implement actual change-dispensing algorithm.
- Support Card / UPI payments using a payment abstraction.
- Add admin operations for inventory and pricing.
- Add database persistence.
- Add JUnit test cases.
- Handle concurrency and thread safety.
- Handle hardware/dispensing failures.

---

## 10. How to Run

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

