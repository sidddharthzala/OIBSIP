import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private int accountNumber;
    private int secretPIN;
    private double accountBalance;

    public BankAccount(int accountNumber, int secretPIN, double accountBalance) {
        this.accountNumber = accountNumber;
        this.secretPIN = secretPIN;
        this.accountBalance = accountBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getSecretPIN() {
        return secretPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void deposit(double amount) {
        accountBalance += amount;
    }

    public boolean withdraw(double amount) {
        if (accountBalance >= amount) {
            accountBalance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (accountBalance >= amount) {
            accountBalance -= amount;
            recipient.deposit(amount);
            return true;
        }
        return false;
    }
}

class TransactionRecord {
    private int accountNumber;
    private String transactionType;
    private double transactionAmount;

    public TransactionRecord(int accountNumber, String transactionType, double transactionAmount) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }
}

class BankingApp {
    private static ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    private static ArrayList<TransactionRecord> transactionHistory = new ArrayList<>();
    private static BankAccount currentAccount;

    public static void main(String[] args) {
        // Add sample bank accounts (You should implement a way to manage bank accounts)
        bankAccounts.add(new BankAccount(1001, 4321, 2000.0));
        bankAccounts.add(new BankAccount(1002, 5678, 3500.0));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Account Number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();

            // Authenticate user
            currentAccount = authenticateAccount(accountNumber, pin);

            if (currentAccount != null) {
                System.out.println("Authentication successful.");
                displayMainMenu(scanner);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }

    private static BankAccount authenticateAccount(int accountNumber, int pin) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber && account.getSecretPIN() == pin) {
                return account;
            }
        }
        return null; // Authentication failed
    }

    private static void displayMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    performWithdrawal(scanner);
                    break;
                case 3:
                    performDeposit(scanner);
                    break;
                case 4:
                    performTransfer(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the banking app. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (TransactionRecord transaction : transactionHistory) {
            System.out.println("Account Number: " + transaction.getAccountNumber() +
                    ", Type: " + transaction.getTransactionType() +
                    ", Amount: " + transaction.getTransactionAmount());
        }
    }

    private static void performWithdrawal(Scanner scanner) {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (currentAccount.withdraw(amount)) {
            transactionHistory.add(new TransactionRecord(currentAccount.getAccountNumber(), "Withdrawal", amount));
            System.out.println("Withdrawal successful. New balance: " + currentAccount.getAccountBalance());
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    private static void performDeposit(Scanner scanner) {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        currentAccount.deposit(amount);
        transactionHistory.add(new TransactionRecord(currentAccount.getAccountNumber(), "Deposit", amount));
        System.out.println("Deposit successful. New balance: " + currentAccount.getAccountBalance());
    }

    private static void performTransfer(Scanner scanner) {
        System.out.print("Enter recipient's Account Number: ");
        int recipientAccountNumber = scanner.nextInt();
        BankAccount recipient = findAccountByAccountNumber(recipientAccountNumber);

        if (recipient != null) {
            System.out.print("Enter transfer amount: ");
            double amount = scanner.nextDouble();

            if (currentAccount.transfer(recipient, amount)) {
                transactionHistory.add(new TransactionRecord(currentAccount.getAccountNumber(), "Transfer", amount));
                System.out.println("Transfer successful. New balance: " + currentAccount.getAccountBalance());
            } else {
                System.out.println("Insufficient funds for transfer.");
            }
        } else {
            System.out.println("Recipient account not found.");
        }
    }

    private static BankAccount findAccountByAccountNumber(int accountNumber) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null; // Account not found
    }
}
