package common.groups;

import java.util.Iterator;

import general.group.Group;
import alignment.Alignment;

public class DefaultAlignmentGroupPrimary extends Group<Alignment>{

	private static final long serialVersionUID = -3251264243069638289L;

	@Override
	public Alignment getRepresentor() {
		
		Alignment represent = null;
		Iterator<Alignment> itr = this.iterator();
		while (itr.hasNext()) {
			Alignment alignment = itr.next();
			if(alignment.isMapped() && !alignment.isSecondary()) {
				if(represent != null)
					throw new RuntimeException();
				else 
					represent = alignment; 
			}
		}
		return represent;
	}

}
