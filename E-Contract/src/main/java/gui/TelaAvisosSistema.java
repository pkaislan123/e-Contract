package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import main.java.cadastros.CadastroAviso;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.MatteBorder;

public class TelaAvisosSistema extends JFrame {

	private JPanel contentPane;
	private JTable tabela_avisos;
	private AvisoTableModel modelo_aviso = new AvisoTableModel();
	private ArrayList<CadastroAviso> lista_avisos = new ArrayList<>();
	private TelaAvisosSistema isto;
	private TelaMain telaPrincipal;
	public TelaAvisosSistema(Window janela_pai) {
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		isto = this;
		telaPrincipal = (TelaMain) janela_pai;
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_8.setBackground(Color.WHITE);
		
		setContentPane(panel_8);
		setBounds(100, 100, 680, 328);


		panel_8.setLayout(new MigLayout("", "[][grow]", "[][][211.00,grow][]"));

		JLabel lblNewLabel_10 = new JLabel("Avisos do Sistema");
		lblNewLabel_10.setOpaque(true);
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBackground(new Color(0, 51, 0));
		lblNewLabel_10.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_8.add(lblNewLabel_10, "flowx,cell 0 0 2 2");

		tabela_avisos = new JTable(modelo_aviso);
		tabela_avisos.setFont(new Font("SansSerif", Font.BOLD, 14));

		tabela_avisos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabela_avisos.getColumnModel().getColumn(1).setPreferredWidth(250);
		tabela_avisos.getColumnModel().getColumn(2).setPreferredWidth(800);
		tabela_avisos.setRowHeight(30);

		JScrollPane scrollPane_1 = new JScrollPane(tabela_avisos);
		scrollPane_1.getViewport().setBackground(new Color(0,0,0,0));
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setBackground(Color.WHITE);
		panel_8.add(scrollPane_1, "cell 1 2,grow");

		JButton btnNewButton = new JButton("Excluir Aviso");
		btnNewButton.setBackground(new Color(255, 0, 51));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela_avisos.getSelectedRow();
				CadastroAviso aviso_selecionado = lista_avisos.get(indiceDaLinha);
				lista_avisos.remove(aviso_selecionado);
				modelo_aviso.onRemove(indiceDaLinha);
				
				if(lista_avisos.isEmpty())
					telaPrincipal.setarIconeAvisos("/imagens/icone_sem_avisos.png");

				
			}
		});

		JButton btnLimparAvisos = new JButton("Limpar");
		btnLimparAvisos.setBackground(new Color(153, 0, 51));
		btnLimparAvisos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparAvisos.setForeground(Color.WHITE);
		btnLimparAvisos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabela_avisos.removeAll();
				lista_avisos.clear();
				modelo_aviso.onRemoveAll();
				telaPrincipal.setarIconeAvisos("/imagens/icone_sem_avisos.png");


			}
		});
		panel_8.add(btnLimparAvisos, "flowx,cell 1 3,alignx right");
		panel_8.add(btnNewButton, "cell 1 3,alignx right");
		
		JButton btnNewButton_1 = new JButton("Minimizar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.setVisible(false);

			}
		});
		btnNewButton_1.setBackground(new Color(51, 0, 0));
		btnNewButton_1.setForeground(Color.WHITE);
		panel_8.add(btnNewButton_1, "cell 1 0,alignx right");
		
		
		
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - getWidth();
		int y = (int) rect.getMaxY() - getHeight() - taskBarHeight;

		this.setLocation(x, y);
		this.setResizable(false);
		
		setAlwaysOnTop(true);
		setUndecorated(true);
		
		
		
		
	}

	
	public static class AvisoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int tipo = 0;
		private final int setor = 1;

		private final int mensagem = 2;

		private final String colunas[] = { "Tipo", "Setor", "Mensagem:" };
		private final ArrayList<CadastroAviso> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public AvisoTableModel() {

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
			case tipo:
				return String.class;
			case setor:
				return String.class;
			case mensagem:
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
			CadastroAviso aviso = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case tipo:
				return aviso.getTipo();
			case setor:
				return aviso.getSetor();
			case mensagem:
				return aviso.getMensagem();
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroAviso nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroAviso getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroAviso nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroAviso nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroAviso> dadosIn) {
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
		public void onRemove(CadastroAviso nota) {
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

	public void incluir_aviso(CadastroAviso avisar) {

		boolean ja_tem_na_lista = false;
		for (CadastroAviso aviso : lista_avisos) {

			if (aviso.getMensagem().equals(avisar.getMensagem())) {
				ja_tem_na_lista = true;
				break;
			}

		}

		if (!ja_tem_na_lista) {
			// JOptionPane.showMessageDialog(isto, avisar.getMensagem());

			lista_avisos.add(avisar);
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					modelo_aviso.onAdd(avisar);
					
						telaPrincipal.setarIconeAvisos("/imagens/icone_com_avisos.png");
					

				}
			});

		}

	}
}
