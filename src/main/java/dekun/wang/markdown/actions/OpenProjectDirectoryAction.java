package dekun.wang.markdown.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import dekun.wang.markdown.settings.model.PluginSettingsStateBo;
import org.jetbrains.annotations.NotNull;

import static dekun.wang.markdown.actions.SetAsDefaultDirectoryAction.DEFAULT_DIRECTORY_PROPERTY_KEY;

/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class OpenProjectDirectoryAction extends OpenTyporaBaseAction {
    @Override
    protected @NotNull String getDirectory(AnActionEvent event, PluginSettingsStateBo settings) {
        Project project = getEventProject (event);
        if (project == null) {
            return System.getProperty ("user.home");
        }

        PropertiesComponent properties = PropertiesComponent.getInstance (project);
        String defaultDirectory = properties.getValue (DEFAULT_DIRECTORY_PROPERTY_KEY);

        if (defaultDirectory == null) {
            defaultDirectory = project.getBasePath ();
            if (defaultDirectory == null) {
                defaultDirectory = System.getProperty ("user.home");
            }
        }
        return defaultDirectory;
    }
}
