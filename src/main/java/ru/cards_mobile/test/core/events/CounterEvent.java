package ru.cards_mobile.test.core.events;

import org.springframework.context.ApplicationEvent;

public class CounterEvent extends ApplicationEvent {

    public CounterEvent(Object source) {
        super(source);
    }

}
