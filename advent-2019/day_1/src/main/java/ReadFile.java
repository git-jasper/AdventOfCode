import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ReadFile {

    public static List<String> read() {
        File file = new File(ReadFile.class.getResource("modules.txt").getFile());
        try {
            return FileUtils.readLines(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
