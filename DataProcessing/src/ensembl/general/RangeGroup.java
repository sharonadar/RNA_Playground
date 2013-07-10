package ensembl.general;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ensembl.EnsambleRef;
import general.group.Group;
import general.range.Range;

public class RangeGroup<T extends EnsambleRef> extends Group<T> {

	private static final long serialVersionUID = -375053319046948960L;
	
	private Range range;
	
	public RangeGroup(Range range) {
		this.range = range;
	}

	@Override
	public T getRepresentor() {
		return null;
	}
	
	public List<T> getByOffset(int offset) {
		List<T> res = new ArrayList<T>();
		Iterator<T> itr = this.iterator();
		while(itr.hasNext()) {
			T ref = itr.next();
			if((ref.getGeneStart() <= offset) && (ref.getGeneEnd() <= offset))
				res.add(ref);
		}
		return res;
	}
	
	public List<T> getByExactRange(Range reqRange) {
		return getByExactRange(reqRange, 0);
	}
	
	public List<T> getByExactRange(Range reqRange, int mergins) {
		List<T> res = new ArrayList<T>();
		Iterator<T> itr = this.iterator();
		while(itr.hasNext()) {
			T ref = itr.next();
			Range range = new Range(ref.getGeneStart(), ref.getGeneEnd());
			if(reqRange.compare(range, mergins) == 0)
				res.add(ref);
		}
		return res;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}
	
	public void unite(RangeGroup<T> newGroup) {
		this.range = new Range(Math.min(newGroup.getRange().getStart(), range.getStart()), Math.max(newGroup.getRange().getEnd(), range.getEnd()));
		this.addAll(newGroup);
	}

}
