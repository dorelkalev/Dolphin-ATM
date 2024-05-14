/**
 This sets the foundation for the transactions that every bank account must have.
 */

public interface iBank {

    /**
     This allows the user to deposit money into the bank.
     @param amount
     @throws Exception
     */
    void deposit(double amount) throws Exception;

    /**
     This allows the user to withdraw money from the bank.
     @param amount
     @throws Exception
     */

    void withdraw (double amount) throws Exception;

    /**
     This allows money to be transferred from one account to another.
     @param otherAccount
     @param amount
     @throws Exception
     */

    void transfer(BankAccount otherAccount, double amount) throws Exception;
}