package edu.hw2.task4;

public class StackTraceUtility {
    private final static int CALLING_ELEMENT_INDEX = 2;

    private StackTraceUtility() {
    }

    public static CallingInfo callingInfo() {
        StackTraceElement callingElement = Thread.currentThread().getStackTrace()[CALLING_ELEMENT_INDEX];
        return new CallingInfo(callingElement.getClassName(), callingElement.getMethodName());
    }
}
