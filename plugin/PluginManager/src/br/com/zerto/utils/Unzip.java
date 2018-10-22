/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Zerato
 */
public class Unzip {

    /**
     * Holds the destination directory.
     * File will be unzipped into the destination directory.
     */
    private File destination;

    /**
     * Holds path to zip file.
     */
    private File source;

    public Unzip() {
    }

    public Unzip(File source, File destination) {
        this.source = source;
        this.destination = destination;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public void extract() throws IOException {
        // delete destination file if exists
        if (destination.exists() && destination.isDirectory()) {
            FileUtils.delete(destination.toPath(), destination.getName());
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(source))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                try {
                    File file = new File(destination, zipEntry.getName());

                    // create intermediary directories - sometimes zip don't add them
                    File dir = new File(file.getParent());
                    dir.mkdirs();

                    if (zipEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        byte[] buffer = new byte[1024];
                        int length;
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            while ((length = zipInputStream.read(buffer)) >= 0) {
                                fos.write(buffer, 0, length);
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                }
            }
        }
    }

}