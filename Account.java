package BankTest;

/**
 * Created by MaheshKayara on 6/30/2017.
 */
class Account{
    private String mAccountHolderName;
    private float mBalance;
    Account(String accountHolderName){
        mAccountHolderName = accountHolderName;
        mBalance = 0;
    }

	//deposit amount
    public void deposit(float amount){
        mBalance += amount;
    }

	//withdraw amount
    public boolean withdraw(float amount){
        if(amount>mBalance)
            return false;
        else{
            mBalance -= amount;
            return true;
        }
    }

	//fetch account holder name for the account
    public String getName(){
        return mAccountHolderName;
    }

	//fetch balance for the account
    public float getBalance(){
        return mBalance;
    }
}