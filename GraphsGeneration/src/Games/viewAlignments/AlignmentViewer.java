package Games.viewAlignments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;

public class AlignmentViewer {
	
	private JFrame frame = new JFrame();
    private JScrollPane scroll = new JScrollPane();
    private JTable myTable;

    public AlignmentViewer() throws IOException {
    	
    	generateTable();
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        myTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        myTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        myTable.setShowHorizontalLines(true);
        myTable.setShowVerticalLines(true);
        scroll.setViewportView(myTable);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void generateTable() throws IOException {
    	
    	int maxLength = 0;
    	BufferedReader br = new BufferedReader(new FileReader(new File("C:\\temp\\omrk.txt")));
		String line;
		List<String> ids = new ArrayList<String>();
		List<ViewerNeuclotid[]> seqs = new ArrayList<ViewerNeuclotid[]>();
		while ((line = br.readLine()) != null) {
			if(line.startsWith("@"))
				continue;
			
			ids.add(line.split("\t")[0]); 
			ViewerNeuclotid[] vns = Sequence.getViewerNecs(line);
			seqs.add(vns);
			maxLength = Math.max(maxLength, vns.length);
		}
		br.close();
		
		String[] titles = new String[maxLength + 1];
		titles[0] = "id";
		for(int i = 0 ; i < maxLength ; ++i) 
			titles[i + 1] = String.valueOf(i + 1);
		
		Object[][] data = new Object[seqs.size()][maxLength + 1];
		int seqId = 0;
		for(ViewerNeuclotid[] seq : seqs) {
			data[seqId][0] = ids.get(seqId);
			System.arraycopy(seq, 0, data[seqId], 1, seq.length);
			seqId++;
		}
		
		myTable = new JTable(data, titles);
		myTable.setDefaultRenderer(Object.class, new MyRenderer());
    }
    
    
    
    private class MyRenderer extends DefaultTableCellRenderer {
		
		private static final long serialVersionUID = -569109969253510109L;
		
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (value instanceof ViewerNeuclotid) {
				ViewerNeuclotid nc = (ViewerNeuclotid)value;
				switch (nc.getOrder()) {
				case MATCH_CHAR:
					c.setBackground(table.getBackground());
					break;
				case INSERT_CHAR:
					c.setBackground(Color.RED);
					break;
				case DELETE_CHAR:
					c.setBackground(Color.GREEN);
					break;
				default:
					c.setBackground(Color.ORANGE);
					break;
				}
			}
			else
			    c.setBackground(table.getBackground());

			return c;
		}

	}

    public static void main(String args[]) throws IOException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                //System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
       
        new AlignmentViewer();
    }
    
}