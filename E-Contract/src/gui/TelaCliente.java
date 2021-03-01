package gui;

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

import cadastros.CadastroCliente;
import cadastros.CadastroGrupo;
import cadastros.CadastroNFe;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoGrupos;

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
import gui.TelaElaborarNovoContrato;
import gui.TelaNotasFiscais.NFeTableModel;
import outros.JButtonBackground;
import outros.JPanelBackground;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Font;
import java.awt.Frame;

public class TelaCliente extends JDialog {

	private JDialog telaPai;
	private JPanel contentPane;

     
     DefaultTableModel modelo_grupos = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
 	private ClienteTableModel modelo_cliente = new ClienteTableModel();
 	private TableRowSorter<ClienteTableModel> sorter;
    private static ArrayList<CadastroCliente> clientes_pesquisados = new ArrayList<>();
	private static ArrayList<CadastroCliente> clientes_disponiveis = new ArrayList<>();
	private static ArrayList<CadastroGrupo> lista_grupos = new ArrayList<>();

	private CadastroCliente clienteSelecionado;
	private JTextField entNome;
	
	private TelaCliente isto;
	private JTable table;
	private JTextField entApelido;
	private JTextField entCpfCnpj;
	private JTextField entIe;
	
	public  void pesquisar( )
	{ 
	
		modelo_cliente.onRemoveAll();
    GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
    clientes_pesquisados = listaClientes.getClientes(-1, -1, null);
    clientes_disponiveis.clear();
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
     */
    for (CadastroCliente cliente : listaClientes.getClientes(-1, -1, null)) {
    	String cpf, cnpj, nome;
     	
    if(cliente.getArmazem() == 1 || cliente.getTransportador() == 1)	
    {
    	
    }else {
    	
    	modelo_cliente.onAdd(cliente);
    	clientes_disponiveis.add(cliente);
    }
    }
		
	}
	
	

	
	
