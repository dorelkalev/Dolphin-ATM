
/**
 This creates the savings account class, which is a bank account.
 */

public class SavingsAccount extends BankAccount {
    private static final double interestRate = 0.005;

    /**
     This is a parameterized constructor to create a savings account object.
     @param userid
     @param userpassword
     @param balance
     */

    public SavingsAccount(int userid, String userpassword, double balance) {super(userid, userpassword, balance);}

    /**
     This overrides BankAccount's deposit method so that it adds a 0.005% interest to the
     deposit if the user deposits at least $10,000.
     @param amount
     */

    @Override
    public void deposit(double amount) {
        try{super.deposit(amount);}
        catch(Exception e){System.out.println(e.getMessage());}
        if (amount >= 10000) {
            double bonus = amount * interestRate;
            this.balance += bonus;
            System.out.println("You received a bonus of: $" + bonus);
        }
    }
}