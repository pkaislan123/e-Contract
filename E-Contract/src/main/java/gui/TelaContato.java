package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ClienteContato;
import main.java.cadastros.Contato;
import main.java.cadastros.ClienteContato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaContato extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTable table_ClienteContato;
	private ClienteContatoTableModel modelo_ClienteContato = new ClienteContatoTableModel();
	private ClienteContato ClienteContatoSelecionado;
	private Window telaPai;
	private static ArrayList<ClienteContato> ClienteContatos = new ArrayList<>();

	private TableRowSorter<ClienteContatoTableModel> sorter;
	private JTextField entNomeCliente;
	private JLabel lblNewLabel;
	private JLabel lblCpfcnpj;
	private JLabel lblBanco;
	private JTextField entNomeContato;
	private JTextField entCpfCliente;
	private JButton btnRefazerPesquisa;
	private JButton btnLimpar;
	private JButton btnFiltrar;
	private TelaContato isto;
	private JPopupMenu jPopupMenuContasBancarias;
	private JButton btnEditar;
	private JLabel lblIeCliente;
	private JTextField entIeCliente;
	private JButton btnSelecionar;

	public  void pesquisar_contatos() {
		modelo_ClienteContato.onRemoveAll();
		GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
		ClienteContatos = listaClientes.getContatos();

		for (ClienteContato cc : ClienteContatos) {
			modelo_ClienteContato.onAdd(cc);

		}


	}

	private Window janela_pai_global;
	public TelaContato(Window janela_pai) {
		// setModal(true);

		 isto = this;
		 janela_pai_global = janela_pai;
		setResizable(false);
		setTitle("E-Contract - Contatos");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 594);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		sorter = new TableRowSorter<ClienteContatoTableModel>(modelo_ClienteContato);
		painelPrincipal.setLayout(
				new MigLayout("", "[][][50px][10px][278px][49px][71px][14px][103px][12px][89px][][10px][155px,grow]", "[33px][33px][28px][grow][]"));

		table_ClienteContato = new JTable(modelo_ClienteContato);
		table_ClienteContato.setRowSorter(sorter);

		table_ClienteContato.setBackground(new Color(255, 255, 255));

		table_ClienteContato.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_ClienteContato.getColumnModel().getColumn(3).setPreferredWidth(120);
		
		lblIeCliente = new JLabel("I.E Cliente:");
		lblIeCliente.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		painelPrincipal.add(lblIeCliente, "cell 11 0");
		
		entIeCliente = new JTextField();
		entIeCliente.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			filtrar();
			}
		});
		entIeCliente.setFont(new Font("SansSerif", Font.BOLD, 16));
		entIeCliente.setColumns(10);
		painelPrincipal.add(entIeCliente, "cell 13 0,growx");

		table_ClienteContato.setRowHeight(30);
		JScrollPane scrollPaneCB = new JScrollPane(table_ClienteContato);
		painelPrincipal.add(scrollPaneCB, "cell 0 3 14 1,grow");
		scrollPaneCB.setAutoscrolls(true);
		scrollPaneCB.setBackground(new Color(255, 255, 255));
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSel = table_ClienteContato.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = table_ClienteContato.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
																							// model

				ClienteContatoSelecionado = ClienteContatos.get(indiceDaLinha);

				TelaEditarContato editar = new TelaEditarContato(ClienteContatoSelecionado.getContato(), isto);
				editar.setVisible(true);
				
			}
		});
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar.setBackground(new Color(102, 51, 0));
		painelPrincipal.add(btnEditar, "cell 8 4,growx");

		entNomeCliente = new JTextField();
		entNomeCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entNomeCliente.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNomeCliente.setColumns(10);
		painelPrincipal.add(entNomeCliente, "cell 4 0,grow");

		lblNewLabel = new JLabel("Nome Cliente:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel, "cell 2 0 2 1,alignx right,aligny center");

		lblCpfcnpj = new JLabel("CPF/CNPJ Cliente:");
		lblCpfcnpj.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		painelPrincipal.add(lblCpfcnpj, "cell 5 0 3 1,alignx right,aligny center");

		lblBanco = new JLabel("Nome Contato:");
		lblBanco.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		painelPrincipal.add(lblBanco, "cell 2 1 2 1,alignx right,aligny center");

		entNomeContato = new JTextField();
		entNomeContato.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entNomeContato.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNomeContato.setColumns(10);
		painelPrincipal.add(entNomeContato, "cell 4 1,grow");

		entCpfCliente = new JTextField();
		entCpfCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entCpfCliente.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCpfCliente.setColumns(10);
		painelPrincipal.add(entCpfCliente, "cell 8 0 3 1,grow");

		btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 153));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				pesquisar_contatos();
			}
		});
		painelPrincipal.add(btnRefazerPesquisa, "cell 6 2 3 1,alignx right,growy");

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(Color.ORANGE);
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));

			}
		});
		painelPrincipal.add(btnLimpar, "cell 10 2,growx,aligny center");

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 51, 0));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		painelPrincipal.add(btnFiltrar, "cell 13 2,alignx left,aligny top");
		
		btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				processarSelecao(janela_pai);
				
				
			}
		});
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(btnSelecionar, "cell 11 4");

		pesquisar_contatos();
		setMenuContasBancarias();

		this.setLocationRelativeTo(janela_pai);

	}
	
	
	public void processarSelecao(Window janela_pai) {
		int rowSel = table_ClienteContato.getSelectedRow();// pega o indice da linha na tabela
		int indiceDaLinha = table_ClienteContato.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
				
		ClienteContato contato = modelo_ClienteContato.getValue(indiceDaLinha);

		if(janela_pai instanceof TelaEnviarMsgMail) {
		((TelaEnviarMsgMail) janela_pai).setEmail(contato.getContato().getE_mail());
		isto.dispose();
		}else if(janela_pai instanceof TelaEnviarMsgEmailDocsGeral) {
			((TelaEnviarMsgEmailDocsGeral) janela_pai).setEmail(contato.getContato().getE_mail());
			isto.dispose();	
		}else if(janela_pai instanceof TelaEnviarAoContador) {
			((TelaEnviarAoContador) janela_pai).setEmail(contato.getContato().getE_mail());
			isto.dispose();	
		}else if(janela_pai instanceof TelaEnviarMsgWhatsappDocs) {
			((TelaEnviarMsgWhatsappDocs) janela_pai).setNumero(contato.getContato().getCelular());
			isto.dispose();	
		}
	}

	public void setMenuContasBancarias() {
		jPopupMenuContasBancarias = new JPopupMenu();
		JMenuItem jMenuItemDetalhar = new JMenuItem();

		jMenuItemDetalhar.setText("Detalhar");

		jMenuItemDetalhar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {

				int rowSel = table_ClienteContato.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = table_ClienteContato.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
						
				ClienteContato contato = modelo_ClienteContato.getValue(indiceDaLinha);

			

			}
		});

		jPopupMenuContasBancarias.add(jMenuItemDetalhar);
		
		table_ClienteContato.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuContasBancarias.show(table_ClienteContato, e.getX(), e.getY());
				}else {
					if (e.getClickCount() == 2) {
						processarSelecao(janela_pai_global);

					}
				}
			}
		});

	}

	public static class ClienteContatoTableModel extends AbstractTableModel {

		private final int id_cliente = 0;
		private final int nome_cliente = 1;
		private final int cpf_cliente = 2;
		private final int ie_cliente = 3;
		private final int id_contato = 4;
		private final int cargo_contato = 5;
		private final int nome_contato = 6;
		private final int celular_contato = 7;
		private final int fixo_contato = 8;
		private final int e_mail_contato = 9;
		private final int descricao_contato = 10;
		private final int observacao_contato = 11;

		private final String colunas[] = {"ID CLIENTE", "NOME CLIENTE", "CPF/CNPJ CLIENTE", "I.E CLIENTE", "ID:", "CARGO", "NOME:", "CELULAR:", "FIXO:", "E-MAIL:", "DESCRIÇÃO:", "OBSERVAÇÃO" };
		private final ArrayList<ClienteContato> dados = new ArrayList<>();// usamos como dados uma lista genérica de nfs

		public ClienteContatoTableModel() {

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
			case id_cliente:
				return Integer.class;
			case nome_cliente:
				return String.class;
			case cpf_cliente:
				return String.class;
			case ie_cliente:
				return String.class;
			case id_contato:
				return Integer.class;
			case cargo_contato:
				return String.class;
			case nome_contato:
				return String.class;
			case celular_contato:
				return String.class;
			case fixo_contato:
				return String.class;
			case e_mail_contato:
				return String.class;
			case descricao_contato:
				return String.class;
			case observacao_contato:
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
			ClienteContato clienteContato = dados.get(rowIndex);
			Contato contato = clienteContato.getContato();
			CadastroCliente cliente = clienteContato.getCliente();

			// retorna o valor da coluna
			switch (columnIndex) {
			case id_cliente:
				return cliente.getId();
			case nome_cliente:{
				if (cliente.getTipo_pessoa() == 0) {
					return cliente.getNome().toUpperCase() + " " + cliente.getSobrenome().toUpperCase();
				} else {
					return cliente.getRazao_social().toUpperCase();
				}
			}
			case cpf_cliente:{
				if (cliente.getTipo_pessoa() == 0)
					return cliente.getCpf();
				else
					return cliente.getCnpj();
			}
			case ie_cliente:
				return cliente.getIe();
			case id_contato:
				return contato.getId();
			case cargo_contato:
				return contato.getCargo();
			case nome_contato:
				return contato.getNome();
			case celular_contato:
				return contato.getCelular();
			case fixo_contato:
				return contato.getFixo();
			case e_mail_contato:
				return contato.getE_mail();
			case descricao_contato:
				return contato.getDescricao();
			case observacao_contato:
				return contato.getObservacao();
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
			ClienteContato nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public ClienteContato getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(ClienteContato nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(ClienteContato nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<ClienteContato> dadosIn) {
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
		public void onRemove(ClienteContato nota) {
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

	public void setTelaPai(JFrame _telaPai) {
		this.telaPai = _telaPai;
	}

	public void filtrar() {
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		/*
		 * private final int id_cliente = 0;
		private final int nome_cliente = 1;
		private final int cpf_cliente = 2;
		private final int ie_cliente = 3;
		private final int id_ClienteContato = 4;
		private final int cargo_ClienteContato = 5;
		private final int nome_ClienteContato = 6;
		private final int celular_ClienteContato = 7;
		private final int fixo_ClienteContato = 8;
		private final int e_mail_ClienteContato = 9;
		private final int descricao_ClienteContato = 10;
		private final int observacao_ClienteContato = 11;
		 */
		
		String nome_cliente = entNomeCliente.getText().toUpperCase();
		String cpf_cliente = entCpfCliente.getText().toUpperCase();
		String ie_cliente = entIeCliente.getText().toUpperCase();
		String nome_contato = entNomeContato.getText().toUpperCase();

		if (checkString(nome_cliente))
			filters.add(RowFilter.regexFilter(nome_cliente, 1));

		if (checkString(cpf_cliente))
			filters.add(RowFilter.regexFilter(cpf_cliente, 2));

		if (checkString(ie_cliente))
			filters.add(RowFilter.regexFilter(ie_cliente, 3));

		if (checkString(nome_contato))
			filters.add(RowFilter.regexFilter(nome_contato, 6));

		sorter.setRowFilter(RowFilter.andFilter(filters));
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
}
