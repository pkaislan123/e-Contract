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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
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



public class TelaContaBancaria extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JPanel painel_table_cb;
	private JTable table_cb;
	private ContaBancariaTableModel modelo_cb = new ContaBancariaTableModel();
	private ContaBancaria contaSelecionada;
	private JDialog telaPai;
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
		painelPrincipal.setLayout(null);
		
		
		 painel_table_cb = new JPanel();
		 painel_table_cb.setBounds(38, 213, 841, 255);
		 
		 table_cb = new JTable(modelo_cb);
		 sorter = new TableRowSorter<ContaBancariaTableModel>(modelo_cb);
		 table_cb.setRowSorter(sorter);

		 table_cb.setBackground(new Color(255, 255, 255));
		 
		 table_cb.getColumnModel().getColumn(2)
	        .setPreferredWidth(150);
		 table_cb.getColumnModel().getColumn(3)
	        .setPreferredWidth(120);
		 
	        table_cb.setRowHeight(30);
	     
	    
	        painel_table_cb.setLayout(null);
	        JScrollPane scrollPaneCB = new JScrollPane(table_cb);
	        scrollPaneCB.setBounds(10, 11, 821, 237);
	        scrollPaneCB.setAutoscrolls(true);
	        scrollPaneCB.setBackground(new Color(255, 255, 255));
	        painel_table_cb.add(scrollPaneCB);
			
		
	        painelPrincipal.add(painel_table_cb);
	        
	        btnSelecionar = new JButton("Selecionar");
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
	        btnSelecionar.setBounds(680, 502, 89, 23);
	        painelPrincipal.add(btnSelecionar);
	        
	        entNome = new JTextField();
	        entNome.setColumns(10);
	        entNome.setBounds(98, 71, 278, 33);
	        painelPrincipal.add(entNome);
	        
	        lblNewLabel = new JLabel("Nome:");
	        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        lblNewLabel.setBounds(51, 78, 37, 17);
	        painelPrincipal.add(lblNewLabel);
	        
	        lblCpfcnpj = new JLabel("CPF/CNPJ:");
	        lblCpfcnpj.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        lblCpfcnpj.setBounds(425, 80, 71, 17);
	        painelPrincipal.add(lblCpfcnpj);
	        
	        lblBanco = new JLabel("Banco:");
	        lblBanco.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        lblBanco.setBounds(50, 122, 38, 17);
	        painelPrincipal.add(lblBanco);
	        
	        entBanco = new JTextField();
	        entBanco.setColumns(10);
	        entBanco.setBounds(99, 115, 277, 33);
	        painelPrincipal.add(entBanco);
	        
	        entId = new JTextField();
	        entId.setColumns(10);
	        entId.setBounds(510, 71, 277, 33);
	        painelPrincipal.add(entId);
	        
	        btnRefazerPesquisa = new JButton("Refazer Pesquisa");
	        btnRefazerPesquisa.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	    	        pesquisar_contas(modelo_cb);
	        	}
	        });
	        btnRefazerPesquisa.setBounds(487, 170, 126, 28);
	        painelPrincipal.add(btnRefazerPesquisa);
	        
	        btnLimpar = new JButton("Limpar");
	        btnLimpar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
				    sorter.setRowFilter( RowFilter.regexFilter(""));

	        	}
	        });
	        btnLimpar.setBounds(625, 172, 89, 23);
	        painelPrincipal.add(btnLimpar);
	        
	        btnFiltrar = new JButton("Filtrar");
	        btnFiltrar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	 filtrar();
	        	}
	        });
	        btnFiltrar.setBounds(724, 170, 89, 23);
	        painelPrincipal.add(btnFiltrar);
	        
	        entAgencia = new JTextField();
	        entAgencia.setColumns(10);
	        entAgencia.setBounds(510, 115, 277, 33);
	        painelPrincipal.add(entAgencia);
	        
	        lblAgncia = new JLabel("Agência:");
	        lblAgncia.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	        lblAgncia.setBounds(442, 122, 48, 17);
	        painelPrincipal.add(lblAgncia);
		
	        
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
	public void setTelaPai(JDialog _telaPai) {
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
