package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;
import javax.swing.ScrollPaneConstants;

public class TelaContratos extends JDialog {

    private static ArrayList<CadastroContrato> lista_contratos = new ArrayList<>();

	private final JPanel painelPrincipal = new JPanel();
	DefaultTableModel modelo = new DefaultTableModel(){
        public boolean isCellEditable(int linha, int coluna) {  
            return false;
        }  
    };
    

	public TelaContratos() {
		setModal(true);

		TelaContratos isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Contratos");

		

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 933, 632);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(0, null, 0);
			}
		});
		//btnContrato.setIcon(new ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");
		btnContrato.setBounds(730, 82, 172, 33);
		painelPrincipal.add(btnContrato);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(30, 126, 872, 266);
		painelPrincipal.add(panel);
		
        JTable tabela = new JTable(modelo);
		
		tabela.setBackground(new Color(255, 255, 255));
		//tabela.setPreferredSize(new Dimension(0, 200)); 
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.addColumn("ID");
		modelo.addColumn("Código");
		 modelo.addColumn("Status");

        modelo.addColumn("Quantidade");
        modelo.addColumn("Medida");
        modelo.addColumn("Produto");
        modelo.addColumn("Safra");
        modelo.addColumn("Valor Produto");
        modelo.addColumn("Valor Total");
        modelo.addColumn("Compradores");

        modelo.addColumn("Vendedores");
        modelo.addColumn("Corretores");
        modelo.addColumn("Data do Contrato");
       

        pesquisar(modelo);

        
      
        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(90);
        tabela.getColumnModel().getColumn(2)
        .setPreferredWidth(170);
        tabela.getColumnModel().getColumn(3)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(4)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5)
        .setPreferredWidth(70);
        tabela.getColumnModel().getColumn(6)
        .setPreferredWidth(70);
        tabela.getColumnModel().getColumn(7)
        .setPreferredWidth(90);
        tabela.getColumnModel().getColumn(8)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(9)
        .setPreferredWidth(150);
        tabela.getColumnModel().getColumn(10)
        .setPreferredWidth(150);
        tabela.getColumnModel().getColumn(11)
        .setPreferredWidth(100);
        tabela.getColumnModel().getColumn(12)
        .setPreferredWidth(80);
        
       
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(10, 11, 852, 244);
		
		panel.add(scrollPane);
		
		
		
		JButton btnSelecionar = new JButton("Abrir");
		btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();
				
				

				int id_contrato = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar.getContrato(id_contrato);
				
				
				System.out.println("Produto: " + contrato_selecionado.getModelo_safra().getProduto().getNome_produto());
				CadastroCliente corretores_contrato_selecionado []= contrato_selecionado.getCorretores();
				CadastroCliente compradores_contrato_selecionado []= contrato_selecionado.getCompradores();
				CadastroCliente vendedores_contrato_selecionado []= contrato_selecionado.getVendedores();

				
				for(CadastroCliente corretor : corretores_contrato_selecionado) {
					if(corretor != null)
					System.out.println("Nome do corretor : " + corretor.getNome_empresarial() + " outro nome: " + corretor.getNome_fantaia());
				}
				
				for(CadastroCliente comprador : compradores_contrato_selecionado) {
					if(comprador != null)
					System.out.println("Nome do comprador : " + comprador.getNome_empresarial() + " outro nome: " + comprador.getNome_fantaia());
				}
				
				for(CadastroCliente vendedor : vendedores_contrato_selecionado) {
					if(vendedor != null)
					System.out.println("Nome do vendedor : " + vendedor.getNome_empresarial() + " outro nome: " + vendedor.getNome_fantaia());
				}
				
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado);
			}
		});
		btnSelecionar.setBounds(781, 439, 121, 23);
		getContentPane().add(btnSelecionar);
		
				
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
	
		public static void pesquisar(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
    lista_contratos.clear();

      
    
   
    for (CadastroContrato contrato : gerenciar.getContratos()) {
    	String cpf, cnpj, nome;
   

		int status = contrato.getStatus_contrato();
		String text_status = "";
		if(status== 1) {
			text_status=  "Recolher Assinaturas".toUpperCase();

		}
		else if(status == 2) {
			text_status = "Assinado".toUpperCase();

		}
		else if(status == 3) {
			text_status = "Cumprindo".toUpperCase();

		}
		
		/*
		    * 	modelo.addColumn("ID");
		modelo.addColumn("Código");
		 modelo.addColumn("Status");

        modelo.addColumn("Quantidade");
        modelo.addColumn("Medida");
        modelo.addColumn("Produto");
        modelo.addColumn("Safra");
        modelo.addColumn("Valor Produto");
        modelo.addColumn("Valor Total");
        modelo.addColumn("Vendedores");
        modelo.addColumn("Compradores");
        modelo.addColumn("Corretores");
        modelo.addColumn("Data do Contrato");

		    */
		
		if(contrato.getSub_contrato() == 0) {
			
			 modelo.addRow(new Object[]{contrato.getId(), contrato.getCodigo(), text_status, contrato.getQuantidade(), 
	            		contrato.getMedida().toUpperCase(),
	            contrato.getProduto().toUpperCase(),
	            contrato.getModelo_safra().getAno_colheita() + "/" +  contrato.getModelo_safra().getAno_plantio(),
	             "R$ " + contrato.getValor_produto(), 
	            "R$ " + contrato.getValor_a_pagar(), 
	            contrato.getNomes_compradores(), contrato.getNomes_vendedores(), contrato.getNomes_corretores(),
	            contrato.getData_contrato()
	            
	            });
	            lista_contratos.add(contrato);
	    	}
			
		}
    	
           
    
    
        
	}
}
