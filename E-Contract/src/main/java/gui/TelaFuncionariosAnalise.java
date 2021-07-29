package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFuncionarioDepartamentos;
import main.java.cadastros.CadastroFuncionarioSalario;
import main.java.classesExtras.ComboBoxPersonalizadoDepartamento;
import main.java.classesExtras.ComboBoxRenderPersonalizadoDepartamento;
import main.java.conexaoBanco.GerenciarBancoFuncionarioSalarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDepartamentos;
import main.java.gui.TelaContratos.ContratoTableModel;
import main.java.outros.GetData;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaFuncionariosAnalise extends JFrame {

	private SalarioTableModel modelSalarios = new SalarioTableModel();
	private JTabbedPane painelPrincipal  = new JTabbedPane() ;
	private JPanel painelSalarios = new JPanel();
	private TelaFuncionariosAnalise isto ;
	Locale ptBr = new Locale("pt", "BR");
	private TableRowSorter<SalarioTableModel> sorter;
	private JTable tabela_salarios;
	private JTextField entColaborador;
	private JTextField entAno;
	private JLabel lblValorTotalHorasExtras,lblValorTotalBruto,lblValorTotalBase;
	private JComboBox cBDepartamento;
	
	private JComboBox cbMes;
	
	public TelaFuncionariosAnalise(Window janela_pai) {

		 isto = this;
		
		setResizable(true);
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Análise de Recursos Humanos");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width, dim.height - taskBarHeight);
		
	

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		
		painelSalarios.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Sálario",painelSalarios);
		painelSalarios.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JPanel painelCamposPesquisa = new JPanel();
		painelCamposPesquisa.setBackground(Color.WHITE);
		painelSalarios.add(painelCamposPesquisa, "cell 0 0,grow");
		painelCamposPesquisa.setLayout(new MigLayout("", "[][][][][][grow][][][][][][][][][][][][][][][][][][][][][][][]", "[]"));
		
		JLabel lblColaborador = new JLabel("Colaborador:");
		lblColaborador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCamposPesquisa.add(lblColaborador, "cell 0 0,alignx trailing");
		
		entColaborador = new JTextField();
		entColaborador.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelCamposPesquisa.add(entColaborador, "cell 1 0,growx");
		entColaborador.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Departamento:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCamposPesquisa.add(lblNewLabel, "cell 2 0,alignx trailing");
		
		 cBDepartamento = new JComboBox();			
		painelCamposPesquisa.add(cBDepartamento, "cell 3 0,growx");
		
		JLabel lblPerodo = new JLabel("Mês:");
		lblPerodo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCamposPesquisa.add(lblPerodo, "cell 4 0,alignx trailing");
		
		 cbMes = new JComboBox();
		 cbMes.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelCamposPesquisa.add(cbMes, "cell 5 0,growx");
		cbMes.addItem("TODOS");
		cbMes.addItem("JANEIRO");
		cbMes.addItem("FEVEREIRO");
		cbMes.addItem("MARÇO");
		cbMes.addItem("ABRIL");
		cbMes.addItem("MAIO");
		cbMes.addItem("JUNHO");
		cbMes.addItem("JULHO");
		cbMes.addItem("AGOSTO");
		cbMes.addItem("SETEMBRO");
		cbMes.addItem("OUTUBRO");
		cbMes.addItem("NOVEMBRO");
		cbMes.addItem("DEZEMBRO");
		
		JLabel lblAt = new JLabel("Ano:");
		lblAt.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCamposPesquisa.add(lblAt, "cell 6 0,alignx trailing");
		
		entAno = new JTextField();
		entAno.setFont(new Font("SansSerif", Font.BOLD, 14));
		entAno.setColumns(10);
		entAno.setText(Integer.toString(new GetData().getAnoAtual())  );
		painelCamposPesquisa.add(entAno, "cell 7 0,growx");
		
		JButton btnNewButton_1_1 = new JButton("limpar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setBackground(new Color(153, 0, 0));
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCamposPesquisa.add(btnNewButton_1_1, "cell 26 0");
		
		JButton btnFiltrar = new JButton("filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setBackground(new Color(0, 0, 153));
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCamposPesquisa.add(btnFiltrar, "cell 27 0");
		
		JButton btnNewButton = new JButton("pesquisar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_salarios();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCamposPesquisa.add(btnNewButton, "cell 28 0");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelSalarios.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		sorter = new TableRowSorter<SalarioTableModel>(modelSalarios);

		 tabela_salarios  = new JTable(modelSalarios);
		tabela_salarios.setRowHeight(30);
		tabela_salarios.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(tabela_salarios);
		scrollPane.getViewport().setBackground(Color.white);
		panel.add(scrollPane, "cell 0 0,grow");
		
		JPanel painelCalculos = new JPanel();
		painelCalculos.setBackground(Color.WHITE);
		panel.add(painelCalculos, "cell 0 1,grow");
		painelCalculos.setLayout(new MigLayout("", "[][]", "[][][][]"));
		
		JLabel lblValorTotalBaset = new JLabel("Valor Total Base:");
		lblValorTotalBaset.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCalculos.add(lblValorTotalBaset, "cell 0 0,alignx right");
		
		 lblValorTotalBase = new JLabel("R$ 0.000,00");
		lblValorTotalBase.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblValorTotalBase, "cell 1 0");
		
		JLabel lblValorTotalHorast = new JLabel("Valor Total Horas Extras:");
		lblValorTotalHorast.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCalculos.add(lblValorTotalHorast, "cell 0 1,alignx right");
		
		
		
		 lblValorTotalHorasExtras = new JLabel("R$ 0.000,00");
		lblValorTotalHorasExtras.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblValorTotalHorasExtras, "cell 1 1");
		
		JLabel lblValorTotalBrutot = new JLabel("Valor Total Bruto:");
		lblValorTotalBrutot.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelCalculos.add(lblValorTotalBrutot, "cell 0 2,alignx right");
		
		 lblValorTotalBruto = new JLabel("R$ 0.000,00");
		lblValorTotalBruto.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblValorTotalBruto, "cell 1 2");
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		pesquisar_salarios();
		pesquisarDepartamentos();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);
		
		
	}
	
	public void filtrar() {

		
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
		

		if (cBDepartamento.getSelectedItem().toString() != null) {
			String s_dep = "";
			if (checkString(cBDepartamento.getSelectedItem().toString())) {
				s_dep = cBDepartamento.getSelectedItem().toString();
				if (!(s_dep.equalsIgnoreCase("TODOS"))) {
					filters.add(RowFilter.regexFilter(s_dep, 2));
				}
			}
		}
		
		
		
		

		if (entColaborador.getText() != null) {
			String s_colaborador = "";
			if (checkString(entColaborador.getText())) {
				s_colaborador = entColaborador.getText().toUpperCase();
				if (!(s_colaborador.equalsIgnoreCase("TODOS")))
					filters.add(RowFilter.regexFilter(s_colaborador, 3));
			}
		}

		
	
		String mes = getMes(cbMes.getSelectedIndex());
		if (!(mes.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(mes, 4));
	
		
		if (entAno.getText() != null) {
			String s_ano = "";
			if (checkString(entAno.getText())) {
				s_ano = entAno.getText().toUpperCase();
				if (!(s_ano.equalsIgnoreCase("TODOS")))
					filters.add(RowFilter.regexFilter(s_ano, 5));
			}
		}
		
		
		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();


	}
	
	
	
	public String getMes(int mes) {
		
		if(mes == 0) {
			return "TODOS";
		}
		else if (mes == 1) {
			return "JANEIRO";
		} else if (mes == 2) {
			return "FEVEREIRO";
		} else if (mes == 3) {
			return "MARÇO";
		} else if (mes == 4) {
			return "ABRIL";
		} else if (mes == 5) {
			return "MAIO";
		} else if (mes == 6) {
			return "JUNHO";
		} else if (mes == 7) {
			return "JULHO";
		} else if (mes == 8) {
			return "AGOSTO";
		} else if (mes == 9) {
			return "SETEMBRO";
		} else if (mes == 10) {
			return "OUTUBRO";
		} else if (mes == 11) {
			return "NOVEMBRO";
		} else {
			return "DEZEMBRO";
		}
	}

	
	public void pesquisarDepartamentos() {
		GerenciarBancoFuncionariosDepartamentos gerenciar = new GerenciarBancoFuncionariosDepartamentos();
		ArrayList<CadastroFuncionarioDepartamentos> deps = gerenciar.getDepartamentos();
		
		cBDepartamento.addItem("TODOS");
		

		for (CadastroFuncionarioDepartamentos dep : deps) {
			cBDepartamento.addItem(dep.getNome());
		}

	}
	
	public void limpar() {
		sorter.setRowFilter(RowFilter.regexFilter(""));

		calcular();

	}
	
	public void calcular() {
		
		
		double valor_total_base = 0, valor_total_bruto = 0, valor_total_horas_extras = 0;
		
		
		for (int row = 0; row < tabela_salarios.getRowCount(); row++) {

			int index = tabela_salarios.convertRowIndexToModel(row);
			CadastroFuncionarioSalario salario = modelSalarios.getValue(index);
			
			valor_total_base += salario.getSalario_base();
			valor_total_horas_extras += salario.getTotal_hora_extras();
			
			double bruto = salario.getSalario_base() + salario.getTotal_hora_extras() + salario.getTotal_acrescimos() ;
			
			valor_total_bruto += bruto;
			
			
		}
		
		lblValorTotalHorasExtras.setText(formatarValor(valor_total_horas_extras));
		lblValorTotalBruto.setText(formatarValor(valor_total_bruto));
		lblValorTotalBase.setText(formatarValor(valor_total_base));


	}

	public void pesquisar_salarios() {
		GerenciarBancoFuncionarioSalarios gerenciar = new GerenciarBancoFuncionarioSalarios();

		modelSalarios.onRemoveAll();

		ArrayList<CadastroFuncionarioSalario> lista_salarios = gerenciar.getsalarios();
		for (CadastroFuncionarioSalario sal : lista_salarios) {
			modelSalarios.onAdd(sal);
		}
		
		calcular();

	}

	
	public class SalarioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_salario = 0;
		private final int id_ct_trabalho = 1;
		private final int departamento = 2;
		private final int nome_colaborador = 3;
		private final int mes = 4;
		private final int ano = 5;
		private final int salario_base = 6;
		private final int total_descontos =7;
		private final int total_acrescimos = 8;
		private final int total_horas_extras = 9;
		private final int bruto = 10;
		private final int liquido = 11;

		private final String colunas[] = { "ID", "CT de Trabalho","Departamento","Colaborador", "Mês", "Ano", "Sálario Base", "Total Descontos",
				"Total Acréscimos", "Total Horas Extras", "Bruto", "Líquido" };

		private final ArrayList<CadastroFuncionarioSalario> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public SalarioTableModel() {

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

			case id_salario:
				return Integer.class;
			case id_ct_trabalho:
				return String.class;
			case departamento:
				return String.class;
			case nome_colaborador:
				return String.class;
			case mes:
				return String.class;
			case ano:
				return String.class;
			case salario_base:
				return String.class;
			case total_descontos:
				return String.class;
			case total_acrescimos:
				return String.class;
			case total_horas_extras:
				return String.class;
			case bruto:
				return String.class;
			case liquido:
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
			CadastroFuncionarioSalario dado = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id_salario:
				return dado.getId_salario();
			case id_ct_trabalho:
				return dado.getId_ct_trabalho();
			case departamento:
				return dado.getNome_departamento().toUpperCase();
			case nome_colaborador:
				return dado.getNome_funcionario().toUpperCase();
			case mes: {
				int mes = dado.getMes() + 1;
				if (mes == 1) {
					return "JANEIRO";
				} else if (mes == 2) {
					return "FEVEREIRO";
				} else if (mes == 3) {
					return "MARÇO";
				} else if (mes == 4) {
					return "ABRIL";
				} else if (mes == 5) {
					return "MAIO";
				} else if (mes == 6) {
					return "JUNHO";
				} else if (mes == 7) {
					return "JULHO";
				} else if (mes == 8) {
					return "AGOSTO";
				} else if (mes == 9) {
					return "SETEMBRO";
				} else if (mes == 10) {
					return "OUTUBRO";
				} else if (mes == 11) {
					return "NOVEMBRO";
				} else if (mes == 12) {
					return "DEZEMBRO";
				}
			}
			case ano: {
				return dado.getAno();
			}
			case salario_base: {
				return formatarValor(dado.getSalario_base());
			}
			case total_descontos: {
				return formatarValor(dado.getTotal_descontos());

			}
			case total_acrescimos: {
				return formatarValor(dado.getTotal_acrescimos());

			}
			case total_horas_extras: {
				return formatarValor(dado.getTotal_hora_extras());

			}
			case bruto: {
				return formatarValor(dado.getSalario_base() + dado.getTotal_hora_extras() + dado.getTotal_acrescimos());

			}
			case liquido: {
				return formatarValor(dado.getSalario_base() + dado.getTotal_hora_extras() + dado.getTotal_acrescimos() - dado.getTotal_descontos());

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
			CadastroFuncionarioSalario recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioSalario getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioSalario nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioSalario nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioSalario> dadosIn) {
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
		public void onRemove(CadastroFuncionarioSalario nota) {
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
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public String formatarValor(double valor) {
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}
	 
}
