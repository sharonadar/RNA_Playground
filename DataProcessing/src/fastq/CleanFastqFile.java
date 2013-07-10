package fastq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fastq.Scorer.ScorerType;

public class CleanFastqFile {

	private static Adapter adapter = new Adapter("TGGAATTCT");
	private static Scorer scorer = new Scorer(ScorerType.SANGER);
	
	private static final boolean TRIM = false;
	private static final String INPUT = "C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_29";
	private static final String OUTPUT = "C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_29.clean";
	
	public static void main(String[] args) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(OUTPUT)));
		BufferedReader br = new BufferedReader(new FileReader(new File(INPUT)));
		
		while (true) {
		   	Fastq fastq = Fastq.readFastqFromFile(br, TRIM);
			if(fastq == null) 
		   		break;
			clipAdapter(bw, fastq);	
		}
		br.close();
		bw.close();
	}

	private static boolean clipAdapter(BufferedWriter bw, Fastq fastq) throws IOException {
		if(adapter.isLegalSequence(fastq)) {
			adapter.clipSequence(fastq);
			if(scorer.getMinimalScore(fastq) >= 20) {
				bw.write(fastq.toString());
				bw.write("\n");
				return true;
			}
		}
		return false;
	}
	
}
