import java.io.*;
import java.io.IOException;
import java.util.Scanner;

/**
 This creates the bank account abstract class that can be used to create additional accounts. It implements iBank.
 */

public abstract class BankAccount implements iBank {
    protected int userid;
    protected String userpassword;
    protected double balance;
    protected static double fee = 5.0;
    protected boolean deposited;
    protected boolean withdrawn;
    protected boolean transferred;
    protected double depositValue;
    protected double withdrawValue;
    protected double transferValue;

    /**
     This is a parameterized constructor to create a bank account.
     @param userid
     @param userpassword
     @param balance
     */

    public BankAccount(int userid, String userpassword, double balance) {
        this.userid = userid;
        this.userpassword = userpassword;
        this.balance = balance;
    }

    /**
     Returns the user's bank I.D.
     @return
     */

    public int getUserid() {
        return this.userid;
    }

    /**
     Allows the user to deposit from the bank account.
     @param amount
     @throws Exception
     */

    public void deposit(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Amount entered is either negative or 0");
        }
        this.balance += amount;
        this.depositValue += amount;
        this.deposited = true;

    }

    /**
     Allows the user to withdraw from the bank account.
     @param amount
     @throws Exception
     */

    public void withdraw(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Amount entered is either negative or 0");
        }
        if (this.balance < amount) {
            throw new Exception("Not enough funds to withdraw!");
        } else {

            this.balance -= amount;
            this.withdrawValue += amount;
            this.withdrawn = true;
        }
    }

     /**
     Allows the user to transfer money from one bank account to another.
     @param otherAccount
     @param amount
     @throws Exception
     */

    public void transfer(BankAccount otherAccount, double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Amount entered is either negative or 0");
        }
        try {
            if (this.balance < amount) throw new Exception("Not enough funds to withdraw!");
            this.balance -= amount;
            otherAccount.balance += amount;
            otherAccount.updatefilebalance();
            System.out.println("Transferred $" + amount + " from Account ID: " + this.userid + " to Account ID: " + otherAccount.getUserid());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     Prints out the receipt of all the transactions the user has done.
     */

    public void printReceipt() {
        if (this.deposited) {
            System.out.println("Deposited: $" + this.depositValue);
        }
        if (this.withdrawn) {
            System.out.println("Withdrawn: $" + this.withdrawValue);

        }

        System.out.println("Net amount: $" +  (this.depositValue-this.withdrawValue) );

        System.out.println("Current Balance: $" + balance);
    }

    /**
     Applies a $5.00 usage fee for each transaction done.
     */

    public void applyusagefee(){this.balance-=fee;}

    /**
     Updates the bankinfo.txt file. The balance for each account once all transactions are done will be updated.
     */

    public void updatefilebalance() {
        try (Scanner fileScanner = new Scanner(new File("bankinfo.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("temp_bankinfo.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                int userId = lineScanner.nextInt();
                String userPassword = lineScanner.next();
                double balance = lineScanner.nextDouble();
                char accountType = lineScanner.next().charAt(0);
                if (userId == this.userid) {
                    balance = this.balance;
                }
                writer.println(userId + " " + userPassword + " " + balance + " " + accountType);
                lineScanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File originalFile = new File("bankinfo.txt");
        File tempFile = new File("temp_bankinfo.txt");
        if (tempFile.exists() && tempFile.length() > 0) {
            if (originalFile.delete()) {
                tempFile.renameTo(originalFile);
            }
        }
    }
}