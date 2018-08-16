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
package io.github.bradenhc.querybaker.util;

import java.sql.Date;

import io.github.bradenhc.querybaker.cond.Condition;
import io.github.bradenhc.querybaker.cond.Condition.Symbol;
import io.github.bradenhc.querybaker.sql.Column;

public class SQLFormatter {
	public static String format(Object o) {
		if (o instanceof Column) {
			Column c = (Column) o;
			return c.alias() == null ? c.name() : c.alias();
		} else if (o instanceof Symbol) {
			return ((Symbol) o).toString();
		} else if (o instanceof Condition) {
			return o.toString();
		} else if (o instanceof String) {
			// TODO: This one is a little more complicated. We need to clean the string
			// before we append it so that
			// we don't have issues inserting it into the SQL table
			String s = (String) o;
			return "\"" + s + "\"";
		} else if (o instanceof Date) {
			// TODO When an SQL date object is passed in, we need to format it appropriately
			Date d = (Date) o;
			return d.toString();
		} else {
			return o.toString();
		}
	}
}
