package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroCotacao;
import main.java.conexaoBanco.GerenciarBancoCotacao;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

public class TelaCotacoes extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private CotacaoTableModel modelo = new CotacaoTableModel();
	private static ArrayList<CadastroCotacao> cotacoes = new ArrayList<>();
	private JTable tabela;

	public TelaCotacoes(Window janela_pai) {
		// setAlwaysOnTop(true);

		TelaCotacoes isto = this;

		setTitle("E-Contract - Cotações");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1021, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);

		JButton btnsafra = new JButton("+Cotação");
		btnsafra.setBackground(new Color(0, 51, 0));
		btnsafra.setForeground(Color.WHITE);
		btnsafra.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnsafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCotacao tela = new TelaCadastroCotacao(0, null, isto);
				tela.setVisible(true);
			}
		});
		painelPrincipal.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int index = tabela.getSelectedRow();
				CadastroCotacao cotacao = modelo.getValue(index);

				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir a Cotação?", "Excluir Cotação",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					GerenciarBancoCotacao gerenciar = new GerenciarBancoCotacao();

					boolean apagar = gerenciar.excluirCotacao(cotacao.getId_cotacao());
					if (apagar) {
						JOptionPane.showMessageDialog(isto, "Cotação Excluída!");
						isto.pesquisar_cotacoes();
					}
				}

			}
		});
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExcluir.setBackground(Color.RED);
		painelPrincipal.add(btnExcluir, "flowx,cell 0 1,alignx right");

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int index = tabela.getSelectedRow();
				CadastroCotacao cotacao = modelo.getValue(index);

				TelaCadastroCotacao tela = new TelaCadastroCotacao(1, cotacao, isto);
				tela.setVisible(true);

			}
		});
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar.setBackground(Color.BLUE);
		painelPrincipal.add(btnEditar, "cell 0 1,alignx right");

		painelPrincipal.add(btnsafra, "cell 0 1,alignx right,growy");

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 0,grow");
		// panel.setLayout(null);

		tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));
		tabela.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela);

		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane, "cell 0 0,grow");
		pesquisar_cotacoes();

		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar_cotacoes() {

		modelo.onRemoveAll();
		GerenciarBancoCotacao gerenciar = new GerenciarBancoCotacao();
		ArrayList<CadastroCotacao> cotacoes = gerenciar.getCotacoes();

		for (CadastroCotacao cot : cotacoes) {
			modelo.onAdd(cot);
		}

	}

	public static class CotacaoTableModel extends AbstractTableModel {

		private final int id = 0;
		private final int produto = 1;
		private final int transgenia = 2;
		private final int medida = 3;
		private final int quantidade = 4;
		private final int unidade = 5;
		private final int valor = 6;
		private final int data = 7;
		private final int hora = 8;
		private final int local = 9;
		private final int indicador = 10;

		private final String colunas[] = { "ID:", "Produto:", "Transgenia:", "Medida", "Quantidade", "Unidade", "Valor",
				"Data", "Hora", "Local", "Indicador" };
		private final ArrayList<CadastroCotacao> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public CotacaoTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na table
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case medida:
				return String.class;
			case quantidade:
				return String.class;
			case unidade:
				return String.class;

			case valor:
				return String.class;
			case data:
				return Date.class;
			case hora:
				return LocalTime.class;
			case local:
				return String.class;
			case indicador:
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

			// pega o dados corrente da linha
			CadastroCotacao cotacao = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return cotacao.getId_cotacao();
			case produto:
				return cotacao.getProduto().getNome_produto();
			case transgenia:
				return cotacao.getProduto().getTransgenia();
			case medida:
				return cotacao.getMedida();
			case quantidade:
				return cotacao.getQuantidade();
			case unidade:
				return cotacao.getUnidade();

			case valor:
				return cotacao.getValor();
			case data:
				return cotacao.getData();
			case hora: {
				try {
					return LocalDateTime.ofInstant(cotacao.getData().toInstant(), ZoneId.systemDefault()).toLocalTime();
				} catch (Exception e) {
					return "";
				}
			}
			case local:
				return cotacao.getLocalidade();
			case indicador:
				return cotacao.getIndicador();

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
			CadastroCotacao nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroCotacao getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroCotacao nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroCotacao nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroCotacao> dadosIn) {
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
		public void onRemove(CadastroCotacao nota) {
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
