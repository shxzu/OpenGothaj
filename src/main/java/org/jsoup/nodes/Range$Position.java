package org.jsoup.nodes;

public class Range$Position {
    private final int pos;
    private final int lineNumber;
    private final int columnNumber;

    public Range$Position(int pos, int lineNumber, int columnNumber) {
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
        Range$Position position = (Range$Position)o;
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
