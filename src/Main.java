import lanta.dataTypes.Stack;
import lanta.utils.MenuConstructor;
import lanta.utils.ScannerHandler;

public class Main {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        MenuConstructor menu = new MenuConstructor("Exit", "Add");
        ScannerHandler scannerHandler = new ScannerHandler();
        StringBuilder builder = new StringBuilder();
        boolean repeat = true;
        while(repeat){
            switch (menu.getOption(ScannerHandler.getScanner())) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    String character = scannerHandler.scan((data) -> String.valueOf(data.charAt(0)));
                    if("([{".contains(character)) {
                        stack.push(character);
                        builder.append(character);
                    }
                    else if (")]}".contains(character) && stack.peek() != null && stack.peek().equals(character.equals(")")?"(":character.equals("]")?"[":"{")) {
                        builder.append(character);
                    } else System.out.println("Invalid sequence!\n"+ (stack.peek() != null?"Try closing "+stack.peek():"Try opening something"));
                    break;
            }
        }
        while (!stack.isEmpty()) {
            String character = stack.pop();
            builder.append(character.equals("(") ? ")" : character.equals("[") ? "]" : "}");
        }
        System.out.println(builder);
    }
}