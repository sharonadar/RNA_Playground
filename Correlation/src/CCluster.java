import java.util.ArrayList;
import java.util.List;


public class CCluster {
	
	private List<CVector> elements = new ArrayList<CVector>();
	
	public CCluster(CVector vector) {
		elements.add(vector);
	}
	
	public void join(CCluster newCluster) {
		elements.addAll(newCluster.getElements());
	}
	
	public List<CVector> getElements() {
		return elements;
	}

}
