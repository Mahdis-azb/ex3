package com.iut;

import com.iut.account.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryInterface implements Repository<Account, String> {
    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public boolean save(Account input) {
        if (accounts.containsKey(input.getId())) {
            return false; // Account already exists
        }
        accounts.put(input.getId(), input);
        return true;
    }

    @Override
    public boolean update(Account input) {
        if (!accounts.containsKey(input.getId())) {
            return false; // Account does not exist
        }
        accounts.put(input.getId(), input);
        return true;
    }

    @Override
    public boolean delete(String id) {
        return accounts.remove(id) != null;
    }

    @Override
    public boolean existsById(String id) {
        return accounts.containsKey(id);
    }

    @Override
    public Account findById(String id) {
        return accounts.get(id);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

}
