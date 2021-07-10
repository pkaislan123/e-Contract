package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.awt.Font;
import java.awt.Window;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class TelaContaBancaria extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTable table_cb;
	private ContaBancariaTableModel modelo_cb = new ContaBancariaTableModel();
	private ContaBancaria contaSelecionada;
	private Window telaPai;
	private static ArrayList<ContaBancaria> contas_bancarias = new ArrayList<>();
	private JButton btnSelecionar;
	 
 	private TableRowSorter<ContaBancariaTableModel> sorter;
 	private JTextField entNome;
 	private JLabel lblNewLabel;
 	private JLabel lblCpfcnpj;
 	private JLabel lblBanco;
 	private JTextField entBanco;
 	private JTextField entId;
 	private JButton btnRefazerPesquisa;
 	private JButton btnLimpar;
 	private JButton btnFiltrar;
 	private JTextField entAgencia;
 	private JLabel lblAgncia;

	 
	 public static void pesquisar_contas(ContaBancariaTableModel modelo_cb)
		{ 
		 modelo_cb.onRemoveAll();
	    GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
	    contas_bancarias = listaClientes.getContas();
	    
	    
	    for(ContaBancaria conta_bancaria : contas_bancarias)
		 {
	    	modelo_cb.onAdd(conta_bancaria);

		 }
	    
	    
	   
	    }

	public TelaContaBancaria(Window janela_pai)  {
		//setModal(true);

		TelaContaBancaria isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Conta Bancaria");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 907, 594);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		 sorter = new TableRowSorter<ContaBancariaTableModel>(modelo_cb);
	        painelPrincipal.setLayout(new MigLayout("", "[][][50px][10px][278px][49px][71px][14px][103px][12px][89px][10px][155px]", "[33px][33px][28px][grow][]"));
	        
	        btnSelecionar = new JButton("Selecionar");
	        btnSelecionar.setForeground(Color.WHITE);
	        btnSelecionar.setBackground(new Color(0, 51, 51));
	        btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
	        btnSelecionar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		int rowSel = table_cb.getSelectedRow();//pega o indice da linha na tabela
					int  indiceDaLinha = table_cb.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model				
					
	        		contaSelecionada = contas_bancarias.get(indiceDaLinha);
					
	        		if(telaPai != null) {
	        			if(telaPai instanceof TelaElaborarNovoContrato) {
	        				((TelaElaborarNovoContrato) telaPai).setContaBancaria(contaSelecionada);

	        			}else if(telaPai instanceof TelaImportarContratoManual) {
	        				((TelaImportarContratoManual) telaPai).setContaBancaria(contaSelecionada);

	        			}
	        		}
				

					isto.dispose();
	        	}
	        });
	        
	        table_cb = new JTable(modelo_cb);
	        table_cb.setRowSorter(sorter);
	        
	        		 table_cb.setBackground(new Color(255, 255, 255));
	        		 
	        		 table_cb.getColumnModel().getColumn(2)
	        .setPreferredWidth(150);
	        		 table_cb.getColumnModel().getColumn(3)
	        .setPreferredWidth(120);
	         		 
	        table_cb.setRowHeight(30);
	        JScrollPane scrollPaneCB = new JScrollPane(table_cb);
	        painelPrincipal.add(scrollPaneCB, "cell 0 3 13 1,grow");
	        scrollPaneCB.setAutoscrolls(true);
	        scrollPaneCB.setBackground(new Color(255, 255, 255));
	        painelPrincipal.add(btnSelecionar, "cell 10 4 3 1,alignx center,aligny top");
	        
	        entNome = new JTextField();
	        entNome.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		filtrar();
	        	}
	        });
	        entNome.setFont(new Font("SansSerif", Font.BOLD, 16));
	        entNome.setColumns(10);
	        painelPrincipal.add(entNome, "cell 4 0,grow");
	        
	        lblNewLabel = new JLabel("Nome:");
	        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        painelPrincipal.add(lblNewLabel, "cell 2 0 2 1,alignx right,aligny center");
	        
	        lblCpfcnpj = new JLabel("CPF/CNPJ:");
	        lblCpfcnpj.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        painelPrincipal.add(lblCpfcnpj, "cell 6 0 2 1,alignx right,aligny center");
	        
	        lblBanco = new JLabel("Banco:");
	        lblBanco.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        painelPrincipal.add(lblBanco, "cell 2 1 2 1,alignx right,aligny center");
	        
	        entBanco = new JTextField();
	        entBanco.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		filtrar();

	        	}
	        });
	        entBanco.setFont(new Font("SansSerif", Font.BOLD, 16));
	        entBanco.setColumns(10);
	        painelPrincipal.add(entBanco, "cell 4 1,grow");
	        
	        entId = new JTextField();
	        entId.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		filtrar();

	        	}
	        });
	        entId.setFont(new Font("SansSerif", Font.BOLD, 16));
	        entId.setColumns(10);
	        painelPrincipal.add(entId, "cell 8 0 5 1,grow");
	        
	        btnRefazerPesquisa = new JButton("Refazer Pesquisa");
	        btnRefazerPesquisa.setBackground(new Color(0, 51, 153));
	        btnRefazerPesquisa.setForeground(Color.WHITE);
	        btnRefazerPesquisa.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnRefazerPesquisa.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	    	        pesquisar_contas(modelo_cb);
	        	}
	        });
	        painelPrincipal.add(btnRefazerPesquisa, "cell 6 2 3 1,alignx right,growy");
	        
	        btnLimpar = new JButton("Limpar");
	        btnLimpar.setBackground(Color.ORANGE);
	        btnLimpar.setForeground(Color.WHITE);
	        btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnLimpar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
				    sorter.setRowFilter( RowFilter.regexFilter(""));

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
	        painelPrincipal.add(btnFiltrar, "cell 12 2,alignx left,aligny top");
	        
	        entAgencia = new JTextField();
	        entAgencia.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		filtrar();

	        	}
	        });
	        entAgencia.setFont(new Font("SansSerif", Font.BOLD, 16));
	        entAgencia.setColumns(10);
	        painelPrincipal.add(entAgencia, "cell 8 1 5 1,grow");
	        
	        lblAgncia = new JLabel("Agência:");
	        lblAgncia.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        painelPrincipal.add(lblAgncia, "cell 6 1 2 1,alignx right,aligny center");
		
	        
	        pesquisar_contas(modelo_cb);
		this.setLocationRelativeTo(janela_pai);

		
		
	}
	
	public static class ContaBancariaTableModel extends AbstractTableModel{
		 

	    private final int id=0;
	    private final int cpf=1;
	    private final int nome=2;
	    private final int banco = 3;
	    private final int codigo = 4;
	    private final int agencia = 5;
	    private final int conta = 6;

	 
	    private final String colunas[]={"ID:","CPF/CNPJ:","Nome:","Banco:","Código:", "Agência:", "Conta:"};
	    private final ArrayList<ContaBancaria> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public ContaBancariaTableModel() {
	        
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
	        case banco:
	            return String.class;
	        case codigo:
	            return String.class;
	        case agencia:
	            return String.class;
	        case conta:
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
	    	ContaBancaria conta_bc =dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case id:
	        	conta_bc.getId_conta();
	        case cpf:
	            return conta_bc.getCpf_titular();
	        case nome:
	            return conta_bc.getNome();
	        case banco:
	            return conta_bc.getBanco();
	        case codigo:
	            return conta_bc.getCodigo();
	        case agencia:
	            return conta_bc.getAgencia();
	        case conta:
	            return conta_bc.getConta();
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
	    	ContaBancaria nota=dados.get(rowIndex);
	 
	      
	    }
	 
	    //Métodos abaixo são para manipulação de dados
	 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public ContaBancaria getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(ContaBancaria nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(ContaBancaria nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<ContaBancaria> dadosIn) {
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
	    public void onRemove(ContaBancaria nota) {
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
	public void setTelaPai(JFrame _telaPai) {
		this.telaPai = _telaPai;
	}

	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);


		    String nome = entNome.getText().toUpperCase();
		    String banco = entBanco.getText().toUpperCase();
		    String agencia =  entAgencia.getText().toUpperCase();
		    String cpnf_cnpj = entId.getText().toUpperCase();

		   
		    if(checkString(nome))
		    filters.add(RowFilter.regexFilter(nome, 2));
		    
		    if(checkString(banco))
		    filters.add(RowFilter.regexFilter(banco, 3));

		    if(checkString(agencia))
		    filters.add(RowFilter.regexFilter(agencia, 5));
		    
		    if(checkString(cpnf_cnpj))
			    filters.add(RowFilter.regexFilter(cpnf_cnpj, 1));
		  
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
	}
	
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
}
