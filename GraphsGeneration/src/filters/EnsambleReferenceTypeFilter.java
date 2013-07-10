package filters;

import java.util.ArrayList;
import java.util.List;

import ensembl.general.EnsambleTypedRef;
import ensembl.general.ReferenceType;
import general.ParserFilter;

public class EnsambleReferenceTypeFilter  extends ParserFilter<EnsambleTypedRef> {

	private List<ReferenceType> types = new ArrayList<ReferenceType>();
	
	public EnsambleReferenceTypeFilter(String[] types) {
		for (String type : types) {
			this.types.add(ReferenceType.valueOf(type));
		}
	}
	
	@Override
	public boolean shouldKeep(EnsambleTypedRef obj) {
		return types.contains(obj.getType());
	}

}
