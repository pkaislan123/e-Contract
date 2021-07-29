
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
import main.java.cadastros.CadastroFuncionarioDepartamentos;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDepartamentos;
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

public class TelaFuncionariosDepartamentos extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFuncionariosDepartamentos isto;
	private JTable tabela_departamentos;
	private ArrayList<CadastroFuncionarioDepartamentos> lista_departamentos = new ArrayList<>();
	private JDialog telaPai;
	private DepartamentoTableModel modelo_departamentos = new DepartamentoTableModel();

	public TelaFuncionariosDepartamentos(int flag_retorno, Window janela_pai) {

		isto = this;
		setBounds(100, 100, 1018, 708);
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[grow][grow][][]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[128px]"));

		JLabel lblNewLabel = new JLabel("Departamentos - Recursos Humanos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,growx,aligny bottom");

		JPanel panel_2 = new JPanel();
		painelPrinciapl.add(panel_2, "cell 0 2,growx");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BorderLayout(0, 0));

		tabela_departamentos = new JTable(modelo_departamentos);
		// instancia o sorter

		tabela_departamentos.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_departamentos);
		scrollPane.getViewport().setBackground(Color.white);
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

				TelaFuncionariosCadastroDepartamentos tela = new TelaFuncionariosCadastroDepartamentos(isto);
				tela.setVisible(true);
				
			}
		});

		JButton btnNewButton_4 = new JButton("Excluir");
		btnNewButton_4.setBackground(new Color(204, 0, 0));
		btnNewButton_4.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir o Departamento selecionado?", "Excluir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					boolean exclusao = new GerenciarBancoFuncionariosDepartamentos()
							.removerdepartamento(getDepartamentoSelecionado().getId());
					if (exclusao) {
						JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
					} else {
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
				panel_3.add(btnNewButton_3, "cell 1 0 2 1,alignx center,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");

		pesquisar();
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
		GerenciarBancoFuncionariosDepartamentos gerenciar = new GerenciarBancoFuncionariosDepartamentos();
		lista_departamentos.clear();
		modelo_departamentos.onRemoveAll();

		lista_departamentos = gerenciar.getDepartamentos();
		for (CadastroFuncionarioDepartamentos cc : lista_departamentos) {
			modelo_departamentos.onAdd(cc);
		}
	}

	

	public CadastroFuncionarioDepartamentos getDepartamentoSelecionado() {
		int indiceDaLinha = tabela_departamentos.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_departamentos.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoFuncionariosDepartamentos gerenciar_cont = new GerenciarBancoFuncionariosDepartamentos();
		return gerenciar_cont.getdepartamento(id_selecionado);

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

	public class DepartamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int descricao = 2;

		private final String colunas[] = { "ID", "Nome", "Descrição" };

		private final ArrayList<CadastroFuncionarioDepartamentos> dados = new ArrayList<>();// usamos como dados uma
																							// lista
		// genérica de

		public DepartamentoTableModel() {

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
			case nome:
				return String.class;
			case descricao:
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
			CadastroFuncionarioDepartamentos departamento = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id:
				return departamento.getId();
			case nome:
				return departamento.getNome();
			case descricao:
				return departamento.getDescricao();

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
			CadastroFuncionarioDepartamentos recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioDepartamentos getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioDepartamentos nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioDepartamentos nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioDepartamentos> dadosIn) {
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
		public void onRemove(CadastroFuncionarioDepartamentos nota) {
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
