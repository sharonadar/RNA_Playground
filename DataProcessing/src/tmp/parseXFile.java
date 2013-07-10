package tmp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class parseXFile {
	
	public static final String inputFie = "C:/cygwin/home/sharon.adar/Data/Samples/tmp/wago9.fa";
	public static final String uniqFile = "C:/cygwin/home/sharon.adar/Data/Samples/tmp/wago9.22G.uniq";
	public static final String countFile = "C:/cygwin/home/sharon.adar/Data/Samples/tmp/wago9.22G.count";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFie)));
		BufferedWriter bwu = new BufferedWriter(new FileWriter(new File(uniqFile)));
		BufferedWriter bwc = new BufferedWriter(new FileWriter(new File(countFile)));
		
		while (true) {
			 String comment = br.readLine();
			 if(comment == null)
				 break;
			 
			 String sequence = br.readLine();
			 
			 if(!(sequence.startsWith("G") && sequence.length() == 22))
				 continue;
			 
			 Integer count = Integer.parseInt(comment.split("x")[1]);
			 bwc.write((count / 5.0) + "\t" + sequence + "\n");
			 bwu.write(sequence + "\n");
			 
		}
		
		bwu.close();
		bwc.close();
		br.close();
	}
}
