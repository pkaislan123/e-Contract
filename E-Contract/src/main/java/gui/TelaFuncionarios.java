
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
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
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
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
	private JTable table;
	private JTextField entCpfCnpj;
	
	
	
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
		setBounds(100, 100, 810, 612);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel painelClientes = new JPanel();
		painelClientes.setBackground(Color.WHITE);
		painelClientes.setBounds(10, 11, 739, 446);
		painelClientes.setLayout(new MigLayout("", "[61px][2px][278px][13px][71px][4px][77px][][12px][89px][10px][131px]", "[33px][37px][28px][329px]"));
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entNome.setText("");
			
				entCpfCnpj.setText("");
			}
		});
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparCampos.setBackground(Color.RED);
		painelClientes.add(btnLimparCampos, "cell 7 2");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelClientes.add(panel, "cell 0 3 12 1,grow");
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelos_funcionarios);
		
		 sorter = new TableRowSorter<FuncionarioTableModel>(modelos_funcionarios);

			
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
        
	
        tabela.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
               // pesquisar(modelo);

        	}
        });
        panel.setLayout(new MigLayout("", "[271px][12px][108px][9px][110px][7px][109px][10px][105px]", "[253px][36px]"));
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane, "cell 0 0 9 1,grow");
		
		JButton btnUsurio = new JButton("Novo Colaborador");
		panel.add(btnUsurio, "cell 8 1,alignx left,aligny top");
		btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));
		
		JButton btnSelecionar = new JButton("Selecionar");
		panel.add(btnSelecionar, "cell 6 1,growx,aligny top");
		btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		
		JButton btnEditar = new JButton("Gerenciar");
		panel.add(btnEditar, "cell 4 1,alignx left,aligny top");
		btnEditar.setBackground(Color.WHITE);
		btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				TelaGerenciarFuncionario telagerenciar  = new TelaGerenciarFuncionario(lista_funcionarios.get(indexRowModel), isto);
				telagerenciar.setVisible(true);
				//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
				//editarCliente(indiceDaLinha);
				
				
			}
		});
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarSelecao(flag_tipo_tela, tabela,janela_pai);

			}
		});
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				TelaFuncionariosCadastroFuncionario funcionario = new TelaFuncionariosCadastroFuncionario(0, null, isto);
				funcionario.setVisible(true);
				
			}
		});		
		
		 URL url = getClass().getResource("/imagens/pesquisar.png");
	 	ImageIcon img_botao = new ImageIcon(url);
		 
		 entNome = new JTextField();
		 entNome.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent e) {
		 		filtrar();
		 	}
		 });
		 painelClientes.add(entNome, "cell 2 0,grow");
		 entNome.setColumns(10);
		 
		
		 JTabbedPane painelPrincipal = new JTabbedPane();;
		   painelPrincipal.setBackground(new Color(255, 255, 255));
			painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			
		
			painelPrincipal.addTab("Clientes", painelClientes);
			
			JLabel lblNewLabel = new JLabel("Nome:");
			lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			painelClientes.add(lblNewLabel, "cell 0 0,alignx center,aligny center");
			
			JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
			lblCpfcnpj.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			painelClientes.add(lblCpfcnpj, "cell 4 0,growx,aligny center");
			
			entCpfCnpj = new JTextField();
			entCpfCnpj.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					filtrar();
				}
			});
			entCpfCnpj.setColumns(10);
			painelClientes.add(entCpfCnpj, "cell 6 0 6 1,grow");
			
			JButton btnFiltrar = new JButton("Filtrar");
			btnFiltrar.setBackground(new Color(0, 51, 0));
			btnFiltrar.setForeground(Color.WHITE);
			btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtrar();
				}
			});
			painelClientes.add(btnFiltrar, "cell 11 2,alignx left,growy");
			
			JButton btnLimpar = new JButton("Limpar Pesquisa");
			btnLimpar.setBackground(new Color(204, 51, 0));
			btnLimpar.setForeground(Color.WHITE);
			btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    sorter.setRowFilter( RowFilter.regexFilter(""));

				}
			});
			painelClientes.add(btnLimpar, "cell 9 2,grow");
			
			JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
			btnRefazerPesquisa.setBackground(Color.BLUE);
			btnRefazerPesquisa.setForeground(Color.WHITE);
			btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 14));
			btnRefazerPesquisa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pesquisar();
				}
			});
			painelClientes.add(btnRefazerPesquisa, "cell 4 2 3 1,alignx right,growy");
			
			
			contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
			contentPane.add(painelPrincipal, "cell 0 0,grow");
			
		
	
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
	}

	
	public static class FuncionarioTableModel extends AbstractTableModel{
		 

	    private final int id=0;
	    private final int cpf=1;
	    private final int nome=2;
	  
	 
	    private final String colunas[]={"ID:","CPF:","Nome:"};
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
	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		    String nome = entNome.getText().toUpperCase();
		    String cpnf_cnpj = entCpfCnpj.getText().toUpperCase();

		    
		  
		    if(checkString(cpnf_cnpj))
		    filters.add(RowFilter.regexFilter(cpnf_cnpj, 1));
		    
		    if(checkString(nome))
			    filters.add(RowFilter.regexFilter(nome, 2));
		  
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
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
	
	public void processarSelecao(int flag_tipo_tela, JTable tabela, Window janela_pai) {
		
		int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
		int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
		funcionarioSelecionado = lista_funcionarios.get(indiceDaLinha);
		
		if(flag_tipo_tela == 0) {
			((TelaCadastroClassificadores) janela_pai).setColaborador(funcionarioSelecionado);
			isto.dispose();
			
		}
	}
}
