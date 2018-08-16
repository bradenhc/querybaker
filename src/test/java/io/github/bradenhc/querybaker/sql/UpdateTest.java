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

import static org.junit.jupiter.api.Assertions.*;
import static io.github.bradenhc.querybaker.cond.Condition.*;
import static io.github.bradenhc.querybaker.sql.Update.*;

import org.junit.jupiter.api.Test;

import io.github.bradenhc.querybaker.sql.Column;
import io.github.bradenhc.querybaker.sql.DataType;
import io.github.bradenhc.querybaker.sql.Table;
import io.github.bradenhc.querybaker.sql.Update;

class UpdateTest {

	private Table table = Table.create("test_table");
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
