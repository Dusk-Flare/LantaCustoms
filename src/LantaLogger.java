public class LantaLogger {
    private String loggerID;
    private int logID;

    public LantaLogger(Class<?> owner) {
        loggerID = owner.getName();
        logID = 0;
    }

    public <T extends Exception> void logExcept(T exception) throws T{
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        log((cause == null) ? message : cause + "\n" + message);
        throw exception;
    }

    public void log(Object message) {
        StringBuilder log = new StringBuilder();
        String heading = "["+logID+" | "+loggerID+"]: ";
        String indent = "\n" + " ".repeat(heading.length());
        String[] messageContent = message.toString().split("\n");
        log.append(heading).append(messageContent[0]);
        for (int i = 1; i < messageContent.length; i++) log.append(indent).append(messageContent[i]);
        System.out.println(log.toString());
        logID++;
    }
}
