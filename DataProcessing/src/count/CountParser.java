package count;

import general.ParserFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CountParser {
	
	public Map<String, Double> parseCountFile(String fileName, ParserFilter<Count> filter) throws IOException {
		Map<String, Double> map = new HashMap<String, Double>();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		while ((line = br.readLine()) != null) {
		   Count cnt = new Count(line);
		   if(filter.shouldKeep(cnt))
			   map.put(cnt.getSequence(), cnt.getCount());
		}
		br.close();
		return map;
	}

}
