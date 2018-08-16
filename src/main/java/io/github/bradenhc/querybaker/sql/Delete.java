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

public class Delete implements IQueryBuilder {

	private Table mTable;
	private Condition mCondition = null;

	public Delete(Table table) {
		mTable = table;
	}

	public static Delete from(String name) {
		return new Delete(new Table(name));
	}
	
	public Delete where(Condition c) {
		mCondition = c;
		return this;
	}

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
