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
import io.github.bradenhc.querybaker.util.SQLFormatter;

public class Update implements IQueryBuilder {

	private Table mTable;
	private List<Pair> mUpdates = new ArrayList<>();
	private Condition mCondition;
	
	public Update(Table table) {
		mTable = table;
	}
	
	public static Update table(String name) {
		return new Update(new Table(name));
	}
	
	public static Update table(Table table) {
		return new Update(table);
	}
	
	public Update values(Pair ...pairs) {
		for(Pair p : pairs) {
			mUpdates.add(p);
		}
		return this;
	}
	
	public Update where(Condition c) {
		mCondition = c;
		return this;
	}
	
	public static Pair pair(Column c, Object v) {
		return new Pair(c,v);
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(mTable.name()).append(" ");
		sb.append("SET ");
		boolean first = true;
		for(Pair p : mUpdates) {
			if(first) {
				first = false;
				sb.append(p.column.name()).append(" = ").append(SQLFormatter.format(p.value));
			} else {
				sb.append(", ").append(p.column.name()).append(" = ").append(SQLFormatter.format(p.value));
			}
		}
		if(mCondition != null) {
			sb.append(" WHERE ").append(mCondition.toString());
		}
		return sb.toString();
	}

}
