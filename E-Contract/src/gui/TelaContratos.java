package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;
import gui.TelaNotasFiscais.NFeTableModel;
import manipular.ManipularArquivoTerceiros;
import manipular.ManipularNotasFiscais;
import manipular.ManipularTxt;

import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaContratos extends JDialog {

    private static ArrayList<CadastroContrato> lista_contratos = new ArrayList<>();
    private JDialog telaPai;
    
	private final JPanel painelPrincipal = new JPanel();
	DefaultTableModel modelo = new DefaultTableModel(){
        public boolean isCellEditable(int linha, int coluna) {  
            return false;
        }  
    };
    
    private TelaContratos isto;
    private JTextField entChavePesquisa;
	private TableRowSorter<DefaultTableModel> sorter;


	public TelaContratos(int flag_retorno) {
		//setModal(true);
		//setAlwaysOnTop(true);

		 isto = this;
		
		//setResizable(false);
		setTitle("E-Contract - Contratos");

		

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1074, 493);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(0, null, 0);
			}
		});
		//btnContrato.setIcon(new ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");
		btnContrato.setBounds(864, 80, 172, 33);
		painelPrincipal.add(btnContrato);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(30, 126, 1006, 266);
		painelPrincipal.add(panel);
		
        JTable tabela = new JTable(modelo);
        sorter = new TableRowSorter<DefaultTableModel>(modelo);
        
		
        tabela.setRowSorter(sorter);
        
		tabela.setBackground(new Color(255, 255, 255));
		//tabela.setPreferredSize(new Dimension(0, 200)); 
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.addColumn("ID");
		modelo.addColumn("Código");
		   modelo.addColumn("Compradores");

	        modelo.addColumn("Vendedores");
		 modelo.addColumn("Status");

        modelo.addColumn("Quantidade");
        modelo.addColumn("Medida");
        modelo.addColumn("Produto");
        modelo.addColumn("Safra");
        modelo.addColumn("Valor Produto");
        modelo.addColumn("Valor Total");
     
        modelo.addColumn("Corretores");
        modelo.addColumn("Data do Contrato");
       

        pesquisar(modelo);

        
      
        tabela.getColumnModel().getColumn(0)
        .setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1)
        .setPreferredWidth(90);
        tabela.getColumnModel().getColumn(2)
        .setPreferredWidth(170);
        tabela.getColumnModel().getColumn(3)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(4)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5)
        .setPreferredWidth(70);
        tabela.getColumnModel().getColumn(6)
        .setPreferredWidth(70);
        tabela.getColumnModel().getColumn(7)
        .setPreferredWidth(90);
        tabela.getColumnModel().getColumn(8)
        .setPreferredWidth(80);
        tabela.getColumnModel().getColumn(9)
        .setPreferredWidth(150);
        tabela.getColumnModel().getColumn(10)
        .setPreferredWidth(150);
        tabela.getColumnModel().getColumn(11)
        .setPreferredWidth(100);
        tabela.getColumnModel().getColumn(12)
        .setPreferredWidth(80);
        
       
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pesquisar(modelo);
			}
		});
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(10, 11, 986, 244);
		
		panel.add(scrollPane);
		
		
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();
				
			

				int id_contrato_selecionado = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar_cont = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar_cont.getContrato(id_contrato_selecionado);
				
				
				System.out.println("Produto: " + contrato_selecionado.getModelo_safra().getProduto().getNome_produto());
				CadastroCliente corretores_contrato_selecionado []= contrato_selecionado.getCorretores();
				CadastroCliente compradores_contrato_selecionado []= contrato_selecionado.getCompradores();
				CadastroCliente vendedores_contrato_selecionado []= contrato_selecionado.getVendedores();

				
				for(CadastroCliente corretor : corretores_contrato_selecionado) {
					if(corretor != null)
					System.out.println("Nome do corretor : " + corretor.getNome_empresarial() + " outro nome: " + corretor.getNome_fantaia());
				}
				
				for(CadastroCliente comprador : compradores_contrato_selecionado) {
					if(comprador != null)
					System.out.println("Nome do comprador : " + comprador.getNome_empresarial() + " outro nome: " + comprador.getNome_fantaia());
				}
				
				for(CadastroCliente vendedor : vendedores_contrato_selecionado) {
					if(vendedor != null)
					System.out.println("Nome do vendedor : " + vendedor.getNome_empresarial() + " outro nome: " + vendedor.getNome_fantaia());
				}
				
			
						TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado);
					
			}
		});
		btnAbrir.setBounds(915, 403, 121, 23);
		getContentPane().add(btnAbrir);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela.getSelectedRow();
				int id_contrato = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar.getContrato(id_contrato);
				
				
				if(flag_retorno == 1) {
				
					((TelaConfirmarCarregamento ) telaPai).setContratoCarregamento(contrato_selecionado);
					isto.dispose();
					
				}
			}
		});
		btnSelecionar.setBounds(816, 403, 89, 23);
		painelPrincipal.add(btnSelecionar);
		
		JButton btnImportarTerceiros = new JButton("Importar");
		btnImportarTerceiros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importarContratoTerceiros();
			}
		});
		btnImportarTerceiros.setBounds(717, 403, 89, 23);
		painelPrincipal.add(btnImportarTerceiros);
		
		entChavePesquisa = new JTextField();
		entChavePesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				 String  text = entChavePesquisa.getText().toUpperCase();
				    sorter.setRowFilter(RowFilter.regexFilter(text));
			}
		});
		entChavePesquisa.setBounds(30, 80, 456, 33);
		painelPrincipal.add(entChavePesquisa);
		entChavePesquisa.setColumns(10);
		
				
		if(flag_retorno == 1) {
			//selecionar contrato para carregamento
			btnAbrir.setEnabled(false);
			btnAbrir.setVisible(false);
			
		}else {
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);
		}
		this.setLocationRelativeTo(null);

	}
	
		public static void pesquisar(DefaultTableModel modelo)
	{ 
		modelo.setNumRows(0);
    GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
    lista_contratos.clear();

      
    
   
    for (CadastroContrato contrato : gerenciar.getContratos()) {
    	String cpf, cnpj, nome;
   

		int status = contrato.getStatus_contrato();
		String text_status = "";
		if(status== 1) {
			text_status=  "Recolher Assinaturas".toUpperCase();

		}
		else if(status == 2) {
			text_status = "Assinado".toUpperCase();

		}
		else if(status == 3) {
			text_status = "Cumprindo".toUpperCase();

		}
		
		/*
		    * 	modelo.addColumn("ID");
		modelo.addColumn("Código");
		 modelo.addColumn("Status");

        modelo.addColumn("Quantidade");
        modelo.addColumn("Medida");
        modelo.addColumn("Produto");
        modelo.addColumn("Safra");
        modelo.addColumn("Valor Produto");
        modelo.addColumn("Valor Total");
        modelo.addColumn("Vendedores");
        modelo.addColumn("Compradores");
        modelo.addColumn("Corretores");
        modelo.addColumn("Data do Contrato");

		    */
		
		if(contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3) {
			
			String corretores = "";
			if(contrato.getNomes_corretores() != null){
				corretores = contrato.getNomes_corretores().toUpperCase(); 
			}
			 modelo.addRow(new Object[]{contrato.getId(), contrato.getCodigo(),  contrato.getNomes_compradores().toUpperCase(), contrato.getNomes_vendedores().toUpperCase(), text_status.toUpperCase(), contrato.getQuantidade(), 
	            		contrato.getMedida().toUpperCase().toUpperCase(),
	            contrato.getProduto().toUpperCase().toUpperCase(),
	            contrato.getModelo_safra().getAno_colheita() + "/" +  contrato.getModelo_safra().getAno_plantio(),
	             "R$ " + contrato.getValor_produto(), 
	            "R$ " + contrato.getValor_a_pagar(), 
	            corretores,
	            contrato.getData_contrato()
	            
	            });
	            lista_contratos.add(contrato);
	    	}
			
		}
    	
           
    
    
        
	}
		
		  public boolean testeConexao() {
		    	try {
				     Thread.sleep(10000);
				     URL url = new URL("http://www.google.com.br");
					
					System.out.println("Tentando conexao!");


					URLConnection connection = url.openConnection();
					connection.connect();
					return true;
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("erro ao se conectar a internet!");
					return false;
		 		}
		    }
		  
		  
		  public void setTelaPai(JDialog _telaPai) {
			  this.telaPai = _telaPai;
		  }
		  
		  
		  
		  public void importarContratoTerceiros() {
			  
			  JOptionPane.showMessageDialog(null, "Na próxima tela, importe os arquivos\ndo contrato de terceiros");

			  JFileChooser fileChooser = new JFileChooser();
				fileChooser.setPreferredSize(new Dimension(800, 600));
				fileChooser.setMultiSelectionEnabled(true);
				FileNameExtensionFilter  filter = new FileNameExtensionFilter("Arquivo PDF", "pdf");
				
				int result = fileChooser.showOpenDialog(isto);
				
				File[] files = fileChooser.getSelectedFiles();

				
				
				for(File arquivo : files) {
					ManipularArquivoTerceiros manipular = new ManipularArquivoTerceiros();
					CadastroContrato contrato_importar = manipular.filtrar(arquivo);
				
				}
		  }
}
