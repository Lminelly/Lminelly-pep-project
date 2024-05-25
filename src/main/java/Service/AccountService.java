package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public boolean deleteAccount(int accountId) {
        return accountDAO.deleteAccount(accountId);
    }
}
