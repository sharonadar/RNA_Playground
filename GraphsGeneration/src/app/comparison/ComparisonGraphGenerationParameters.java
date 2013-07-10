package app.comparison;

import java.util.List;

import reader.ReadDetails;

public class ComparisonGraphGenerationParameters {
	
	private String referenceFileName;
	private List<ReadDetails> inputs;
	
	private String[] refTypes;
	private boolean complementaryStrand;
	private String groupName;
	private String graphGroup;
	
	public ComparisonGraphGenerationParameters(String reference, List<ReadDetails> inputs) {
		this.referenceFileName = reference;
		this.inputs = inputs;
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

	public String[] getRefTypes() {
		return refTypes;
	}

	public void setRefTypes(String[] refTypes) {
		this.refTypes = refTypes;
	}

	public boolean isComplementaryStrand() {
		return complementaryStrand;
	}

	public void setComplementaryStrand(boolean complementaryStrand) {
		this.complementaryStrand = complementaryStrand;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGraphGroup() {
		return graphGroup;
	}

	public void setGraphGroup(String graphGroup) {
		this.graphGroup = graphGroup;
	}
	
}
