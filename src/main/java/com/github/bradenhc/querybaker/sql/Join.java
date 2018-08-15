package com.github.bradenhc.querybaker.sql;

import com.github.bradenhc.querybaker.cond.Condition;

public class Join {

	private SQLJoinType mType;
	private Table mJoinTable;
	private Condition mCondition;

	public Join(SQLJoinType type, Table joinTable, Condition condition) {
		mType = type;
		mJoinTable = joinTable;
		mCondition = condition;
	}

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
