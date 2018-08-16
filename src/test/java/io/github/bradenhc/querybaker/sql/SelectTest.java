/*
 * Copyright 2018 Braden Hitchcock
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.bradenhc.querybaker.sql;

import static io.github.bradenhc.querybaker.cond.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.bradenhc.querybaker.cond.Condition;
import io.github.bradenhc.querybaker.sql.Column;
import io.github.bradenhc.querybaker.sql.DataType;
import io.github.bradenhc.querybaker.sql.Select;
import io.github.bradenhc.querybaker.sql.Table;

class SelectTest {

	private Table mTable = new Table("test_table");
	private Column mCol1 = new Column("column_1", DataType.INTEGER, 1);
	private Column mCol2 = new Column("column_2", DataType.VARCHAR, 255);

	@BeforeEach
	void setup() {
		mTable = Table.create("test_table");
		mCol1 = new Column("column_1", DataType.INTEGER, 1);
		mCol2 = new Column("column_2", DataType.VARCHAR, 255);
		mTable.columns(mCol1, mCol2);
	}

	@Test
	void testSimpleSelect() {
		Select s = Select.from(mTable).columns(mCol1, mCol2);
		String expected = String.format("SELECT %s, %s FROM %s", mCol1.name(), mCol2.name(), mTable.name());
		assertEquals(expected, s.build());
	}

	@Test
	void testSelectAll() {
		Select s = Select.from(mTable).all();
		String expected = String.format("SELECT * FROM %s", mTable.name());
		assertEquals(expected, s.build());
	}

	@Test
	void testWithWhere() {
		Select s = Select.from(mTable).columns(mCol1, mCol2)
				.where(and(lessThanOrEqual(mCol1, 34), equal(mCol2, "hello")));
		String expected = String.format("SELECT %s, %s FROM %s WHERE (%s <= 34 AND %s = \"hello\")", mCol1.name(),
				mCol2.name(), mTable.name(), mCol1.name(), mCol2.name());
		assertEquals(expected, s.build());
	}

	@Test
	void testFull() {
		Select s = Select.from(mTable).columns(mCol1, mCol2)
				.where(and(lessThanOrEqual(mCol1, 34), equal(mCol2, "hello"))).order(mCol1);
		String expected = String.format("SELECT %s, %s FROM %s WHERE (%s <= 34 AND %s = \"hello\") ORDER BY %s",
				mCol1.name(), mCol2.name(), mTable.name(), mCol1.name(), mCol2.name(), mCol1.name());
		assertEquals(expected, s.build());
	}

	@Test
	void testJoin() {
		Table table2 = Table.create("test_table_two").alias("tbt");
		Column c1 = new Column("column_a", DataType.INTEGER, 1, true, true);
		Column c2 = new Column("column_b", DataType.INTEGER, 1, true);
		table2.columns(c1, c2);
		mTable.alias("tt");
		
		Condition jc = equal(mCol1, c1);

		Select s = mTable.select().columns(mCol1, mCol2, c1).innerJoin(table2, jc).where(greaterThan(mCol1, 24));

		String expected = String.format("SELECT %s, %s, %s FROM %s %s INNER JOIN %s %s ON %s = %s WHERE %s > 24",
				mCol1.alias(), mCol2.alias(), c1.alias(), mTable.name(), mTable.alias(), table2.name(), table2.alias(),
				mCol1.alias(), c1.alias(), mCol1.alias());

		String actual = s.build();

		System.out.println(actual);

		assertEquals(expected, actual);
	}

}
