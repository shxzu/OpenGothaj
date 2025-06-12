package org.yaml.snakeyaml.constructor;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlTimestamp
extends AbstractConstruct {
    private Calendar calendar;

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override
    public Object construct(Node node) {
        TimeZone timeZone;
        ScalarNode scalar = (ScalarNode)node;
        String nodeValue = scalar.getValue();
        Matcher match = YMD_REGEXP.matcher(nodeValue);
        if (match.matches()) {
            String year_s = match.group(1);
            String month_s = match.group(2);
            String day_s = match.group(3);
            this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            this.calendar.clear();
            this.calendar.set(1, Integer.parseInt(year_s));
            this.calendar.set(2, Integer.parseInt(month_s) - 1);
            this.calendar.set(5, Integer.parseInt(day_s));
            return this.calendar.getTime();
        }
        match = TIMESTAMP_REGEXP.matcher(nodeValue);
        if (!match.matches()) {
            throw new YAMLException("Unexpected timestamp: " + nodeValue);
        }
        String year_s = match.group(1);
        String month_s = match.group(2);
        String day_s = match.group(3);
        String hour_s = match.group(4);
        String min_s = match.group(5);
        String seconds = match.group(6);
        String millis = match.group(7);
        if (millis != null) {
            seconds = seconds + "." + millis;
        }
        double fractions = Double.parseDouble(seconds);
        int sec_s = (int)Math.round(Math.floor(fractions));
        int usec = (int)Math.round((fractions - (double)sec_s) * 1000.0);
        String timezoneh_s = match.group(8);
        String timezonem_s = match.group(9);
        if (timezoneh_s != null) {
            String time = timezonem_s != null ? ":" + timezonem_s : "00";
            timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
        } else {
            timeZone = TimeZone.getTimeZone("UTC");
        }
        this.calendar = Calendar.getInstance(timeZone);
        this.calendar.set(1, Integer.parseInt(year_s));
        this.calendar.set(2, Integer.parseInt(month_s) - 1);
        this.calendar.set(5, Integer.parseInt(day_s));
        this.calendar.set(11, Integer.parseInt(hour_s));
        this.calendar.set(12, Integer.parseInt(min_s));
        this.calendar.set(13, sec_s);
        this.calendar.set(14, usec);
        return this.calendar.getTime();
    }
}
