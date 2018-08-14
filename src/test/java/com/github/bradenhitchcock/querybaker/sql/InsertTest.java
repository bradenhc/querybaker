package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static com.github.bradenhitchcock.querybaker.sql.Insert.*;

class InsertTest {

	private Table table = new Table("test_table");
	private Column c1 = new Column("column_1", DataType.INTEGER, 1);
	private Column c2 = new Column("column_2", DataType.VARCHAR, 255);

	@Test
	void test() {
		Insert i = Insert.into(table).values(pair(c1, 24), pair(c2, "test"));
		String expected = String.format("INSERT INTO %s (%s, %s) VALUES (24, \"test\")", table.name(), c1.name(),
				c2.name());
		assertEquals(expected, i.build());
	}

}
