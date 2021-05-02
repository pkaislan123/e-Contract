package main.java.gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Locale;
import org.freixas.jcalendar.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
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
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
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
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
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
import keeptoo.KGradientPanel;



import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Insets;

public class TelaCriarNota extends JInternalFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaCriarNota isto;
	private JDialog telaPai;
	private JTextField entNome;
	private JTextField entDescricao;
	private JTextField entTempoNotificacao;
	private JPanel painelNotificar;
	private JRadioButton rBHoras, rBDias, rBMinutos;
	private JLabel lblDataLembrete;
	private JButton btnDefinirTempo;
	private JCheckBox chkBoxSim, chkBoxNao, chckBoxNaoNotificar;
	private Calendar data_selecionada;
	private JComboBox cBTipo;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaCriarNota instance;
	private CadastroNota nota_global;
	private JTextArea textAreaAnotacao;

	public TelaCriarNota(int flag_modo_operacao, CadastroNota nota, Window janela_anotacoes) {
		// setModal(true);
		getDadosGlobais();
		isto = this;
		instance = this;
		setResizable(true);
		nota_global = nota;
		if (flag_modo_operacao == 1) {
			setTitle("E-Contract - Criar Anotação");
		} else {
			setTitle("E-Contract - Editar Anotação");

		}
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 628, 660);
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = Color.WHITE;
		painelPrincipal.setBackground(new Color(51, 153, 204));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		GridBagLayout gbl_painelPrincipal = new GridBagLayout();
		gbl_painelPrincipal.columnWidths = new int[] { 395, 185, 37, 6, 0 };
		gbl_painelPrincipal.rowHeights = new int[] { 64, 143, 29, 336, 28, 0 };
		gbl_painelPrincipal.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_painelPrincipal.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		painelPrincipal.setLayout(gbl_painelPrincipal);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridwidth = 4;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		painelPrincipal.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		entNome = new JTextField();
		GridBagConstraints gbc_entNome = new GridBagConstraints();
		gbc_entNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_entNome.insets = new Insets(0, 0, 5, 0);
		gbc_entNome.gridx = 1;
		gbc_entNome.gridy = 0;
		panel.add(entNome, gbc_entNome);
		entNome.setColumns(10);

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
		gbc_lblDescrio.insets = new Insets(0, 0, 0, 5);
		gbc_lblDescrio.gridx = 0;
		gbc_lblDescrio.gridy = 1;
		panel.add(lblDescrio, gbc_lblDescrio);
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 14));

		entDescricao = new JTextField();
		GridBagConstraints gbc_entDescricao = new GridBagConstraints();
		gbc_entDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_entDescricao.gridx = 1;
		gbc_entDescricao.gridy = 1;
		panel.add(entDescricao, gbc_entDescricao);
		entDescricao.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		painelPrincipal.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 189, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		chckBoxNaoNotificar = new JCheckBox("Não notificar");
		chckBoxNaoNotificar.setForeground(Color.BLACK);
		GridBagConstraints gbc_chckBoxNaoNotificar = new GridBagConstraints();
		gbc_chckBoxNaoNotificar.insets = new Insets(0, 0, 5, 0);
		gbc_chckBoxNaoNotificar.gridx = 1;
		gbc_chckBoxNaoNotificar.gridy = 0;
		panel_1.add(chckBoxNaoNotificar, gbc_chckBoxNaoNotificar);
		chckBoxNaoNotificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckBoxNaoNotificar.isSelected()) {
					chckBoxNaoNotificar.setSelected(true);
					painelNotificar.setEnabled(false);
					for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
						Component c = painelNotificar.getComponent(i);
						c.setEnabled(false);
					}
				} else {
					chckBoxNaoNotificar.setSelected(false);
					painelNotificar.setEnabled(true);
					for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
						Component c = painelNotificar.getComponent(i);
						c.setEnabled(true);
					}
				}

			}
		});
		chckBoxNaoNotificar.setSelected(true);
		chckBoxNaoNotificar.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblLembrete = new JLabel("Notificar me a cada:");
		lblLembrete.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblLembrete = new GridBagConstraints();
		gbc_lblLembrete.anchor = GridBagConstraints.EAST;
		gbc_lblLembrete.insets = new Insets(0, 0, 5, 5);
		gbc_lblLembrete.gridx = 0;
		gbc_lblLembrete.gridy = 1;
		panel_1.add(lblLembrete, gbc_lblLembrete);
		lblLembrete.setFont(new Font("Tahoma", Font.BOLD, 14));

		painelNotificar = new JPanel();
		GridBagConstraints gbc_painelNotificar = new GridBagConstraints();
		gbc_painelNotificar.insets = new Insets(0, 0, 5, 0);
		gbc_painelNotificar.gridx = 1;
		gbc_painelNotificar.gridy = 1;
		panel_1.add(painelNotificar, gbc_painelNotificar);
		painelNotificar.setEnabled(false);
		GridBagLayout gbl_painelNotificar = new GridBagLayout();
		gbl_painelNotificar.columnWidths = new int[] { 95, 65, 56, 48, 0 };
		gbl_painelNotificar.rowHeights = new int[] { 28, 0 };
		gbl_painelNotificar.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_painelNotificar.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		painelNotificar.setLayout(gbl_painelNotificar);

		rBDias = new JRadioButton("Dias");
		rBDias.setBackground(Color.WHITE);
		rBDias.setOpaque(true);
		rBDias.setForeground(Color.BLACK);
		rBDias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBDias.isSelected()) {
					rBDias.setSelected(true);
					rBHoras.setSelected(false);
					rBMinutos.setSelected(false);

				}
			}
		});

		entTempoNotificacao = new JTextField();
		entTempoNotificacao.setForeground(Color.BLACK);
		entTempoNotificacao.setText("Exemplo:  30");
		GridBagConstraints gbc_entTempoNotificacao = new GridBagConstraints();
		gbc_entTempoNotificacao.anchor = GridBagConstraints.NORTH;
		gbc_entTempoNotificacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_entTempoNotificacao.insets = new Insets(0, 0, 0, 5);
		gbc_entTempoNotificacao.gridx = 0;
		gbc_entTempoNotificacao.gridy = 0;
		painelNotificar.add(entTempoNotificacao, gbc_entTempoNotificacao);
		entTempoNotificacao.setColumns(10);

		rBHoras = new JRadioButton("Horas");
		rBHoras.setBackground(Color.WHITE);
		rBHoras.setOpaque(true);
		rBHoras.setForeground(Color.BLACK);
		rBHoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBHoras.isSelected()) {
					rBHoras.setSelected(true);
					rBMinutos.setSelected(false);
					rBDias.setSelected(false);

				}
			}
		});

		rBMinutos = new JRadioButton("Minutos");
		rBMinutos.setOpaque(true);
		rBMinutos.setBackground(Color.WHITE);
		rBMinutos.setForeground(Color.BLACK);
		rBMinutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBMinutos.isSelected()) {
					rBMinutos.setSelected(true);
					rBHoras.setSelected(false);
					rBDias.setSelected(false);

				}
			}
		});
		rBMinutos.setSelected(true);
		GridBagConstraints gbc_rBMinutos = new GridBagConstraints();
		gbc_rBMinutos.anchor = GridBagConstraints.WEST;
		gbc_rBMinutos.insets = new Insets(0, 0, 0, 5);
		gbc_rBMinutos.gridx = 1;
		gbc_rBMinutos.gridy = 0;
		painelNotificar.add(rBMinutos, gbc_rBMinutos);
		GridBagConstraints gbc_rBHoras = new GridBagConstraints();
		gbc_rBHoras.anchor = GridBagConstraints.WEST;
		gbc_rBHoras.insets = new Insets(0, 0, 0, 5);
		gbc_rBHoras.gridx = 2;
		gbc_rBHoras.gridy = 0;
		painelNotificar.add(rBHoras, gbc_rBHoras);
		GridBagConstraints gbc_rBDias = new GridBagConstraints();
		gbc_rBDias.anchor = GridBagConstraints.WEST;
		gbc_rBDias.gridx = 3;
		gbc_rBDias.gridy = 0;
		painelNotificar.add(rBDias, gbc_rBDias);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 2;
		panel_1.add(lblTipo, gbc_lblTipo);
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 14));

		cBTipo = new JComboBox();
		cBTipo.setFont(new Font("SansSerif", Font.BOLD, 12));
		cBTipo.setForeground(Color.WHITE);
		GridBagConstraints gbc_cBTipo = new GridBagConstraints();
		gbc_cBTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBTipo.insets = new Insets(0, 0, 5, 0);
		gbc_cBTipo.gridx = 1;
		gbc_cBTipo.gridy = 2;
		panel_1.add(cBTipo, gbc_cBTipo);
		cBTipo.addItem("Anotação comum");
		cBTipo.addItem("Tópico Fixo");
		cBTipo.addItem("Tópico Fixo Broadcast");

		JLabel lblLembrete_1 = new JLabel("Lembrete:");
		lblLembrete_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblLembrete_1 = new GridBagConstraints();
		gbc_lblLembrete_1.anchor = GridBagConstraints.EAST;
		gbc_lblLembrete_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblLembrete_1.gridx = 0;
		gbc_lblLembrete_1.gridy = 3;
		panel_1.add(lblLembrete_1, gbc_lblLembrete_1);
		lblLembrete_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		chkBoxNao = new JCheckBox("Não");
		chkBoxNao.setFont(new Font("SansSerif", Font.BOLD, 12));
		chkBoxNao.setForeground(Color.BLACK);
		GridBagConstraints gbc_chkBoxNao = new GridBagConstraints();
		gbc_chkBoxNao.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkBoxNao.insets = new Insets(0, 0, 5, 5);
		gbc_chkBoxNao.gridx = 0;
		gbc_chkBoxNao.gridy = 0;
		panel_2.add(chkBoxNao, gbc_chkBoxNao);
		chkBoxNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChkBoxNaoLembrarSelecionado();
			}
		});
		chkBoxNao.setSelected(true);

		chkBoxSim = new JCheckBox("Sim");
		chkBoxSim.setFont(new Font("SansSerif", Font.BOLD, 12));
		chkBoxSim.setForeground(Color.BLACK);
		GridBagConstraints gbc_chkBoxSim = new GridBagConstraints();
		gbc_chkBoxSim.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkBoxSim.insets = new Insets(0, 0, 5, 5);
		gbc_chkBoxSim.gridx = 1;
		gbc_chkBoxSim.gridy = 0;
		panel_2.add(chkBoxSim, gbc_chkBoxSim);
		chkBoxSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setChkBoxSimLembrarSelecionado();

			}
		});

		btnDefinirTempo = new JButton("Definir Tempo");
		btnDefinirTempo.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_btnDefinirTempo = new GridBagConstraints();
		gbc_btnDefinirTempo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDefinirTempo.insets = new Insets(0, 0, 5, 0);
		gbc_btnDefinirTempo.gridx = 2;
		gbc_btnDefinirTempo.gridy = 0;
		panel_2.add(btnDefinirTempo, gbc_btnDefinirTempo);
		btnDefinirTempo.setEnabled(false);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 153, 102));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 3;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panel_2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		lblDataLembrete = new JLabel("Lembrar em: 10/02/2021 as 14:20");
		GridBagConstraints gbc_lblDataLembrete = new GridBagConstraints();
		gbc_lblDataLembrete.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDataLembrete.gridx = 0;
		gbc_lblDataLembrete.gridy = 0;
		panel_3.add(lblDataLembrete, gbc_lblDataLembrete);
		lblDataLembrete.setVisible(false);
		lblDataLembrete.setEnabled(false);
		lblDataLembrete.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblDataLembrete.setForeground(Color.WHITE);
		btnDefinirTempo.setVisible(false);
		btnDefinirTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaDefinirTempo tela = new TelaDefinirTempo(null, data_selecionada);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});

		JLabel lblDescrio_1 = new JLabel("Texto:");
		lblDescrio_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblDescrio_1 = new GridBagConstraints();
		gbc_lblDescrio_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescrio_1.anchor = GridBagConstraints.NORTH;
		gbc_lblDescrio_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescrio_1.gridx = 0;
		gbc_lblDescrio_1.gridy = 2;
		painelPrincipal.add(lblDescrio_1, gbc_lblDescrio_1);

		textAreaAnotacao = new JTextArea();
		textAreaAnotacao.setBackground(new Color(51, 255, 204));
		textAreaAnotacao.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaAnotacao.setLineWrap(true);
		textAreaAnotacao.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textAreaAnotacao);
		scrollPane.setBackground(Color.WHITE);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		painelPrincipal.add(scrollPane, gbc_scrollPane);

		JTextArea textAreaCola = new JTextArea();
		textAreaCola.setText("Atenção:  A janela ao lado ainda não esta totalmente funcional. Como estamos numa area de desktops virtuais, o modulo de pesquisa no sistema ainda está sendo programada. Você ainda pode criar, editar, salvar e excluir suas notas normalmente!");
		GridBagConstraints gbc_textAreaCola = new GridBagConstraints();
		gbc_textAreaCola.gridwidth = 3;
		gbc_textAreaCola.fill = GridBagConstraints.BOTH;
		gbc_textAreaCola.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaCola.gridx = 1;
		gbc_textAreaCola.gridy = 3;
		painelPrincipal.add(textAreaCola, gbc_textAreaCola);
		textAreaCola.setWrapStyleWord(true);
		textAreaCola.setLineWrap(true);
		textAreaCola.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaCola.setBackground(new Color(255, 255, 102));

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nome, descricao, texto;

				int notificar, lembrar, uni_tempo = 0, tempo_notificacao = 0, tipo;

				if (chckBoxNaoNotificar.isSelected()) {
					notificar = 0;
				} else {

					if (rBMinutos.isSelected()) {
						uni_tempo = 1;
					} else if (rBHoras.isSelected()) {
						uni_tempo = 2;
					} else if (rBDias.isSelected()) {
						uni_tempo = 3;
					}

					tempo_notificacao = Integer.parseInt(entTempoNotificacao.getText());

					notificar = 1;
				}

				nome = entNome.getText();
				descricao = entDescricao.getText();
				texto = textAreaAnotacao.getText();

				Date date_lembrete = null;
				LocalTime hora_lembrete = null;

				if (chkBoxSim.isSelected()) {
					lembrar = 1;

					date_lembrete = data_selecionada.getTime();
					LocalDateTime data_completa = toLocalDateTime(data_selecionada);
					hora_lembrete = data_completa.toLocalTime();

				} else {
					lembrar = 0;
				}

				Date date_hoje = new Date();

				/*
				 * cBTipo.addItem("Anotação comum"); cBTipo.addItem("Tópico Fixo");
				 * cBTipo.addItem("Tópico Fixo Broadcast"); cBTipo.addItem("Lembrete");
				 */
				tipo = cBTipo.getSelectedIndex() + 1;

				nota_global.setData_lembrete(date_lembrete);
				nota_global.setHora_lembrete(hora_lembrete);
				nota_global.setNome(nome);
				nota_global.setDescricao(descricao);
				nota_global.setTexto(texto);
				nota_global.setNotificar(notificar);
				nota_global.setUni_tempo(uni_tempo);
				nota_global.setTempo_notificacao(tempo_notificacao);
				nota_global.setLembrar(lembrar);
				nota_global.setTipo(tipo);
				nota_global.setId_usuario_pai(login.getId());

				GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
				boolean atualizou = gerenciar.atualizarStatusnota(nota_global);
				if (atualizou) {
					JOptionPane.showMessageDialog(null, "Anotação atualizada");
					((TelaNotas) telaPai).atualizarLista();

				} else {
					JOptionPane.showMessageDialog(null, "Erro ao atualizar a anotação\nConsulte o administrado!");
					((TelaNotas) janela_anotacoes).atualizarLista();

				}

			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nome, descricao, texto;

				int notificar, lembrar, uni_tempo = 0, tempo_notificacao = 0, tipo;

				if (chckBoxNaoNotificar.isSelected()) {
					notificar = 0;
				} else {

					if (rBMinutos.isSelected()) {
						uni_tempo = 1;
					} else if (rBHoras.isSelected()) {
						uni_tempo = 2;
					} else if (rBDias.isSelected()) {
						uni_tempo = 3;
					}

					tempo_notificacao = Integer.parseInt(entTempoNotificacao.getText());

					notificar = 1;
				}

				nome = entNome.getText();
				descricao = entDescricao.getText();
				texto = textAreaAnotacao.getText();

				Date date_lembrete = null;
				LocalTime hora_lembrete = null;

				if (chkBoxSim.isSelected()) {
					lembrar = 1;

					date_lembrete = data_selecionada.getTime();
					LocalDateTime data_completa = toLocalDateTime(data_selecionada);
					hora_lembrete = data_completa.toLocalTime();

				} else {
					lembrar = 0;
				}

				Date date_hoje = new Date();

				/*
				 * cBTipo.addItem("Anotação comum"); cBTipo.addItem("Tópico Fixo");
				 * cBTipo.addItem("Tópico Fixo Broadcast"); cBTipo.addItem("Lembrete");
				 */
				tipo = cBTipo.getSelectedIndex() + 1;

				CadastroNota nota = new CadastroNota();
				nota.setData_nota(date_hoje);
				nota.setData_lembrete(date_lembrete);
				nota.setHora_lembrete(hora_lembrete);
				nota.setNome(nome);
				nota.setDescricao(descricao);
				nota.setTexto(texto);
				nota.setNotificar(notificar);
				nota.setUni_tempo(uni_tempo);
				nota.setTempo_notificacao(tempo_notificacao);
				nota.setLembrar(lembrar);
				nota.setTipo(tipo);
				nota.setId_usuario_pai(login.getId());

				GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
				int salvou = gerenciar.inserirnota(nota);
				if (salvou > 0) {
					JOptionPane.showMessageDialog(isto, "Anotação criada com sucesso!");
					((TelaNotas) janela_anotacoes).atualizarLista();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao salvar anotação\nConsulte o administrador!");
				}

			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.gridwidth = 3;
		gbc_btnSalvar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnSalvar.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvar.gridx = 1;
		gbc_btnSalvar.gridy = 4;
		painelPrincipal.add(btnSalvar, gbc_btnSalvar);
		GridBagConstraints gbc_btnAtualizar = new GridBagConstraints();
		gbc_btnAtualizar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnAtualizar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAtualizar.gridx = 1;
		gbc_btnAtualizar.gridy = 4;
		painelPrincipal.add(btnAtualizar, gbc_btnAtualizar);

		for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
			Component c = painelNotificar.getComponent(i);
			c.setEnabled(false);
		}

		if (flag_modo_operacao == 1) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);

		} else {
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			rotinasEdicao();
		}

		this.setVisible(true);
	}

	public void rotinasEdicao() {
		entNome.setText(nota_global.getNome());
		entDescricao.setText(nota_global.getDescricao());
		textAreaAnotacao.setText(nota_global.getTexto());

		// set tip
		cBTipo.setSelectedIndex(nota_global.getTipo() - 1);

		// set notificar
		if (nota_global.getNotificar() == 1) {

			chckBoxNaoNotificar.setSelected(false);
			painelNotificar.setEnabled(true);
			for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
				Component c = painelNotificar.getComponent(i);
				c.setEnabled(true);
			}

			if (nota_global.getUni_tempo() == 1) {
				rBMinutos.setSelected(true);
				rBHoras.setSelected(false);
				rBDias.setSelected(false);

			} else if (nota_global.getUni_tempo() == 2) {
				rBHoras.setSelected(true);
				rBMinutos.setSelected(false);
				rBDias.setSelected(false);

			} else {
				rBDias.setSelected(true);
				rBMinutos.setSelected(false);
				rBHoras.setSelected(false);

			}

			entTempoNotificacao.setText(Integer.toString(nota_global.getTempo_notificacao()));

		}

	}

	private class MyDateListener implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {
				System.out.println(c.getTime());
			} else {
				System.out.println("No time selected.");
			}
		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

	public void setData(Calendar date) {
		String strDateLembrete = "";

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		strDateLembrete = dateFormat.format(date.getTime());
		lblDataLembrete.setText(strDateLembrete);

		data_selecionada = date;
	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public void setChkBoxSimLembrarSelecionado() {
		if (chkBoxSim.isSelected()) {
			chkBoxSim.setSelected(true);
			chkBoxNao.setSelected(false);

			btnDefinirTempo.setEnabled(true);
			btnDefinirTempo.setVisible(true);

			lblDataLembrete.setEnabled(true);
			lblDataLembrete.setVisible(true);

		} else {
			chkBoxNao.setSelected(true);
			chkBoxSim.setSelected(false);

			btnDefinirTempo.setEnabled(false);
			btnDefinirTempo.setVisible(false);

			lblDataLembrete.setEnabled(false);
			lblDataLembrete.setVisible(false);
		}
	}

	public void setChkBoxNaoLembrarSelecionado() {
		if (chkBoxNao.isSelected()) {
			chkBoxNao.setSelected(true);
			chkBoxSim.setSelected(false);

			btnDefinirTempo.setEnabled(false);
			btnDefinirTempo.setVisible(false);

			lblDataLembrete.setEnabled(false);
			lblDataLembrete.setVisible(false);

		} else {
			chkBoxSim.setSelected(true);
			chkBoxNao.setSelected(false);

			btnDefinirTempo.setEnabled(true);
			btnDefinirTempo.setVisible(true);

			lblDataLembrete.setEnabled(true);
			lblDataLembrete.setVisible(true);
		}
	}

}
