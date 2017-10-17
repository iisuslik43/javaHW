package ru.iisuslik.maybe;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Some tests to class Maybe
 */
public class MaybeTest {
    /**
     * Tries to create Maybe.just
     *
     * @throws Exception
     */
    @Test
    public void just() throws Exception {
        Maybe<Integer> m = Maybe.just(43);
    }

    /**
     * Tries to create Maybe.nothing
     *
     * @throws Exception
     */
    @Test
    public void nothing() throws Exception {
        Maybe<Integer> m = Maybe.nothing();
    }

    /**
     * Tests get
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        Maybe<Integer> m = Maybe.just(43);
        Integer i = m.get();
        assertEquals(43, i.intValue());
        Maybe<Integer> mEmpty = Maybe.nothing();
        boolean wasException = false;
        try {
            Integer iNull = mEmpty.get();
        } catch (MaybeException mE) {
            wasException = true;
        }
        assertTrue(wasException);
    }

    /**
     * Tests isPresent
     *
     * @throws Exception
     */
    @Test
    public void isPresent() throws Exception {
        Maybe<Integer> m = Maybe.just(43);
        Maybe<Integer> mEmpty = Maybe.nothing();
        assertTrue(m.isPresent());
        assertFalse(mEmpty.isPresent());
    }

    /**
     * Tests map
     *
     * @throws Exception
     */
    @Test
    public void map() throws Exception {
        Maybe<Integer> m = Maybe.just(43);
        Maybe<Integer> mEmpty = Maybe.nothing();
        Maybe<Integer> resultEmpty = mEmpty.map(x -> x * x);
        assertFalse(resultEmpty.isPresent());
        Maybe<Integer> result = m.map(x -> x * x - 146);
        assertTrue(result.isPresent());
        assertEquals(1703, result.get().intValue());
    }

    /**
     * Tests getIntFromFile, this test uses file ./src/test/resources/testInFile
     *
     * @throws Exception
     */
    @Test
    public void parseFile() throws Exception {
        String sep = File.separator;
        String testDirectory = "." + sep + "src" + sep + "test" + sep + "resources" + sep;
        String inFile = testDirectory + "testInFile";
        String outFile = testDirectory + "testOutFile";
        Maybe.getIntFromFile(inFile, outFile);
        ArrayList<String> vector = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(outFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                vector.add(line);
            }
        } catch (IOException e) {
            // pass
        }
        String[] arr = {"144", "529", "null", "1849", "144", "null", "null", "null"};
        assertEquals(arr, vector.toArray());
        File out = new File(outFile);
        out.delete();
    }
}