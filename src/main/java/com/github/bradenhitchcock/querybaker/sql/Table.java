package com.github.bradenhitchcock.querybaker.sql;

import java.util.ArrayList;
import java.util.List;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;

public class Table implements IQueryBuilder {
	
	private String mName;
	private List<Column> mColumns = new ArrayList<>();
	
	public Table(String name) {
		mName = name;
	}
	
	public static Table create(String name) {
		return new Table(name);
	}

	public Select select() {
		return new Select(this);
	}
	
	public Insert insert() {
		return new Insert(this);
	}
	
	public Update update() {
		return new Update(this);
	}
	
	public Delete delete() {
		return new Delete(this);
	}
	
	public Table drop() {
		return this;
	}
	
	public Table column(Column c) {
		mColumns.add(c);
		return this;
	}
	
	public List<Column> columns(){
		return mColumns;
	}
	
	public Table name(String name) {
		mName = name;
		return this;
	}
	
	public String name() {
		return mName;
	}

	@Override
	public String build() {
		// TODO Auto-generated method stub
		return null;
	}
	
}