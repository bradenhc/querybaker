package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bradenhitchcock.querybaker.cond.Condition.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableTest {

	private String mTableName;
	private String mTableAlias;

	private Column mCol1;
	private Column mCol2;
	private Column mCol3;
	private Table mTable;

	@BeforeEach
	void setup() {
		mTableName = "test_table";
		mTableAlias = "tt";

		mCol1 = new Column("column_1", DataType.INTEGER, 1, true, true);
		mCol2 = new Column("column_2", DataType.VARCHAR, 255, true);
		mCol3 = new Column("column_3", DataType.DATE, 1);
		mTable = Table.create(mTableName).alias(mTableAlias).columns(mCol1, mCol2, mCol3);
	}

	@Test
	void test() {

		String expected = String.format("CREATE TABLE %s (%s, %s, %s);", mTableName, mCol1, mCol2, mCol3);
		String actual = mTable.build();

		System.out.println(actual);

		assertEquals(expected, actual);
	}

	@Test
	void testTableSelect() {

		Select s = mTable.select().columns(mCol1, mCol2);

		String expected = String.format("SELECT %s, %s FROM %s %s", mCol1.alias(), mCol2.alias(), mTableName,
				mTableAlias);
		String actual = s.build();

		System.out.println(actual);

		assertEquals(expected, actual);

		// Remove the alias

		mTable.alias(null);

		s = mTable.select().columns(mCol1, mCol2);

		expected = String.format("SELECT %s, %s FROM %s", mCol1.name(), mCol2.name(), mTableName);
		actual = s.build();

		System.out.println(actual);

		assertEquals(expected, actual);

	}

	@Test
	void testTablesSelectWithAliasedWhere() {
		String date = "2018-1-1";
		Select s = mTable.select().all().where(and(equal(mCol1, 24), not(lessThan(mCol3, date))));

		String expected = String.format("SELECT * FROM %s %s WHERE (%s = 24 AND  NOT (%s < \"%s\"))", mTableName,
				mTableAlias, mCol1.alias(), mCol3.alias(), date);
		String actual = s.build();

		System.out.println(actual);

		assertEquals(expected, actual);
	}

}
