package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class TelaCadastroSafra extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();

	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
    private JComboBox  cBProduto, cBAnoPlantio, cBAnoColheita;
    private JTextArea entDescricao;
    private JTextField entCodigoSafra;
    private JLabel lblCodigoProduto;
    private TelaCadastroSafra isto ;
	public TelaCadastroSafra(Window janela_pai) {
		//setAlwaysOnTop(true);

		//setModal(true);

		 isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Nova Safra");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblAnoPlantio = new JLabel("Ano Plantio:");
		lblAnoPlantio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAnoPlantio.setBounds(76, 106, 128, 33);
		painelPrincipal.add(lblAnoPlantio);
		
		JLabel lblAnoColheita = new JLabel("Ano Colheita:");
		lblAnoColheita.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAnoColheita.setBounds(65, 150, 138, 33);
		painelPrincipal.add(lblAnoColheita);
		
		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProduto.setBounds(109, 195, 95, 33);
		painelPrincipal.add(lblProduto);
		
		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();
		cBProduto = new JComboBox();
		cBProduto.setModel(modelProduto);
		cBProduto.setRenderer(cBProdutoPersonalizado);
		cBProduto.setBounds(214, 195, 305, 33);
		painelPrincipal.add(cBProduto);
		
		cBProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                CadastroProduto produto= (CadastroProduto) modelProduto.getSelectedItem();
                lblCodigoProduto.setText(Integer.toString(produto.getCodigo()));
  				
			}
			
		});
		
	  
		
		 GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		 ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();
		
		 for(CadastroProduto produto : produtos)
		{
		    	modelProduto.addProduto(produto);

		}
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ano_plantio, ano_colheita;
			    String descricao;
				
				GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
				CadastroSafra safra = new CadastroSafra();
				
				descricao = entDescricao.getText();
				
				ano_plantio = cBAnoPlantio.getSelectedItem().toString();
				ano_colheita = cBAnoColheita.getSelectedItem().toString();
				
				int id_produto ;
				
                String codigo_safra = entCodigoSafra.getText();
				
				
				if(codigo_safra.length() != 1) {
					
					
					try {
					
						CadastroProduto produto= (CadastroProduto) modelProduto.getSelectedItem();
					
					safra.setAno_plantio(Integer.parseInt(ano_plantio));
					safra.setAno_colheita(Integer.parseInt(ano_colheita));
					safra.setDescricao_safra(descricao);
					safra.setProduto(produto);
					
					String scodigo_safra = Integer.toString(produto.getCodigo()) + Integer.parseInt(codigo_safra);
					
					safra.setCodigo(Integer.parseInt(scodigo_safra));
					
					if(gerenciar.inserir_safra(safra) == 1)
					{
						isto.dispose();
					}
					else
					{
						
					}
					}catch(Exception g) {
						JOptionPane.showMessageDialog(isto, "Codigo Invalido!");

					}
						
				}else {
					JOptionPane.showMessageDialog(isto, "Codigo Invalido!");
				}
				
				
				
			}
		});
		btnSalvar.setBounds(558, 410, 89, 23);
		painelPrincipal.add(btnSalvar);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDescrio.setBounds(91, 291, 113, 33);
		painelPrincipal.add(lblDescrio);
		
		entDescricao = new JTextArea();
		entDescricao.setBackground(SystemColor.inactiveCaption);
		entDescricao.setBounds(214, 299, 250, 82);
		painelPrincipal.add(entDescricao);
		
		cBAnoPlantio = new JComboBox();
		cBAnoPlantio.setBounds(214, 106, 188, 33);
		painelPrincipal.add(cBAnoPlantio);
		
		cBAnoColheita = new JComboBox();
		cBAnoColheita.setBounds(214, 150, 188, 33);
		painelPrincipal.add(cBAnoColheita);
		
		
		cBAnoPlantio.addItem("2020");
		cBAnoPlantio.addItem("2021");
		cBAnoPlantio.addItem("2022");
		cBAnoPlantio.addItem("2023");
		cBAnoPlantio.addItem("2024");
		cBAnoPlantio.addItem("2025");
		cBAnoPlantio.addItem("2026");
		cBAnoPlantio.addItem("2027");
		cBAnoPlantio.addItem("2028");
		cBAnoPlantio.addItem("2029");
		cBAnoPlantio.addItem("2030");
	
		
	
		cBAnoColheita.addItem("2020");
		cBAnoColheita.addItem("2021");
		cBAnoColheita.addItem("2022");
		cBAnoColheita.addItem("2023");
		cBAnoColheita.addItem("2024");
		cBAnoColheita.addItem("2025");
		cBAnoColheita.addItem("2026");
		cBAnoColheita.addItem("2027");
		cBAnoColheita.addItem("2028");
		cBAnoColheita.addItem("2029");
		cBAnoColheita.addItem("2030");
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCodigo.setBounds(119, 247, 95, 33);
		painelPrincipal.add(lblCodigo);
		
		entCodigoSafra = new JTextField();
		entCodigoSafra.setColumns(10);
		entCodigoSafra.setBounds(299, 249, 103, 33);
		painelPrincipal.add(entCodigoSafra);
		
		entCodigoSafra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				//120.927.987-00
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCodigoSafra.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
			
				if(entCodigoSafra.getText().length()>=2){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCodigoSafra.setText(entCodigoSafra.getText().substring(0,2));
				}
			
			}
				
			}
		});
		
		 lblCodigoProduto = new JLabel("");
		lblCodigoProduto.setFont(new Font("Arial", Font.BOLD, 20));
		lblCodigoProduto.setBounds(214, 247, 75, 41);
		painelPrincipal.add(lblCodigoProduto);

		   CadastroProduto produto= (CadastroProduto) modelProduto.getSelectedItem();
           lblCodigoProduto.setText(Integer.toString(produto.getCodigo()));

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);
		
		
	}
}
