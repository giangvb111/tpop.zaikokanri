package com.tpop.zaikokanri.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements ApplicationListener<Event<?>> {

    @Override
    public void onApplicationEvent(Event<?> event) {

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
