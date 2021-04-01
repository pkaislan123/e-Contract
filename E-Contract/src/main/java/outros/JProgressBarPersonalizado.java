package main.java.outros;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

public class JProgressBarPersonalizado extends JProgressBar{
	
	 private ImageIcon img;
	    
	    
	   
	  
		
	public JProgressBarPersonalizado() {
	    super();
	    setOpaque( false );
	       img = new ImageIcon();

	}

	public void paintComponent( Graphics g ) {

	    super.paintComponent( g );

	    // setando a cor para um branco semitransparente
	    //g.setColor( new Color( 173, 216, 230, 100 ));
	    g.setColor( new Color( 0, 0, 0, 0 ));
	    // desenha um ret�ngulo em toda a extens�o do painel
	    g.fillRect( 0, 0, getWidth(), getHeight() );
       //g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

	}
	
	public void setLayoutManager(LayoutManager layout)
	{
	    this.setLayout(layout);

	}
	
	@Override
	public Color getBackground()
	{
		return new Color( 0, 0, 0, 0 );
	}
	
	

}
