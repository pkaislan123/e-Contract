package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroDocumento;
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoAditivos;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoDocumento;
import keeptoo.KGradientPanel;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.ManipularTxt;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import outros.DadosGlobais;
import outros.GetData;
import outros.JPanelTransparent;
import relatoria.RelatorioContratoIndividual;
import tratamento_proprio.Log;

import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JComboBox;



public class TelaGerenciarCliente extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private TelaGerenciarCliente isto;
    private KGradientPanel menu_lateral;
    private  JPanel panel_docs;
	private JTree arvore_documentos;

    DefaultMutableTreeNode no_comprovantes; 
	DefaultMutableTreeNode no_docs_pessoais ;
	DefaultMutableTreeNode no_outros ;
	private DefaultMutableTreeNode no_selecionado;
    private CadastroCliente cliente_local;
    private  JComboBox cBTipoDocumento ;
    
    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private  JTextArea entDescricaoDocumento;
	private JLabel lblTipoPessoa, lblTipoIdentificacao, lblIdentificacao, lblIe, lblIE, lblStatus, lblEndereco;

	
	public TelaGerenciarCliente(CadastroCliente cliente_selecionado) {
		setModal(true);
		
		
		getDadosGlobais();

		 isto = this;
		 cliente_local = cliente_selecionado;
		
		setResizable(true);
		setTitle("E-Contract - Gerenciar Cliente");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1078, 595);
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JPanel painelDadosIniciais = new JPanel();
		painelDadosIniciais.setBackground(new Color(0, 128, 128));
		painelDadosIniciais.setBounds(198, 153, 864, 358);
		painelPrincipal.add(painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, cliente_selecionado);
				telaEdicao.setTelaPai(isto);
				telaEdicao.setVisible(true);
			}
		});
		btnEditar.setBounds(629, 297, 89, 23);
		painelDadosIniciais.add(btnEditar);
		
		JPanel painelInfo = new JPanel();
		painelInfo.setBackground(new Color(0, 100, 0));
		painelInfo.setBounds(22, 11, 696, 275);
		painelDadosIniciais.add(painelInfo);
		painelInfo.setLayout(new MigLayout("", "[][]", "[][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("TIpo Pessoa:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfo.add(lblNewLabel, "cell 0 0");
		
		
		
		 lblTipoPessoa = new JLabel("Juridica");
		 lblTipoPessoa.setForeground(Color.WHITE);
		lblTipoPessoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInfo.add(lblTipoPessoa, "cell 1 0");
		
		 lblTipoIdentificacao = new JLabel("CPF/CNPJ:");
		lblTipoIdentificacao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfo.add(lblTipoIdentificacao, "cell 0 1,alignx right");
		
		 lblIdentificacao = new JLabel("120.927.986-00");
		 lblIdentificacao.setForeground(Color.WHITE);
		lblIdentificacao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInfo.add(lblIdentificacao, "cell 1 1");
		
		 lblIe = new JLabel("IE:");
		lblIe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfo.add(lblIe, "cell 0 3,alignx right");
		
		lblIE = new JLabel("120.927.986-00");
		lblIE.setForeground(Color.WHITE);
		lblIE.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInfo.add(lblIE, "cell 1 3,alignx left");
		
		JLabel lblnasdad = new JLabel("Status:");
		lblnasdad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfo.add(lblnasdad, "cell 0 4,alignx right");
		
		 lblStatus = new JLabel("120.927.986-00");
		 lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInfo.add(lblStatus, "cell 1 4,alignx left");
		
		JLabel lblEndereo = new JLabel("Endereço:");
		lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfo.add(lblEndereo, "cell 0 6,alignx right");
		
		 lblEndereco = new JLabel("Rodovia MG 188, km 242, Zona Rural, Guarda-Mor/MG 38570-000");
		 lblEndereco.setForeground(Color.WHITE);
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInfo.add(lblEndereco, "cell 1 6");
		
		
			
		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 255, 204);
		menu_lateral.kEndColor = Color.BLUE;
		menu_lateral.setBounds(0, 0, 200, 556);
		 painelPrincipal.add(menu_lateral);
		 menu_lateral.setLayout(null);
		 
		 JPanelTransparent panel = new JPanelTransparent();
		 panel.setLayout(null);
		 panel.setBounds(10, 167, 181, 181);
		 menu_lateral.add(panel);
		 
		 JLabel btnDocumentos = new JLabel("Documentos");
		 btnDocumentos.setForeground(Color.WHITE);
		 btnDocumentos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDocumentos.setBackground(new Color(0, 0, 0, 100));
		 btnDocumentos.setBounds(10, 53, 146, 20);
		 panel.add(btnDocumentos);
		 
		 JLabel btnDadosIniciais = new JLabel("Dados Inicias");
		 btnDadosIniciais.setOpaque(true);
		 btnDadosIniciais.setForeground(Color.WHITE);
		 btnDadosIniciais.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
		 btnDadosIniciais.setBounds(10, 22, 161, 20);
		 panel.add(btnDadosIniciais);
		 
		 JLabel btnContratos = new JLabel("Contratos");
		 btnContratos.setForeground(Color.WHITE);
		 btnContratos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnContratos.setBackground(new Color(0, 0, 0, 100));
		 btnContratos.setBounds(10, 84, 146, 20);
		 panel.add(btnContratos);
		 
		 JPanel panel_1_1_1 = new JPanel();
		 panel_1_1_1.setBackground(Color.BLACK);
		 panel_1_1_1.setBounds(10, 449, 23, 107);
		 menu_lateral.add(panel_1_1_1);
		 
		 JPanel panel_1_1_1_1 = new JPanel();
		 panel_1_1_1_1.setBackground(Color.BLACK);
		 panel_1_1_1_1.setBounds(43, 375, 23, 181);
		 menu_lateral.add(panel_1_1_1_1);
		 
		 JPanel painelContratos = new JPanel();
		 painelContratos.setBackground(Color.WHITE);
		 painelContratos.setForeground(Color.BLACK);
		 painelContratos.setBounds(197, 154, 865, 356);
		 painelPrincipal.add(painelContratos);
		 painelContratos.setLayout(null);
		 

		 KGradientPanel panelTopo = new KGradientPanel();
		 panelTopo.kStartColor = new Color(51, 0, 51);
		 panelTopo.kEndColor = new Color(153, 51, 204);
		 panelTopo.setBounds(172, 65, 890, 90);
		 painelPrincipal.add(panelTopo);
		 panelTopo.setLayout(null);
		 
		 String nome ="";
		 if(cliente_selecionado.getTipo_pessoa() == 0)
		    nome = cliente_selecionado.getNome_empresarial();
		 else
			 nome = cliente_selecionado.getNome_fantaia();
		 
		 
		 JLabel lblNomeUsuario = new JLabel(nome.toUpperCase());
		 lblNomeUsuario.setForeground(Color.WHITE);
		 lblNomeUsuario.setFont(new Font("Tahoma", Font.BOLD, 23));
		 lblNomeUsuario.setBounds(44, 32, 567, 33);
		 panelTopo.add(lblNomeUsuario);
		 
		  panel_docs = new JPanel();
		  panel_docs.setBackground(Color.WHITE);
		 panel_docs.setBounds(43, 11, 315, 336);
		
		 
		 JPanel painelDocumentos = new JPanel();
		 painelDocumentos.setBounds(198, 153, 864, 358);
		 painelPrincipal.add(painelDocumentos);
		 painelDocumentos.setLayout(null);
		 
		 painelDocumentos.add(panel_docs);
		 
		 JPanel painelInserirDocumento = new JPanel();
		 painelInserirDocumento.setLayout(null);
		 painelInserirDocumento.setBounds(381, 35, 431, 288);
		 painelDocumentos.add(painelInserirDocumento);
		 
		 JLabel lblNewLabel_15 = new JLabel("Nome:");
		 lblNewLabel_15.setBounds(34, 11, 46, 14);
		 painelInserirDocumento.add(lblNewLabel_15);
		 
		 JLabel lblNewLabel_16 = new JLabel("Descrição:");
		 lblNewLabel_16.setBounds(19, 105, 70, 14);
		 painelInserirDocumento.add(lblNewLabel_16);
		 
		  entDescricaoDocumento = new JTextArea();
		 entDescricaoDocumento.setBounds(75, 100, 330, 85);
		 painelInserirDocumento.add(entDescricaoDocumento);
		 
		 JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		 lblNewLabel_17.setBounds(19, 210, 46, 14);
		 painelInserirDocumento.add(lblNewLabel_17);
		 
		 entCaminhoDocumento = new JTextField();
		 entCaminhoDocumento.setColumns(10);
		 entCaminhoDocumento.setBounds(75, 198, 231, 39);
		 painelInserirDocumento.add(entCaminhoDocumento);
		 
		 entNomeDocumento = new JTextField();
		 entNomeDocumento.setColumns(10);
		 entNomeDocumento.setBounds(75, 8, 330, 27);
		 painelInserirDocumento.add(entNomeDocumento);
		 
		 JButton btnSelecionarDocumento = new JButton("Selecionar");
		 btnSelecionarDocumento.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		selecionarDocumento();
		 	}
		 });
		 btnSelecionarDocumento.setBounds(316, 205, 89, 24);
		 painelInserirDocumento.add(btnSelecionarDocumento);
		 
		 JButton btnAdicionarDocumento = new JButton("Adicionar");
		 btnAdicionarDocumento.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		adicionarNovoDocumento();
		 	}
		 });
		 btnAdicionarDocumento.setBounds(316, 254, 89, 23);
		 painelInserirDocumento.add(btnAdicionarDocumento);
		 
		 JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		 lblNewLabel_16_1.setBounds(19, 58, 70, 14);
		 painelInserirDocumento.add(lblNewLabel_16_1);
		 
		  cBTipoDocumento = new JComboBox();
		 cBTipoDocumento.setBounds(75, 54, 330, 22);
		 painelInserirDocumento.add(cBTipoDocumento);
		 cBTipoDocumento.addItem("Documento Pessoal");
		 cBTipoDocumento.addItem("Comprovantes");
		 cBTipoDocumento.addItem("Outros");

		 
		 JButton btnSair = new JButton("Sair");
		 btnSair.setBounds(923, 522, 89, 23);
		 painelPrincipal.add(btnSair);
		 
		 JPanel panel_1 = new JPanel();
		 panel_1.setBackground(Color.BLACK);
		 panel_1.setBounds(911, 9, 151, 17);
		 painelPrincipal.add(panel_1);
		 
		 JPanel panel_1_1 = new JPanel();
		 panel_1_1.setBackground(Color.BLACK);
		 panel_1_1.setBounds(803, 37, 259, 17);
		 painelPrincipal.add(panel_1_1);
		 btnSair.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		isto.dispose();
		 	}
		 });

			
		
		 btnDadosIniciais.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					painelDocumentos.setEnabled(false);
					painelDocumentos.setVisible(false);
					
					painelDadosIniciais.setEnabled(true);
					painelDadosIniciais.setVisible(true);

					painelContratos.setEnabled(false);
					painelContratos.setVisible(false);

					
					btnDadosIniciais.setOpaque(true);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(false);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();

					btnContratos.setOpaque(false);
					btnContratos.setBackground(new Color(0, 0, 0, 100));

					btnContratos.repaint();
					btnContratos.updateUI();

				}
			});

		
		 btnDocumentos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					

					painelDadosIniciais.setEnabled(false);
					painelDadosIniciais.setVisible(false);
					
					painelDocumentos.setEnabled(true);
					painelDocumentos.setVisible(true);


					painelContratos.setEnabled(false);
					painelContratos.setVisible(false);
					
					btnDadosIniciais.setOpaque(false);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(true);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();

		
					btnContratos.setOpaque(false);
					btnContratos.setBackground(new Color(0, 0, 0, 100));

					btnContratos.repaint();
					btnContratos.updateUI();
				

				}
			});

		 
		 btnContratos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					

					painelContratos.setEnabled(true);
					painelContratos.setVisible(true);

					painelDadosIniciais.setEnabled(false);
					painelDadosIniciais.setVisible(false);
					
					painelDocumentos.setEnabled(false);
					painelDocumentos.setVisible(false);


					btnContratos.setOpaque(true);
					btnContratos.setBackground(new Color(0, 0, 0, 100));

					btnContratos.repaint();
					btnContratos.updateUI();
					
					
					btnDadosIniciais.setOpaque(false);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(false);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();

		
				

				}
			});

		 setInformacoesDocumentos();
	   // setInfo();

		this.setLocationRelativeTo(null);

		
		
		
	}
	
	
	

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(cliente_local.getId());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				panel_docs.setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setBounds(10, 5, 295, 320);
				panel_docs.add(panel);
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos deste Cliente:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel_18, "cell 0 0");

			
				
				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				 no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				 no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				 no_outros = new DefaultMutableTreeNode("Outros");
				 
				 
				// add the child nodes to the root node
				root.add(no_docs_pessoais);
				root.add(no_comprovantes);
				root.add(no_outros);

				// create the tree by passing in the root node
				arvore_documentos = new JTree(root);
				
			
		        
				arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
				    @Override
				    public void valueChanged(TreeSelectionEvent e) {
				        no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
				       // lblNoSelecionado.setText(no_selecionado.getUserObject().toString());
				    }
				});
				JPopupMenu jPopupMenu = new JPopupMenu();
				JMenuItem jMenuItemVizualizar= new JMenuItem();
				JMenuItem jMenuItemExcluir= new JMenuItem();

				jMenuItemVizualizar.setText("Vizualizar");
				jMenuItemExcluir.setText("Excluir");

				
				jMenuItemVizualizar.addActionListener(
						  new java.awt.event.ActionListener() {
						    // Importe a classe java.awt.event.ActionEvent
						    public void actionPerformed(ActionEvent e) { 
						    	String nome_arquivo = no_selecionado.getUserObject().toString();
						    
						    	String quebra [] = nome_arquivo.split("@");
								
								String nome_official = "";
								for(int i = 1; i < quebra.length ; i++) {
									nome_official += quebra[i];
								}
								
								String nome_pasta = "";
								if(cliente_local.getTipo_pessoa() == 0)
									nome_pasta = cliente_local.getNome_empresarial();
								else
									nome_pasta = cliente_local.getNome_fantaia();
								

						    	
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta + "\\DADOS PESSOAS\\"  + nome_official;

								if (Desktop.isDesktopSupported()) {
									try {
										Desktop desktop = Desktop.getDesktop();
										File myFile = new File(caminho_salvar);
										desktop.open(myFile);
									} catch (IOException ex) {
									}
								}
						    }
						  });
				
				jMenuItemExcluir.addActionListener(
						  new java.awt.event.ActionListener() {
						    // Importe a classe java.awt.event.ActionEvent
						    public void actionPerformed(ActionEvent e) { 
						    	/*if (JOptionPane.showConfirmDialog(isto, 
							            "Deseja Excluir este comprovante", "Exclusão", 
							            JOptionPane.YES_NO_OPTION,
							            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
									     
						    		String nome_arquivo = no_selecionado.getUserObject().toString();
						    		String quebra [] = nome_arquivo.split("@");
									String id = quebra[0];
									int i_id = Integer.parseInt(id);
									
									String nome_official = "";
									for(int i = 1; i < quebra.length ; i++) {
										nome_official += quebra[i];
									}
							    	String unidade_base_dados = configs_globais.getServidorUnidade();
									String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\\" + nome_official;

									boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
									if(excluido) {
										
									
										
										GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
										boolean excluir_documento = gerenciar_docs.removerDocumento(i_id);
										
										if(excluir_documento) {
											JOptionPane.showMessageDialog(null, "Comprovante Excluido!");

										}else {
											JOptionPane.showMessageDialog(null, "Arquivo fisico apagado, mas as informações\ndeste comprovante ainda estão no \nConsulte o administrador");

										}
										
                                        atualizarArvoreDocumentos();
										
									}else {
										JOptionPane.showMessageDialog(null, "Erro ao excluir o comprovante\nConsulte o administrador!");
									}
									
							        }
								else
								{
									
									
								}
						 */		
						    } 
						  
						  });
				
				jPopupMenu.add(jMenuItemVizualizar);
				jPopupMenu.add(jMenuItemExcluir);

				arvore_documentos.addMouseListener(
						  new java.awt.event.MouseAdapter() {
						    //Importe a classe java.awt.event.MouseEvent
						    public void mouseClicked(MouseEvent e) {
						      // Se o botão direito do mouse foi pressionado
						      if (e.getButton() == MouseEvent.BUTTON3){
						        // Exibe o popup menu na posição do mouse.
						        jPopupMenu.show(arvore_documentos, e.getX(), e.getY());
						      }
						    }
						  });
				
				    arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_docs_pessoais = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_docs_pessoais.png"));
					ImageIcon icone_comprovantes = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_comprovantes.png"));
					ImageIcon icone_outros = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_outros.png"));
 
					@Override
			            public Component getTreeCellRendererComponent(JTree tree,
			                    Object value, boolean selected, boolean expanded,
			                    boolean isLeaf, int row, boolean focused) {
						
						 DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
					        String s = node.getUserObject().toString();
					        if ("Documentos Pessoais".equals(s)) {
					        	 setOpenIcon(icone_docs_pessoais);
					             setClosedIcon(icone_docs_pessoais);

					        } else if("Comprovantes".equals(s)) {
			                    setOpenIcon(icone_comprovantes);
					             setClosedIcon(icone_comprovantes);

					        }else if("Outros".equals(s)) {
			                    setOpenIcon(icone_outros);
					             setClosedIcon(icone_outros);

					        }
					        super.getTreeCellRendererComponent(
					            tree, value, selected, expanded, isLeaf, row, hasFocus);
						
						
						
					        return this;
			            }
				            
			        });
				 
				arvore_documentos.setShowsRootHandles(true);
				arvore_documentos.setRootVisible(false);
				panel.add(arvore_documentos, "cell 0 1,grow");
				
				
				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							no_docs_pessoais.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 4) {
							// outros
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());
				
				
				
				

			}
		});

	}
	
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	public void selecionarDocumento() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo .PDF", "pdf");
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();
		entCaminhoDocumento.setText(caminho_arquivo);

	}
	
	
	public void adicionarNovoDocumento() {

		String nome, descricao, nome_arquivo, caminho_arquivo;
		int id_contrato_pai;

		nome = entNomeDocumento.getText();
		descricao = entDescricaoDocumento.getText();
		caminho_arquivo = entCaminhoDocumento.getText();

		String nome_arquivo_original_conteudo[] = caminho_arquivo.split("\"");
		String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		// copiar o arquivo para a nova pasta

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();
			
			//pegar nome da pasta
			String nome_pasta = "";
			if(cliente_local.getTipo_pessoa() == 0)
				nome_pasta = cliente_local.getNome_empresarial();
			else
				nome_pasta = cliente_local.getNome_fantaia();
			

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta + "\\DADOS PESSOAS";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();
			
			if(caminho_arquivo.length() > 10) {
				if(nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
			nome_arquivo = cliente_local.getApelido() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
					+ extensaoDoArquivo;

			String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do contrato
				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao(descricao);
				novo_documento.setNome(nome);

				String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();
				int tipo_documento = -1;

				if (s_tipo_documento.equalsIgnoreCase("Documento Pessoal")) {
					tipo_documento = 1;
				} else if (s_tipo_documento.equalsIgnoreCase("Comprovantes")) {
					tipo_documento = 2;
				} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
					tipo_documento = 3;
				} 

				novo_documento.setTipo(tipo_documento);
				novo_documento.setId_pai(0);
				novo_documento.setNome_arquivo(nome_arquivo);
				novo_documento.setId_cliente(cliente_local.getId());

				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				int cadastrar = gerenciar_doc.inserir_documento_padrao_cliente(novo_documento);
				if (cadastrar > 0) {
					JOptionPane.showMessageDialog(null, "Arquivo copiado e salvo na base de dados\nOrigem: "
							+ caminho_arquivo + "\nDestino: " + caminho_completo);
					
					entNomeDocumento.setText("");
					entDescricaoDocumento.setText("");
					entCaminhoDocumento.setText("");
					
					atualizarArvoreDocumentos();
				} else {
					JOptionPane.showMessageDialog(null,
							"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}
				}else {
					JOptionPane.showMessageDialog(null, "Nome do arquivo invalido!");

				}
			}else {
				JOptionPane.showMessageDialog(null, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
public void atualizarArvoreDocumentos() {
		
		
		new Thread() {
			@Override
			public void run() {
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(cliente_local.getId());
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                
				root.removeAllChildren();
			
				no_docs_pessoais.removeAllChildren();
				no_comprovantes.removeAllChildren();
				no_outros.removeAllChildren();

				 no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				 no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				 no_outros = new DefaultMutableTreeNode("Outros");
				 
				root.add(no_docs_pessoais);
				root.add(no_comprovantes);
				root.add(no_outros);

			
				
				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							//model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root, root.getChildCount());
							
							
							no_docs_pessoais.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// carregamentos
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 4) {
							// outros
							no_outros.add(new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}
			    model.reload(); //this notifies the listeners and changes the GUI
				
				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());


			}

		 
		});
		
	     } 
	  }.start();

	}
	



   public void atualizarInfo() {
	   GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	   cliente_local = gerenciar.getCliente(cliente_local.getId());
	   
		//lblIe, lblIE, lblStatus, lblEndereco;
          if(cliente_local.getTipo_pessoa() == 0) {
        	  lblTipoPessoa.setText("Fisica");
        	  lblTipoIdentificacao.setText("CPF:");
        	  lblIdentificacao.setText(cliente_local.getCpf());
          }else {
        	  lblTipoPessoa.setText("Juridica");
        	  lblTipoIdentificacao.setText("CNPJ:");
        	  lblIdentificacao.setText(cliente_local.getCnpj());
          }
	   
          lblIE.setText(cliente_local.getIe());
          String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", " + cliente_local.getBairro()
          + ", " + cliente_local.getCidade() + "/"+ cliente_local.getUf() + " Cep: " + cliente_local.getCep();
	      lblEndereco.setText(endereco_completo);

	   
	   
   }

   public void setInfo() {
	   
	   GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	   cliente_local = gerenciar.getCliente(cliente_local.getId());
	   
		//lblIe, lblIE, lblStatus, lblEndereco;
          if(cliente_local.getTipo_pessoa() == 0) {
        	  lblTipoPessoa.setText("Fisica");
        	  lblTipoIdentificacao.setText("CPF:");
        	  lblIdentificacao.setText(cliente_local.getCpf());
          }else {
        	  lblTipoPessoa.setText("Juridica");
        	  lblTipoIdentificacao.setText("CNPJ:");
        	  lblIdentificacao.setText(cliente_local.getCnpj());
          }
	   
          lblIE.setText(cliente_local.getIe());
          String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", " + cliente_local.getBairro()
          + ", " + cliente_local.getCidade() + "/"+ cliente_local.getUf() + " Cep: " + cliente_local.getCep();
	      lblEndereco.setText(endereco_completo);
	   
   }
}
