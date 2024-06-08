package com.reto.interbank.services.filter;

import org.slf4j.MDC;
import reactor.core.publisher.Signal;
import reactor.util.context.ContextView;

import java.util.function.Consumer;

public class MdcContextLifter implements Consumer<Signal<?>> {

    private final String key;

    public MdcContextLifter(String key) {
        this.key = key;
    }
    @Override
    public void accept(Signal<?> signal) {
        ContextView contextView = signal.getContextView();
        if (contextView.hasKey(key)) {
            MDC.put(key, contextView.get(key));
        } else {
            MDC.remove(key);
        }
    }

}
