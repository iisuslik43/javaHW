package sp;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.*;

public class SecondPartTasksTest {

    /**
     * Try to find string "lol" in 2 files in directory resources
     */
    @Test
    public void testFindQuotes() throws Exception {
        char sep = File.separatorChar;
        String common = "." + sep + "src" + sep + "test" + sep + "resources" + sep;
        List<String> files = Arrays.asList(common + "first.txt", common + "second.txt");
        List<String> res = Arrays.asList("lol", "trolololol", "lollll", "lellol");
        assertEquals(res, findQuotes(files, "lol"));
    }

    /**
     * Try to calculate pi/4 ten times and check that real |pi/4 - piDivide4()| < 0.005
     */
    @Test
    public void testPiDividedBy4() throws Exception {
        double piDividedBy4 = 0.7853981;
        for (int i = 0; i < 10; i++) {
            assertTrue(Math.abs(piDividedBy4() - piDividedBy4) < 0.005);
        }
    }

    /**
     * Try check that findPrinter from small map is correct
     */
    @Test
    public void testFindPrinter() throws Exception {
        Map<String, List<String>> m1 = ImmutableMap.of("First", Arrays.asList("abs", "asd"),
                "Second", Arrays.asList("a", "s", "d"),
                "Third", Collections.singletonList("abcde"));
        assertEquals("First", findPrinter(m1));
    }

    /**
     * Create 4 maps and union it
     */
    @Test
    public void testCalculateGlobalOrder() throws Exception {
        Map<String, Integer> m1 = ImmutableMap.of("Dead mouses", 2,
                "Chocolate candies", 3);

        Map<String, Integer> m2 = ImmutableMap.of("Alive ***", 19,
                "Chocolate candies", 2);
        Map<String, Integer> m3 = ImmutableMap.of("Serious snow", 20);
        Map<String, Integer> m4 = ImmutableMap.of("Russian field of experiments", 1,
                "Dead mouses", 7);

        Map<String, Integer> res = ImmutableMap.of("Alive ***", 19, "Chocolate candies", 5, "Dead mouses", 9,
                "Russian field of experiments", 1, "Serious snow", 20);
        assertEquals(res, calculateGlobalOrder(Arrays.asList(m1, m2, m3, m4)));
    }


}