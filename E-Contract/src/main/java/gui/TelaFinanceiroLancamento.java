package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioLancamentos;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;
import main.java.conexaoBanco.GerenciarBancoLancamento;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFinanceiroLancamento extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroLancamento isto;
	 private JTable tabela_lancamentos;
	 private ArrayList<Lancamento> lista_lancamentos = new ArrayList<>();
	 private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
	 private JDialog telaPai;
	 private JLabel lblValorTotalJurosRecebido, lblValorTotalJurosPago,lblValorVencerAPagar,lblValorVencerAReceber;
	  private JLabel lblValorPago, lblValorTotalDespesas;
	 private TableRowSorter<LancamentoTableModel> sorter;
	 private JComboBox cbStatusLancamento, cbStatusCondicaoPagamento,cbStatusAoContador;
	 private JComboBox cbGrupoConta,cbCondicaoPagamento,cbTipoLancamento;
	 private JComboBox cbConta ,cbCentroCusto,cbInstituicaoBancaria;
	 private JTextField entClienteFornecedor;
	 private JLabel lblDespesasAPagar, lblDespesasPago,	lblTotalDespesas, lblReceitasRecebido, lblTotalReceitas, lblReceitasAReceber;
	 private JTextField menorDataLancamento;
	 private JTextField maiorDataLancamento;
	 private JTextField menorDataVencimento;
	 private JTextField maiorDataVencimento;
	 private JTextField menorDataPagamento;
	 private JTextField maiorDataPagamento;
	 private JLabel lblValorAReceber, lblValorRestanteAPagar,lblValorRecebido, lblValorReceitas ;
	 private JComboBox cbPrioridade,cbSituacao ;
	 private JTextField entIdentificadorGeral;
	 private JTextField entDestinatarioNF;
	 
	public TelaFinanceiroLancamento(int flag_modo_operacao, int flag_retorno, Window janela_pai) {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);
		
		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);
		
		isto = this;
		setResizable(true);
		DadosGlobais dados = DadosGlobais.getInstance();
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);
		
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[][grow][]", "[][100px][grow][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[269px][]", "[49px]"));
		
		JLabel lblNewLabel = new JLabel("Lançamentos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");
		
		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1 3 1,growx,aligny top");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new MigLayout("", "[grow][grow][123px,grow][465px,grow][grow][grow][grow][][grow][grow]", "[31px,grow][][47px,grow][][][31px,grow][grow][grow][grow][]"));
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Centro de Custo:");
		lblNewLabel_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2, "cell 0 0,alignx trailing");
		
		 cbCentroCusto = new JComboBox();
		
		panel_1.add(cbCentroCusto, "cell 1 0,growx");
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo de Lançamento:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1, "cell 2 0,alignx trailing,aligny center");
		
		 cbTipoLancamento = new JComboBox();
		cbTipoLancamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbTipoLancamento, "cell 3 0,growx");
		cbTipoLancamento.addItem("TODOS");
		cbTipoLancamento.addItem("DESPESAS");
		cbTipoLancamento.addItem("RECEITAS");
		cbTipoLancamento.addItem("EMPRESTIMOS");

		 
		 JLabel lblNewLabel_1_1_2_1 = new JLabel("Instituição Bancária:");
		 lblNewLabel_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_2_1, "cell 4 0,alignx trailing");
		
		 cbInstituicaoBancaria = new JComboBox();
		 panel_1.add(cbInstituicaoBancaria, "cell 5 0,growx");
		
		JLabel lblNewLabel_1_1_3_1 = new JLabel("Situação:");
		lblNewLabel_1_1_3_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_3_1, "cell 6 0,alignx trailing");
		
		 cbSituacao = new JComboBox();
		cbSituacao.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbSituacao, "cell 7 0 3 1,growx");
		cbSituacao.addItem("TODOS");
		cbSituacao.addItem("Atrazado");
		cbSituacao.addItem("Em dias");
		cbSituacao.addItem("Datas Invalidas");
		
		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Cliente/Fornecedor:");
		lblNewLabel_1_1_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2_1_1, "cell 0 1,alignx trailing");
		
		entClienteFornecedor = new JTextField();
		panel_1.add(entClienteFornecedor, "cell 1 1,growx");
		entClienteFornecedor.setColumns(10);
		
		JLabel lblNewLabel_1_1_4 = new JLabel("Status:");
		lblNewLabel_1_1_4.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_4, "cell 2 1,alignx right");
		
		 cbStatusLancamento = new JComboBox();
		 cbStatusLancamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(cbStatusLancamento, "cell 3 1,growx,aligny top");
		 cbStatusLancamento.addItem("TODOS");
		 cbStatusLancamento.addItem("DESPESAS A PAGAR");
		 cbStatusLancamento.addItem("DESPESAS PAGAS");
		 cbStatusLancamento.addItem("RECEITAS A RECEBER");
		 cbStatusLancamento.addItem("RECEITAS RECEBIDAS");
		 
		 JLabel lblNewLabel_1_1_3 = new JLabel("Condição do Pagamento:");
		 lblNewLabel_1_1_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_3, "cell 4 1,alignx trailing");
		
		 cbCondicaoPagamento = new JComboBox();
		 cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(cbCondicaoPagamento, "cell 5 1,growx");
		
		JLabel lblNewLabel_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
		lblNewLabel_1_1_2_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2_1_1_2, "cell 0 2,alignx trailing");
		
		entIdentificadorGeral = new JTextField();
		entIdentificadorGeral.setColumns(10);
		panel_1.add(entIdentificadorGeral, "cell 1 2,growx");
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Grupo de Contas:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1, "cell 2 2,alignx right,aligny center");
		
		 cbGrupoConta = new JComboBox();
		cbGrupoConta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbGrupoConta, "cell 3 2,grow");
		
		JLabel lblNewLabel_1_1_4_2_1 = new JLabel("Status Pagamento:");
		lblNewLabel_1_1_4_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_4_2_1, "cell 4 2,alignx trailing");
		
		 cbStatusCondicaoPagamento = new JComboBox();
		 cbStatusCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(cbStatusCondicaoPagamento, "cell 5 2,growx");
		 cbStatusCondicaoPagamento.addItem("TODOS");
		 cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
		 cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
		
		JButton btnLimparBusca = new JButton("Limpar Busca");
		panel_1.add(btnLimparBusca, "cell 6 2 3 1,growx");
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimparBusca.setBackground(new Color(0, 0, 102));
		btnLimparBusca.setForeground(Color.WHITE);
		btnLimparBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));
				cbPrioridade.setSelectedIndex(0);
				cbSituacao.setSelectedIndex(0);
				cbStatusLancamento.setSelectedIndex(0);
				cbCondicaoPagamento.setSelectedIndex(0);
				cbCentroCusto.setSelectedIndex(0);
				cbGrupoConta.setSelectedIndex(0);
				cbConta.setSelectedIndex(0);
				cbInstituicaoBancaria.setSelectedIndex(0);
				cbTipoLancamento.setSelectedIndex(0);
				entClienteFornecedor.setText("");
				pegarDatas();


				calcular();
			}
		});
		btnLimparCampos.setHorizontalAlignment(SwingConstants.RIGHT);
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setBackground(new Color(0, 0, 102));
		panel_1.add(btnLimparCampos, "cell 9 2,growx");
		 
		 JLabel lblNewLabel_1_1_2_1_1_2_1 = new JLabel("Destinatário NF:");
		 lblNewLabel_1_1_2_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_2_1_1_2_1, "cell 0 3,alignx trailing");
		 
		 entDestinatarioNF = new JTextField();
		 entDestinatarioNF.setColumns(10);
		 panel_1.add(entDestinatarioNF, "cell 1 3,growx");
		 
		 JLabel lblNewLabel_1_1_1_1 = new JLabel("Conta:");
		 lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_1_1, "cell 2 3,alignx right,aligny center");
		 
		  cbConta = new JComboBox();
		  cbConta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		  panel_1.add(cbConta, "cell 3 3,growx,aligny center");
		 
		 JLabel lblNewLabel_1_1_4_2 = new JLabel("Prioridade:");
		 lblNewLabel_1_1_4_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_4_2, "cell 4 3,alignx trailing");
		 
		  cbPrioridade = new JComboBox();
		  cbPrioridade.addItem("TODOS");
		  cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
		  cbPrioridade.addItem("Média Prioridade - Em menos de 15 dias");
		  cbPrioridade.addItem("Prioridade Leve - Ainda este mês");
		  cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");
		  panel_1.add(cbPrioridade, "cell 5 3,growx");
		 
		 JButton btnFiltar = new JButton("Filtrar");
		 panel_1.add(btnFiltar, "cell 6 3 3 1,growx");
		 btnFiltar.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		filtrar();
		 	}
		 });
		 btnFiltar.setBackground(new Color(0, 0, 102));
		 btnFiltar.setForeground(Color.WHITE);
		 
		 JButton btnNewButton = new JButton("pesquisar");
		 panel_1.add(btnNewButton, "cell 9 3,growx");
		 btnNewButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		pesquisar();
		 	}
		 });
		 btnNewButton.setForeground(Color.WHITE);
		 btnNewButton.setBackground(new Color(0, 0, 102));
		 
		 JLabel lblNewLabel_1_1_2_1_1_1 = new JLabel("Contador:");
		 lblNewLabel_1_1_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(lblNewLabel_1_1_2_1_1_1, "cell 0 5,alignx trailing");
		
		 cbStatusAoContador = new JComboBox();
		 panel_1.add(cbStatusAoContador, "cell 1 5,growx");
		 cbStatusAoContador.addItem("TODOS");
		 cbStatusAoContador.addItem("Não se aplica".toUpperCase());
		 cbStatusAoContador.addItem("Não Enviado ao contador".toUpperCase());
		 cbStatusAoContador.addItem("Enviado ao contador".toUpperCase());
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_1.add(panel_2, "cell 0 6 10 1,grow");
		panel_2.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][]"));
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, "cell 0 0,grow");
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(new MigLayout("", "[][][][][grow][][][grow]", "[]"));
		
		JLabel lblNewLabel_1_1_4_1 = new JLabel("Período Lançamento:");
		panel_4.add(lblNewLabel_1_1_4_1, "cell 0 0");
		lblNewLabel_1_1_4_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JLabel lblNewLabel_8 = new JLabel("De");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblNewLabel_8, "cell 1 0,alignx trailing");
		
		menorDataLancamento = new JTextField();
		panel_4.add(menorDataLancamento, "cell 2 0 3 1,growx");
		menorDataLancamento.setColumns(10);
		menorDataLancamento.setText(pegarDataMenos(1));
		
		JLabel lblNewLabel_6 = new JLabel("a");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblNewLabel_6, "cell 5 0,alignx trailing");
		
		maiorDataLancamento = new JTextField();
		panel_4.add(maiorDataLancamento, "cell 6 0 2 1,growx");
		maiorDataLancamento.setColumns(10);
		maiorDataLancamento.setText(pegarDataMais(1));
		
		JPanel panel_4_1 = new JPanel();
		panel_2.add(panel_4_1, "cell 1 0,grow");
		panel_4_1.setBackground(Color.WHITE);
		panel_4_1.setLayout(new MigLayout("", "[][][grow][][grow]", "[][]"));
		
		JLabel lblNewLabel_1_1_4_1_1 = new JLabel("Período Vencimento:");
		panel_4_1.add(lblNewLabel_1_1_4_1_1, "cell 0 0");
		lblNewLabel_1_1_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JLabel lblNewLabel_8_1 = new JLabel("De");
		lblNewLabel_8_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1.add(lblNewLabel_8_1, "cell 1 0,alignx trailing");
		
		menorDataVencimento = new JTextField();
		menorDataVencimento.setText((String) null);
		menorDataVencimento.setColumns(10);
		menorDataVencimento.setText(pegarDataMenos(1));

		panel_4_1.add(menorDataVencimento, "cell 2 0,growx");
		
		JLabel lblNewLabel_6_1 = new JLabel("a");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1.add(lblNewLabel_6_1, "cell 3 0,alignx trailing");
		
		maiorDataVencimento = new JTextField();
		maiorDataVencimento.setText((String) null);
		maiorDataVencimento.setColumns(10);
		maiorDataVencimento.setText(pegarDataMais(1));
		panel_4_1.add(maiorDataVencimento, "cell 4 0,growx");
		
		JPanel panel_4_1_1 = new JPanel();
		panel_2.add(panel_4_1_1, "cell 2 0,grow");
		panel_4_1_1.setBackground(Color.WHITE);
		panel_4_1_1.setLayout(new MigLayout("", "[][][grow][][grow]", "[]"));
		
		JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Período Pagamento:");
		lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_4_1_1.add(lblNewLabel_1_1_4_1_1_1, "cell 0 0");
		
		JLabel lblNewLabel_8_1_1 = new JLabel("De");
		lblNewLabel_8_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1_1.add(lblNewLabel_8_1_1, "cell 1 0,alignx trailing");
		
		menorDataPagamento = new JTextField();
		menorDataPagamento.setText((String) null);
		menorDataPagamento.setColumns(10);
		menorDataPagamento.setText(pegarDataMenos(1));
		panel_4_1_1.add(menorDataPagamento, "cell 2 0,growx");
		
		JLabel lblNewLabel_6_1_1 = new JLabel("a");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1_1.add(lblNewLabel_6_1_1, "cell 3 0,alignx trailing");
		
		maiorDataPagamento = new JTextField();
		maiorDataPagamento.setText((String) null);
		maiorDataPagamento.setColumns(10);
		maiorDataPagamento.setText(pegarDataMais(1));
		
				panel_4_1_1.add(maiorDataPagamento, "cell 4 0,growx");
				
				JPanel panel_6 = new JPanel();
				panel_6.setBackground(new Color(255, 255, 204));
				panel_1.add(panel_6, "cell 0 7 10 1,grow");
				panel_6.setLayout(new MigLayout("", "[][][][][][][][][][]", "[][][]"));
				
				JLabel lblNewLabel_1_1_4_1_2 = new JLabel("Filtros Rápidos:");
				lblNewLabel_1_1_4_1_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
				panel_6.add(lblNewLabel_1_1_4_1_2, "cell 0 0 1 2");
				
				JButton btnNewButton_5 = new JButton("Atrazados a uma semana");
				btnNewButton_5.setBackground(new Color(255, 153, 0));
				btnNewButton_5.setForeground(Color.WHITE);
				btnNewButton_5.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						filtroRapidoDespesaAtrazo(7);
						
					}
				});
				
				JLabel lblNewLabel_2 = new JLabel("Despesas:");
				panel_6.add(lblNewLabel_2, "cell 1 0");
				panel_6.add(btnNewButton_5, "cell 2 0");
				
				JButton btnNewButton_5_1 = new JButton("Atrazados a 15 dias");
				btnNewButton_5_1.setBackground(new Color(255, 51, 0));
				btnNewButton_5_1.setForeground(Color.WHITE);
				btnNewButton_5_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaAtrazo(15);

					}
				});
				panel_6.add(btnNewButton_5_1, "cell 3 0");
				
				JButton btnNewButton_5_1_1 = new JButton("Atrazados a 1 Mês");
				btnNewButton_5_1_1.setBackground(new Color(204, 0, 0));
				btnNewButton_5_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaAtrazo(30);

					}
				});
				panel_6.add(btnNewButton_5_1_1, "cell 4 0");
				
				JButton btnNewButton_5_1_1_1 = new JButton("Atrazados > 1 mês");
				btnNewButton_5_1_1_1.setBackground(new Color(102, 0, 0));
				btnNewButton_5_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaAtrazo(600);

					}
				});
				panel_6.add(btnNewButton_5_1_1_1, "cell 5 0");
				
				JButton btnNewButton_5_1_1_1_1 = new JButton("Vencem essa semana");
				btnNewButton_5_1_1_1_1.setBackground(new Color(255, 51, 0));
				btnNewButton_5_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaEmDias(7);
					}
				});
				panel_6.add(btnNewButton_5_1_1_1_1, "cell 6 0");
				
				JButton btnNewButton_5_1_1_1_1_1 = new JButton("Vencem em 15 dias");
				btnNewButton_5_1_1_1_1_1.setBackground(new Color(255, 51, 0));
				btnNewButton_5_1_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaEmDias(15);

					}
				});
				panel_6.add(btnNewButton_5_1_1_1_1_1, "cell 7 0");
				
				JButton btnNewButton_5_1_1_1_1_1_1 = new JButton("Vencem em 1 mês");
				btnNewButton_5_1_1_1_1_1_1.setBackground(new Color(255, 51, 0));
				btnNewButton_5_1_1_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaEmDias(30);

					}
				});
				panel_6.add(btnNewButton_5_1_1_1_1_1_1, "cell 8 0");
				
				JButton btnNewButton_5_1_1_1_1_1_1_1 = new JButton("Vence em > 1 Mês");
				btnNewButton_5_1_1_1_1_1_1_1.setBackground(new Color(255, 51, 0));
				btnNewButton_5_1_1_1_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoDespesaEmDias(365);

					}
				});
				panel_6.add(btnNewButton_5_1_1_1_1_1_1_1, "cell 9 0");
				
				JButton btnNewButton_5_2 = new JButton("Vencem essa semana");
				btnNewButton_5_2.setBackground(new Color(0, 153, 0));
				btnNewButton_5_2.setForeground(Color.WHITE);
				btnNewButton_5_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaEmDias(7);
					}
				});
				
				JButton btnNewButton_5_3 = new JButton("Atrazados a uma semana");
				btnNewButton_5_3.setBackground(new Color(51, 255, 153));
				btnNewButton_5_3.setForeground(new Color(0, 0, 0));
				btnNewButton_5_3.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaAtrazo(7);
					}
				});
				
				JLabel lblNewLabel_2_1 = new JLabel("Receitas:");
				panel_6.add(lblNewLabel_2_1, "cell 1 1,alignx right");
				panel_6.add(btnNewButton_5_3, "cell 2 1");
				
				JButton btnNewButton_5_3_1 = new JButton("Atrazados a 15 dias");
				btnNewButton_5_3_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaAtrazo(15);
					}
				});
				btnNewButton_5_3_1.setForeground(Color.WHITE);
				btnNewButton_5_3_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3_1.setBackground(new Color(0, 153, 153));
				panel_6.add(btnNewButton_5_3_1, "cell 3 1");
				
				JButton btnNewButton_5_3_1_1 = new JButton("Atrazados a 1 Mês");
				btnNewButton_5_3_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaAtrazo(30);
					}
				});
				btnNewButton_5_3_1_1.setForeground(Color.WHITE);
				btnNewButton_5_3_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3_1_1.setBackground(new Color(0, 153, 102));
				panel_6.add(btnNewButton_5_3_1_1, "cell 4 1");
				
				JButton btnNewButton_5_3_1_1_1 = new JButton("Atrazados > 1 Mês");
				btnNewButton_5_3_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaAtrazo(365);
					}
				});
				btnNewButton_5_3_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_3_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3_1_1_1.setBackground(new Color(0, 51, 51));
				panel_6.add(btnNewButton_5_3_1_1_1, "cell 5 1");
				panel_6.add(btnNewButton_5_2, "cell 6 1,growx");
				
				JButton btnNewButton_5_3_1_1_1_1 = new JButton("Vencem em 15 dias");
				btnNewButton_5_3_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaEmDias(15);

					}
				});
				btnNewButton_5_3_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_3_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3_1_1_1_1.setBackground(new Color(51, 51, 0));
				panel_6.add(btnNewButton_5_3_1_1_1_1, "cell 7 1");
				
				JButton btnNewButton_5_3_1_1_1_1_1 = new JButton("Vencem em 1 mês");
				btnNewButton_5_3_1_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaEmDias(30);

					}
				});
				btnNewButton_5_3_1_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_3_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_3_1_1_1_1_1.setBackground(new Color(0, 51, 102));
				panel_6.add(btnNewButton_5_3_1_1_1_1_1, "cell 8 1");
				
				JButton btnNewButton_5_1_1_1_1_1_1_1_1 = new JButton("Vence em > 1 mês");
				btnNewButton_5_1_1_1_1_1_1_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filtroRapidoReceitaEmDias(30);

					}
				});
				btnNewButton_5_1_1_1_1_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_5_1_1_1_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				btnNewButton_5_1_1_1_1_1_1_1_1.setBackground(new Color(0, 51, 0));
				panel_6.add(btnNewButton_5_1_1_1_1_1_1_1_1, "cell 9 1");
		
		
		LancamentosRender renderer = new LancamentosRender();
		
		tabela_lancamentos = new JTable(modelo_lancamentos);
		tabela_lancamentos.setDefaultRenderer(Object.class, renderer);
		
		//define o sorter na tablea
		sorter = new TableRowSorter<LancamentoTableModel>(modelo_lancamentos);
		tabela_lancamentos.setRowSorter(sorter);
		tabela_lancamentos.setRowHeight(30);
 
		JScrollPane scrollPane = new JScrollPane(tabela_lancamentos);
		painelPrinciapl.add(scrollPane, "cell 0 2 3 1,grow");
		
	
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrinciapl.add(panel_5, "cell 0 3 2 1,grow");
		panel_5.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		
		JPanel painelDespesas = new JPanel();
		painelDespesas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		painelDespesas.setBackground(Color.WHITE);
		panel_5.add(painelDespesas, "cell 0 0,grow");
				 painelDespesas.setLayout(new MigLayout("", "[62px][109px][8px][91px][51px]", "[16px][20px][20px][][20px][]"));
				 
				 JLabel lblNewLabel_1 = new JLabel("Despesas:");
				 lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
				 painelDespesas.add(lblNewLabel_1, "cell 0 0,alignx left,aligny top");
				 
				 JLabel lblNewLabel_3_1 = new JLabel("N.º de Despesas:");
				 lblNewLabel_3_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
				 painelDespesas.add(lblNewLabel_3_1, "cell 1 1,alignx left,aligny bottom");
		
				 lblTotalDespesas = new JLabel("0");
				 painelDespesas.add(lblTotalDespesas, "cell 2 1,alignx left,aligny top");
				 lblTotalDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		JLabel lblValorDespesaslbl = new JLabel("Valor Total:");
		lblValorDespesaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorDespesaslbl, "cell 3 1,alignx right,aligny bottom");
		
		 lblValorTotalDespesas = new JLabel("0");
		 lblValorTotalDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));
		 painelDespesas.add(lblValorTotalDespesas, "cell 4 1,alignx left,aligny top");
		 
		 JLabel lblNewLabel_5_1 = new JLabel("        ");
		 painelDespesas.add(lblNewLabel_5_1, "cell 0 2,alignx right,aligny center");
		 lblNewLabel_5_1.setOpaque(true);
		 lblNewLabel_5_1.setForeground(Color.BLACK);
		 lblNewLabel_5_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNewLabel_4 = new JLabel("Pago:");
		 painelDespesas.add(lblNewLabel_4, "cell 1 2,alignx left,aligny bottom");
		 lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 14));
		 
		  lblDespesasPago = new JLabel("0");
		  painelDespesas.add(lblDespesasPago, "cell 2 2,alignx left,aligny top");
		  lblDespesasPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		   
		   JLabel lblValorPagolbl = new JLabel("Valor Pago:");
		   lblValorPagolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		   painelDespesas.add(lblValorPagolbl, "cell 3 2,alignx right,aligny bottom");
		  
		   lblValorPago = new JLabel("0");
		   lblValorPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		   painelDespesas.add(lblValorPago, "cell 4 2,alignx left,aligny top");
		   
		   JLabel lblValorJurosPago = new JLabel("Valor Juros Pago:");
		   lblValorJurosPago.setFont(new Font("SansSerif", Font.PLAIN, 14));
		   painelDespesas.add(lblValorJurosPago, "cell 3 3,alignx right");
		   
		    lblValorTotalJurosPago = new JLabel("R$ 0,00");
		   lblValorTotalJurosPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		   painelDespesas.add(lblValorTotalJurosPago, "cell 4 3");
		   
		   JLabel lblNewLabel_5 = new JLabel("        ");
		   painelDespesas.add(lblNewLabel_5, "cell 0 4,alignx right,aligny center");
		   lblNewLabel_5.setOpaque(true);
		   lblNewLabel_5.setBackground(Color.RED);
		   lblNewLabel_5.setForeground(Color.BLACK);
		   
		   JLabel lblNewLabel_3 = new JLabel("A Pagar:");
		   painelDespesas.add(lblNewLabel_3, "cell 1 4,alignx left,aligny bottom");
		   lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 14));
		   
		    lblDespesasAPagar = new JLabel("0");
		    painelDespesas.add(lblDespesasAPagar, "cell 2 4,alignx left,aligny top");
		    lblDespesasAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		    
		    JLabel lblValorAPagar_1_1 = new JLabel("Valor a Pagar:");
		    lblValorAPagar_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		    painelDespesas.add(lblValorAPagar_1_1, "cell 3 4,alignx right,aligny bottom");
		    
		     lblValorRestanteAPagar = new JLabel("0");
		     lblValorRestanteAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		     painelDespesas.add(lblValorRestanteAPagar, "cell 4 4,alignx left,aligny top");
		     
		     JLabel lblValorAPagar_1_1_1 = new JLabel("Valor a Vencer:");
		     lblValorAPagar_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		     painelDespesas.add(lblValorAPagar_1_1_1, "cell 3 5,alignx right");
		     
		      lblValorVencerAPagar = new JLabel("R$ 0,00");
		     lblValorVencerAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		     painelDespesas.add(lblValorVencerAPagar, "cell 4 5");
		  		 
		  		 JPanel panel_7 = new JPanel();
		  		 panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		  		 panel_7.setBackground(Color.WHITE);
		  		 panel_5.add(panel_7, "cell 1 0,grow");
		  		 panel_7.setLayout(new MigLayout("", "[52px][81px][24px][107px][51px]", "[][20px][20px][][20px][]"));
		  		 
		  		 JLabel lblNewLabel_1_2 = new JLabel("Receitas:");
		  		 lblNewLabel_1_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		  		 panel_7.add(lblNewLabel_1_2, "cell 0 0,alignx left,aligny center");
		  		 
		  		 JLabel lblNewLabel_7_1 = new JLabel("N.º Receitas:");
		  		 panel_7.add(lblNewLabel_7_1, "cell 1 1,alignx left,aligny bottom");
		  		 lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 
		  		 
		  		  lblTotalReceitas = new JLabel("0");
		  		  panel_7.add(lblTotalReceitas, "cell 2 1,alignx left,aligny top");
		  		  lblTotalReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		 
		  		 JLabel lblValorReceitaslbl = new JLabel("Valor Total:");
		  		 lblValorReceitaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 panel_7.add(lblValorReceitaslbl, "cell 3 1,alignx right,aligny bottom");
		  		 
		  		  lblValorReceitas = new JLabel("R$ 0,00");
		  		  lblValorReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  panel_7.add(lblValorReceitas, "cell 4 1,alignx left,aligny top");
		  		 
		  		 JLabel lblNewLabel_5_2_1 = new JLabel("        ");
		  		 panel_7.add(lblNewLabel_5_2_1, "cell 0 2,alignx right,aligny center");
		  		 lblNewLabel_5_2_1.setOpaque(true);
		  		 lblNewLabel_5_2_1.setForeground(Color.BLACK);
		  		 lblNewLabel_5_2_1.setBackground(new Color(0, 51, 0));
		  		 
		  		 JLabel lblNewLabel_4_1_1 = new JLabel("Recebido:");
		  		 panel_7.add(lblNewLabel_4_1_1, "cell 1 2,alignx left,aligny bottom");
		  		 lblNewLabel_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 
		  		  lblReceitasRecebido = new JLabel("0");
		  		  panel_7.add(lblReceitasRecebido, "cell 2 2,alignx left,aligny top");
		  		  lblReceitasRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		 
		  		 JLabel lblValorRecebidolbl = new JLabel("Valor Recebido:");
		  		 lblValorRecebidolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 panel_7.add(lblValorRecebidolbl, "cell 3 2,alignx right,aligny bottom");
		  		 
		  		  lblValorRecebido = new JLabel("R$ 0,00");
		  		  lblValorRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  panel_7.add(lblValorRecebido, "cell 4 2,alignx left,aligny top");
		  		 
		  		 JLabel lblValorJurosRecebido = new JLabel("Valor Juros Recebido:");
		  		 lblValorJurosRecebido.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 panel_7.add(lblValorJurosRecebido, "cell 3 3,alignx right");
		  		 
		  		  lblValorTotalJurosRecebido = new JLabel("R$ 0,00");
		  		 lblValorTotalJurosRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		 panel_7.add(lblValorTotalJurosRecebido, "cell 4 3");
		  		 
		  		 JLabel lblNewLabel_5_2 = new JLabel("        ");
		  		 panel_7.add(lblNewLabel_5_2, "cell 0 4,alignx right,aligny center");
		  		 lblNewLabel_5_2.setOpaque(true);
		  		 lblNewLabel_5_2.setForeground(Color.BLACK);
		  		 lblNewLabel_5_2.setBackground(Color.YELLOW);
		  		 
		  		 JLabel lblNewLabel_4_1 = new JLabel("A Receber:");
		  		 panel_7.add(lblNewLabel_4_1, "cell 1 4,alignx left,aligny bottom");
		  		 lblNewLabel_4_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 

		  		 
		  		  lblReceitasAReceber = new JLabel("0");
		  		  panel_7.add(lblReceitasAReceber, "cell 2 4,alignx left,aligny top");
		  		  lblReceitasAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  
		  		  JLabel lblValorAReceberlbl = new JLabel("Valor a Receber:");
		  		  lblValorAReceberlbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		  panel_7.add(lblValorAReceberlbl, "cell 3 4,alignx right,aligny bottom");
		  		  
		  		 
		  		  
		  		   lblValorAReceber = new JLabel("R$ 0,00");
		  		  lblValorAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  panel_7.add(lblValorAReceber, "cell 4 4,alignx left,aligny top");
		  		  
		  		  JLabel lblValorAPagar_1_1_1_1 = new JLabel("Valor a Vencer:");
		  		  lblValorAPagar_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		  panel_7.add(lblValorAPagar_1_1_1_1, "cell 3 5,alignx right");
		  		  
		  		   lblValorVencerAReceber = new JLabel("R$ 0,00");
		  		  lblValorVencerAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  panel_7.add(lblValorVencerAReceber, "cell 4 5");
		
		JPanel panel_3 = new JPanel();
		painelPrinciapl.add(panel_3, "cell 2 3,alignx right,aligny top");
		panel_3.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Novo Lançamento");
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(-1, null, isto);
				tela.setVisible(true);
				
			}
		});
		
		JButton btnNewButton_4 = new JButton("Excluir");
		btnNewButton_4.setBackground(new Color(204, 0, 0));
		btnNewButton_4.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir o Lançamento?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					    ArrayList<Lancamento> lancamentos_selecionados = getLancamentosSelecionado();
						
					    if(lancamentos_selecionados.size() == 1) {
					    
					    	Lancamento lancamento_excluir = lancamentos_selecionados.get(0);
					    	
					    ArrayList<FinanceiroPagamento> lista_pagamentos = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamentosPorLancamento(lancamento_excluir.getId_lancamento());
					
						if(lista_pagamentos.size() > 0) {
							JOptionPane.showMessageDialog(isto, "O lançamento selecionado possui pagamentos, exclua os primeiro");

						}else {
							boolean prosseguir = true;
						//excluir primeiro as parcelas
							GerenciarBancoParcelas gerenciar =  new GerenciarBancoParcelas();
							ArrayList<Parcela> lista_parcelas = gerenciar.getParcelasPorLancamento(lancamento_excluir.getId_lancamento());
							for(Parcela parcela : lista_parcelas) {
								boolean remover_parcela = gerenciar.removerParcela(parcela.getId_parcela());
								if(!remover_parcela) {
									prosseguir = false;
									JOptionPane.showMessageDialog(isto, "Erro ao excluir parcela do lançamento\nBanco de dados Corrompido\nConsulte o administrador");

									break;
								}
							}
						if(prosseguir) {	
							
						boolean exclusao = new GerenciarBancoLancamento().removerLancamento(lancamento_excluir.getId_lancamento());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}
						}
						}

						 pesquisar();
						 
						 
			        }else {
						JOptionPane.showMessageDialog(isto, "Para exclusão, selecione somente um lançamento por vez");

					}
					
				}
			}
		});
		panel_3.setLayout(new MigLayout("", "[63px][81px][61px][81px]", "[23px][][]"));
		panel_3.add(btnNewButton_4, "cell 0 0,alignx left,aligny top");
		
		JButton btnNewButton_3 = new JButton("Selecionar");
		btnNewButton_3.setBackground(new Color(0, 0, 51));
		btnNewButton_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_3.add(btnNewButton_3, "cell 1 0,alignx left,aligny top");
		
		JButton btnNewButton_2 = new JButton("Gerenciar");
		btnNewButton_2.setBackground(new Color(51, 0, 102));
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    ArrayList<Lancamento> lancamentos_selecionados = getLancamentosSelecionado();
				
			    if(lancamentos_selecionados.size() == 1) {
			    	Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
				TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar, isto);
				tela.setVisible(true);
			    }else if (lancamentos_selecionados.size() > 1) {
			    	boolean prosseguir = true;
			    	for(Lancamento lancamento : lancamentos_selecionados) {
			    		if(lancamento.getTipo_lancamento() != 0) {
			    			prosseguir = false;
			    			break;
			    		}
			    	}
			    	
			    	if(prosseguir) {
			    		TelaFinanceiroGerenciarLancamentoMultiplo tela = new TelaFinanceiroGerenciarLancamentoMultiplo(lancamentos_selecionados, isto);
						tela.setVisible(true);
			    	}else {
				    	JOptionPane.showMessageDialog(null, "Para gerenciar multiplos lançamentos,\ntodos devem ser do tipo 'Despesa' ");

			    	}
			    }
			    
			}
		});
		panel_3.add(btnNewButton_2, "cell 2 0,alignx left,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		JButton btnNewButton_1_1 = new JButton("Novo Empréstimo");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroEmprestimo tela = new TelaFinanceiroCadastroEmprestimo(-1, null, isto);
				tela.setVisible(true);
			}
		});
		
		JButton btnNewButton_3_1 = new JButton("Exportar");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = tabela_lancamentos.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_lancamentos.getRowSorter().convertRowIndexToModel(indice);

					Lancamento lancamento = lista_lancamentos.get(indexRowModel);
					lancamentos_selecionados.add(lancamento);
				}

				TelaEscolhaRelatorioLancamentos escolha_opcoes = new TelaEscolhaRelatorioLancamentos(lancamentos_selecionados,
						isto);
				escolha_opcoes.setVisible(true);

			}
		});
		btnNewButton_3_1.setForeground(Color.WHITE);
		btnNewButton_3_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_3_1.setBackground(new Color(51, 0, 51));
		panel_3.add(btnNewButton_3_1, "cell 1 1,growx");
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1_1.setBackground(new Color(0, 0, 102));
		panel_3.add(btnNewButton_1_1, "cell 3 1");
		
		JButton btnNewButton_1_1_1 = new JButton("Nova Transferência");
		btnNewButton_1_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1_1_1.setBackground(new Color(204, 153, 0));
		panel_3.add(btnNewButton_1_1_1, "cell 3 2");
		
		popular_centros_custo();
		popular_instituicao_bancaria();
		popular_grupo_contas();
		popular_contas();
		popular_condicao_pagamento();
		
		
		pesquisar();
		boolean pegar_datas = true;
		if(pegar_datas) {
		pegarDatas();
		}
		calcular();
		
		
		 cbCentroCusto.addItemListener(new ItemListener() {
			 	public void itemStateChanged(ItemEvent e) {
			 		filtrar();
			 	}
			 });
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void limpar(){
		sorter.setRowFilter(RowFilter.regexFilter(""));
		calcular();
	}
	
	public void pegarDatas() {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();

		Map<String,String> datas = new HashMap<String,String>();
		 datas = gerenciar.pegarDatas();
         
		 menorDataLancamento.setText(datas.get("menor_data_lancamento"));
          maiorDataLancamento.setText(datas.get("maior_data_lancamento"));
         
          menorDataVencimento.setText(datas.get("menor_data_vencimento"));
          maiorDataVencimento.setText(datas.get("maior_data_vencimento"));
          
          menorDataPagamento.setText(datas.get("menor_data_pagamento"));
          maiorDataPagamento.setText(datas.get("maior_data_pagamento"));
	}
	
	public void pesquisar() {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		lista_lancamentos.clear();
		modelo_lancamentos.onRemoveAll();
		


		 
		lista_lancamentos = gerenciar.buscaLancamentosCompletos();
		for(Lancamento cc : lista_lancamentos) {
			modelo_lancamentos.onAdd(cc);
		}
		
		calcular();
	}
	
