package lanta.math;

@FunctionalInterface
    public interface Expression<T extends Number> {
        T eval(Number x);
    }
