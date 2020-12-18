package model;

import java.util.Vector;

import connect.Connect;

public abstract class Model {
	protected String tableName;
	protected Connect con = Connect.getInstance();

	public Model() {
		
	}

}
