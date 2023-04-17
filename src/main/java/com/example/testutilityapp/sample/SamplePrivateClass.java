package com.example.testutilityapp.sample;

public class SamplePrivateClass {

    private static String staticField;

    private String instanceField;

    public static void clear() {
        staticField = null;
    }

    @SuppressWarnings("unused")
    private static String invokeStaticMethod(String arg) {
        return staticField + ":" + arg;
    }

    @SuppressWarnings("unused")
    private String invokeInstanceMethod(String arg) {
        return this.instanceField + ":" + arg;
    }

}
