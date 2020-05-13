package org.jpcl.fileop.file;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 文件工具类
 * @author Administrator
 */
final public class FileUtils {
    private FileUtils() {}

    /**
     * 取得一个有效的文件
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String path) throws FileNotFoundException {
        if (path == null || path.trim().length() == 0) {
            throw new FileNotFoundException("文件路径非法无效。");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在。");
        }
        return file;
    }
}
