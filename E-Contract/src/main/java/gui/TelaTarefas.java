package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
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
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class TelaTarefas extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
    private TelaTarefas isto;
    private JDialog telaPai;
    private JTable table_tarefas_designadas;
    
	private TarefaTableModel modelo_minhas_tarefas = new TarefaTableModel();
	private TarefaTableModel modelo_tarefas_designadas = new TarefaTableModel();
	private JTable table_minhas_tarefas;

    private ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = new ArrayList<>();
    private ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = new ArrayList<>();

    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	
	public TelaTarefas(Window janela_pai) {
		//setModal(true);

		getDadosGlobais();
		 isto = this;
		setResizable(true);
		setTitle("E-Contract - Minhas Tarefas");

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[309px][grow][grow]", "[][26px][grow][][grow][][26px][grow][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 0));
		painelPrincipal.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[198px]", "[64px]"));
		
		JLabel lblNewLabel = new JLabel("Tarefas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny bottom");
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaTarefas.class.getResource("/imagens/icone_tarefa.png")));
		panel.add(lblNewLabel_2, "cell 0 0,alignx right,aligny top");
		
		JLabel lblNewLabel_1 = new JLabel("Minhas Tarefas:");
		lblNewLabel_1.setForeground(new Color(0, 51, 0));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1, "cell 0 1,alignx center,growy");
		
		
		
		table_minhas_tarefas = new JTable(modelo_minhas_tarefas);
		table_minhas_tarefas.setBackground(new Color(0, 153, 102));
		
		table_minhas_tarefas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_minhas_tarefas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_minhas_tarefas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_minhas_tarefas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_minhas_tarefas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_minhas_tarefas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_minhas_tarefas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_minhas_tarefas.getColumnModel().getColumn(7).setPreferredWidth(90);
		
				
				table_minhas_tarefas.setRowHeight(30);
				JScrollPane scrollPaneMinhasTarefas = new JScrollPane(table_minhas_tarefas);
				painelPrincipal.add(scrollPaneMinhasTarefas, "cell 0 2 3 1,grow");
		
		
		
		table_tarefas_designadas = new JTable(modelo_tarefas_designadas);
		table_tarefas_designadas.setBackground(new Color(255, 153, 0));
		

		table_tarefas_designadas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_tarefas_designadas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_tarefas_designadas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_tarefas_designadas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_tarefas_designadas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_tarefas_designadas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_tarefas_designadas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_tarefas_designadas.getColumnModel().getColumn(7).setPreferredWidth(90);
		
		JButton btnVerTarefa_1 = new JButton("Ver Tarefa");
		btnVerTarefa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = table_minhas_tarefas.getSelectedRow();
				
				CadastroContrato.CadastroTarefa tarefa = (CadastroContrato.CadastroTarefa) modelo_minhas_tarefas.getValue(indiceDaLinha);
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				int cont = gerenciar.getContratoPorTarefa(tarefa.getId_tarefa());
				
				
				CadastroContrato contrato = gerenciar.getContrato(cont);
				
				
				if(contrato.getSub_contrato() == 1) {
					// e um subcontrato
					//pegar contrato pai
					CadastroContrato contratopai = gerenciar.getContratoPai(contrato.getId());
					TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
					telagerenciar.abrirAbaTarefasSubContrato(contrato);
					telagerenciar.setVisible(true);
					
				}else {
				
				TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
				telagerenciar.abrirAbaTarefasContrato();
				telagerenciar.setVisible(true);
				}
				
				
			}
		});
		btnVerTarefa_1.setForeground(Color.WHITE);
		btnVerTarefa_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVerTarefa_1.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnVerTarefa_1, "cell 2 3,alignx right");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBackground(Color.BLACK);
		lblNewLabel_3.setOpaque(true);
		painelPrincipal.add(lblNewLabel_3, "cell 0 5 3 1,growx");

		table_tarefas_designadas.setRowHeight(30);
		
		JScrollPane scrollPaneTarefasDesignadas = new JScrollPane(table_tarefas_designadas);
		painelPrincipal.add(scrollPaneTarefasDesignadas, "cell 0 7 3 1,grow");
		JLabel lblNewLabel_1_1 = new JLabel("Tarefas designadas a mim");
		lblNewLabel_1_1.setBackground(new Color(0, 51, 204));
		lblNewLabel_1_1.setForeground(new Color(255, 153, 51));
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		painelPrincipal.add(lblNewLabel_1_1, "cell 0 6,alignx right,aligny top");
		
		JButton btnVerTarefa = new JButton("Ver Tarefa");
		btnVerTarefa.setForeground(Color.WHITE);
		btnVerTarefa.setBackground(new Color(0, 51, 0));
		btnVerTarefa.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVerTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas_designadas.getSelectedRow();
				
				CadastroContrato.CadastroTarefa tarefa = (CadastroContrato.CadastroTarefa) modelo_tarefas_designadas.getValue(indiceDaLinha);
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				int cont = gerenciar.getContratoPorTarefa(tarefa.getId_tarefa());
				
				
				CadastroContrato contrato = gerenciar.getContrato(cont);
				
				
				if(contrato.getSub_contrato() == 1) {
					// e um subcontrato
					//pegar contrato pai
					CadastroContrato contratopai = gerenciar.getContratoPai(contrato.getId());
					TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
					telagerenciar.abrirAbaTarefasSubContrato(contrato);
					telagerenciar.setVisible(true);
					
				}else {
				
				TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
				telagerenciar.abrirAbaTarefasContrato();
				telagerenciar.setVisible(true);
				}
				
			}
		});
		painelPrincipal.add(btnVerTarefa, "cell 2 8,alignx right,growy");
	
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		getTarefas();
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public void getTarefas() {
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = gerenciar.getTarefasPorCriador(login.getId());
		
		for(CadastroContrato.CadastroTarefa tf : minhas_tarefas) {
			modelo_minhas_tarefas.onAdd(tf);
		}
		
		ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = gerenciar.getTarefasComoExecutor(login.getId());
		
		for(CadastroContrato.CadastroTarefa tf : tarefas_designadas) {
			modelo_tarefas_designadas.onAdd(tf);
		}
		
		
	}
	

		
	
	public class TarefaTableModel extends AbstractTableModel {
		
		// constantes p/identificar colunas
		private final int id_tarefa = 0;
		private final int status = 1;
		private final int nome = 2;
		private final int descricao = 3;
		private final int mensagem = 4;
		private final int resposta = 5;
		private final int data = 6;
		private final int hora = 7;
		private final int criador = 8;
		private final int executor = 9;

		private final int hora_agendada = 10;

		private final int data_agendada = 11;
		private final int prioridade = 12;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Nome", "Descrição", "Mensagem", "Resposta", "Data", "Hora",
				"Criador", "Executor", "Hora Agendada", "Data Agendada", "Prioridade" };

		private final ArrayList<CadastroContrato.CadastroTarefa> dados = new ArrayList<>();// usamos como dados uma lista genérica
																				// de
																				// nfs

		public TarefaTableModel() {

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
			case id_tarefa: {
				return Integer.class;

			}
			case status: {
				return String.class;
			}
			case nome: {
				return String.class;
			}
			case descricao: {
				return String.class;
			}
			case mensagem: {
				return String.class;
			}
			case resposta: {
				return String.class;
			}
			case data: {
				return String.class;
			}
			case hora: {
				return String.class;
			}
			case criador: {
				return String.class;
			}
			case executor: {
				return String.class;
			}

			case hora_agendada: {
				return String.class;
			}
			case data_agendada: {
				return String.class;
			}
			case prioridade: {
				return String.class;
			}
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
			CadastroContrato.CadastroTarefa dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_tarefa: {
				dado.getId_tarefa();
			}
			case status: {
				if (dado.getStatus_tarefa() == 1) {
					return "Concluida";

				} else if (dado.getStatus_tarefa() == 2) {
					return "Em Andamento";

				}
			}
			case nome: {
				return dado.getNome_tarefa();
			}
			case descricao: {
				return dado.getDescricao_tarefa();
			}
			case mensagem: {
				return dado.getMensagem();
			}
			case resposta: {
				return dado.getResposta();
			}
			case data: {
				return dado.getData();
			}
			case hora: {
				return dado.getHora();
			}
			case criador: {
				return dado.getNome_criador();
			}
			case executor: {
				return dado.getNome_executor();
			}

			case hora_agendada: {
				return dado.getHora_agendada();
			}
			case data_agendada: {
				return dado.getData_agendada();
			}
			case prioridade: {

				if (dado.getPrioridade() == 1) {
					return "Imediata - Neste Momento";
				} else if (dado.getPrioridade() == 2) {
					return "Urgente - Nesta Hora";
				} else if (dado.getPrioridade() == 3) {
					return "Quanto Antes - Ainda Hoje";
				} else if (dado.getPrioridade() == 4) {
					return "Média - Ainda essa semana";
				} else if (dado.getPrioridade() == 5) {
					return "Leve - Ainda esse mês";
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

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroContrato.CadastroTarefa ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato.CadastroTarefa getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato.CadastroTarefa dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroContrato.CadastroTarefa dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato.CadastroTarefa> dadosIn) {
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
		public void onRemove(CadastroContrato.CadastroTarefa dado) {
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

		public CadastroContrato.CadastroTarefa onGet(int row) {
			return dados.get(row);
		}
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
