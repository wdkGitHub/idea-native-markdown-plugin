package dekun.wang.markdown.model;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;

/**
 * @author wdk
 */
public class Environment {
    private static final Logger log = Logger.getInstance (Environment.class);
    private final OperationSystem os;
    private final String osVersion;
    private final String gui;

    public Environment(OperationSystem os, String osVersion, String gui) {
        this.os = os;
        this.osVersion = osVersion;
        this.gui = gui;
    }

    public static Environment getEnvironment() {
//        String osName = System.getProperty (SystemInfo.OS_NAME);
//        String osVersion = System.getProperty (SystemInfo.OS_VERSION);
        log.debug (SystemInfo.OS_NAME);
        log.debug (SystemInfo.OS_VERSION);
        String osName = System.getProperty ("os.name");
        String osVersion = System.getProperty ("os.version");
        String gui = System.getProperty ("sun.desktop");
        OperationSystem os = OperationSystem.fromString (osName);
        log.debug (osName);
        log.debug (osVersion);
        log.debug (gui);
        return new Environment (os, osVersion, gui);
    }

    public Markdown getDefaultTerminal() {
        switch (os) {
            case MAC_OS:
                return Markdown.TYPORA;
            case WINDOWS:
            case LINUX:
                throw new RuntimeException ("暂不支持，os: " + os);
            default:
                throw new IllegalArgumentException ("os: " + os);
        }
    }

    public OperationSystem getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getGui() {
        return gui;
    }

    @Override
    public String toString() {
        return "Environment{" + "os=" + os + ", osVersion='" + osVersion + '\'' + ", gui='" + gui + '\'' + '}';
    }
}
