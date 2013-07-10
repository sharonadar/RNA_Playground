package general.range;
import java.util.Comparator;

public class RangeComparator implements Comparator<Range>{

	private final int mergin;
	
	public RangeComparator(int mergin) {
		this.mergin = mergin;
	}
	
	@Override
	public int compare(Range o1, Range o2) {
		return o1.compare(o2, mergin);
	}

}
