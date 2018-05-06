package ru.cards_mobile.test.core;

import net.jcip.annotations.ThreadSafe;
import ru.cards_mobile.test.core.events.CounterEvent;
import ru.cards_mobile.test.core.events.EventListener;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public abstract class EventProducingCounter implements Counter {

    static final int THRESHOLD = 100;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public void click() {
        if (atomicInteger.get() >= THRESHOLD - 1) {//check is b4 incrementing
            synchronized (this) {
                int local = atomicInteger.get(); // get and set are redirected to volatile
                if (local >= THRESHOLD - 1) {
                    atomicInteger.set(local - THRESHOLD);
                    getEventListener().registerEvent(new CounterEvent(this));
                }
            }
        }
        atomicInteger.incrementAndGet();
    }

    int getCount() {
        return atomicInteger.get();
    }

    protected abstract EventListener getEventListener();

}
