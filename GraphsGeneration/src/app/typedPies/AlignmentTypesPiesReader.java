package app.typedPies;

import java.awt.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;
import ensembl.EnsambleGenes;
import ensembl.general.EnsambleTypedRef;
import ensembl.general.ReferenceType;
import general.range.Range;

public class AlignmentTypesPiesReader extends AlignmentReader {
	
	private final boolean complementaryStrand;
	
	public AlignmentTypesPiesReader(EnsambleGenes<EnsambleTypedRef> context, boolean complementaryStrand) {
		super(context);
		this.complementaryStrand = complementaryStrand;
	}

	public Map<String, Component> getResults() {
		Map<String, Component> res = new HashMap<String, Component>();
		for (Entry<String, List<AlignmentHandlersIfc>> itr : containers.entrySet()) {
			for (AlignmentHandlersIfc handler : itr.getValue()) {
				res.putAll(handler.collectResults());
			}
		}
		return res;
	}

	@Override
	protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
		Map<String, List<AlignmentHandlersIfc>> containers = new HashMap<String, List<AlignmentHandlersIfc>>();
		containers.put("", Arrays.asList( new AlignmentHandlersIfc[] {new CountTypesHandler()}));
		return containers;
	}
	
	@SuppressWarnings("unchecked")
	protected void handleSingleAlignment(ReadDetails read, Alignment align, double count) {
		EnsambleGenes<EnsambleTypedRef> categories = (EnsambleGenes<EnsambleTypedRef>) context;
		
		Range range = align.getRange();
		boolean sense = getStrand(align);
		
		List<EnsambleTypedRef> relevantRefs = categories.get(align.getChromosome(), sense, range);
		for (EnsambleTypedRef ref : relevantRefs) {
			ReferenceType type = ref.getType();
			for (List<AlignmentHandlersIfc> handlers_for_names : containers.values()) {
				for (AlignmentHandlersIfc handler : handlers_for_names) {
					handler.handleAlignment(type, read.getName(), align, count);
				}
			}
		}
	}

	private boolean getStrand(Alignment align) {
		if(complementaryStrand)
			return !align.isPlusStrand();
		else
			return align.isPlusStrand();
	}
}
