package ensembl.general;

import ensembl.EnsambleRef;

public class EnsambleTypedRef extends EnsambleRef{

	private ReferenceType type;
	
	public EnsambleTypedRef(String inputStr) {
		super(inputStr);
		String[] res = inputStr.split("\t");
		if(res.length >= 7) 
			this.type = ReferenceType.valueOf(res[6]);
	}

	public ReferenceType getType() {
		return type;
	}

	public void setType(ReferenceType type) {
		this.type = type;
	}

}
