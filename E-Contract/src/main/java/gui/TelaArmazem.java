package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
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

import main.java.cadastros.CadastroCliente;
import main.java.conexaoBanco.GerenciarBancoClientes;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;


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
	public TelaArmazem(int flag_retorno, Window janela_pai) {

		//setModal(true);

		TelaArmazem isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Armazéns");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[653px][][][]", "[23px][grow][23px]"));
		
		
		
		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 1 4 1,grow");
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
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
                pesquisar(modelo);

        	}
        });
        panel.setLayout(new BorderLayout(0, 0));
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(0, 0, 255));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela.getSelectedRow();

				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(6, armazens_disponiveis.get(indiceDaLinha), isto);
				telaEdicao.setTelaPai(isto);
				telaEdicao.setVisible(true);

			}
		});
		painelPrincipal.add(btnEditar, "cell 0 2,alignx right,aligny top");
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(flag_retorno == 1) {
					int indice = tabela.getSelectedRow();
					((TelaSiloCadastroSilo) janela_pai).setArmazem(armazens_disponiveis.get(indice));
					isto.dispose();
				}
				
			}
		});
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionar.setBackground(new Color(51, 51, 0));
		painelPrincipal.add(btnSelecionar, "cell 2 2");
		
		JButton btnarmazm = new JButton("+Armazém");
		btnarmazm.setBackground(new Color(0, 51, 51));
		btnarmazm.setForeground(Color.WHITE);
		btnarmazm.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnarmazm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			TelaCadastroCliente tela = new TelaCadastroCliente(5,  null, isto);
			tela.setTelaPai(isto);
			tela.setVisible(true);
			}
		});
		painelPrincipal.add(btnarmazm, "cell 3 2,alignx right,aligny top");
		
		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);
		
		
	}
	
	public void atualizaTabela() {
		pesquisar(modelo);
	}
	
}
