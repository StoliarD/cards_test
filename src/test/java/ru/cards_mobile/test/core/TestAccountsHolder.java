package ru.cards_mobile.test.core;

import org.junit.Assert;
import org.junit.Test;
import ru.cards_mobile.test.entities.Account;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.cards_mobile.test.TestUtil.sneaky;
import static ru.cards_mobile.test.TestUtil.viaReflection;

/**
 * из нескольких потоков делаем запросы на добавление и изменение в "базе"
 * считаем количество изменений в базе
 * конечное состояние "базы" также детерминированно и проверяется.
 */
public class TestAccountsHolder {

    @Test
    public void test() throws InterruptedException {
        int n = 8;
        AccountsHolder service = new AccountsHolder();
        AtomicInteger putted = new AtomicInteger();
        AtomicInteger updated = new AtomicInteger();
        Account account = new Account("test", "test", new Date());
        Account accountNew = new Account("test", "testNew", new Date());
        Account account2 = new Account("test2", "test2", new Date());
        Account account2New = new Account("test2", "test2New", new Date());
        CountDownLatch before = new CountDownLatch(n);
        CountDownLatch after = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            new Thread(()->{ //running threads are immune to gc
                before.countDown();
                sneaky(before::await); // all threads are fired at the same time
                incrementIfTrue(service.put(account), putted);
                incrementIfTrue(service.put(account2), putted);
                incrementIfTrue(service.replaceBySnils(accountNew), updated);
                incrementIfTrue(service.replaceBySnils(account2New), updated);
                sneaky(after::countDown);
            }).start();
        }
        after.await(); // all jobs are done
        Assert.assertEquals(2, putted.get());
        Assert.assertEquals(2, updated.get());
        Map data = viaReflection(service, "data");
        Assert.assertEquals(2, data.size());
        Assert.assertEquals(accountNew, service.find(accountNew.getSnils()));
        Assert.assertEquals(account2New, service.find(account2New.getSnils()));
    }

    private void incrementIfTrue(boolean check, AtomicInteger value) {
        if (check) value.incrementAndGet();
    }

}
