package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import keeptoo.KGradientPanel;
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
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
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
import main.java.graficos.JPanelGraficoRecebimento;
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
import main.java.manipular.MonitorarRomaneios;
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

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class TelaPadrao extends JDialog {

	private TelaPadrao isto;
	private JDialog telaPai;

	public TelaPadrao(Window janela_pai) {

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Cadastro Parcela");
		FinanceiroPagamentoCompleto pag_completo = new FinanceiroPagamentoCompleto();

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 4000, 81);

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);

		painelPrincipal.setLayout(new MigLayout("", "[200px:200px:200px][250px:250px:250px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px]", "[]"));
		
		JLabel lblGrupoContas = new JLabel("Grupo de Contas",SwingConstants.CENTER);
		lblGrupoContas.setOpaque(true);
		lblGrupoContas.setBorder(null);
		lblGrupoContas.setBackground(new Color(0, 51, 204));
		lblGrupoContas.setForeground(Color.WHITE);
		lblGrupoContas.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblGrupoContas, "cell 0 0,grow");
		
		JLabel lblConta = new JLabel("Conta", SwingConstants.CENTER);
		lblConta.setOpaque(true);
		lblConta.setBorder(null);
		lblConta.setForeground(Color.WHITE);
		lblConta.setFont(new Font("Arial", Font.BOLD, 16));
		lblConta.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblConta, "cell 1 0,grow");
		
		JLabel lblJaneiro = new JLabel("Janeiro", SwingConstants.CENTER);
		lblJaneiro.setOpaque(true);
		lblJaneiro.setBorder(null);
		lblJaneiro.setForeground(Color.WHITE);
		lblJaneiro.setFont(new Font("Arial", Font.BOLD, 16));
		lblJaneiro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblJaneiro, "cell 2 0,grow");
		
		JLabel lblFevereiro = new JLabel("Fevereiro", SwingConstants.CENTER);
		lblFevereiro.setOpaque(true);
		lblFevereiro.setBorder(null);
		lblFevereiro.setForeground(Color.WHITE);
		lblFevereiro.setFont(new Font("Arial", Font.BOLD, 16));
		lblFevereiro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblFevereiro, "cell 3 0,grow");
		
		JLabel lblMarco = new JLabel("Marco", SwingConstants.CENTER);
		lblMarco.setOpaque(true);
		lblMarco.setBorder(null);
		lblMarco.setForeground(Color.WHITE);
		lblMarco.setFont(new Font("Arial", Font.BOLD, 16));
		lblMarco.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblMarco, "cell 4 0,grow");
		
		JLabel lblAbril = new JLabel("Abril", SwingConstants.CENTER);
		lblAbril.setOpaque(true);
		lblAbril.setBorder(null);
		lblAbril.setForeground(Color.WHITE);
		lblAbril.setFont(new Font("Arial", Font.BOLD, 16));
		lblAbril.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblAbril, "cell 5 0,grow");
		
		JLabel lblMaio = new JLabel("Maio", SwingConstants.CENTER);
		lblMaio.setOpaque(true);
		lblMaio.setBorder(null);
		lblMaio.setForeground(Color.WHITE);
		lblMaio.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaio.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblMaio, "cell 6 0,grow");
		
		JLabel lblJunho = new JLabel("Junho", SwingConstants.CENTER);
		lblJunho.setOpaque(true);
		lblJunho.setForeground(Color.WHITE);
		lblJunho.setFont(new Font("Arial", Font.BOLD, 16));
		lblJunho.setBorder(null);
		lblJunho.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblJunho, "cell 7 0,grow");
		
		JLabel lblJulho = new JLabel("Julho", SwingConstants.CENTER);
		lblJulho.setOpaque(true);
		lblJulho.setForeground(Color.WHITE);
		lblJulho.setFont(new Font("Arial", Font.BOLD, 16));
		lblJulho.setBorder(null);
		lblJulho.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblJulho, "cell 8 0,grow");
		
		JLabel lblAgosto = new JLabel("Agosto", SwingConstants.CENTER);
		lblAgosto.setOpaque(true);
		lblAgosto.setForeground(Color.WHITE);
		lblAgosto.setFont(new Font("Arial", Font.BOLD, 16));
		lblAgosto.setBorder(null);
		lblAgosto.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblAgosto, "cell 9 0,grow");
		
		JLabel lblSetembro = new JLabel("Setembro", SwingConstants.CENTER);
		lblSetembro.setOpaque(true);
		lblSetembro.setForeground(Color.WHITE);
		lblSetembro.setFont(new Font("Arial", Font.BOLD, 16));
		lblSetembro.setBorder(null);
		lblSetembro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblSetembro, "cell 10 0,grow");
		
		JLabel lblOutubro = new JLabel("Outubro", SwingConstants.CENTER);
		lblOutubro.setOpaque(true);
		lblOutubro.setForeground(Color.WHITE);
		lblOutubro.setFont(new Font("Arial", Font.BOLD, 16));
		lblOutubro.setBorder(null);
		lblOutubro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblOutubro, "cell 11 0,grow");
		
		JLabel lblNovembro = new JLabel("Novembro", SwingConstants.CENTER);
		lblNovembro.setOpaque(true);
		lblNovembro.setForeground(Color.WHITE);
		lblNovembro.setFont(new Font("Arial", Font.BOLD, 16));
		lblNovembro.setBorder(null);
		lblNovembro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblNovembro, "cell 12 0,grow");
		
		JLabel lblDezembro = new JLabel("Dezembro", SwingConstants.CENTER);
		lblDezembro.setOpaque(true);
		lblDezembro.setForeground(Color.WHITE);
		lblDezembro.setFont(new Font("Arial", Font.BOLD, 16));
		lblDezembro.setBorder(null);
		lblDezembro.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblDezembro, "cell 13 0,grow");


		setContentPane(painelPrincipal);
		
		JLabel lblTotal = new JLabel("Total", SwingConstants.CENTER);
		lblTotal.setOpaque(true);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotal.setBorder(null);
		lblTotal.setBackground(new Color(0, 51, 204));
		painelPrincipal.add(lblTotal, "cell 14 0,grow");

		

		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

}
