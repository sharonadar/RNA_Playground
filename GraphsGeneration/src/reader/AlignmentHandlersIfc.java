package reader;

import java.awt.Component;
import java.util.Map;

import alignment.Alignment;

public interface AlignmentHandlersIfc {

	public abstract void handleAlignment(Object context, String name, Alignment align, double count);

	public abstract void done() throws Exception;
	
	public void startFile(String name);

	void doneFile(String name) throws Exception;
	
	public Map<String, Component> collectResults();

}