package com.github.bradenhitchcock.querybaker.cond;

import static org.junit.jupiter.api.Assertions.*;

import static com.github.bradenhitchcock.querybaker.cond.Condition.*;

import org.junit.jupiter.api.Test;

import com.github.bradenhitchcock.querybaker.sql.Column;
import com.github.bradenhitchcock.querybaker.sql.DataType;

class ConditionTest {

	private Column c1 = new Column("column_1", DataType.INTEGER, 1);
	private Column c2 = new Column("column_2", DataType.INTEGER, 1);
	private Column c3 = new Column("column_3", DataType.INTEGER, 1);

	@Test
	void testMultiple() {
		Condition c = and(not(equal(c1, 24)), lessThan(c2, 45), or(equal(c3, 56), equal(c3, 89)));

		String expected = String.format("( NOT (%s = 24) AND %s < 45 AND (%s = 56 OR %s = 89))", c1.name(), c2.name(),
				c3.name(), c3.name());

		assertEquals(expected, c.toString());
	}

}
