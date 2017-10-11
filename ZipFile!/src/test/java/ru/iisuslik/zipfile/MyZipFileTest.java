package ru.iisuslik.zipfile;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static ru.iisuslik.zipfile.MyZipFile.main;

/** Some testes for MyZipFile*/
public class MyZipFileTest {

    /**
     * Tests functionality
     * @throws Exception
     */
    @Test
    public void mainTest() throws Exception {

        char sep = File.separatorChar;
        String testDirectory = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "test";
        String[] arr = {testDirectory, "k.k"};
        File unarchive = new File(testDirectory + sep + "ExtractedFiles");
        main(arr);
        assertTrue(unarchive.exists());
        assertEquals(1, unarchive.list().length);
        assertEquals("kek", (unarchive.listFiles())[0].getName());
        File kek = new File(unarchive.getPath() + sep + "kek");
        assertTrue(kek.delete());
        assertTrue(unarchive.delete());
    }



}