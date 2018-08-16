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
import io.github.bradenhc.querybaker.util.SQLFormatter;

public class Insert implements IQueryBuilder {
	
	private Table mTable;
	private List<Column> mColumns = new ArrayList<>();
	private List<Object> mValues = new ArrayList<>();
	
	public Insert(Table table) {
		mTable = table;
	}
	
	public static Insert into(String table) {
		return new Insert(new Table(table));
	}
	
	public static Insert into(Table table) {
		return new Insert(table);
	}
	
	public Insert values(Pair ...pairs) {
		for(Pair p : pairs) {
			mColumns.add(p.column);
			mValues.add(p.value);
		}
		return this;
	}
	
	public static Pair pair(Column c, Object v) {
		return new Pair(c, v);
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(mTable.name()).append(" ");
		sb.append("(");
		boolean first = true;
		for(Column c : mColumns) {
			if(first) {
				sb.append(c.name());
				first = false;
			} else {
				sb.append(", ").append(c.name());
			}
		}
		sb.append(") ");
		sb.append("VALUES (");
		first = true;
		for(Object v : mValues) {
			String s = SQLFormatter.format(v);
			if(first) {
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
