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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
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
		setBounds(100, 100, 735, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato();
			}
		});
		//btnContrato.setIcon(new ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");
		btnContrato.setBounds(547, 67, 172, 33);
		painelPrincipal.add(btnContrato);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(30, 126, 653, 266);
		painelPrincipal.add(panel);
		
        JTable tabela = new JTable(modelo);
		
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("ID");
		modelo.addColumn("Código");
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
        modelo.addColumn("Status");


        pesquisar(modelo);

        
       for(int i = 0; i <=12; i++) {
        tabela.getColumnModel().getColumn(i)
        .setPreferredWidth(150);
       }
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(10, 11, 633, 244);
		
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
		btnSelecionar.setBounds(562, 428, 121, 23);
		getContentPane().add(btnSelecionar);
		
		
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
	
	
	public static void pesquisar(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
    lista_contratos.clear();

      
    
   /*
    * 	modelo.addColumn("Código");
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
        modelo.addColumn("Status");

    */
    for (CadastroContrato contrato : gerenciar.getContratos()) {
    	String cpf, cnpj, nome;
   
   
            modelo.addRow(new Object[]{contrato.getId(), contrato.getCodigo()});
            lista_contratos.add(contrato);
    	}
    
    
        
	}
}
