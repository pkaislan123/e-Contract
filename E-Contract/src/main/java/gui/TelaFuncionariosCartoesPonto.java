
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import main.java.cadastros.CartaoPonto;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.outros.JTextFieldPersonalizado;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class TelaFuncionariosCartoesPonto extends JDialog {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFuncionariosCartoesPonto isto;
	 private JTable tabela_cartoes;
	 private ArrayList<CartaoPonto> lista_cartaos = new ArrayList<>();
	 private JDialog telaPai;
	 private CartaoTableModel modelo_cartaos = new CartaoTableModel();
	 private TableRowSorter<CartaoTableModel> sorter;
	 
	 
	 
	public TelaFuncionariosCartoesPonto(int flag_retorno, Window janela_pai) {
		
		isto = this;
		setBounds(100, 100, 1018, 708);
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[grow][grow][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[128px]"));
		
		JLabel lblNewLabel = new JLabel("Cartões de Ponto");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,growx,aligny bottom");
		
		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new MigLayout("", "[84px][326.00,grow][197.00][316.00,grow][grow]", "[][28px][grow][]"));
		
		JPanel panel_2 = new JPanel();
		painelPrinciapl.add(panel_2, "cell 0 2,growx");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		
		
		tabela_cartoes = new JTable(modelo_cartaos);
		//instancia o sorter
		sorter = new TableRowSorter<CartaoTableModel>(modelo_cartaos);

		//define o sorter na tablea
		tabela_cartoes.setRowSorter(sorter);
		tabela_cartoes.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_cartoes);
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
				TelaFuncionariosCadastroCartaoPonto cadastro = new TelaFuncionariosCadastroCartaoPonto( isto);
				cadastro.setVisible(true);
				
			}
		});
		
		JButton btnNewButton_4 = new JButton("Excluir");
		btnNewButton_4.setBackground(new Color(204, 0, 0));
		btnNewButton_4.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir o cartao selecionado?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoCartaoPonto().removercartao(getcartaoSelecionado().getId_cartao());
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
				
				if(flag_retorno == 1) {
					
					//verifica se o cartao selecionado ja nao esta associado a outro funcionario
					
					CartaoPonto cartao_selecionado = getcartaoSelecionado();
					GerenciarBancoCartaoPonto gerenciar = new GerenciarBancoCartaoPonto();
					boolean associado = gerenciar.verificarAssosiacaoPorCartao(cartao_selecionado.getId_cartao());
					if(!associado) {
					
					((TelaAplicarAssociacaoFuncionarioCartao) janela_pai).setCartaoPonto(cartao_selecionado);
					isto.dispose();
					}else {
						JOptionPane.showMessageDialog(isto, "O Cartão Selecionado já esta associado a outro colaborador");
					}
				}
				
				
			}
		});
		panel_3.add(btnNewButton_3, "cell 1 0 2 1,alignx center,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void pesquisar() {
		GerenciarBancoCartaoPonto gerenciar = new GerenciarBancoCartaoPonto();
		lista_cartaos.clear();
		modelo_cartaos.onRemoveAll();
		
		lista_cartaos = gerenciar.getcartaos();
		for(CartaoPonto cc : lista_cartaos) {
			modelo_cartaos.onAdd(cc);
		}
	}

public boolean checkString(String txt) {
	return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
}

	
	
	public CartaoPonto getcartaoSelecionado() {
		int indiceDaLinha = tabela_cartoes.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_cartoes.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoCartaoPonto gerenciar_cont = new GerenciarBancoCartaoPonto();
		return gerenciar_cont.getcartao(id_selecionado);
		
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

	
	

	public  class CartaoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int uid = 1;
		
		
	
		private final String colunas[] = { "ID", "UID"};
		 Locale ptBr = new Locale("pt", "BR");

		private final ArrayList<CartaoPonto> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public CartaoTableModel() {

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
				return String.class;
			case uid:
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
			// retorna o valor conforme a coluna e linha
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CartaoPonto cartao = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id:
				return cartao.getId_cartao();
			case uid:
				return cartao.getUid();
		
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
			CartaoPonto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CartaoPonto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CartaoPonto nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CartaoPonto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CartaoPonto> dadosIn) {
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
		public void onRemove(CartaoPonto nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}
}
