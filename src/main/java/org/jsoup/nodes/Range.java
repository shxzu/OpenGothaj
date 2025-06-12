package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Node;

public class Range {
    private final Position start;
    private final Position end;
    private static final String RangeKey = Attributes.internalKey("jsoup.sourceRange");
    private static final String EndRangeKey = Attributes.internalKey("jsoup.endSourceRange");
    private static final Position UntrackedPos = new Position(-1, -1, -1);
    private static final Range Untracked = new Range(UntrackedPos, UntrackedPos);

    public Range(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position start() {
        return this.start;
    }

    public Position end() {
        return this.end;
    }

    public boolean isTracked() {
        return this != Untracked;
    }

    static Range of(Node node, boolean start) {
        String key;
        String string = key = start ? RangeKey : EndRangeKey;
        if (!node.hasAttr(key)) {
            return Untracked;
        }
        return (Range)Validate.ensureNotNull(node.attributes().getUserData(key));
    }

    public void track(Node node, boolean start) {
        node.attributes().putUserData(start ? RangeKey : EndRangeKey, this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Range range = (Range)o;
        if (!this.start.equals(range.start)) {
            return false;
        }
        return this.end.equals(range.end);
    }

    public int hashCode() {
        int result = this.start.hashCode();
        result = 31 * result + this.end.hashCode();
        return result;
    }

    public String toString() {
        return this.start + "-" + this.end;
    }

    public static class Position {
        private final int pos;
        private final int lineNumber;
        private final int columnNumber;

        public Position(int pos, int lineNumber, int columnNumber) {
            this.pos = pos;
            this.lineNumber = lineNumber;
            this.columnNumber = columnNumber;
        }

        public int pos() {
            return this.pos;
        }

        public int lineNumber() {
            return this.lineNumber;
        }

        public int columnNumber() {
            return this.columnNumber;
        }

        public boolean isTracked() {
            return this != UntrackedPos;
        }

        public String toString() {
            return this.lineNumber + "," + this.columnNumber + ":" + this.pos;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position)o;
            if (this.pos != position.pos) {
                return false;
            }
            if (this.lineNumber != position.lineNumber) {
                return false;
            }
            return this.columnNumber == position.columnNumber;
        }

        public int hashCode() {
            int result = this.pos;
            result = 31 * result + this.lineNumber;
            result = 31 * result + this.columnNumber;
            return result;
        }
    }
}
