package com.example.mock.sample;

public class SampleSubClass {

    private int x;

    private String message = "MESSAGE";

    public String getMessage() {
        return message;
    }

    public String concatMessage(String prefix) {
        return prefix + this.message;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public int calcAdd(int x, int y) {
        return x + y;
    }

    public int calcSub(int x, int y) {
        return x - y;
    }

    public int calcDiv(int x, int y) throws Exception {
        if (y == 0) {
            throw new Exception("Divided by 0");
        }
        return x / y;
    }
}
