package com.github.bradenhitchcock.querybaker.sql;

public class Column {
	private String mName;
	private DataType mDataType;
	private int mDataTypeSize = 1;
	private boolean mIsNotNull = false;
	private boolean mIsPrimaryKey = false;

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
		return sb.toString();
	}
}