public void filtrar() {
		
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
/*
private final int id = 0;
		private final int data = 1;
		private final int tipo_lancamento = 2;
		private final int prioridade = 3;
	
		private final int centro_custo = 4;
		private final int identificador_geral = 5;
		private final int destinatario_nf = 6;
		private final int cliente_fornecedor = 7;
		private final int grupo_conta = 8;
		private final int conta = 9;
		private final int valor = 10;
		private final int valor_pago = 11;
		private final int valor_a_pagar = 12;
		private final int valor_proxima_parcela_a_vencer = 13;

		private final int data_vencimento = 14;
		private final int data_pagamento = 15;
		private final int status = 16;
		private final int situacao = 17;
		private final int condicao_pagamento = 18;
		private final int status_condicao_pagamento = 19;
		private final int status_contador = 20;

		*/
		
		  String data_inicial_filtrar_data_lancamento = menorDataLancamento.getText().replace(" ", "");
		    String data_final_filtrar_data_lancamento = maiorDataLancamento.getText().replace(" ", "");
		    
		    if(checkString(data_inicial_filtrar_data_lancamento) && checkString(data_final_filtrar_data_lancamento) ) {
			Date data_menor = null;
			Date data_maior = null ;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_lancamento);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_lancamento);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}
			
			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER,
					data_menor, 1));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_menor, 1));
			filters.add(RowFilter.orFilter(datas));
	     
		  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

		   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
					data_maior, 1));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_maior, 1));
			filters.add(RowFilter.orFilter(datas_maior));
		 
		 }
		    
		    String data_inicial_filtrar_data_vencimento = menorDataVencimento.getText().replace(" ", "");
		    String data_final_filtrar_data_vencimento = maiorDataVencimento.getText().replace(" ", "");
		    
		    if(checkString(data_inicial_filtrar_data_vencimento) && checkString(data_final_filtrar_data_vencimento) ) {
			Date data_menor = null;
			Date data_maior = null ;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_vencimento);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_vencimento);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}
			
			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER,
					data_menor, 15));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_menor, 15));
			filters.add(RowFilter.orFilter(datas));
	     
		  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

		   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
					data_maior, 15));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_maior, 15));
			filters.add(RowFilter.orFilter(datas_maior));
		 
		 }
		    
		    /*
		
		    String data_inicial_filtrar_data_pagamento = entPeriodoPagamentoInicial.getText().replace(" ", "");
		    String data_final_filtrar_data_pagamento = entPeriodoPagamentoFinal.getText().replace(" ", "");
		    
		    if(checkString(data_inicial_filtrar_data_pagamento) && checkString(data_final_filtrar_data_pagamento) ) {
			Date data_menor = null;
			Date data_maior = null ;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_pagamento);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_pagamento);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}
			
			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER,
					data_menor, 11));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_menor, 11));
			filters.add(RowFilter.orFilter(datas));
	     
		  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

		   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
					data_maior, 11));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_maior, 11));
			filters.add(RowFilter.orFilter(datas_maior));
		 
		 }
		    
		    */
		
		
		
		try {
			String s_centro_custo = "";
		if(checkString(cbCentroCusto.getSelectedItem().toString())) {
			
			s_centro_custo = cbCentroCusto.getSelectedItem().toString();
			if(!(s_centro_custo.equalsIgnoreCase("TODOS")))
			 filters.add(RowFilter.regexFilter(s_centro_custo, 4));

		}
		}catch(Exception t) {
			
		}
		
		if(cbTipoLancamento.getSelectedItem().toString() != null) {
			String s_tipo_lancamento = "";
			if(checkString(cbTipoLancamento.getSelectedItem().toString())) {
				s_tipo_lancamento = cbTipoLancamento.getSelectedItem().toString();
				if(!(s_tipo_lancamento.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_tipo_lancamento, 2));
				}
			}
			}
		
		
		if(cbPrioridade.getSelectedItem().toString() != null) {
			String s_prioridade = "";
			if(checkString(cbPrioridade.getSelectedItem().toString())) {
				s_prioridade = cbPrioridade.getSelectedItem().toString();
				if(!(s_prioridade.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_prioridade, 3));
				}

			}
			}
		
	
		if(entClienteFornecedor.getText() != null) {
			String s_cliente_servidor = "";
			if(checkString(entClienteFornecedor.getText())) {
				s_cliente_servidor = entClienteFornecedor.getText().toUpperCase();
				if(!(s_cliente_servidor.equalsIgnoreCase("TODOS")))
				filters.add(RowFilter.regexFilter(s_cliente_servidor, 7));
			}
		}
		
		

		if(entIdentificadorGeral.getText() != null) {
			String s_id_geral = "";
			if(checkString(entIdentificadorGeral.getText())) {
				s_id_geral = entIdentificadorGeral.getText().toUpperCase();
				
				filters.add(RowFilter.regexFilter(s_id_geral, 5));
			}
		}
	
		
		if(entDestinatarioNF.getText() != null) {
			String s_dest_nf = "";
			if(checkString(entDestinatarioNF.getText())) {
				s_dest_nf = entDestinatarioNF.getText().toUpperCase();
				
				filters.add(RowFilter.regexFilter(s_dest_nf, 6));
			}
		}
	
		
		if(cbGrupoConta.getSelectedItem().toString() != null) {
		String s_grupo_contas = "";
		if(checkString(cbGrupoConta.getSelectedItem().toString())) {
			s_grupo_contas = cbGrupoConta.getSelectedItem().toString();
			if(!(s_grupo_contas.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(s_grupo_contas, 8));

		}
		}
		
		
		if( cbConta.getSelectedItem().toString() != null) {
		String s_contas = "";
		if(checkString(cbConta.getSelectedItem().toString())) {
			s_contas = cbConta.getSelectedItem().toString();
			if(!(s_contas.equalsIgnoreCase("TODOS"))) {
			filters.add(RowFilter.regexFilter(s_contas, 9));
			}
		}
		}
		
		
		
		
		String s_tipo_conta = "";
		if(cbStatusLancamento.getSelectedIndex() == 1) {
			s_tipo_conta = "A Pagar";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
		}else if(cbStatusLancamento.getSelectedIndex() == 2) {
			s_tipo_conta = "Pago";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
		}else if(cbStatusLancamento.getSelectedIndex() == 3) {
			s_tipo_conta = "A Receber";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
		}else if(cbStatusLancamento.getSelectedIndex() == 4) {
			s_tipo_conta = "Recebido";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
		
		}
		
		if( cbSituacao.getSelectedItem().toString() != null) {
			String s_situacao = "";
			if(checkString(cbSituacao.getSelectedItem().toString())) {
				s_situacao = cbSituacao.getSelectedItem().toString();
				if(!(s_situacao.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_situacao, 18));
				}
			}
			}
		
		if( cbCondicaoPagamento.getSelectedItem().toString() != null) {
			String s_condicao = "";
			if(checkString(cbCondicaoPagamento.getSelectedItem().toString())) {
				s_condicao = cbCondicaoPagamento.getSelectedItem().toString();
				if(!(s_condicao.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_condicao, 19));
				}
			}
			}
		
		if( cbStatusCondicaoPagamento.getSelectedItem().toString() != null) {
			String s_status_condicao = "";
			if(checkString(cbStatusCondicaoPagamento.getSelectedItem().toString())) {
				s_status_condicao = cbStatusCondicaoPagamento.getSelectedItem().toString();
				if(!(s_status_condicao.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_status_condicao, 20));
				}
			}
			
			}
		
		
		if( cbStatusAoContador.getSelectedItem().toString() != null) {
			String s_status_contador = "";
			if(checkString(cbStatusAoContador.getSelectedItem().toString())) {
				s_status_contador = cbStatusAoContador.getSelectedItem().toString();
				if(!(s_status_contador.equalsIgnoreCase("TODOS"))) {
				filters.add(RowFilter.regexFilter(s_status_contador, 21));
				}
			}
			
			}
		
		
		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();
		

}


