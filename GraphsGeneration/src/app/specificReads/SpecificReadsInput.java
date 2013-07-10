package app.specificReads;

import java.util.List;

import reader.ReadDetails;

public class SpecificReadsInput {
	
	private String referenceFileName;
	private List<ReadDetails> inputs;
	private List<String> geneNames;
	
	
	public SpecificReadsInput(String reference, List<ReadDetails> inputs, List<String> geneNames) {
		this.referenceFileName = reference;
		this.inputs = inputs;
		this.geneNames = geneNames;
	}


	public String getReferenceFileName() {
		return referenceFileName;
	}


	public void setReferenceFileName(String referenceFileName) {
		this.referenceFileName = referenceFileName;
	}


	public List<ReadDetails> getInputs() {
		return inputs;
	}


	public void setInputs(List<ReadDetails> inputs) {
		this.inputs = inputs;
	}


	public List<String> getGeneNames() {
		return geneNames;
	}


	public void setGeneNames(List<String> geneNames) {
		this.geneNames = geneNames;
	}

}