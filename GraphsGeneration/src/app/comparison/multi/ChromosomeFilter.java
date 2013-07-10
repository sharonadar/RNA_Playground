package app.comparison.multi;

import ensembl.EnsambleRef;
import general.Chromosome;
import general.ParserFilter;

public class  ChromosomeFilter <T extends EnsambleRef> extends ParserFilter<T> {

	private Chromosome chr;
	
	public ChromosomeFilter(Chromosome chr) {
		this.chr = chr;
	}
	
	@Override
	public boolean shouldKeep(T obj) {
		return obj.getChromosomeName().equals(chr);
	}

}