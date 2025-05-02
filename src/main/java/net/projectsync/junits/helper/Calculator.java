package net.projectsync.junits.helper;

public class Calculator {

    private static boolean isUpperCase = false;

    int x;
    int y;

    int z;
    public Calculator() {

    }

    public int add(int x, int y) {
        return x + y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    public int division(int x, int y) {
        return x / y;
    }

    private String privateMethod(String str) {

        return (isUpperCase) ?
                str.toUpperCase().substring(0, 3) :
                str.toLowerCase().substring(0, 3);
    }

    public static String getString() {
        return "Original string";
    }
}
