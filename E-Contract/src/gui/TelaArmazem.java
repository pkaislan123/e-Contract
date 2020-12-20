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
import conexaoBanco.GerenciarBancoClientes;

public class TelaArmazem extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	private DefaultTableModel modelo = new DefaultTableModel();
	private static ArrayList<CadastroCliente> armazens = new ArrayList<>();
	private static ArrayList<CadastroCliente> armazens_disponiveis = new ArrayList<>();

	
	private CadastroCliente clienteSelecionado;
	
	public static void pesquisar(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
		armazens_disponiveis.clear();
    GerenciarBancoClientes listaArmazens = new GerenciarBancoClientes();
    armazens = listaArmazens.getClientes(-1, -1, null);
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
     */
    for (CadastroCliente cliente : listaArmazens.getClientes(-1, -1, null)) {
    	String cpf, cnpj, nome;
     	
    if(cliente.getArmazem() == 0)	
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
    	armazens_disponiveis.add(cliente);
    }
    }
		
	}
	public TelaArmazem() {
		setModal(true);

		TelaArmazem isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Armazéns");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnarmazm = new JButton("+Armazém");
		btnarmazm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			TelaCadastroCliente tela = new TelaCadastroCliente(5,  null, null);
			}
		});
		btnarmazm.setBounds(519, 68, 89, 23);
		painelPrincipal.add(btnarmazm);
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 106, 653, 266);
		painelPrincipal.add(panel);
		//panel.setLayout(null);
	
		
		
		JTable tabela = new JTable(modelo);
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
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela.getSelectedRow();

				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(6, armazens_disponiveis.get(indiceDaLinha), null);

			}
		});
		btnEditar.setBounds(574, 400, 89, 23);
		painelPrincipal.add(btnEditar);
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
}
