package org.jsoup.safety;

import org.jsoup.helper.Validate;

abstract class Safelist$TypedValue {
    private final String value;

    Safelist$TypedValue(String value) {
        Validate.notNull(value);
        this.value = value;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Safelist$TypedValue other = (Safelist$TypedValue)obj;
        if (this.value == null) {
            return other.value == null;
        }
        return this.value.equals(other.value);
    }

    public String toString() {
        return this.value;
    }
}
