package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JInternalFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPJ;
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
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;



public class TelaHome extends JInternalFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaHome isto;
    private JDialog telaPai;
    private static ArrayList<CadastroCliente> clientes_pesquisados = new ArrayList<>();
  	private static ArrayList<CadastroCliente> clientes_disponiveis = new ArrayList<>();

  	
  	public  void pesquisar( )
	{ 
	
	
    GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
    clientes_disponiveis.clear();
    
   
    for (CadastroCliente cliente : listaClientes.getClientes(-1, -1, null)) {
    	String cpf, cnpj, nome;
     	
    if(cliente.getArmazem() == 1 || cliente.getTransportador() == 1)	
    {
    	
    }else {
    	
    	clientes_disponiveis.add(cliente);
    }
    }
		
	}
  	
	public TelaHome() {

		 isto = this;
		setResizable(true);
		setTitle("E-Contract - Home");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 554, 409);
		painelPrincipal.kGradientFocus = 2500;
		painelPrincipal.kStartColor = new Color(0, 204, 204);
		painelPrincipal.kEndColor = new Color(153, 102, 0);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnNewButton = new JButton("Clientes");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(1,0, null);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBounds(25, 164, 128, 23);
		painelPrincipal.add(btnNewButton);
		
		JButton btnTransportadores = new JButton("Transportadores");
		btnTransportadores.setBackground(new Color(0, 51, 0));
		btnTransportadores.setForeground(Color.WHITE);
		btnTransportadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores tela = new TelaTransportadores(0, null);
				tela.setVisible(true);
			}
		});
		btnTransportadores.setBounds(210, 161, 128, 28);
		painelPrincipal.add(btnTransportadores);
		
		JButton btnContratos = new JButton("Contratos");
		btnContratos.setBackground(new Color(204, 153, 0));
		btnContratos.setForeground(Color.WHITE);
		btnContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContratos telaContratos = new TelaContratos(0, null);
				telaContratos.setVisible(true);
			}
		});
		btnContratos.setBounds(386, 161, 128, 28);
		painelPrincipal.add(btnContratos);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_cliente_tela_home.png")));
		lblNewLabel.setBounds(25, 24, 128, 128);
		painelPrincipal.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_transportadores_tela_home.png")));
		lblNewLabel_1.setBounds(210, 24, 128, 128);
		painelPrincipal.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_contratos_tela_home_2.png")));
		lblNewLabel_1_1.setBounds(386, 24, 128, 128);
		painelPrincipal.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_notas_fiscais_tela_home.png")));
		lblNewLabel_2.setBounds(25, 199, 128, 128);
		painelPrincipal.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_romaneios_tela_home.png")));
		lblNewLabel_1_2.setBounds(210, 199, 128, 128);
		painelPrincipal.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(TelaHome.class.getResource("/imagens/icone_relatorios_tela_home.png")));
		lblNewLabel_1_1_1.setBounds(386, 201, 128, 128);
		painelPrincipal.add(lblNewLabel_1_1_1);
		
		JButton btnNotasFiscais = new JButton("Notas Fiscais");
		btnNotasFiscais.setBackground(new Color(102, 0, 153));
		btnNotasFiscais.setForeground(Color.WHITE);
		btnNotasFiscais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTodasNotasFiscais verNotas = new TelaTodasNotasFiscais(1, 0,null);
				 verNotas.setVisible(true);
			}
		});
		btnNotasFiscais.setBounds(25, 335, 128, 23);
		painelPrincipal.add(btnNotasFiscais);
		
		JButton btnRomaneios = new JButton("Romaneios");
		btnRomaneios.setBackground(new Color(255, 153, 0));
		btnRomaneios.setForeground(Color.WHITE);
		btnRomaneios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRomaneios tela = new TelaRomaneios(0,null);
				
			}
		});
		btnRomaneios.setBounds(210, 335, 128, 23);
		painelPrincipal.add(btnRomaneios);
		
		JButton btnRomaneios_1 = new JButton("Relatoria");
		btnRomaneios_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  JDesktopPane desktopPane = getDesktopPane();
				//TelaRelatoriaContratosInternal tela = new TelaRelatoriaContratosInternal();
				//desktopPane.add(tela);

			}
		});
		btnRomaneios_1.setForeground(Color.WHITE);
		btnRomaneios_1.setBackground(new Color(51, 51, 51));
		btnRomaneios_1.setBounds(386, 337, 128, 23);
		painelPrincipal.add(btnRomaneios_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("");
		lblNewLabel_1_2_1.setBounds(386, 201, 128, 128);
		painelPrincipal.add(lblNewLabel_1_2_1);
	
		
		
		new Thread() {
			@Override
			public void run() {
				pesquisar();
			}
		}.start();

		
		
		this.setVisible(true);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
