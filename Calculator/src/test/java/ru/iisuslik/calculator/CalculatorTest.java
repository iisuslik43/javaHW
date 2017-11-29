package ru.iisuslik.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Some tests to calculator
 */
public class CalculatorTest {


    @Mock
    private Stack<Integer> stMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    private ArrayList<String> just43 = new ArrayList<>();
    private ArrayList<String> twoPlusTwo = new ArrayList<>();
    private ArrayList<String> tenMultiplySeven = new ArrayList<>();
    private ArrayList<String> tenMinusSeven = new ArrayList<>();
    private ArrayList<String> tenDivideSeven = new ArrayList<>();
    private ArrayList<String> plusAndMinus = new ArrayList<>();
    private ArrayList<String> big = new ArrayList<>();

    public CalculatorTest() {
        just43.addAll(Collections.singletonList("2"));
        Collections.addAll(twoPlusTwo, "2", "2", "+");
        Collections.addAll(tenMultiplySeven, "10", "7", "*");
        Collections.addAll(tenMinusSeven, "10", "7", "-");
        Collections.addAll(tenDivideSeven, "16", "2", "/");
        Collections.addAll(plusAndMinus, "16", "2", "3", "+", "-");
        Collections.addAll(big, "8", "2", "5", "*", "+", "1", "3", "2", "*", "+", "4", "-", "/");

    }

    /**
     * Try to check that calculate(43) = 43
     */
    @Test
    public void justNumber() throws Exception {
        when(stMock.pop()).thenReturn(43);

        Calculator calc = new Calculator(stMock);
        assertEquals(43, calc.calculate(just43));
        verify(stMock).push(anyInt());
    }

    /**
     * Try to calculate 2 + 2
     */
    @Test
    public void twoPlusTwo() throws Exception {
        when(stMock.pop()).thenReturn(2).thenReturn(2).thenReturn(4);
        Calculator calc = new Calculator(stMock);
        assertEquals(4, calc.calculate(twoPlusTwo));
        verify(stMock, times(3)).push(anyInt());
    }

    /**
     * Try to calculate 10 + 7
     */
    @Test
    public void tenMultiplySeven() throws Exception {
        when(stMock.pop()).thenReturn(10).thenReturn(7).thenReturn(70);
        Calculator calc = new Calculator(stMock);
        assertEquals(70, calc.calculate(tenMultiplySeven));
        verify(stMock, times(3)).push(anyInt());
    }

    /**
     * Try to calculate 10 - 7
     */
    @Test
    public void tenMinusSeven() throws Exception {
        when(stMock.pop()).thenReturn(10).thenReturn(7).thenReturn(3);
        Calculator calc = new Calculator(stMock);
        assertEquals(3, calc.calculate(tenMinusSeven));
        verify(stMock, times(3)).push(anyInt());
    }

    /**
     * Try to calculate 16 / 2
     */
    @Test
    public void tenDivideSeven() throws Exception {
        when(stMock.pop()).thenReturn(16).thenReturn(2).thenReturn(8);
        Calculator calc = new Calculator(stMock);
        assertEquals(8, calc.calculate(tenDivideSeven));
        verify(stMock, times(3)).push(anyInt());
    }

    /**
     * Try to calculate 16 - (2 + 3) = 16 2 3 + -
     */
    @Test
    public void simpleCompose() throws Exception {
        when(stMock.pop()).thenReturn(2).thenReturn(3).thenReturn(5).thenReturn(16).thenReturn(11);
        Calculator calc = new Calculator(stMock);
        assertEquals(11, calc.calculate(plusAndMinus));
        verify(stMock, times(5)).push(anyInt());
    }

    /**
     * Try to calculate (8 + 2 * 5)/(1+3*2-4) = 825*+132*+4-/
     */
    @Test
    public void hardCompose() throws Exception {
        when(stMock.pop()).thenReturn(5).thenReturn(2).thenReturn(10).thenReturn(8).thenReturn(2).thenReturn(3).
                thenReturn(6).thenReturn(1).thenReturn(4).thenReturn(7).thenReturn(3).thenReturn(18).thenReturn(6);
        Calculator calc = new Calculator(stMock);
        assertEquals(6, calc.calculate(big));
        verify(stMock, times(13)).push(anyInt());
    }
}