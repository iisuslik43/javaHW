package ru.iisuslik.maybe;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Template class that realise standard Maybe idiom
 *
 * @param <T> template type of included value
 */
public class Maybe<T> {
    private T t;

    private Maybe(T value) {
        t = value;
    }

    /**
     * Function to create Maybe with value
     *
     * @param value value you want to store in Maybe
     * @return new Maybe<T> with t
     */
    public static <T> Maybe<T> just(@NotNull T value) {
        return new Maybe<>(value);
    }

    /**
     * Function to create Maybe without value
     *
     * @return Maybe<T> with value = null
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * Get stored value
     *
     * @return stored value
     * @throws EmptyMaybeException throws if value == null
     */
    public T get() throws EmptyMaybeException {
        if (t == null) {
            throw new EmptyMaybeException();
        }
        return t;
    }

    /**
     * Function to check if Maybe store anything
     *
     * @return false if value == null and true else
     */
    public boolean isPresent() {
        return t != null;
    }

    /**
     * applies some function to value in Maybe
     *
     * @param mapper Function that you want to apply to this Maybe
     * @param <U>    type of returned Maybe
     * @return just result of function or nothing, if Maybe was stored nothing
     */
    public <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (!isPresent()) {
            return Maybe.nothing();
        }
        return just(mapper.apply(t));
    }

    /**
     * Function that takes all lines from file, parse integers from them,
     * and if the line can be parsed, it writes square of this number to another file,
     * else it writes "null"
     *
     * @param inFile  file from where you want to have lines
     * @param outFile file in that you want to write result
     * @throws IOException if there are some problems with input file
     */
    public static void getIntFromFile(@NotNull String inFile, @NotNull String outFile) throws IOException {
        ArrayList<Maybe<Integer>> vector = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Maybe<Integer> m;
                try {
                    int next = Integer.parseInt(line);
                    m = Maybe.just(next);
                    m = m.map(x -> x * x);
                } catch (java.lang.NumberFormatException e) {
                    m = Maybe.nothing();
                }
                vector.add(m);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outFile), StandardCharsets.UTF_8))) {
            for (Maybe<Integer> m : vector) {
                try {
                    if (m.isPresent()) {
                        writer.write(m.get().toString());
                        writer.newLine();
                    } else {
                        writer.write("null");
                        writer.newLine();
                    }
                } catch (EmptyMaybeException ignored) {
                }
            }
        }
    }
}