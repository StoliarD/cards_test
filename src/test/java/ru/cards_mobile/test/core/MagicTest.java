package ru.cards_mobile.test.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.cards_mobile.test.core.events.CounterEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class MagicTest {

    private AtomicInteger integer = new AtomicInteger();
    private MagicService service;

    @Before
    public void before() {
        service = new MagicService() {
            @Override
            void magicMethod() throws Throwable { // when invoked fist time - imitates events spam while job is running
                integer.incrementAndGet();
                if (integer.get() == 1) {
                    service.onApplicationEvent(new CounterEvent(this));
                    service.onApplicationEvent(new CounterEvent(this));
                    service.onApplicationEvent(new CounterEvent(this));
                }
                Thread.sleep(100);
            }
        };
        service.init();
    }

    @Test
    public void test() throws InterruptedException {
        service.onApplicationEvent(new CounterEvent(this));
        Thread.sleep(3000); //because of executor delay
        Assert.assertEquals(2, integer.get());
    }



}
