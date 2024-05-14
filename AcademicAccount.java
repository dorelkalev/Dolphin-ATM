/**
 This creates the academic account class, which is a bank account.
 */

public class AcademicAccount extends BankAccount {

    /**
     This is a parameterized constructor to create an academic account.
     @param userid
     @param userpassword
     @param balance
     */

    public AcademicAccount(int userid, String userpassword, double balance) {

        super(userid, userpassword, balance);

    }

    /**
     This disables the usage fee for student accounts.
     */

    @Override
    public void applyusagefee() {
        this.balance -= 0;
    }

    /**
     This updates printReceipt to let the student know that his or her account has a low balance.
     */

    @Override
    public void printReceipt() {

       super.printReceipt();

        if (this.balance < 100.00 ){
            System.out.println("Your account has a low balance of " + this.balance);
        }

    }

}