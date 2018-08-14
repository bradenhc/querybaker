package com.github.bradenhitchcock.querybaker.sql;

import com.github.bradenhitchcock.querybaker.api.IQueryBuilder;

public class Update implements IQueryBuilder {

	private Table mTable;
	
	public Update(Table table) {
		mTable = table;
	}
	
	public static Update table(String name) {
		return new Update(new Table(name));
	}
	
	@Override
	public String build() {
		// TODO Auto-generated method stub
		return null;
	}

}
