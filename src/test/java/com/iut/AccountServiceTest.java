package com.iut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.iut.account.model.Account;
import com.iut.account.service.AccountService;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceTest {

    private Repository<Account, String> repository;
    
    private AccountService accountService;

    @BeforeEach
    void setup() {
        repository = new RepositoryInterface();
        accountService = new AccountService(repository);
    }

    @Test
    void createAccountTest() {
        boolean created = accountService.createAccount("1234", 100, "1");
        Assertions.assertTrue(created);
        Assertions.assertTrue(repository.existsById("1"));
    }

    @Test
    void depositTest() {
        accountService.createAccount("1234", 100, "1");
        boolean result = accountService.deposit("1234", 50);
        Assertions.assertTrue(result);
        Assertions.assertEquals(150, accountService.getBalance("1234"));
    }

    @Test
    void withdrawTest() {
        accountService.createAccount("1234", 100, "1");
        boolean result = accountService.withdraw("1234", 70);
        Assertions.assertTrue(result);
        Assertions.assertEquals(30, accountService.getBalance("1234"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.withdraw("1234", -30));
    }

    @Test
    void transferTest() {
        accountService.createAccount("1234", 100, "1");
        accountService.createAccount("5678", 50, "2");
        boolean result= accountService.transfer("1234", "5678", 40);
        Assertions.assertTrue(result);
        Assertions.assertEquals(60, accountService.getBalance("1234"));
        Assertions.assertEquals(90, accountService.getBalance("5678"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.transfer("1234", "5678", -50));
    }

    @Test
    void getBalanceTest() {
        accountService.createAccount("1234", 200, "1");
        int balance = accountService.getBalance("1234");
        Assertions.assertEquals(200, balance);

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.getBalance(""));
    }

    @Test
    void existsAndGetAccountTest() {
        accountService.createAccount("1234", 300, "1");
        Assertions.assertTrue(repository.existsById("1234"));
        Account account = accountService.getAccount("1234");
        Assertions.assertNotNull(account);
        Assertions.assertEquals("1234", account.getId());
        Assertions.assertEquals(300, account.getBalance());
    }

    @Test
    void getAllAccountsTest() {
        accountService.createAccount("1234", 100, "1");
        accountService.createAccount("5678", 200, "2");

        List<Account> allAccounts = accountService.getAllAccounts();

        Assertions.assertEquals(2, allAccounts.size());

        Assertions.assertTrue(allAccounts.stream().anyMatch(account -> account.getId().equals("1234")));
        Assertions.assertTrue(allAccounts.stream().anyMatch(account -> account.getId().equals("5678")));
    }

}
