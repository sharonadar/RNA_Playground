import java.util.List;


public class CVector {
	
	private String name;
	private List<Double> values;

	public CVector(String line) {
		String[] str = line.split("\t");
		this.name = str[0];
		for(int i = 1 ; i < str.length ; ++i) {
			values.add(Double.valueOf(str[i]));
		}
	}

}
