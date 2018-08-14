package com.github.bradenhitchcock.querybaker.sql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SelectTest {

	private Table table = new Table("test_table");
	private Column c1 = new Column("column_1", DataType.INTEGER, 1);
	private Column c2 = new Column("column_2", DataType.VARCHAR, 255);

	@Test
	void testSimpleSelect() {
		Select s = Select.from(table).columns(c1, c2);
		String expected = String.format("SELECT %s, %s FROM %s", c1.name(), c2.name(), table.name());
		assertEquals(expected, s.build());
	}

	@Test
	void testSelectAll() {
		Select s = Select.from(table).all();
		String expected = String.format("SELECT * FROM %s", table.name());
		assertEquals(expected, s.build());
	}

}
