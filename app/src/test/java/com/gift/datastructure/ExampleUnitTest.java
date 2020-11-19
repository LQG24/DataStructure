package com.gift.datastructure;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    }
}