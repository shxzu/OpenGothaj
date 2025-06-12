package org.jsoup.internal;

import javax.annotation.Nullable;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

public class StringUtil$StringJoiner {
    @Nullable
    StringBuilder sb = StringUtil.borrowBuilder();
    final String separator;
    boolean first = true;

    public StringUtil$StringJoiner(String separator) {
        this.separator = separator;
    }

    public StringUtil$StringJoiner add(Object stringy) {
        Validate.notNull(this.sb);
        if (!this.first) {
            this.sb.append(this.separator);
        }
        this.sb.append(stringy);
        this.first = false;
        return this;
    }

    public StringUtil$StringJoiner append(Object stringy) {
        Validate.notNull(this.sb);
        this.sb.append(stringy);
        return this;
    }

    public String complete() {
        String string = StringUtil.releaseBuilder(this.sb);
        this.sb = null;
        return string;
    }
}
