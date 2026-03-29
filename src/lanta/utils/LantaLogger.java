package lanta.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LantaLogger {
    private int logID;
    private int errors;
    private final String loggerID;
    private final boolean isDiscrete;
    private final ArrayList<String> fullLog;

    public LantaLogger(Class<?> owner, boolean isDiscrete) {
        loggerID = owner.getSimpleName();
        logID = 0;
        errors = 0;
        fullLog = new ArrayList<>();
        this.isDiscrete = isDiscrete;
    }

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
