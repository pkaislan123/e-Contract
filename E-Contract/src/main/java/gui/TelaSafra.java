package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroSafra;
import main.java.conexaoBanco.GerenciarBancoSafras;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

public class TelaSafra extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private SafraTableModel modelo = new SafraTableModel();
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JTable tabela;

	public TelaSafra(Window janela_pai) {
		// setAlwaysOnTop(true);

		TelaSafra isto = this;

		setResizable(false);
		setTitle("E-Contract -Safras");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);

		JButton btnsafra = new JButton("+Safra");
		btnsafra.setBackground(new Color(0, 51, 0));
		btnsafra.setForeground(Color.WHITE);
		btnsafra.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnsafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroSafra tela = new TelaCadastroSafra(isto);
			}
		});
		painelPrincipal.setLayout(new MigLayout("", "[653px]", "[grow][23px]"));

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				((TelaEditarSafrasEvidencia) janela_pai).adicionarSafra(getSafraSelecionada());
				isto.dispose();

			}
		});
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(btnSelecionar, "flowx,cell 0 1,alignx right");
		painelPrincipal.add(btnsafra, "cell 0 1,alignx right,growy");

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 0,grow");
		// panel.setLayout(null);

		tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(tabela);

		panel.setLayout(new MigLayout("", "[633px]", "[grow]"));
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane, "cell 0 0,grow");
        pesquisar_safras();

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);

	}

	public void pesquisar_safras() {

		modelo.onRemoveAll();
		GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
		ArrayList<CadastroSafra> safras = gerenciar.getSafras();
		
		for(CadastroSafra saf : safras) {
			modelo.onAdd(saf);
		}
		
	}

	public static class SafraTableModel extends AbstractTableModel {

		private final int id = 0;
		private final int produto = 1;
		private final int transgenia = 2;
		private final int ano_plantio = 3;
		private final int ano_colheita = 4;

		private final String colunas[] = { "ID:", "Produto:", "Transgenia:", "Ano Plantio", "Ano Colheita" };
		private final ArrayList<CadastroSafra> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public SafraTableModel() {

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
			case ano_plantio:
				return String.class;
			case ano_colheita:
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
			CadastroSafra safra = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return safra.getId_safra();
			case produto:
				return safra.getProduto().getNome_produto();
			case transgenia:
				return safra.getProduto().getTransgenia();
			case ano_plantio:
				return safra.getAno_plantio();
			case ano_colheita:
				return safra.getAno_colheita();
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
			CadastroSafra nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroSafra getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroSafra nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroSafra nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroSafra> dadosIn) {
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
		public void onRemove(CadastroSafra nota) {
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

	public CadastroSafra getSafraSelecionada() {
		int linhas_selecionada = tabela.getSelectedRow();// pega o indice da linha na table

		return (CadastroSafra) modelo.getValue(linhas_selecionada);
	}
}
