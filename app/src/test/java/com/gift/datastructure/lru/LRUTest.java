package com.gift.datastructure.lru;

import org.junit.Test;

/**
 * https://blog.csdn.net/justloveyou_/article/details/71713781#t6
 */
public class LRUTest {
    
    @Test
    public void test() {
        LRU<Character, Integer> lru = new LRU<>(16, 0.75f, true);
        String s = "abcdefghijkl";
        for (int i = 0; i < s.length(); i++) {
            lru.put(s.charAt(i), i);
        }
        
        System.out.println("LRU中key为h的Entry的值为： " + lru.get('h'));
        System.out.println("LRU的大小 ：" + lru.size());
        System.out.println("LRU ：" + lru);
    }
}
