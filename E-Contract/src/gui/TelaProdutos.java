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
import cadastros.CadastroProduto;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoProdutos;

public class TelaProdutos extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	private DefaultTableModel modelo = new DefaultTableModel();
	private static ArrayList<CadastroProduto> produtos = new ArrayList<>();
	
	
	public static void pesquisarProdutos(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
    produtos = listaProdutos.getProdutos();
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Descrição");
       
     */
    for (CadastroProduto produto : produtos) {
    	String  nome_produto, descricao;
     	int id;
   
    	
    		id = produto.getId_produto();
    		nome_produto = produto.getNome_produto();
    		descricao = produto.getDescricao_produto();
            modelo.addRow(new Object[]{id, nome_produto, descricao});

    	}
    }
    
		
	
	public TelaProdutos() {
		setModal(true);

		TelaProdutos isto = this;
		
		setResizable(false);
		setTitle("E-Contract - produtos");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 106, 653, 266);
		painelPrincipal.add(panel);
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Descrição");

        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(120);
       
        pesquisarProdutos(modelo);
        panel.setLayout(null);
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		pesquisarProdutos(modelo);

        	}
        });
        scrollPane.setBounds(10, 11, 633, 244);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnProduto = new JButton("+ Produto");
		btnProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroProduto tela = new TelaCadastroProduto();
			}
		});
		btnProduto.setBounds(554, 55, 89, 23);
		painelPrincipal.add(btnProduto);
		
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
}
