package ru.cards_mobile.test.core;

import org.junit.Test;
import ru.cards_mobile.test.core.events.CounterEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class MagicTest {

    @Test
    public void test() {
        AtomicInteger count = new AtomicInteger();
        MagicService service = new MagicService() {
            @Override
            void magicMethod() throws Throwable {
                count.incrementAndGet();
                Thread.sleep(1000);

            }
        };

        service.onApplicationEvent(new CounterEvent(this));


    }



}
