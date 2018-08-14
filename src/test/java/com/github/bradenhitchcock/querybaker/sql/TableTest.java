package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TableTest {

	private String mTableName = "test_table";

	@Test
	void test() {
		Column c1 = new Column("column_1", DataType.INTEGER, 1, true, true);
		Column c2 = new Column("column_2", DataType.VARCHAR, 255, true);
		Column c3 = new Column("column_3", DataType.DATE, 1);
		Table t = Table.create(mTableName).columns(c1, c2, c3);

		String expected = String.format("CREATE TABLE %s (%s, %s, %s);", t.name(), c1, c2, c3);
		String actual = t.build();

		System.out.println(actual);

		assertEquals(expected, actual);
	}

}
