package com.github.bradenhitchcock.querybaker.util;

import java.sql.Date;

import com.github.bradenhitchcock.querybaker.cond.Condition.Symbol;
import com.github.bradenhitchcock.querybaker.sql.Column;

public class SQLFormatter {
	public static String format(Object o) {
		if (o instanceof Column) {
			return ((Column) o).name();
		} else if (o instanceof Symbol) {
			return ((Symbol) o).toString();
		} else if (o instanceof String) {
			// TODO: This one is a little more complicated. We need to clean the string
			// before we append it so that
			// we don't have issues inserting it into the SQL table
			String s = (String) o;
			return "\"" + s + "\"";
		} else if (o instanceof Date) {
			// TODO When an SQL date object is passed in, we need to format it appropriately
			Date d = (Date) o;
			return d.toString();
		} else {
			return o.toString();
		}
	}
}
