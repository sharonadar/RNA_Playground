package ensembl.goCategory;

import ensembl.EnsambleRef;

public class GoCategory extends EnsambleRef{

	private String goTermAccession;
	
	public GoCategory(String inputStr) {
		super(inputStr);
		String[] res = inputStr.split("\t");
		if((res.length >= 7) && (res[6].startsWith("GO:"))) 
			this.goTermAccession = res[6];
	}
	
	
	public String getGoTermAccession() {
		return goTermAccession;
	}
	public void setGoTermAccession(String goTermAccession) {
		this.goTermAccession = goTermAccession;
	}

}
