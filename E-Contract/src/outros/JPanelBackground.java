package outros;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelBackground extends JPanel {

	 private ImageIcon img;
	    
	    public JPanelBackground(){
	       img = new ImageIcon();
	    }
	    
	    @Override
	    public void paintComponent(Graphics g){
	        super.paintComponent(g);
	        
	        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	        
	    }
	    
	    public void setImg(ImageIcon img2){
	        this.img = img2;
	    }
	    
	    public ImageIcon getImg(){
	        return this.img;
	    }
}
