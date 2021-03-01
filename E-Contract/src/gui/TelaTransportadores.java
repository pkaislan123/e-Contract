package gui;

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

import cadastros.CadastroCliente;
import conexaoBanco.GerenciarBancoClientes;

public class TelaTransportadores extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	private DefaultTableModel modelo = new DefaultTableModel();
	private static ArrayList<CadastroCliente> transportadores = new ArrayList<>();
	private static ArrayList<CadastroCliente> transportadores_disponiveis = new ArrayList<>();
	private JDialog telaPai;

	
	private CadastroCliente clienteSelecionado;
	
	public static void pesquisar(DefaultTableModel modelo)
	{ 
		transportadores_disponiveis.clear();
		modelo.setNumRows(0);
    GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
    transportadores = gerenciar.getClientes(-1, -1, null);
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
     */
    for (CadastroCliente cliente : gerenciar.getClientes(-1, -1, null)) {
    	String cpf, cnpj, nome;
     	
    if(cliente.getTransportador() == 0)	
    {
    	
    }else {
    	if(cliente.getTipo_pessoa() == 1)
    	{	//cnpj
    	    cnpj = cliente.getCnpj();
    	    nome = cliente.getRazao_social();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getRntrc(), cliente.getApelido(), cnpj, nome});

    	}
    	else
    	{
    		cpf = cliente.getCpf();
    		nome = cliente.getNome() + " " + cliente.getSobrenome();
            modelo.addRow(new Object[]{cliente.getId(),cliente.getRntrc(), cliente.getApelido(), cpf, nome});

    	}
    	transportadores_disponiveis.add(cliente);
    }
    }
		
	}
	
	private TelaTransportadores isto ;
	public TelaTransportadores(int flag_operacao, Window janela_pai) {
		//setAlwaysOnTop(true);

		//setModal(true);
		 isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Transportadores");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 892, 624);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnarmazm = new JButton("+Transportador");
		btnarmazm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				TelaCadastroTransportadores tela = new TelaCadastroTransportadores(0,  null, null, isto);
			}
		});
		btnarmazm.setBounds(749, 517, 113, 28);
		painelPrincipal.add(btnarmazm);
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 145, 852, 343);
		painelPrincipal.add(panel);
		//panel.setLayout(null);
	
		
		
		JTable tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
        modelo.addColumn("RNTRC");
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
        scrollPane.setBounds(10, 0, 842, 343);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela.getSelectedRow();

				TelaCadastroTransportadores telaEdicao = new TelaCadastroTransportadores(1, transportadores_disponiveis.get(indiceDaLinha), null, isto);

			}
		});
		btnEditar.setBounds(650, 517, 89, 23);
		painelPrincipal.add(btnEditar);
		
		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();
				
				if(telaPai instanceof TelaConfirmarCarregamento) {
				((TelaConfirmarCarregamento) telaPai).setTransportador(transportadores_disponiveis.get(indiceDaLinha));
				}else if(telaPai instanceof TelaConfirmarRecebimento) {
					((TelaConfirmarRecebimento) telaPai).setTransportador(transportadores_disponiveis.get(indiceDaLinha));

				}
				isto.dispose();
			}
		});
		btnSelecionarTransportador.setBounds(551, 517, 89, 23);
		painelPrincipal.add(btnSelecionarTransportador);
		
		
		if(flag_operacao != 2) {
			btnSelecionarTransportador.setVisible(false);
			btnSelecionarTransportador.setEnabled(false);
			
		} else {
			btnSelecionarTransportador.setVisible(true);
			btnSelecionarTransportador.setEnabled(true);
		}
		
		this.setLocationRelativeTo(janela_pai);

		//this.setVisible(true);
		
		
	}
	
	
	public void setTelaPai(JDialog telaPai) {
		
		this.telaPai = telaPai;
		
	}
	
}
