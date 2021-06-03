
package main.java.gui;

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
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;



import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import main.java.cadastros.AcrescimoCompleto;
import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioDescontos;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DescontoCompleto;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDescontos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.CartaoPontoCompleto;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;

public class TelaGerenciarFuncionario extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
    private TelaGerenciarFuncionario isto;
    private KGradientPanel menu_lateral;
    private  JPanel panel_docs;
	private JTree arvore_documentos;

	
	private ArrayList<DescontoCompleto> lista_descontos = new ArrayList<>();
	
	private ArrayList<AcrescimoCompleto> lista_acrescimos = new ArrayList<>();

    DefaultMutableTreeNode no_comprovantes; 
	DefaultMutableTreeNode no_docs_pessoais ;
	DefaultMutableTreeNode no_outros ;
	private DefaultMutableTreeNode no_selecionado;
    private CadastroFuncionario funcionario_local;
    private  JComboBox cBTipoDocumento ;
    private TelaTodasNotasFiscais telaTodasNotasFiscais;
    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	
	private DescontoTableModel modelo_desconto_completo = new DescontoTableModel();
	private AcrescimoTableModel modelo_acrescimo_completo = new AcrescimoTableModel();
	private CartaoTableModel modelo_cartoes = new CartaoTableModel();
	private ArrayList<CartaoPontoCompleto> lista_cartoes_completos = new ArrayList<>();
	private  JTextArea entDescricaoDocumento;
	private ContratosTrabalhoTableModel modelo_contratos = new ContratosTrabalhoTableModel();
	private TelaRomaneios telaRomaneio;
	private JLabel lblTipoPessoa, lblTipoIdentificacao, lblCpf, lblStatus, lblEndereco;
    private  JLabel lblTotalContratosConcluidosComprador, lblTotalContratosComprador, lblTotalContratosAbertosComprador;
    private JLabel lblNome;
    private JLabel lblTotalContratosConcluidosVendedor, lblTotalContratosVendedor, lblTotalContratosAbertosVendedor, lblNivel;
    private JTable tabela_contratos;
    private ArrayList<CadastroFuncionarioAdmissao> lista_contratos = new ArrayList<>();
    private JTable table_descontos ;
    private JTable tabela_registros_ponto;
    private JTable table_jornada_trabalho;
    
    public TelaGerenciarFuncionario(CadastroFuncionario funcionario, Window janela_pai) {
		//setModal(true);
		
		
		getDadosGlobais();

		 isto = this;
		 funcionario_local = funcionario;
		
		setResizable(true);
		setTitle("E-Contract - Gerenciar Colaborador");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1305, 701);
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		
		JPanel painelContratos = new JPanel();
		painelContratos.setVisible(false);
		
		
		JPanel painelRegistrodePonto = new JPanel();
		painelRegistrodePonto.setBackground(Color.WHITE);
		painelRegistrodePonto.setVisible(false);
		
		   
		painelRegistrodePonto.setEnabled(false);
		painelRegistrodePonto.setBounds(198, 153, 1091, 468);
		painelPrincipal.add(painelRegistrodePonto);
		painelRegistrodePonto.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		painelRegistrodePonto.add(tabbedPane_1, "cell 0 0 2 1,grow");
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Registro de Ponto", null, panel_4, null);
		panel_4.setLayout(new MigLayout("", "[grow]", "[][]"));
		
	
		
		tabela_registros_ponto = new JTable();
		tabela_registros_ponto.setRowHeight(30);
		
		JScrollPane scrollPaneRegistrosPonto = new JScrollPane(tabela_registros_ponto);
		panel_4.add(scrollPaneRegistrosPonto, "cell 0 0");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		tabbedPane_1.addTab("Cartões de Ponto", null, panel_5, null);
		panel_5.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		
		JTable tabela_cartoes = new JTable(modelo_cartoes);
		tabela_cartoes.setRowHeight(30);
		
		JScrollPane scrollPaneCartoes = new JScrollPane(tabela_cartoes);
		panel_5.add(scrollPaneCartoes, "cell 0 0,grow");
		
		JButton btnExcluirAssociacao = new JButton("Excluir Associação");
		btnExcluirAssociacao.setForeground(Color.WHITE);
		btnExcluirAssociacao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnExcluirAssociacao.setBackground(new Color(153, 0, 0));
		panel_5.add(btnExcluirAssociacao, "flowx,cell 0 1,alignx right");
		
		JButton btnNovaAssociacao = new JButton("Nova Associação");
		btnNovaAssociacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaAplicarAssociacaoFuncionarioCartao tela = new TelaAplicarAssociacaoFuncionarioCartao(funcionario_local, isto);
				tela.setVisible(true);
				
			}
		});
		btnNovaAssociacao.setForeground(Color.WHITE);
		btnNovaAssociacao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNovaAssociacao.setBackground(new Color(0, 51, 0));
		panel_5.add(btnNovaAssociacao, "cell 0 1,alignx right");
		scrollPaneCartoes.getViewport().setBackground(Color.white);
		
		
		scrollPaneRegistrosPonto.getViewport().setBackground(Color.white);
		
		
		
		
		
		painelContratos.setBackground(Color.WHITE);
		painelContratos.setForeground(Color.WHITE);
		painelContratos.setBounds(198, 154, 1091, 467);
		painelPrincipal.add(painelContratos);
		painelContratos.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		painelContratos.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Contratos de Trabalho", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[grow][grow][][]", "[grow][grow][]"));
		
		
		
		tabela_contratos = new JTable(modelo_contratos);
		
				tabela_contratos.setRowHeight(30);
				
				
				JScrollPane scrollPane = new JScrollPane(tabela_contratos);
				scrollPane.getViewport().setBackground(Color.white);
				panel_1.add(scrollPane, "cell 0 0 4 1,grow");
				
				JButton btnNewButton = new JButton("Excluír");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if (JOptionPane.showConfirmDialog(isto, 
					            "Deseja excluir o Contrato selecionado?", "Excluir", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							
								boolean exclusao = new GerenciarBancoFuncionariosContratoTrabalho().removercontrato(getContratoSelecionado().getId_contrato());
								if(exclusao) {
									JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
								}else {
									JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

								}

								 pesquisar_contratos();
					        }
						
					}
				});
				btnNewButton.setBackground(new Color(204, 0, 0));
				btnNewButton.setForeground(Color.WHITE);
				btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
				panel_1.add(btnNewButton, "cell 1 2,alignx right");
				
				JButton btnNewButton_1 = new JButton("Editar");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TelaFuncionariosCadastroContratoTrabalho tela = new TelaFuncionariosCadastroContratoTrabalho(1, funcionario_local, getContratoSelecionado(), isto);
						tela.setVisible(true);
					}
				});
				btnNewButton_1.setBackground(new Color(255, 153, 0));
				btnNewButton_1.setForeground(Color.WHITE);
				btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
				panel_1.add(btnNewButton_1, "cell 2 2");
				
				JButton btnNewButton_2 = new JButton("Adicionar");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						TelaFuncionariosCadastroContratoTrabalho tela = new TelaFuncionariosCadastroContratoTrabalho(0,funcionario_local, null, isto);
						tela.setVisible(true);
						
					}
				});
				btnNewButton_2.setBackground(new Color(0, 51, 0));
				btnNewButton_2.setForeground(Color.WHITE);
				btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
				panel_1.add(btnNewButton_2, "cell 3 2");
				
				JPanel panel_2 = new JPanel();
				panel_2.setBackground(Color.WHITE);
				tabbedPane.addTab("Descontos e Benefícios Fixos", null, panel_2, null);
				panel_2.setLayout(new MigLayout("", "[grow][][grow]", "[][][grow][]"));
				
				JLabel lblObservaoDescontoRelativo = new JLabel("Observação: Desconto relativo a INSS e IRRF são aplicados de forma automática de acordo com a legislação trabalhista vigente");
				lblObservaoDescontoRelativo.setForeground(Color.RED);
				lblObservaoDescontoRelativo.setFont(new Font("SansSerif", Font.PLAIN, 14));
				panel_2.add(lblObservaoDescontoRelativo, "cell 0 1 3 1");
				
				
				 table_descontos =  new JTable(modelo_desconto_completo);
				 
				 table_descontos.setRowHeight(30);
				 
				 JScrollPane scrollDescontos = new JScrollPane(table_descontos);
				 scrollDescontos.getViewport().setBackground(Color.white);
				 panel_2.add(scrollDescontos, "cell 0 2,grow");
				 
				 JSeparator separator = new JSeparator();
				 separator.setBackground(Color.BLACK);
				 panel_2.add(separator, "cell 1 2 1 2,growx");
				 
				 JTable table_acrescimos =  new JTable(modelo_acrescimo_completo);
				 
				 table_descontos.setRowHeight(30);
				 
				 
				 JScrollPane scrollAcrescimos = new JScrollPane(table_acrescimos);
				 panel_2.add(scrollAcrescimos, "cell 2 2,grow");
				 
				 JButton btnRemoverDesconto = new JButton("Remover");
				 btnRemoverDesconto.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		
				 		GerenciarBancoFuncionariosDescontos gerenciar = new GerenciarBancoFuncionariosDescontos();
				 		DescontoCompleto cad_excluir = getDescontoSelecionado();
				 		if(cad_excluir != null) {
				 			boolean excluir  = gerenciar.removerRelacaoContratoDesconto(cad_excluir);
				 			if(excluir) {
				 				JOptionPane.showMessageDialog(isto, "Desconto Removido");
				 				
				 				pesquisar_descontos_por_contrato();

				 			}else {
				 				JOptionPane.showMessageDialog(isto, "Erro ao desaplicar o desconto");
				 			}
				 		}
				 		
				 		
				 	}
				 });
				 btnRemoverDesconto.setBackground(new Color(255, 0, 0));
				 btnRemoverDesconto.setForeground(Color.WHITE);
				 btnRemoverDesconto.setFont(new Font("SansSerif", Font.BOLD, 16));
				 panel_2.add(btnRemoverDesconto, "flowx,cell 0 3,alignx right");
				 
				 JButton btnAdicionarDescont = new JButton("Adicionar");
				 btnAdicionarDescont.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		TelaAplicarContratoDesconto tela = new TelaAplicarContratoDesconto(funcionario_local, isto);
				 		tela.setVisible(true);
				 	}
				 });
				 btnAdicionarDescont.setBackground(new Color(0, 51, 51));
				 btnAdicionarDescont.setForeground(Color.WHITE);
				 btnAdicionarDescont.setFont(new Font("SansSerif", Font.BOLD, 16));
				 panel_2.add(btnAdicionarDescont, "cell 0 3,alignx right");
				 
				 JButton btnRemoverAcrescimo = new JButton("Remover");
				 btnRemoverAcrescimo.setForeground(Color.WHITE);
				 btnRemoverAcrescimo.setFont(new Font("SansSerif", Font.BOLD, 16));
				 btnRemoverAcrescimo.setBackground(Color.RED);
				 panel_2.add(btnRemoverAcrescimo, "flowx,cell 2 3,alignx right");
				 
				 JButton btnAdicionarAcrescimo = new JButton("Adicionar");
				 btnAdicionarAcrescimo.setForeground(Color.WHITE);
				 btnAdicionarAcrescimo.setFont(new Font("SansSerif", Font.BOLD, 16));
				 btnAdicionarAcrescimo.setBackground(new Color(0, 51, 51));
				 panel_2.add(btnAdicionarAcrescimo, "cell 2 3,alignx right");
				 
				 JPanel panel_3 = new JPanel();
				 tabbedPane.addTab("Jornada de Trabalho", null, panel_3, null);
				 panel_3.setLayout(new MigLayout("", "[grow]", "[grow][]"));
				 
				
				 table_jornada_trabalho = new JTable();
				 table_jornada_trabalho.setRowHeight(30);
		
				 JScrollPane scrollPane_1 = new JScrollPane(table_jornada_trabalho);
				 scrollPane_1.getViewport().setBackground(Color.white);
				 panel_3.add(scrollPane_1, "cell 0 0,grow");
				 
				 JButton btnNewButton_3 = new JButton("Nova Jornada");
				 btnNewButton_3.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		TelaFuncionariosCadastroJornadaTrabalho tela = new TelaFuncionariosCadastroJornadaTrabalho(funcionario_local, isto);
				 		tela.setVisible(true);
				 		
				 	}
				 });
				 panel_3.add(btnNewButton_3, "cell 0 1,alignx right");
		
		JPanel painelDadosIniciais = new JPanel();
		painelDadosIniciais.setBackground(new Color(0, 128, 128));
		painelDadosIniciais.setBounds(198, 153, 1091, 468);
		painelPrincipal.add(painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFuncionariosCadastroFuncionario telaEdicao = new TelaFuncionariosCadastroFuncionario(1, funcionario_local, isto);
				telaEdicao.setVisible(true);
			}
		});
		btnEditar.setBounds(629, 297, 89, 23);
		painelDadosIniciais.add(btnEditar);
		
		JPanel painelInfo = new JPanel();
		painelInfo.setBackground(new Color(0, 128, 128));
		painelInfo.setBounds(22, 11, 696, 275);
		painelDadosIniciais.add(painelInfo);
		painelInfo.setLayout(new MigLayout("", "[][]", "[][][][][][][]"));
		
		 
		  lblTipoIdentificacao = new JLabel("CPF:");
		  lblTipoIdentificacao.setForeground(Color.WHITE);
		  lblTipoIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 14));
		  painelInfo.add(lblTipoIdentificacao, "cell 0 1,alignx right");
		  
		   lblCpf = new JLabel("");
		   lblCpf.setForeground(Color.WHITE);
		   lblCpf.setFont(new Font("Tahoma", Font.BOLD, 16));
		   painelInfo.add(lblCpf, "cell 1 1");
		     
		     JLabel lblEndereo = new JLabel("Endereço:");
		     lblEndereo.setForeground(Color.WHITE);
		     lblEndereo.setFont(new Font("Tahoma", Font.BOLD, 14));
		     painelInfo.add(lblEndereo, "cell 0 3,alignx right");
		     
		      lblEndereco = new JLabel("Rodovia MG 188, km 242, Zona Rural, Guarda-Mor/MG 38570-000");
		      lblEndereco.setForeground(Color.WHITE);
		      lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 16));
		      painelInfo.add(lblEndereco, "cell 1 3");
		    
		     lblStatus = new JLabel("");
		     lblStatus.setForeground(Color.WHITE);
		     lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		     painelInfo.add(lblStatus, "cell 1 4,alignx left");
				
		
			
		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 255, 204);
		menu_lateral.kEndColor = Color.BLUE;
		menu_lateral.setBounds(0, 0, 200, 662);
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
		 
		
		 

		 KGradientPanel panelTopo = new KGradientPanel();
		 panelTopo.kStartColor = new Color(51, 0, 51);
		 panelTopo.kEndColor = new Color(0, 51, 51);
		 panelTopo.setBounds(172, 65, 1117, 90);
		 painelPrincipal.add(panelTopo);
		 panelTopo.setLayout(null);
		 
		  lblNome = new JLabel("New label");
		 lblNome.setForeground(Color.WHITE);
		 lblNome.setFont(new Font("SansSerif", Font.BOLD, 24));
		 lblNome.setBounds(54, 33, 1007, 37);
		 panelTopo.add(lblNome);
		 
		
		 
	
		 
		 JButton btnSair = new JButton("Sair");
		 btnSair.setBounds(1183, 633, 89, 23);
		 painelPrincipal.add(btnSair);
		       
		        panel_docs = new JPanel();
		        panel_docs.setBackground(Color.WHITE);
		        panel_docs.setBounds(43, 11, 315, 336);
		        
		         
		         JPanel painelDocumentos = new JPanel();
		         painelDocumentos.setVisible(false);
		         painelDocumentos.setEnabled(false);
		         painelDocumentos.setBounds(198, 153, 1091, 468);
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
		 btnSair.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		isto.dispose();
		 	}
		 });

		 JLabel btnDeposito = new JLabel("Registro de Ponto");
		 btnDeposito.setForeground(Color.WHITE);
		 btnDeposito.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnDeposito.setBackground(new Color(0, 0, 0, 100));
		 btnDeposito.setBounds(10, 116, 146, 20);
		 panel.add(btnDeposito);
			
		
		 btnDadosIniciais.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					painelDocumentos.setEnabled(false);
					painelDocumentos.setVisible(false);
					
					painelDadosIniciais.setEnabled(true);
					painelDadosIniciais.setVisible(true);

					painelContratos.setEnabled(false);
					painelContratos.setVisible(false);

					painelRegistrodePonto.setEnabled(false);
					painelRegistrodePonto.setVisible(false);
					
					btnDadosIniciais.setOpaque(true);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();

					btnDocumentos.setOpaque(false);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();
					
					btnDeposito.setOpaque(false);
					btnDeposito.setBackground(new Color(0, 0, 0, 100));

					btnDeposito.repaint();
					btnDeposito.updateUI();

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



					painelRegistrodePonto.setEnabled(false);
					painelRegistrodePonto.setVisible(false);
					
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

					btnDeposito.setOpaque(false);
					btnDeposito.setBackground(new Color(0, 0, 0, 100));

					btnDeposito.repaint();
					btnDeposito.updateUI();
		
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


					painelRegistrodePonto.setEnabled(false);
					painelRegistrodePonto.setVisible(false);
					
					btnContratos.setOpaque(true);
					btnContratos.setBackground(new Color(0, 0, 0, 100));

					btnContratos.repaint();
					btnContratos.updateUI();
					
					
					btnDadosIniciais.setOpaque(false);
					btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

					btnDadosIniciais.repaint();
					btnDadosIniciais.updateUI();
					
					btnDeposito.setOpaque(false);
					btnDeposito.setBackground(new Color(0, 0, 0, 100));

					btnDeposito.repaint();
					btnDeposito.updateUI();
		

					btnDocumentos.setOpaque(false);
					btnDocumentos.setBackground(new Color(0, 0, 0, 100));

					btnDocumentos.repaint();
					btnDocumentos.updateUI();

		
				

				}
			});
		 
		 
	
		 
		 btnDeposito.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);
				
				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				painelContratos.setEnabled(false);
				painelContratos.setVisible(false);

				painelRegistrodePonto.setEnabled(true);
				painelRegistrodePonto.setVisible(true);
				
				btnDeposito.setOpaque(true);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();
				
				
				btnDadosIniciais.setOpaque(false);
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
		

	
		   setInformacoesDocumentos();
		   pesquisar_cartoes();
	     pesquisar_contratos();
	     pesquisar_descontos_por_contrato();
	     setInfo();
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
    
    public void pesquisar_contratos() {
    	
    	modelo_contratos.onRemoveAll();
    	lista_contratos.clear();
    	
    	GerenciarBancoFuncionariosContratoTrabalho gerenciar  = new GerenciarBancoFuncionariosContratoTrabalho();
    	
    	lista_contratos = gerenciar.getcontratosPorColaborador(funcionario_local.getId_funcionario());
    	
    	for(CadastroFuncionarioAdmissao cad : lista_contratos) {
    		modelo_contratos.onAdd(cad);
    	}
    	
    }
	
    
 public void pesquisar_cartoes() {
    	
    	modelo_cartoes.onRemoveAll();
    	lista_cartoes_completos.clear();
    	
    	GerenciarBancoCartaoPonto gerenciar  = new GerenciarBancoCartaoPonto();
    	
    	lista_cartoes_completos = gerenciar.getCartoesPorFuncionario(funcionario_local.getId_funcionario());
    	
    	for(CartaoPontoCompleto cad : lista_cartoes_completos) {
    		modelo_cartoes.onAdd(cad);
    	}
    	
    }
	

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(funcionario_local.getId_funcionario());
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
								
								
								String nome_pasta = "colaborador_" + funcionario_local.getCpf();

						    	
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\" + nome_pasta + "\\documentos\\"  + nome_official;

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

						} else if (doc.getTipo() == 3) {
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

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o documento a anexar!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				entCaminhoDocumento.setText(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
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
			String nome_pasta = "colaborador_" + funcionario_local.getCpf();
				

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\" + nome_pasta + "\\documentos";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();
			
			if(caminho_arquivo.length() > 10) {
				if(nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
			nome_arquivo = funcionario_local.getApelido() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
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
				novo_documento.setId_cliente(funcionario_local.getId_funcionario());

				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				int cadastrar = gerenciar_doc.inserir_documento_padrao_cliente(novo_documento);
				if (cadastrar > 0) {
					JOptionPane.showMessageDialog(isto, "Arquivo copiado e salvo na base de dados\nOrigem: "
							+ caminho_arquivo + "\nDestino: " + caminho_completo);
					
					entNomeDocumento.setText("");
					entDescricaoDocumento.setText("");
					entCaminhoDocumento.setText("");
					
					atualizarArvoreDocumentos();
				} else {
					JOptionPane.showMessageDialog(isto,
							"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}
				}else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			}else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
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
		
				  //telaRomaneios
				  telaRomaneio = dados.getTelaRomaneios();
				  
				  //telaTodasNotasFiscais
				  telaTodasNotasFiscais = dados.getTelaTodasNotasFiscais();
	}
	
	
public void atualizarArvoreDocumentos() {
		
		
		new Thread() {
			@Override
			public void run() {
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(funcionario_local.getId_funcionario());
		
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
	

	

   public void atualizarFuncionario() {
	   funcionario_local = new GerenciarBancoFuncionarios().getfuncionario(funcionario_local.getId_funcionario());
	
	   
   }
   
   
   
   public class ContratosTrabalhoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int status = 1;
		private final int data_admissao = 2;
		private final int tipo_contrato = 3;
		private final int cargo = 4;
		private final int funcao = 5;
		private final int salario = 6;


		Locale ptBr = new Locale("pt", "BR");

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Data Admissão", "Tipo Contrato", "Cargo",
				"Função", "Salário"};
		private final ArrayList<CadastroFuncionarioAdmissao> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ContratosTrabalhoTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case status:
				return String.class;
			case data_admissao:
				return String.class;
			case tipo_contrato:
				return String.class;
			case cargo:
				return String.class;
			case funcao:
				return String.class;
			case salario:
				return String.class;
	
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colunas[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			CadastroFuncionarioAdmissao dado = dados.get(rowIndex);
		
			
			switch (columnIndex) {
			case id:
				return dado.getId_contrato();
			case status:{
			
				if(dado.getStatus() == 0) {
					return "Ativo";
				}else if(dado.getStatus() == 1) {
					return "Inativo";
				}
				
			}
			case data_admissao:{
				return dado.getData_admissao();		
				
			}
			case tipo_contrato:{
				return dado.getTipo_contrato();
			}
			case cargo:
				return dado.getCargo();
			case funcao:
				return dado.getFuncao();
			case salario:{
				
				return  NumberFormat.getCurrencyInstance(ptBr).format(dado.getSalario());

			}
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroFuncionarioAdmissao ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioAdmissao getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioAdmissao dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioAdmissao dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioAdmissao> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(CadastroFuncionarioAdmissao dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}
   
   }
   
   
   
	public CadastroFuncionarioAdmissao getContratoSelecionado() {
		int indiceDaLinha = tabela_contratos.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_contratos.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoFuncionariosContratoTrabalho gerenciar_cont = new GerenciarBancoFuncionariosContratoTrabalho();
		return gerenciar_cont.getcontrato(id_selecionado);
		
	}
	
	public void setInfo() {
		try {
			lblCpf.setText(formatarCpf(funcionario_local.getCpf()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblNome.setText(funcionario_local.getNome() + " " + funcionario_local.getSobrenome());
		
	}
	
	
	 public static String formatarCpf(String texto ) throws ParseException {
	        MaskFormatter mf = new MaskFormatter("###.###.###-##");
	        mf.setValueContainsLiteralCharacters(false);
	        return mf.valueToString(texto);
	    }
	 
	 public static String formatarCNPJ(String texto ) throws ParseException {
	        MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
	        mf.setValueContainsLiteralCharacters(false);
	        return mf.valueToString(texto);
	    }
	 
	 public static String formatarCep(String texto ) throws ParseException {
	        MaskFormatter mf = new MaskFormatter("#####-###");
	        mf.setValueContainsLiteralCharacters(false);
	        return mf.valueToString(texto);
	    }
	 
	 
	 
	 
		public  class DescontoTableModel extends AbstractTableModel {

			// constantes p/identificar colunas
			private final int id = 0;
			private final int descricao = 1;
			private final int referencia = 2;
			private final int porcentagem = 3;
			private final int valor_fixo = 4;
			
		
			private final String colunas[] = { "Contrato de Trabalho ID", "Descrição","Referência",  "Porcentagem", "Valor Fixo"};
			 Locale ptBr = new Locale("pt", "BR");

			private final ArrayList<DescontoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																					// genérica de
			// nfs

			public DescontoTableModel() {

			}

			@Override
			public int getColumnCount() {
				// retorna o total de colunas
				return colunas.length;
			}

			@Override
			public int getRowCount() {
				// retorna o total de linhas na tabela
				return dados.size();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// retorna o tipo de dado, para cada coluna
				switch (columnIndex) {
		
				case id:
					return Integer.class;
				case descricao:
					return String.class;
				case referencia:
					return String.class;
				case porcentagem:
					return String.class;
				case valor_fixo:
					return String.class;
			

				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public String getColumnName(int columnIndex) {
				return colunas[columnIndex];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// retorna o valor conforme a coluna e linha
				NumberFormat z = NumberFormat.getNumberInstance();

				// pega o dados corrente da linha
				DescontoCompleto desconto = dados.get(rowIndex);

				// retorna o valor da coluna
				switch (columnIndex) {

				case id:
					return desconto.getId_contrato_trabalho();
				case descricao:
					return desconto.getId_desconto() + "-" + desconto.getDescricao();
				case referencia:
					return desconto.getReferencia();
				case porcentagem:
					return desconto.getPorcentagem();
				case valor_fixo:
					return desconto.getValor();
				
				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// metodo identifica qual coluna é editavel

				// só iremos editar a coluna BENEFICIO,
				// que será um checkbox por ser boolean

				return false;
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				DescontoCompleto recebimento = dados.get(rowIndex);

			}

			// Métodos abaixo são para manipulação de dados

			/**
			 * retorna o valor da linha indicada
			 * 
			 * @param rowIndex
			 * @return
			 */
			public DescontoCompleto getValue(int rowIndex) {
				return dados.get(rowIndex);
			}

			/**
			 * retorna o indice do objeto
			 * 
			 * @param empregado
			 * @return
			 */
			public int indexOf(DescontoCompleto nota) {
				return dados.indexOf(nota);
			}

			/**
			 * add um empregado á lista
			 * 
			 * @param empregado
			 */
			public void onAdd(DescontoCompleto nota) {
				dados.add(nota);
				fireTableRowsInserted(indexOf(nota), indexOf(nota));
			}

			/**
			 * add uma lista de empregados
			 * 
			 * @param dadosIn
			 */
			public void onAddAll(ArrayList<DescontoCompleto> dadosIn) {
				dados.addAll(dadosIn);
				fireTableDataChanged();
			}

			/**
			 * remove um registro da lista, através do indice
			 * 
			 * @param rowIndex
			 */
			public void onRemove(int rowIndex) {
				dados.remove(rowIndex);
				fireTableRowsDeleted(rowIndex, rowIndex);
			}

			/**
			 * remove um registro da lista, através do objeto
			 * 
			 * @param empregado
			 */
			public void onRemove(DescontoCompleto nota) {
				int indexBefore = indexOf(nota);// pega o indice antes de apagar
				dados.remove(nota);
				fireTableRowsDeleted(indexBefore, indexBefore);
			}

			/**
			 * remove todos registros da lista
			 */
			public void onRemoveAll() {
				dados.clear();
				fireTableDataChanged();
			}

		}
		
		public  class AcrescimoTableModel extends AbstractTableModel {

			// constantes p/identificar colunas
			private final int id = 0;
			private final int descricao = 1;
			private final int referencia = 2;
			private final int porcentagem = 3;
			private final int valor_fixo = 4;
			
		
			private final String colunas[] = { "Contrato de Trabalho ID", "Descrição","Referência",  "Porcentagem", "Valor Fixo"};
			 Locale ptBr = new Locale("pt", "BR");

			private final ArrayList<AcrescimoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																					// genérica de
			// nfs

			public AcrescimoTableModel() {

			}

			@Override
			public int getColumnCount() {
				// retorna o total de colunas
				return colunas.length;
			}

			@Override
			public int getRowCount() {
				// retorna o total de linhas na tabela
				return dados.size();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// retorna o tipo de dado, para cada coluna
				switch (columnIndex) {
		
				case id:
					return Integer.class;
				case descricao:
					return String.class;
				case referencia:
					return String.class;
				case porcentagem:
					return String.class;
				case valor_fixo:
					return String.class;
			

				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public String getColumnName(int columnIndex) {
				return colunas[columnIndex];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// retorna o valor conforme a coluna e linha
				NumberFormat z = NumberFormat.getNumberInstance();

				// pega o dados corrente da linha
				AcrescimoCompleto acrescimo = dados.get(rowIndex);

				// retorna o valor da coluna
				switch (columnIndex) {

				case id:
					return acrescimo.getId_contrato_trabalho();
				case descricao:
					return acrescimo.getId_acrescimo() + "-" + acrescimo.getDescricao();
				case referencia:
					return acrescimo.getReferencia();
				case porcentagem:
					return acrescimo.getPorcentagem();
				case valor_fixo:
					return acrescimo.getValor();
				
				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// metodo identifica qual coluna é editavel

				// só iremos editar a coluna BENEFICIO,
				// que será um checkbox por ser boolean

				return false;
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				AcrescimoCompleto recebimento = dados.get(rowIndex);

			}

			// Métodos abaixo são para manipulação de dados

			/**
			 * retorna o valor da linha indicada
			 * 
			 * @param rowIndex
			 * @return
			 */
			public AcrescimoCompleto getValue(int rowIndex) {
				return dados.get(rowIndex);
			}

			/**
			 * retorna o indice do objeto
			 * 
			 * @param empregado
			 * @return
			 */
			public int indexOf(AcrescimoCompleto nota) {
				return dados.indexOf(nota);
			}

			/**
			 * add um empregado á lista
			 * 
			 * @param empregado
			 */
			public void onAdd(AcrescimoCompleto nota) {
				dados.add(nota);
				fireTableRowsInserted(indexOf(nota), indexOf(nota));
			}

			/**
			 * add uma lista de empregados
			 * 
			 * @param dadosIn
			 */
			public void onAddAll(ArrayList<AcrescimoCompleto> dadosIn) {
				dados.addAll(dadosIn);
				fireTableDataChanged();
			}

			/**
			 * remove um registro da lista, através do indice
			 * 
			 * @param rowIndex
			 */
			public void onRemove(int rowIndex) {
				dados.remove(rowIndex);
				fireTableRowsDeleted(rowIndex, rowIndex);
			}

			/**
			 * remove um registro da lista, através do objeto
			 * 
			 * @param empregado
			 */
			public void onRemove(AcrescimoCompleto nota) {
				int indexBefore = indexOf(nota);// pega o indice antes de apagar
				dados.remove(nota);
				fireTableRowsDeleted(indexBefore, indexBefore);
			}

			/**
			 * remove todos registros da lista
			 */
			public void onRemoveAll() {
				dados.clear();
				fireTableDataChanged();
			}

		}
		
		
		public void pesquisar_descontos_por_contrato() {

			GerenciarBancoFuncionariosDescontos gerenciar = new GerenciarBancoFuncionariosDescontos();
			lista_descontos.clear();
			modelo_desconto_completo.onRemoveAll();

			for(CadastroFuncionarioAdmissao cad : lista_contratos) {
				
				ArrayList<DescontoCompleto> descontos_locais = gerenciar.getdescontosPorContratoTrabalho(cad.getId_contrato());
				
				lista_descontos.addAll(descontos_locais);
				modelo_desconto_completo.onAddAll(descontos_locais);
				
			}
			
			
			
		}
		
		public DescontoCompleto getDescontoSelecionado() {
			int indiceDaLinha = table_descontos.getSelectedRow();

			DescontoCompleto desc = (DescontoCompleto) modelo_desconto_completo.getValue(indiceDaLinha);
			return desc;
		}
		


		public  class CartaoTableModel extends AbstractTableModel {

			// constantes p/identificar colunas
			private final int id_funcionario = 0;
			private final int nome_funcionario = 1;
			
			private final int id_cartao = 2;
			private final int uid_cartao = 3;

			
		
			private final String colunas[] = { "ID Colaborador", "Nome Funcionário", "ID Cartão", "UID CARTÃO"};
			 Locale ptBr = new Locale("pt", "BR");

			private final ArrayList<CartaoPontoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																					// genérica de
			// nfs

			public CartaoTableModel() {

			}

			@Override
			public int getColumnCount() {
				// retorna o total de colunas
				return colunas.length;
			}

			@Override
			public int getRowCount() {
				// retorna o total de linhas na tabela
				return dados.size();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// retorna o tipo de dado, para cada coluna
				switch (columnIndex) {
		
				
				case id_funcionario:
					return Integer.class;
				case nome_funcionario:
					return String.class;
				case id_cartao:
					return Integer.class;
				case uid_cartao:
					return String.class;
				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public String getColumnName(int columnIndex) {
				return colunas[columnIndex];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// retorna o valor conforme a coluna e linha
				NumberFormat z = NumberFormat.getNumberInstance();

				// pega o dados corrente da linha
				CartaoPontoCompleto cartao = dados.get(rowIndex);

				// retorna o valor da coluna
				switch (columnIndex) {

				case id_funcionario:
					return cartao.getId_funcionario();
				case nome_funcionario:
					return cartao.getNome_funcionario();
				case id_cartao:
					return cartao.getId_cartao();
				case uid_cartao:
					return cartao.getUid();
			
				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// metodo identifica qual coluna é editavel

				// só iremos editar a coluna BENEFICIO,
				// que será um checkbox por ser boolean

				return false;
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				CartaoPontoCompleto recebimento = dados.get(rowIndex);

			}

			// Métodos abaixo são para manipulação de dados

			/**
			 * retorna o valor da linha indicada
			 * 
			 * @param rowIndex
			 * @return
			 */
			public CartaoPontoCompleto getValue(int rowIndex) {
				return dados.get(rowIndex);
			}

			/**
			 * retorna o indice do objeto
			 * 
			 * @param empregado
			 * @return
			 */
			public int indexOf(CartaoPontoCompleto nota) {
				return dados.indexOf(nota);
			}

			/**
			 * add um empregado á lista
			 * 
			 * @param empregado
			 */
			public void onAdd(CartaoPontoCompleto nota) {
				dados.add(nota);
				fireTableRowsInserted(indexOf(nota), indexOf(nota));
			}

			/**
			 * add uma lista de empregados
			 * 
			 * @param dadosIn
			 */
			public void onAddAll(ArrayList<CartaoPontoCompleto> dadosIn) {
				dados.addAll(dadosIn);
				fireTableDataChanged();
			}

			/**
			 * remove um registro da lista, através do indice
			 * 
			 * @param rowIndex
			 */
			public void onRemove(int rowIndex) {
				dados.remove(rowIndex);
				fireTableRowsDeleted(rowIndex, rowIndex);
			}

			/**
			 * remove um registro da lista, através do objeto
			 * 
			 * @param empregado
			 */
			public void onRemove(CartaoPontoCompleto nota) {
				int indexBefore = indexOf(nota);// pega o indice antes de apagar
				dados.remove(nota);
				fireTableRowsDeleted(indexBefore, indexBefore);
			}

			/**
			 * remove todos registros da lista
			 */
			public void onRemoveAll() {
				dados.clear();
				fireTableDataChanged();
			}

		}
}
