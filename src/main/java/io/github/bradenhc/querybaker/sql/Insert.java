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
import io.github.bradenhc.querybaker.util.Formatter;

/**
 * Represents an INSERT INTO statement for SQL databases.
 */
public class Insert implements IQueryBuilder {

	private Table mTable;
	private List<Column> mColumns = new ArrayList<>();
	private List<Object> mValues = new ArrayList<>();

	/**
	 * Creates a new INSERT INTO statement using the provided {@link Table} object. This constructor is private. Use the
	 * static {@link Insert#into(Table)} method instead.
	 * 
	 * @param table
	 *        the Table object instance to use
	 */
	private Insert(Table table) {
		mTable = table;
	}

	/**
	 * Creates a new INSERT INTO statement using the provided {@link Table} object.
	 * 
	 * @param table
	 *        the Table object instance to use
	 * @return a new Insert instance used to build and create a INSERT INTO query
	 */
	public static Insert into(Table table) {
		return new Insert(table);
	}

	/**
	 * Specifies the values that should be inserted into the table. The values in this method are passed using
	 * {@link Pair} objects that contain the column and the value that should be inserted into that column.
	 * 
	 * @param pairs
	 *        any number of Pair objects passed as individual parameters to the function
	 * @return the modified Insert instance
	 */
	public Insert values(Pair... pairs) {
		for (Pair p : pairs) {
			mColumns.add(p.column);
			mValues.add(p.value);
		}
		return this;
	}

	/**
	 * Creates a new {@link Pair} object given a {@link Column} and an Object value. The resulting object can be used in
	 * the {@link Insert#values(Pair...)} method.
	 * 
	 * @param c
	 *        the column
	 * @param v
	 *        the value
	 * @return a new Pair instance containing the column and value
	 */
	public static Pair pair(Column c, Object v) {
		return new Pair(c, v);
	}

	/**
	 * Generates the INSERT INTO statement. See {@link IQueryBuilder#build()} for more information.
	 */
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(mTable.name()).append(" ");
		sb.append("(");
		boolean first = true;
		for (Column c : mColumns) {
			if (first) {
				sb.append(c.name());
				first = false;
			} else {
				sb.append(", ").append(c.name());
			}
		}
		sb.append(") ");
		sb.append("VALUES (");
		first = true;
		for (Object v : mValues) {
			String s = Formatter.format(v);
			if (first) {
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
