package BankTest;

import javax.xml.soap.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by MaheshKayara on 6/30/2017.
 */
public class Bank {

    static class Node {
        Node mLeft, mRight;
        Account mAccount;

        public Node(Account account) {
            mAccount = account;
        }
    }

    private Node mAccountTree;
    
	//Bank Name
    private String mBankName;
	//List of banks.
    private static ArrayList<Bank> bankList = new ArrayList<>();

    private Bank(String bankName) {
        mBankName = bankName;
        mAccountTree = null;
        //mAccountList = new ArrayList<>();
    }

	//Add an acount to bank
    private Account addAccount(String accountHolderName) {
        Account newAccount = new Account(accountHolderName);
        Node node = new Node(newAccount);

        if (mAccountTree == null) {
            mAccountTree = node;
            return mAccountTree.mAccount;
        }
        addAccountRecursive(mAccountTree, node);
        return newAccount;
    }


    private void addAccountRecursive(Node node, Node newAccount) {
        int compareValue = (newAccount.mAccount.getName()).compareTo(node.mAccount.getName());
        if (compareValue < 0) {
            if (node.mLeft == null) {
                node.mLeft = newAccount;
                return;
            }
            addAccountRecursive(node.mLeft, newAccount);
        } else if (compareValue > 0) {
            if (node.mRight == null) {
                node.mRight = newAccount;
                return;
            }
            addAccountRecursive(node.mRight, newAccount);
        }
    }

	//fetch account
    private Account getAccount(String accountHolderName) {
        return getAccountRecursive(mAccountTree, accountHolderName);
    }

    private Account getAccountRecursive(Node node, String accountHolderName) {
        if (node == null)
            return null;
        int compareValue = (node.mAccount.getName()).compareTo(accountHolderName);
        if (compareValue == 0)
            return node.mAccount;
        else if (compareValue < 0)
            return getAccountRecursive(node.mLeft, accountHolderName);
        else
            return getAccountRecursive(node.mRight, accountHolderName);

    }

	//create bank if not present
    private static Bank createBank(String bankName) {
        Bank bank = new Bank(bankName);
        bankList.add(bank);
        return bank;
    }

	//fetch bank
    private static Bank getBank(String bankName) {
        Bank bank;
        for (int i = 0, numBank = bankList.size(); i < numBank; i++) {
            bank = bankList.get(i);
            if (bank.mBankName.equals(bankName)) {
                return bank;
            }
        }
        return null;
    }

	//display the final bank balance 
    private static void displayFinalBalance() {

        for (Bank bank: bankList) {
            String name = bank.mBankName;
            System.out.println("\nBank Name : " + name + "\nAcName\tBalance");
            printAccountDetailsInorder(bank.mAccountTree);
        }
    }

    private static void printAccountDetailsInorder(Node root) {
        if (root == null) {
            return;
        }
        printAccountDetailsInorder(root.mLeft);
        System.out.println(root.mAccount.getName() + "\t" + root.mAccount.getBalance());
        printAccountDetailsInorder(root.mRight);
    }

	//deposit amount 
    public boolean depositAmount(String accountHolderName, float amount) {
        Account account = getAccount(accountHolderName);
        if (account == null) {
            account = addAccount(accountHolderName);
            account.deposit(amount);
            return true;
        }
        account.deposit(amount);
        return true;
    }

	//withdraw amount
    public boolean withdrawAmount(String accountHolderName, float amount) {
        Account account = getAccount(accountHolderName);
        if (account == null)
            return false;
        account.withdraw(amount);
        return true;
    }

    public static void main(String[] args) {
        try {
			//local file path that contains account actions.
            String fileName = "G:\\Test\\log.txt";
            BufferedReader b = new BufferedReader(new FileReader(fileName));
            String logDataLineByLine;
            while ((logDataLineByLine = b.readLine()) != null) {
                String[] logContent = logDataLineByLine.split(",");
                String bName = logContent[0];
                String aHName = logContent[1];
                String aAction = logContent[2];
                float amount = Float.parseFloat(logContent[3]);
                Bank bank = getBank(bName);
                if (bank == null)
                    bank = createBank(bName);
                if (aAction.equals("Deposit"))
                    bank.depositAmount(aHName, amount);
                else if (aAction.equals("Withdraw"))
                    bank.withdrawAmount(aHName, amount);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        displayFinalBalance();
    }
}