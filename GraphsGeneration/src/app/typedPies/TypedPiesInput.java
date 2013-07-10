package app.typedPies;

import java.util.List;

import reader.ReadDetails;

public class TypedPiesInput {
	
	private String referenceFileName;
	private List<ReadDetails> inputs;
	private boolean complementaryStrand;
	
	public TypedPiesInput(String reference, List<ReadDetails> inputs, boolean complementaryStrand) {
		this.inputs = inputs;
		this.referenceFileName = reference;
		this.complementaryStrand = complementaryStrand;
	}

	public List<ReadDetails> getInputs() {
		return inputs;
	}

	public void setInputs(List<ReadDetails> inputs) {
		this.inputs = inputs;
	}

	public String getReferenceFileName() {
		return referenceFileName;
	}

	public void setReferenceFileName(String referenceFileName) {
		this.referenceFileName = referenceFileName;
	}

	public boolean isComplementaryStrand() {
		return complementaryStrand;
	}

	public void setComplementaryStrand(boolean complementaryStrand) {
		this.complementaryStrand = complementaryStrand;
	}
	
}
