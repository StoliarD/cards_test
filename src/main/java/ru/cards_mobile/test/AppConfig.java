package ru.cards_mobile.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import ru.cards_mobile.test.core.AccountsService;
import ru.cards_mobile.test.core.CountTriggeringAccountsHolder;
import ru.cards_mobile.test.core.Counter;
import ru.cards_mobile.test.core.EventProducingCounter;
import ru.cards_mobile.test.core.events.EventListener;

@SpringBootApplication
public class AppConfig {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Bean
    public Counter counter() {
        return new EventProducingCounter() {
            @Override
            protected EventListener getEventListener() {
                return eventPublisher::publishEvent;
            }
        };
    }

    @Bean
    public AccountsService accountsService() {
        //counter() is delegated to BeanFactory via cglib so Counter is fully initialized by Spring
        return new CountTriggeringAccountsHolder(counter());
    }

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
