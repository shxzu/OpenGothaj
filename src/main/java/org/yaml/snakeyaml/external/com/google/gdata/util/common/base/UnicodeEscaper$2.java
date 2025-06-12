package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

class UnicodeEscaper$2
extends ThreadLocal<char[]> {
    UnicodeEscaper$2() {
    }

    @Override
    protected char[] initialValue() {
        return new char[1024];
    }
}
