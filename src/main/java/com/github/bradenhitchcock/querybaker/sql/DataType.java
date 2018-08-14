package com.github.bradenhitchcock.querybaker.sql;

public enum DataType {
	// Text-based Data Types
	CHAR{
		public String apachedb(int size) {
			return " CHAR(" + size + ") ";
		}
	},
	VARCHAR {
		public String apachedb(int size) {
			return " VARCHAR(" + size + ") ";
		}
	},
	LONG_VARCHAR {
		public String apachedb(int size) {
			return " LONG VARCHAR ";
		}
	},
	CHAR_FOR_BIT_DATA {
		public String apachedb(int size) {
			return " CHAR(" + size + ") FOR BIT DATA ";
		}
	},
	VARCHAR_FOR_BIT_DATA {
		public String apachedb(int size) {
			return " VARCHAR(" + size +") FOR BIT DATA ";
		}
	},
	LONG_VARCHAR_FOR_BIT_DATA {
		public String apachedb(int size) {
			return " LONG VARCHAR FOR BIT DATA ";
		}
	},
	CLOB {
		public String apachedb(int size) {
			return " CLOB(" + size + ") ";
		}
	},
	BLOB {
		public String apachedb(int size) {
			return " BLOB(" + size + ") ";
		}
	},
	XML {
		public String apachedb(int size) {
			return " XML ";
		}
	},
	
	// Number-based Data Types
	BOOLEAN {
		public String apachedb(int size) {
			return " BOOLEAN ";
		}
	},
	SMALLINT {
		public String apachedb(int size) {
			return " SMALLINT ";
		}
	},
	INTEGER {
		public String apachedb(int size) {
			return " INT ";
		}
	},
	BIGINT {
		public String apachedb(int size) {
			return " BIGINT ";
		}
	},
	DECIMAL {
		public String apachedb(int size) {
			return " DECIMAL ";
			// TODO add precision and scale
		}
	},
	REAL {
		public String apachedb(int size) {
			return " REAL ";
		}
	},
	DOUBLE {
		public String apachedb(int size) {
			return " DOUBLE ";
		}
	},
	FLOAT {
		public String apachedb(int size) {
			return " FLOAT ";
			// TODO add precision
		}
	},
	
	
	// Date-based Data Types
	DATE {
		public String apachedb(int size) {
			return " DATE ";
		}
	},
	TIME {
		public String apachedb(int size) {
			return " TIME ";
		}
	},
	TIMESTAMP {
		public String apachedb(int size) {
			return " TIMESTAMP ";
		}
	};
	
	// Other Data Types
	
	public abstract String apachedb(int size);
}
