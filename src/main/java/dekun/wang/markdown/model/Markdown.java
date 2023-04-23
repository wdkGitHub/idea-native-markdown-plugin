package dekun.wang.markdown.model;

import java.io.File;

/**
 * @author wdk
 */

public enum Markdown {

    // macOS
    TYPORA ("Typora"), MWEB_PRO ("MWeb\\ Pro.app"), GENERIC ("");

    private final String command;
    private final String defaultPath;

    Markdown(String command) {
        this.command = command;
        this.defaultPath = null;
    }

    Markdown(String command, String defaultPath) {
        this.command = command;
        this.defaultPath = defaultPath;
    }

    public static Markdown fromString(String command) {
        return matchTerminal (getExecutable (command));
    }

    private static Markdown matchTerminal(String fileName) {
        for (Markdown markdown : Markdown.values ()) {
            if (markdown != GENERIC  // skip GENERIC because `contains("")` returns `true` for any string
                    && containsIgnoreCase (fileName, markdown.command)) {
                return markdown;
            }
        }
        return GENERIC;
    }

    protected static String getExecutable(String command) {
        return new File (command).getName ();
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase ().contains (s2.toLowerCase ());
    }

    public String getCommand() {
        return command;
    }

    public String getDefaultPath() {
        return defaultPath;
    }
}
