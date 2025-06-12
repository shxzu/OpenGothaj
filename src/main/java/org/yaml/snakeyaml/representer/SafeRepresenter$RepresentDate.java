package org.yaml.snakeyaml.representer;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentDate
implements Represent {
    protected SafeRepresenter$RepresentDate() {
    }

    @Override
    public Node representData(Object data) {
        int gmtOffset;
        Calendar calendar;
        if (data instanceof Calendar) {
            calendar = (Calendar)data;
        } else {
            calendar = Calendar.getInstance(SafeRepresenter.this.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : SafeRepresenter.this.timeZone);
            calendar.setTime((Date)data);
        }
        int years = calendar.get(1);
        int months = calendar.get(2) + 1;
        int days = calendar.get(5);
        int hour24 = calendar.get(11);
        int minutes = calendar.get(12);
        int seconds = calendar.get(13);
        int millis = calendar.get(14);
        StringBuilder buffer = new StringBuilder(String.valueOf(years));
        while (buffer.length() < 4) {
            buffer.insert(0, "0");
        }
        buffer.append("-");
        if (months < 10) {
            buffer.append("0");
        }
        buffer.append(months);
        buffer.append("-");
        if (days < 10) {
            buffer.append("0");
        }
        buffer.append(days);
        buffer.append("T");
        if (hour24 < 10) {
            buffer.append("0");
        }
        buffer.append(hour24);
        buffer.append(":");
        if (minutes < 10) {
            buffer.append("0");
        }
        buffer.append(minutes);
        buffer.append(":");
        if (seconds < 10) {
            buffer.append("0");
        }
        buffer.append(seconds);
        if (millis > 0) {
            if (millis < 10) {
                buffer.append(".00");
            } else if (millis < 100) {
                buffer.append(".0");
            } else {
                buffer.append(".");
            }
            buffer.append(millis);
        }
        if ((gmtOffset = calendar.getTimeZone().getOffset(calendar.getTime().getTime())) == 0) {
            buffer.append('Z');
        } else {
            if (gmtOffset < 0) {
                buffer.append('-');
                gmtOffset *= -1;
            } else {
                buffer.append('+');
            }
            int minutesOffset = gmtOffset / 60000;
            int hoursOffset = minutesOffset / 60;
            int partOfHour = minutesOffset % 60;
            if (hoursOffset < 10) {
                buffer.append('0');
            }
            buffer.append(hoursOffset);
            buffer.append(':');
            if (partOfHour < 10) {
                buffer.append('0');
            }
            buffer.append(partOfHour);
        }
        return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), DumperOptions.ScalarStyle.PLAIN);
    }
}
