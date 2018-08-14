package com.github.bradenhitchcock.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;
import com.github.bradenhitchcock.querybaker.cond.Condition;
import com.github.bradenhitchcock.querybaker.util.SQLFormatter;

public class Update implements IQueryBuilder {

	private Table mTable;
	private List<Pair> mUpdates = new ArrayList<>();
	private Condition mCondition;
	
	public Update(Table table) {
		mTable = table;
	}
	
	public static Update table(String name) {
		return new Update(new Table(name));
	}
	
	public static Update table(Table table) {
		return new Update(table);
	}
	
	public Update values(Pair ...pairs) {
		for(Pair p : pairs) {
			mUpdates.add(p);
		}
		return this;
	}
	
	public Update where(Condition c) {
		mCondition = c;
		return this;
	}
	
	public static Pair pair(Column c, Object v) {
		return new Pair(c,v);
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(mTable.name()).append(" ");
		sb.append("SET ");
		boolean first = true;
		for(Pair p : mUpdates) {
			if(first) {
				first = false;
				sb.append(p.column.name()).append(" = ").append(SQLFormatter.format(p.value));
			} else {
				sb.append(", ").append(p.column.name()).append(" = ").append(SQLFormatter.format(p.value));
			}
		}
		if(mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		return sb.toString();
	}

}