public void calcular() {

	int numero_lancamentos = 0;
	int numero_despesas = 0;
	int numero_despesas_a_pagar = 0;
	int numero_despesas_pago = 0;
	int numero_receitas = 0;
	int numero_receitas_a_receber = 0;
	int numero_receitas_recebido = 0;
	
	
	//despesas
	BigDecimal valor_total_despesas = BigDecimal.ZERO;
	BigDecimal valor_a_pagar = BigDecimal.ZERO;
	BigDecimal	valor_pago = BigDecimal.ZERO;
	BigDecimal	valor_total_juros_pago = BigDecimal.ZERO;
	BigDecimal valor_total_vencer_pagar =  BigDecimal.ZERO;
	
	//receitas
	BigDecimal valor_total_receitas = BigDecimal.ZERO;
	BigDecimal valor_a_receber = BigDecimal.ZERO;
	BigDecimal	valor_recebido = BigDecimal.ZERO;
	BigDecimal	valor_total_juros_recebido = BigDecimal.ZERO;
	BigDecimal valor_total_vencer_receber =  BigDecimal.ZERO;


	for (int row = 0; row < tabela_lancamentos.getRowCount(); row++) {

		int index = tabela_lancamentos.convertRowIndexToModel(row);
		Lancamento lancamento = modelo_lancamentos.getValue(index);
		
		if(lancamento.getStatus() == 0) {
			//despesas a pagar
			valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
			valor_a_pagar = valor_a_pagar.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
			
			valor_pago = valor_pago.add(lancamento.getValor_ja_pago());
			numero_despesas_a_pagar++;
	
			BigDecimal valor_total = lancamento.getValor();
			BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();
			
			BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);
			
			if(valor__ja_pago.compareTo(valor_total) > 0) 
				valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
			else 
			 valor_total_vencer_pagar = valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
					
		
		}else if(lancamento.getStatus() == 1) {
			//despesas ja paga
			valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
			valor_pago = valor_pago.add(lancamento.getValor());
	

			BigDecimal valor_total = lancamento.getValor();
			BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();
			
			BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);
			
			if(valor__ja_pago.compareTo(valor_total) > 0) 
				valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
			else 
			 valor_total_vencer_pagar = valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
					

			
			numero_despesas_pago++;
		}else if(lancamento.getStatus() == 2) {
			//receitas a receber
			valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
			valor_a_receber = valor_a_receber.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
			
			valor_recebido = valor_recebido.add(lancamento.getValor_ja_pago());

			BigDecimal valor_total_a_receber = lancamento.getValor();
			BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();
			
			BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
			
			if(valor__ja_recebido.compareTo(valor_total_a_receber) > 0) 
				valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
			 else 
			    valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
				
			
			numero_receitas_a_receber++;
		}else if(lancamento.getStatus() == 3) {
			//receitas recebidas
			valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
			valor_recebido = valor_recebido.add(lancamento.getValor());
	
			
			BigDecimal valor_total_a_receber = lancamento.getValor();
			BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();
			
			BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
			
			if(valor__ja_recebido.compareTo(valor_total_a_receber) > 0) 
				valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
			 else 
			    valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
				
			
			
			
			
			numero_receitas_recebido++;
		}
		
	}
	
	lblDespesasAPagar.setText(numero_despesas_a_pagar + "");
	lblDespesasPago.setText(numero_despesas_pago + "");
	lblTotalDespesas.setText((numero_despesas_a_pagar + numero_despesas_pago ) + "");
	lblReceitasAReceber.setText(numero_receitas_a_receber + "");
	lblReceitasRecebido.setText(numero_receitas_recebido + "");
	lblTotalReceitas.setText((numero_receitas_a_receber + numero_receitas_recebido) + "");
	
	
	//valores
	Locale ptBr = new Locale("pt", "BR");
	lblValorRestanteAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar));
	 lblValorPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago));
	 lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
	 lblValorTotalJurosPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_pago));
	 lblValorVencerAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_pagar));
	
	//receitas
	lblValorAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber));
	lblValorRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_recebido));
	lblValorReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
	 lblValorTotalJurosRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_recebido));
	 lblValorVencerAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_receber));

	
	
}

