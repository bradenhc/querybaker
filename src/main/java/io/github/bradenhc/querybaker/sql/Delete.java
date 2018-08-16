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

import io.github.bradenhc.querybaker.api.IQueryBuilder;
import io.github.bradenhc.querybaker.cond.Condition;

/**
 * Represents a DELETE FROM statement for an SQL database query.
 */
public class Delete implements IQueryBuilder {

	private Table mTable;
	private Condition mCondition = null;

	/**
	 * Creates a new instane of a DELETE FROM statement using the provided {@link Table}
	 * 
	 * @param table
	 *        the Table instance to use to build the DELETE FROM statement
	 */
	private Delete(Table table) {
		mTable = table;
	}

	/**
	 * Specifies the {@link Table} instance to use when building the query to delete entries from.
	 * 
	 * @param table
	 *        the Table instance to use to build the query
	 * @return a Delete query builder object implementation that can be used to generate a DELETE FROM SQL statement
	 */
	public static Delete from(Table table) {
		return new Delete(table);
	}

	/**
	 * Adds a condition clause represented by a {@link Condition} object to the DELETE FROM statement.
	 * 
	 * @param c
	 *        the condition to add to the statement
	 * @return the modified DELETE FROM statement query builder object
	 */
	public Delete where(Condition c) {
		mCondition = c;
		return this;
	}

	/**
	 * Creates the DELETE FROM statement. See {@link IQueryBuilder#build()} more information about this method.
	 */
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("DELETE FROM ");
		String name = mTable.alias() != null ? mTable.name() + " " + mTable.alias() : mTable.name();
		sb.append(name);
		if (mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		return sb.toString();
	}

}
