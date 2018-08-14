package com.github.bradenhitchcock.querybaker.cond;

import com.github.bradenhitchcock.querybaker.util.SQLFormatter;

public class Condition {

	private StringBuilder mStringBuilder = new StringBuilder();

	private Condition() {

	}

	public static Condition and(Condition c1, Condition c2, Condition... additional) {
		c1.prepend(Symbol.LP);
		c1.append(Symbol.AND).append(c2);
		for (Condition c : additional) {
			c1.append(Symbol.AND).append(c);
		}
		c1.append(Symbol.RP);
		return c1;
	}

	public static Condition or(Condition c1, Condition c2, Condition... additional) {
		c1.prepend(Symbol.LP);
		c1.append(Symbol.OR).append(c2);
		for (Condition c : additional) {
			c1.append(Symbol.OR).append(c);
		}
		c1.append(Symbol.RP);
		return c1;
	}

	public static Condition not(Condition c) {
		c.prepend(Symbol.LP).prepend(Symbol.NOT);
		c.append(Symbol.RP);
		return c;
	}

	public static Condition equal(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.EQ).append(right);
		return c;
	}

	public static Condition lessThan(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.LT).append(right);
		return c;
	}

	public static Condition lessThanOrEqual(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.LTE).append(right);
		return c;
	}

	public static Condition greaterThan(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.GT).append(right);
		return c;
	}

	public Condition greaterThanOrEqual(Object left, Object right) {
		Condition c = new Condition();
		c.append(left).append(Symbol.GTE).append(right);
		return c;
	}

	public Condition append(Object o) {
		// We may need to perform formatting conversions depending on what type of
		// object is passed in
		String result = SQLFormatter.format(o);
		mStringBuilder.append(result);
		return this;
	}

	public Condition prepend(Object o) {
		String result = SQLFormatter.format(o);
		mStringBuilder.insert(0, result);
		return this;
	}

	@Override
	public String toString() {
		return mStringBuilder.toString();
	}

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
