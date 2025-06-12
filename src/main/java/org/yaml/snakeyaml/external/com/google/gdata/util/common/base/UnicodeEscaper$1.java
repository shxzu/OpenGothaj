package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.IOException;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.UnicodeEscaper;

class UnicodeEscaper$1
implements Appendable {
    int pendingHighSurrogate = -1;
    final char[] decodedChars = new char[2];
    final Appendable val$out;

    UnicodeEscaper$1(Appendable appendable) {
        this.val$out = appendable;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        return this.append(csq, 0, csq.length());
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        int index = start;
        if (index < end) {
            char[] escaped;
            int unescapedChunkStart = index;
            if (this.pendingHighSurrogate != -1) {
                char c;
                if (!Character.isLowSurrogate(c = csq.charAt(index++))) {
                    throw new IllegalArgumentException("Expected low surrogate character but got " + c);
                }
                escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                if (escaped != null) {
                    this.outputChars(escaped, escaped.length);
                    ++unescapedChunkStart;
                } else {
                    this.val$out.append((char)this.pendingHighSurrogate);
                }
                this.pendingHighSurrogate = -1;
            }
            while (true) {
                if ((index = UnicodeEscaper.this.nextEscapeIndex(csq, index, end)) > unescapedChunkStart) {
                    this.val$out.append(csq, unescapedChunkStart, index);
                }
                if (index == end) break;
                int cp = UnicodeEscaper.codePointAt(csq, index, end);
                if (cp < 0) {
                    this.pendingHighSurrogate = -cp;
                    break;
                }
                escaped = UnicodeEscaper.this.escape(cp);
                if (escaped != null) {
                    this.outputChars(escaped, escaped.length);
                } else {
                    int len = Character.toChars(cp, this.decodedChars, 0);
                    this.outputChars(this.decodedChars, len);
                }
                unescapedChunkStart = index += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
            }
        }
        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        if (this.pendingHighSurrogate != -1) {
            if (!Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
            }
            char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
            if (escaped != null) {
                this.outputChars(escaped, escaped.length);
            } else {
                this.val$out.append((char)this.pendingHighSurrogate);
                this.val$out.append(c);
            }
            this.pendingHighSurrogate = -1;
        } else if (Character.isHighSurrogate(c)) {
            this.pendingHighSurrogate = c;
        } else {
            if (Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
            }
            char[] escaped = UnicodeEscaper.this.escape(c);
            if (escaped != null) {
                this.outputChars(escaped, escaped.length);
            } else {
                this.val$out.append(c);
            }
        }
        return this;
    }

    private void outputChars(char[] chars, int len) throws IOException {
        for (int n = 0; n < len; ++n) {
            this.val$out.append(chars[n]);
        }
    }
}
