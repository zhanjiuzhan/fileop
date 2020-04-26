package org.jpcl.fileop.write;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 */
public class FileWrite {
    public static void writeTextFileByLine(String path, List<String> content) {
        FileWriter writer;
        try {
            writer = new FileWriter(path);
            for (String str : content) {
                writer.write(str + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
