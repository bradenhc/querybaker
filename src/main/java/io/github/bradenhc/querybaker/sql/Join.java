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

import io.github.bradenhc.querybaker.cond.Condition;

/**
 * Represents an SQL JOIN statement. The resulting statement makes up part of various other SQL statements.The available
 * joins are given by the {@link SQLJoinType} enum.
 */
public class Join {

	private SQLJoinType mType;
	private Table mJoinTable;
	private Condition mCondition;

	/**
	 * Create a new JOIN object instance. The instance will be a join of the provided {@link SQLJoinType type} and will
	 * join the provided {@link Table table} on the provided {@link Condition condition}.
	 * 
	 * @param type
	 *        the type of join to create
	 * @param joinTable
	 *        the table to join on
	 * @param condition
	 *        the condition that dictates the join
	 */
	public Join(SQLJoinType type, Table joinTable, Condition condition) {
		mType = type;
		mJoinTable = joinTable;
		mCondition = condition;
	}

	/**
	 * Enumeration that represents the valid types of SQL joins that can be used in SQL query statements.
	 */
	enum SQLJoinType {
		INNER, LEFT, RIGHT, FULL
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		switch (mType) {
		case INNER:
			sb.append("INNER JOIN ");
			break;
		case LEFT:
			sb.append("LEFT OUTER JOIN ");
			break;
		case RIGHT:
			sb.append("RIGHT OUTER JOIN");
			break;
		case FULL:
			sb.append("FULL OUTER JOIN");
		default:
			sb.append("INNER JOIN ");
		}
		String joinTableName = mJoinTable.alias() != null ? mJoinTable.name() + " " + mJoinTable.alias()
				: mJoinTable.name();
		sb.append(joinTableName).append(" ON ");
		sb.append(mCondition.toString());
		return sb.toString();
	}
}
