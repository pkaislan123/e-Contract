package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroGrupo;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoGrupos;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JOptionPane;


public class TelaCadastroGrupo extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entNomeGrupo;
	private JLabel lblCodigo ;
	private JTable table;
	private JTable table_1;
    private JTextArea textAreaDescricaoGrupo ;
	private ArrayList<CadastroCliente> integrantes = new ArrayList<>();
	private JDialog telaPai;
	private CadastroGrupo grupo_global;
	 DefaultTableModel modelo = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };

	public TelaCadastroGrupo(int flag_modo_tela, CadastroGrupo grupo, Window janela_pai) {
		//setModal(true);

		TelaCadastroGrupo isto = this;
		
		setResizable(true);
		
		if(grupo != null)
			grupo_global = grupo;
		
		if(flag_modo_tela == 0)
		 setTitle("E-Contract - Cadastro Grupo");
		else 
		 setTitle("E-Contract - Edicao Grupo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 582, 553);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Codigo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 76, 24);
		painelPrincipal.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(20, 52, 76, 24);
		painelPrincipal.add(lblNome);
		
		 lblCodigo = new JLabel("");
		lblCodigo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(82, 11, 139, 21);
		painelPrincipal.add(lblCodigo);
		
		entNomeGrupo = new JTextField();
		entNomeGrupo.setBounds(82, 50, 420, 32);
		painelPrincipal.add(entNomeGrupo);
		entNomeGrupo.setColumns(10);
		
		JLabel lblIntegrantes = new JLabel("Integrantes:");
		lblIntegrantes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIntegrantes.setBounds(10, 187, 106, 24);
		painelPrincipal.add(lblIntegrantes);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescrio.setBounds(10, 93, 76, 24);
		painelPrincipal.add(lblDescrio);
		
		 textAreaDescricaoGrupo = new JTextArea();
		textAreaDescricaoGrupo.setLineWrap(true);
		textAreaDescricaoGrupo.setWrapStyleWord(true);
		textAreaDescricaoGrupo.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaDescricaoGrupo.setBounds(81, 95, 421, 81);
		painelPrincipal.add(textAreaDescricaoGrupo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(82, 220, 420, 221);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 420, 175);
		panel_1.add(panel);
		panel.setLayout(null);
		
		modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
       
		
		table = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 420, 175);
		panel.add(scrollPane);
		
		
		
		JButton btnAdicionarIntegrante = new JButton("+Integrante");
		btnAdicionarIntegrante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0,10, null);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnAdicionarIntegrante.setBounds(309, 186, 111, 23);
		panel_1.add(btnAdicionarIntegrante);
		
		JButton btnExcluirIntegrante = new JButton("Excluir");
		btnExcluirIntegrante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int indiceDaLinha = table.getSelectedRow();
				CadastroCliente clienteSelecionado = integrantes.get(indiceDaLinha);
				integrantes.remove(clienteSelecionado);
				modelo.removeRow(indiceDaLinha);
			}
		});
		btnExcluirIntegrante.setBounds(200, 186, 89, 23);
		panel_1.add(btnExcluirIntegrante);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				if(flag_modo_tela == 0) {
				String nome_grupo = entNomeGrupo.getText();
				
				if(nome_grupo == null || nome_grupo.equals(" ") || nome_grupo.equals("") || nome_grupo.length() < 5) {
					JOptionPane.showMessageDialog(null, "Nome de Grupo Invalido!");
				}else {
					
					String descricao = textAreaDescricaoGrupo.getText();
					String membros = "";
					
					for(CadastroCliente cliente :  integrantes) {
						membros = membros + cliente.getId() + ";";
					}
					
					CadastroGrupo grupo = new CadastroGrupo();
					grupo.setNome_grupo(nome_grupo);
					grupo.setDescricao_grupo(descricao);
					grupo.setIntegrantes(membros);
					
					GerenciarBancoGrupos gerenciar = new GerenciarBancoGrupos();
					int salvar = gerenciar.inserirGrupo(grupo);
					
					if(salvar > 0) {
						JOptionPane.showMessageDialog(null, "Grupo Criado com Sucesso");

					}else {
						JOptionPane.showMessageDialog(null, "Erro ao inserir grupo\nConsulte o administrador");

					}
					
					
					
				}
				}else {
					String nome_grupo = entNomeGrupo.getText();
					
					if(nome_grupo == null || nome_grupo.equals(" ") || nome_grupo.equals("") || nome_grupo.length() < 5) {
						JOptionPane.showMessageDialog(null, "Nome de Grupo Invalido!");
					}else {
						
						String descricao = textAreaDescricaoGrupo.getText();
						String membros = "";
						
						for(CadastroCliente cliente :  integrantes) {
							membros = membros + cliente.getId() + ";";
						}
						
						CadastroGrupo grupo = grupo_global;
						grupo.setNome_grupo(nome_grupo);
						grupo.setDescricao_grupo(descricao);
						grupo.setIntegrantes(membros);
						
						GerenciarBancoGrupos gerenciar = new GerenciarBancoGrupos();
						boolean salvar = gerenciar.atualizarGrupo(grupo);
						
						if(salvar) {
							JOptionPane.showMessageDialog(null, "Grupo Atualizo com Sucesso");

						}else {
							JOptionPane.showMessageDialog(null, "Erro ao atualizar grupo\nConsulte o administrador");

						}
						
				}
				}
				
				((TelaCliente) telaPai).atualizarTabelaGrupos();
				isto.dispose();


			}
		});
		btnConcluir.setBounds(467, 480, 89, 23);
		painelPrincipal.add(btnConcluir);
		
			
		
		if(flag_modo_tela == 1)
			rotinaEdicao();
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void adicionarIntegrante(CadastroCliente _cliente) {
		integrantes.add(_cliente);
		
		modelo.setNumRows(0);
	   
	    /*
	     * modelo.addColumn("Id");
	        modelo.addColumn("IE");
	        modelo.addColumn("Apelido");
	        modelo.addColumn("CPF/CNPJ");
	        modelo.addColumn("Nome");
	       
	     */
	    for (CadastroCliente cliente : integrantes) {
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
	    }
	    }
	}
	
	
	public void adicionarIntegrantes(ArrayList<CadastroCliente> lista_clientes) {
		modelo.setNumRows(0);
		integrantes.addAll(lista_clientes);
		
	    for (CadastroCliente cliente : integrantes) {
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
	    }
	    }
	    
		
	}
	
	public void rotinaEdicao() {
		lblCodigo.setText(grupo_global.getId_grupo() + "");
		entNomeGrupo.setText(grupo_global.getNome_grupo());
		textAreaDescricaoGrupo.setText(grupo_global.getDescricao_grupo());
		
		String membros_conteudo [] = grupo_global.getIntegrantes().split(";");
		ArrayList<CadastroCliente> participantes = new ArrayList<>();
		
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		for(String id_membro : membros_conteudo) {
			int id = Integer.parseInt(id_membro);
			
			CadastroCliente cliente = gerenciar.getCliente(id);
			if(cliente != null) {
				participantes.add(cliente);
			}
			
		}
	
		integrantes = participantes;
		
		  for (CadastroCliente cliente : integrantes) {
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
		    }
		    }
	}
	
	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
}
