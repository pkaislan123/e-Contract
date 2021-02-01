package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;


import cadastros.CadastroProduto;
import conexaoBanco.GerenciarBancoProdutos;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JComboBox;

public class TelaCadastroProduto extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entNomeProduto;
	private JTextField entCodigo;
    private JComboBox cBTransgenia;

	public TelaCadastroProduto() {

		setModal(true);

		TelaCadastroProduto isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Cadastro Produto");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 412, 398);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNomeProduto = new JLabel("Nome Produto:");
		lblNomeProduto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNomeProduto.setBounds(23, 32, 137, 36);
		painelPrincipal.add(lblNomeProduto);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblDescrio.setBounds(61, 182, 103, 51);
		painelPrincipal.add(lblDescrio);
		
		entNomeProduto = new JTextField();
		entNomeProduto.setBounds(170, 32, 200, 31);
		painelPrincipal.add(entNomeProduto);
		entNomeProduto.setColumns(10);
		
		JTextArea entDescricao = new JTextArea();
		entDescricao.setLineWrap(true);
		entDescricao.setWrapStyleWord(true);
		entDescricao.setBackground(SystemColor.inactiveCaptionBorder);
		entDescricao.setBounds(174, 186, 196, 139);
		painelPrincipal.add(entDescricao);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoProdutos gerenciar = new GerenciarBancoProdutos();
				CadastroProduto produto = new CadastroProduto();
				
				String nome, descricao;
				String codigo;
				
				nome = entNomeProduto.getText();
				descricao = entDescricao.getText();
			
			
				codigo = entCodigo.getText();
				
				
				if(codigo.length() != 1) {
					
					try {
						
						
					produto.setNome_produto(nome);
					produto.setDescricao_produto(descricao);
					produto.setCodigo(Integer.parseInt(codigo));
					produto.setTransgenia(cBTransgenia.getSelectedItem().toString());
					
					 if(gerenciar.inserir_produto(produto) == 1)
					    {
					    	
					    	isto.dispose();
					    }
					    else
					    {
					    	
					    }
					}catch(Exception t) {
						 JOptionPane.showMessageDialog(null, "Código Invalido!");

					}
					
				}else {
					 JOptionPane.showMessageDialog(null, "Código Invalido!");

				}
				
			
				
				
			   
				
				
			}
		});
		btnSalvar.setBounds(281, 337, 89, 23);
		painelPrincipal.add(btnSalvar);
		
		JLabel lblCodigoParaContratos = new JLabel("       Codigo:");
		lblCodigoParaContratos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCodigoParaContratos.setBounds(61, 79, 103, 36);
		painelPrincipal.add(lblCodigoParaContratos);
		
		entCodigo = new JTextField();
		entCodigo.setColumns(10);
		entCodigo.setBounds(170, 74, 200, 36);
		painelPrincipal.add(entCodigo);
		
		JLabel lblTransgenia = new JLabel("Transgenia:");
		lblTransgenia.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblTransgenia.setBounds(49, 136, 111, 24);
		painelPrincipal.add(lblTransgenia);
		
		 cBTransgenia = new JComboBox();
		cBTransgenia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cBTransgenia.setBounds(170, 137, 200, 29);
		cBTransgenia.addItem("Transgenico(GMO)");
		cBTransgenia.addItem("Convencional(NON-GMO)");
	
		painelPrincipal.add(cBTransgenia);
		
		entCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				//120.927.987-00
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCodigo.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
			
				if(entCodigo.getText().length()>=2){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCodigo.setText(entCodigo.getText().substring(0,2));
				}
			
			}
				
			}
		});
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
}
