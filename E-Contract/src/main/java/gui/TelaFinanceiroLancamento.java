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
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.Lancamento;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

public class TelaFinanceiroLancamento extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroLancamento isto;
	 private JTable tabela_lancamentos;
	 private ArrayList<Lancamento> lista_lancamentos = new ArrayList<>();
	 private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
	 private JDialog telaPai;
	  private JLabel lblValorAPagar, lblValorPago, lblValorDespesas;
	 private TableRowSorter<LancamentoTableModel> sorter;
	 private JComboBox cbStatusLancamento;
	 private JComboBox cbGrupoConta,cbCondicaoPagamento,cbTipoLancamento;
	 private JComboBox cbConta ,cbCentroCusto,cbInstituicaoBancaria;
	 private JTextField entClienteServidor;
	 private JLabel lblDespesasAPagar, lblDespesasPago,	lblTotalDespesas, lblReceitasRecebido, lblTotalReceitas, lblReceitasAReceber;
	 private JTextField entPeriodoLancamentoInicial;
	 private JTextField entPeriodoLancamentoFinal;
	 private JTextField entPeriodoVencimentoInicial;
	 private JTextField entPeriodoVencimentoFinal;
	 private JTextField entPeriodoPagamentoInicial;
	 private JTextField entPeriodoPagamentoFinal;
	 private JLabel lblValorAReceber, lblValorRecebido, lblValorReceitas;
	 
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
		panel_1.setLayout(new MigLayout("", "[grow][grow][123px,grow][465px,grow][grow][grow][grow][grow][grow]", "[31px,grow][47px,grow][31px,grow][grow][]"));
		
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
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Condição do Pagamento:");
		lblNewLabel_1_1_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_3, "cell 4 0,alignx trailing");
		
		 cbCondicaoPagamento = new JComboBox();
		cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbCondicaoPagamento, "cell 5 0,growx");
		
		JButton btnLimpar = new JButton("Limpar");
		panel_1.add(btnLimpar, "cell 6 0,alignx right");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBackground(new Color(0, 0, 102));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnFiltar = new JButton("Filtrar");
		panel_1.add(btnFiltar, "cell 7 0");
		btnFiltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltar.setBackground(new Color(0, 0, 102));
		btnFiltar.setForeground(Color.WHITE);
		
		JButton btnNewButton = new JButton("pesquisar");
		panel_1.add(btnNewButton, "cell 8 0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Instituição Bancária:");
		lblNewLabel_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2_1, "cell 0 1,alignx trailing");
		
		 cbInstituicaoBancaria = new JComboBox();
		panel_1.add(cbInstituicaoBancaria, "cell 1 1,growx");
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Grupo de Contas:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1, "cell 2 1,alignx right,aligny center");
		
		 cbGrupoConta = new JComboBox();
		cbGrupoConta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbGrupoConta, "cell 3 1,grow");
		
		JLabel lblNewLabel_1_1_4 = new JLabel("Status:");
		lblNewLabel_1_1_4.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_4, "cell 4 1,alignx right");
		
		 cbStatusLancamento = new JComboBox();
		 cbStatusLancamento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		 panel_1.add(cbStatusLancamento, "cell 5 1,growx,aligny top");
		 cbStatusLancamento.addItem("TODOS");
		 cbStatusLancamento.addItem("DESPESAS A PAGAR");
		 cbStatusLancamento.addItem("DESPESAS PAGAS");
		 cbStatusLancamento.addItem("RECEITAS A RECEBER");
		 cbStatusLancamento.addItem("RECEITAS RECEBIDAS");
		
		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Cliente/Fornecedor:");
		lblNewLabel_1_1_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2_1_1, "cell 0 2,alignx trailing");
		
		entClienteServidor = new JTextField();
		panel_1.add(entClienteServidor, "cell 1 2,growx");
		entClienteServidor.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Conta:");
		lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1_1, "cell 2 2,alignx right,aligny center");
		
		 cbConta = new JComboBox();
		cbConta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbConta, "cell 3 2,growx,aligny center");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_1.add(panel_4, "cell 0 3 3 1,alignx left,growy");
		panel_4.setLayout(new MigLayout("", "[][][][][][][][]", "[]"));
		
		JLabel lblNewLabel_1_1_4_1 = new JLabel("Período Lançamento:");
		panel_4.add(lblNewLabel_1_1_4_1, "cell 0 0");
		lblNewLabel_1_1_4_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JLabel lblNewLabel_8 = new JLabel("De");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblNewLabel_8, "cell 1 0,alignx trailing");
		
		entPeriodoLancamentoInicial = new JTextField();
		panel_4.add(entPeriodoLancamentoInicial, "cell 2 0 3 1,growx");
		entPeriodoLancamentoInicial.setColumns(10);
		entPeriodoLancamentoInicial.setText(pegarDataMenos(1));
		
		JLabel lblNewLabel_6 = new JLabel("a");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblNewLabel_6, "cell 5 0,alignx trailing");
		
		entPeriodoLancamentoFinal = new JTextField();
		panel_4.add(entPeriodoLancamentoFinal, "cell 6 0 2 1,growx");
		entPeriodoLancamentoFinal.setColumns(10);
		entPeriodoLancamentoFinal.setText(pegarDataMais(1));
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(Color.WHITE);
		panel_1.add(panel_4_1, "cell 3 3,grow");
		panel_4_1.setLayout(new MigLayout("", "[][][grow][][grow]", "[][]"));
		
		JLabel lblNewLabel_1_1_4_1_1 = new JLabel("Período Vencimento:");
		panel_4_1.add(lblNewLabel_1_1_4_1_1, "cell 0 0");
		lblNewLabel_1_1_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JLabel lblNewLabel_8_1 = new JLabel("De");
		lblNewLabel_8_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1.add(lblNewLabel_8_1, "cell 1 0,alignx trailing");
		
		entPeriodoVencimentoInicial = new JTextField();
		entPeriodoVencimentoInicial.setText((String) null);
		entPeriodoVencimentoInicial.setColumns(10);
		entPeriodoVencimentoInicial.setText(pegarDataMenos(1));

		panel_4_1.add(entPeriodoVencimentoInicial, "cell 2 0,growx");
		
		JLabel lblNewLabel_6_1 = new JLabel("a");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1.add(lblNewLabel_6_1, "cell 3 0,alignx trailing");
		
		entPeriodoVencimentoFinal = new JTextField();
		entPeriodoVencimentoFinal.setText((String) null);
		entPeriodoVencimentoFinal.setColumns(10);
		entPeriodoVencimentoFinal.setText(pegarDataMais(1));
		panel_4_1.add(entPeriodoVencimentoFinal, "cell 4 0,growx");
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setBackground(Color.WHITE);
		panel_1.add(panel_4_1_1, "cell 4 3 5 1,grow");
		panel_4_1_1.setLayout(new MigLayout("", "[][][grow][][grow]", "[]"));
		
		JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Período Pagamento:");
		lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_4_1_1.add(lblNewLabel_1_1_4_1_1_1, "cell 0 0");
		
		JLabel lblNewLabel_8_1_1 = new JLabel("De");
		lblNewLabel_8_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1_1.add(lblNewLabel_8_1_1, "cell 1 0,alignx trailing");
		
		entPeriodoPagamentoInicial = new JTextField();
		entPeriodoPagamentoInicial.setText((String) null);
		entPeriodoPagamentoInicial.setColumns(10);
		entPeriodoPagamentoInicial.setText(pegarDataMenos(1));
		panel_4_1_1.add(entPeriodoPagamentoInicial, "cell 2 0,growx");
		
		JLabel lblNewLabel_6_1_1 = new JLabel("a");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4_1_1.add(lblNewLabel_6_1_1, "cell 3 0,alignx trailing");
		
		entPeriodoPagamentoFinal = new JTextField();
		entPeriodoPagamentoFinal.setText((String) null);
		entPeriodoPagamentoFinal.setColumns(10);
		entPeriodoPagamentoFinal.setText(pegarDataMais(1));
		
		
		LancamentosRender renderer = new LancamentosRender();

		panel_4_1_1.add(entPeriodoPagamentoFinal, "cell 4 0,growx");
		
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
		painelDespesas.setLayout(new MigLayout("", "[][][][][]", "[][][][]"));
		
		JLabel lblNewLabel_1 = new JLabel("Despesas:");
		painelDespesas.add(lblNewLabel_1, "cell 0 0");
		
		JLabel lblNewLabel_5 = new JLabel("        ");
		painelDespesas.add(lblNewLabel_5, "cell 0 1,alignx right");
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBackground(Color.RED);
		lblNewLabel_5.setForeground(Color.BLACK);
		
		JLabel lblNewLabel_3 = new JLabel("A Pagar:");
		painelDespesas.add(lblNewLabel_3, "cell 1 1");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		 lblDespesasAPagar = new JLabel("0");
		 painelDespesas.add(lblDespesasAPagar, "flowx,cell 2 1");
		 lblDespesasAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		 
		 JLabel lblValorAPagarlbl = new JLabel("Valor a Pagar:");
		 lblValorAPagarlbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		 painelDespesas.add(lblValorAPagarlbl, "cell 3 1,alignx right");
		 
		 
		 
		  lblValorAPagar = new JLabel("0");
		 lblValorAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		 painelDespesas.add(lblValorAPagar, "cell 4 1");
		 
		 JLabel lblNewLabel_5_1 = new JLabel("        ");
		 painelDespesas.add(lblNewLabel_5_1, "cell 0 2,alignx right");
		 lblNewLabel_5_1.setOpaque(true);
		 lblNewLabel_5_1.setForeground(Color.BLACK);
		 lblNewLabel_5_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNewLabel_4 = new JLabel("Pago:");
		 painelDespesas.add(lblNewLabel_4, "cell 1 2");
		 lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 14));
		 
		  lblDespesasPago = new JLabel("0");
		  painelDespesas.add(lblDespesasPago, "flowx,cell 2 2");
		  lblDespesasPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		  
		  JLabel lblValorPagolbl = new JLabel("Valor Pago:");
		  lblValorPagolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  painelDespesas.add(lblValorPagolbl, "cell 3 2,alignx right");
		  
		   lblValorPago = new JLabel("0");
		  lblValorPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		  painelDespesas.add(lblValorPago, "cell 4 2");
		  
		  JLabel lblNewLabel_7 = new JLabel("Despesas:");
		  painelDespesas.add(lblNewLabel_7, "cell 1 3");
		  lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  
		  		 lblTotalDespesas = new JLabel("0");
		  		 painelDespesas.add(lblTotalDespesas, "flowx,cell 2 3");
		  		 lblTotalDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		 
		  		 JLabel lblValorDespesaslbl = new JLabel("Valor Despesas:");
		  		 lblValorDespesaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 painelDespesas.add(lblValorDespesaslbl, "cell 3 3,alignx right");
		  		 
		  		  lblValorDespesas = new JLabel("0");
		  		 lblValorDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		 painelDespesas.add(lblValorDespesas, "cell 4 3");
		  		 
		  		 JPanel panel_7 = new JPanel();
		  		 panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		  		 panel_7.setBackground(Color.WHITE);
		  		 panel_5.add(panel_7, "cell 1 0,grow");
		  		 panel_7.setLayout(new MigLayout("", "[][][][][]", "[][][][]"));
		  		 
		  		 JLabel lblNewLabel_1_2 = new JLabel("Receitas:");
		  		 panel_7.add(lblNewLabel_1_2, "cell 0 0");
		  		 
		  		 JLabel lblNewLabel_5_2 = new JLabel("        ");
		  		 panel_7.add(lblNewLabel_5_2, "cell 0 1,alignx right");
		  		 lblNewLabel_5_2.setOpaque(true);
		  		 lblNewLabel_5_2.setForeground(Color.BLACK);
		  		 lblNewLabel_5_2.setBackground(Color.YELLOW);
		  		 
		  		 JLabel lblNewLabel_4_1 = new JLabel("A Receber:");
		  		 panel_7.add(lblNewLabel_4_1, "cell 1 1");
		  		 lblNewLabel_4_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		 

		  		 
		  		  lblReceitasAReceber = new JLabel("0");
		  		  panel_7.add(lblReceitasAReceber, "cell 2 1");
		  		  lblReceitasAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  
		  		  JLabel lblValorAReceberlbl = new JLabel("Valor a Receber:");
		  		  lblValorAReceberlbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		  panel_7.add(lblValorAReceberlbl, "cell 3 1,alignx right");
		  		  
		  		 
		  		  
		  		   lblValorAReceber = new JLabel("R$ 0,00");
		  		  lblValorAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		  panel_7.add(lblValorAReceber, "cell 4 1");
		  		  
		  		  JLabel lblNewLabel_5_2_1 = new JLabel("        ");
		  		  panel_7.add(lblNewLabel_5_2_1, "cell 0 2,alignx right");
		  		  lblNewLabel_5_2_1.setOpaque(true);
		  		  lblNewLabel_5_2_1.setForeground(Color.BLACK);
		  		  lblNewLabel_5_2_1.setBackground(new Color(0, 51, 0));
		  		  
		  		  JLabel lblNewLabel_4_1_1 = new JLabel("Recebido:");
		  		  panel_7.add(lblNewLabel_4_1_1, "cell 1 2");
		  		  lblNewLabel_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		  
		  		   lblReceitasRecebido = new JLabel("0");
		  		   panel_7.add(lblReceitasRecebido, "cell 2 2");
		  		   lblReceitasRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		   
		  		   JLabel lblValorRecebidolbl = new JLabel("Valor Recebido:");
		  		   lblValorRecebidolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		   panel_7.add(lblValorRecebidolbl, "cell 3 2,alignx right");
		  		   
		  		    lblValorRecebido = new JLabel("R$ 0,00");
		  		   lblValorRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		   panel_7.add(lblValorRecebido, "cell 4 2");
		  		   
		  		   JLabel lblNewLabel_7_1 = new JLabel("Receitas:");
		  		   panel_7.add(lblNewLabel_7_1, "cell 1 3");
		  		   lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		   
		  		   
		  		    lblTotalReceitas = new JLabel("0");
		  		    panel_7.add(lblTotalReceitas, "cell 2 3");
		  		    lblTotalReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		    
		  		    JLabel lblValorReceitaslbl = new JLabel("Valor Receitas:");
		  		    lblValorReceitaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		  		    panel_7.add(lblValorReceitaslbl, "cell 3 3,alignx right");
		  		    
		  		     lblValorReceitas = new JLabel("R$ 0,00");
		  		    lblValorReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));
		  		    panel_7.add(lblValorReceitas, "cell 4 3");
		
		JPanel panel_3 = new JPanel();
		painelPrinciapl.add(panel_3, "cell 2 3,alignx right,aligny top");
		panel_3.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Cadastrar");
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
					
						boolean exclusao = new GerenciarBancoLancamento().removerLancamento(getLancamentoSelecionado().getId_lancamento());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

						 pesquisar();
			        }
				
			}
		});
		panel_3.setLayout(new MigLayout("", "[63px][81px][61px][81px]", "[23px]"));
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
				TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(getLancamentoSelecionado(), isto);
				tela.setVisible(true);
			}
		});
		panel_3.add(btnNewButton_2, "cell 2 0,alignx left,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		popular_centros_custo();
		popular_instituicao_bancaria();
		popular_grupo_contas();
		popular_contas();
		popular_condicao_pagamento();
		
		pesquisar();
		calcular();
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void limpar(){
		sorter.setRowFilter(RowFilter.regexFilter(""));
		calcular();
	}
	
	public void pesquisar() {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		lista_lancamentos.clear();
		modelo_lancamentos.onRemoveAll();
		
		lista_lancamentos = gerenciar.getLancamentos();
		for(Lancamento cc : lista_lancamentos) {
			modelo_lancamentos.onAdd(cc);
		}
	}
	
public void filtrar() {
		
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
/*private final int id = 0;
		private final int data = 1;
		private final int tipo_lancamento = 2;
		private final int centro_custo = 3;
		private final int instituicao_bancaria = 4;
		private final int cliente_fornecedor = 5;
		private final int grupo_conta = 6;
		private final int conta = 7;
		private final int condicao_pagamento = 8;
		private final int valor = 9;
		private final int data_vencimento = 10;
		private final int data_pagamento =11;
		private final int status = 12;
		*/
		  String data_inicial_filtrar_data_lancamento = entPeriodoLancamentoInicial.getText().replace(" ", "");
		    String data_final_filtrar_data_lancamento = entPeriodoLancamentoFinal.getText().replace(" ", "");
		    
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
		    
		    String data_inicial_filtrar_data_vencimento = entPeriodoVencimentoInicial.getText().replace(" ", "");
		    String data_final_filtrar_data_vencimento = entPeriodoVencimentoFinal.getText().replace(" ", "");
		    
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
					data_menor, 10));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_menor, 10));
			filters.add(RowFilter.orFilter(datas));
	     
		  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

		   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
					data_maior, 10));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_maior, 10));
			filters.add(RowFilter.orFilter(datas_maior));
		 
		 }
		
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
		
		String s_centro_custo = "";
		
		try {
		if(checkString(cbCentroCusto.getSelectedItem().toString())) {
			
			s_centro_custo = cbCentroCusto.getSelectedItem().toString();
			if(!(s_centro_custo.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(s_centro_custo, 3));

		}
		}catch(Exception t) {
			
		}
		
		if(cbTipoLancamento.getSelectedItem().toString() != null) {
			String s_tipo_lancamento = "";
			if(checkString(cbTipoLancamento.getSelectedItem().toString())) {
				s_tipo_lancamento = cbTipoLancamento.getSelectedItem().toString();
				if(!(s_tipo_lancamento.equalsIgnoreCase("TODOS")))
				filters.add(RowFilter.regexFilter(s_tipo_lancamento, 2));

			}
			}
		
		if(entClienteServidor.getText() != null) {
			String s_cliente_servidor = "";
			if(checkString(entClienteServidor.getText())) {
				s_cliente_servidor = entClienteServidor.getText().toUpperCase();
				if(!(s_cliente_servidor.equalsIgnoreCase("TODOS")))
				filters.add(RowFilter.regexFilter(s_cliente_servidor, 5));
			}
		}
		
		
		if(cbGrupoConta.getSelectedItem().toString() != null) {
		String s_grupo_contas = "";
		if(checkString(cbGrupoConta.getSelectedItem().toString())) {
			s_grupo_contas = cbGrupoConta.getSelectedItem().toString();
			if(!(s_grupo_contas.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(s_grupo_contas, 6));

		}
		}
		
		if( cbConta.getSelectedItem().toString() != null) {
		String s_contas = "";
		if(checkString(cbConta.getSelectedItem().toString())) {
			s_contas = cbConta.getSelectedItem().toString();
			if(!(s_contas.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(s_contas, 7));

		}
		}
		
		

		if( cbInstituicaoBancaria.getSelectedItem().toString() != null) {
		String s_ibs = "";
		if(checkString(cbInstituicaoBancaria.getSelectedItem().toString())) {
			s_ibs = cbInstituicaoBancaria.getSelectedItem().toString();
			if(!(s_ibs.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(s_ibs, 4));

		}
		}
		
		
		if( cbCondicaoPagamento.getSelectedItem().toString() != null) {
			String s_cP = "";
			if(checkString(cbCondicaoPagamento.getSelectedItem().toString())) {
				s_cP = cbCondicaoPagamento.getSelectedItem().toString();
				if(!(s_cP.equalsIgnoreCase("TODOS")))
				filters.add(RowFilter.regexFilter(s_cP, 8));

			}
			}
		
		String s_tipo_conta = "";
		if(cbStatusLancamento.getSelectedIndex() == 1) {
			s_tipo_conta = "A Pagar";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 12));
		}else if(cbStatusLancamento.getSelectedIndex() == 2) {
			s_tipo_conta = "Pago";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 12));
		}else if(cbStatusLancamento.getSelectedIndex() == 3) {
			s_tipo_conta = "A Receber";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 12));
		}else if(cbStatusLancamento.getSelectedIndex() == 4) {
			s_tipo_conta = "Recebido";
			if (checkString(s_tipo_conta))
				filters.add(RowFilter.regexFilter(s_tipo_conta, 12));
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
	
	BigDecimal valor_a_pagar = BigDecimal.ZERO,valor_pago = BigDecimal.ZERO;
	BigDecimal valor_a_receber = BigDecimal.ZERO,valor_recebido = BigDecimal.ZERO;

	for (int row = 0; row < tabela_lancamentos.getRowCount(); row++) {

		int index = tabela_lancamentos.convertRowIndexToModel(row);
		Lancamento lancamento = modelo_lancamentos.getValue(index);
		
		if(lancamento.getStatus() == 0) {
			valor_a_pagar = valor_a_pagar.add(lancamento.getValor());
			numero_despesas_a_pagar++;
		}else if(lancamento.getStatus() == 1) {
			valor_pago = valor_pago.add(lancamento.getValor());
			numero_despesas_pago++;
		}else if(lancamento.getStatus() == 2) {
			valor_a_receber = valor_a_receber.add(lancamento.getValor());
			numero_receitas_a_receber++;
		}else if(lancamento.getStatus() == 3) {
			valor_recebido = valor_recebido.add(lancamento.getValor());
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
	//despesas
	lblValorAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar));
	lblValorPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago));
	lblValorDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago.add(valor_a_pagar)));

	//receitas
	lblValorAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber));
	lblValorRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_recebido));
	lblValorReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber.add(valor_recebido)));

	
	
}

