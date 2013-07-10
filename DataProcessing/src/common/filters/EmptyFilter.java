package common.filters;

import general.ParserFilter;

public class EmptyFilter<T> extends ParserFilter<T>{

	@Override
	public boolean shouldKeep(T obj) {
		return true;
	}

}
