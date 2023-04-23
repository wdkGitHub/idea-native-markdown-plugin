package dekun.wang.markdown.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import dekun.wang.markdown.OpenCommandBuilder;
import dekun.wang.markdown.model.Command;
import dekun.wang.markdown.model.Environment;
import dekun.wang.markdown.settings.PluginSettings;
import dekun.wang.markdown.settings.model.PluginSettingsStateBo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public abstract class OpenTyporaBaseAction extends DumbAwareAction {
//    @Deprecated
    protected static final String ENV_FAVORITE_MARKDOWN = "FAVORITE_MARKDOWN";
    protected static final Environment env = Environment.getEnvironment ();
    private static final Logger log = Logger.getInstance (OpenTyporaBaseAction.class);

    @NotNull
    protected abstract String getDirectory(AnActionEvent event, PluginSettingsStateBo settings);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        log.info (env.toString ());

        try {
            PluginSettingsStateBo settings = PluginSettings.getInstance ().getState ();
            final String directory = getDirectory (event, settings);
            final String favoriteTerminalString = getFavoriteMarkdown (settings);
            Command command = OpenCommandBuilder.createCommand (env, directory, favoriteTerminalString);
            log.info (command.getCommands ().toString ());
            command.execute (directory);
        } catch (IOException e) {
            throw new RuntimeException ("Failed to execute the command!", e);
        }
    }

    private String getFavoriteMarkdown(PluginSettingsStateBo settings) {
        // to be compatible with old versions (prior to v0.2)
        final String envFavoriteTerminal = System.getenv (ENV_FAVORITE_MARKDOWN);
        if (settings != null) {
            String favoriteTerminalString = settings.getFavoriteMarkdown ();
            if (favoriteTerminalString != null && !favoriteTerminalString.isEmpty ()) {
                return favoriteTerminalString;
            }
        }
        return envFavoriteTerminal;
    }
}
