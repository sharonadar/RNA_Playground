package Games;

public class GeneOffset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int start = 11130088 ;
		int end = 7669571;
		
		System.out.println(((start / 50) + 2) + "\t" + ((start % 50) + 1));
		System.out.println(((end / 50) + 2) + "\t" + ((end % 50) + 1));
	}

}
