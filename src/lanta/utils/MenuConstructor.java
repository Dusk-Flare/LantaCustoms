package lanta.utils;

import lanta.math.Parser;

import java.util.HashMap;
import java.util.Scanner;

public class MenuConstructor {
    private final HashMap<Integer, String> options;
    private int option;
    public MenuConstructor(String defaultOption,String... options) {
        this.options = new HashMap<>();
        this.options.put(0, defaultOption);
        this.option = 1;
        for (String option : options) {
            this.options.put(this.option, option);
            this.option++;
        }
    }
    public MenuConstructor(HashMap<Integer, String> options) {
        this.options = options;
    }

    public int getOption(Scanner scanner) {
        StringBuilder message = new StringBuilder();
        message.append(0 + " - ").append(this.options.get(0));
        for(int i = 1; i < this.options.size(); i++){
            message.append(" | ").append(i).append(" - ").append(this.options.get(i));
        }
        System.out.println(message);
        System.out.println("Select an option:");
        int selectedOption = Parser.toNumber(scanner.nextLine(), Integer::parseInt, 0);
        if(options.containsKey(selectedOption)) return selectedOption;
        else {
            System.out.println("Invalid option, defaulting to 0");
            return 0;
        }
    }
}
