package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cadastros.CadastroCliente;
import conexaoBanco.GerenciarBancoClientes;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import gui.TelaElaborarNovoContrato;
import outros.JButtonBackground;
import outros.JPanelBackground;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class TelaCliente extends JDialog {

	private JDialog telaPai;
	private JPanel contentPane;
	private JComboBox cBOrdernar;
	 DefaultTableModel modelo = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
    private static ArrayList<CadastroCliente> clientes_pesquisados = new ArrayList<>();
	private static ArrayList<CadastroCliente> clientes_disponiveis = new ArrayList<>();

	private CadastroCliente clienteSelecionado;
	private JTextField entPesquisa;
	
	private TelaCliente isto;
	
	public static void pesquisar(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
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
    	if(cliente.getTipo_pessoa() == 1)
    	{	//cnpj
    	    cnpj = cliente.getCnpj();
    	    nome = cliente.getRazao_social();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getIe(), cliente.getApelido(), cnpj, nome});

    	}
    	else
    	{
    		cpf = cliente.getCpf();
    		nome = cliente.getNome() + " " + cliente.getSobrenome();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getIe(), cliente.getApelido(), cpf, nome});

    	}
    	clientes_disponiveis.add(cliente);
    }
    }
		
	}
	
	
	public static void pesquisarPersonalizado(DefaultTableModel modelo,int tipoBuscar, int ordem, String campo_busca)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
    clientes_pesquisados = listaClientes.getClientes(tipoBuscar, ordem, campo_busca);
    clientes_disponiveis.clear();
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
     */
    for (CadastroCliente cliente : listaClientes.getClientes(tipoBuscar, ordem, campo_busca)) {
    	String cpf, cnpj, nome;
     	
    if(cliente.getArmazem() == 1)	
    {
    	
    }else {
    	if(cliente.getTipo_pessoa() == 1)
    	{	//cnpj
    	    cnpj = cliente.getCnpj();
    	    nome = cliente.getRazao_social();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getIe(), cliente.getApelido(), cnpj, nome});

    	}
    	else
    	{
    		cpf = cliente.getCpf();
    		nome = cliente.getNome() + " " + cliente.getSobrenome();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getIe(), cliente.getApelido(), cpf, nome});

    	}
    	clientes_disponiveis.add(cliente);
    }
    }
		
	}
	
	public TelaCliente(int flag_tipo_tela, int flag_tipo_cliente) {
		
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
		setBounds(100, 100, 679, 508);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JButton btnUsurio = new JButton("+ Cliente");
		btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente novoCliente = new TelaCadastroCliente(1, null);
				novoCliente.setTelaPai(isto);
				novoCliente.setVisible(true);
			}
		});		btnUsurio.setBounds(543, 389, 120, 23);
		contentPane.add(btnUsurio);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 106, 653, 266);
		contentPane.add(panel);
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelo);
		tabela.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(flag_tipo_tela == 0) {
					System.out.println("Valor do Enter: " + arg0.getKeyCode());
					int indiceDaLinha = tabela.getSelectedRow();
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
		modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(120);
        tabela.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                	if(flag_tipo_tela == 0) {
                	int indiceDaLinha = tabela.getSelectedRow();
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
        
        pesquisar(modelo);
        panel.setLayout(null);
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
                pesquisar(modelo);

        	}
        });
        scrollPane.setBounds(10, 11, 633, 244);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(Color.WHITE);
		btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = 0;
				indiceDaLinha = tabela.getSelectedRow();
				
				
				
				//TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
				editarCliente(indiceDaLinha);
				
			}
		});
		btnEditar.setBounds(403, 389, 121, 23);
		contentPane.add(btnEditar);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();
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


				isto.dispose();
			}
		});
		btnSelecionar.setBounds(403, 389, 121, 23);
		contentPane.add(btnSelecionar);
		
		 cBOrdernar = new JComboBox();
		cBOrdernar.setBounds(495, 62, 114, 33);
		contentPane.add(cBOrdernar);
		cBOrdernar.addItem("Id");
		cBOrdernar.addItem("IE");
		cBOrdernar.addItem("Apelido");
		cBOrdernar.addItem("CPF/CNPJ");
		cBOrdernar.addItem("Nome");
		
		entPesquisa = new JTextField();
		entPesquisa.setBounds(10, 62, 486, 33);
		contentPane.add(entPesquisa);
		entPesquisa.setColumns(10);
		
		 URL url = getClass().getResource("/imagens/pesquisar.png");
	 	ImageIcon img_botao = new ImageIcon(url);
		
	 	JButtonBackground btnPesquisar = new JButtonBackground();
	 	btnPesquisar.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent arg0) {
	 			pesquisar();
	 		}
	 	});
		
	 	btnPesquisar.setBounds(609, 62, 54, 33);
		btnPesquisar.setBorder(null);
		btnPesquisar.setImg(img_botao);
		//btnPesquisar.repaint();

		contentPane.add(btnPesquisar);
		
		JLabel label = new JLabel("");
		btnPesquisar.add(label);
		
		JLabel lblNewLabel = new JLabel("Ordernar por:");
		lblNewLabel.setBounds(495, 43, 114, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnVerNotasFiscais = new JButton("Acessar NF's");
		btnVerNotasFiscais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = tabela.getSelectedRow();
				
				TelaNotasFiscais verNotas = new TelaNotasFiscais(1, clientes_disponiveis.get(indiceDaLinha));
				 verNotas.setVisible(true);
				//
				
			}
		});
		btnVerNotasFiscais.setBackground(Color.WHITE);
		btnVerNotasFiscais.setBounds(272, 389, 121, 23);
		contentPane.add(btnVerNotasFiscais);
		
	   if(flag_tipo_tela == 1)
	   {
		   //em modo cliente
		   btnSelecionar.setEnabled(false);
		   btnSelecionar.setVisible(false);
	   }
	   else
	   {
		   //em modo selecao
		   btnEditar.setEnabled(false);
		   btnEditar.setVisible(false);
		   
		   btnUsurio.setEnabled(false);
		   btnUsurio.setVisible(false);
		   
	   }
	
	
		
		
		this.setLocationRelativeTo(null);





	}
	
	public void pesquisar() {
		String campo_busca, ordem;
		int id_ordem = -1;
		
		campo_busca = entPesquisa.getText();
		ordem = cBOrdernar.getSelectedItem().toString();
	
		
		if(ordem.equals("Id")) {
			id_ordem = 1;
		}
		if(ordem.equals("Nome")) {
			id_ordem = 2;
		}
		
		if(ordem.equals("IE")) {
			id_ordem = 3;
		}
		if(ordem.equals("Apelido")) {
			id_ordem = 4;
		}
		if(ordem.equals("CPF/CNPJ")) {
			id_ordem = 5;
		}
		
		if(campo_busca != null && campo_busca != "" && campo_busca != " " && campo_busca.length() > 2)
		pesquisarPersonalizado( modelo,1, id_ordem, campo_busca );

		
	}
	
	public void editarCliente( int indiceDaLinha) {
		TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha));
		telaEdicao.setTelaPai(isto);
		telaEdicao.setVisible(true);

	}
	
	
	
	public void atualizaTabela() {
		pesquisar(modelo);
	}
	
	public CadastroCliente selecionaCliente()
	{
		return clienteSelecionado;
		
	}
	
	public void setTelaPai(JDialog _telapai) {
		this.telaPai = _telapai;
	}
	
}
