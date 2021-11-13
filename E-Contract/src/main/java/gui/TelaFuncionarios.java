
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioDepartamentos;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoAcessoTemporario;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDepartamentos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.gui.TelaContratos.EvenOddRenderer;
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
import main.java.cadastros.CadastroFuncionario;
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

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;



import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Font;
import java.awt.Frame;
import net.miginfocom.swing.MigLayout;

public class TelaFuncionarios extends JFrame {

	private Window telaPai;
	private JPanel contentPane;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;


 	private FuncionarioTableModel modelos_funcionarios = new FuncionarioTableModel();
 	private TableRowSorter<FuncionarioTableModel> sorter;
    private static ArrayList<CadastroFuncionario> lista_funcionarios = new ArrayList<>();
	private JTextField entNome;
	private CadastroFuncionario funcionarioSelecionado;
	private TelaFuncionarios isto;
	private JTable tabela;
	private JTextField entCpfCnpj;
	private JComboBox cBDepartamento;
	private JComboBox cBStatus;
	private JLabel lblContratosAtivos, lblContratosInativos;	
	
	public TelaFuncionarios(int flag_tipo_tela, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCliente.class.getResource("/imagens/equipe.png")));
		getDadosGlobais();
		// flag_tipo_tela == 1 //modo cliente edição
		// flag_tipo_tela == 0 //modo cliente seleção
		
		//flag_tipo_cliente == 1 //retorna comprador
		//flag_tipo_cliente == 2 //retorna vendedor
		//setAlwaysOnTop(true);

	//setModal(true);

		 isto = this;
		setTitle("E-Contract - Colaboradores");

		
		setResizable(false);
	
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1061, 719);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel painelClientes = new JPanel();
		painelClientes.setBackground(Color.WHITE);
		painelClientes.setBounds(10, 11, 739, 446);
		painelClientes.setLayout(new MigLayout("", "[61px][][][2px][278px,grow][13px][71px][4px][77px,grow][][12px][89px][10px][131px]", "[][][37px][][grow][::50px][][][]"));
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entNome.setText("");
			
				entCpfCnpj.setText("");
				
				cBDepartamento.setSelectedIndex(0);
			}
		});
		
		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblDepartamento, "cell 0 1 3 1");
		
		 cBDepartamento = new JComboBox();
		 cBDepartamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelClientes.add(cBDepartamento, "cell 4 1,growx");
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblStatus, "cell 6 1,alignx right");
		
		 cBStatus = new JComboBox();
		cBStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cBStatus.addItem("TODOS");
		cBStatus.addItem("ATIVO");
		cBStatus.addItem("DESATIVADO");
		painelClientes.add(cBStatus, "cell 8 1 2 1,growx");
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparCampos.setBackground(Color.RED);
		painelClientes.add(btnLimparCampos, "cell 9 3");
		//panel.setLayout(null);
		
		
		FuncionarioContratoCellREnder renderer = new FuncionarioContratoCellREnder();
		 sorter = new TableRowSorter<FuncionarioTableModel>(modelos_funcionarios);

		 tabela = new JTable(modelos_funcionarios);
		
					
				 tabela.setRowSorter(sorter);
				 tabela.addKeyListener(new KeyAdapter() {
				 	@Override
				 	public void keyPressed(KeyEvent arg0) {
				 		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				 		{
				 			processarSelecao(flag_tipo_tela, tabela,janela_pai);
				 		}
				 	}
				 });
				 
        tabela.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
				 			processarSelecao(flag_tipo_tela, tabela,janela_pai);

                }
				 		
              }
             });
        
		tabela.setDefaultRenderer(Object.class, renderer);

        tabela.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelClientes.add(scrollPane, "cell 0 4 14 1,grow");
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
               // pesquisar(modelo);

        	}
        });
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		
		
		 URL url = getClass().getResource("/imagens/pesquisar.png");
	 	ImageIcon img_botao = new ImageIcon(url);
		 
		 entNome = new JTextField();
		 entNome.setFont(new Font("Arial", Font.BOLD, 16));
		 entNome.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent e) {
		 		
		 	}
		 });
		 painelClientes.add(entNome, "cell 4 0,grow");
		 entNome.setColumns(10);
		 
		
		 JTabbedPane painelPrincipal = new JTabbedPane();;
		   painelPrincipal.setBackground(new Color(255, 255, 255));
			painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			
		
			painelPrincipal.addTab("Clientes", painelClientes);
			
			JLabel lblNewLabel = new JLabel("Nome:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			painelClientes.add(lblNewLabel, "cell 0 0 3 1,alignx right,aligny center");
			
			JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
			lblCpfcnpj.setFont(new Font("Arial", Font.PLAIN, 16));
			painelClientes.add(lblCpfcnpj, "cell 6 0,growx,aligny center");
			
			entCpfCnpj = new JTextField();
			entCpfCnpj.setFont(new Font("Arial", Font.BOLD, 16));
			entCpfCnpj.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
				
				}
			});
			entCpfCnpj.setColumns(10);
			painelClientes.add(entCpfCnpj, "cell 8 0 6 1,grow");
			
			JButton btnFiltrar = new JButton("Filtrar");
			btnFiltrar.setBackground(new Color(0, 51, 0));
			btnFiltrar.setForeground(Color.WHITE);
			btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtrar();
				}
			});
			painelClientes.add(btnFiltrar, "cell 13 3,alignx left,growy");
			
			JButton btnLimpar = new JButton("Limpar Pesquisa");
			btnLimpar.setBackground(new Color(204, 51, 0));
			btnLimpar.setForeground(Color.WHITE);
			btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    sorter.setRowFilter( RowFilter.regexFilter(""));

				}
			});
			painelClientes.add(btnLimpar, "cell 11 3,grow");
			
			JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
			btnRefazerPesquisa.setBackground(Color.BLUE);
			btnRefazerPesquisa.setForeground(Color.WHITE);
			btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnRefazerPesquisa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pesquisar();
				}
			});
			painelClientes.add(btnRefazerPesquisa, "cell 6 3 3 1,alignx right,growy");
			
			JLabel lblNewLabel_1 = new JLabel("Legenda:");
			lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
			painelClientes.add(lblNewLabel_1, "cell 0 6");
			
			JButton btnEditar = new JButton("Gerenciar");
			painelClientes.add(btnEditar, "cell 9 6");
			btnEditar.setBackground(Color.WHITE);
			btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					
					if(login != null) {
						if(login.getConfigs_privilegios().getNivel_privilegios() <= 2) {

							int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
							int indexRowModel = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
						
							
							TelaGerenciarFuncionario telagerenciar  = new TelaGerenciarFuncionario(new GerenciarBancoFuncionarios().getfuncionario(lista_funcionarios.get(indexRowModel).getId_funcionario()), isto);
							telagerenciar.setVisible(true);
							//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
							//editarCliente(indiceDaLinha);
							
						}else {
							//verifica se tem acesso temporario
							GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
							ArrayList<CadastroAcessoTemporario> acessos = gerenciar.getAcessosTemporariosPorExecutor(login.getId());
							
							boolean tem_acesso = false;
							
							for(CadastroAcessoTemporario acesso : acessos) {

								int modulo = acesso.getModulo();
								if(modulo == 0) {
									//modulo e recursos humanos
									LocalDateTime inicio = LocalDateTime.parse(acesso.getData_inicial() + " " + acesso.getHora_inicial(),
											DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
									LocalDateTime fim = LocalDateTime.parse(acesso.getData_final() + " " + acesso.getHora_final(),
											DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
									
									LocalDateTime agora = LocalDateTime.now();

						
									
									if(agora.isAfter(inicio) && agora.isBefore(fim)) {
										tem_acesso = true;
										break;
									}
									
									
									
								}
								
							}
							
								if(!tem_acesso) 
							JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
							else {
								int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
								int indexRowModel = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
								TelaGerenciarFuncionario telagerenciar  = new TelaGerenciarFuncionario(new GerenciarBancoFuncionarios().getfuncionario(lista_funcionarios.get(indexRowModel).getId_funcionario()), isto);
								telagerenciar.setVisible(true);
								//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
								//editarCliente(indiceDaLinha);
							}
						}
						
					}
					
				
					
				}
			});
			
			JButton btnSelecionar = new JButton("Selecionar");
			painelClientes.add(btnSelecionar, "cell 11 6");
			btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
			btnSelecionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					processarSelecao(flag_tipo_tela, tabela,janela_pai);

				}
			});
			
			JButton btnUsurio = new JButton("Novo Colaborador");
			painelClientes.add(btnUsurio, "cell 13 6");
			btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));
			btnUsurio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					TelaFuncionariosCadastroFuncionario funcionario = new TelaFuncionariosCadastroFuncionario(0, null, isto);
					funcionario.setVisible(true);
					
				}
			});		
			
			JLabel lblNewLabel_2 = new JLabel("Ativo:");
			lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 16));
			painelClientes.add(lblNewLabel_2, "cell 0 7,alignx right");
			 
			 JLabel lblNewLabel_3 = new JLabel("___");
			 lblNewLabel_3.setOpaque(true);
			 lblNewLabel_3.setForeground(new Color(0, 51, 0));
			 lblNewLabel_3.setBackground(new Color(0, 51, 0));
			 painelClientes.add(lblNewLabel_3, "cell 1 7");
			
			 lblContratosAtivos = new JLabel("0");
			lblContratosAtivos.setFont(new Font("SansSerif", Font.BOLD, 16));
			painelClientes.add(lblContratosAtivos, "cell 2 7,alignx center");
			
			JLabel lblNewLabel_2_1 = new JLabel("Desativado:");
			lblNewLabel_2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
			painelClientes.add(lblNewLabel_2_1, "cell 0 8,alignx right");
			 
			 JLabel lblNewLabel_3_1 = new JLabel("___");
			 lblNewLabel_3_1.setOpaque(true);
			 lblNewLabel_3_1.setForeground(new Color(204, 0, 0));
			 lblNewLabel_3_1.setBackground(new Color(204, 0, 0));
			 painelClientes.add(lblNewLabel_3_1, "cell 1 8");
			
			 lblContratosInativos = new JLabel("0");
			lblContratosInativos.setFont(new Font("SansSerif", Font.BOLD, 16));
			painelClientes.add(lblContratosInativos, "cell 2 8,alignx center");
			
			
			contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
			contentPane.add(painelPrincipal, "cell 0 0,grow");
			
		
			pesquisarDepartamentos();
			pesquisar();
		this.setLocationRelativeTo(janela_pai);


	}
	

	
	
	public  void pesquisar( )
	{ 
	
		modelos_funcionarios.onRemoveAll();
    GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
    lista_funcionarios = gerenciar.getfuncionarios();
    
  
    for (CadastroFuncionario funcionario : lista_funcionarios) {     	
  
    
    	modelos_funcionarios.onAdd(funcionario);
    
    
		
	}
    
    calcular();
	}

	
	public static class FuncionarioTableModel extends AbstractTableModel{
		 

	    private final int id=0;
	    private final int cpf=1;
	    private final int nome=2;
	    private final int departamento=3;
	    private final int status = 4;
	 
	    private final String colunas[]={"ID:","CPF:","Nome:", "Departamento:", "Status:"};
	    private final ArrayList<CadastroFuncionario> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public FuncionarioTableModel() {
	        
	    }
	 
	    @Override
	    public int getColumnCount() {
	        //retorna o total de colunas
	        return colunas.length;
	    }
	 
	    @Override
	    public int getRowCount() {
	        //retorna o total de linhas na tabela
	        return dados.size();
	    }
	 
	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	        //retorna o tipo de dado, para cada coluna
	        switch (columnIndex) {
	        case id:
	            return Integer.class;
	        case cpf:
	            return String.class;
	        case nome:
	            return String.class;
	        case departamento:
	            return String.class;
	        case status:
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
	        //retorna o valor conforme a coluna e linha
	 
	        //pega o dados corrente da linha
	    	CadastroFuncionario nota=dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case id:
	            return nota.getId_funcionario();
	        case cpf:{
	        	return nota.getCpf();
	        }
	        case nome:
	           return nota.getNome().toUpperCase() + " " + nota.getSobrenome().toUpperCase();
	        case departamento:{
	        	return nota.getNome_departamento();
	        }
	        case status:{
	        	int sts = nota.getStatus();
	        	if(sts == 1) {
	        		return "ATIVO";
	        	}else if (sts == 0) {
	        		return "DESATIVADO";
	        	}
	        }
	        default:
	            throw new IndexOutOfBoundsException("Coluna Inválida!!!");
	        }
	    }
	 
	    @Override
	    public boolean isCellEditable(int rowIndex, int columnIndex) {
	        //metodo identifica qual coluna é editavel
	 
	        //só iremos editar a coluna BENEFICIO, 
	        //que será um checkbox por ser boolean
	      
	 
	        return false;
	    }
	 
	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	    	CadastroFuncionario nota=dados.get(rowIndex);
	 
	      
	    }
	 
	    //Métodos abaixo são para manipulação de dados
	 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public CadastroFuncionario getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(CadastroFuncionario nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(CadastroFuncionario nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<CadastroFuncionario> dadosIn) {
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
	    public void onRemove(CadastroFuncionario nota) {
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
	
	public void calcular() {
		
		int num_ativos = 0, num_inativos = 0;
		
		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			CadastroFuncionario func = modelos_funcionarios.getValue(index);
			
			
			int sts = func.getStatus();
        	if(sts == 1) {
        		num_ativos++;
        	}else if (sts == 0) {
        		num_inativos++;
        	}
			
		}
		
		
		lblContratosAtivos.setText(num_ativos + "");
		lblContratosInativos.setText(num_inativos + "");

	}
	
	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		    String nome = entNome.getText().toUpperCase();
		    String cpnf_cnpj = entCpfCnpj.getText().toUpperCase();

		  
		    if(checkString(cpnf_cnpj))
		    filters.add(RowFilter.regexFilter(cpnf_cnpj, 1));
		    
		    if(checkString(nome))
			    filters.add(RowFilter.regexFilter(nome, 2));
		    
		    if (cBDepartamento.getSelectedItem().toString() != null) {
				String s_dep = "";
				if (checkString(cBDepartamento.getSelectedItem().toString())) {
					s_dep = cBDepartamento.getSelectedItem().toString();
					if (!(s_dep.equalsIgnoreCase("TODOS"))) {
						filters.add(RowFilter.regexFilter(s_dep, 3));
					}
				}
			}
		    
		    
		    if (cBStatus.getSelectedItem().toString() != null) {
				String sts = "";
				if (checkString(cBStatus.getSelectedItem().toString())) {
					sts = cBStatus.getSelectedItem().toString();
					if (!(sts.equalsIgnoreCase("TODOS"))) {
						filters.add(RowFilter.regexFilter(sts, 4));
					}
				}
			}
		  
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
		    calcular();
	}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
				  
				
			
	}
	
	public void pesquisarDepartamentos() {
		GerenciarBancoFuncionariosDepartamentos gerenciar = new GerenciarBancoFuncionariosDepartamentos();
		ArrayList<CadastroFuncionarioDepartamentos> deps = gerenciar.getDepartamentos();
		
		cBDepartamento.addItem("TODOS");
		

		for (CadastroFuncionarioDepartamentos dep : deps) {
			cBDepartamento.addItem(dep.getNome());
		}

	}
	
	public void processarSelecao(int flag_tipo_tela, JTable tabela, Window janela_pai) {
		
		int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
		int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
		funcionarioSelecionado = lista_funcionarios.get(indiceDaLinha);
		
		if(flag_tipo_tela == 0) {
			((TelaCadastroClassificadores) janela_pai).setColaborador(funcionarioSelecionado);
			isto.dispose();
			
		}
	}
	
	
	
	class FuncionarioContratoCellREnder implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String dados = (String) table.getValueAt(row, 4);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {
				if (dados.equalsIgnoreCase("ATIVO")) {
					renderer.setForeground(Color.WHITE);
					renderer.setBackground(new Color(0,51,0)); // verde

				} else if (dados.equalsIgnoreCase("DESATIVADO")) {
					renderer.setBackground(new Color(204,0,0)); // vermelho
					renderer.setForeground(Color.WHITE);

				}

			}

			return renderer;
		}
	}
}
