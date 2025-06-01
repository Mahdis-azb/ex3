package com.iut;

import com.iut.account.service.AccountService;
import com.iut.user.service.UserService;
import com.iut.user.model.User;
import com.iut.account.model.Account;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private final UserService userService;
    private final AccountService accountService;

    public BankService(final UserService userService, final AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public boolean registerNewUser(User user) {
        boolean created = userService.createUser(user);
        if (created) {
            // Add default account with zero balance
            String defaultAccountId = user.getId() + "_account1";
            return accountService.createAccount(defaultAccountId, 0, user.getId());
        }
        return false;
    }

    public User getUser(String userId) {
        return userService.getUser(userId);
    }

    public boolean addAccountToUser(String userId, Account account) {
        return accountService.createAccount(account.getId(), account.getBalance(), userId);
    }

    public List<Account> getUserAccounts(String userId) {
        List<Account> allAccounts = accountService.getAccountsByUserId(userId);
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : allAccounts) {
            if (userId.equals(account.getUserId())) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    public Account getAccount(String accountId) {
        return accountService.getAccount(accountId);
    }

    public boolean deleteAccount(String accountId) {
        return accountService.deleteAccount(accountId);
    }



}
