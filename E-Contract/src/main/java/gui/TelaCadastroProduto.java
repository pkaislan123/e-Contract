package main.java.gui;

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

import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;

import javax.swing.JOptionPane;




import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroProduto extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entNomeProduto;
	private JTextField entCodigo;
    private JComboBox cBTransgenia;
    private JTextField entUrlImagem;

	public TelaCadastroProduto(Window janela_pai) {


		TelaCadastroProduto isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Cadastro Produto");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 471, 315);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[141px][grow]", "[36px][41px][30px][100px:100px:100px][][]"));
		
		JLabel lblNomeProduto = new JLabel("Nome Produto:");
		lblNomeProduto.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblNomeProduto, "cell 0 0,alignx left,growy");
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		painelPrincipal.add(lblDescrio, "cell 0 3,alignx right,aligny top");
		
		entNomeProduto = new JTextField();
		painelPrincipal.add(entNomeProduto, "cell 1 0,growx,aligny top");
		entNomeProduto.setColumns(10);
		
		JTextArea entDescricao = new JTextArea();
		entDescricao.setLineWrap(true);
		entDescricao.setWrapStyleWord(true);
		entDescricao.setBackground(SystemColor.inactiveCaptionBorder);
		painelPrincipal.add(entDescricao, "cell 1 3,grow");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(0, 0, 102));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
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
					
					produto.setUrl_referencia(entUrlImagem.getText());
					
					 if(gerenciar.inserir_produto(produto) == 1)
					    {
					    	
					    	isto.dispose();
					    }
					    else
					    {
					    	
					    }
					}catch(Exception t) {
						 JOptionPane.showMessageDialog(isto, "Código Invalido!");

					}
					
				}else {
					 JOptionPane.showMessageDialog(isto, "Código Invalido!");

				}
				
			
				
				
			   
				
				
			}
		});
		
		JLabel lblUrlDaImagem = new JLabel("Url da Imagem:");
		lblUrlDaImagem.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblUrlDaImagem, "cell 0 4,alignx trailing");
		
		entUrlImagem = new JTextField();
		entUrlImagem.setColumns(10);
		painelPrincipal.add(entUrlImagem, "cell 1 4,growx");
		painelPrincipal.add(btnSalvar, "cell 1 5,alignx right,growy");
		
		JLabel lblCodigoParaContratos = new JLabel("       Codigo:");
		lblCodigoParaContratos.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblCodigoParaContratos, "cell 0 1,alignx right,growy");
		
		entCodigo = new JTextField();
		entCodigo.setColumns(10);
		painelPrincipal.add(entCodigo, "cell 1 1,grow");
		
		JLabel lblTransgenia = new JLabel("Transgenia:");
		lblTransgenia.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		painelPrincipal.add(lblTransgenia, "cell 0 2,alignx right,aligny top");
		
		 cBTransgenia = new JComboBox();
		cBTransgenia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cBTransgenia.addItem("Transgenico(GMO)");
		cBTransgenia.addItem("Convencional(NON-GMO)");
		cBTransgenia.addItem("Não Informar");

		painelPrincipal.add(cBTransgenia, "cell 1 2,growx,aligny bottom");
		
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
		
		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);
		
		
	}
}
