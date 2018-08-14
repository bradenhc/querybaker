package com.github.bradenhitchcock.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;
import com.github.bradenhitchcock.querybaker.cond.Condition;

public class Select implements IQueryBuilder {
	
	private Table mTable;
	private boolean mShouldSelectAll;
	private List<Column> mSelectColumns = new ArrayList<>();
	private Condition mCondition = null;
	private List<Column> mOrder = new ArrayList<>();
	
	public Select(Table table) {
		mTable = table;
	}
	
	public static Select from(String table) {
		return new Select(new Table(table));
	}
	
	public static Select from(Table table) {
		return new Select(table);
	}
	
	public Select all() {
		mShouldSelectAll = true;
		return this;
	}
	
	public Select columns(Column ...columns) {
		for(Column c : columns) {
			mSelectColumns.add(c);
		}
		return this;
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("SELECT ");
		if(mShouldSelectAll) {
			sb.append("* ");
		} else {
			boolean first = true;
			for(Column c : mSelectColumns) {
				if(first) {
					first = false;
					sb.append(c.name());
				} else {
					sb.append(", ").append(c.name());
				}
			}
			sb.append(" ");
		}
		sb.append("FROM ").append(mTable.name());
		// TODO: add support for select joins
		if(mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		if(mOrder.size() > 0) {
			sb.append(" ORDER BY ");
			boolean first = true;
			for(Column c: mOrder) {
				if(first) {
					first = false;
					sb.append(c.name());
				} else {
					sb.append(", ").append(c.name());
				}
			}
		}
		return sb.toString();
	}

}
