// 代码生成时间: 2025-09-23 23:29:31
package com.example.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipDecompressor {

    /**
     * Decompress a zip file to a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The directory where the zip file will be decompressed.
     */
    public void decompressZipFile(String zipFilePath, String destDirectory) {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File entryDestination = new File(destDirectory, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryDestination.toPath());
                } else {
                    Files.createDirectories(entryDestination.getParentFile().toPath());
                    extractFile(zipFile, entry, entryDestination);
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to zip file reading and writing
            throw new RuntimeException("Failed to decompress zip file: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to extract a single file from the zip.
     *
     * @param zipFile The zip file.
     * @param zipEntry The zip entry to extract.
     * @param destination The destination file.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipFile zipFile, ZipEntry zipEntry, File destination) throws IOException {
        try (FileInputStream fis = zipFile.getInputStream(zipEntry);
             FileOutputStream fos = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                fos.write(buffer, 0, length);
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        ZipDecompressor decompressor = new ZipDecompressor();
        String zipFilePath = "path/to/your.zip";
        String destDirectory = "path/to/destination";
        decompressor.decompressZipFile(zipFilePath, destDirectory);
        System.out.println("Decompression completed successfully.");
    }
}
