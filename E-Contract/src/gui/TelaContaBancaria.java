package gui;

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
import javax.swing.table.DefaultTableModel;

import cadastros.CadastroCliente;
import cadastros.ContaBancaria;
import conexaoBanco.GerenciarBancoClientes;



public class TelaContaBancaria extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JPanel painel_table_cb;
	private JTable table_cb;
	private DefaultTableModel modelo_cb = new DefaultTableModel();
	
	private ContaBancaria contaSelecionada;
	
	private static ArrayList<ContaBancaria> contas_bancarias = new ArrayList<>();
	private JButton btnSelecionar;
	 
	 
	 
	 public static void pesquisar_contas(DefaultTableModel modelo_cb)
		{ 
			modelo_cb.setNumRows(0);
	    GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
	    contas_bancarias = listaClientes.getContas();
	    
	    
	    for(ContaBancaria conta_bancaria : contas_bancarias)
		 {
		 
		 String cpf, banco, codigo, agencia, conta, id, nome;
			
		    id = Integer.toString(conta_bancaria.getId_conta());
			cpf = conta_bancaria.getCpf_titular();
			nome = conta_bancaria.getNome();
			banco = conta_bancaria.getBanco();
			codigo = conta_bancaria.getCodigo();
			agencia = conta_bancaria.getAgencia();
			conta = conta_bancaria.getConta();

			modelo_cb.addRow(new Object[]{id, cpf,nome, banco, 
				       codigo, agencia, conta});
		 }
	    
	    
	   
	    }

	public TelaContaBancaria(TelaNovoContratoInformal telaContrato)  {
		setModal(true);

		TelaContaBancaria isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Conta Bancaria");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		 painel_table_cb = new JPanel();
		 painel_table_cb.setBounds(10, 61, 642, 260);
		 
		 table_cb = new JTable(modelo_cb);
		 table_cb.setBackground(new Color(255, 255, 255));
		 
		 modelo_cb.addColumn("Id");
		    modelo_cb.addColumn("CPF");
		    modelo_cb.addColumn("Nome");

		    modelo_cb.addColumn("Banco");


		    modelo_cb.addColumn("Codigo");
			modelo_cb.addColumn("Agência");
            
			modelo_cb.addColumn("Conta");
	      
			table_cb.getColumnModel().getColumn(0)
	        .setPreferredWidth(20);
			   table_cb.getColumnModel().getColumn(2)
		        .setPreferredWidth(100);
	        table_cb.getColumnModel().getColumn(1)
	        .setPreferredWidth(20);
	        table_cb.getColumnModel().getColumn(3)
	        .setPreferredWidth(130);
	        table_cb.getColumnModel().getColumn(4)
	        .setPreferredWidth(30);
	        table_cb.getColumnModel().getColumn(5)
	        .setPreferredWidth(30);
	        table_cb.getColumnModel().getColumn(6)
	        .setPreferredWidth(100);
	     
	    
	        painel_table_cb.setLayout(null);
	    	modelo_cb.setNumRows(0);
	        JScrollPane scrollPaneCB = new JScrollPane(table_cb);
	        scrollPaneCB.setBounds(10, 5, 610, 244);
	        scrollPaneCB.setAutoscrolls(true);
	        scrollPaneCB.setBackground(new Color(255, 255, 255));
	        painel_table_cb.add(scrollPaneCB);
			
		
	        painelPrincipal.add(painel_table_cb);
	        
	        btnSelecionar = new JButton("Selecionar");
	        btnSelecionar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		int indiceDaLinha = table_cb.getSelectedRow();
	        		contaSelecionada = contas_bancarias.get(indiceDaLinha);
					telaContrato.setContaBancaria(contaSelecionada);
				

					isto.dispose();
	        	}
	        });
	        btnSelecionar.setBounds(563, 401, 89, 23);
	        painelPrincipal.add(btnSelecionar);
		
	        
	        pesquisar_contas(modelo_cb);
	        
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}

}
