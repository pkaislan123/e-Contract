package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.SwingConstants;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.ContaBancaria;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.outros.JTextFieldPersonalizado;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Insets;

public class TelaFinanceiroCadastroCentroCusto extends JDialog {

	private JPanel painelPrincial = new JPanel();
	private JTextFieldPersonalizado entNome ;
	private CadastroCliente cliente_selecionado;
	private JComboBox cbCliente;
	private JEditorPane entObservacao,entDescricao;
	private TelaFinanceiroCadastroCentroCusto isto;
	
	public TelaFinanceiroCadastroCentroCusto(int flag_modo_operacao, CentroCusto centro_custo, Window janela_pai) {
		
		if(flag_modo_operacao == 0)
		 setTitle("Cadastro Centro de Custo");
		else
		 setTitle("Edição Centro de Custo");
		
	    isto = this;
	    this.setContentPane(painelPrincial);
	    
	     painelPrincial.setBackground(Color.WHITE);
		
		//define largura x altura da janela
	    setBounds(0, 0, 675, 598);
	    
	    //define o Layout MigLayou para o painel principal, com   colunas x linhas
		painelPrincial.setLayout(new MigLayout("", "[600px,grow][60px,grow]", "[50px][][grow][]"));
		
		//Cria um jpanel titulo
		JPanel panel_titulo = new JPanel();
		
		//define a cor de fundo do jpanel titulo
		panel_titulo.setBackground(new Color(0, 0, 51));
		
		//adiciona o painel titulo no painel pai
		painelPrincial.add(panel_titulo, "cell 0 0,grow");
		
		//define a borda do painel de titulo
		panel_titulo.setLayout(new BorderLayout(0, 0));
		
		//criar uma label chamada lblTitulo
		//texto
		JLabel lblTitulo = new JLabel("Cadastro de Centro de Custo");
		//fonte, arial, negrito, e tamanho 24
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		//define cor de frente, cor do texto
		lblTitulo.setForeground(Color.WHITE);
		//adiciona o label no painel titulo
		panel_titulo.add(lblTitulo);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 51));
		painelPrincial.add(panel_1, "cell 1 0 1 4,grow");
		
		JPanel painelCampos = new JPanel();
		painelCampos.setBackground(Color.WHITE);
		painelPrincial.add(painelCampos, "cell 0 2,grow");
		painelCampos.setLayout(new MigLayout("", "[][grow]", "[][5px][][5px][103.00px][5px][100px]"));
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		painelCampos.add(lblNome, "cell 0 0,alignx trailing");
		
		 entNome = new JTextFieldPersonalizado();
		entNome.setForeground(Color.BLACK);
		painelCampos.add(entNome, "cell 1 0,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Cliente:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 16));
		painelCampos.add(lblNewLabel_2, "cell 0 2,alignx right");
		
		JPanel painelSelecaoCliente = new JPanel();
		painelSelecaoCliente.setBackground(new Color(0, 51, 102));
		painelCampos.add(painelSelecaoCliente, "cell 1 2,grow");
		painelSelecaoCliente.setLayout(new MigLayout("", "[grow]", "[]"));
		
		 cbCliente = new JComboBox();
		cbCliente.setFont(new Font("Arial", Font.PLAIN, 18));
		painelSelecaoCliente.add(cbCliente, "flowx,cell 0 0,growx");
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0,26, null);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setBackground(new Color(0, 0, 102));
		painelSelecaoCliente.add(btnSelecionar, "cell 0 0");
		
		JLabel lblNewLabel = new JLabel("Observação:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		painelCampos.add(lblNewLabel, "cell 0 4,alignx right,aligny top");
		
		 entObservacao = new JEditorPane();
		 entObservacao.setFont(new Font("Arial", Font.PLAIN, 18));
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCampos.add(entObservacao, "cell 1 4,grow");
		
		JLabel lblNewLabel_1 = new JLabel("Descrição:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		painelCampos.add(lblNewLabel_1, "cell 0 6,alignx right,aligny top");
		
		
		 entDescricao = new JEditorPane();
		 entDescricao.setFont(new Font("Arial", Font.PLAIN, 18));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCampos.add(entDescricao, "cell 1 6,grow");
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBackground(Color.WHITE);
		painelPrincial.add(panelBotoes, "cell 0 3,alignx right,growy");
		panelBotoes.setLayout(new MigLayout("", "[89px]", "[23px]"));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoCentroCustos gerenciar = new GerenciarBancoCentroCustos();
				int result = gerenciar.inserirCentroCusto(getSalvar());
				if(result > 0) {
					JOptionPane.showMessageDialog(isto, "Cadastro Salvo");
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao cadastrar\nConsulte o administrador");
				}
				
			}
		});
		
		JButton btnAtualizar = new JButton("Atualizar");
		panelBotoes.add(btnAtualizar, "flowx,cell 0 0");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoCentroCustos gerenciar = new GerenciarBancoCentroCustos();
				boolean result = gerenciar.atualizarCentroCusto(getAtualizar(centro_custo));
				if(result) {
					JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o administrador");
				}
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelBotoes.add(btnCadastrar, "cell 0 0,alignx left,aligny top");
		
		
		if(flag_modo_operacao == 0 ) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else {
			
			rotinasEdicao(centro_custo);
			
			btnCadastrar.setEnabled(false);
			btnCadastrar.setVisible(false);
		}
		
		
		
		//define isto como resizable falso
		this.setResizable(false);
		
		//Seta a localização espacial da tela, nesse caso ele vai pro exato centro
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	
	public CentroCusto getSalvar() {
		CentroCusto cc = new CentroCusto();
		
		String nome = entNome.getText();
		String observacao = entObservacao.getText();
		String descricao = entDescricao.getText();
		
		cc.setNome_centro_custo(nome);
		cc.setObservacao(observacao);
		cc.setDescricao(descricao);
		
		if(cliente_selecionado != null)
		cc.setId_cliente(cliente_selecionado.getId());
		
		
		return cc;
		
	}
	
	public CentroCusto getAtualizar(CentroCusto centro_custo_antigo) {
		CentroCusto cc = new CentroCusto();
		cc.setId_centro_custo(centro_custo_antigo.getId_centro_custo());
		
		String nome = entNome.getText();
		String observacao = entObservacao.getText();
		String descricao = entDescricao.getText();
		
		cc.setNome_centro_custo(nome);
		cc.setObservacao(observacao);
		cc.setDescricao(descricao);
		
		if(cliente_selecionado != null)
		 cc.setId_cliente(cliente_selecionado.getId());
		
		
		return cc;
		
	}

	public void setCliente(CadastroCliente cliente) {
		cliente_selecionado = cliente;

		cbCliente.removeAllItems();

		if (cliente.getTipo_pessoa() == 0) // pessoa fisica
		{
			cbCliente.addItem(cliente.getNome() + " " + cliente.getSobrenome());
			cbCliente.setSelectedItem(cliente.getNome() + " " + cliente.getSobrenome());
			cbCliente.addItem("Indefinido");


		} else // pessoa juridica
		{
			cbCliente.addItem(cliente.getRazao_social());
			cbCliente.setSelectedItem(cliente.getRazao_social());
			cbCliente.addItem("Indefinido");

		}
		
		

	}
	
	public void rotinasEdicao(CentroCusto centro_custo) {
		
		String nome = centro_custo.getNome_centro_custo();
		String observacao = centro_custo.getObservacao();
		String descricao = centro_custo.getDescricao();
		
		if(centro_custo.getId_cliente() > 0) {
			CadastroCliente cliente = new GerenciarBancoClientes().getCliente(centro_custo.getId_cliente());
			if(cliente != null) {
				setCliente(cliente);
			}
			
			
		}
		
		entNome.setText(nome);
		entObservacao.setText(observacao);
		entDescricao.setText(descricao);
	}
}
