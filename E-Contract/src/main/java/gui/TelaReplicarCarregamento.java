package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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




import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class TelaReplicarCarregamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private CadastroContrato contrato_pai_local;
	private CadastroContrato sub_contrato;
	private CadastroContrato.Carregamento carregamento_local;
	private JDialog telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private JComboBox cBSubContratoSelecionado;
	private CadastroNFe nota_fiscal_venda1_carregamento, nota_fiscal_complemento_carregamento, nota_fiscal_interna_carregamento;
	private CadastroRomaneio romaneio_carregamento;
	private TelaReplicarCarregamento isto;
	   private JFrame telaPaiJFrame;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	public TelaReplicarCarregamento(CadastroContrato contrato_pai, CadastroContrato.Carregamento carregamento, Window janela_pai) {
		//setModal(true);

		this.contrato_pai_local = contrato_pai;
		this.carregamento_local = carregamento;

		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Replicar ");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 190);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selecione o sub-contrato ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 22, 156, 17);
		painelPrincipal.add(lblNewLabel);
		modelo.addColumn("Id");
		modelo.addColumn("Codigo");
		modelo.addColumn("Compradores");
		modelo.addColumn("Vendedores");
		modelo.addColumn("Produto");
		modelo.addColumn("Safra");
		modelo.addColumn("Quantidade");
		modelo.addColumn("Safra");
		JButton btnNewButton = new JButton("Concluir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir();
			}
		});
		btnNewButton.setBounds(270, 117, 74, 28);
		painelPrincipal.add(btnNewButton);

		cBSubContratoSelecionado = new JComboBox();
		cBSubContratoSelecionado.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cBSubContratoSelecionado.setBounds(26, 68, 216, 31);
		painelPrincipal.add(cBSubContratoSelecionado);

		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContratos tela = new TelaContratos(3, isto);

				tela.setTelaPai(isto);
				tela.pesquisar_sub_contratos(contrato_pai_local.getId());
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(254, 67, 90, 28);
		painelPrincipal.add(btnNewButton_1);
		new Thread() {
			@Override
			public void run() {
				rotinasEdicao();
			}
		}.start();
		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}

	public void setSubContrato(CadastroContrato _sub_contrato) {
		this.sub_contrato = _sub_contrato;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBSubContratoSelecionado.removeAllItems();
				cBSubContratoSelecionado.repaint();
				cBSubContratoSelecionado.updateUI();

				cBSubContratoSelecionado.addItem(_sub_contrato.getId() + " " + _sub_contrato.getCodigo());

				cBSubContratoSelecionado.repaint();
				cBSubContratoSelecionado.updateUI();

			}
		});
	}

	public void concluir() {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		
		carregamento_local.setId_contrato(sub_contrato.getId());

		// verificar se esse carregamento tem comprovantes anexados
		GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> documentos_anexados = gerenciar_docs
				.getDocumentosPorPai(carregamento_local.getId_carregamento());
		for (CadastroDocumento doc : documentos_anexados) {

			CopiarArquivo copiar = new CopiarArquivo(doc, carregamento_local.getId_carregamento(), sub_contrato);

			String nome_arquivo = copiar.copiar_carregamento();

			if (nome_arquivo != null) {

				doc.setNome("comprovante de carregamento replicado");
				doc.setDescricao("comprovante de carregamento replicado do contrato pai deste sub_contrato");
				doc.setId_contrato_pai(sub_contrato.getId());
				doc.setNome_arquivo(nome_arquivo);

				int anexo_replicado = gerenciar_docs.inserir_documento_padrao(doc);
				if (anexo_replicado > 1) {
					//JOptionPane.showMessageDialog(isto, "Comprovante deste carregamento também foi replicado");

					
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao replicar anexo!\nConsulte o administrador!");

				}
			} else {
				JOptionPane.showMessageDialog(isto,
						"O arquivo fisico não foi copiao! Replica cancelada!\nTente Novamente, se o erro persistir, consulte o administrador");

			}

		}
		
		int retorno = gerenciar.inserirCarregamento(sub_contrato.getId(), carregamento_local);
		if (retorno > 0) {
			JOptionPane.showMessageDialog(isto, "Carregamento Replicado!");
			//gerar pastas e arquivos
			carregamento_local.setId_carregamento(retorno);
			
			gerarPastasEArquivos();

		} else {
			JOptionPane.showMessageDialog(isto,
					"Erro ao Replicar o Carregamento\nNão ha erros no banco de dados\nTente Novamente!");
			isto.dispose();
		}

		isto.dispose();

	}
	
	
	public void gerarPastasEArquivos() {
		//criar a pasta para o recebimento
		boolean pasta_carregamento_contrato1_existe = false;
		boolean pasta_carregamento_contrato2_existe = false;
		
		String caminho_diretorio1 = servidor_unidade + sub_contrato.getCaminho_diretorio_contrato();
		String caminho_diretorio2 = "";
		
		if(sub_contrato.getCaminho_diretorio_contrato2() != null) {
			caminho_diretorio2  = servidor_unidade + sub_contrato.getCaminho_diretorio_contrato2();
		}else {
			caminho_diretorio2 = null;
		}
				
				

		ManipularTxt manipular = new ManipularTxt();
		//cria o diretorio recebimentos no contrato1
		File diretorio_carregamentos_contrato1 = new File( caminho_diretorio1 + "\\carregamentos");
		File diretorio_carregamentos_contrato2 = null;
		if(!diretorio_carregamentos_contrato1.exists()) {
			manipular.criarDiretorio(diretorio_carregamentos_contrato1.getAbsolutePath());
		}
		
		if(caminho_diretorio2 != null) {
			diretorio_carregamentos_contrato2 = new File( caminho_diretorio2 + "\\carregamentos");
			if(!diretorio_carregamentos_contrato2.exists()) {
				manipular.criarDiretorio(diretorio_carregamentos_contrato2.getAbsolutePath());
			}
		}
		
		//criar diretorio do recebimento na pasta do contrato 1
		File diretorio_este_carregamento_contrato1 = new File( caminho_diretorio1 + "\\carregamentos" + "\\carregamento_" + carregamento_local.getId_carregamento());
		if(!diretorio_este_carregamento_contrato1.exists()) {
			boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato1.getAbsolutePath());
			if(criar) {
				//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
				pasta_carregamento_contrato1_existe = true;

			}else {
				
			}
		}else {
			//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja existe");

			pasta_carregamento_contrato1_existe = true;
		}
		
		File diretorio_este_carregamento_contrato2 = new File( caminho_diretorio2 + "\\carregamentos" + "\\carregamento_" + carregamento_local.getId_carregamento());

		if(caminho_diretorio2 != null) {
			
			if(!diretorio_este_carregamento_contrato2.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato2.getAbsolutePath());
				if(criar) {
					pasta_carregamento_contrato2_existe = true;

				}else {
					
				}
			}else {
				pasta_carregamento_contrato2_existe = true;

			}
		}
		
		if(romaneio_carregamento != null) {

			String caminho_completo = romaneio_carregamento.getCaminho_arquivo();
					TratarDados tratar = new TratarDados(caminho_completo);
						String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
						String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
						String conteudo [] = caminho_completo_normalizado.split("\\\\");
						String url_final = "";
						for(String str : conteudo) {
							
							url_final = url_final + str + "\\\\";
						}

				if(pasta_carregamento_contrato1_existe) {
							//copiar a nota para esta pasta
							try {
								
								boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
							    
							} catch (IOException e) {
								//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
								e.printStackTrace();
							}
						}
						if(pasta_carregamento_contrato2_existe) {
							try {
								boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

			}
		
		
		if(nota_fiscal_venda1_carregamento != null) {
			String caminho_completo = nota_fiscal_venda1_carregamento.getCaminho_arquivo();
						TratarDados tratar = new TratarDados(caminho_completo);
							String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
							String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
							String conteudo [] = caminho_completo_normalizado.split("\\\\");
							String url_final = "";
							for(String str : conteudo) {
								
								url_final = url_final + str + "\\\\";
							}

				if(pasta_carregamento_contrato1_existe) {
								//copiar a nota para esta pasta
								try {
									
									boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
								    
								} catch (IOException e) {
									//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
									e.printStackTrace();
								}
							}
							if(pasta_carregamento_contrato2_existe) {
								try {
									boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

			}
		
		
		if(nota_fiscal_complemento_carregamento != null) {
			String caminho_completo = nota_fiscal_complemento_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}


	if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

}
		
		
		if(nota_fiscal_interna_carregamento != null) {
			String caminho_completo = nota_fiscal_interna_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}


if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

}
		
		
	}
	
	public void rotinasEdicao() {
		
		

		
		try {
	    if(checkString(carregamento_local.getCodigo_romaneio())){
	    	//procurar por romaneio
	    	if(checkString(carregamento_local.getCaminho_romaneio())){
	    		ManipularRomaneios manipular = new ManipularRomaneios("");

	        	CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade + carregamento_local.getCaminho_romaneio()));
	        	setRomaneio(romaneio);
	    	}
	    }
		}catch(Exception e) {
			
		}
		
	
		
		//nf venda 1
		try {
	        if(checkString(carregamento_local.getCodigo_nf_venda1())){
	        	if(carregamento_local.getCaminho_nf_venda1().length() > 10) {
	        		//procurar por nf venda
		        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
		        	CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade + carregamento_local.getCaminho_nf_venda1()));
		        	setNotaFiscalVenda1(nota_fiscal_venda);
	        	}
	        
	        	
	        }
			}catch(Exception e) {
				
			}
		
		
		//nf interna1
				try {
			        if(checkString(carregamento_local.getCodigo_nf_interna())){
			        	if(carregamento_local.getCaminho_nf_interna().length() > 10) {
			        		//procurar por nf remessa
				        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
				        	CadastroNFe nota_fiscal_remessa = manipular.filtrar(new File(servidor_unidade + carregamento_local.getCaminho_nf_interna()));
				        	setNotaFiscalInterna(nota_fiscal_remessa);
			        	}
			        
			        }
					}catch(Exception e) {
						//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");
						

					}
		
				//nf complemento
				try {
			        if(checkString(carregamento_local.getCodigo_nf_complemento())){
			        	if(carregamento_local.getCaminho_nf_complemento().length() > 10) {
			        		//procurar por nf remessa
				        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
				        	CadastroNFe nota_fiscal_complemento = manipular.filtrar(new File(servidor_unidade + carregamento_local.getCaminho_nf_complemento()));
				        	setNotaFiscalComplemento(nota_fiscal_complemento);
			        	}
			        
			        
			        	
			        }
					}catch(Exception e) {
						
					}
		
		
	}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	


	public void getDadosGlobais() {
		//gerenciador de logasd
		
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
				  servidor_unidade = configs_globais.getServidorUnidade();
		
	}
	
public void setRomaneio(CadastroRomaneio romaneio) {
		
		romaneio_carregamento = romaneio;
		
	}
	
public void setNotaFiscalVenda1(CadastroNFe _nfe) {
        this.nota_fiscal_venda1_carregamento = _nfe;

	}
	
	public void setNotaFiscalComplemento(CadastroNFe _nfe) {
        this.nota_fiscal_complemento_carregamento = _nfe;
       
	}
	
	public void setNotaFiscalInterna(CadastroNFe _nfe) {
        this.nota_fiscal_interna_carregamento = _nfe;
       
	}
	
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}
}
