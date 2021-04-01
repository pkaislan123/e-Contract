package main.java.outros;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelTransparent extends JPanel {

	private ImageIcon img;
	private int transparencia = 1;
	public JPanelTransparent() {
		super();
		
		
		setOpaque(false);
		img = new ImageIcon();

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// setando a cor para um branco semitransparente
		if(transparencia == 1)
			g.setColor(new Color(0, 0, 0, 0));
		else if(transparencia == 2)
		  g.setColor( new Color( 173, 216, 230, 100 ));
		else
			g.setColor( new Color( 173, 216, 230, 100 ));

		 //

		// desenha um ret�ngulo em toda a extens�o do painel
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

	}

	public void setLayoutManager(LayoutManager layout) {
		this.setLayout(layout);

	}
	
	public void setTransparencia(int i) {
		this.transparencia = i;
	}

	@Override
	public Color getBackground() {
		return new Color(0, 0, 0, 0);
	}

	public void setImg(ImageIcon img2) {
		this.img = img2;
	}

	public ImageIcon getImg() {
		return this.img;
	}
}
