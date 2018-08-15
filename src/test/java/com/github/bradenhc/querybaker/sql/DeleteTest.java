package com.github.bradenhc.querybaker.sql;

import static com.github.bradenhc.querybaker.cond.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.bradenhc.querybaker.sql.Column;
import com.github.bradenhc.querybaker.sql.DataType;
import com.github.bradenhc.querybaker.sql.Delete;
import com.github.bradenhc.querybaker.sql.Table;

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
