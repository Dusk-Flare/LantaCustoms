package lanta.utils;

import lanta.math.Parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScannerHandler {
    private static Scanner scanner;
    public ScannerHandler(){
        scanner = new Scanner(System.in);
    }
    public ScannerHandler(Path source) throws IOException {
        scanner = new Scanner(source);
    }
    public ScannerHandler(Scanner scanner){
        ScannerHandler.scanner = scanner;
    }

    public String scan(){
        return scanner.nextLine();
    }

    public String scan(String display){
        System.out.println(display);
        return scan();
    }

    public <T> T scan(Function<String, T> converter){
        return converter.apply(scan());
    }

    public <T> T scan(String display, Function<String, T> converter){
        System.out.println(display);
        return scan(converter);
    }

    public void consume(Consumer<String> consumer){
        consumer.accept(scan());
    }

    public void consume(String display, Consumer<String> consumer){
        System.out.println(display);
        consume(consumer);
    }

    public <T> void consume(Consumer<T> consumer, Function<String, T> converter){
        consumer.accept(converter.apply(scan()));
    }

    public <T> void consume(String display, Consumer<T> consumer, Function<String, T> converter){
        System.out.println(display);
        consume(consumer, converter);
    }

    public <T extends Number> void consumeNumber(Consumer<T> consumer, Function<String, T> converter){
        consumer.accept(scanNumber(converter));
    }

    public <T extends Number> void consumeNumber(String display, Consumer<T> consumer, Function<String, T> converter){
        System.out.println(display);
        consumeNumber(consumer, converter);
    }

    public <T extends Number> T scanNumber(Function<String, T> converter){
        return Parser.toNumber(scan(), converter);
    }

    public <T extends Number> T scanNumber(String display, Function<String, T> converter){
        System.out.println(display);
        return scanNumber(converter);

    }

    public <T extends Number> T scanNumber(Function<String, T> converter, T defaultsTo){
        return Parser.toNumber(scan(), converter, defaultsTo);
    }

    public <T extends Number> T scanNumber(String display, Function<String, T> converter, T defaultsTo){
        System.out.println(display);
        return scanNumber(converter, defaultsTo);

    }
}
