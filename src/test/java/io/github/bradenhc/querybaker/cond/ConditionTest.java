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
package io.github.bradenhc.querybaker.cond;

import static io.github.bradenhc.querybaker.cond.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.bradenhc.querybaker.cond.Condition;
import io.github.bradenhc.querybaker.sql.Column;
import io.github.bradenhc.querybaker.sql.DataType;

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
