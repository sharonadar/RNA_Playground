package app.alignBin;

import java.util.List;

import reader.ReadDetails;

public class AlignmentBinsInput {
	
	private String referenceName;
	private List<ReadDetails> inputs;
	private boolean separateGraphs;
	private AlignmentFunctionOptions function;
	
	public AlignmentBinsInput(List<ReadDetails> inputs, boolean separateGraphs, AlignmentFunctionOptions function) {
		this.inputs = inputs;
		this.separateGraphs = separateGraphs;
		this.function = function;
	}

	public AlignmentBinsInput(List<ReadDetails> inputs, String referenceName) {
		this.referenceName = referenceName;
		this.inputs = inputs;
	}

	public List<ReadDetails> getInputs() {
		return inputs;
	}

	public void setInputs(List<ReadDetails> inputs) {
		this.inputs = inputs;
	}

	public boolean isSeparateGraphs() {
		return separateGraphs;
	}

	public void setSeparateGraphs(boolean separateGraphs) {
		this.separateGraphs = separateGraphs;
	}

	public AlignmentFunctionOptions getFunction() {
		return function;
	}

	public void setFunction(AlignmentFunctionOptions function) {
		this.function = function;
	}
	
	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

}
