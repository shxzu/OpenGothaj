package org.jsoup.helper;

import java.util.ArrayList;
import org.jsoup.helper.Validate;

public class ValidationException
extends IllegalArgumentException {
    public static final String Validator = Validate.class.getName();

    public ValidationException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        super.fillInStackTrace();
        StackTraceElement[] stackTrace = this.getStackTrace();
        ArrayList<StackTraceElement> filteredTrace = new ArrayList<StackTraceElement>();
        for (StackTraceElement trace : stackTrace) {
            if (trace.getClassName().equals(Validator)) continue;
            filteredTrace.add(trace);
        }
        this.setStackTrace(filteredTrace.toArray(new StackTraceElement[0]));
        return this;
    }
}
