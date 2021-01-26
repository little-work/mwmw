package com.lilin.mynetty.IO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

@Slf4j
public class MainTest {


    public static void main(String[] args) {
        try {
            doDecompress(new File("E:\\提测jar包\\lilin.gz"), new File("E:\\提测jar包"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int bufferSize = 8192;//单位bytes


    public static void doDecompress(File srcFile, File destDir) throws IOException {
        ZipArchiveInputStream is = null;
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(srcFile), bufferSize));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(
                                new FileOutputStream(new File(destDir, entry.getName())), bufferSize);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
    }



}
