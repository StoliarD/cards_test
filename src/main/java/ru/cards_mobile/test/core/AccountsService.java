package ru.cards_mobile.test.core;

import ru.cards_mobile.test.entities.Account;

public interface AccountsService {

    /**
     * @param account obj
     * @return true if and only if there was no acc present with such snils
     */
    boolean put(Account account);

    /**
     * @param account obj
     * @return true if and only if account there was an acc with such snils and it was replaced
     */
    boolean replaceBySnils(Account account);

    /**
     * @return acc or null
     */
    Account find(String snils);

}
