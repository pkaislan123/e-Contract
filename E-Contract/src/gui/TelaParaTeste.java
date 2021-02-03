package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.border.LineBorder;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import javax.swing.border.MatteBorder;

public class TelaParaTeste extends JDialog implements ComponentListener{

	private final JPanel painelPrincipal = new JPanel();


	private TelaParaTeste isto ;

	 GraphicsEnvironment ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 GraphicsDevice[] gds = ge.getScreenDevices();
	 
	public TelaParaTeste(int index_tela_pai, JFrame janelaPai) {
        super(janelaPai, true); 

		//setModal(true);

		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		isto = this;
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 510, 319);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		isto.addComponentListener(this);

	
	    		
		
		  
		GraphicsConfiguration gc = janelaPai.getGraphicsConfiguration();
		Rectangle bounds = gc.getBounds();
		  
		  
		Point realLocation = new Point(); // holds final location of dialog.
		realLocation.x = (bounds.x + bounds.width / 2) - (isto.getWidth() / 2);
		realLocation.y = (bounds.y + bounds.height / 2 )- (isto.getHeight() / 2);
		  
		this.setLocation(realLocation);

	    		        
	    

		
		
	}
	
	
	
    public void componentMoved(ComponentEvent e) {
    	JDialog d = (JDialog) e.getComponent();
    }

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