public boolean checkString(String txt) {
	return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
}

	
	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int tipo_lancamento = 2;
		private final int prioridade = 3;
	
		private final int centro_custo = 4;
		private final int identificador_geral = 5;
		private final int destinatario_nf = 6;
		private final int cliente_fornecedor = 7;
		private final int grupo_conta = 8;
		private final int conta = 9;
		private final int valor = 10;
		private final int valor_pago = 11;
		private final int valor_a_pagar = 12;
		private final int valor_proxima_parcela_a_vencer = 13;
		private final int juros = 14;

		private final int data_vencimento = 15;
		private final int data_pagamento = 16;
		private final int status = 17;
		private final int situacao = 18;
		private final int condicao_pagamento = 19;
		private final int status_condicao_pagamento = 20;
		private final int status_contador = 21;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID","Data Lançamento", "Tipo", "Prioridade", "Centro de Custo","Identificador Geral", "Destinatário da NF",
				"Cliente/Fornecedor", "Grupo de Contas", "Conta", "Valor Total", "Valor Pago/Recebido", "Valor a Pagar/Receber","Valor a Vencer", "Juros","Data Próximo Vencimento", "Data Último Pagamento","Status" , "Situação", "Condições de Pagamento", "Status Condição de Pagamento","Status Contador"};
		private final ArrayList<Lancamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs
		private GerenciarBancoCondicaoPagamentos gerenciar  = null;
		private ArrayList<CondicaoPagamento> lista_condicoes = null;			
		Locale ptBr = new Locale("pt", "BR");

		public LancamentoTableModel() {
			
			 gerenciar = new GerenciarBancoCondicaoPagamentos();
			 
			 lista_condicoes = gerenciar.getCondicaoPagamentos();

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case data:
				return Date.class;
			case tipo_lancamento:
				return String.class;
			case prioridade:
				return String.class;
			case centro_custo:
				return String.class;
			case identificador_geral:
				return String.class;
			case destinatario_nf:
				return String.class;
			case cliente_fornecedor:
				return String.class;
			case grupo_conta:
				return String.class;
			case conta:
				return String.class;
			case valor:
				return String.class;
			case valor_pago:
				return String.class;
			case valor_a_pagar:
				return String.class;
			case valor_proxima_parcela_a_vencer:
				return String.class;
			case juros:
				return String.class;
			case data_vencimento:
				return Date.class;
			case data_pagamento:
				return Date.class;
			case status:
				return String.class;
			case situacao:
				return String.class;
			case condicao_pagamento:
				return String.class;
			case status_condicao_pagamento:
				return String.class;
			case status_contador:
				return String.class;
	
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colunas[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			 Lancamento dado = dados.get(rowIndex);
			 
			
			switch (columnIndex) {
			case id:
				return dado.getId_lancamento();
			case tipo_lancamento:{
				if(dado.getTipo_lancamento() == 0) {
					return "DESPESAS";
				}else if(dado.getTipo_lancamento() == 1) {
					return "RECEITAS";
				}else if(dado.getTipo_lancamento() == 3) {
					return "EMPRESTIMOS";

				}
			}
			case prioridade:{
				int i_prioridade = dado.getPrioridade();
				if(i_prioridade == 0) {
					return "Alta Prioridade - Ainda esta semana";
				}else if(i_prioridade == 1) {
					return "Média Prioridade - Em menos de 15 dias";
				}
				else if(i_prioridade == 2) {
					return "Prioridade Leve - Ainda este mês";
				}
				else if(i_prioridade == 3) {
					return "Baixa Prioridade - Ainda este ano";
				}
				
			}
			case data:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_lancamento());
					return data_menor;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
			case centro_custo:{
				if(dado.getNome_centro_custo() != null)
					return dado.getNome_centro_custo();
				else
					return "INDEFINIDO";
			}
			
			case identificador_geral:{
				return (dado.getIdentificacao() != null) ? dado.getIdentificacao().toUpperCase() : "";

			}
			case destinatario_nf:{
				return (dado.getNome_destinatario_nf() != null) ? dado.getNome_destinatario_nf().toUpperCase() : "";
				
			}
		
			case cliente_fornecedor:{
				if(dado.getNome_cliente_fornecedor() != null)
				return dado.getNome_cliente_fornecedor().toUpperCase();
				else
					return "INDEFINIDO";
			}
			case grupo_conta:{
				return dado.getNome_grupo_contas();
			}
			case conta:{
				return dado.getNome_conta();
			}
		
			case valor:{
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
			}
			case valor_pago:{
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor_ja_pago());
				return valorString;
				
			}
			case valor_a_pagar:{
				BigDecimal valor_total = dado.getValor();
				BigDecimal valor_pago = dado.getValor_ja_pago();
				
				
				if(valor_pago.compareTo(valor_total) >= 0) {
					//esta quitado
					return ("R$ 0.00");
				}else {
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor().subtract(dado.getValor_ja_pago()));
					return valorString;
				}
				
			
				
			}
			case valor_proxima_parcela_a_vencer:{
				
				BigDecimal valor_total = dado.getValor();
				BigDecimal valor_pago = dado.getValor_ja_pago();
				
				BigDecimal valor_a_pagar = valor_total.subtract(valor_pago); 
				if(valor_pago.compareTo(valor_total) >= 0) {
					//divida quitada
					
					return ("R$ 0.00");
				
				
				
				}else {
					
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor_proximo_pagamento_a_vencer());
					return valorString;
				}
				
				
			}
			case juros:{
				BigDecimal valor_total = dado.getValor();
				BigDecimal valor_pago = dado.getValor_ja_pago();
				
				BigDecimal valor_restante = valor_pago.subtract(valor_total);
				
				if(valor_pago.compareTo(valor_total) > 0) {
					
					return NumberFormat.getCurrencyInstance(ptBr).format(valor_total.subtract(valor_pago));
				}else {
					return ("R$ 0.00");
				}
			}
			case data_vencimento:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_vencimento());
					return data_menor;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
			case data_pagamento:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_pagamento());
					return data_menor;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
			case status:{
				int status = dado.getStatus();
				if(status == 0) {
					return ("A Pagar");

				}else if(status == 1) {
					return ("Pago");

				}else if(status == 2) {
					return ("A Receber");

				}else if(status == 3) {
					return ("Recebido");

				}
			}
			case situacao:{
				try {
				//data hoje
				LocalDate hoje = LocalDate.now();
				
				
				//data vencimento
				Date data_vencimento = null;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_vencimento = formato.parse(dado.getData_vencimento());

					try {
						LocalDate ld_data_vencimento = data_vencimento.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
						
						if(ld_data_vencimento.isAfter(hoje)) {
							return "Em dias";
						}else {
							return "Atrazado";
							
						}
						}catch(Exception e) {
							return "Datas Invalidas";
						}
					
				} catch (NullPointerException e ) {
					return "Datas Invalidas";

				}	catch (Exception e ) {
					return "Datas Invalidas";

				}					
				
				}catch(Exception e) {
					return "Datas Invalidas";

				}
			}
			case condicao_pagamento:{
				try {
					String condicoes = "";
				String array_condicoes_pagamento = dado.getIds_forma_pagamento();
				String ids [] = array_condicoes_pagamento.split(",");
				for(String id : ids) {
					int id_condicao_pagamento = Integer.parseInt(id);
					if(id_condicao_pagamento > 0) {
						
						CondicaoPagamento condicao = null;
						for(CondicaoPagamento cond : lista_condicoes) {
							if(cond.getId_condicao_pagamento() == id_condicao_pagamento) {
								condicao = cond;
								break;
							}
						}

						if(condicao != null);
						condicoes += (condicao.getNome_condicao_pagamento() + "|");
					}
					
				}
				return condicoes;
				
				}catch(Exception e) {
					return "";
				}
				
			}
			case status_condicao_pagamento:{
				try {
					String retorno = "";
				String array_status = dado.getStatus_forma_pagamento();
				String status [] = array_status.split(",");
				for(String id : status) {
					int id_status = Integer.parseInt(id);
					if(id_status == 0) {
						//cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
						//cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
						retorno += ("A - Compensar|Realizar|Concluir;");
					}else if(id_status == 1) {
						retorno += ("Compensado|Realizado|Concluído;");

					}
					
				}
				return retorno;
				
				}catch(Exception e) {
					return "";
				}
				
			}
			case status_contador:{
				int status = dado.getContador();
			
				if(status == 0) {
					return "Não se aplica".toUpperCase();
				}else if(status == 1) {
					return "Não Enviado ao contador".toUpperCase();

				}else if(status == 2) {
					return "Enviado ao contador".toUpperCase();
				}

			}
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Lancamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public Lancamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(Lancamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(Lancamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<Lancamento> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(Lancamento dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public Lancamento onGet(int row) {
			return dados.get(row);
		}
	}
	
	public ArrayList<Lancamento> getLancamentosSelecionado() {
		
		
		
		ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<>();
			int linhas_selecionadas[] = tabela_lancamentos.getSelectedRows();//pega o indice da linha na tabela
			
			for(int i = 0; i < linhas_selecionadas.length; i++) {
				
				int indice = tabela_lancamentos.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);//converte pro indice do model				
					Lancamento lancamento_selecionado = lista_lancamentos.get(indice);
					lancamentos_selecionados.add(lancamento_selecionado);
			}
			
			
		return lancamentos_selecionados;
	}
	
	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
	}

	public void adicionarFocus(Component[] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				if (c instanceof JTextFieldPersonalizado) {

					JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
					caixa_texto.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							System.out.println("Ganhou focu");
							caixa_texto.setFocusGained();

						}

						@Override
						public void focusLost(FocusEvent e) {

							caixa_texto.setFocusLost();
						}
					});
				}
			} else {
				Container novo_container = (Container) c;
				// if (0 < novo_container.getComponents())
				{
					adicionarFocus(novo_container.getComponents());
				}
			}
		}
	}
	
	public void popular_grupo_contas() {
		ArrayList<FinanceiroGrupoContas> lista_grupo_contas = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContass();
		cbGrupoConta.removeAllItems();
		cbGrupoConta.addItem("TODOS");
		for(FinanceiroGrupoContas grupo_contas: lista_grupo_contas) {
			cbGrupoConta.addItem(grupo_contas.getNome());
		}
		
	}
	
	public void popular_centros_custo() {
		ArrayList<CentroCusto> lista_centro_custos = new GerenciarBancoCentroCustos().getCentroCustos();
		cbCentroCusto.removeAllItems();
		cbCentroCusto.addItem("TODOS");
		for(CentroCusto cc: lista_centro_custos) {
			cbCentroCusto.addItem(cc.getNome_centro_custo());
		}
		
	}
	
	
	public void popular_instituicao_bancaria() {
		ArrayList<InstituicaoBancaria> lista_ibs = new GerenciarBancoInstituicaoBancaria().getInstituicoesBancarias();
		cbInstituicaoBancaria.removeAllItems();
		cbInstituicaoBancaria.addItem("TODOS");
		for(InstituicaoBancaria ib: lista_ibs) {
			cbInstituicaoBancaria.addItem(ib.getNome_instituicao_bancaria());
		}
		
	}
	
	
	
	public void popular_contas() {
		ArrayList<FinanceiroConta> lista_contas = new GerenciarBancoFinanceiroConta().getFinanceiroContas();
		cbConta.removeAllItems();
		cbConta.addItem("TODOS");
		for(FinanceiroConta contas: lista_contas) {
			cbConta.addItem(contas.getNome());
		}
		
	}
	
	public void popular_condicao_pagamento() {
		ArrayList<CondicaoPagamento> lista_condicao_pagamentos = new GerenciarBancoCondicaoPagamentos().getCondicaoPagamentos();
		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem("TODOS");
		for(CondicaoPagamento cp: lista_condicao_pagamentos) {
			cbCondicaoPagamento.addItem(cp.getNome_condicao_pagamento());
		}
		
	}
	

	
	class LancamentosRender extends DefaultTableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);
		    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		    if( value instanceof Date) {
	            value = f.format(value);
	        }

			int status = -1;
			
			String s_status = (String) table.getValueAt(row, 17);
			if(s_status.equalsIgnoreCase("A Pagar")) {
				status = 0;
			}else if(s_status.equalsIgnoreCase("Pago")) {
				status = 1;
			}else if(s_status.equalsIgnoreCase("A Receber")) {
				status = 2;
			}else if(s_status.equalsIgnoreCase("Recebido")) {
				status = 3;
			}
			
			

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {
				if(status == 0) {
					//return ("A Pagar");
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));

				}else if(status == 1) {
					//return ("Pago");
					renderer.setBackground(Color.orange);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				}else if(status == 2) {
					//return ("A Receber");
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				}else if(status == 3) {
					//return ("Recebido");
					renderer.setBackground(new Color(0, 51, 0));
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				}

			}

			return super.getTableCellRendererComponent(table, value, isSelected,
	               hasFocus, row, column);
			//return renderer;
		}
	}
	
	
	public String pegarDataHoje() {

		LocalDate hoje = LocalDate.now();
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}
	
	public String pegarDataMais(int anos) {

		LocalDate hoje = LocalDate.now().plusYears(1);
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}
	
	public String pegarDataMenos(int anos) {

		LocalDate hoje = LocalDate.now().minusYears(1);
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}
	
	public void filtroRapidoDespesaAtrazo(int dias) {
		cbStatusLancamento.setSelectedIndex(1);//setdespesa
		cbSituacao.setSelectedIndex(1);//set atrazados
		
		LocalDate hoje = LocalDate.now();
		String s_hoje  = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		maiorDataVencimento.setText(s_hoje);
		
		LocalDate menor_data  = hoje.minusDays(dias);
		String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		menorDataVencimento.setText(s_menor_data);
		
		filtrar();
	}
	
	public void filtroRapidoDespesaEmDias(int dias) {
		cbStatusLancamento.setSelectedIndex(1);//setdespesa
		cbSituacao.setSelectedIndex(2);//set atrazados
		
		LocalDate hoje = LocalDate.now();
		String s_hoje  = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		menorDataVencimento.setText(s_hoje);
		
		LocalDate menor_data  = hoje.plusDays(dias);
		String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		maiorDataVencimento.setText(s_menor_data);
		
		filtrar();
	}
	
	public void filtroRapidoReceitaAtrazo(int dias) {
		cbStatusLancamento.setSelectedIndex(3);//setdespesa
		cbSituacao.setSelectedIndex(1);//set atrazados
		
		LocalDate hoje = LocalDate.now();
		String s_hoje  = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		maiorDataVencimento.setText(s_hoje);
		
		LocalDate menor_data  = hoje.minusDays(dias);
		String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		menorDataVencimento.setText(s_menor_data);
		
		filtrar();
	}
	
	public void filtroRapidoReceitaEmDias(int dias) {
		cbStatusLancamento.setSelectedIndex(3);//setdespesa
		cbSituacao.setSelectedIndex(2);//set atrazados
		
		LocalDate hoje = LocalDate.now();
		String s_hoje  = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		menorDataVencimento.setText(s_hoje);
		
		LocalDate menor_data  = hoje.plusDays(dias);
		String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		maiorDataVencimento.setText(s_menor_data);
		
		filtrar();
	}
	
}
