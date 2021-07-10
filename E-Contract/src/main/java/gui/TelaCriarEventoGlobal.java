
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JOptionPane;

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
import main.java.cadastros.EventoGlobal;
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
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;
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
import javax.swing.JComboBox;

public class TelaCriarEventoGlobal extends JFrame {

	private TelaCriarEventoGlobal isto;
	private JDialog telaPai;
	private JComboBox cBTipoEvento;
	private LocalDateTime data_global;
	private JLabel lblDataEvento, lblHoraEvento;

	public TelaCriarEventoGlobal(Window janela_pai) {

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Cadastro Evento Global");
		FinanceiroPagamentoCompleto pag_completo = new FinanceiroPagamentoCompleto();

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 542, 252);

		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));

		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[143px][grow]", "[][][24px][][][][][][][][]"));

		JLabel lblData = new JLabel("Data:", SwingConstants.CENTER);
		lblData.setOpaque(true);
		lblData.setForeground(Color.BLACK);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));
		lblData.setBorder(null);
		lblData.setBackground(Color.WHITE);
		painelPrincipal.add(lblData, "cell 0 0,alignx right");

		lblDataEvento = new JLabel("00/00/0000");
		lblDataEvento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblDataEvento, "cell 1 0");
		
		JLabel lblHora = new JLabel("Hora:", SwingConstants.CENTER);
		lblHora.setOpaque(true);
		lblHora.setForeground(Color.BLACK);
		lblHora.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHora.setBorder(null);
		lblHora.setBackground(Color.WHITE);
		painelPrincipal.add(lblHora, "cell 0 1,alignx right");
		
		 lblHoraEvento = new JLabel("00:00");
		lblHoraEvento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblHoraEvento, "cell 1 1");

		JLabel lblSaldoInicial = new JLabel("Tipo de Evento:", SwingConstants.CENTER);
		lblSaldoInicial.setBorder(null);
		lblSaldoInicial.setOpaque(true);
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSaldoInicial.setBackground(Color.WHITE);
		painelPrincipal.add(lblSaldoInicial, "cell 0 2,alignx trailing,growy");

		cBTipoEvento = new JComboBox();
		cBTipoEvento.setFont(new Font("SansSerif", Font.BOLD, 16));
		cBTipoEvento.addItem("EVENTO TRABALHISTA-FERIADO");

		painelPrincipal.add(cBTipoEvento, "cell 1 2,growx");

		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventoGlobal evt = getDadosSalvar();
				
				GerenciarBancoEventoGlobal gerenciar = new GerenciarBancoEventoGlobal();
				int inserido = gerenciar.inserirevento(evt);
				if(inserido > 0) {
					JOptionPane.showMessageDialog(isto, "Evento Adicionado");
					((TelaCalendario) janela_pai).pesquisar_eventos(evt.getData_evento());
					isto.dispose();

				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao Adicionar Evento, consulte o administrador");
				}
				
			}
		});
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(btnNewButton, "cell 1 10,alignx right");

		this.setLocationRelativeTo(janela_pai);

	}

	public void setData(LocalDateTime data) {
		this.data_global = data;

		String data_formatada   = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora_formatada   = data.format(DateTimeFormatter.ofPattern("HH:mm"));
		
		lblDataEvento.setText(data_formatada);
		lblHoraEvento.setText(hora_formatada);
	}

	public EventoGlobal getDadosSalvar() {
		EventoGlobal evt = new EventoGlobal();
		evt.setTipo_evento(cBTipoEvento.getSelectedIndex());
		evt.setData_evento(lblDataEvento.getText());
		evt.setHora_evento(lblHoraEvento.getText());

		return evt;

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
