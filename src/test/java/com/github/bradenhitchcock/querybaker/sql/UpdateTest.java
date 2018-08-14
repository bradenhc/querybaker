package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bradenhitchcock.querybaker.sql.Update.*;
import static com.github.bradenhitchcock.querybaker.cond.Condition.*;

import org.junit.jupiter.api.Test;

class UpdateTest {

	private Table table = new Table("test_table");
	private Column c1 = new Column("column_1", DataType.INTEGER, 1);
	private Column c2 = new Column("column_2", DataType.VARCHAR, 255);

	@Test
	void test() {
		int v1 = 45;
		String v2 = "world";
		Update u = Update.table(table).values(pair(c1, v1), pair(c2, v2));
		String expected = String.format("UPDATE %s SET %s = %d, %s = \"%s\"", table.name(), c1.name(), v1, c2.name(),
				v2);
		assertEquals(expected, u.build());
	}

	@Test
	void testWithWhere() {
		int v1 = 45;
		String v2 = "world";
		int cond = 24;
		Update u = Update.table(table).values(pair(c1, v1), pair(c2, v2)).where(equal(c1, cond));
		String expected = String.format("UPDATE %s SET %s = %d, %s = \"%s\" WHERE %s = %d", table.name(), c1.name(), v1,
				c2.name(), v2, c1.name(), cond);
		assertEquals(expected, u.build());
	}

}
