package com.melapelapp.commons.lang;

/**
 * Created by mcamacho on 4/4/16.
 */
public class Optional<T> {

    private final T data;

    protected Optional(T data) {
        this.data = data;
    }

    public static <U> Optional<U> empty() {
        return new Optional(null);
    }

    public static <U> Optional<U> of(U data) {
        if (data == null) {
            throw new NullPointerException();
        }

        return new Optional<U>(data);
    }

    public static <U> Optional<U> ofNullable(U data) {
        if (data == null) {
            return new Optional(null);
        } else {
            return of(data);
        }
    }

    public boolean isPresent() {
        return data != null;
    }

    public void ifPresent(Action action) {
        if (isPresent()) {
            action.doAction();
        }
    }

    public T get() {
        return data;
    }

    public T orElse(T other) {
        return data == null ? other : data;
    }

}

interface Action {
    public void doAction();
}