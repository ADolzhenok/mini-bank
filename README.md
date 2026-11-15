# Mini Bank

A console-based Java banking backend simulation.

The project demonstrates:
- user and account management
- money transfers with commission support
- business rule validation
- separation of responsibilities between layers

## Features

### User Management
- create users

### Account Management
- create accounts for users
- deposit money
- withdraw money
- close accounts
- money transfers between accounts

## Business Rules
- duplicate users are not allowed
- login must be valid
- money operations cannot use negative amounts
- account balance cannot become negative
- transfers between different users apply commission
- the last user account cannot be closed
- closing an account transfers remaining funds to another account

## Technologies
- Java 25
- Spring Framework