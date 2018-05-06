package ru.cards_mobile.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.cards_mobile.test.core.events.CounterEvent;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * здесь находится вызов ресурсоемкого метода, запрос на который поступает раз в 100 изменений.
 *
 * через вызов {@link MagicService#magicMethod()} легко повесить сервер.
 * тут проблема решена так : в каджый момент времени происходит не более одного исполнения чудо-метода.
 * если за время исполнения чудо-метода запрос пришел еще раз, то он будет исполнен, но только после завершения текущего.
 * если за время исполнения чудо-метода пришло более одного запроса, то скорее всего нет смысла исполнять их все.
 * будет исполнен только один.
 *
 * также можно реализовать с помощью таймстэмпов создания евента и начала обработки.
 *
 * более сложную логику можно реализовать например с помощью очередей задач, очищая их, когда потребуется.
 */
@Component
public class MagicService implements ApplicationListener<CounterEvent> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private volatile boolean perform = false;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        executorService.scheduleWithFixedDelay(
                () -> {
                    try {
                        if (perform) {
                            perform = false;
                            magicMethod();
                        }
                    } catch (Throwable e) {
                        log.error("error encountered while invoking magic", e);
                        // do something
                        log.info("magic will restart in a second...");
                        perform = true;
                    }
                }, 0, 1, TimeUnit.SECONDS);
    }

    void magicMethod() throws Throwable {
        log.info("magic.....");
        Thread.sleep(60000);
        log.info("magic stopped.");
    }

    @Override
    public void onApplicationEvent(CounterEvent event) {
        perform = true;
    }

}
