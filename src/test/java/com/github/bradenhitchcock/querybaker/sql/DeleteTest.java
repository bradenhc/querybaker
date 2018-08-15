package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bradenhitchcock.querybaker.cond.Condition.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteTest {

	private String mTableName = "table_alpha";
	private String mTableAlias = "ta";

	private Table mTable;
	private Column mCol1;
	private Column mCol2;

	@BeforeEach
	void setUp() throws Exception {
		mTable = Table.create(mTableName).alias(mTableAlias);
		mCol1 = new Column("col1", DataType.INTEGER, 1, true, true);
		mCol2 = new Column("col2", DataType.INTEGER, 1, true);
		mTable.columns(mCol1, mCol2);
	}

	@Test
	void test() {
		Delete d = mTable.delete();

		String expected = String.format("DELETE FROM %s %s", mTableName, mTableAlias);
		String actual = d.build();

		assertEquals(expected, actual);
	}

	@Test
	void testWhere() {
		int v = 24;
		Delete d = mTable.delete().where(equal(mCol1, v));

		String expected = String.format("DELETE FROM %s %s WHERE %s = %d", mTableName, mTableAlias, mCol1.alias(), v);
		String actual = d.build();

		assertEquals(expected, actual);
	}

}
