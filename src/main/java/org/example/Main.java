package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Local chat console application V1.0.0");
        var message = "-u 56 mess sgseg";
        System.out.println(Arrays.toString(message.split(" ", 3)));

        var input = "set sc 5";
        var filteredInput = input.trim().split(" ", 3);
        System.out.println(Arrays.toString(filteredInput));
    }
}