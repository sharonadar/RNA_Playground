package common.filters;

import alignment.Alignment;
import general.ParserFilter;

public class PrimaryAlignmentFilter extends ParserFilter<Alignment>{

	@Override
	public boolean shouldKeep(Alignment align) {
		if(!align.isMapped())
			return false;
		return (!align.isSecondary());
	}

}
