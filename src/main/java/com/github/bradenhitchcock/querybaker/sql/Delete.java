package com.github.bradenhitchcock.querybaker.sql;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;
import com.github.bradenhitchcock.querybaker.cond.Condition;

public class Delete implements IQueryBuilder {

	private Table mTable;
	private Condition mCondition = null;

	public Delete(Table table) {
		mTable = table;
	}

	public static Delete from(String name) {
		return new Delete(new Table(name));
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("DELETE FROM ");
		sb.append(mTable.name());
		if (mCondition != null) {
			sb.append("WHERE ").append(mCondition.toString());
		}
		return sb.toString();
	}

}
