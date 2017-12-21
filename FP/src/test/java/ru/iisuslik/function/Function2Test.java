package ru.iisuslik.function;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Some tests to Function2
 */
public class Function2Test {

    private Function2<Integer, Integer, Integer> sum = (x, y) -> x + y;
    private Function2<Integer, Integer, Integer> dif = (x, y) -> x - y;
    private Function2<Integer, Integer, Integer> mult = (x, y) -> x * y;
    private Function2<String, Integer, Double> strange = (x, y) -> (double) (x.length() + y) / 2;
    private Function1<Integer, Integer> square = x -> x * x;

    /**
     * Try to apply functions int->int->int
     */
    @Test
    public void applySimple() throws Exception {
        assertEquals(43, (int) sum.apply(21, 22));
        assertEquals(4, (int) mult.apply(2, 2));
    }

    /**
     * Try to apply functions string->int->double
     */
    @Test
    public void applyDifferentTypes() throws Exception {
        assertEquals(2.5, (double) strange.apply("tri", 2), 0.1);
    }

    /**
     * Try to compose functions int->int->int
     */
    @Test
    public void compose() throws Exception {
        assertEquals(16, (int) sum.compose(square).apply(1, 3));
    }

    /**
     * Try to bind functions int->int->int
     */
    @Test
    public void bind1() throws Exception {
        Function1<Integer, Integer> plus43 = sum.bind1(43);
        assertEquals(45, (int) plus43.apply(2));
    }

    /**
     * Try to bind functions string->int->double
     */
    @Test
    public void bind1DifferentTypes() throws Exception {
        Function1<Integer, Double> plus3Div2 = strange.bind1("kek");
        assertEquals(3.5, (double) plus3Div2.apply(4), 0.1);
    }


    /**
     * Try to bind function int->int->int: bind1(f) != bind2(f)
     */
    @Test
    public void bind1DontCommut() throws Exception {
        Function1<Integer, Integer> plus43other = dif.bind1(43);
        assertEquals(40, (int) plus43other.apply(3));
    }


    /**
     * Try to bind functions int->int->int
     */
    @Test
    public void bind2() throws Exception {
        Function1<Integer, Integer> plus43 = sum.bind2(43);
        assertEquals(45, (int) plus43.apply(2));
    }


    /**
     * Try to bind functions string->int->double
     */
    @Test
    public void bind2DifferentTypes() throws Exception {
        Function1<String, Double> plus3Div2 = strange.bind2(4);
        assertEquals(4.5, (double) plus3Div2.apply("kekes"), 0.1);
    }


    /**
     * Try to bind function int->int->int: bind2(f) != bind1(f)
     */
    @Test
    public void bind2DontCommut() throws Exception {
        Function1<Integer, Integer> minus43 = dif.bind2(43);
        assertEquals(37, (int) minus43.apply(80));
    }


    /**
     * Try to curry functions int->int->int
     */
    @Test
    public void curry() throws Exception {
        Function1<Integer, Integer> plus43 = sum.curry(43);
        assertEquals(45, (int) plus43.apply(2));
    }

}