package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class TelaFinanceiroGrupoConta extends JDialog {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroGrupoConta isto;
	 private JTable tabela_grupo_contas;
	 private ArrayList<FinanceiroGrupoContas> lista_grupo_contas = new ArrayList<>();
	 private GrupoContasTableModel modelo_grupo_contas = new GrupoContasTableModel();
	 private JDialog telaPai;
	
	public TelaFinanceiroGrupoConta(int flag_modo_operacao, int flag_retorno, Window janela_pai) {
		
		isto = this;
		setBounds(100, 100, 1018, 708);
		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[grow][grow][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Grupos de Contas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(lblNewLabel, "cell 0 0 1 5,growx,aligny center");
		
		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		
		JButton btnNewButton = new JButton("pesquisar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		panel_1.setLayout(new MigLayout("", "[84px]", "[28px]"));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));
		panel_1.add(btnNewButton, "cell 0 0,alignx right,aligny top");
		
		JPanel panel_2 = new JPanel();
		painelPrinciapl.add(panel_2, "cell 0 2,growx");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		
		
		tabela_grupo_contas = new JTable(modelo_grupo_contas);
		
		tabela_grupo_contas.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_grupo_contas);
		panel_2.add(scrollPane);
		
		JPanel panel_3 = new JPanel();
		painelPrinciapl.add(panel_3, "cell 0 3,alignx right");
		panel_3.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Cadastrar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroGrupoConta cadastro = new TelaFinanceiroCadastroGrupoConta(0, null, isto);
				cadastro.setVisible(true);
				
			}
		});
		
		JButton btnNewButton_4 = new JButton("Excluir");
	
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir o Grupo de Contas?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoFinanceiroGrupoContas().removerFinanceiroGrupoContas(getGrupoContasSelecionado().getId_grupo_contas());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

						 pesquisar();
			        }
				
			}
		});
		panel_3.setLayout(new MigLayout("", "[64px][87px][60px][83px]", "[28px]"));
		panel_3.add(btnNewButton_4, "cell 0 0,alignx left,aligny top");
		
		JButton btnNewButton_3 = new JButton("Selecionar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			FinanceiroGrupoContas conta_selecionada = getGrupoContasSelecionado();
				 if(flag_modo_operacao == 0) {
					 if(flag_retorno == 1) {
					  ((TelaFinanceiroCadastroConta) telaPai).setGrupoContas(conta_selecionada);
					  isto.dispose();
					  }
				 }
			}
		});
		panel_3.add(btnNewButton_3, "cell 1 0,alignx left,aligny top");
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroGrupoConta tela = new TelaFinanceiroCadastroGrupoConta(1, getGrupoContasSelecionado(), isto);
				tela.setVisible(true);
			}
		});
		panel_3.add(btnNewButton_2, "cell 2 0,alignx left,aligny top");
		panel_3.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	public void pesquisar() {
		GerenciarBancoFinanceiroGrupoContas gerenciar = new GerenciarBancoFinanceiroGrupoContas();
		lista_grupo_contas.clear();
		modelo_grupo_contas.onRemoveAll();
		
		lista_grupo_contas = gerenciar.getFinanceiroGrupoContass();
		for(FinanceiroGrupoContas cc : lista_grupo_contas) {
			modelo_grupo_contas.onAdd(cc);
		}
	}
	
	
	public class GrupoContasTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
	
	


		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Nome"};
		private final ArrayList<FinanceiroGrupoContas> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public GrupoContasTableModel() {

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
			 FinanceiroGrupoContas dado = dados.get(rowIndex);
			
			switch (columnIndex) {
			case id:
				return dado.getId_grupo_contas();
			case nome: {
				return dado.getNome();

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
			FinanceiroGrupoContas ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroGrupoContas getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroGrupoContas dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroGrupoContas dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroGrupoContas> dadosIn) {
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
		public void onRemove(FinanceiroGrupoContas dado) {
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

		public FinanceiroGrupoContas onGet(int row) {
			return dados.get(row);
		}
	}
	
	public FinanceiroGrupoContas getGrupoContasSelecionado() {
		int indiceDaLinha = tabela_grupo_contas.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_grupo_contas.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoFinanceiroGrupoContas gerenciar_cont = new GerenciarBancoFinanceiroGrupoContas();
		return gerenciar_cont.getFinanceiroGrupoContas(id_selecionado);
		
	}
	
	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
	}

}
