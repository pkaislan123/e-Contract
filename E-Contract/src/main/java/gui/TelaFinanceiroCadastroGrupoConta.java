package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.FinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.outros.JTextFieldPersonalizado;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class TelaFinanceiroCadastroGrupoConta extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextFieldPersonalizado entNome;
	private JEditorPane entObservacao,entDescricao;
	private TelaFinanceiroCadastroGrupoConta isto;

	public TelaFinanceiroCadastroGrupoConta(int flag_modo_operacao, FinanceiroGrupoContas grupo_contas, Window janela_pai) {
		painelPrincipal.setBackground(Color.WHITE);
		this.setContentPane(painelPrincipal);
		                                           //coluna          linha
		                                           //[ coluna 0] [coluna1]
		painelPrincipal.setLayout(new MigLayout("", "[grow][60px]", "[50px][10px][grow][]"));
		
		isto = this;
		
		if(flag_modo_operacao == 0) {
			this.setTitle("E-Contrac - Cadastro de Grupo de Contas");
		}else {
			this.setTitle("E-Contrac - Edição de Grupo de Contas");
		}
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(panelTitulo, "cell 0 0,grow");
		panelTitulo.setLayout(new MigLayout("", "[263px]", "[28px]"));
		
		JLabel lblTitulo = new JLabel("Cadastro de Grupo de Contas");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		panelTitulo.add(lblTitulo, "cell 0 0,alignx center,aligny center");
		
		JPanel panelPilar = new JPanel();
		panelPilar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(panelPilar, "cell 1 0 1 4,grow");
		
		JPanel panelCampos = new JPanel();
		panelCampos.setBackground(Color.WHITE);
		painelPrincipal.add(panelCampos, "cell 0 2,grow");
		panelCampos.setLayout(new MigLayout("", "[][grow]", "[][100px][100px]"));
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCampos.add(lblNome, "cell 0 0,alignx trailing");
		
		entNome = new JTextFieldPersonalizado();
		entNome.setForeground(Color.black);
		panelCampos.add(entNome, "cell 1 0,growx");
		entNome.setColumns(10);
		
		JLabel lblObservacao = new JLabel("Observação:");
		lblObservacao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCampos.add(lblObservacao, "cell 0 1,alignx trailing,aligny top");
		
		
		
		 entObservacao = new JEditorPane();
		 entObservacao.setFont(new Font("SansSerif", Font.BOLD, 16));
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCampos.add(entObservacao, "cell 1 1,grow");
		
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCampos.add(lblDescricao, "cell 0 2,alignx right,aligny top");
		
		 entDescricao = new JEditorPane();
		 entDescricao.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCampos.add(entDescricao, "cell 1 2,grow");
		
		JPanel panelBotoes = new JPanel();
		painelPrincipal.add(panelBotoes, "cell 0 3,alignx right,aligny top");
		panelBotoes.setBackground(Color.WHITE);
		panelBotoes.setLayout(new MigLayout("", "[][]", "[]"));
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroGrupoContas gerenciar_conexao = new GerenciarBancoFinanceiroGrupoContas();
				boolean result = gerenciar_conexao.atualizarFinanceiroGrupoContas(getAtualizar(grupo_contas));
				if(result) {
					JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro Atualizar Cadastro\nConsulte o administrador!");
					isto.dispose();
				}
			}
		});
		panelBotoes.add(btnAtualizar, "cell 0 0");
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroGrupoContas gerenciar_conexao = new GerenciarBancoFinanceiroGrupoContas();
				int result = gerenciar_conexao.inserirFinanceiroGrupoContas(getSalvar());
				if(result > 0) {
					JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro Cadastro\nConsulte o administrador!");
					isto.dispose();
				}
				
			}
		});
		panelBotoes.add(btnCadastrar, "cell 1 0,alignx right,aligny baseline");
		
		//define largura x altura da janela
	    setBounds(0, 0, 675, 598);

	    if(flag_modo_operacao == 0) {
	    	
	    	btnAtualizar.setVisible(false);
	    	btnAtualizar.setEnabled(false);
	    }else {
	    	rotinasEdicao(grupo_contas);
	    	btnCadastrar.setVisible(false);
	    	btnCadastrar.setEnabled(false);
	    }
	    

	    this.setLocationRelativeTo(janela_pai);
	}
	
	
	public void rotinasEdicao(FinanceiroGrupoContas grupo_contas) {
		entNome.setText(grupo_contas.getNome());
		entObservacao.setText(grupo_contas.getObservacao());
		entDescricao.setText(grupo_contas.getDescricao());
		
	}
	
	public FinanceiroGrupoContas getSalvar() {
		FinanceiroGrupoContas grupo = new FinanceiroGrupoContas();
		
		String nome, observacao, descricao;
		
		nome = entNome.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		grupo.setNome(nome);
		grupo.setObservacao(observacao);
		grupo.setDescricao(descricao);
		
		return grupo;
		
	}
	
	public FinanceiroGrupoContas getAtualizar(FinanceiroGrupoContas grupo_antigo) {
		FinanceiroGrupoContas grupo = new FinanceiroGrupoContas();
		grupo.setId_grupo_contas(grupo_antigo.getId_grupo_contas());
		
		String nome, observacao, descricao;
		
		nome = entNome.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		grupo.setNome(nome);
		grupo.setObservacao(observacao);
		grupo.setDescricao(descricao);
		
		return grupo;
		
	}
	
	public void adicionarFocus(Component[] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				if (c instanceof JTextFieldPersonalizado) {

					JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
					caixa_texto.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							System.out.println("Ganhou focu");
							caixa_texto.setFocusGained();

						}

						@Override
						public void focusLost(FocusEvent e) {

							caixa_texto.setFocusLost();
						}
					});
				}
			} else {
				Container novo_container = (Container) c;
				// if (0 < novo_container.getComponents())
				{
					adicionarFocus(novo_container.getComponents());
				}
			}
		}
	}
	
	
	
	

}
