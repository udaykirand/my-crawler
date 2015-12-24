package com.imaginea.crawler;

import java.util.HashSet;
import java.util.Set;

public class TestList {
public static void main(String[] s) {
	Set<String> test = new HashSet<String>();
	System.out.println(test.add("test1"));
	System.out.println(test.add("test4"));
	System.out.println(test.add("test3"));
	System.out.println(test.add("test2"));
	System.out.println("=================");
	System.out.println(test.add("test3"));
}
}
