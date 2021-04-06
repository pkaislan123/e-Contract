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
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.Lancamento;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.conexaoBanco.GerenciarBancoLancamento;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class TelaFinanceiroLancamento extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroLancamento isto;
	 private JTable tabela_lancamentos;
	 private ArrayList<Lancamento> lista_lancamentos = new ArrayList<>();
	 private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
	 private JDialog telaPai;
	 private JTextFieldPersonalizado entNome;
	 private TableRowSorter<LancamentoTableModel> sorter;
	 private JTextFieldPersonalizado entGrupoContas;
	 private JComboBox cbTipoConta;
	 
	 
	 
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
		isto = this;
		
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[128px]"));
		
		JLabel lblNewLabel = new JLabel("Lançamentos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0 2 1,alignx right,aligny bottom");
		
		JLabel lblNewLabel_2 = new JLabel("");
		panel.add(lblNewLabel_2, "cell 5 0,alignx left,aligny top");
		
		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new MigLayout("", "[84px][326.00,grow][197.00][316.00,grow][grow]", "[][28px][grow][]"));
		
		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		entNome = new JTextFieldPersonalizado();
		panel_1.add(entNome, "cell 1 1,growx");
		entNome.setColumns(10);
		entNome.setForeground(Color.black);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Grupo de Contas:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblNewLabel_1_1_1, "cell 2 1,alignx trailing");
		
		 entGrupoContas = new JTextFieldPersonalizado();
		entGrupoContas.setForeground(Color.black);
		panel_1.add(entGrupoContas, "cell 3 1,growx");
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo de Conta:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblNewLabel_1_1, "cell 0 2,alignx trailing");
		
		 cbTipoConta = new JComboBox();
		cbTipoConta.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(cbTipoConta, "cell 1 2,growx");
		cbTipoConta.addItem("DESPESAS");
		cbTipoConta.addItem("RECEITAS");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 153, 153));
		panel_1.add(panel_4, "cell 3 2,grow");
		panel_4.setLayout(new MigLayout("", "[][][][][][][][][][][]", "[]"));
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		panel_4.add(btnLimpar, "cell 1 0,growy");
		btnLimpar.setBackground(new Color(0, 0, 102));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnFiltar = new JButton("Filtrar");
		btnFiltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltar.setBackground(new Color(0, 0, 102));
		btnFiltar.setForeground(Color.WHITE);
		panel_4.add(btnFiltar, "cell 2 0");
		
		JButton btnNewButton = new JButton("pesquisar");
		panel_4.add(btnNewButton, "cell 10 0,growy");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));
		
		JPanel panel_2 = new JPanel();
		painelPrinciapl.add(panel_2, "cell 0 2,growx");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		
		
		tabela_lancamentos = new JTable(modelo_lancamentos);
		//instancia o sorter
		sorter = new TableRowSorter<LancamentoTableModel>(modelo_lancamentos);

		//define o sorter na tablea
		tabela_lancamentos.setRowSorter(sorter);
		tabela_lancamentos.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_lancamentos);
		panel_2.add(scrollPane);
		
		JPanel panel_3 = new JPanel();
		painelPrinciapl.add(panel_3, "cell 0 3,alignx right");
		panel_3.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Cadastrar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(-1, null, isto);
				tela.setVisible(true);
				
			}
		});
		
		JButton btnNewButton_4 = new JButton("Excluir");
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
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_3.add(btnNewButton_3, "cell 1 0,alignx left,aligny top");
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(1, getLancamentoSelecionado(), isto);
				tela.setVisible(true);
			}
		});
		panel_3.add(btnNewButton_2, "cell 2 0,alignx left,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		pesquisar();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void limpar(){
		sorter.setRowFilter(RowFilter.regexFilter(""));
	
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

		String nome = entNome.getText().toUpperCase();
		String grupo_contas = entGrupoContas.getText().toUpperCase();
		String s_tipo_conta = "";
		if(cbTipoConta.getSelectedIndex() == 0) {
			s_tipo_conta = "DESPESAS";
		}else if(cbTipoConta.getSelectedIndex() == 1) {
			s_tipo_conta = "RECEITAS";
		}
		
		if (checkString(nome))
			filters.add(RowFilter.regexFilter(nome, 1));

		if (checkString(s_tipo_conta))
			filters.add(RowFilter.regexFilter(s_tipo_conta, 2));
		
		if (checkString(grupo_contas))
			filters.add(RowFilter.regexFilter(grupo_contas, 3));

		

		sorter.setRowFilter(RowFilter.andFilter(filters));

}

public boolean checkString(String txt) {
	return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
}

	
	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int conta = 1;
		private final int tipo_lancamento = 2;
		
	


		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Conta", "Tipo", };
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
			case conta:
				return String.class;
			case tipo_lancamento:
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
			case conta:{
				if(financeiro_conta != null) {
					return financeiro_conta.getNome();
				}
			}
			case tipo_lancamento:{
				if(financeiro_conta != null) {
					if(financeiro_conta.getTipo_conta() == 0) {
						return "DESPESAS";
					}else if(financeiro_conta.getTipo_conta() == 1) {
						return "RECEITAS";
					}
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
	
}
