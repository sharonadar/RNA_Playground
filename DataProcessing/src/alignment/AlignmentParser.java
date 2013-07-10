package alignment;

import general.ParserFilter;
import general.group.Group;
import general.group.KeyExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class AlignmentParser {

	public <R, T extends Group<Alignment>> Collection<T> parseFile(String fileName, 
			ParserFilter<Alignment> filter, 
			KeyExtractor<R, Alignment> keyExtractor,
			Class<T> groupObj) throws Exception {
		
		Map<R, T> result = new HashMap<R, T>();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		while ((line = br.readLine()) != null) {
			if(line.startsWith("@"))
				continue;
			
			Alignment align = new Alignment(line);
			if(!filter.shouldKeep(align))
				continue;
	
			R key = keyExtractor.getKey(align);
			if(!result.containsKey(key)) {
				result.put(key, groupObj.newInstance());
			}
			result.get(key).add(align);
		}
		br.close();
		return result.values();
	}
}
