package com.massivecraft.factions.listeners;

import java.util.*;
import java.util.function.*;
import org.bukkit.event.Event;

public class ClaimByPassHandler {

    private static final ClaimByPassHandler i = new ClaimByPassHandler();

    private final Map<Class<? extends Event>, EventFunction<?>> map = new HashMap<>();

    public static ClaimByPassHandler getInstance() {
        return i;
    }

    public <T extends Event> void add(Class<T> eventClass, Function<T, Boolean> function) {
        map.put(eventClass, new EventFunction<>(function));
    }

    public <T extends Event> boolean shouldByPassClaim(T event) {
        if (!map.containsKey(event.getClass())) return false;
        EventFunction<T> function = (EventFunction<T>) map.get(event.getClass());
        return function != null ? function.apply(event) : false;
    }

    private static class EventFunction<T extends Event> {
        private final Function<T, Boolean> function;

        public EventFunction(Function<T, Boolean> function) {
            this.function = function;
        }

        public Boolean apply(T event) {
            return function.apply(event);
        }
    }
}
