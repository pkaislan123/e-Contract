package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroParcela extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	
	
	public TelaCadastroParcela() {
		//define largura x altura da janela
	    setBounds(0, 0, 675, 598);
	    
	    //referencia explicita do painel principal
	    setContentPane(painelPrincipal); 
	    
	    
	    //define o miglayout com duas colunas e 3 telas
	    painelPrincipal.setLayout(new MigLayout("", "[grow][60px]", "[][][][]"));
	    
	    
	    
	    
	}

}
