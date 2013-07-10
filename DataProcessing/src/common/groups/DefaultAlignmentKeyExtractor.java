package common.groups;

import alignment.Alignment;
import general.group.KeyExtractor;

public class DefaultAlignmentKeyExtractor implements KeyExtractor<Integer, Alignment>{

	@Override
	public Integer getKey(Alignment obj) {
		return obj.getId();
	}

}
