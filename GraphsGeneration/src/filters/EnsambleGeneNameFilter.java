package filters;

import java.util.List;

import ensembl.EnsambleRef;
import general.ParserFilter;

public class EnsambleGeneNameFilter<T extends EnsambleRef> extends ParserFilter<T> {
	
	private List<String> names;
	
	public EnsambleGeneNameFilter(List<String> names) {
		this.names = names;
	}
	
	@Override
	public boolean shouldKeep(T cat) {
		return names.contains(cat.getEnsemblGeneID());
	}
}
