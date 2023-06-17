package bankaccountmanagementsystem;

import java.io.*;
import java.util.*;

// Bank class (Parent class)
class Bank implements Serializable {
    private String bankName;

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}

// Account class (Child class)
class Account extends Bank implements Serializable {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String bankName, String accountNumber, String accountHolderName, double balance) {
        super(bankName);
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Amount deposited successfully.");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Amount withdrawn successfully.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void displayDetails() {
        System.out.println("Bank Name: " + getBankName());
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}

// BankOperations class
class BankOperations {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static final String FILE_NAME = "accounts.ser";

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Bank Name: ");
        String bankName = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();

        Account account = new Account(bankName, accountNumber, accountHolderName, balance);
        accounts.add(account);

        System.out.println("Account created successfully.");
    }

    public static void depositAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.print("Enter Amount to Deposit: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void withdrawAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.print("Enter Amount to Withdraw: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void searchUserDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.displayDetails();
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            accounts.remove(account);
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public static void loadAccountsFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            accounts = (ArrayList<Account>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Accounts loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    public static void saveAccountsToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}


public class BankAccountManagementSystem {
    public static void main(String[] args) {
        BankOperations.loadAccountsFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n\n\nBank Management System");
            System.out.println("***************************");
            System.out.println("Press 1 to Create Account");
            System.out.println("Press 2 to Deposit Amount");
            System.out.println("Press 3 to Withdraw Amount");
            System.out.println("Press 4 to Search User Details by Account Number"); 
            System.out.println("Press 5 to Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    BankOperations.createAccount();
                    break;
                case 2:
                    BankOperations.depositAmount();
                    break;
                case 3:
                    BankOperations.withdrawAmount();
                    break;
                case 4:
                    BankOperations.searchUserDetails();
                    break; 
                case 5:
                    BankOperations.saveAccountsToFile();
                    System.out.println("Exiting Bank Management System... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }
}
