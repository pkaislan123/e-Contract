package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoLogin;
import outros.DadosGlobais;
import tratamento_proprio.Log;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class TelaUsuarios extends JDialog {

	private final JPanel contentPanel ;
    private static ArrayList<CadastroLogin> usuarios_pesquisados = new ArrayList<>();
	private static ArrayList<CadastroLogin> usuarios_disponiveis = new ArrayList<>();
    private CadastroLogin login;
	private CadastroLogin usuarioSelecionado;
	private Log GerenciadorLog;
	 DefaultTableModel modelo = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
     public static void pesquisar(DefaultTableModel modelo)
 	{ 
 		modelo.setNumRows(0);
     GerenciarBancoLogin listaUsuarios = new GerenciarBancoLogin();
     usuarios_pesquisados = listaUsuarios.getUsuarios();
     usuarios_disponiveis.clear();
     
     /*
      * modelo.addColumn("Id");
         modelo.addColumn("IE");
         modelo.addColumn("Apelido");
         modelo.addColumn("CPF/CNPJ");
         modelo.addColumn("Nome");
        
      */
     for (CadastroLogin usuario : listaUsuarios.getUsuarios()) {
     	String nome, email, login, senha;
      	int direitos, id;
     
     	
     	    id = usuario.getId();
     	    login = usuario.getLogin();
     	    nome = usuario.getNome();
     	    email = usuario.getEmail();
     	    senha = usuario.getSenha();
     
             modelo.addRow(new Object[]{id, login, nome, email});

     
             usuarios_disponiveis.add(usuario);
             }
     
     }

	public TelaUsuarios() {
		setAlwaysOnTop(true);

		//setModal(true);
		
		getDadosGlobais();
		
		TelaUsuarios isto = this;
		setBounds(100, 100, 679, 508);
		setTitle("Usuários");
		getContentPane().setLayout(new BorderLayout());
		
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 67, 653, 305);
		panel.setLayout(null);
		contentPanel.add(panel);
		
		
		
		JTable tabela = new JTable(modelo);
		
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
        modelo.addColumn("Login");
        modelo.addColumn("Nome");
        modelo.addColumn("E-mail");
       
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
        scrollPane.setBounds(10, 11, 633, 283);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnUsurio = new JButton("+ Usuário");
		btnUsurio.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/add_usuario.png")));
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(login.getConfigs_privilegios().getNivel_privilegios() != 1) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				}else {
					TelaCadastroUsuario cadastrarUsuario = new TelaCadastroUsuario(0, null);
				}
			}
		});
		btnUsurio.setBounds(526, 426, 120, 23);
		contentPanel.add(btnUsurio);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = 0;
				indiceDaLinha = tabela.getSelectedRow();
				
	
				
				if(login.getConfigs_privilegios().getNivel_privilegios() != 1) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				}else {
					TelaCadastroUsuario cadastrarUsuario = new TelaCadastroUsuario(1, usuarios_disponiveis.get(indiceDaLinha));
				}
			}
		});
		btnEditar.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/editar.png")));
		btnEditar.setBounds(427, 426, 89, 23);
		contentPanel.add(btnEditar);
		
		
		
		
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setVisible(true);

	}
	
	
	public void getDadosGlobais() {
		//gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		 GerenciadorLog = dados.getGerenciadorLog();
		 
		 //usuario logado
		  login = dados.getLogin();
	}

}
