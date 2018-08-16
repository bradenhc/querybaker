/*
 * Copyright 2018 Braden Hitchcock
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.bradenhc.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import io.github.bradenhc.querybaker.api.IQueryBuilder;
import io.github.bradenhc.querybaker.cond.Condition;
import io.github.bradenhc.querybaker.sql.Join.SQLJoinType;

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
