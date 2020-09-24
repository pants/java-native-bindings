package wtf.pants.bindings.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {

    public static List<String> getResourceLines(String path) {
        try {
            var res = FileUtils.class.getResource(path);
            return Files.readAllLines(new File(res.toURI()).toPath());
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}
