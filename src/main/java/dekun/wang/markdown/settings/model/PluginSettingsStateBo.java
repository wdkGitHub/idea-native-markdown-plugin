package dekun.wang.markdown.settings.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author wdk
 * @see <a href=""></a>
 * <br>
 */
public class PluginSettingsStateBo {

    private String favoriteMarkdown;

    public PluginSettingsStateBo() {
    }

    public PluginSettingsStateBo(String favoriteMarkdown) {
        this.favoriteMarkdown = favoriteMarkdown;
    }

    public String getFavoriteMarkdown() {
        return favoriteMarkdown;
    }

    public void setFavoriteMarkdown(String favoriteMarkdown) {
        this.favoriteMarkdown = favoriteMarkdown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass () != o.getClass ()) {
            return false;
        }
        PluginSettingsStateBo that = (PluginSettingsStateBo) o;

        return new EqualsBuilder ().append (favoriteMarkdown, that.favoriteMarkdown).isEquals ();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder (17, 37).append (favoriteMarkdown).toHashCode ();
    }


}
