// 代码生成时间: 2025-09-29 18:10:51
package com.yourcompany.folderorganizer;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * FolderOrganizer 类实现了一个文件夹结构整理器
 */
@QuarkusMain
@ApplicationScoped
public class FolderOrganizer implements QuarkusApplication {

    @Override
    public int run(String... args) {
        // 检查参数是否足够
        if (args.length < 1) {
            System.err.println("Usage: FolderOrganizer <directory path> [target directory path]");
            return 1;
        }

        String sourceFolderPath = args[0];
        String targetFolderPath = args.length > 1 ? args[1] : null;

        // 调用整理函数
        try {
            organizeFolders(sourceFolderPath, targetFolderPath);
        } catch (IOException e) {
            System.err.println("Error occurred while organizing folders: " + e.getMessage());
            return 1;
        }

        return 0;
    }

    /**
     * 整理文件夹
     * @param sourceFolder 源文件夹路径
     * @param targetFolder 目标文件夹路径
     * @throws IOException 如果发生 IO 错误
     */
    public void organizeFolders(String sourceFolder, String targetFolder) throws IOException {
        File sourceDir = new File(sourceFolder);
        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory: " + sourceFolder);
        }

        // 创建目标文件夹如果它不存在
        File targetDir = new File(targetFolder != null ? targetFolder : sourceFolder);
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new IOException("Failed to create target directory: " + targetFolder);
        }

        // 遍历源目录
        try (Stream<Path> paths = Files.walk(Paths.get(sourceFolder))) {
            paths.filter(Files::isRegularFile)
                .sorted(Comparator.comparing(Path::toString))
                .forEach(path -> {
                    try {
                        // 将文件重命名并移动到目标目录
                        File sourceFile = path.toFile();
                        String fileName = sourceFile.getName();
                        Path targetPath = Paths.get(targetFolder, fileName);
                        Files.move(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.err.println("Error moving file: " + path + " - " + e.getMessage());
                    }
                });
        }
    }
}
