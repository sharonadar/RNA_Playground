package general.group;

import java.util.ArrayList;

public abstract class Group<T> extends ArrayList<T>{

	private static final long serialVersionUID = -401238636546246526L;
	
	public abstract T getRepresentor();
	
}
