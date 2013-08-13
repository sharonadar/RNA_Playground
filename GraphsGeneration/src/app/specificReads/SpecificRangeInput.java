package app.specificReads;

import java.util.List;

import reader.ReadDetails;
import Games.whatsIntheBin.InterestingGene;

public class SpecificRangeInput {
	
	private List<ReadDetails> inputs;
	private InterestingGene geneRange;
	
	
	public SpecificRangeInput(List<ReadDetails> inputs, InterestingGene geneRange) {
		this.inputs = inputs;
		this.geneRange = geneRange;
	}

	public List<ReadDetails> getInputs() {
		return inputs;
	}


	public void setInputs(List<ReadDetails> inputs) {
		this.inputs = inputs;
	}


	public InterestingGene getGene() {
		return geneRange;
	}

}