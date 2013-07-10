package general;

public class Utils {
	
	public static String complement(String seq) {
		StringBuffer sb = new StringBuffer();
		for(int i = seq.length() -1 ; i >= 0  ; --i) {
			char c = seq.charAt(i);
			switch (c) {
			case 'A':
				sb.append('T');
				break;
			case 'T':
				sb.append('A');
				break;
			case 'C':
				sb.append('G');
				break;
			case 'G':
				sb.append('C');
				break;
			}
		}
		return sb.toString();
	}

}
