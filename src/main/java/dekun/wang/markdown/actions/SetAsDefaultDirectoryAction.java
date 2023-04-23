package dekun.wang.markdown.actions;

import com.intellij.ide.actions.RevealFileAction;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class SetAsDefaultDirectoryAction extends DumbAwareAction {

    public static final String DEFAULT_DIRECTORY_PROPERTY_KEY = "dekun.wang.idea-native-markdown-plugin.properties.directory";
    private static final Logger log = Logger.getInstance (SetAsDefaultDirectoryAction.class);

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent event) {
        return RevealFileAction.findLocalFile (event.getData (CommonDataKeys.VIRTUAL_FILE));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject (e);
        VirtualFile directory = getSelectedDirectory (e);
        if (project == null || directory == null) {
            return;
        }

        PropertiesComponent properties = PropertiesComponent.getInstance (project);
        properties.setValue (DEFAULT_DIRECTORY_PROPERTY_KEY, directory.getPath ());

        log.info ("'" + properties.getValue (DEFAULT_DIRECTORY_PROPERTY_KEY) + "' is set as default directory for project: " + project.getName ());

    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread ();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = getEventProject (event);
        event.getPresentation ().setEnabledAndVisible (project != null && getSelectedFile (event) != null);
    }

    @Nullable
    private VirtualFile getSelectedDirectory(@NotNull AnActionEvent event) {
        VirtualFile file = getSelectedFile (event);
        return file != null ? file.isDirectory () ? file : file.getParent () : null;
    }


}
