package main.java.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

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
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;



import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.Label;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Panel;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.ConverterToImagem;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.Gerador_Desenho;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

public class TelaRecortarImagem2 extends JDialog{

	private final JPanel painelPrincipal = new JPanel();
    Dimension Dimensao = Toolkit.getDefaultToolkit().getScreenSize();  
    private BufferedImage Buffered_da_Imagem = new BufferedImage((int)Dimensao.getWidth(),
            (int)Dimensao.getHeight(), BufferedImage.TYPE_INT_RGB);
    private JFrame telaPaiJFrame;
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

	 GraphicsEnvironment ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 GraphicsDevice[] gds = ge.getScreenDevices();
	 
	public TelaRecortarImagem2(String imagem, JFrame janela_pai) {
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
		setBounds(100, 100, 1029, 701);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		 painelPrincipal.setLayout(new MigLayout("", "[][][][][grow][21px][grow][14px][grow]", "[][grow][]"));
		
		
		
		
		 lblCtrlPressionado = new JLabel("CTRL Solto");
		 lblCtrlPressionado.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblCtrlPressionado, "cell 4 0,grow");
		
		JButton btnNewButton = new JButton("Concluir");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setForeground(Color.WHITE);
		painelPrincipal.add(btnNewButton, "cell 8 2,growx,aligny top");
	 
		
		
		
		Gerador_Desenho panel = new Gerador_Desenho(image, width, height);
		panel.setBounds(0,0,width, height);
		panel.setTelaPai(isto);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		painelPrincipal.add(scrollPane, "cell 0 1 9 1,grow");
		
		JLabel lblNewLabel = new JLabel("Porcentagem");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 0,growx,aligny center");
		
		 lblPorcentagem = new JLabel("100%");
		 lblPorcentagem.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblPorcentagem, "cell 2 0,growx,aligny center");
		
		JButton btnNewButton_1 = new JButton("Cortar");
		btnNewButton_1.setBackground(new Color(204, 51, 0));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.recortar();
			}
		});
		painelPrincipal.add(btnNewButton_1, "cell 6 2,growx,aligny top");
		
		JButton btnNewButton_2 = new JButton("Desfazer Última alteração");
		btnNewButton_2.setBackground(new Color(204, 102, 0));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.desfazer();
			}
		});
		painelPrincipal.add(btnNewButton_2, "cell 4 2,growx,aligny top");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String caminh_imagem = panel.retornarImagem();
				if(caminh_imagem != null) {
					//((TelaGerenciarContrato) telaPai).caminho_salvar_comprovante_pagamento(caminh_imagem);
					((TelaGerenciarContrato) telaPaiJFrame).caminho_salvar_comprovante_pagamento(caminh_imagem);
					
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao salvar a imagem recortada\nConsulte o administrador!");
				}
			}
		});
			
			
	    		this.setLocationRelativeTo(janela_pai);

	        

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
	
	


	  

	  
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}	
       
              
}
