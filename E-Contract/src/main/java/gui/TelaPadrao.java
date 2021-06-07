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
		setBounds(100, 100, 675, 261);

		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));
		painelPrincipal.setLayout(new MigLayout("", "[:400px:400px][][grow]", "[][][][][][][][grow][:50px:50px][][][]"));
		painelPrincipal.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		setContentPane(painelPrincipal);

		JLabel lblTipoLancamento = new JLabel("");
		lblTipoLancamento.setOpaque(true);
		lblTipoLancamento.setBackground(new Color(0, 51, 0));
		lblTipoLancamento.setForeground(Color.WHITE);
		lblTipoLancamento.setFont(new Font("Tahoma", Font.BOLD, 20));
		painelPrincipal.add(lblTipoLancamento, "cell 0 0 3 1,growx");

		Lancamento lan = new Lancamento();
		lan.setTipo_lancamento(0);
		pag_completo.setLancamento(lan);
		if (pag_completo.getLancamento().getTipo_lancamento() == 0) {
			// despesa
			lblTipoLancamento.setBackground(new Color(204, 0, 0));
			lblTipoLancamento.setText("DESPESA");
		} else if (pag_completo.getLancamento().getTipo_lancamento() == 1) {
			lblTipoLancamento.setBackground(Color.green);
			lblTipoLancamento.setText("RECEITA");

		}
		
		JLabel lblPagamentoId = new JLabel("Pagamento ID:");
		lblPagamentoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblPagamentoId, "cell 0 1");

		JLabel lblIdentificador = new JLabel("Identificador");
		lblIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblIdentificador, "cell 0 2");

		JLabel lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblData, "flowx,cell 0 3");
		lblData.setText("00/00/000");

		JLabel lblNewLabel_2 = new JLabel("Valor:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_2, "cell 1 3,alignx left");

		JLabel lblValor = new JLabel("");
		lblValor.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblValor, "cell 2 3,alignx left");
		lblValor.setText(pag_completo.getFpag().getValor().toString());
		
		JLabel lblFormaPagamento = new JLabel("Forma Pagamento");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFormaPagamento, "cell 0 4");
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 0 5");

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setOpaque(false);
		textAreaDescricao.setBackground(Color.WHITE);
		textAreaDescricao.setBorder(null);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		painelPrincipal.add(textAreaDescricao, "cell 0 6 1 3,grow");
		
		JLabel lblPagador = new JLabel("Pagador:");
		lblPagador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblPagador, "cell 0 9");
		
		JButton btnAbrirLancamento = new JButton("Abrir Lançamento");
		btnAbrirLancamento.setForeground(Color.WHITE);
		btnAbrirLancamento.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnAbrirLancamento, "cell 0 11,alignx center");

		JLabel lblNewLabel_4 = new JLabel("Saldo:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_4, "cell 1 11,alignx left");

		JLabel lblSaldo = new JLabel("R$ 100.000.000,00");
		lblSaldo.setForeground(Color.BLACK);
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblSaldo, "cell 2 11,alignx left");

		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

}
