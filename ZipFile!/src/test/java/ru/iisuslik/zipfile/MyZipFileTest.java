package ru.iisuslik.zipfile;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static ru.iisuslik.zipfile.MyZipFile.*;

/**
 * Some testes for MyZipFile
 */
public class MyZipFileTest {

    private char sep = File.separatorChar;

    /**
     * Tests functionality
     */
    @Test
    public void mainTest() throws Exception {

        String testDirectory = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "test1";
        String[] arr = {testDirectory, "k.k"};
        File unarchive = new File(testDirectory + sep + "ExtractedFiles");


        File file = new File(arr[0]);
        File extractTo = new File(file.getAbsolutePath() + File.separator + "ExtractedFiles");
        if (!extractTo.exists()) {
            extractTo.mkdir();
        }
        MyZipFile.setExtractTo(extractTo);
        findZipFiles(file, arr[1]);


        assertNotEquals(null, unarchive.listFiles());
        assertTrue(unarchive.exists());
        assertEquals(2, unarchive.list().length);
        assertEquals("kek", (unarchive.listFiles())[0].getName());
        assertEquals("kok", (unarchive.listFiles())[1].getName());
        File kek = new File(unarchive.getPath() + sep + "kek");
        assertTrue(kek.delete());
        File kok = new File(unarchive.getPath() + sep + "kok");
        assertTrue(kok.delete());
        assertTrue(unarchive.delete());
    }

    /**
     * Tests that in directory without zip files will happen nothing
     */
    @Test
    public void emptyTest() throws Exception{
        String testDirectory = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "test2";
        String[] arr = {testDirectory, "k.k"};
        File unarchive = new File(testDirectory + sep + "ExtractedFiles");


        File file = new File(arr[0]);
        File extractTo = new File(file.getAbsolutePath() + File.separator + "ExtractedFiles");
        MyZipFile.setExtractTo(extractTo);
        findZipFiles(file, arr[1]);


        assertNotEquals(null, unarchive.listFiles());
        assertTrue(unarchive.exists());
        assertEquals(0, unarchive.list().length);
        assertTrue(unarchive.delete());

    }

    /**
     * Check, that if args are really correct, result of checkArgs will be true
     */
    @Test
    public void checkCorrectArgs() throws Exception {
        String[] args = {".", "file"};
        assertTrue(MyZipFile.checkArgs(args));
    }

    /**
     * Check, that if you give checkArgs 1 or 3 args, it will return false
     */
    @Test
    public void checkNotTwoArgs() throws Exception {
        String[] args = {".", "file", "asd"};
        assertFalse(MyZipFile.checkArgs(args));
        args = new String[]{"asd"};
        assertFalse(MyZipFile.checkArgs(args));
    }

    /**
     * Check, that if first arg is not the name of directory, checkArgs returns false
     */
    @Test
    public void checkNotDirectory() throws Exception {
        String[] args = {".\\build.gradle", "file"};
        assertFalse(MyZipFile.checkArgs(args));
    }

    /**
     * Check, that if first arg is the name of file that not exists, checkArgs returns false
     */
    @Test
    public void checkNotExists() throws Exception {
        String[] args = {".\\topkek", "file"};
        assertFalse(MyZipFile.checkArgs(args));
    }

}