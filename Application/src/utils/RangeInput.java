package utils;

import general.Chromosome;
import general.range.Range;

import java.awt.Choice;
import java.awt.Label;
import java.awt.Panel;

import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import Games.whatsIntheBin.InterestingGene;

public class RangeInput extends Panel{
	
	private static final long serialVersionUID = 2440124582812987593L;
	
	private Choice chromosome;
	private JTextField startOffset;
	private JTextField endOffset;
	
	public RangeInput() {
		setLayout(new MigLayout("", "[grow][30%][40%][40%]", ""));
		
		Label chrNameLabel = new Label("Input range");
		add(chrNameLabel, "left, cell 0 0");
		
		chromosome = new Choice();
		for (Chromosome option : Chromosome.values()) {
			chromosome.add(option.toString());
		}
		add(chromosome, "right, cell 1 0");
		
		startOffset = new JTextField("start offset");
		add(startOffset, "flowx,cell 2 0,growx");
		
		endOffset = new JTextField("end offset");
		add(endOffset, "flowx,cell 3 0,growx");
	}
	
	public InterestingGene getRange() {
		int start = Integer.parseInt(startOffset.getText());
		int end = Integer.parseInt(endOffset.getText());
		return new InterestingGene(Chromosome.valueOf(chromosome.getSelectedItem()), new Range(start, end), true);
	}

}
