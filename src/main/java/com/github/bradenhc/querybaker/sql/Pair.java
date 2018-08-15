package com.github.bradenhc.querybaker.sql;

public class Pair {
	public Column column;
	public Object value;
	
	public Pair(Column c, Object v) {
		column = c;
		value = v;
	}
}
