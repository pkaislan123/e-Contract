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
		setBounds(100, 100, 1200, 81);

		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));

		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
		
		JLabel lblMes = new JLabel("     FEVEREIRO     ",SwingConstants.CENTER);
		lblMes.setBorder(null);
		lblMes.setOpaque(true);
		lblMes.setBackground(new Color(0, 102, 153));
		lblMes.setForeground(Color.WHITE);
		lblMes.setFont(new Font("Arial", Font.BOLD, 18));
		painelPrincipal.add(lblMes, "cell 0 0,grow");
		
		JLabel lblSaldoInicial = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblSaldoInicial.setBorder(null);
		lblSaldoInicial.setOpaque(true);
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.BOLD, 18));
		lblSaldoInicial.setBackground(Color.WHITE);
		painelPrincipal.add(lblSaldoInicial, "cell 1 0,grow");
		
		JLabel lblReceitas = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblReceitas.setBorder(null);
		lblReceitas.setOpaque(true);
		lblReceitas.setForeground(Color.BLACK);
		lblReceitas.setFont(new Font("Arial", Font.BOLD, 18));
		lblReceitas.setBackground(new Color(204, 255, 153));
		painelPrincipal.add(lblReceitas, "cell 2 0,grow");
		
		JLabel lblDespesas = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblDespesas.setBorder(null);
		lblDespesas.setOpaque(true);
		lblDespesas.setForeground(Color.BLACK);
		lblDespesas.setFont(new Font("Arial", Font.BOLD, 18));
		lblDespesas.setBackground(new Color(255, 102, 102));
		painelPrincipal.add(lblDespesas, "cell 3 0,grow");
		
		JLabel lblTotal = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblTotal.setBorder(null);
		lblTotal.setOpaque(true);
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotal.setBackground(Color.WHITE);
		painelPrincipal.add(lblTotal, "cell 4 0,grow");
		
		JLabel lblLucro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblLucro.setBorder(null);
		lblLucro.setOpaque(true);
		lblLucro.setForeground(Color.BLACK);
		lblLucro.setFont(new Font("Arial", Font.BOLD, 18));
		lblLucro.setBackground(Color.WHITE);
		painelPrincipal.add(lblLucro, "cell 5 0,grow");
		
		JLabel lblLucratividade = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblLucratividade.setBorder(null);
		lblLucratividade.setOpaque(true);
		lblLucratividade.setForeground(Color.BLACK);
		lblLucratividade.setFont(new Font("Arial", Font.BOLD, 18));
		lblLucratividade.setBackground(Color.WHITE);
		painelPrincipal.add(lblLucratividade, "cell 6 0,grow");



		

		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

}
