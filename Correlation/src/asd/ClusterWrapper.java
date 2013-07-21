package asd;

import java.util.List;

public class ClusterWrapper extends Cluster implements Comparable<ClusterWrapper> {

	private Double cor;
	
	public Double getCor() {
		return cor;
	}

	public ClusterWrapper(List<Couple> result) {
		super(result);
		this.cor = Caluclator.getCorrelation(this);
	}

	@Override
	public int compareTo(ClusterWrapper o) {
		return o.getCor().compareTo(getCor());
	}
}
