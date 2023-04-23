package dekun.wang.markdown;

import com.intellij.openapi.diagnostic.Logger;
import dekun.wang.markdown.model.Command;
import dekun.wang.markdown.model.Environment;
import dekun.wang.markdown.model.Markdown;
import dekun.wang.markdown.model.OperationSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author wdk
 * @see <a href=""></a>
 * <br>
 */
public class OpenCommandBuilder {
    private static final Logger log = Logger.getInstance (OpenCommandBuilder.class);

    private static final String PROJECT_DIR_PLACEHOLDER = "${project.dir}";

    public static Command createCommand(@NotNull Environment env, @NotNull String projectDirectory, @Nullable String favoriteTerminalString) throws FileNotFoundException {
        checkProjectDirectory (projectDirectory);

        String command = favoriteTerminalString != null ? favoriteTerminalString : env.getDefaultTerminal ().getCommand ();

        if (isCustomCommand (command)) {
            String[] customCommand = command.replace (PROJECT_DIR_PLACEHOLDER, projectDirectory).split (" ");
            return new Command (customCommand);
        }

        Markdown mk = Markdown.fromString (command);

        log.info ("Favorite mk is [" + favoriteTerminalString + "] and using [" + mk + "]");
        log.info ("Project directory: " + projectDirectory);

        OperationSystem os = env.getOs ();
        switch (os) {
            case MAC_OS:
                switch (mk) {
                    case TYPORA:
                    case MWEB_PRO:
                    default:
                        return new Command ("open", projectDirectory, "-a", command);
                }
            case WINDOWS:
            case LINUX:
            default:
                throw new RuntimeException ("The environment is not supported: " + os);
        }
    }

    protected static boolean isCustomCommand(String command) {
        return command.contains (PROJECT_DIR_PLACEHOLDER);
    }

    protected static void checkProjectDirectory(@NotNull String projectDirectory) throws FileNotFoundException {
        File path = new File (projectDirectory);
        if (!path.exists ()) {
            throw new FileNotFoundException (path.getPath ());
        }
        if (!path.isDirectory ()) {
            throw new IllegalArgumentException (path.getPath ());
        }
    }
}
