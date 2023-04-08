package com.example.mock.sample;

public class SampleClass {
    private SampleSubClass sampleSubClass;

    public SampleClass() {
        sampleSubClass = new SampleSubClass();
    }

    public int getSubX() {
        return sampleSubClass.getX();
    }

    public String getSubMessage() {
        return sampleSubClass.getMessage();
    }

    public String getSubMessageWithPrefix(String prefix) {
        return sampleSubClass.concatMessage(prefix);
    }

    public int calc(int a, int b) {
        int result;
        result = sampleSubClass.calcAdd(a, b);

        return result;
    }

    public int calcUsingStaticMethod(int a, int b) {
        int result;

        result = SampleStaticMethods.calcAdd(a, b);

        return result;
    }

    public int calcDiv(int a, int b) {
        int result = 0;
        try {
            result = sampleSubClass.calcDiv(a, b);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

}
