package com.github.bigwolftime.filetobase64.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liuxin
 * @date 2023/12/5 12:44
 */
public class FileUtil {

    public static byte[] getFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

}
