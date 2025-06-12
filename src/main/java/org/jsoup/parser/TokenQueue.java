package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

public class TokenQueue {
    private String queue;
    private int pos = 0;
    private static final char ESC = '\\';

    public TokenQueue(String data) {
        Validate.notNull(data);
        this.queue = data;
    }

    public boolean isEmpty() {
        return this.remainingLength() == 0;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    public void addFirst(String seq) {
        this.queue = seq + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public boolean matches(String seq) {
        return this.queue.regionMatches(true, this.pos, seq, 0, seq.length());
    }

    public boolean matchesAny(String ... seq) {
        for (String s : seq) {
            if (!this.matches(s)) continue;
            return true;
        }
        return false;
    }

    public boolean matchesAny(char ... seq) {
        if (this.isEmpty()) {
            return false;
        }
        for (char c : seq) {
            if (this.queue.charAt(this.pos) != c) continue;
            return true;
        }
        return false;
    }

    public boolean matchChomp(String seq) {
        if (this.matches(seq)) {
            this.pos += seq.length();
            return true;
        }
        return false;
    }

    public boolean matchesWhitespace() {
        return !this.isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }

    public boolean matchesWord() {
        return !this.isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }

    public void advance() {
        if (!this.isEmpty()) {
            ++this.pos;
        }
    }

    public char consume() {
        return this.queue.charAt(this.pos++);
    }

    public void consume(String seq) {
        if (!this.matches(seq)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
        int len = seq.length();
        if (len > this.remainingLength()) {
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        this.pos += len;
    }

    public String consumeTo(String seq) {
        int offset = this.queue.indexOf(seq, this.pos);
        if (offset != -1) {
            String consumed = this.queue.substring(this.pos, offset);
            this.pos += consumed.length();
            return consumed;
        }
        return this.remainder();
    }

    public String consumeToIgnoreCase(String seq) {
        int start = this.pos;
        String first = seq.substring(0, 1);
        boolean canScan = first.toLowerCase().equals(first.toUpperCase());
        while (!this.isEmpty() && !this.matches(seq)) {
            if (canScan) {
                int skip = this.queue.indexOf(first, this.pos) - this.pos;
                if (skip == 0) {
                    ++this.pos;
                    continue;
                }
                if (skip < 0) {
                    this.pos = this.queue.length();
                    continue;
                }
                this.pos += skip;
                continue;
            }
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeToAny(String ... seq) {
        int start = this.pos;
        while (!this.isEmpty() && !this.matchesAny(seq)) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }

    public String chompTo(String seq) {
        String data = this.consumeTo(seq);
        this.matchChomp(seq);
        return data;
    }

    public String chompToIgnoreCase(String seq) {
        String data = this.consumeToIgnoreCase(seq);
        this.matchChomp(seq);
        return data;
    }

    public String chompBalanced(char open, char close) {
        String out;
        int start = -1;
        int end = -1;
        int depth = 0;
        int last = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean inRegexQE = false;
        while (!this.isEmpty()) {
            block15: {
                char c;
                block16: {
                    block13: {
                        block14: {
                            c = this.consume();
                            if (last == 92) break block13;
                            if (c == '\'' && c != open && !inDoubleQuote) {
                                inSingleQuote = !inSingleQuote;
                            } else if (c == '\"' && c != open && !inSingleQuote) {
                                boolean bl = inDoubleQuote = !inDoubleQuote;
                            }
                            if (!inSingleQuote && !inDoubleQuote && !inRegexQE) break block14;
                            last = c;
                            break block15;
                        }
                        if (c == open) {
                            ++depth;
                            if (start == -1) {
                                start = this.pos;
                            }
                        } else if (c == close) {
                            --depth;
                        }
                        break block16;
                    }
                    if (c == 'Q') {
                        inRegexQE = true;
                    } else if (c == 'E') {
                        inRegexQE = false;
                    }
                }
                if (depth > 0 && last != 0) {
                    end = this.pos;
                }
                last = c;
            }
            if (depth > 0) continue;
        }
        String string = out = end >= 0 ? this.queue.substring(start, end) : "";
        if (depth > 0) {
            Validate.fail("Did not find balanced marker at '" + out + "'");
        }
        return out;
    }

    public static String unescape(String in) {
        StringBuilder out = StringUtil.borrowBuilder();
        char last = '\u0000';
        for (char c : in.toCharArray()) {
            if (c == '\\') {
                if (last == '\\') {
                    out.append(c);
                }
            } else {
                out.append(c);
            }
            last = c;
        }
        return StringUtil.releaseBuilder(out);
    }

    public boolean consumeWhitespace() {
        boolean seen = false;
        while (this.matchesWhitespace()) {
            ++this.pos;
            seen = true;
        }
        return seen;
    }

    public String consumeWord() {
        int start = this.pos;
        while (this.matchesWord()) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeElementSelector() {
        int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny("*|", "|", "_", "-"))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeCssIdentifier() {
        int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('-', '_'))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }

    public String remainder() {
        String remainder = this.queue.substring(this.pos);
        this.pos = this.queue.length();
        return remainder;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }
}
