package outros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class JButtonPersonalizado extends JButton {
	private Shape shape;
    public JButtonPersonalizado(String texto) {
        super();
        //this.setBackground(getBackground());
//        this.setBackground(  new Color( 173, 216, 230, 100 ));
      
        //define a cor do texto, fonte e tamanho da letra
       this.setForeground(Color.WHITE); // altera a cor da fonte
       this.setFont(new Font("Times New Roman", Font.BOLD, 18));
       this.setText(texto);
      
    }
    protected void paintComponent(Graphics g) {
         //g.setColor(new Color(0,0,0,0));
    	g.setColor(getBackground());

    	 g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 100, 100);
         super.paintComponent(g);
    }
    
   
   
}
