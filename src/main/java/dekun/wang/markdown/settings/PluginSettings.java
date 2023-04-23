package dekun.wang.markdown.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import dekun.wang.markdown.settings.model.PluginSettingsStateBo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author wdk
 * @see <a href=""></a>
 * <p>
 * com.intellij.projectConfigurable
 */
@State(name = "dekun.wang.markdown.plugin.PluginSettings", storages = @Storage("nativeMarkdownWdkPlugin.xml"))
public class PluginSettings implements PersistentStateComponent<PluginSettingsStateBo> {

    private PluginSettingsStateBo pluginSettingsStateBo;

    public static PluginSettings getInstance() {
        return ServiceManager.getService (PluginSettings.class);
    }

    @Override
    public @Nullable PluginSettingsStateBo getState() {
        return pluginSettingsStateBo;
    }

    @Override
    public void loadState(@NotNull PluginSettingsStateBo state) {
        pluginSettingsStateBo = state;
    }
}
