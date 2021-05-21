
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEscolha;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class TelaFinanceiroGerenciarLancamentoMultiplo extends JFrame  {

		private String caminho_global;
	private JTabbedPane painelPrincipal ;
	private JPanel painelPagamentos = new JPanel();
	private final JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
	private final JLabel lblNewLabel_1_1_1 = new JLabel("Cliente/Fornecedor:");
	private final JLabel entCentroCusto = new JLabel("Centro Custo");
	private final JLabel entClienteFornecedor = new JLabel("Cliente/Fornecedor");
	private final JLabel lblNewLabel_3 = new JLabel("As partes:");
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	Locale ptBr = new Locale("pt", "BR");
	private final JLabel lblNewLabel_3_2 = new JLabel("Devedor:");
	private final JLabel lblNewLabel_3_2_1 = new JLabel("Recebedor:");
	    private SwingController controllerParcela = null;
		private SwingViewBuilder factoryParcela;
		private String servidor_unidade;
		private final JPanel panel_1 = new JPanel();
		private final JLabel lblNewLabel_2 = new JLabel("Parcelas");
		private final JScrollPane scrollParcelas ;
		private final JPanel panel_2 = new JPanel();
		private final JScrollPane scrollPagamentos;
		private final JLabel lblNewLabel_4 = new JLabel("Pagamentos");
		private JTable tabela_pagamentos, tabela_parcelas;
		private ParcelaTableModel modelo_parcelas = new ParcelaTableModel();
		private ParcelaEmprestimoTableModel modelo_parcelas_emprestimo = new ParcelaEmprestimoTableModel();

		private ArrayList<Parcela> lista_parcelas = new ArrayList<>();
		private ArrayList<ParcelaEmprestimo> lista_parcelas_emprestimo = new ArrayList<>();

		private PagamentoTableModel modelo_pagamentos = new PagamentoTableModel();
		private ArrayList<FinanceiroPagamento> lista_pagamentos = new ArrayList<>();
		
		
		private final JPanel panel_3 = new JPanel();
		private final JPanel panel_4 = new JPanel();
		private final JPanel panel_8 = new JPanel();
		private final JPanel painelObservacao = new JPanel();
		private final JPanel painelCalculos = new JPanel();
		private final JLabel lblNewLabel_5 = new JLabel("Observação:");
		private final JTextArea entObservacaoParcela = new JTextArea();
		private final JLabel lblNewLabel_5_1 = new JLabel("Cálculos:");
		private final JLabel lblNewLabel_6_1 = new JLabel("Valor Total das Parcelas:");
		private final JLabel lblValorTotalDasParcelas = new JLabel("0000");
		private final JPanel panel_10 = new JPanel();
		private final JPanel panel_11 = new JPanel();
		private final JPanel panel_12 = new JPanel();
		private final JLabel lblNewLabel_5_2 = new JLabel("Observação:");
		private final JTextArea entObservacaoPagamento = new JTextArea();
		private final JLabel lblNewLabel_5_1_1 = new JLabel("Cálculos:");
		private final JLabel lblNewLabel_6_1_1 = new JLabel("Valor Total dos Pagamentos:");
		private final JLabel lblValorTotalDosPagamentos = new JLabel("0000");
		private final JPanel panel_13 = new JPanel();
		private final JPanel panel_14 = new JPanel();
		private final JPanel panel_15 = new JPanel();
		private final JPanel panel_16 = new JPanel();
		private final JPanel panel_17 = new JPanel();
		private final JLabel lblNewLabel_6_1_1_1 = new JLabel("Valor Total Restante:");
		private final JLabel lblValorTotalRestante = new JLabel("0000");
		private JPanel painel_vizualizar;
		private final JButton btnNewButton_4 = new JButton("Vizualizar");
		private final JButton btnNewButton_2_1 = new JButton("Vizualizar");
		  private SwingController controller = null;
			private SwingViewBuilder factory;
			private final TelaFinanceiroGerenciarLancamentoMultiplo isto;
			ArrayList<Lancamento> lancamentos_globais;
			
	public TelaFinanceiroGerenciarLancamentoMultiplo(ArrayList<Lancamento> lancamentos, Window janela_pai) {

		lancamentos_globais = lancamentos;
		setResizable(true);
		isto = this;
		
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getDadosGlobais();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);
		
		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);
		
	
		
		DadosGlobais dados = DadosGlobais.getInstance();
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);
	
		
			setTitle("E-Contract - Gerenciar Lançamentos - MÚLTIPLOS");

		painelPrincipal = new JTabbedPane();
		setContentPane(painelPrincipal);
		painelPagamentos.setBackground(new Color(255, 255, 255));
		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		panel_14.setBackground(new Color(255, 102, 0));
		
		painelPagamentos.add(panel_14, "cell 0 0,grow");
		panel_14.setLayout(new MigLayout("", "[grow][]", "[][grow][grow][]"));
		panel_14.add(lblNewLabel_2, "cell 0 0,alignx center");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_14.add(panel_1, "cell 0 1 2 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		tabela_parcelas = new JTable();
		tabela_parcelas.setRowHeight(30);
		
		tabela_parcelas.setModel(modelo_parcelas);
		
		scrollParcelas = new JScrollPane(tabela_parcelas);
		
		tabela_parcelas.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

		    public void valueChanged(ListSelectionEvent e) {

		        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		        if(!lsm.isSelectionEmpty()){
		        	int rowSel = tabela_parcelas.getSelectedRow();//pega o indice da linha na tabela
		        	Parcela parcela = new GerenciarBancoParcelas().getParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
		        	entObservacaoParcela.setText(parcela.getObservacao());
		        	
		        	
		        	
		        }
		    }
		});
		
		
		panel_1.add(scrollParcelas);
		panel_15.setBackground(Color.WHITE);
		
		panel_14.add(panel_15, "cell 0 2 2 1,grow");
		panel_15.setLayout(new MigLayout("", "[351.00][grow]", "[::100px,grow][::50px,grow][::50px,grow][grow]"));
		panel_15.add(painelObservacao, "cell 0 0 2 1,grow");
		painelObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelObservacao.setBackground(Color.WHITE);
		painelObservacao.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelObservacao.add(lblNewLabel_5, "cell 0 0");
		entObservacaoParcela.setLineWrap(true);
		entObservacaoParcela.setWrapStyleWord(true);
		entObservacaoParcela.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		entObservacaoParcela.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacaoParcela.setEditable(false);
		
		painelObservacao.add(entObservacaoParcela, "cell 1 0,grow");
		
		panel_15.add(painelCalculos, "cell 0 1 2 3,grow");
		painelCalculos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCalculos.setBackground(Color.WHITE);
		painelCalculos.setLayout(new MigLayout("", "[grow][40px,grow]", "[24px][24px]"));
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		
		painelCalculos.add(lblNewLabel_5_1, "cell 0 0 2 1,alignx center,aligny top");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelCalculos.add(lblNewLabel_6_1, "cell 0 1,alignx right,aligny center");
		lblValorTotalDasParcelas.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		painelCalculos.add(lblValorTotalDasParcelas, "cell 1 1,alignx left,aligny top");
		panel_16.setBackground(Color.WHITE);
		panel_14.add(panel_16, "cell 0 3 2 1,alignx center");
		panel_16.setLayout(new MigLayout("", "[69px][][][65px][67px]", "[31px]"));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = tabela_parcelas.getSelectedRow();//pega o indice da linha na tabela
					Parcela parcela = new GerenciarBancoParcelas().getParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
					if(parcela.getCaminho_arquivo() != null && parcela.getCaminho_arquivo().length() > 10)
		        	{
		        		String url = servidor_unidade + new GerenciarBancoLancamento().getLancamento(parcela.getId_lancamento_pai()).getDiretorio_lancamento() + "\\parcela_" + parcela.getId_parcela() +
	               "\\" +   parcela.getCaminho_arquivo();
		        		if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}
		        	
		        	}
				

	        	
			}
		});
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setBackground(new Color(0, 51, 0));
		btnNewButton_4.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_16.add(btnNewButton_4, "cell 1 0");
		panel_17.setBackground(new Color(0, 153, 153));
		
		painelPagamentos.add(panel_17, "cell 1 0,grow");
		panel_17.setLayout(new MigLayout("", "[grow]", "[][402.00,grow][193.00,grow][]"));
		panel_17.add(lblNewLabel_4, "cell 0 0,alignx center");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_17.add(panel_2, "cell 0 1,grow");
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		tabela_pagamentos = new JTable();
		tabela_pagamentos.setRowHeight(30);
		
		tabela_pagamentos.setModel(modelo_pagamentos);
		
		
		scrollPagamentos = new JScrollPane(tabela_pagamentos);
		 
		 tabela_pagamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			    public void valueChanged(ListSelectionEvent e) {

			        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			        if(!lsm.isSelectionEmpty()){
			    		
			        	int rowSel = tabela_pagamentos.getSelectedRow();//pega o indice da linha na tabela
			        	FinanceiroPagamento pagamento = modelo_pagamentos.getValue(rowSel);
			        	entObservacaoPagamento.setText(pagamento.getObservacao());
			    		
			        }
			    }
			});
		panel_2.setLayout(new BorderLayout(0, 0));
			
		 
		 
		panel_2.add(scrollPagamentos);
		panel_17.add(panel_10, "cell 0 2,grow");
		panel_10.setBackground(Color.WHITE);
		panel_10.setLayout(new MigLayout("", "[260px][grow]", "[84px,grow][4px][114px,grow][]"));
		panel_11.setBackground(Color.WHITE);
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_10.add(panel_11, "cell 0 0 2 2,grow");
		panel_11.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_11.add(lblNewLabel_5_2, "cell 0 0");
		entObservacaoPagamento.setFont(new Font("SansSerif", Font.BOLD, 14));
		entObservacaoPagamento.setLineWrap(true);
		entObservacaoPagamento.setWrapStyleWord(true);
		entObservacaoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_11.add(entObservacaoPagamento, "cell 1 0,grow");
		panel_12.setBackground(Color.WHITE);
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_10.add(panel_12, "cell 0 2 2 2,grow");
		panel_12.setLayout(new MigLayout("", "[175px,grow][40px,grow]", "[24px][24px][]"));
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		
		panel_12.add(lblNewLabel_5_1_1, "cell 0 0 2 1,alignx center,aligny top");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_12.add(lblNewLabel_6_1_1, "cell 0 1,alignx right,aligny center");
		lblValorTotalDosPagamentos.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		panel_12.add(lblValorTotalDosPagamentos, "cell 1 1,alignx left,aligny top");
		lblNewLabel_6_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_12.add(lblNewLabel_6_1_1_1, "cell 0 2,alignx right");
		lblValorTotalRestante.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		panel_12.add(lblValorTotalRestante, "cell 1 2");
		panel_17.add(panel_13, "cell 0 3,alignx center");
		panel_13.setBackground(Color.WHITE);
		panel_13.setLayout(new MigLayout("", "[64px][][][][][60px][90px]", "[28px]"));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = tabela_pagamentos.getSelectedRow();//pega o indice da linha na tabela

		        	FinanceiroPagamento pag = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamento(modelo_pagamentos.getValue(rowSel).getId_pagamento());
		        	if(pag.getCaminho_arquivo() != null && pag.getCaminho_arquivo().length() > 10)
		        	{
		        		String url = servidor_unidade + new GerenciarBancoLancamento().getLancamento(pag.getId_lancamento()).getDiretorio_lancamento() + "\\pagamento_" + pag.getId_pagamento() +
	                  "\\" + pag.getCaminho_arquivo();
		        		if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}
		        	
		        	}
	        	
	        	
	        	
			}
		});
		btnNewButton_2_1.setForeground(Color.WHITE);
		btnNewButton_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_2_1.setBackground(new Color(0, 51, 51));
		
		panel_13.add(btnNewButton_2_1, "cell 1 0");
		

		boolean chamar = true;
		if(chamar)
		 rotinas();
		this.setLocationRelativeTo(janela_pai);		
		
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
		servidor_unidade = configs_globais.getServidorUnidade();
	}
	
	
	
	public void rotinas() {
		
	
	

	 pesquisar_parcelas();
	 pesquisar_pagamentos();


	}
	
	public class ParcelaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_parcela = 0;
		private final int identificador = 1;
		private final int descricao = 2;
		private final int data_vencimento = 3;
		private final int valor = 4;
		private final int status = 5;
		
		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Vencimento", "Valor", "Status"};
		private final ArrayList<Parcela> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ParcelaTableModel() {

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
			case id_parcela:
				return Integer.class;
			case identificador:
				return String.class;
			case descricao:
				return String.class;
			case data_vencimento:
				return Date.class;
			case valor:
				return String.class;
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
			 Parcela dado = dados.get(rowIndex);
			
	
			
			switch (columnIndex) {
			case id_parcela:
				return dado.getId_parcela();
			case identificador:
				return dado.getIdentificador();
			case descricao:{
				return dado.getDescricao();
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
			case valor:{
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
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
			Parcela ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public Parcela getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(Parcela dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(Parcela dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<Parcela> dadosIn) {
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
		public void onRemove(Parcela dado) {
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

		public Parcela onGet(int row) {
			return dados.get(row);
		}
	}
	
	
	public class ParcelaEmprestimoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_parcela = 0;
		private final int identificador = 1;
		private final int descricao = 2;
		private final int data_vencimento = 3;
		private final int especie = 4;
		private final int quantidade = 5;
		private final int medida = 6;
		private final int valor_unitario = 7;

		private final int valor = 8;
		private final int status = 9;
		
		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Vencimento","Espécie", "Quantidade", "Medida", "Valor Unitário", "Valor Total", "Status"};
		private final ArrayList<ParcelaEmprestimo> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ParcelaEmprestimoTableModel() {

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
			case id_parcela:
				return Integer.class;
			case identificador:
				return String.class;
			case descricao:
				return String.class;
			case data_vencimento:
				return Date.class;
			case especie:
				return String.class;
			case quantidade:
				return String.class;
			case medida:
				return String.class;
			case valor_unitario:
				return String.class;
			case valor:
				return String.class;
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
			ParcelaEmprestimo dado = dados.get(rowIndex);
			
	
			
			switch (columnIndex) {
			case id_parcela:
				return dado.getId_parcela();
			case identificador:
				return dado.getIdentificador();
			case descricao:{
				return dado.getDescricao();
			}
			case data_vencimento:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_vencimento());
					return data_menor;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "Data Inválida";
				}				
				
			}
			case especie:{
				if(dado.getObjeto() == 0) {
					return "MOEDA";
				}else {
				return dado.getEspecie();
				}
			}
			case quantidade:{
				if(dado.getObjeto() == 0) {
					return "MOEDA";
				}else
				return dado.getQuantidade();
			}
			case medida:{
				if(dado.getObjeto() == 0) {
					return "MOEDA";
				}else
				return dado.getUnidade_medida();
				
			}
			case valor_unitario:{
				if(dado.getObjeto() == 0) {
					return "MOEDA";
				}else
				return dado.getValor_unitario();
			}
			case valor:{
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
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
			ParcelaEmprestimo ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public ParcelaEmprestimo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(ParcelaEmprestimo dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(ParcelaEmprestimo dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<ParcelaEmprestimo> dadosIn) {
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
		public void onRemove(ParcelaEmprestimo dado) {
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

		public ParcelaEmprestimo onGet(int row) {
			return dados.get(row);
		}
	}
	


	
	public void atualizar_informacoes() {
		rotinas();
	}

	
	public void pesquisar_parcelas() {

		lista_parcelas.clear();
		modelo_parcelas.onRemoveAll();
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
		BigDecimal valor_total_parcelas =  BigDecimal.ZERO;

		for(Lancamento lancamento_global : lancamentos_globais) {
		
		for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_global.getId_lancamento())) {

			modelo_parcelas.onAdd(parcela);
			lista_parcelas.add(parcela);
			valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());
			
	      }
		
		
		}
		lblValorTotalDasParcelas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas));

	}
	
	public void pesquisar_pagamentos() {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();

		lista_pagamentos.clear();
		modelo_pagamentos.onRemoveAll();
		BigDecimal valor_total_pagamentos =  BigDecimal.ZERO;
		BigDecimal valor_restante = BigDecimal.ZERO;
	for(Lancamento lancamento_global : lancamentos_globais) {	
		
		
		
		for (FinanceiroPagamento pagamento : gerenciar.getFinanceiroPagamentosPorLancamento(lancamento_global.getId_lancamento())) {

			modelo_pagamentos.onAdd(pagamento);
			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());
			
	      }
		
		valor_restante = valor_restante.add(lancamento_global.getValor().subtract(valor_total_pagamentos));

		
	}

	lblValorTotalDosPagamentos.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos));
	lblValorTotalRestante.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_restante));
	
		}
	
	
	public class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int identificador = 1;
		private final int pagador = 2;
		private final int recebedor = 3;
		private final int descricao = 4;
		private final int forma_pagamento = 5;
		private final int status = 6;
		private final int data_pagamento = 7;
		private final int valor = 8;
		
		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador","Pagador", "Recebedor", "Descrição","Forma de Pagamento", "Status",  "Data Pagamento", "Valor"};
		private final ArrayList<FinanceiroPagamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs
		GerenciarBancoInstituicaoBancaria gerenciar_ibs = new GerenciarBancoInstituicaoBancaria();
		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		
		public PagamentoTableModel() {

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
			case id_pagamento:
				return Integer.class;
			case identificador:
				return String.class;
			case pagador:
				return String.class;
			case recebedor:
				return String.class;
			case descricao:
				return String.class;
			case forma_pagamento:
				return String.class;
			case status:
				return String.class;
			case data_pagamento:
				return Date.class;
			case valor:
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
			 FinanceiroPagamento dado = dados.get(rowIndex);
	
			
			switch (columnIndex) {
			case id_pagamento:
				return dado.getId_pagamento();
			case identificador:
				return dado.getIdentificador();
			case pagador:{
					//é uma despesa, retornar o id da ib
					if(dado.getId_pagador()  > 0) {
					InstituicaoBancaria ib = gerenciar_ibs.getInstituicaoBancaria(dado.getId_pagador());
					return ib.getNome_instituicao_bancaria();
					}else {
						return "INDEFINIDO";

					}
				
			}
			case recebedor:{
					//é uma despesa, retornar o proprio cliente deste lançamento
					CadastroCliente cliente;
					  try {
						  cliente = new GerenciarBancoClientes().getCliente(dado.getId_recebedor());
						  String nome_cliente = "";
						  if(cliente.getTipo_pessoa() == 0) {
							  return cliente.getNome_empresarial();
						  }else
							 return  nome_cliente = cliente.getNome_fantaia();
						  }catch(Exception e) {
							  return "INDEFINIDO";
						  }
				
			}
			case descricao:{
				return dado.getDescricao();
			}
			case forma_pagamento:{
				GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
				if(dado.getId_condicao_pagamento() > 0) {
					CondicaoPagamento condicao  = gerenciar.getCondicaoPagamento(dado.getId_condicao_pagamento());

					if(condicao !=null)
						return condicao.getNome_condicao_pagamento();
				}
				
				
			}
			case status:{
				if(dado.getStatus_pagamento() == 0) {
					return "A - Compensar|Realizar|Concluir";
					
				}else if(dado.getStatus_pagamento() == 1) {
					return "Compensado|Realizado|Concluído";
				}
			}case data_pagamento:{
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
					data_menor = formato.parse(dado.getData_pagamento());
					return data_menor;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
			case valor:{
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
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
			FinanceiroPagamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroPagamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroPagamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroPagamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroPagamento> dadosIn) {
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
		public void onRemove(FinanceiroPagamento dado) {
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

		public FinanceiroPagamento onGet(int row) {
			return dados.get(row);
		}
	}
	
	
	
	
	
public void atualizarValorLancamento(Lancamento lancamento_pai) {
	

	GerenciarBancoFinanceiroPagamento gerenciar_pag = new GerenciarBancoFinanceiroPagamento();
	BigDecimal valor_total_pagamentos =  BigDecimal.ZERO;
	
	for (FinanceiroPagamento pagamento : gerenciar_pag.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

			
		valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());
			
	 }
	
	BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
	GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();

	BigDecimal valor_total_parcelas =  BigDecimal.ZERO;
	
	for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
			BigDecimal valor_parcela = parcela.getValor();
			if(lancamento_pai.getTipo_lancamento() == 0) {
				//é uma despesa
			if(valor_pagamentos_retroativos.compareTo(valor_parcela) >=0) {
				gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());
				
			}else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
				gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

			}
			}else if(lancamento_pai.getTipo_lancamento() == 1) {
				//é uma receita
				if(valor_pagamentos_retroativos.compareTo(valor_parcela) >=0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());
					
				}else if(valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
			}
			
			valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
			valor_total_parcelas = valor_total_parcelas.add(valor_parcela);
			
	 }
	new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(), lancamento_pai.getId_lancamento());
	
	
	if(valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
		//pagamento concluido
		//atualizar status do lancmento
		if(lancamento_pai.getTipo_lancamento() == 0) //é uma despesa
		new GerenciarBancoLancamento().atualizarStatusLancamento(1, lancamento_pai.getId_lancamento());
		else if(lancamento_pai.getTipo_lancamento() == 1)//é uma receita
			new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

	}else if(valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
		//pagamento incompleto
		if(lancamento_pai.getTipo_lancamento() == 0) //é uma despesa
			new GerenciarBancoLancamento().atualizarStatusLancamento(0, lancamento_pai.getId_lancamento());
			else if(lancamento_pai.getTipo_lancamento() == 1)//é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

	}
	
		
}




	
}
