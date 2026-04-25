package lanta.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * The LantaLogger class is a simple logger that registers messages and errors into the console, and keeps track of them.
 */
public class LantaLogger {
    private int logID;
    private int errors;
    private final String loggerID;
    private final boolean isDiscrete;
    private final ArrayList<String> fullLog;

     /**
      * Constructs a logger that contains the id owner and may log onto the console if isDiscrete is false
      *
     * @param owner This parameter represents the specific class this logger is supposed to log under.
     * @param isDiscrete This boolean dictates weather or not the logger will immediately print out the logged message upon call.
     */
    public LantaLogger(Class<?> owner, boolean isDiscrete) {
        loggerID = owner.getSimpleName();
        logID = 0;
        errors = 0;
        fullLog = new ArrayList<>();
        this.isDiscrete = isDiscrete;
    }

    /**
     * Constructs a logger that contains the id owner and logs directly onto the console
     *
     * @param owner This parameter represents the specific class this logger is supposed to log under.
     */
    public LantaLogger(Class<?> owner) {
        this(owner, false);
    }

    public int totalErrors(){
        return errors;
    }

    public <T extends Exception> void logExcept(T exception) throws T{
        logCatch(exception);
        throw exception;
    }

    public <T extends Exception> void logCatch(T exception){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        log(sw.toString());
        errors++;
    }

    public void log(Object message) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        StringBuilder log = new StringBuilder();
        String heading = "["+logID+"|"+now+"|"+loggerID+"]: ";
        String indent = "\n" + " ".repeat(heading.length());
        String[] messageContent = message.toString().split("\n");
        log.append(heading).append(messageContent[0]);
        for (int i = 1; i < messageContent.length; i++) log.append(indent).append(messageContent[i]);
        fullLog.add(log.toString());
        if(!isDiscrete) System.out.println(log);
        logID++;
    }

    public void displayLogs(){
        for(String log : fullLog){
            System.out.println(log);
        }
    }
}
