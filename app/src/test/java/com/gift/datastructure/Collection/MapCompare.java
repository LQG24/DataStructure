package com.gift.datastructure.Collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapCompare {
    @Test
    public void test() {
        Map<Person, String> map = new HashMap<>();
        map.put(new Person("xiao", "Ming", 18), "nice");
        map.put(new Person("xiao", "hong", 20), "hong");

        System.out.println("get:"+map.get(new Person("xiao", "hong", 20)));
    }


    class Person {
        String firstName;
        String lastName;
        int age;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Person) {
                Person p = (Person) o;
                return this.age == p.age && Objects.equals(this.firstName, p.firstName) && Objects.equals(this.lastName, p.lastName);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, age);
        }
    }
}
