package ru.iisuslik.zipfile;


import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class realises console utility that takes directory and regular expression
 * and unarchive from all archive files in this directory(recursive) all files with
 * names, that satisfy regular expression.
 * Use it like java MyZipFile <directory> <regexp>
 */
public class MyZipFile {

    private static File extractTo;

    private static void write(InputStream in, OutputStream out) throws IOException {
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
     */
    private static void extractZipFile(File file, String regExp) {
        try (ZipFile zip = new ZipFile(file)) {
            Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.print(getName(entry) + ' ');
                if (!entry.isDirectory() && Pattern.matches(regExp, getName(entry))) {
                    write(zip.getInputStream(entry), new BufferedOutputStream(
                            new FileOutputStream(new File(extractTo, getName(entry)))));
                }
            }
            System.out.println();
        } catch (IOException ignored) {
        }
    }


    /**
     * Recursively search all archive files in directory and call extractZipFIle in them
     *
     * @param file   directory where to search files
     * @param regExp regexp for extractZipFile
     */
    private static void findZipFiles(File file, String regExp, String whitespaces) {
        for (File nextFile : file.listFiles()) {
            if (nextFile.isFile() && file.canRead()) {
                System.out.print(whitespaces);
                System.out.print("file: ");
                System.out.println(nextFile.getName() + " ");
                extractZipFile(nextFile, regExp);

            } else if (nextFile.isDirectory() && file.canRead()) {
                System.out.print(whitespaces);
                System.out.print("dir: ");
                System.out.println(nextFile.getName() + " ");
                findZipFiles(nextFile, regExp, whitespaces + "--");
            }
        }
    }

    /**
     * Realization of utility
     *
     * @param args directory and regexp
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please use like MyZipFile <directory> <regexp>");
        }
        String path = args[0];
        String regExp = args[1];
        File file = new File(path);

        if (!file.isDirectory()) {
            System.out.println("Input file is not a directory");
            return;
        }
        if (!file.exists() || !file.canRead()) {
            System.out.println("Directory doesn't exists");
            return;
        }

        extractTo = new File(file.getAbsolutePath() + File.separator + "ExtractedFiles");
        if (!extractTo.exists()) {
            extractTo.mkdir();
        }
        findZipFiles(file, regExp, "");
    }
}

