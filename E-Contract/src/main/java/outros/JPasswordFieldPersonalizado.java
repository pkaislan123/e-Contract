package main.java.outros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPasswordField;

public class JPasswordFieldPersonalizado extends JPasswordField{
	private Shape shape;
    public JPasswordFieldPersonalizado(int size) {
        super(size);
        setOpaque(false);
        this.setBackground(new Color( 255, 255, 255, 10));
//        this.setBackground(  new Color( 173, 216, 230, 100 ));
      
        //define a cor do texto, fonte e tamanho da letra
       this.setForeground(Color.WHITE); // altera a cor da fonte
       this.setFont(new Font("Times New Roman", Font.BOLD, 18));
      
    }
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());

    	//g.setColor(new Color( 0, 0, 0, 0 ));
       // g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         g.drawRoundRect(0, getHeight()-1,getWidth()-1,1, 15, 15);
         
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
    
    
   
}
