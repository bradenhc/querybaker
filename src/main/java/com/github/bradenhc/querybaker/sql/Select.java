package com.github.bradenhc.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhc.querybaker.api.IQueryBuilder;
import com.github.bradenhc.querybaker.cond.Condition;
import com.github.bradenhc.querybaker.sql.Join.SQLJoinType;

public class Select implements IQueryBuilder {

	private Table mTable;
	private boolean mShouldSelectAll;
	private List<Column> mSelectColumns = new ArrayList<>();
	private Condition mCondition = null;
	private List<Column> mOrder = new ArrayList<>();
	private List<Join> mJoins = new ArrayList<>();

	public Select(Table table) {
		mTable = table;
	}

	public static Select from(String table) {
		return new Select(new Table(table));
	}

	public static Select from(Table table) {
		return new Select(table);
	}

	// TODO add a select from a select

	public Select all() {
		mShouldSelectAll = true;
		return this;
	}

	public Select columns(Column... columns) {
		for (Column c : columns) {
			mSelectColumns.add(c);
		}
		return this;
	}

	public Select innerJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.INNER, t, c));
		return this;
	}

	public Select leftJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.LEFT, t, c));
		return this;
	}

	public Select rightJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.RIGHT, t, c));
		return this;
	}

	public Select fullJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.FULL, t, c));
		return this;
	}

	public Select where(Condition c) {
		mCondition = c;
		return this;
	}

	public Select order(Column... columns) {
		for (Column c : columns) {
			mOrder.add(c);
		}
		return this;
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("SELECT ");
		if (mShouldSelectAll) {
			String s = mTable.alias() != null ? mTable.alias() + ".* " : "* ";
			sb.append(s);
		} else {
			boolean first = true;
			for (Column c : mSelectColumns) {
				String name = mTable.alias() != null ? c.alias() : c.name();
				if (first) {
					first = false;
					sb.append(name);
				} else {
					sb.append(", ").append(name);
				}
			}
			sb.append(" ");
		}
		sb.append("FROM ").append(mTable.name());
		if (mTable.alias() != null) {
			sb.append(" ").append(mTable.alias());
		}
		if (mJoins.size() > 0) {
			for (Join j : mJoins) {
				sb.append(" ").append(j);
			}
		}
		if (mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		if (mOrder.size() > 0) {
			sb.append(" ORDER BY ");
			boolean first = true;
			for (Column c : mOrder) {
				String name = mTable.alias() != null ? c.alias() : c.name();
				if (first) {
					first = false;
					sb.append(name);
				} else {
					sb.append(", ").append(name);
				}
			}
		}
		return sb.toString();
	}

}
