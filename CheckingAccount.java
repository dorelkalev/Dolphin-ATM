import java.util.Scanner;

/**
 This creates the checking account class, which is a bank account.
 */

public class CheckingAccount extends BankAccount {
    private double overdraftfee = 20.00;

    /**
     This is a parameterized constructor to create a checking account object.
     @param userid
     @param userpassword
     @param balance
     */

    public CheckingAccount(int userid, String userpassword, double balance) { super(userid, userpassword, balance); }

    /**
     This overrides BankAccount's withdraw method to allow the user to enable a $20.00 overdraft fee.
     @param amount
     @throws Exception
     */

    @Override
    public void withdraw(double amount) throws Exception {
        boolean overdraftfeeProtection = false;
        Scanner User = new Scanner(System.in);
        System.out.println("Enable overdraft protection for this specific transaction?\nY - Yes\nN - No");
        char choice = User.next().toUpperCase().charAt(0);
        if (choice == 'Y') {
            overdraftfeeProtection = true;
            System.out.println("Overdraft fee protection has been enabled!");
        } else if (choice == 'N') {
            overdraftfeeProtection = false;
            System.out.println("Overdraft fee protection has been disabled!");
        } else {
            System.out.println("Invalid input! Overdraft protection is disabled by default");
        }

        if (amount <= 0) {
            throw new Exception("Amount entered is either negative or 0");
        }
        if ((this.balance < amount) && overdraftfeeProtection) {
            throw new Exception("Trying to withdraw more than balance! Overdraft fee protection is enabled. No money withdrawn");
        } else if (this.balance < amount) {
            this.balance -= amount + overdraftfee;
            this.withdrawValue += amount;
            this.withdrawn = true;
            throw new Exception("Trying to withdraw more than balance! Overdraft fee protection is disabled. Charging overdraft fee: $" + overdraftfee);
        } else {
            this.balance -= amount;
            this.withdrawValue += amount;
            this.withdrawn = true;
        }
    }
}