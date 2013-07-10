package general.range;

public class Range implements Comparable<Range>{
	
	private int start;
	private int end;
	
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Range other = (Range) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + start + "," + end + ")";
	}

	@Override
	public int compareTo(Range o) {
		return this.compare(o, 0);
	}

	public int compare(Range o2, int mergin) {
		if( this.getStart() > o2.getEnd() + mergin)
			return 1;
		if(this.getEnd() + mergin < o2.getStart())
			return -1;
		
		return 0;
	}
}
