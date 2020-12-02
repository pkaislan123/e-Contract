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

import cadastros.CadastroProduto;
import cadastros.CadastroSafra;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoSafras;

public class TelaSafra extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private DefaultTableModel modelo = new DefaultTableModel();
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	
	
	public static void pesquisarSafras(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
    safras = listaSafras.getSafras();
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Ano Plantio/Ano Colheita");
       
     */
    for (CadastroSafra safra : safras) {
    	
    	int id_safra, ano_plantio, ano_colheita;
    	String nome_produto;
    	
    	id_safra = safra.getId_safra();
    	ano_plantio = safra.getAno_plantio();
    	ano_colheita = safra.getAno_colheita();
    	nome_produto = safra.getProduto().getNome_produto();
    	
            modelo.addRow(new Object[]{Integer.toString(id_safra), nome_produto, Integer.toString(ano_plantio) + "/" + Integer.toString(ano_colheita),});

    	}
    }
    
		

	public TelaSafra() {
		setModal(true);

		TelaSafra isto = this;
		
		setResizable(false);
		setTitle("E-Contract -Safras");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnsafra = new JButton("+Safra");
		btnsafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			TelaCadastroSafra tela = new TelaCadastroSafra();
			}
		});
		btnsafra.setBounds(537, 70, 89, 23);
		painelPrincipal.add(btnsafra);
		
		

		JPanel panel = new JPanel();
		panel.setBounds(10, 106, 653, 266);
		painelPrincipal.add(panel);
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Ano Plantio/Ano Colheita");

        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(120);
       
        pesquisarSafras(modelo);
        panel.setLayout(null);
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		pesquisarSafras(modelo);

        	}
        });
        scrollPane.setBounds(10, 11, 633, 244);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}

}
