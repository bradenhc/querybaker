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
import io.github.bradenhc.querybaker.util.Formatter;

public class Update implements IQueryBuilder {

	private Table mTable;
	private List<Pair> mUpdates = new ArrayList<>();
	private Condition mCondition;

	/**
	 * This constructor is private. See {@link Update#table(Table)} instead.
	 * 
	 * @param table
	 */
	private Update(Table table) {
		mTable = table;
	}

	/**
	 * Creates a new Update query builder that will act on the provided {@link Table} object.
	 * 
	 * @param table
	 *        the table to use to build the UPDATE query
	 * @return a new Update query builder
	 */
	public static Update table(Table table) {
		return new Update(table);
	}

	/**
	 * Adds column/value {@link Pair pairs} to the list of updates that will be used to build the UPDATE query.
	 * 
	 * @param pairs
	 *        a parameterized list of pairs to add to the update
	 * @return the modified Update query builder instance
	 */
	public Update values(Pair... pairs) {
		for (Pair p : pairs) {
			mUpdates.add(p);
		}
		return this;
	}

	/**
	 * Adds a conditional expression to the UPDATE statement.
	 * 
	 * @param c
	 *        the conditional expression to add
	 * @return the modified Update instance
	 */
	public Update where(Condition c) {
		mCondition = c;
		return this;
	}

	/**
	 * Creates a new {@link Pair} object that can be used to add to the list of updates for the Update query builder.
	 * 
	 * @param c
	 *        the column
	 * @param v
	 *        the value
	 * @return a new Pair object
	 */
	public static Pair pair(Column c, Object v) {
		return new Pair(c, v);
	}

	/**
	 * Generates the UPDATE query. See {@link IQueryBuilder#build()} for more information.
	 */
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(mTable.name()).append(" ");
		sb.append("SET ");
		boolean first = true;
		for (Pair p : mUpdates) {
			if (first) {
				first = false;
				sb.append(p.column.name()).append(" = ").append(Formatter.format(p.value));
			} else {
				sb.append(", ").append(p.column.name()).append(" = ").append(Formatter.format(p.value));
			}
		}
		if (mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		return sb.toString();
	}

}
