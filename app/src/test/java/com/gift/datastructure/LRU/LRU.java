package com.gift.datastructure.LRU;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {
    
    public LRU(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }
    
    
    /**
     * 重写LinkedHashMap中的removeEldestEntry方法，当LRU中元素多余6个时，删除最不经常使用的元素
    * */
    @Override
    protected boolean removeEldestEntry(Entry eldest) {
        if (size() > 6) {
            return true;
        }
        return false;
    }
}
