package dekun.wang.markdown.actions;

import com.intellij.ide.actions.RevealFileAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import dekun.wang.markdown.settings.model.PluginSettingsStateBo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class OpenSelectedDirectoryAction extends OpenTyporaBaseAction {

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent event) {
        return RevealFileAction.findLocalFile (event.getData (CommonDataKeys.VIRTUAL_FILE));
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = getEventProject (event);
        event.getPresentation ().setEnabledAndVisible (
                project != null && getSelectedFile (event) != null);
    }

    @Override
    protected @NotNull String getDirectory(AnActionEvent event, PluginSettingsStateBo settings) {
        VirtualFile directory = getSelectedDirectory (event);
        if (directory == null) {
            return System.getProperty ("user.home");
        }
        return directory.getPath ();
    }

    @Nullable
    private VirtualFile getSelectedDirectory(@NotNull AnActionEvent event) {
        VirtualFile file = getSelectedFile (event);
        return file != null
                ? file.isDirectory () ? file : file.getParent ()
                : null;
    }
}
