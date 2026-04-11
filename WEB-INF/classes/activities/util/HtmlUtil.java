package activities.util;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlUtil {

    public static String e(String s) {
        if (s == null) {
            return "";
        }
        return StringEscapeUtils.escapeHtml4(s);
    }
}
//Compilacion: javac -cp ".;..\lib\mysql-connector-java-8.0.28.jar;..\lib\commons-text-1.15.0.jar;..\lib\commons-lang3-3.20.0.jar;C:\Program Files\Apache Software Foundation\Tomcat 9.0\lib\servlet-api.jar" .\activities\util\HtmlUtil.java