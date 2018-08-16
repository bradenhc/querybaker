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
