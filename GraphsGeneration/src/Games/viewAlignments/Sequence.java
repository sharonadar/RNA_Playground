package Games.viewAlignments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sequence {

	public static ViewerNeuclotid[] getViewerNecs(String line) {
		String[] split = line.split("\t");
		String cigar = split[5];
		String original = split[9];
		String md = null;
		
		for(int i = 0 ; i < split.length ; ++i) {
			if(split[i].startsWith("MD")) {
				md = split[i];
				break;
			}
		}

		return generateGenomeRef(original, md, cigar);
	}

	private static ViewerNeuclotid[] generateGenomeRef(String original, String md, String cigar) {
		
		List<ViewerNeuclotid> ncs = new ArrayList<ViewerNeuclotid>();
		// Handle Cigar
		Pattern pattern = Pattern.compile("(\\d+)(\\D+)");
		Matcher matcher = pattern.matcher(cigar);
		int curLength = 0;
		while (matcher.find()) {
			int length = Integer.valueOf(matcher.group(1));
			char order = matcher.group(2).charAt(0);
			SequenceOrder so = SequenceOrder.getSequenceOrder(order);
			
			for (int i = 0 ; i < length ; ++i) {
				switch (so) {
				case MATCH_CHAR:
					ncs.add(new ViewerNeuclotid(original.charAt(curLength), original.charAt(curLength)));
					curLength++;
					break;
				case SOFT_CLIPPING:
				case INSERT_CHAR:
					ncs.add(new ViewerNeuclotid(null, original.charAt(curLength)));
					curLength++;
					break;
				case DELETE_CHAR:
					ncs.add(new ViewerNeuclotid(so.representor, null));
					break;
				default:
					throw new RuntimeException("Unexpected char " + order + " " + cigar);
				}
			}
		}
		
		// Handle MD
		pattern = Pattern.compile("(\\d+)(\\D*)");
		matcher = pattern.matcher(md);
		curLength = 0;
		int offset = 0;
		while (matcher.find()) {
			int length = Integer.valueOf(matcher.group(1));
			String replace = matcher.group(2);
			for(int i = 0 ; i < length ; ++i)
				if(ncs.get(i).getOldValue() == null)
					offset++;
			
			curLength += length;
			
			if(replace.isEmpty())
				continue;
			
			int startIdx = replace.startsWith("^") ? 1 : 0;
			for(int i = startIdx ; i < replace.length() ; ++i) {
				if(ncs.get(curLength + offset).getOldValue() == null)
					offset++;
				
				if(startIdx == 1)
					ncs.get(curLength + offset).setOldValue(replace.charAt(i));
				else
					ncs.get(curLength + offset).setOldValue(replace.charAt(i));
				curLength++;
			}
		}

		return ncs.toArray(new ViewerNeuclotid[]{});
	}
}
