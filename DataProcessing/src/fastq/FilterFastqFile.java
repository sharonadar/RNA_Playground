package fastq;

import general.ParserFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterFastqFile {

	private static final String INPUT = "C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_29.clean";
	private static final String OUTPUT = "C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_29.15";
	
	public static void main(String[] args) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(OUTPUT)));
		BufferedReader br = new BufferedReader(new FileReader(new File(INPUT)));

		List<ParserFilter<Fastq>> filters = generateFilters();
		
		while (true) {
		   	Fastq fastq = Fastq.readFastqFromFile(br, false);
			if(fastq == null) 
		   		break;
			filter(bw, fastq, filters);	
		}
		br.close();
	}

	private static List<ParserFilter<Fastq>> generateFilters() {
		List<ParserFilter<Fastq>> filters = new ArrayList<ParserFilter<Fastq>>();
		filters.add(new ParserFilter<Fastq>() {
			@Override
			public boolean shouldKeep(Fastq obj) {
				String str = obj.getSequence();
				return (str.length() >= 15);
//				return ((str.length() == 21) && (str.startsWith("T")));
			}
		});
		return filters;
	}

	private static boolean filter(BufferedWriter bw, Fastq fastq, List<ParserFilter<Fastq>> filters) throws IOException {
		for (ParserFilter<Fastq> filter : filters) {
			if(!filter.shouldKeep(fastq)) 
				return false;
		}
		bw.write(fastq.getSequence());
		bw.append("\n");
		return true;
	}

}
