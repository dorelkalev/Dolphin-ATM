import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * This program simulates the interaction between a user and the ATM.
 */

public class ATM {

    /**
     This is the driver code for the ATM class.
     @param args
     @throws IOException
     */

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Dolphin ATM");
        Queue<BankAccount> userQueue = new LinkedList<>();
        try (FileInputStream myfile = new FileInputStream("bankinfo.txt");
             Scanner infs = new Scanner(myfile)) {
            while (infs.hasNextLine()) {
                String line = infs.nextLine();
                Scanner lineScanner = new Scanner(line);
                int userId = lineScanner.nextInt();
                String userPassword = lineScanner.next();
                double balance = lineScanner.nextDouble();
                char accountType = lineScanner.next().charAt(0);
                BankAccount account;
                switch (accountType) {
                    case 'C':
                        account = new CheckingAccount(userId, userPassword, balance);
                        break;
                    case 'S':
                        account = new SavingsAccount(userId, userPassword, balance);
                        break;
                    case 'A':
                        account = new AcademicAccount(userId, userPassword, balance);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid account type: " + accountType);
                }
                userQueue.offer(account);
                lineScanner.close();
            }
        }

        Scanner User = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Please enter your user ID and password to login");
            System.out.println("Dolphin ATM charges a 5$ convenience fee");
            System.out.print("User ID: ");
            int inputUserId = User.nextInt();
            System.out.print("Password: ");
            String inputPassword = User.next();

            try {
                for (BankAccount currentAccount : userQueue) {
                    if (currentAccount.userid == inputUserId && currentAccount.userpassword.equals(inputPassword)) {
                        loggedIn = true;
                        System.out.println("Login successful!");
                        System.out.println("Userid: " + currentAccount.userid + " Balance: $" + currentAccount.balance + " Account Type: " + currentAccount.getClass().getName());
                        System.out.println("What would you like to do?");
                        System.out.println("List of actions:");
                        System.out.println("W - Withdraw");
                        System.out.println("D - Deposit");
                        System.out.println("T - Transfer");
                        boolean signout = false;
                        while (!signout) {
                            char action = User.next().toUpperCase().charAt(0);
                            switch (action) {
                                case 'W':
                                    System.out.println("How much money would you like to withdraw?");
                                    double intake = User.nextDouble();
                                    try {
                                        currentAccount.withdraw(intake);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    currentAccount.updatefilebalance();
                                    break;
                                case 'D':
                                    System.out.println("How much money would you like to deposit?");
                                    double intake2 = User.nextDouble();
                                    try {
                                        currentAccount.deposit(intake2);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    currentAccount.updatefilebalance();
                                    break;
                                case 'T':
                                    System.out.println("How much money would you like to transfer?");
                                    double intake3 = User.nextDouble();
                                    System.out.println("What is the account ID of the account you would like to transfer to?");
                                    int otheraccountid = User.nextInt();
                                    BankAccount otheraccount = null;
                                    for (BankAccount otherac : userQueue) {
                                        if (otherac.getUserid() == otheraccountid) {
                                            otheraccount = otherac;
                                            break;
                                        }
                                    }
                                    if (otheraccount != null) {
                                        try {
                                            currentAccount.transfer(otheraccount, intake3);
                                            currentAccount.updatefilebalance();
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } else {
                                        System.out.println("Account not found!");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid action. Please try again.");
                                    break;
                            }
                            System.out.println("Do you want to perform another transaction? (Y/N)");
                            char anotherTransaction;
                            anotherTransaction = User.next().toUpperCase().charAt(0);
                            if (anotherTransaction == 'Y') {
                                System.out.println("What would you like to do?");
                                System.out.println("List of actions:");
                                System.out.println("W - Withdraw");
                                System.out.println("D - Deposit");
                                System.out.println("T - Transfer");
                            } else if (anotherTransaction == 'N') {
                                currentAccount.applyusagefee();
                                currentAccount.updatefilebalance();
                                currentAccount.printReceipt();
                                signout = true;
                                System.out.println("Convenience fee has been charged");
                                System.out.println("Signed out successfully.");
                            }
                        }
                    }
                }
                        if (!loggedIn) {
                            throw new IllegalArgumentException("Incorrect user ID or password. Please try again.");
                        }
                    } catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                User.close();
            }
        }
