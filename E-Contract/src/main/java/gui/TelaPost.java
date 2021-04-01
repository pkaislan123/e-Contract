package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;




import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
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
import main.java.conexaoBanco.GerenciarBancoNotas;
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
public class TelaPost extends JDialog {
	private TelaPost isto;
	private final JLabel lblNomeNotaGlobal = new JLabel("Anotação 1");
	private final JTextArea textAreaTextoGlobal = new JTextArea();
	private final JButton btnVizualizarNotaGlobal = new JButton("Vizualizar");
	private final JButton btnMudarGlobal = new JButton("Próximo");
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JTextArea textAreaTexto;
	private JLabel lblNomeNota;
	private ConfiguracoesGlobais configs_globais;
	public boolean ativo = false;
	private JPanel panel;
	
	public TelaPost(Window janela_pai) {
		getContentPane().setBackground(new Color(255, 255, 204));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		 panel = new JPanel();
		panel.setBackground(new Color(255, 255, 204));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		 lblNomeNota = new JLabel("New label");
		lblNomeNota.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNomeNota = new GridBagConstraints();
		gbc_lblNomeNota.fill = GridBagConstraints.VERTICAL;
		gbc_lblNomeNota.gridwidth = 5;
		gbc_lblNomeNota.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomeNota.gridx = 0;
		gbc_lblNomeNota.gridy = 0;
		panel.add(lblNomeNota, gbc_lblNomeNota);
		
		 textAreaTexto = new JTextArea();
		 textAreaTexto.setWrapStyleWord(true);
		 textAreaTexto.setLineWrap(true);
		 textAreaTexto.setEditable(false);
		textAreaTexto.setBorder(new EmptyBorder(0, 0, 0, 0));
		textAreaTexto.setFont(new Font("SansSerif", Font.BOLD, 18));
		textAreaTexto.setBackground(new Color(255, 255, 204));
		GridBagConstraints gbc_textAreaTexto = new GridBagConstraints();
		gbc_textAreaTexto.gridheight = 3;
		gbc_textAreaTexto.gridwidth = 5;
		gbc_textAreaTexto.fill = GridBagConstraints.BOTH;
		gbc_textAreaTexto.gridx = 0;
		gbc_textAreaTexto.gridy = 1;
		panel.add(textAreaTexto, gbc_textAreaTexto);

		 isto = this;
		setResizable(true);
	
		getDadosGlobais();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Topicos");
		setBounds(0, 0, 352, 251);
		
 
		   Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - getWidth( ) ;
	        int y = 10 ;
		
			//setAlwaysOnTop(true);

			
		
			    GraphicsDevice[] gd = ge.getScreenDevices();
			    if(gd.length > 3) {
				        rect = gd[1].getDefaultConfiguration().getBounds();
				         x = (int) rect.getMaxX() - getWidth( ) ;
				         y = 80 ;
			           this.setLocation(x, y);

			    }else {
			           this.setLocation(x, y);

			    }
			
			
			    procurarNotas(true);
			    isto.setVisible(true);
	}
	
	

	public void procurarNotas(boolean _ativo) {
		this.ativo = _ativo;

		new Thread() {
			@Override
			public void run() {
				
				while(true) {
					
					//pegar minhas anotacoes
					ArrayList<CadastroNota> minhas_notas = new ArrayList<>();
					ArrayList<CadastroNota> todas_as_notas = new ArrayList<>();

					GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
					
					minhas_notas = gerenciar.getnotas(login.getId());
					todas_as_notas = gerenciar.getTodasNotas();
					//verifica quais notas sao de topico
					ArrayList<CadastroNota> minhas_notas_topico = new ArrayList<>();
					for(CadastroNota not: minhas_notas) {
						if(not.getTipo() == 2) {
							//se e uma nota do tipo topico
							minhas_notas_topico.add(not);
						}
					}
					
					//verifica quais notas sao de topico global
					ArrayList<CadastroNota> notas_topico_global = new ArrayList<>();
					for(CadastroNota not: minhas_notas) {
						if(not.getTipo() == 3) {
							//se e uma nota do tipo topico global
							notas_topico_global.add(not);
						}
					}
					
					//verifica quais notas sao de topico global de outros usuarios
					ArrayList<CadastroNota> notas_topico_global_outros_usuarios = new ArrayList<>();
					for(CadastroNota not: todas_as_notas) {
						if(not.getTipo() == 3) {
							//se e uma nota do tipo topico global
							notas_topico_global_outros_usuarios.add(not);
						}
					}
					
					ArrayList<CadastroNota> notas_mostrar = new ArrayList<>();
					notas_mostrar.addAll(notas_topico_global);
					notas_mostrar.addAll(minhas_notas_topico);
					notas_mostrar.addAll(notas_topico_global_outros_usuarios);

					
					if(notas_mostrar.size() > 0) {
						for(CadastroNota not: notas_mostrar) {

							java.awt.EventQueue.invokeLater(new Runnable() { 
							    public void run() { 
							    	lblNomeNota.setText(not.getNome());
							    	
							    	
									textAreaTexto.setText(not.getTexto());
									panel.repaint();
									panel.updateUI();
								
                                
											   
							    } 
							}); 
					    
						
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						
						
						
						
					}
					
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}.start();
	
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
}
