package formats;

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class XYLabelSeries extends XYSeries {
	
	Map<XYDataItem, String> dataset = new HashMap<XYDataItem, String>();

	public XYLabelSeries(Comparable<?> key) {
		super(key);
	}

	private static final long serialVersionUID = 1589819858189601780L;
	
	public void add(XYDataItem item, String title) {
		dataset.put(item, title);
		super.add(item);
	}
	
	public String getLabel(int id) {
		XYDataItem obj = (XYDataItem) this.data.get(id);
		return dataset.get(obj);
	}

}
