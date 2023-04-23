package dekun.wang.markdown.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wdk
 */
public class Command {

    private final List<String> commands;

    public Command(String... commands) {
        this.commands = Arrays.stream (commands).filter (Objects::nonNull).collect (Collectors.toList ());
    }

    public void add(String... commands) {
        this.commands.addAll (Arrays.asList (commands));
    }

    @Deprecated
    public void execute() throws IOException {
        new ProcessBuilder (commands).start ();
    }

    public void execute(String path) throws IOException {
        new ProcessBuilder (commands).directory (new File (path)).start ();
    }

    public List<String> getCommands() {
        return new ArrayList<> (commands);
    }

    @Override
    public String toString() {
        return "Command{" + "commands=" + commands + '}';
    }
}