public boolean checkString(String txt) {
	return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
}

	
	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int tipo_lancamento = 2;
		private final int centro_custo = 3;
		private final int instituicao_bancaria = 4;
		private final int cliente_fornecedor = 5;
		private final int grupo_conta = 6;
		private final int conta = 7;
		private final int valor = 8;
		private final int data_vencimento = 9;
		private final int status = 10;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID","Data Lançamento", "Tipo", "Centro de Custo", "Instituição Bancária",
				"Cliente/Fornecedor", "Grupo de Contas", "Conta", "Valor", "Data Vencimento", "Status" };
		private final ArrayList<Lancamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public LancamentoTableModel() {

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
			case centro_custo:
				return String.class;
			case instituicao_bancaria:
				return String.class;
			case cliente_fornecedor:
				return String.class;
			case conta:
				return String.class;
			case grupo_conta:
				return String.class;
			case tipo_lancamento:
				return String.class;
			case valor:
				return String.class;
			case data_vencimento:
				return Date.class;
			case status:
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
			
			 FinanceiroConta financeiro_conta = null;
			 if(dado.getId_conta() > 0) {
				 financeiro_conta = new GerenciarBancoFinanceiroConta().getFinanceiroConta(dado.getId_conta());
			 }
			
			
			switch (columnIndex) {
			case id:
				return dado.getId_lancamento();
			case tipo_lancamento:{
				/*if(financeiro_conta != null) {
					if(financeiro_conta.getTipo_conta() == 0) {
						return "DESPESAS";
					}else if(financeiro_conta.getTipo_conta() == 1) {
						return "RECEITAS";
					}
				}*/
				if(dado.getTipo_lancamento() == 0) {
					return "DESPESAS";
				}else if(dado.getTipo_lancamento() == 1) {
					return "RECEITAS";
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
				CentroCusto cc = null;
				if(dado.getId_centro_custo() > 0) {
				cc = new GerenciarBancoCentroCustos().getCentroCusto(dado.getId_centro_custo());
				 if(cc != null) {
					 return cc.getNome_centro_custo();
				 }
				
				
				}else {
					return "";
				}
			}
			case instituicao_bancaria:{
				InstituicaoBancaria ib = null;
				if(dado.getId_instituicao_bancaria() > 0) {
					ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(dado.getId_instituicao_bancaria());
				 if(ib != null) {
					 return ib.getNome_instituicao_bancaria();
				 }
				
				
				}else {
					return "";
				}
			}
			case cliente_fornecedor:{
				CadastroCliente c = null;
				if(dado.getId_cliente_fornecedor() > 0) {
					c = new GerenciarBancoClientes().getCliente(dado.getId_cliente_fornecedor());
				 if(c != null) {
					 if(c.getTipo_pessoa() == 0) {
						 return c.getNome_empresarial().toUpperCase();
					 }else {
						 return c.getNome_fantaia().toUpperCase();
					 }
				 }
				
				
				}else {
					return "";
				}
			}
			case grupo_conta:{
				if(financeiro_conta != null) {
					FinanceiroGrupoContas grupo = null;
					grupo = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContas(financeiro_conta.getId_grupo_contas());
					if(grupo != null)
						return grupo.getNome();
						
					
				}
			}
			case conta:{
				if(financeiro_conta != null) {
					return financeiro_conta.getNome();
				}
			}
		
		
			case valor:{
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
			}
			case data_vencimento:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_vencimento());
					return data_menor;

				} catch (ParseException e) {
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
	
	public Lancamento getLancamentoSelecionado() {
		int indiceDaLinha = tabela_lancamentos.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_lancamentos.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoLancamento gerenciar_cont = new GerenciarBancoLancamento();
		return gerenciar_cont.getLancamento(id_selecionado);
		
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
			String s_status = (String) table.getValueAt(row, 10);
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
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));

				}else if(status == 1) {
					//return ("Pago");
					renderer.setBackground(Color.orange);
					renderer.setForeground(Color.white);
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
	
}
