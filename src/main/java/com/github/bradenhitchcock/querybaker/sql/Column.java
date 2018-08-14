package com.github.bradenhitchcock.querybaker.sql;

public class Column {
	private String mName;
	private String mAlias;
	private DataType mDataType;
	private int mDataTypeSize = 1;
	private boolean mIsNotNull = false;
	private boolean mIsPrimaryKey = false;
	private boolean mAutoIncrement = false;

	public Column(String name, DataType dataType, int size, boolean isNotNull, boolean isPrimaryKey) {
		this(name, dataType, size, isNotNull);
		mIsPrimaryKey = isPrimaryKey;
	}

	public Column(String name, DataType dataType, int size, boolean isNotNull) {
		this(name, dataType, size);
		mIsNotNull = isNotNull;
	}

	public Column(String name, DataType dataType, int size) {
		mName = name;
		mDataType = dataType;
		mDataTypeSize = size;
	}

	public void name(String name) {
		mName = name;
	}

	public String name() {
		return mName;
	}
	
	public void alias(String alias) {
		mAlias = alias + "." + mName;
	}
	
	public String alias() {
		return mAlias;
	}

	public void dataType(DataType dataType, int size) {
		dataType(dataType);
		mDataTypeSize = size;
	}

	public void dataType(DataType dataType) {
		mDataType = dataType;
	}

	public DataType dataType() {
		return mDataType;
	}

	public void setPrimaryKey(boolean value) {
		mIsPrimaryKey = value;
	}

	public boolean isPrimaryKey() {
		return mIsPrimaryKey;
	}

	public void setNotNull(boolean value) {
		mIsNotNull = value;
	}

	public boolean isNotNull() {
		return mIsNotNull;
	}
	
	public void setAutoIncrement(boolean value) {
		mAutoIncrement = value;
	}
	
	public boolean autoIncrement() {
		return mAutoIncrement;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mName);
		sb.append(mDataType.apachedb(mDataTypeSize));
		if (mIsNotNull) {
			sb.append("NOT NULL");
		}
		if (mIsPrimaryKey) {
			sb.append(" PRIMARY KEY");
		}
		if(mAutoIncrement) {
			sb.append(" ").append(getAutoincrementSyntaxFromMode());
		}
		return sb.toString();
	}
	
	private String getAutoincrementSyntaxFromMode() {
		// Apache DB
		return "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
	}
}
