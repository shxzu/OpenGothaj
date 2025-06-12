package org.jsoup.internal;

import java.util.Stack;

class StringUtil$1
extends ThreadLocal<Stack<StringBuilder>> {
    StringUtil$1() {
    }

    @Override
    protected Stack<StringBuilder> initialValue() {
        return new Stack<StringBuilder>();
    }
}
