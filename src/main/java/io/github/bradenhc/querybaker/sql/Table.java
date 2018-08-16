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

/**
 * Represents a table in a SQL database.
 */
public class Table implements IQueryBuilder {

	private String mName;
	private String mAlias;
	private List<Column> mColumns = new ArrayList<>();

	/**
	 * Constructor is private. See {@link Table#create(String, List)} instead.
	 * 
	 * @param name
	 * @param columns
	 */
	private Table(String name, List<Column> columns) {
		this(name);
		mColumns = columns;
	}

	/**
	 * Constructor is private. See {@link Table#create(String)} instead.
	 * 
	 * @param name
	 */
	private Table(String name) {
		mName = name;
	}

	/**
	 * Creates a new Table instance with the provided name.
	 * 
	 * @param name
	 *        the name of the table
	 * @return a new Table instance with the provide name
	 */
	public static Table create(String name) {
		return new Table(name);
	}

	/**
	 * Creates a new table instance with the provided name and columns.
	 * 
	 * @param name
	 *        the name of the table
	 * @param columns
	 *        a list of columns to add to the table
	 * @return a new Table instance with the provided name and columns
	 */
	public static Table create(String name, List<Column> columns) {
		return new Table(name, columns);
	}

	/**
	 * Assigns the provided alias to the table.
	 * 
	 * @param alias
	 *        the alias to assign to the table
	 * @return the modified Table object
	 */
	public Table alias(String alias) {
		if (mAlias != null) {
			// We are resetting/removing the alias. Remove the alias from all the column
			// names
			for (Column c : mColumns) {
				c.alias(null);
			}
		}
		if (alias != null) {
			// We are setting the alias for the first time
			for (Column c : mColumns) {
				c.alias(alias);
			}
		}
		mAlias = alias;
		return this;
	}

	public String alias() {
		return mAlias;
	}

	/**
	 * Creates a new {@link Select} query builder instance that will generate a SELECT statement for this table.
	 * 
	 * @return a new Select query builder object
	 */
	public Select select() {
		return Select.from(this);
	}

	/**
	 * Creates a new {@link Insert} query builder instance that will generate a INSERT INTO statement for this table.
	 * 
	 * @return a new Insert query builder object
	 */
	public Insert insert() {
		return Insert.into(this);
	}

	/**
	 * Creates a new {@link Update} query builder instance that will generate a UPDATE statement for this table.
	 * 
	 * @return a new Update query builder object
	 */
	public Update update() {
		return Update.table(this);
	}

	/**
	 * Creates a new {@link Delete} query builder instance that will generate a DELETE FROM statement for this table.
	 * 
	 * @return a new Delete query builder object
	 */
	public Delete delete() {
		return Delete.from(this);
	}

	/**
	 * Generates the string query that can be used to drop this table, as in a DROP TABLE statement.
	 * 
	 * @return a DROP TABLE string for this table
	 */
	public String drop() {
		return "DROP TABLE " + mName;
	}

	/**
	 * Generates the string query that can be used to truncate this table, as in a TRUNCATE TABLE statement.
	 * 
	 * @return a TRUNCATE TABLE string for this table
	 */
	public String truncate() {
		return "TRUNCATE TABLE " + mName;
	}

	public List<Column> columns() {
		return mColumns;
	}

	/**
	 * Adds the paremterized list of columns to the table
	 * 
	 * @param columns
	 *        a parameterized list of columns to add to the table
	 * @return the modified Table instance
	 */
	public Table columns(Column... columns) {
		for (Column c : columns) {
			if (mAlias != null) {
				c.alias(mAlias);
			}
			mColumns.add(c);
		}
		return this;
	}

	/**
	 * Sets the name of the table
	 * 
	 * @param name
	 *        the String representing the name of the table
	 * @return the modified Table instance
	 */
	public Table name(String name) {
		mName = name;
		return this;
	}

	public String name() {
		return mName;
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("CREATE TABLE ");
		sb.append(mName).append(" (");
		boolean first = true;
		for (Column c : mColumns) {
			if (first) {
				first = false;
				sb.append(c.toString());
			} else {
				sb.append(", ").append(c.toString());
			}
		}
		sb.append(");");
		return sb.toString();
	}

}
