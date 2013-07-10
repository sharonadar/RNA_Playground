package reader;

import general.ParserFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import alignment.Alignment;

import common.filters.EmptyFilter;

import count.Count;
import count.CountParser;

public abstract class AlignmentReader {

	protected Map<String, List<AlignmentHandlersIfc>> containers;
	protected Object context;
	
	public AlignmentReader(Object context) {
		this.context = context;
		this.containers = createContainers();
	}
	
	public void handleReads(List<ReadDetails> reads, ParserFilter<Alignment> filter) throws Exception {
		
		for (ReadDetails read : reads) {
			System.out.println(read.getName());
			startFileHandling(read);
			
			CountParser parser = new CountParser();
			Map<String, Double> countFile = parser.parseCountFile(read.getCountFile(), new EmptyFilter<Count>());
			BufferedReader br = new BufferedReader(new FileReader(new File(read.getAlignFile())));
			String line;
			while ((line = br.readLine()) != null) {
				if(line.startsWith("@"))
					continue;
				
			   	Alignment align = new Alignment(line);
			   	if(!filter.shouldKeep(align))
			   		continue;
			   	
			   	Double count = countFile.get(align.getSequence());
			   	handleSingleAlignment(read, align, count);
			}
			br.close();
			doneFileHandling(read);
		}
		
		for (List<AlignmentHandlersIfc> container : containers.values()) {
			for (AlignmentHandlersIfc handler : container) {
				handler.done();
			}
		}
	}
	
	protected abstract Map<String, List<AlignmentHandlersIfc>> createContainers();
	
	protected void startFileHandling(ReadDetails read) {
		for (List<AlignmentHandlersIfc> container : containers.values()) {
			for (AlignmentHandlersIfc handler : container) {
				handler.startFile(read.getName());
			}
		}
	}
	
	abstract protected void handleSingleAlignment(ReadDetails read, Alignment align, double count);

	protected void doneFileHandling(ReadDetails read) throws Exception {
		for (List<AlignmentHandlersIfc> container : containers.values()) {
			for (AlignmentHandlersIfc handler : container) {
				handler.doneFile(read.getName());
			}
		}
	}
}
