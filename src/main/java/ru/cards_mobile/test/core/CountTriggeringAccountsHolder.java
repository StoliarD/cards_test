package ru.cards_mobile.test.core;

import ru.cards_mobile.test.entities.Account;

public class CountTriggeringAccountsHolder extends AccountsHolder {

    private Counter counter;

    public CountTriggeringAccountsHolder(Counter counter) {
        this.counter = counter;
    }

    @Override
    public boolean put(Account account) {
        return countIfTrue(super.put(account));
    }

    @Override
    public boolean replaceBySnils(Account account) {
        return countIfTrue(super.replaceBySnils(account));
    }

    private boolean countIfTrue(boolean arg) {
        if (arg) counter.click(); //no matter in which order this is invoked
        return arg;

    }
}
