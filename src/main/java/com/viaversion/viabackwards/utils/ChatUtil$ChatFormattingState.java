package com.viaversion.viabackwards.utils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class ChatUtil$ChatFormattingState {
    private final Set<Character> formatting;
    private final char defaultColor;
    private char color;

    private ChatUtil$ChatFormattingState(char defaultColor) {
        this(new HashSet<Character>(), defaultColor, defaultColor);
    }

    public ChatUtil$ChatFormattingState(Set<Character> formatting, char defaultColor, char color) {
        this.formatting = formatting;
        this.defaultColor = defaultColor;
        this.color = color;
    }

    private void setColor(char newColor) {
        this.formatting.clear();
        this.color = newColor;
    }

    public ChatUtil$ChatFormattingState copy() {
        return new ChatUtil$ChatFormattingState(new HashSet<Character>(this.formatting), this.defaultColor, this.color);
    }

    public void appendTo(StringBuilder builder) {
        builder.append('§').append(this.color);
        for (Character formatCharacter : this.formatting) {
            builder.append('§').append(formatCharacter);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ChatUtil$ChatFormattingState that = (ChatUtil$ChatFormattingState)o;
        return this.defaultColor == that.defaultColor && this.color == that.color && Objects.equals(this.formatting, that.formatting);
    }

    public int hashCode() {
        return Objects.hash(this.formatting, Character.valueOf(this.defaultColor), Character.valueOf(this.color));
    }

    public void processNextControlChar(char controlChar) {
        if (controlChar == 'r') {
            this.setColor(this.defaultColor);
            return;
        }
        if (controlChar == 'l' || controlChar == 'm' || controlChar == 'n' || controlChar == 'o') {
            this.formatting.add(Character.valueOf(controlChar));
            return;
        }
        this.setColor(controlChar);
    }
}
