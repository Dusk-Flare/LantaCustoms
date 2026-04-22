package lanta.utils;

import lanta.math.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringHandler {
    private static final LantaLogger logger = new LantaLogger(StringHandler.class);

    public static Matcher matcher(String string, String regex){
        try{
            return string != null ? Pattern.compile(regex).matcher(string) : null;
        } catch(PatternSyntaxException | IndexOutOfBoundsException exception){
            logger.logCatch(exception);
            return null;
        }
    }

    public static boolean matches(String string, String regex){
        try{
            return string != null && Pattern.matches(regex, string);
        } catch(PatternSyntaxException exception){
            logger.logCatch(exception);
            return false;
        }
    }

    public static String extract(String string, String regex, int group){
        if(string == null) return null;
        try{
            Matcher matcher = Pattern.compile(regex).matcher(string);
            return matcher.find() ? matcher.group(group) : null;
        } catch(PatternSyntaxException | IndexOutOfBoundsException exception){
            logger.logCatch(exception);
            return null;
        }
    }

    public static List<String> extract(String string, String regex){
        List<String> extracted = new ArrayList<>();
        if(string == null) return null;
        try{
            Matcher matcher = Pattern.compile(regex).matcher(string);
            while (matcher.find()) {
                extracted.add(matcher.group());
            }
            return extracted;
        } catch(PatternSyntaxException exception){
            logger.logCatch(exception);
            return null;
        }
    }

    public static String replaceAll(String string, String replace, String regex){
        try {
            return string != null ? string.replaceAll(regex, replace) :  null;
        } catch (PatternSyntaxException exception) {
            logger.logCatch(exception);
            return string;
        }
    }
    
    public static String appendVars(String string, Object... vars){
        for (int i = 0; i < vars.length; i++) {
            string = replaceAll(string, Matcher.quoteReplacement(vars[i] != null ? vars[i].toString() : ""), "\\$"+i);
            if(string == null) return null;
        }
        return string;
    }

    public static String replaceVars(String string, Object... vars) {
        Matcher matcher = matcher(string, "\\$(\\d+)");
        if (string == null || matcher == null) return null;
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            Integer index = Parser.toNumber(matcher.group(1), Integer::parseInt);
            matcher.appendReplacement(result, Matcher.quoteReplacement((index != null && index < vars.length && vars[index] != null) ? vars[index].toString() : ""));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
