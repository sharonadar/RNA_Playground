package count;

import general.ParserFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UniteCountReads {

	public static Map<String, Double> handleMultipleCountFiles(List<String> fileNames, 
			CountUniteFunction func, ParserFilter<Count> filter) throws IOException {
		Map<String, List<Double>> parsedInfo = new HashMap<String, List<Double>>();
		for (String fileName : fileNames) {
			CountParser parser = new CountParser();
			Map<String, Double> count1 = parser.parseCountFile(fileName, filter);
			for (Entry<String, Double> entry : count1.entrySet()) {
				if(parsedInfo.containsKey(entry.getKey())) {
					parsedInfo.get(entry.getKey()).add(entry.getValue());
				} else {
					List<Double> vals = new ArrayList<Double>();
					vals.add(entry.getValue());
					parsedInfo.put(entry.getKey(), vals);
				}
			}
		}
		
		Map<String, Double> res = new HashMap<String, Double>();
		for (Entry<String, List<Double>> read : parsedInfo.entrySet()) {
			Double value = func.calcDouble(read.getValue());
			res.put(read.getKey(), value);
		}
		
		return res;
	}
}
