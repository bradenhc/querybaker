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

import static io.github.bradenhc.querybaker.sql.Insert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.bradenhc.querybaker.sql.Column;
import io.github.bradenhc.querybaker.sql.DataType;
import io.github.bradenhc.querybaker.sql.Insert;
import io.github.bradenhc.querybaker.sql.Table;

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
