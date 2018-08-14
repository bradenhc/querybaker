package com.github.bradenhitchcock.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;

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
	
	public Insert values(InsertPair ...pairs) {
		for(InsertPair p : pairs) {
			mColumns.add(p.column);
			mValues.add(p.value);
		}
		return this;
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
			if(first) {
				sb.append(v);
				first = false;
			} else {
				sb.append(", ").append(v);
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public class InsertPair {
		public Column column;
		public Object value;
		
		public InsertPair(Column c, Object v) {
			column = c;
			value = v;
		}
	}
	
}
