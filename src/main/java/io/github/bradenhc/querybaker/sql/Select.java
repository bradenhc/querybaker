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

/**
 * Represents a SELECT statement used to query table entry data in a SQL database.
 */
public class Select implements IQueryBuilder {

	private Table mTable;
	private boolean mShouldSelectAll;
	private List<Column> mSelectColumns = new ArrayList<>();
	private Condition mCondition = null;
	private List<Column> mOrder = new ArrayList<>();
	private List<Join> mJoins = new ArrayList<>();

	/**
	 * Creates a new SELECT statement instance on the provided {@link Table}. This constructor is private. Use the
	 * {@link Select#from(Table)} method instead.
	 * 
	 * @param table
	 *        the table to select on
	 */
	private Select(Table table) {
		mTable = table;
	}

	/**
	 * Creates a new SELECT statement instance on the provided {@link Table}.
	 * 
	 * @param table
	 *        the table to select on
	 */
	public static Select from(Table table) {
		return new Select(table);
	}

	// TODO add a select from a select

	/**
	 * Equivalent to a {@code SELECT *} statement in SQL queries. Tells the Select query builder to select on all
	 * columns in the internal table.
	 * 
	 * @return the modified Select instance
	 */
	public Select all() {
		mShouldSelectAll = true;
		return this;
	}

	/**
	 * Specifies which columns in the database table to select on.
	 * 
	 * @param columns
	 *        a parameterized list of columns in the provided {@link Table} to select on
	 * @return the modified Select instance
	 */
	public Select columns(Column... columns) {
		for (Column c : columns) {
			mSelectColumns.add(c);
		}
		return this;
	}

	/**
	 * Adds an INNER JOIN clause to the Select statement. This will join on the provided table under the provided
	 * conditions.
	 * 
	 * @param t
	 *        the table to join on
	 * @param c
	 *        the condition that dictates the join
	 * @return the modified Select object
	 */
	public Select innerJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.INNER, t, c));
		return this;
	}

	/**
	 * Adds a LEFT OUTER JOIN clause to the Select statement. This will join on the provided table under the provided
	 * conditions.
	 * 
	 * @param t
	 *        the table to join on
	 * @param c
	 *        the condition that dictates the join
	 * @return the modified Select object
	 */
	public Select leftJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.LEFT, t, c));
		return this;
	}

	/**
	 * Adds an RIGHT OUTER JOIN clause to the Select statement. This will join on the provided table under the provided
	 * conditions.
	 * 
	 * @param t
	 *        the table to join on
	 * @param c
	 *        the condition that dictates the join
	 * @return the modified Select object
	 */
	public Select rightJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.RIGHT, t, c));
		return this;
	}

	/**
	 * Adds an FULL OUTER JOIN clause to the Select statement. This will join on the provided table under the provided
	 * conditions.
	 * 
	 * @param t
	 *        the table to join on
	 * @param c
	 *        the condition that dictates the join
	 * @return the modified Select object
	 */
	public Select fullJoin(Table t, Condition c) {
		mJoins.add(new Join(SQLJoinType.FULL, t, c));
		return this;
	}

	/**
	 * Adds a WHERE clause to the SELECT statement based on the provided condition
	 * 
	 * @param c
	 *        the condition to filter queried values
	 * @return the modified Select object
	 */
	public Select where(Condition c) {
		mCondition = c;
		return this;
	}

	/**
	 * Adds an ORDER BY clause to the SELECT statement. The order of the provided columns will be reflected in the the
	 * ORDER BY clause.
	 * 
	 * @param columns
	 *        a parameterized list of columns to order the query results by
	 * @return the modified Select object
	 */
	public Select order(Column... columns) {
		for (Column c : columns) {
			mOrder.add(c);
		}
		return this;
	}

	/**
	 * Generates the SELECT statement. See {@link IQueryBuilder#build()} for more information.
	 */
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
