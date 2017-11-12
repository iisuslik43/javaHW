package ru.iisuslik.zipfile;


import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.*;

import org.jetbrains.annotations.NotNull;

/**
 * This class realises console utility that takes directory and regular expression
 * and unarchive from all archive files in this directory(recursive) all files with
 * names, that satisfy regular expression.
 * Use it like java MyZipFile <directory> <regexp>
 */
public class MyZipFile {

    private static File extractTo;

    private static void write(@NotNull InputStream in, @NotNull OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }

    private static String getName(ZipEntry entry) {
        if (entry == null) return "";
        File forName = new File(entry.getName());
        return forName.getName();
    }

    /**
     * @param file   archive file where you want to find files
     * @param regExp regular expression that you want to try to match with files
     * @throws IOException if there are some problems with files in directory
     */
    private static void extractZipFile(@NotNull File file, @NotNull String regExp) throws IOException {
        try (ZipFile zip = new ZipFile(file)) {
            Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (!entry.isDirectory() && Pattern.matches(regExp, getName(entry))) {
                    write(zip.getInputStream(entry), new BufferedOutputStream(
                            new FileOutputStream(new File(extractTo, getName(entry)))));
                }
            }
        } catch (ZipException ignored) {
        }
    }


    /**
     * Recursively search all archive files in directory and call extractZipFIle in them
     *
     * @param file   directory where to search files
     * @param regExp regexp for extractZipFile
     * @throws IOException if there are some problems with files in directory
     */
    public static void findZipFiles(@NotNull File file, @NotNull String regExp) throws IOException {
        if (file.listFiles() == null) {
            return;
        }
        for (File nextFile : file.listFiles()) {
            if (nextFile.isFile() && file.canRead()) {
                extractZipFile(nextFile, regExp);

            } else if (nextFile.isDirectory() && file.canRead()) {
                findZipFiles(nextFile, regExp);
            }
        }
    }

    /**
     * Function to check that arguments for function main are correct
     *
     * @param args arguments to check
     * @return true if arguments are correct, false else
     */
    public static boolean checkArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Please use like MyZipFile <directory> <regexp>");
            return false;
        }
        String path = args[0];
        File file = new File(path);


        if (!file.exists() || !file.canRead()) {
            System.out.println("Directory doesn't exists");
            return false;
        }
        if (!file.isDirectory()) {
            System.out.println("Input file is not a directory");
            return false;
        }
        return true;
    }


    /**
     * Just setter extractTo directory
     *
     * @param extractTo directory to set
     */
    public static void setExtractTo(File extractTo) {
        MyZipFile.extractTo = extractTo;
        if (!extractTo.exists()) {
            extractTo.mkdir();
        }
    }

    /**
     * Realization of utility
     *
     * @param args directory and regexp
     */
    public static void main(String[] args) {
        if (!checkArgs(args)) {
            return;
        }
        String path = args[0];
        String regExp = args[1];
        File file = new File(path);

        setExtractTo(new File(file.getAbsolutePath() + File.separator + "ExtractedFiles"));
        try {
            findZipFiles(file, regExp);
        } catch (IOException ie) {
            System.out.println("One of archive files can't be read");
        }
    }
}

