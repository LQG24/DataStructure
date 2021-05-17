package com.gift.datastructure.Collection;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * SortedMap在遍历时严格按照Key的顺序遍历，最常用的实现类是TreeMap；
 * 作为SortedMap的Key必须实现Comparable接口，或者传入Comparator；
 * 要严格按照compare()规范实现比较逻辑，否则，TreeMap将不能正常工作。
 */
public class TreeMapTest {
    @Test
    public void test() {
//        Map<Student, Integer> map = new TreeMap<>(new Comparator<Student>() {
//            public int compare(Student p1, Student p2) {
////                if (p1.score == p2.score) {
////                    return 0;
////                }
////                return p1.score > p2.score ? -1 : 1;
//                return -Integer.compare(p1.score, p2.score);
//            }
//        });
        Map<Student, Integer> map = new TreeMap<>();

        map.put(new Student("Tom", 77), 1);
        map.put(new Student("Bob", 66), 2);
        map.put(new Student("Lily", 99), 3);
        for (Student key : map.keySet()) {
            System.out.println(key);
        }
        System.out.println(map.get(new Student("Bob", 66))); // null?
    }

    static class Student implements Comparable {
        public String name;
        public int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String toString() {
            return String.format("{%s: score=%d}", name, score);
        }

        @Override
        public int compareTo(Object o) {
            Student student = (Student) o;
            return -Integer.compare(this.score, student.score);
        }
    }
}
