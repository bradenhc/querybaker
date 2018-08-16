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

import io.github.bradenhc.querybaker.util.Formatter;

/**
 * Represents a conditional expression in a database query. A good example is a WHERE clause in a SQL database query.
 * Currently this class only supports creation of SQL conditions, but in future releases it will be extended to be
 * database-type agnostic.
 */
public class Condition {

	/** The builder to hold the condition clause as it is being built */
	private StringBuilder mStringBuilder = new StringBuilder();

	private Condition() {
		// Constructor is private so that others don't accidentally create their own condition instance. We want them to
		// use the provided static methods
	}

	/**
	 * Creates a sequence of condition clauses joined by a logical AND operation.
	 * 
	 * @param c1
	 *        the first condition in the sequence
	 * @param c2
	 *        the second condition in the sequence
	 * @param additional
	 *        any additional conditions that should also be grouped in this chain of logical AND operations
	 * @return a Condition instance with its internal StringBuilder holding a correctly formatted clause of where
	 *         clauses combined with logical ANDs
	 */
	public static Condition and(Condition c1, Condition c2, Condition... additional) {
		c1.prepend(Symbol.LP);
		c1.append(Symbol.AND).append(c2);
		for (Condition c : additional) {
			c1.append(Symbol.AND).append(c);
		}
		c1.append(Symbol.RP);
		return c1;
	}

	/**
	 * Creates a sequence of condition clauses joined by a logical OR operation.
	 * 
	 * @param c1
	 *        the first condition in the sequence
	 * @param c2
	 *        the second condition in the sequence
	 * @param additional
	 *        any additional conditions that should also be grouped in this chain of logical OR operations
	 * @return a Condition instance with its internal StringBuilder holding a correctly formatted clause of where
	 *         clauses combined with logical OR
	 */
	public static Condition or(Condition c1, Condition c2, Condition... additional) {
		c1.prepend(Symbol.LP);
		c1.append(Symbol.OR).append(c2);
		for (Condition c : additional) {
			c1.append(Symbol.OR).append(c);
		}
		c1.append(Symbol.RP);
		return c1;
	}

	/**
	 * Creates a conditional clause that is negated with a logical NOT operation.
	 * 
	 * @param c
	 *        the condition to negate with a conditional NOT
	 * @return a Condition object instance with its internal StringBuilder set to include the NOT operation on the
	 *         existing clause
	 */
	public static Condition not(Condition c) {
		c.prepend(Symbol.LP).prepend(Symbol.NOT);
		c.append(Symbol.RP);
		return c;
	}

	/**
	 * Creates a conditional expression using an EQUALS ('=') operations to compare the left and right objects. The
	 * objects will be formatted and turned into their valid string equivalents.
	 * 
	 * @param left
	 *        the object whose string value will be placed on the left side of the equal expression
	 * @param right
	 *        the object whose string value will be placed on the right side of the equal expression
	 * @return a new Condition instance representing the equals comparison
	 */
	public static Condition equal(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.EQ).append(right);
		return c;
	}

	/**
	 * Creates a conditional expression using an LESS THAN ('&lt;') operations to compare the left and right objects.
	 * The objects will be formatted and turned into their valid string equivalents.
	 * 
	 * @param left
	 *        the object whose string value will be placed on the left side of the less than expression
	 * @param right
	 *        the object whose string value will be placed on the right side of the less than expression
	 * @return a new Condition instance representing the less than comparison
	 */
	public static Condition lessThan(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.LT).append(right);
		return c;
	}

	/**
	 * Creates a conditional expression using an LESS THAN OR EQUAL ('&lt;=') operations to compare the left and right
	 * objects. The objects will be formatted and turned into their valid string equivalents.
	 * 
	 * @param left
	 *        the object whose string value will be placed on the left side of the less than or equal expression
	 * @param right
	 *        the object whose string value will be placed on the right side of the less than or equal expression
	 * @return a new Condition instance representing the less than or equal comparison
	 */
	public static Condition lessThanOrEqual(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.LTE).append(right);
		return c;
	}

	/**
	 * Creates a conditional expression using an GREATER THAN ('&gt;') operations to compare the left and right objects.
	 * The objects will be formatted and turned into their valid string equivalents.
	 * 
	 * @param left
	 *        the object whose string value will be placed on the left side of the greater than expression
	 * @param right
	 *        the object whose string value will be placed on the right side of the greater than expression
	 * @return a new Condition instance representing the greater than comparison
	 */
	public static Condition greaterThan(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.GT).append(right);
		return c;
	}

	/**
	 * Creates a conditional expression using an GREATER THAN OR EQUAL ('&gt;=') operations to compare the left and
	 * right objects. The objects will be formatted and turned into their valid string equivalents.
	 * 
	 * @param left
	 *        the object whose string value will be placed on the left side of the greater than or equal expression
	 * @param right
	 *        the object whose string value will be placed on the right side of the greater than or equal expression
	 * @return a new Condition instance representing the greater than or equal comparison
	 */
	public Condition greaterThanOrEqual(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.GTE).append(right);
		return c;
	}

	/**
	 * Formats and adds the string representation of the provided object to the end of the current string representation
	 * of the conditional expression according to the internal StringBuilder. The formating is done according to the
	 * {@link Formatter#format(Object)} method.
	 * 
	 * @param o
	 *        the object to format and append to the condition
	 * @return the modified condition object
	 */
	public Condition append(Object o) {
		// We may need to perform formatting conversions depending on what type of
		// object is passed in
		String result = Formatter.format(o);
		mStringBuilder.append(result);
		return this;
	}

	/**
	 * Formats and adds the string representation of the provided object to the beginning of the current string
	 * representation of the conditional expression according to the internal StringBuilder. The formatting is done
	 * according to the {@link Formatter#format(Object)} method.
	 * 
	 * @param o
	 *        the object to format and prepend to the condition
	 * @return the modified condition object
	 */
	public Condition prepend(Object o) {
		String result = Formatter.format(o);
		mStringBuilder.insert(0, result);
		return this;
	}

	@Override
	public String toString() {
		return mStringBuilder.toString();
	}

	/**
	 * Represents the symbols used on a conditional expression clause for database queries. These enumerations have
	 * string representations that will not be formated as a string when being formatted by an implementation of the
	 * {@link Formatter}.
	 */
	public enum Symbol {
		EQ {
			public String toString() {
				return " = ";
			}
		},
		LT {
			public String toString() {
				return " < ";
			}
		},
		LTE {
			public String toString() {
				return " <= ";
			}
		},
		GT {
			public String toString() {
				return " > ";
			}
		},
		GTE {
			public String toString() {
				return " >= ";
			}
		},
		LP {
			public String toString() {
				return "(";
			}
		},
		RP {
			public String toString() {
				return ")";
			}
		},
		SPACE {
			public String toString() {
				return " ";
			}
		},
		AND {
			public String toString() {
				return " AND ";
			}
		},
		OR {
			public String toString() {
				return " OR ";
			}
		},
		NOT {
			public String toString() {
				return " NOT ";
			}
		};

		public abstract String toString();
	}

}
