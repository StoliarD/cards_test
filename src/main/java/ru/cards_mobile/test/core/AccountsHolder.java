package ru.cards_mobile.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cards_mobile.test.entities.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * in-memory implementation
 *
 * {@inheritDoc}
 */
public class AccountsHolder implements AccountsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ConcurrentMap<String, Account> data = new ConcurrentHashMap<>();

    public boolean put(Account account) {
        String snils = account.getSnils();
        Account persisted = data.putIfAbsent(snils, account); //atomic
        boolean res = persisted == null; //key wasn't mapped
        log.info("account put : " + res + "  " + account);
        return res;
    }

    public boolean replaceBySnils(Account account) {
        String snils = account.getSnils();
        Account replace = data.replace(snils, account); //atomic
        boolean res =  replace != null && !account.equals(replace); //key was mapped with different account
        log.info("account replaced : " + res + "  " + account);
        return res;
    }

    public Account find(String snils) {
        Account res = data.get(snils);
        log.info("account fount : " + res + " by snils " + snils);
        return res;
    }

}
