package Games.viewAlignments;

class ViewerNeuclotid {
	
	private Character oldValue = null;
	private Character newValue = null;
	
	public ViewerNeuclotid(Character oldValue, Character newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	@Override
	public String toString() {
		String a = oldValue == null ? "-" : String.valueOf(oldValue);
		String b = newValue == null ? "-" : String.valueOf(newValue);
		
		if(a.equals(b))
			return a;
		else
			return a + "(" + b + ")";
	}

	public SequenceOrder getOrder() {
		if(oldValue == null)
			return SequenceOrder.INSERT_CHAR;
		if(newValue == null)
			return SequenceOrder.DELETE_CHAR;
		if(newValue.equals(oldValue))
			return SequenceOrder.MATCH_CHAR;
		return SequenceOrder.REPLACE_CHAR;
	}

	public Character getOldValue() {
		return oldValue;
	}

	public void setOldValue(Character oldValue) {
		this.oldValue = oldValue;
	}

	public Character getNewValue() {
		return newValue;
	}

	public void setNewValue(Character newValue) {
		this.newValue = newValue;
	}
}