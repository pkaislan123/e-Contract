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

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

public class TelaProdutos extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	private DefaultTableModel modelo = new DefaultTableModel();
	private static ArrayList<CadastroProduto> produtos = new ArrayList<>();
	private JDialog telaPai;
	
	public static void pesquisarProdutos(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
    produtos = listaProdutos.getProdutos();
    
    /*
     * modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Descrição");
       
     */
    for (CadastroProduto produto : produtos) {
    	String  nome_produto, descricao, transgenia;
     	int id;
   
    	
    		id = produto.getId_produto();
    		nome_produto = produto.getNome_produto();
    		descricao = produto.getDescricao_produto();
    		transgenia = produto.getTransgenia();
    		
            modelo.addRow(new Object[]{id, nome_produto, descricao,transgenia });

    	}
    }
    
		
	
	public TelaProdutos(int flag_retorno, Window janela_pai) {
		//setAlwaysOnTop(true);

		setModal(true);

		TelaProdutos isto = this;
		
		setResizable(false);
		setTitle("E-Contract - produtos");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 106, 653, 266);
		painelPrincipal.add(panel);
		//panel.setLayout(null);
		
		
		
		JTable tabela = new JTable(modelo);
		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Descrição");
        modelo.addColumn("Transgenia");

        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(120);
       
        pesquisarProdutos(modelo);
        panel.setLayout(null);
	
		
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		pesquisarProdutos(modelo);

        	}
        });
        scrollPane.setBounds(10, 11, 633, 244);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);
		
		JButton btnProduto = new JButton("+ Produto");
		btnProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroProduto tela = new TelaCadastroProduto(isto);
			}
		});
		btnProduto.setBounds(554, 55, 89, 23);
		painelPrincipal.add(btnProduto);
		
		JButton btnSelecionarProduto = new JButton("Selecionar");
		btnSelecionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	int indiceDaLinha = tabela.getSelectedRow();
				if(flag_retorno == 1) {
					((TelaConfirmarCarregamento) telaPai).setProduto(produtos.get(indiceDaLinha));
					isto.dispose();
					
				}
			}
		});
		btnSelecionarProduto.setBounds(574, 396, 89, 23);
		painelPrincipal.add(btnSelecionarProduto);
		
		if(flag_retorno == 0) {
			//modo de criacao
			btnSelecionarProduto.setVisible(false);
			btnSelecionarProduto.setEnabled(false);
		}
		
		this.setLocationRelativeTo(janela_pai);

		
		
	}
	
	
	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
	}
}
