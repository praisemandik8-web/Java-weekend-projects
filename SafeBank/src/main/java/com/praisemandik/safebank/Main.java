
package com.praisemandik.safebank;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DecimalFormat moneyFormat =
            new DecimalFormat("#,##0.00");

    private static final DateTimeFormatter dateFormat =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       WELCOME TO SAFEBANK");
        System.out.println("====================================");

        BankAccount account = createAccount();

        boolean running = true;

        while (running) {

            displayMenu();

            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    depositMoney(account);
                    break;

                case "2":
                    withdrawMoney(account);
                    break;

                case "3":
                    displayBalance(account);
                    break;

                case "4":
                    displayTransactionHistory(account);
                    break;

                case "5":
                    running = false;
                    System.out.println("Thank you for banking with SafeBank!");
                    break;

                default:
                    System.out.println("Invalid selection. Please choose 1-5.");
            }
        }

        scanner.close();
    }


    private static BankAccount createAccount() {

        while (true) {

            try {
                System.out.print("Enter account holder's name: ");
                String name = scanner.nextLine();

                System.out.print("Enter opening balance: ₦");
                BigDecimal openingBalance =
                        new BigDecimal(scanner.nextLine().trim());

                BankAccount account =
                        new BankAccount(name, openingBalance);

                System.out.println("\nAccount created successfully!");
                System.out.println("Welcome, " + account.getAccountHolder());

                return account;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");

            } catch (IllegalArgumentException e) {
                System.out.println("Account creation failed: " + e.getMessage() + "\n");
            }
        }
    }


    private static void displayMenu() {

        System.out.println("\n====================================");
        System.out.println("             SAFEBANK MENU");
        System.out.println("====================================");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
        System.out.println("====================================");
    }


    private static void depositMoney(BankAccount account) {

        try {
            System.out.print("Enter deposit amount: ₦");

            BigDecimal amount =
                    new BigDecimal(scanner.nextLine().trim());

            account.deposit(amount);

            System.out.println("\nDeposit successful!");
            System.out.println("New balance: ₦" +
                    moneyFormat.format(account.getBalance()));

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric amount.");

        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }


    private static void withdrawMoney(BankAccount account) {

        try {
            System.out.print("Enter withdrawal amount: ₦");

            BigDecimal amount =
                    new BigDecimal(scanner.nextLine().trim());

            account.withdraw(amount);

            System.out.println("\nWithdrawal successful!");
            System.out.println("New balance: ₦" +
                    moneyFormat.format(account.getBalance()));

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric amount.");

        } catch (InvalidAmountException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());

        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());

        } catch (WithdrawalLimitExceededException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }


    private static void displayBalance(BankAccount account) {

        System.out.println("\n========== ACCOUNT DETAILS ==========");
        System.out.println("Account holder: " + account.getAccountHolder());
        System.out.println("Current balance: ₦" +
                moneyFormat.format(account.getBalance()));
        System.out.println("=====================================");
    }


    private static void displayTransactionHistory(BankAccount account) {

        List<Transaction> transactions = account.getTransactions();

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {
            System.out.println("No successful transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {

            System.out.println("Type: " +
                    transaction.getTransactionType());

            System.out.println("Amount: ₦" +
                    moneyFormat.format(transaction.getAmount()));

            System.out.println("Date: " +
                    transaction.getTransactionDate().format(dateFormat));

            System.out.println("-----------------------------------------");
        }
    }
}
