package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.gui.TelaContratos.ContratoTableModel;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;




public class TelaTransportadores extends JDialog {
	private TransportadoresTableModel modelo_transportadores = new TransportadoresTableModel();
	private TableRowSorter<TransportadoresTableModel> sorter;
	private final JPanel painelPrincipal = new JPanel();

	private static ArrayList<CadastroCliente> transportadores = new ArrayList<>();
	private JDialog telaPai;

	
	private CadastroCliente clienteSelecionado;
	
	public void pesquisar() { 
		modelo_transportadores.onRemoveAll();
		transportadores.clear();
    GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
    

    for(CadastroCliente cli :  gerenciar.getTransportadores()) {
		modelo_transportadores.onAdd(cli);
		transportadores.add(cli);
    }
    
		
	}
	
	private TelaTransportadores isto ;
	private JTextField entNomeMotorista;
	private JTextField entCpfMotorista;
	private JTextField entPlaca;
	
	public TelaTransportadores(int flag_operacao, Window janela_pai) {
		//setAlwaysOnTop(true);

		//setModal(true);
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Transportadores");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 624);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		
		JButton btnarmazm = new JButton("+Transportador");
		btnarmazm.setBackground(new Color(0, 51, 0));
		btnarmazm.setForeground(Color.WHITE);
		btnarmazm.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnarmazm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				TelaCadastroTransportadores tela = new TelaCadastroTransportadores(0,  null, null, isto);
			}
		});
		painelPrincipal.setLayout(new MigLayout("", "[630px,grow][10px][89px][10px][113px]", "[grow][grow][28px]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 0 0 5 1,grow");
		panel_1.setLayout(new MigLayout("", "[][grow][][grow][][grow][][][]", "[][]"));
		
		JButton btnRefazerPesquisar = new JButton("Refazer Pesquisa");
		btnRefazerPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnRefazerPesquisar.setBackground(new Color(0, 0, 153));
		btnRefazerPesquisar.setForeground(Color.WHITE);
		btnRefazerPesquisar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(btnRefazerPesquisar, "cell 6 0 3 1,growx");
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblNewLabel, "cell 0 1,alignx trailing");
		
		entNomeMotorista = new JTextField();
		entNomeMotorista.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(entNomeMotorista, "cell 1 1,growx");
		entNomeMotorista.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblCpf, "cell 2 1,alignx trailing");
		
		entCpfMotorista = new JTextField();
		entCpfMotorista.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCpfMotorista.setColumns(10);
		panel_1.add(entCpfMotorista, "cell 3 1,growx");
		
		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblPlaca, "cell 4 1,alignx trailing");
		
		entPlaca = new JTextField();
		entPlaca.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entPlaca.setColumns(10);
		panel_1.add(entPlaca, "cell 5 1,growx");
		
		JButton btnNewButton = new JButton("Limpar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));
			}
		});
		btnNewButton.setBackground(new Color(255, 0, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(btnNewButton, "cell 6 1");
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltrar.setBackground(new Color(0, 51, 0));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(btnFiltrar, "cell 7 1 2 1,growx");
		painelPrincipal.add(btnarmazm, "cell 4 2,grow");
		
		
		
		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 1 5 1,grow");
		//panel.setLayout(null);
	
		

		JTable tabela = new JTable(modelo_transportadores);
		sorter = new TableRowSorter<TransportadoresTableModel>(modelo_transportadores);

		tabela.setBackground(new Color(255, 255, 255));
		tabela.setRowSorter(sorter);
		tabela.setRowHeight(30);

    
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        panel.setLayout(new BorderLayout(0, 0));
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(0, 0, 102));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowSel = tabela.getSelectedRow();
				int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model				

				TelaCadastroTransportadores telaEdicao = new TelaCadastroTransportadores(1, new GerenciarBancoClientes().getCliente(transportadores.get(indiceDaLinha).getId()), null, isto);

			}
		});
		painelPrincipal.add(btnEditar, "cell 2 2,growx,aligny top");
		
		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.setBackground(new Color(0, 0, 0));
		btnSelecionarTransportador.setForeground(Color.WHITE);
		btnSelecionarTransportador.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSel = tabela.getSelectedRow();
				int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model				

				if(telaPai instanceof TelaConfirmarCarregamento) {
				((TelaConfirmarCarregamento) telaPai).setTransportador(new GerenciarBancoClientes().getCliente(transportadores.get(indiceDaLinha).getId()));
				}else if(telaPai instanceof TelaConfirmarRecebimento) {
					((TelaConfirmarRecebimento) telaPai).setTransportador(new GerenciarBancoClientes().getCliente(transportadores.get(indiceDaLinha).getId()));

				}
				isto.dispose();
			}
		});
		painelPrincipal.add(btnSelecionarTransportador, "cell 0 2,alignx right,aligny top");
		
		
		if(flag_operacao != 2) {
			btnSelecionarTransportador.setVisible(false);
			btnSelecionarTransportador.setEnabled(false);
			
		} else {
			btnSelecionarTransportador.setVisible(true);
			btnSelecionarTransportador.setEnabled(true);
		}
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);

		//this.setVisible(true);
		
		
	}
	
	public void filtrar() {
/*
 * 	private final int id = 0;
		private final int rntrc = 1;
		private final int apelido = 2;
		private final int cpf_cnpj = 3;
		private final int nome = 4;
		private final int veiculos = 5;
 */
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String nome = entNomeMotorista.getText().toUpperCase();
		String cpf = entCpfMotorista.getText().toUpperCase();
		String placa = entPlaca.getText().toUpperCase();
	

		if (checkString(nome))
			filters.add(RowFilter.regexFilter(nome, 4));

		if (checkString(cpf))
			filters.add(RowFilter.regexFilter(cpf, 3));

		if (checkString(placa))
			filters.add(RowFilter.regexFilter(placa, 5));


		sorter.setRowFilter(RowFilter.andFilter(filters));
}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	public void setTelaPai(JDialog telaPai) {
		
		this.telaPai = telaPai;
		
	}
	
	public class TransportadoresTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int rntrc = 1;
		private final int apelido = 2;
		private final int cpf_cnpj = 3;
		private final int nome = 4;
		private final int veiculos = 5;
		
		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = {"ID", "RNTRC", "APELIDO", "CPF/CNPJ", "NOME", "VEICULOS" };
		private final ArrayList<CadastroCliente> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public TransportadoresTableModel() {

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
			case rntrc:
				return String.class;
			case apelido:
				return String.class;
			case cpf_cnpj:
				return String.class;
			case nome:
				return String.class;
			case veiculos:
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
			CadastroCliente cli = dados.get(rowIndex);


			switch (columnIndex) {
			case id:{
				return cli.getId();
			}
			case rntrc:{
				
				if(cli.getRntrc() != null) {
					return cli.getRntrc();
				}else {
					return "";
				}
			}
			case apelido:{
				return cli.getApelido().toUpperCase();
				
			}
			case cpf_cnpj:{
					if(cli.getTipo_pessoa() == 0) {
						if(cli.getCpf() != null) {
							return cli.getCpf();
						}else {
							return "";
						}
						
					}else {
						if(cli.getCnpj() != null) {
							return cli.getCnpj();
						}else {
							return "";
						}
					}
						
					
				}
			case nome:{
				 if(cli.getTipo_pessoa() == 0) {
					 if(cli.getNome() != null && cli.getSobrenome() != null) {
						 return cli.getNome().toUpperCase() + " " + cli.getSobrenome().toUpperCase();
					 }else {
						 return "";
					 }
				 }else {
					 if(cli.getRazao_social() != null) {
						 return cli.getRazao_social().toUpperCase();
					 }else {
						 return "";
					 }
				 }
					
				}
			
			case veiculos:{
				if(cli.getPlacas() != null) {
					return cli.getPlacas().toUpperCase();
				}else {
					return "";
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
			CadastroCliente contrato = dados.get(rowIndex);

		}

		 //Métodos abaixo são para manipulação de dados
		 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public CadastroCliente getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(CadastroCliente nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(CadastroCliente nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<CadastroCliente> dadosIn) {
	        dados.addAll(dadosIn);
	        fireTableDataChanged();
	    }
	 
	    /**
	     * remove um registro da lista, através do indice
	     * @param rowIndex
	     */
	    public void onRemove(int rowIndex) {
	        dados.remove(rowIndex);
	        fireTableRowsDeleted(rowIndex, rowIndex);
	    }
	 
	    /**
	     * remove um registro da lista, através do objeto
	     * @param empregado
	     */
	    public void onRemove(CadastroCliente nota) {
	        int indexBefore=indexOf(nota);//pega o indice antes de apagar
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