	public TelaCliente(int flag_tipo_tela, int flag_tipo_cliente, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCliente.class.getResource("/imagens/equipe.png")));
		
		// flag_tipo_tela == 1 //modo cliente edição
		// flag_tipo_tela == 0 //modo cliente seleção
		
		//flag_tipo_cliente == 1 //retorna comprador
		//flag_tipo_cliente == 2 //retorna vendedor
		//setAlwaysOnTop(true);

	//setModal(true);

		 isto = this;
		setTitle("E-Contract - Clientes");

		
		setResizable(false);
	
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 612);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel painelClientes = new JPanel();
		painelClientes.setBackground(Color.WHITE);
		painelClientes.setBounds(10, 11, 739, 446);
		
		
		JPanel painelGrupos = new JPanel();
		painelGrupos.setBackground(Color.WHITE);
		painelGrupos.setBounds(10, 11, 739, 446);painelClientes.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 151, 748, 329);
		painelClientes.add(panel);
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelo_cliente);
		
		 sorter = new TableRowSorter<ClienteTableModel>(modelo_cliente);

			
		 tabela.setRowSorter(sorter);
		tabela.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(flag_tipo_tela == 0) {
					System.out.println("Valor do Enter: " + arg0.getKeyCode());
					int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
					int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
    				clienteSelecionado = clientes_disponiveis.get(indiceDaLinha);
    				if(flag_tipo_cliente == 1)
    					((TelaElaborarNovoContrato) telaPai).setComprador1(clienteSelecionado);
       				else if (flag_tipo_cliente == 2)
       					((TelaElaborarNovoContrato) telaPai).setVendedor1(clienteSelecionado);
       				else if (flag_tipo_cliente == 3)
       					((TelaElaborarNovoContrato) telaPai).setVendedor2(clienteSelecionado);
       				else if (flag_tipo_cliente == 4)
       					((TelaElaborarNovoContrato) telaPai).setCorretor(clienteSelecionado);
       				else if (flag_tipo_cliente == 5)
       					((TelaConfirmarCarregamento) telaPai).setClienteCarregamento(clienteSelecionado);
       				else if (flag_tipo_cliente == 6)
       					((TelaConfirmarCarregamento) telaPai).setVendedor(clienteSelecionado);
       				else if (flag_tipo_cliente == 8)
       					((TelaConfirmarPagamentoContratual) telaPai).setDepositante(clienteSelecionado);
       				else if (flag_tipo_cliente == 9)
       					((TelaConfirmarPagamentoContratual) telaPai).setFavorecido(clienteSelecionado);
       				else if (flag_tipo_cliente == 10)
       					((TelaCadastroGrupo) telaPai).adicionarIntegrante(clienteSelecionado);
       				else if (flag_tipo_cliente == 11)
       					((TelaRelatoriaContratos) telaPai).setClienteAlvo(clienteSelecionado);
       				else if (flag_tipo_cliente == 12)
       					((TelaImportarContratoManual) telaPai).setCorretor(clienteSelecionado);
       				else if (flag_tipo_cliente == 13)
       					((TelaImportarContratoManual) telaPai).setComprador1(clienteSelecionado);
       				else if (flag_tipo_cliente == 14)
       					((TelaImportarContratoManual) telaPai).setVendedor1(clienteSelecionado);
       				else if (flag_tipo_cliente == 15)
       					((TelaImportarContratoManual) telaPai).setVendedor2(clienteSelecionado);
       				else if (flag_tipo_cliente == 16)
       					((TelaConfirmarRecebimento) telaPai).setClienteRecebimento(clienteSelecionado);
       				else if (flag_tipo_cliente == 17)
       					((TelaConfirmarRecebimento) telaPai).setVendedor(clienteSelecionado);
       				else if (flag_tipo_cliente == 18)
       					((TelaElaborarNovoContrato) telaPai).setComprador2(clienteSelecionado);
       				else if (flag_tipo_cliente == 20)
       					((TelaImportarContratoManual) telaPai).setComprador2(clienteSelecionado);


    				isto.dispose();
					}
					else {
						int indiceDaLinha = 0;
						indiceDaLinha = tabela.getSelectedRow();
						System.out.println("Indice da linha selecionado: " + indiceDaLinha);
						//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
						editarCliente(indiceDaLinha);

						
					}
				}
			}
		});
		tabela.setBackground(new Color(255, 255, 255));
	
       
        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(20);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4)
        .setPreferredWidth(120);
        tabela.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                	if(flag_tipo_tela == 0) {
                		int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
    					int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model    
    					clienteSelecionado = clientes_disponiveis.get(indiceDaLinha);
    				if(flag_tipo_cliente == 1)
    					((TelaElaborarNovoContrato) telaPai).setComprador1(clienteSelecionado);
       				else if (flag_tipo_cliente == 2)
       					((TelaElaborarNovoContrato) telaPai).setVendedor1(clienteSelecionado);
       				else if (flag_tipo_cliente == 3)
       					((TelaElaborarNovoContrato) telaPai).setVendedor2(clienteSelecionado);
       				else if (flag_tipo_cliente == 4)
       					((TelaElaborarNovoContrato) telaPai).setCorretor(clienteSelecionado);
       				else if (flag_tipo_cliente == 5)
       					((TelaConfirmarCarregamento) telaPai).setClienteCarregamento(clienteSelecionado);
       				else if (flag_tipo_cliente == 6)
       					((TelaConfirmarCarregamento) telaPai).setVendedor(clienteSelecionado);
       				else if (flag_tipo_cliente == 8)
       					((TelaConfirmarPagamentoContratual) telaPai).setDepositante(clienteSelecionado);
       				else if (flag_tipo_cliente == 9)
       					((TelaConfirmarPagamentoContratual) telaPai).setFavorecido(clienteSelecionado);
       				else if (flag_tipo_cliente == 10)
       					((TelaCadastroGrupo) telaPai).adicionarIntegrante(clienteSelecionado);
       				else if (flag_tipo_cliente == 11)
       					((TelaRelatoriaContratos) telaPai).setClienteAlvo(clienteSelecionado);
       				else if (flag_tipo_cliente == 12)
       					((TelaImportarContratoManual) telaPai).setCorretor(clienteSelecionado);
       				else if (flag_tipo_cliente == 13)
       					((TelaImportarContratoManual) telaPai).setComprador1(clienteSelecionado);
       				else if (flag_tipo_cliente == 14)
       					((TelaImportarContratoManual) telaPai).setVendedor1(clienteSelecionado);
       				else if (flag_tipo_cliente == 15)
       					((TelaImportarContratoManual) telaPai).setVendedor2(clienteSelecionado);
       				else if (flag_tipo_cliente == 16)
       					((TelaConfirmarRecebimento) telaPai).setClienteRecebimento(clienteSelecionado);
       				else if (flag_tipo_cliente == 17)
       					((TelaConfirmarRecebimento) telaPai).setVendedor(clienteSelecionado);
       				else if (flag_tipo_cliente == 18)
       					((TelaElaborarNovoContrato) telaPai).setComprador2(clienteSelecionado);
       				else if (flag_tipo_cliente == 20)
       					((TelaImportarContratoManual) telaPai).setComprador2(clienteSelecionado);


    				isto.dispose();
                	}
                	else {
    					int indiceDaLinha = 0;
    					indiceDaLinha = tabela.getSelectedRow();
    					
    					//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
    					editarCliente(indiceDaLinha);

    					
    				
                    }
                }
				
              }
             });
        
        pesquisar();
        panel.setLayout(null);
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
               // pesquisar(modelo);

        	}
        });
        scrollPane.setBounds(0, 11, 741, 253);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnUsurio = new JButton("+ Cliente");
		btnUsurio.setBounds(636, 276, 105, 33);
		panel.add(btnUsurio);
		btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBounds(517, 276, 109, 33);
		panel.add(btnSelecionar);
		btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		
		JButton btnEditar = new JButton("Gerenciar");
		btnEditar.setBounds(400, 276, 110, 36);
		panel.add(btnEditar);
		btnEditar.setBackground(Color.WHITE);
		btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));
		
		JButton btnVerNotasFiscais = new JButton("Todas as NF's");
		btnVerNotasFiscais.setBounds(283, 281, 108, 28);
		panel.add(btnVerNotasFiscais);
		btnVerNotasFiscais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = tabela.getSelectedRow();
				
				TelaTodasNotasFiscais verNotas = new TelaTodasNotasFiscais(1, 0, isto);
				 verNotas.setVisible(true);
				//
				
			}
		});
		btnVerNotasFiscais.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("Todos os Romaneios");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaRomaneios tela = new TelaRomaneios(0,isto);
				tela.pesquisarTodosOsRomaneios(clientes_disponiveis);
				
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(123, 278, 148, 28);
		panel.add(btnNewButton_1);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				int rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				TelaGerenciarCliente telagerenciar  = new TelaGerenciarCliente(clientes_disponiveis.get(indexRowModel), isto);
				telagerenciar.setVisible(true);
				//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
				//editarCliente(indiceDaLinha);
				
			}
		});
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = -1;
				int indiceDaLinha = -1;
				
				if(flag_tipo_cliente == 10) {
					
				}else {
					 rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
					 indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model				
					clienteSelecionado = clientes_disponiveis.get(indiceDaLinha);
				}
			
				if(flag_tipo_cliente == 1)
					((TelaElaborarNovoContrato) telaPai).setComprador1(clienteSelecionado);
   				else if (flag_tipo_cliente == 2)
   					((TelaElaborarNovoContrato) telaPai).setVendedor1(clienteSelecionado);
   				else if (flag_tipo_cliente == 3)
   					((TelaElaborarNovoContrato) telaPai).setVendedor2(clienteSelecionado);
   				else if (flag_tipo_cliente == 4)
   					((TelaElaborarNovoContrato) telaPai).setCorretor(clienteSelecionado);
   				else if (flag_tipo_cliente == 5)
   					((TelaConfirmarCarregamento) telaPai).setClienteCarregamento(clienteSelecionado);
   				else if (flag_tipo_cliente == 6)
   					((TelaConfirmarCarregamento) telaPai).setVendedor(clienteSelecionado);
   				else if (flag_tipo_cliente == 8)
   					((TelaConfirmarPagamentoContratual) telaPai).setDepositante(clienteSelecionado);
   				else if (flag_tipo_cliente == 9)
   					((TelaConfirmarPagamentoContratual) telaPai).setFavorecido(clienteSelecionado);
   				else if (flag_tipo_cliente == 10) {
   					
   					ArrayList<CadastroCliente> integrantes_selecionados = new ArrayList<>();
   					int linhas_selecionadas[] = tabela.getSelectedRows();//pega o indice da linha na tabela
   					
   					for(int i = 0; i < linhas_selecionadas.length; i++) {
   						
   						int indice = tabela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);//converte pro indice do model				
   	   					clienteSelecionado = clientes_disponiveis.get(indice);
   	   				    integrantes_selecionados.add(clienteSelecionado);
   					}
   					
   					
   					((TelaCadastroGrupo) telaPai).adicionarIntegrantes(integrantes_selecionados);
   					
   				}
   				else if (flag_tipo_cliente == 11)
   					((TelaRelatoriaContratos) telaPai).setClienteAlvo(clienteSelecionado);
   				else if (flag_tipo_cliente == 12)
   					((TelaImportarContratoManual) telaPai).setCorretor(clienteSelecionado);
   				else if (flag_tipo_cliente == 13)
   					((TelaImportarContratoManual) telaPai).setComprador1(clienteSelecionado);
   				else if (flag_tipo_cliente == 14)
   					((TelaImportarContratoManual) telaPai).setVendedor1(clienteSelecionado);
   				else if (flag_tipo_cliente == 15)
   					((TelaImportarContratoManual) telaPai).setVendedor2(clienteSelecionado);
   				else if (flag_tipo_cliente == 16)
   					((TelaConfirmarRecebimento) telaPai).setClienteRecebimento(clienteSelecionado);
   				else if (flag_tipo_cliente == 17)
   					((TelaConfirmarRecebimento) telaPai).setVendedor(clienteSelecionado);
   				else if (flag_tipo_cliente == 18)
   					((TelaElaborarNovoContrato) telaPai).setComprador2(clienteSelecionado);
   				else if (flag_tipo_cliente == 20)
   					((TelaImportarContratoManual) telaPai).setComprador2(clienteSelecionado);
				isto.dispose();
			}
		});
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente novoCliente = new TelaCadastroCliente(1, null, isto);
				novoCliente.setTelaPai(isto);
				novoCliente.setVisible(true);
			}
		});		
		
		 URL url = getClass().getResource("/imagens/pesquisar.png");
	 	ImageIcon img_botao = new ImageIcon(url);
		 
		 entNome = new JTextField();
		 entNome.setBounds(69, 13, 278, 33);
		 painelClientes.add(entNome);
		 entNome.setColumns(10);
		 
		
		 JTabbedPane painelPrincipal = new JTabbedPane();;
		 painelPrincipal.setBounds(26, 23, 768, 511);
		   painelPrincipal.setBackground(new Color(255, 255, 255));
			painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			
		
			painelPrincipal.addTab("Clientes", painelClientes);
			
			JLabel lblNewLabel = new JLabel("Nome:");
			lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblNewLabel.setBounds(22, 20, 37, 17);
			painelClientes.add(lblNewLabel);
			
			JLabel lblApelido = new JLabel("Apelido:");
			lblApelido.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblApelido.setBounds(22, 67, 45, 17);
			painelClientes.add(lblApelido);
			
			entApelido = new JTextField();
			entApelido.setColumns(10);
			entApelido.setBounds(69, 57, 278, 33);
			painelClientes.add(entApelido);
			
			JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
			lblCpfcnpj.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblCpfcnpj.setBounds(360, 22, 71, 17);
			painelClientes.add(lblCpfcnpj);
			
			entCpfCnpj = new JTextField();
			entCpfCnpj.setColumns(10);
			entCpfCnpj.setBounds(435, 13, 277, 33);
			painelClientes.add(entCpfCnpj);
			
			JButton btnFiltrar = new JButton("Filtrar");
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtrar();
				}
			});
			btnFiltrar.setBounds(623, 116, 89, 23);
			painelClientes.add(btnFiltrar);
			
			JButton btnLimpar = new JButton("Limpar");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    sorter.setRowFilter( RowFilter.regexFilter(""));

				}
			});
			btnLimpar.setBounds(524, 118, 89, 23);
			painelClientes.add(btnLimpar);
			
			JLabel lblIe = new JLabel("IE:");
			lblIe.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblIe.setBounds(409, 67, 16, 17);
			painelClientes.add(lblIe);
			
			entIe = new JTextField();
			entIe.setColumns(10);
			entIe.setBounds(435, 61, 277, 33);
			painelClientes.add(entIe);
			
			JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
			btnRefazerPesquisa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pesquisar();
				}
			});
			btnRefazerPesquisa.setBounds(386, 116, 126, 28);
			painelClientes.add(btnRefazerPesquisa);
			painelPrincipal.addTab("Grupos", painelGrupos);
			painelGrupos.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(22, 95, 690, 275);
			painelGrupos.add(panel_1);
			panel_1.setLayout(null);
			
			modelo_grupos.addColumn("Id");
			modelo_grupos.addColumn("Nome");
			modelo_grupos.addColumn("Descrição");
			
			table= new JTable(modelo_grupos);
			
			JScrollPane scrollPaneGrupos = new JScrollPane(table);
			scrollPaneGrupos.setBounds(0, 0, 690, 275);
			panel_1.add(scrollPaneGrupos);
			
			
			
			JButton btnNewButton = new JButton("+Grupo");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TelaCadastroGrupo tela = new TelaCadastroGrupo(0, null, isto);
					tela.setTelaPai(isto);
					tela.setVisible(true);
				}
			});
			btnNewButton.setBounds(607, 392, 69, 28);
			painelGrupos.add(btnNewButton);
			
			JButton btnEditarGrupo = new JButton("Editar");
			btnEditarGrupo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int indiceDaLinha = 0;
					indiceDaLinha = table.getSelectedRow();
					
				   TelaCadastroGrupo tela_edicao_grupo = new TelaCadastroGrupo(1, lista_grupos.get(indiceDaLinha), isto);
				   tela_edicao_grupo.setTelaPai(isto);
				   tela_edicao_grupo.setVisible(true);
					
				}
			});
			btnEditarGrupo.setBounds(441, 392, 60, 28);
			painelGrupos.add(btnEditarGrupo);
			
			JButton btnSelecionarGrupo = new JButton("Selecionar");
			btnSelecionarGrupo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(flag_tipo_tela == 0) {
						int indiceDaLinha = table.getSelectedRow();
	    				CadastroGrupo grupo_selecionado = lista_grupos.get(indiceDaLinha);
	    				((TelaRelatoriaContratos) telaPai).setGrupoAlvo(grupo_selecionado);
	    				isto.dispose();
					}
	    				
				}
			});
			btnSelecionarGrupo.setBounds(513, 392, 87, 28);
			painelGrupos.add(btnSelecionarGrupo);

			contentPane.add(painelPrincipal, BorderLayout.CENTER);
			
			if(flag_tipo_tela == 1)
	   {
		   //em modo cliente
		   btnSelecionar.setEnabled(false);
		   btnSelecionar.setVisible(false);
	   }
	   else
	   {
		   //em modo selecao
		  // btnEditar.setEnabled(false);
		  // btnEditar.setVisible(false);
		   
		  // btnUsurio.setEnabled(false);
		  // btnUsurio.setVisible(false);
		   
	   }
			if(flag_tipo_tela == 0) {
		          //modo selecao
				
			}else {
				btnSelecionar.setEnabled(false);
				btnSelecionar.setVisible(false);
				
				btnSelecionarGrupo.setEnabled(false);
				btnSelecionarGrupo.setVisible(false);
			}
			atualizarTabelaGrupos();
	
			
		this.setLocationRelativeTo(janela_pai);


	}
	
	public void editarCliente( int indiceDaLinha) {
		TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha), isto);
		telaEdicao.setTelaPai(isto);
		telaEdicao.setVisible(true);

	}
	
	
	
	public void atualizaTabela() {
		pesquisar();
	}
	
	public CadastroCliente selecionaCliente()
	{
		return clienteSelecionado;
		
	}
	
	public void setTelaPai(JDialog _telapai) {
		this.telaPai = _telapai;
	}
	
	
	public void atualizarTabelaGrupos() {
		modelo_grupos.setNumRows(0);
	    GerenciarBancoGrupos gerenciar = new GerenciarBancoGrupos();
	    lista_grupos.clear();
	    lista_grupos = gerenciar.getGrupos();
	 
	    for (CadastroGrupo grupo : lista_grupos) {
	    	
	   
	    	modelo_grupos.addRow(new Object[]{grupo.getId_grupo(),grupo.getNome_grupo(),grupo.getDescricao_grupo()});

	    	
	    }
	}
	
	public static class ClienteTableModel extends AbstractTableModel{
		 

	    private final int id=0;
	    private final int ie=1;
	    private final int apelido=2;
	    private final int cpf_cnpj=3;
	    private final int nome=4;
	  
	 
	    private final String colunas[]={"ID:","IE:","Apelido:","CPF/CNPJ:","Nome:"};
	    private final ArrayList<CadastroCliente> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public ClienteTableModel() {
	        
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
	        case ie:
	            return String.class;
	        case apelido:
	            return String.class;
	        case cpf_cnpj:
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
	    	CadastroCliente nota=dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case id:
	            return nota.getId();
	        case ie:
	            return nota.getIe();
	        case apelido:
	            return nota.getApelido().toUpperCase();
	        case cpf_cnpj:{
	        	if(nota.getTipo_pessoa() == 0)
	            return nota.getCpf();
	        	else
		            return nota.getCnpj();

	            
	        }
	        case nome:
	        	if(nota.getTipo_pessoa() == 0)
		            return nota.getNome_empresarial().toUpperCase();
		        	else
			            return nota.getNome_fantaia().toUpperCase();
	       
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
	    	CadastroCliente nota=dados.get(rowIndex);
	 
	      
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
	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		    String nome = entNome.getText().toUpperCase();
		    String apelido = entApelido.getText().toUpperCase();
		    String ie =  entIe.getText().toUpperCase();
		    String cpnf_cnpj = entCpfCnpj.getText().toUpperCase();

		   
		    if(checkString(ie))
		    filters.add(RowFilter.regexFilter(ie, 1));
		    
		    if(checkString(apelido))
		    filters.add(RowFilter.regexFilter(apelido, 2));

		    if(checkString(cpnf_cnpj))
		    filters.add(RowFilter.regexFilter(cpnf_cnpj, 3));
		    
		    if(checkString(nome))
			    filters.add(RowFilter.regexFilter(nome, 4));
		  
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
	}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
}
