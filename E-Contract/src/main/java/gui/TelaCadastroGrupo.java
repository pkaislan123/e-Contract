package main.java.gui;
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

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JOptionPane;
import java.awt.Insets;


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
		 setTitle("E-Contract - Edição Grupo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 758, 641);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		 painelPrincipal.setLayout(new MigLayout("", "[80px][5px][497px][5px][109px]", "[22px][38px][59px][25px][326px][31px][31px]"));
		 
		 JLabel lblNewLabel = new JLabel("Codigo:");
		 lblNewLabel.setForeground(Color.WHITE);
		 lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		 painelPrincipal.add(lblNewLabel, "cell 0 0,alignx right,growy");
		 
		  lblCodigo = new JLabel("");
		  lblCodigo.setForeground(Color.WHITE);
		  lblCodigo.setBorder(new LineBorder(new Color(0, 0, 0)));
		  lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 14));
		  painelPrincipal.add(lblCodigo, "cell 2 0 3 1,grow");
		 
		 JLabel lblNome = new JLabel("Nome:");
		 lblNome.setForeground(Color.WHITE);
		 lblNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		 painelPrincipal.add(lblNome, "cell 0 1,alignx right,aligny center");
		  
		  entNomeGrupo = new JTextField();
		  entNomeGrupo.setFont(new Font("SansSerif", Font.BOLD, 20));
		  painelPrincipal.add(entNomeGrupo, "cell 2 1 3 1,grow");
		  entNomeGrupo.setColumns(10);
		  
		  JLabel lblDescrio = new JLabel("Descrição:");
		  lblDescrio.setForeground(Color.WHITE);
		  lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		  painelPrincipal.add(lblDescrio, "cell 0 2,growx,aligny top");
		 
		  textAreaDescricaoGrupo = new JTextArea();
		  textAreaDescricaoGrupo.setFont(new Font("SansSerif", Font.BOLD, 20));
		  textAreaDescricaoGrupo.setLineWrap(true);
		  textAreaDescricaoGrupo.setWrapStyleWord(true);
		  textAreaDescricaoGrupo.setBorder(new LineBorder(new Color(0, 0, 0)));
		  painelPrincipal.add(textAreaDescricaoGrupo, "cell 2 2 3 1,grow");
		 
		 JLabel lblIntegrantes = new JLabel("                     Integrantes:");
		 lblIntegrantes.setForeground(Color.WHITE);
		 lblIntegrantes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 painelPrincipal.add(lblIntegrantes, "cell 0 3 5 1,grow");
		 
		 
		 table = new JTable(modelo);
		 table.setBackground(Color.WHITE);
		 JScrollPane scrollPane = new JScrollPane(table);
		 scrollPane.setBackground(Color.WHITE);
		 painelPrincipal.add(scrollPane, "cell 0 4 5 1,grow");
		 
		 table.setRowHeight(30);
		 
		 JButton btnExcluirIntegrante = new JButton("Excluir");
		 btnExcluirIntegrante.setForeground(Color.WHITE);
		 btnExcluirIntegrante.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnExcluirIntegrante.setOpaque(false);
		 btnExcluirIntegrante.setBackground(new Color(153, 51, 0));
		 painelPrincipal.add(btnExcluirIntegrante, "cell 2 5,alignx right,aligny top");
		 btnExcluirIntegrante.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		int indiceDaLinha = table.getSelectedRow();
		 		CadastroCliente clienteSelecionado = integrantes.get(indiceDaLinha);
		 		integrantes.remove(clienteSelecionado);
		 		modelo.removeRow(indiceDaLinha);
		 	}
		 });
		 
		 JButton btnConcluir = new JButton("Concluir");
		 btnConcluir.setBackground(new Color(0, 51, 0));
		 btnConcluir.setForeground(Color.WHITE);
		 btnConcluir.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnConcluir.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 	
		 		if(flag_modo_tela == 0) {
		 		String nome_grupo = entNomeGrupo.getText();
		 		
		 		if(nome_grupo == null || nome_grupo.equals(" ") || nome_grupo.equals("") || nome_grupo.length() < 5) {
		 			JOptionPane.showMessageDialog(isto, "Nome de Grupo Invalido!");
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
		 				JOptionPane.showMessageDialog(isto, "Grupo Criado com Sucesso");

		 			}else {
		 				JOptionPane.showMessageDialog(isto, "Erro ao inserir grupo\nConsulte o administrador");

		 			}
		 			
		 			
		 			
		 		}
		 		}else {
		 			String nome_grupo = entNomeGrupo.getText();
		 			
		 			if(nome_grupo == null || nome_grupo.equals(" ") || nome_grupo.equals("") || nome_grupo.length() < 5) {
		 				JOptionPane.showMessageDialog(isto, "Nome de Grupo Invalido!");
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
		 					JOptionPane.showMessageDialog(isto, "Grupo Atualizo com Sucesso");

		 				}else {
		 					JOptionPane.showMessageDialog(isto, "Erro ao atualizar grupo\nConsulte o administrador");

		 				}
		 				
		 		}
		 		}
		 		
		 		((TelaCliente) janela_pai).atualizarTabelaGrupos();
		 		isto.dispose();


		 	}
		 });
		 
		 
		 
		 JButton btnAdicionarIntegrante = new JButton("+Integrante");
		 btnAdicionarIntegrante.setBackground(new Color(153, 102, 51));
		 btnAdicionarIntegrante.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnAdicionarIntegrante.setForeground(Color.WHITE);
		 painelPrincipal.add(btnAdicionarIntegrante, "cell 4 5,growx,aligny top");
		 btnAdicionarIntegrante.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		TelaCliente tela = new TelaCliente(0,10, null);
		 		tela.setTelaPai( isto);
		 		tela.setVisible(true);
		 	}
		 });
		 painelPrincipal.add(btnConcluir, "cell 4 6,growx,aligny top");
		
		modelo.addColumn("Id");
        modelo.addColumn("IE");
        modelo.addColumn("Apelido");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Nome");
		
			
		
		if(flag_modo_tela == 1)
			rotinaEdicao();
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public void adicionarIntegrantes(ArrayList<CadastroCliente> lista_clientes) {
		modelo.setNumRows(0);
		integrantes.addAll(lista_clientes);
		
	    for (CadastroCliente cliente : lista_clientes) {
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
