package app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	private static final long serialVersionUID = -4376019532039732441L;
	
	private BufferedImage image;

    public ImagePanel() {
       try {                
          image = ImageIO.read(new File("lib/options.jpg"));
       } catch (IOException ex) {
    	   ex.printStackTrace();
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);             
    }

}
