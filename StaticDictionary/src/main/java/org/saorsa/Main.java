package org.saorsa;

import dictionary.StaticDictionary;

public class Main {
    public static void main(String[] args) throws Exception {
        StaticDictionary<String, Integer> namesCount = new StaticDictionary<>();

        namesCount.put("Slav", 1);
        namesCount.put("Atanas", 2);

        System.out.println(namesCount.size());
    }
}