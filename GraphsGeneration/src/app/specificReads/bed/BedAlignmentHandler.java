package app.specificReads.bed;

import java.awt.Component;
import java.util.Collection;

import bed.BedLine;

public interface BedAlignmentHandler {

	public abstract void handleBedAlignment(Boolean sense, String read,
			BedLine align, double count);

	public abstract Collection<Component> done() throws Exception;

	public abstract void doneFile(String name);

	public abstract void startFile(String name);

}