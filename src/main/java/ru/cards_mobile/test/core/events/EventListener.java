package ru.cards_mobile.test.core.events;

import org.springframework.context.ApplicationEvent;

public interface EventListener {

    void registerEvent(ApplicationEvent event);

}
