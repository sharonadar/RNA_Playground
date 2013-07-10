import general.range.Range;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Correlation {

	private static final String FILE_NAME = "";
	private static final int K = 10;
	
	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new FileReader(new File(FILE_NAME)));
		String line;
		List<CVector> vectors = new ArrayList<CVector>();
		while ((line = br.readLine()) != null) {
			vectors.add(new CVector(line));
		}
		br.close();
		
		//
		
		while(vectors.size() > K) {
			
		}
	}
	
}
