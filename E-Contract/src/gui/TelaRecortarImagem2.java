package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JViewport;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import outros.Gerador_Desenho;
import outros.JPanelBackground;
import outros.JPanelTransparent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import manipular.ConverterToImagem;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Panel;



public class TelaRecortarImagem2 extends JDialog{

	private final JPanel painelPrincipal = new JPanel();
    Dimension Dimensao = Toolkit.getDefaultToolkit().getScreenSize();  
    private BufferedImage Buffered_da_Imagem = new BufferedImage((int)Dimensao.getWidth(),
            (int)Dimensao.getHeight(), BufferedImage.TYPE_INT_RGB);
   
    private int valor;
    private Color Ultima_Cor;
    private int x;
    private int y;  
    private BufferedImage Buffered_da_Reta = new BufferedImage((int)Dimensao.getWidth(),
            (int)Dimensao.getHeight(), BufferedImage.TYPE_INT_RGB);
	private JDialog telaPai;
    private int zoom = 100;
	private TelaRecortarImagem2 isto ;
	private BufferedImage image = null ;
	int width   = 0;
	int height = 0 ;
	 private static volatile boolean controlStatus = false;
	 private JLabel lblCtrlPressionado, lblPorcentagem;

	public TelaRecortarImagem2(String imagem) {
		setModal(true);
		
		
		 imagem = testarTipoDoc(imagem);
		
		ImageIcon img = null;
		isto = this;
		
		 try {
		    	
				 image = ImageIO.read(new File(imagem));
				 width   = image.getWidth();
				 height   = image.getHeight();
				 img = new ImageIcon(image);
		

					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		setTitle("E-Contract - Importar Imagem");


		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 654, 635);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		
		
		 lblCtrlPressionado = new JLabel("CTRL Solto");
		lblCtrlPressionado.setBounds(241, 0, 156, 22);
		painelPrincipal.add(lblCtrlPressionado);
		
		JButton btnNewButton = new JButton("Concluir");
		
		btnNewButton.setBounds(549, 554, 71, 23);
		painelPrincipal.add(btnNewButton);
	 
		
		
		
		Gerador_Desenho panel = new Gerador_Desenho(image, width, height);
		panel.setBounds(0,0,width, height);
		panel.setTelaPai(isto);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 30, 638, 504);
		painelPrincipal.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Porcentagem");
		lblNewLabel.setBounds(10, 4, 78, 14);
		painelPrincipal.add(lblNewLabel);
		
		 lblPorcentagem = new JLabel("100%");
		lblPorcentagem.setBounds(98, 4, 89, 14);
		painelPrincipal.add(lblPorcentagem);
		
		JButton btnNewButton_1 = new JButton("Cortar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.recortar();
			}
		});
		btnNewButton_1.setBounds(446, 554, 89, 23);
		painelPrincipal.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Desfazer");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.desfazer();
			}
		});
		btnNewButton_2.setBounds(336, 554, 89, 23);
		painelPrincipal.add(btnNewButton_2);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String caminh_imagem = panel.retornarImagem();
				if(caminh_imagem != null) {
					((TelaGerenciarContrato) telaPai).caminho_salvar_comprovante_pagamento(caminh_imagem);
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao salvar a imagem recortada\nConsulte o administrador!");
				}
			}
		});
			
		this.setLocationRelativeTo(null);
		setResizable(false);

		
		
	}
	
	
	
	
	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	
	public String testarTipoDoc(String imagem) {
		
		ConverterToImagem converter = new ConverterToImagem();
		if(imagem.endsWith(".pdf")) {
			//é um pdf, converter para png
			return converter.to_img(imagem);
		}else {
			return imagem;
		}
		
	}
	
	
	public void setPorcentagem(String texto) {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
				lblPorcentagem.setText(texto);

		    } 
		}); 
		
		    
	}
	
	public void setCtrl(String text) {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		lblCtrlPressionado.setText(text);

		   
		    } 
		}); 
		
	}
	
	


	  

	  
	 
              
}
