package dekun.wang.markdown.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.ex.FileChooserDialogImpl;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import dekun.wang.markdown.model.Markdown;
import dekun.wang.markdown.ui.PluginSettingForm;
import org.apache.http.util.TextUtils;
import org.jdesktop.swingx.util.OS;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @author wdk
 */
public class PluginSettingsConfigurable implements Configurable {

    private final Project project;
    private final String warningMessage = "warningMessage";
    private final PluginSettingForm pluginSettingForm;
    private final PluginSettings pluginSettings;
    private final FileChooserDescriptor terminalChooserDescriptor;
    private VirtualFile selectedTerminal;

    public PluginSettingsConfigurable() {
        // Set 'chooseFolders' depend on OS, because macOS application represents a directory.
        terminalChooserDescriptor = new FileChooserDescriptor (true, OS.isMacOSX (), false, false, false, false);
        Project[] openProjects = ProjectManager.getInstance ().getOpenProjects ();
        if (openProjects.length > 0) {
            project = openProjects[0];
        } else {
            project = ProjectManager.getInstance ().getDefaultProject ();
        }
        pluginSettingForm = new PluginSettingForm ();
        pluginSettings = PluginSettings.getInstance ();
        // FileChooserDialog support  -longforus
        String favoriteMarkdown = "";
        if (pluginSettings != null && pluginSettings.getState () != null) {
            favoriteMarkdown = pluginSettings.getState ().getFavoriteMarkdown ();
        }
        if (!TextUtils.isEmpty (favoriteMarkdown)) {
            selectedTerminal = VirtualFileManager.getInstance ().findFileByUrl (getFileUrl (favoriteMarkdown));
        }
        pluginSettingForm.getCommit ().addActionListener (e -> {
            VirtualFile[] chosenTerminals = new FileChooserDialogImpl (terminalChooserDescriptor, project).choose (project, selectedTerminal);
            if (chosenTerminals.length > 0) {
                VirtualFile file = chosenTerminals[0];
                if (file != null) {
                    String canonicalPath = file.getCanonicalPath ();
                    Markdown mk = Markdown.fromString (canonicalPath);
                    if (mk == Markdown.GENERIC) {
                        Messages.showWarningDialog (warningMessage, "Warning");
                    }
                    selectedTerminal = file;
                    pluginSettingForm.getInstallPath ().setText (canonicalPath);
                }
            }
        });
    }

    private String getFileUrl(String path) {
        return "file://" + path;
    }


    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
//        return "Native Markdown Plugin";
        return "本机 Markdown 软件";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return pluginSettingForm.getSettingPanel ();

    }

    @Override
    public boolean isModified() {
        return !pluginSettingForm.getSettingsState ().equals (pluginSettings.getState ());
    }

    @Override
    public void apply() throws ConfigurationException {
        pluginSettings.loadState (pluginSettingForm.getSettingsState ());

    }

    @Override
    public @Nullable @NonNls String getHelpTopic() {
        return "Configure Native Markdown Plugin";
    }

    @Override
    public void reset() {
        if (pluginSettings.getState () != null) {
            pluginSettingForm.getSettingState (pluginSettings.getState ());
        }
    }

    @Override
    public void disposeUIResources() {
    }
}
