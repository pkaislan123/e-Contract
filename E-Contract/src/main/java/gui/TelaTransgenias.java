

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

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.CadastroTransgenia;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoTransgenia;
import main.java.conexaoBanco.GerenciarBancoTransgenia;
import main.java.outros.JTextFieldPersonalizado;

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

public class TelaTransgenias extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaTransgenias isto;
	 private JTable tabela_transgenias;
	 private ArrayList<CadastroTransgenia> lista_transgenias = new ArrayList<>();
	 private TransgeniasTableModel modelo_transgenias = new TransgeniasTableModel();
	 private JDialog telaPai;
	 private JTextFieldPersonalizado entNome;
	 private TableRowSorter<TransgeniasTableModel> sorter;
	 
	 
	 
	public TelaTransgenias(int retorno_tela, Window janela_pai) {
		
		isto = this;
		setBounds(100, 100, 1018, 708);
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[grow][grow][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[128px]"));
		
		JLabel lblNewLabel = new JLabel("Transgênese ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,growx,aligny bottom");
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaTransgenias.class.getResource("/imagens/gmo64.png")));
		panel.add(lblNewLabel_2, "cell 1 0");
		
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
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 153, 153));
		panel_1.add(panel_4, "cell 2 1 2 1,grow");
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
		
		
		
		tabela_transgenias = new JTable(modelo_transgenias);
		//instancia o sorter
		sorter = new TableRowSorter<TransgeniasTableModel>(modelo_transgenias);

		//define o sorter na tablea
		tabela_transgenias.setRowSorter(sorter);
		tabela_transgenias.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_transgenias);
		panel_2.add(scrollPane);
		
		JPanel panel_3 = new JPanel();
		painelPrinciapl.add(panel_3, "cell 0 3,alignx right");
		panel_3.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Cadastrar");
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaTransgeniasCadastroTransgenia tela = new TelaTransgeniasCadastroTransgenia(0, null, isto);
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
			            "Deseja excluir o classificador selecionado?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoTransgenia().removertransgenia(getClassificadorSelecionado().getId_transgenia());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Transgenia Excluída");
							pesquisar();
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
			
					if(retorno_tela == 1) {
						
						isto.dispose();
					}
				}
			
		});
		panel_3.add(btnNewButton_3, "cell 1 0,alignx left,aligny top");
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.setBackground(new Color(255, 153, 0));
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				TelaTransgeniasCadastroTransgenia tela = new TelaTransgeniasCadastroTransgenia(1, getClassificadorSelecionado(), isto);
				tela.setVisible(true);
			}
		});
		panel_3.add(btnNewButton_2, "cell 2 0,alignx left,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void limpar(){
		sorter.setRowFilter(RowFilter.regexFilter(""));
	
	}
	
	public void pesquisar() {
		GerenciarBancoTransgenia gerenciar = new GerenciarBancoTransgenia();
		lista_transgenias.clear();
		modelo_transgenias.onRemoveAll();
		
		lista_transgenias = gerenciar.gettransgenia();
		for(CadastroTransgenia cc : lista_transgenias) {
			modelo_transgenias.onAdd(cc);
		}
	}
	
public void filtrar() {
		
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String nome = entNome.getText().toUpperCase();
	
		  
	    if(checkString(nome))
		    filters.add(RowFilter.regexFilter(nome, 1));

		sorter.setRowFilter(RowFilter.andFilter(filters));

}

public boolean checkString(String txt) {
	return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
}

	
	public class TransgeniasTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int descricao = 2;
		private final int teste = 3;
		private final int resultado = 4;
		private final int royalties = 5;



		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Nome", "Descrição", "Tipo do Teste", "Resultado do Teste", "Pagamento Royalties"};
		private final ArrayList<CadastroTransgenia> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public TransgeniasTableModel() {

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
			case nome:
				return String.class;
			case descricao:
				return String.class;
			case teste:
				return String.class;
			case resultado:
				return String.class;
			case royalties:
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
			 CadastroTransgenia dado = dados.get(rowIndex);
		
			
			switch (columnIndex) {
			case id:
				return dado.getId_transgenia();
			case nome:
				return dado.getNome();
			case descricao:
				return dado.getDescricao();
			case teste:{
				if(dado.getTeste() == 0) {
					return "DECLARADO EM NOTA";
				}else {
					return "TESTADO NO ARMAZÉM";

				}
			}
			case resultado:{
				if(dado.getResultado() == 0) {
					return "NEGATIVO";
				}else {
					return "POSITIVO";

				}
			}
			case royalties:{
				if(dado.getRoyalties() == 0) {
					return "NÃO";
				}else {
					return "SIM";

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
			CadastroTransgenia ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroTransgenia getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroTransgenia dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroTransgenia dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroTransgenia> dadosIn) {
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
		public void onRemove(CadastroTransgenia dado) {
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

		public CadastroTransgenia onGet(int row) {
			return dados.get(row);
		}
	}
	
	public CadastroTransgenia getClassificadorSelecionado() {
		int indiceDaLinha = tabela_transgenias.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_transgenias.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoTransgenia gerenciar_cont = new GerenciarBancoTransgenia();
		return gerenciar_cont.gettransgenia(id_selecionado);
		
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
