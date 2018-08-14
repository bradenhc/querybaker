package com.github.bradenhitchcock.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;
import com.github.bradenhitchcock.querybaker.util.SQLFormatter;

public class Insert implements IQueryBuilder {
	
	private Table mTable;
	private List<Column> mColumns = new ArrayList<>();
	private List<Object> mValues = new ArrayList<>();
	
	public Insert(Table table) {
		mTable = table;
	}
	
	public static Insert into(String table) {
		return new Insert(new Table(table));
	}
	
	public static Insert into(Table table) {
		return new Insert(table);
	}
	
	public Insert values(Pair ...pairs) {
		for(Pair p : pairs) {
			mColumns.add(p.column);
			mValues.add(p.value);
		}
		return this;
	}
	
	public static Pair pair(Column c, Object v) {
		return new Pair(c, v);
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(mTable.name()).append(" ");
		sb.append("(");
		boolean first = true;
		for(Column c : mColumns) {
			if(first) {
				sb.append(c.name());
				first = false;
			} else {
				sb.append(", ").append(c.name());
			}
		}
		sb.append(") ");
		sb.append("VALUES (");
		first = true;
		for(Object v : mValues) {
			String s = SQLFormatter.format(v);
			if(first) {
				sb.append(s);
				first = false;
			} else {
				sb.append(", ").append(s);
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
}
