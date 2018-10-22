/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Zerato
 */
public class FileUtils {

    public static List<String> readLines(Path path, boolean ignoreComments) throws IOException {
        File file = path.toFile();
        if (!file.exists() || !file.isFile()) {
            return new ArrayList<>(); 
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (ignoreComments && !line.startsWith("#") && !lines.contains(line)) {
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    public static void writeLines(Collection<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
    }

    /**
     * Delete a file or recursively delete a folder, do not follow symlinks.
     *
     * @param path the file or folder to delete
     * @throws IOException if something goes wrong
     */
    public static void delete(Path rootDirecory, String fileName) throws IOException {
        File[] files = rootDirecory.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains(fileName)) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * Finds a path with various endings or null if not found.
     *
     * @param basePath the base name
     * @param endings a list of endings to search for
     * @return new path or null if not found
     */
    public static Path findWithEnding(Path basePath, String endings) {
        File[] files = basePath.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(endings)) {
                        return file.toPath();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Delete a file (not recursively) and ignore any errors.
     *
     * @param path the path to delete
     */
    public static void optimisticDelete(Path path) {
        if (path == null) {
            return;
        }

        try {
            Files.delete(path);
        } catch (IOException ignored) { }
    }

    /**
     * Unzip a zip file in a directory that has the same name as the zip file.
     * For example if the zip file is {@code my-plugin.zip} then the resulted directory
     * is {@code my-plugin}.
     *
     * @param filePath the file to evaluate
     * @return Path of unzipped folder or original path if this was not a zip file
     * @throws IOException on error
     */
    public static Path expandIfZip(Path filePath) throws IOException {
        if (!isZipFile(filePath)) {
            return filePath;
        }

        FileTime pluginZipDate = Files.getLastModifiedTime(filePath);
        String fileName = filePath.getFileName().toString();
        Path pluginDirectory = filePath.resolveSibling(fileName.substring(0, fileName.lastIndexOf(".")));

        if (!Files.exists(pluginDirectory) || pluginZipDate.compareTo(Files.getLastModifiedTime(pluginDirectory)) > 0) {
            // do not overwrite an old version, remove it
            if (Files.exists(pluginDirectory)) {
                FileUtils.delete(pluginDirectory, fileName);
            }

            // create root for plugin
            Files.createDirectories(pluginDirectory);

            // expand '.zip' file
            Unzip unzip = new Unzip();
            unzip.setSource(filePath.toFile());
            unzip.setDestination(pluginDirectory.toFile());
            unzip.extract();
        }

        return pluginDirectory;
    }

    /**
     * Return true only if path is a zip file.
     *
     * @param path to a file/dir
     * @return true if file with {@code .zip} ending
     */
    public static boolean isZipFile(Path path) {
        return Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".zip");
    }

    /**
     * Return true only if path is a jar file.
     *
     * @param path to a file/dir
     * @return true if file with {@code .jar} ending
     */
    public static boolean isJarFile(Path path) {
        return Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".jar");
    }

    public static Path getPath(Path path, String first, String... more) throws IOException {
        URI uri = path.toUri();
        return getPath(uri, first, more);
    }

    public static Path getPath(URI uri, String first, String... more) throws IOException {
        return getFileSystem(uri).getPath(first, more);
    }

    public static Path findFile(Path directoryPath, String fileName) {
        File[] files = directoryPath.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().equals(fileName)) {
                        return file.toPath();
                    }
                } else if (file.isDirectory()) {
                    Path foundFile = findFile(file.toPath(), fileName);
                    if (foundFile != null) {
                        return foundFile;
                    }
                }
            }
        }

        return null;
    }

    public static List<Path> listWarForFolderPath(Path directoryPath) {
        File[] files = directoryPath.toFile().listFiles();
        List<Path> pathsWar = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    pathsWar.addAll(listWarForFolderPath(file.toPath()));
                } else {
                    if (file.isFile()) {
                        if (file.getName().endsWith(".war")) {
                            pathsWar.add(file.toPath());
                        }
                    }
                }
            }
        }
        return pathsWar;
    }
    
    private static FileSystem getFileSystem(URI uri) throws IOException {
        try {
            return FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException e) {
            return FileSystems.newFileSystem(uri, Collections.<String, String>emptyMap());
        }
    }

    
    
}