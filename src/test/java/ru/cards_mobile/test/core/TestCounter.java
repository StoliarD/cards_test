package ru.cards_mobile.test.core;

import org.junit.Assert;
import org.junit.Test;
import ru.cards_mobile.test.core.events.EventListener;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCounter {

    private final int N = new Random().nextInt(1000);

    @Test
    public void test() throws InterruptedException {
        AtomicInteger eventsOccurred = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        EventProducingCounter counter = new EventProducingCounter() {
            @Override
            protected EventListener getEventListener() {
                return event -> eventsOccurred.incrementAndGet();
            }
        };
        System.out.println(N);
        CountDownLatch start = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            executorService.execute(
                    () -> {
                        start.countDown();
                        counter.click();
                    }
            );
        }
        start.await();
        executorService.shutdown();
        boolean b = executorService.awaitTermination(5, TimeUnit.SECONDS);
        Assert.assertTrue(b);
        System.out.println(eventsOccurred.get());
        System.out.println(counter.getCount());
        Assert.assertEquals(N / EventProducingCounter.THRESHOLD, eventsOccurred.get());
        Assert.assertEquals(N % EventProducingCounter.THRESHOLD, counter.getCount());
        executorService.shutdown();
    }

}
