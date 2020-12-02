package outros;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class JButtonBackground extends JButton{
	 private ImageIcon img;

	    
	    public JButtonBackground(){
           super();
	       img = new ImageIcon();
	       
	       setOpaque(false);
	        this.setBackground(new Color( 255, 255, 255, 0));
	    }
	    
	    @Override
	    public void paintComponent(Graphics g){
	        super.paintComponent(g);
	        

		    // setando a cor para um branco semitransparente
		    //g.setColor( new Color( 173, 216, 230, 100 ));
		    g.setColor( new Color( 0, 0, 0, 0 ));
		    // desenha um ret�ngulo em toda a extens�o do painel
		    g.fillRect( 0, 0, getWidth(), getHeight() );
	        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	        
	    }
	    
	    public void setImg(ImageIcon img2){
	        this.img = img2;
	    }
	    
	    public ImageIcon getImg(){
	        return this.img;
	    }
	    
	    @Override
		public Color getBackground()
		{
			return new Color( 0, 0, 0, 0 );
		}
}
