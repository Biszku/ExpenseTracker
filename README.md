# Expense Tracker

## Description

This is a simple expense tracker application that allows users to add, update,
delete, list all expenses and display either general expenses summary or expenses summary 
by month.

## Features

- Add a new expense
- Update a specific expense
- Delete a specific expense
- List all expenses
- Display expenses summary
- Display expenses summary by month

## Installation

1. Clone the repository
```bash
git clone https://github.com/Biszku/ExpenseTracker
cd ExpenseTracker
```

2. Compile the source code:
```bash
javac -d out .\src\*.java
```

3. Run the application:
```bash
java -cp out Main [opearation] [arguments]...
```

## Usage

```bash
#add a new expense
Example: java -cp out Main add --description "Lunch" --amount 20
#output: Expense added successfully (ID: 1)

#update a specific expense
Example: java -cp out Main update --id 1 --description "Dinner" --amount 10
#output: Expense updated successfully (ID: 1)

#delete a specific expense
Example: java -cp out Main delete --id 1
#output: Expense deleted successfully (ID: 1)

#list all expenses
Example: java -cp out Main list
#output:
# ID  Date       Description  Amount
# 1   2024-08-06  Lunch        $20
# 2   2024-08-06  Dinner       $10
#...

#display expenses summary
Example: java -cp out Main summary
#output: Total expenses: $120

#display expenses summary by month
Example: java -cp out Main summary --month 8
#output: Total expenses: $120
```