package com.github.bradenhc.querybaker.sql;

import static com.github.bradenhc.querybaker.sql.Insert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.bradenhc.querybaker.sql.Column;
import com.github.bradenhc.querybaker.sql.DataType;
import com.github.bradenhc.querybaker.sql.Insert;
import com.github.bradenhc.querybaker.sql.Table;

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
