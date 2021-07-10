package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;



import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
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
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

public class TelaGerenciarTransportador extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private TelaGerenciarTransportador isto;
    private KGradientPanel menu_lateral;
    private  JPanel panel_docs;
	private JTree arvore_documentos;

    DefaultMutableTreeNode no_comprovantes; 
	DefaultMutableTreeNode no_docs_pessoais ;
	DefaultMutableTreeNode no_outros ;
	private DefaultMutableTreeNode no_selecionado;
    private CadastroCliente cliente_local;
    private  JComboBox cBTipoDocumento ;
    private TelaTodasNotasFiscais telaTodasNotasFiscais;
    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private  JTextArea entDescricaoDocumento;
	private TelaRomaneios telaRomaneio;
	private JLabel lblTipoPessoa, lblTipoIdentificacao, lblIdentificacao, lblIe, lblIE, lblStatus, lblEndereco;

    private JLabel lblNivel;
	public TelaGerenciarTransportador(CadastroCliente transportador_selecionado, Window janela_pai) {
		//setModal(true);
		
		
		getDadosGlobais();

		 isto = this;
		 cliente_local = transportador_selecionado;
		
		setResizable(true);
		setTitle("E-Contract - Gerenciar Cliente");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1078, 595);
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		JPanel painelDeposito = new JPanel();
		painelDeposito.setBackground(Color.WHITE);
		painelDeposito.setVisible(false);
		painelDeposito.setEnabled(false);
		painelDeposito.setBounds(198, 153, 864, 358);
		painelPrincipal.add(painelDeposito);
		painelDeposito.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Romaneios\r\n");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_2.setBackground(new Color(0, 0, 0));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    TelaRomaneios telaRomaneio;
					telaRomaneio = new TelaRomaneios(1,isto);
					telaRomaneio.setTelaPai(isto);
					telaRomaneio.setClienteSelecionado(transportador_selecionado);
				    telaRomaneio.pesquisarTodosOsRomaneiosTransportador();
					telaRomaneio.setVisible(true);
				
				
			}
		});
		btnNewButton_2.setBounds(437, 290, 115, 33);
		painelDeposito.add(btnNewButton_2);
		
		JPanel painelDadosIniciais = new JPanel();
		painelDadosIniciais.setBackground(new Color(0, 128, 128));
		painelDadosIniciais.setBounds(198, 153, 864, 358);
		painelPrincipal.add(painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(0, 0, 102));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroTransportadores telaEdicao = new TelaCadastroTransportadores(1, transportador_selecionado, null,isto);
				telaEdicao.setVisible(true);
			}
		});
		btnEditar.setBounds(629, 297, 89, 23);
		painelDadosIniciais.add(btnEditar);
		
		JPanel painelInfo = new JPanel();
		painelInfo.setBackground(new Color(0, 128, 128));
		painelInfo.setBounds(22, 11, 696, 275);
		painelDadosIniciais.add(painelInfo);
		painelInfo.setLayout(new MigLayout("", "[][]", "[][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("TIpo Pessoa:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblNewLabel, "cell 0 0");
		
		
		
		 lblTipoPessoa = new JLabel("Juridica");
		 lblTipoPessoa.setForeground(Color.WHITE);
		 lblTipoPessoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		 painelInfo.add(lblTipoPessoa, "cell 1 0");
		 
		  lblTipoIdentificacao = new JLabel("CPF/CNPJ:");
		  lblTipoIdentificacao.setForeground(Color.WHITE);
		  lblTipoIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 14));
		  painelInfo.add(lblTipoIdentificacao, "cell 0 1,alignx right");
		  
		   lblIdentificacao = new JLabel("");
		   lblIdentificacao.setForeground(Color.WHITE);
		   lblIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 16));
		   painelInfo.add(lblIdentificacao, "cell 1 1");
		   
		    lblIe = new JLabel("IE:");
		    lblIe.setForeground(Color.WHITE);
		    lblIe.setFont(new Font("Tahoma", Font.BOLD, 14));
		    painelInfo.add(lblIe, "cell 0 3,alignx right");
		    
		    lblIE = new JLabel("120.927.986-00");
		    lblIE.setForeground(Color.WHITE);
		    lblIE.setFont(new Font("Tahoma", Font.BOLD, 16));
		    painelInfo.add(lblIE, "cell 1 3,alignx left");
		    
		    JLabel lblnasdad = new JLabel("Status:");
		    lblnasdad.setForeground(Color.WHITE);
		    lblnasdad.setFont(new Font("Tahoma", Font.BOLD, 14));
		    painelInfo.add(lblnasdad, "cell 0 4,alignx right");
		    
		     lblStatus = new JLabel("");
		     lblStatus.setForeground(Color.WHITE);
		     lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		     painelInfo.add(lblStatus, "cell 1 4,alignx left");
		     
		     JLabel lblEndereo = new JLabel("Endereço:");
		     lblEndereo.setForeground(Color.WHITE);
		     lblEndereo.setFont(new Font("Tahoma", Font.BOLD, 14));
		     painelInfo.add(lblEndereo, "cell 0 6,alignx right");
		     
		      lblEndereco = new JLabel("Rodovia MG 188, km 242, Zona Rural, Guarda-Mor/MG 38570-000");
		      lblEndereco.setForeground(Color.WHITE);
		      lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 16));
		      painelInfo.add(lblEndereco, "cell 1 6");
		      lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		      lblNewLabel.setBounds(45, 67, 115, 20);
		
		
			
		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 255, 204);
		menu_lateral.kEndColor = Color.BLUE;
		menu_lateral.setBounds(0, 0, 200, 556);
		 painelPrincipal.add(menu_lateral);
		 menu_lateral.setLayout(null);
		 
		 JPanelTransparent panel = new JPanelTransparent();
		 panel.setLayout(null);
		 panel.setBounds(10, 167, 181, 181);
		 menu_lateral.add(panel);
		 
		 JLabel btnDocumentos = new JLabel("Documentos");
		 btnDocumentos.setForeground(Color.WHITE);
		 btnDocumentos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDocumentos.setBackground(new Color(0, 0, 0, 100));
		 btnDocumentos.setBounds(10, 53, 146, 20);
		 panel.add(btnDocumentos);
		 
		 JLabel btnDadosIniciais = new JLabel("Dados Inicias");
		 btnDadosIniciais.setOpaque(true);
		 btnDadosIniciais.setForeground(Color.WHITE);
		 btnDadosIniciais.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
		 btnDadosIniciais.setBounds(10, 22, 161, 20);
		 panel.add(btnDadosIniciais);
		 
		
		 

		 KGradientPanel panelTopo = new KGradientPanel();
		 panelTopo.kStartColor = new Color(51, 0, 51);
		 panelTopo.kEndColor = new Color(153, 51, 204);
		 panelTopo.setBounds(172, 65, 890, 90);
		 painelPrincipal.add(panelTopo);
		 panelTopo.setLayout(null);
		 
		 String nome ="";
		 if(transportador_selecionado.getTipo_pessoa() == 0)
		    nome = transportador_selecionado.getNome_empresarial();
		 else
			 nome = transportador_selecionado.getNome_fantaia();
		 
		 
		 JLabel lblNomeUsuario = new JLabel(nome.toUpperCase());
		 lblNomeUsuario.setForeground(Color.WHITE);
		 lblNomeUsuario.setFont(new Font("Tahoma", Font.BOLD, 23));
		 lblNomeUsuario.setBounds(44, 32, 567, 33);
		 panelTopo.add(lblNomeUsuario);
		 
		  lblNivel = new JLabel("");
		 lblNivel.setBounds(641, 16, 170, 30);
		 panelTopo.add(lblNivel);

		 
		 JButton btnSair = new JButton("Sair");
		 btnSair.setForeground(Color.WHITE);
		 btnSair.setBackground(new Color(153, 0, 0));
		 btnSair.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnSair.setBounds(923, 522, 89, 23);
		 painelPrincipal.add(btnSair);
		       
		        panel_docs = new JPanel();
		        panel_docs.setBackground(Color.WHITE);
		        panel_docs.setBounds(43, 11, 315, 336);
		        
		         
		         JPanel painelDocumentos = new JPanel();
		         painelDocumentos.setVisible(false);
		         painelDocumentos.setEnabled(false);
		         painelDocumentos.setBounds(198, 153, 864, 358);
		         painelPrincipal.add(painelDocumentos);
		         painelDocumentos.setLayout(null);
		         
		         painelDocumentos.add(panel_docs);
		         
		         JPanel painelInserirDocumento = new JPanel();
		         painelInserirDocumento.setLayout(null);
		         painelInserirDocumento.setBounds(381, 35, 431, 288);
		         painelDocumentos.add(painelInserirDocumento);
		         
		         JLabel lblNewLabel_15 = new JLabel("Nome:");
		         lblNewLabel_15.setBounds(34, 11, 46, 14);
		         painelInserirDocumento.add(lblNewLabel_15);
		         
		         JLabel lblNewLabel_16 = new JLabel("Descrição:");
		         lblNewLabel_16.setBounds(19, 105, 70, 14);
		         painelInserirDocumento.add(lblNewLabel_16);
		         
		          entDescricaoDocumento = new JTextArea();
		          entDescricaoDocumento.setBounds(75, 100, 330, 85);
		          painelInserirDocumento.add(entDescricaoDocumento);
		          
		          JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		          lblNewLabel_17.setBounds(19, 210, 46, 14);
		          painelInserirDocumento.add(lblNewLabel_17);
		          
		          entCaminhoDocumento = new JTextField();
		          entCaminhoDocumento.setColumns(10);
		          entCaminhoDocumento.setBounds(75, 198, 231, 39);
		          painelInserirDocumento.add(entCaminhoDocumento);
		          
		          entNomeDocumento = new JTextField();
		          entNomeDocumento.setColumns(10);
		          entNomeDocumento.setBounds(75, 8, 330, 27);
		          painelInserirDocumento.add(entNomeDocumento);
		          
		          JButton btnSelecionarDocumento = new JButton("Selecionar");
		          btnSelecionarDocumento.addActionListener(new ActionListener() {
		          	public void actionPerformed(ActionEvent e) {
		          		
		          		selecionarDocumento();
		          	}
		          });
		          btnSelecionarDocumento.setBounds(316, 205, 89, 24);
		          painelInserirDocumento.add(btnSelecionarDocumento);
		          
		          JButton btnAdicionarDocumento = new JButton("Adicionar");
		          btnAdicionarDocumento.addActionListener(new ActionListener() {
		          	public void actionPerformed(ActionEvent e) {
		          		adicionarNovoDocumento();
		          	}
		          });
		          btnAdicionarDocumento.setBounds(316, 254, 89, 23);
		          painelInserirDocumento.add(btnAdicionarDocumento);
		          
		          JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		          lblNewLabel_16_1.setBounds(19, 58, 70, 14);
		          painelInserirDocumento.add(lblNewLabel_16_1);
		          
		           cBTipoDocumento = new JComboBox();
		           cBTipoDocumento.setBounds(75, 54, 330, 22);
		           painelInserirDocumento.add(cBTipoDocumento);
		           cBTipoDocumento.addItem("Documento Pessoal");
		           cBTipoDocumento.addItem("Comprovantes");
		           cBTipoDocumento.addItem("Outros");
		 btnSair.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		isto.dispose();
		 	}
		 });

		 JLabel btnDeposito = new JLabel("Romaneios");
		 btnDeposito.setForeground(Color.WHITE);
		 btnDeposito.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDeposito.setBackground(new Color(0, 0, 0, 100));
		 btnDeposito.setBounds(10, 85, 146, 20);
		 panel.add(btnDeposito);
			
		
		 btnDadosIniciais.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					painelDocumentos.setEnabled(false);
					painelDocumentos.setVisible(false);
					
					painelDadosIniciais.setEnabled(true);
					painelDadosIniciais.setVisible(true);

				

					painelDeposito.setEnabled(false);
					painelDeposito.setVisible(false);
					
					btnDadosIniciais.setOpaque(true);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(false);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();
					
					btnDeposito.setOpaque(false);
					btnDeposito.setBackground(new Color(0, 0, 0, 100));

					btnDeposito.repaint();
					btnDeposito.updateUI();

				

				}
			});

		
		 btnDocumentos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					

					painelDadosIniciais.setEnabled(false);
					painelDadosIniciais.setVisible(false);
					
					painelDocumentos.setEnabled(true);
					painelDocumentos.setVisible(true);



					painelDeposito.setEnabled(false);
					painelDeposito.setVisible(false);
					
				
					btnDadosIniciais.setOpaque(false);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(true);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();

					btnDeposito.setOpaque(false);
					btnDeposito.setBackground(new Color(0, 0, 0, 100));

					btnDeposito.repaint();
					btnDeposito.updateUI();
		
				
				}
			});
		 
		 
	
		 
		 btnDeposito.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);
				
				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				
				painelDeposito.setEnabled(true);
				painelDeposito.setVisible(true);
				
				btnDeposito.setOpaque(true);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();
				
				
				btnDadosIniciais.setOpaque(false);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();
				
				

				
		 	}
		 });
		

	
		   setInformacoesDocumentos();
	     setInfo();
	    // setInfoContratosComoVendedor();
	    // setInfoContratosComoComprador();

	     
	  
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(cliente_local.getId());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				panel_docs.setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setBounds(10, 5, 295, 320);
				panel_docs.add(panel);
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos deste Cliente:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel_18, "cell 0 0");

			
				
				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				 no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				 no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				 no_outros = new DefaultMutableTreeNode("Outros");
				 
				 
				// add the child nodes to the root node
				root.add(no_docs_pessoais);
				root.add(no_comprovantes);
				root.add(no_outros);

				// create the tree by passing in the root node
				arvore_documentos = new JTree(root);
				
			
		        
				arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
				    @Override
				    public void valueChanged(TreeSelectionEvent e) {
				        no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
				       // lblNoSelecionado.setText(no_selecionado.getUserObject().toString());
				    }
				});
				JPopupMenu jPopupMenu = new JPopupMenu();
				JMenuItem jMenuItemVizualizar= new JMenuItem();
				JMenuItem jMenuItemExcluir= new JMenuItem();

				jMenuItemVizualizar.setText("Vizualizar");
				jMenuItemExcluir.setText("Excluir");

				
				jMenuItemVizualizar.addActionListener(
						  new java.awt.event.ActionListener() {
						    // Importe a classe java.awt.event.ActionEvent
						    public void actionPerformed(ActionEvent e) { 
						    	String nome_arquivo = no_selecionado.getUserObject().toString();
						    
						    	String quebra [] = nome_arquivo.split("@");
								
								String nome_official = "";
								for(int i = 1; i < quebra.length ; i++) {
									nome_official += quebra[i];
								}
								
								String nome_pasta = "";
								if(cliente_local.getTipo_pessoa() == 0)
									nome_pasta = cliente_local.getNome_empresarial();
								else
									nome_pasta = cliente_local.getNome_fantaia();
								

						    	
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta + "\\DADOS PESSOAS\\"  + nome_official;

								if (Desktop.isDesktopSupported()) {
									try {
										Desktop desktop = Desktop.getDesktop();
										File myFile = new File(caminho_salvar);
										desktop.open(myFile);
									} catch (IOException ex) {
									}
								}
						    }
						  });
				
				jMenuItemExcluir.addActionListener(
						  new java.awt.event.ActionListener() {
						    // Importe a classe java.awt.event.ActionEvent
						    public void actionPerformed(ActionEvent e) { 
						    	/*if (JOptionPane.showConfirmDialog(isto, 
							            "Deseja Excluir este comprovante", "Exclusão", 
							            JOptionPane.YES_NO_OPTION,
							            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
									     
						    		String nome_arquivo = no_selecionado.getUserObject().toString();
						    		String quebra [] = nome_arquivo.split("@");
									String id = quebra[0];
									int i_id = Integer.parseInt(id);
									
									String nome_official = "";
									for(int i = 1; i < quebra.length ; i++) {
										nome_official += quebra[i];
									}
							    	String unidade_base_dados = configs_globais.getServidorUnidade();
									String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\\" + nome_official;

									boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
									if(excluido) {
										
									
										
										GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
										boolean excluir_documento = gerenciar_docs.removerDocumento(i_id);
										
										if(excluir_documento) {
											JOptionPane.showMessageDialog(null, "Comprovante Excluido!");

										}else {
											JOptionPane.showMessageDialog(null, "Arquivo fisico apagado, mas as informações\ndeste comprovante ainda estão no \nConsulte o administrador");

										}
										
                                        atualizarArvoreDocumentos();
										
									}else {
										JOptionPane.showMessageDialog(null, "Erro ao excluir o comprovante\nConsulte o administrador!");
									}
									
							        }
								else
								{
									
									
								}
						 */		
						    } 
						  
						  });
				
				jPopupMenu.add(jMenuItemVizualizar);
				jPopupMenu.add(jMenuItemExcluir);

				arvore_documentos.addMouseListener(
						  new java.awt.event.MouseAdapter() {
						    //Importe a classe java.awt.event.MouseEvent
						    public void mouseClicked(MouseEvent e) {
						      // Se o botão direito do mouse foi pressionado
						      if (e.getButton() == MouseEvent.BUTTON3){
						        // Exibe o popup menu na posição do mouse.
						        jPopupMenu.show(arvore_documentos, e.getX(), e.getY());
						      }
						    }
						  });
				
				    arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_docs_pessoais = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_docs_pessoais.png"));
					ImageIcon icone_comprovantes = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_comprovantes.png"));
					ImageIcon icone_outros = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_outros.png"));
 
					@Override
			            public Component getTreeCellRendererComponent(JTree tree,
			                    Object value, boolean selected, boolean expanded,
			                    boolean isLeaf, int row, boolean focused) {
						
						 DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
					        String s = node.getUserObject().toString();
					        if ("Documentos Pessoais".equals(s)) {
					        	 setOpenIcon(icone_docs_pessoais);
					             setClosedIcon(icone_docs_pessoais);

					        } else if("Comprovantes".equals(s)) {
			                    setOpenIcon(icone_comprovantes);
					             setClosedIcon(icone_comprovantes);

					        }else if("Outros".equals(s)) {
			                    setOpenIcon(icone_outros);
					             setClosedIcon(icone_outros);

					        }
					        super.getTreeCellRendererComponent(
					            tree, value, selected, expanded, isLeaf, row, hasFocus);
						
						
						
					        return this;
			            }
				            
			        });
				 
				arvore_documentos.setShowsRootHandles(true);
				arvore_documentos.setRootVisible(false);
				panel.add(arvore_documentos, "cell 0 1,grow");
				
				
				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							no_docs_pessoais.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// outros
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());
				
				
				
				

			}
		});

	}
	
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}

	
	public void selecionarDocumento() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o documento a anexar!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				entCaminhoDocumento.setText(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
	}
	
	
	public void adicionarNovoDocumento() {

		String nome, descricao, nome_arquivo, caminho_arquivo;
		int id_contrato_pai;

		nome = entNomeDocumento.getText();
		descricao = entDescricaoDocumento.getText();
		caminho_arquivo = entCaminhoDocumento.getText();

		String nome_arquivo_original_conteudo[] = caminho_arquivo.split("\"");
		String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		// copiar o arquivo para a nova pasta

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();
			
			//pegar nome da pasta
			String nome_pasta = "";
			if(cliente_local.getTipo_pessoa() == 0)
				nome_pasta = cliente_local.getNome_empresarial();
			else
				nome_pasta = cliente_local.getNome_fantaia();
			

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta + "\\DADOS PESSOAS";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();
			
			if(caminho_arquivo.length() > 10) {
				if(nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
			nome_arquivo = cliente_local.getApelido() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
					+ extensaoDoArquivo;

			String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do contrato
				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao(descricao);
				novo_documento.setNome(nome);

				String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();
				int tipo_documento = -1;

				if (s_tipo_documento.equalsIgnoreCase("Documento Pessoal")) {
					tipo_documento = 1;
				} else if (s_tipo_documento.equalsIgnoreCase("Comprovantes")) {
					tipo_documento = 2;
				} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
					tipo_documento = 3;
				} 

				novo_documento.setTipo(tipo_documento);
				novo_documento.setId_pai(0);
				novo_documento.setNome_arquivo(nome_arquivo);
				novo_documento.setId_cliente(cliente_local.getId());

				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				int cadastrar = gerenciar_doc.inserir_documento_padrao_cliente(novo_documento);
				if (cadastrar > 0) {
					JOptionPane.showMessageDialog(isto, "Arquivo copiado e salvo na base de dados\nOrigem: "
							+ caminho_arquivo + "\nDestino: " + caminho_completo);
					
					entNomeDocumento.setText("");
					entDescricaoDocumento.setText("");
					entCaminhoDocumento.setText("");
					
					atualizarArvoreDocumentos();
				} else {
					JOptionPane.showMessageDialog(isto,
							"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}
				}else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			}else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
				  //telaRomaneios
				  telaRomaneio = dados.getTelaRomaneios();
				  
				  //telaTodasNotasFiscais
				  telaTodasNotasFiscais = dados.getTelaTodasNotasFiscais();
	}
	
	
public void atualizarArvoreDocumentos() {
		
		
		new Thread() {
			@Override
			public void run() {
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(cliente_local.getId());
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                
				root.removeAllChildren();
			
				no_docs_pessoais.removeAllChildren();
				no_comprovantes.removeAllChildren();
				no_outros.removeAllChildren();

				 no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				 no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				 no_outros = new DefaultMutableTreeNode("Outros");
				 
				root.add(no_docs_pessoais);
				root.add(no_comprovantes);
				root.add(no_outros);

			
				
				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							//model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root, root.getChildCount());
							
							
							no_docs_pessoais.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// carregamentos
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 4) {
							// outros
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}
			    model.reload(); //this notifies the listeners and changes the GUI
				
				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());


			}

		 
		});
		
	     } 
	  }.start();

	}
	



   public void atualizarInfo() {
	   GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	   cliente_local = gerenciar.getCliente(cliente_local.getId());
	   
		//lblIe, lblIE, lblStatus, lblEndereco;
          if(cliente_local.getTipo_pessoa() == 0) {
        	  lblTipoPessoa.setText("Fisica");
        	  lblTipoIdentificacao.setText("CPF:");
        	  lblIdentificacao.setText(cliente_local.getCpf());
          }else {
        	  lblTipoPessoa.setText("Juridica");
        	  lblTipoIdentificacao.setText("CNPJ:");
        	  lblIdentificacao.setText(cliente_local.getCnpj());
          }
	   
          lblIE.setText(cliente_local.getIe());
          String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", " + cliente_local.getBairro()
          + ", " + cliente_local.getCidade() + "/"+ cliente_local.getUf() + " Cep: " + cliente_local.getCep();
	      lblEndereco.setText(endereco_completo);

	   
	   
   }

   public void setInfo() {
	   
	   GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	   cliente_local = gerenciar.getCliente(cliente_local.getId());
	   
		//lblIe, lblIE, lblStatus, lblEndereco;
          if(cliente_local.getTipo_pessoa() == 0) {
        	  lblTipoPessoa.setText("Fisica");
        	  lblTipoIdentificacao.setText("CPF:");
        	  lblIdentificacao.setText(cliente_local.getCpf());
          }else {
        	  lblTipoPessoa.setText("Juridica");
        	  lblTipoIdentificacao.setText("CNPJ:");
        	  lblIdentificacao.setText(cliente_local.getCnpj());
          }
	   
          lblIE.setText(cliente_local.getIe());
          String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", " + cliente_local.getBairro()
          + ", " + cliente_local.getCidade() + "/"+ cliente_local.getUf() + " Cep: " + cliente_local.getCep();
	      lblEndereco.setText(endereco_completo);
	   
   }
   
   
   

}
