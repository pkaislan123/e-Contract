package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import net.miginfocom.swing.MigLayout;
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

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
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
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.graficos.JPanelGraficoRecebimento;
import main.java.gui.TelaCadastroCliente;
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
import main.java.manipular.MonitorarRomaneios;
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
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
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

import javax.swing.border.LineBorder;
import javax.swing.JTextField;



public class TelaCadastroRomaneio extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaCadastroRomaneio isto;
    private JDialog telaPai;
    private JPanel painel_vizualizar;
    private SwingController controller = null;
	private SwingViewBuilder factory;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private JPanel painelVizualizarRomaneio;
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Classificador:");
	private final JLabel lblSilo = new JLabel("Umidade 2:");
	private final JLabel lblNewLabel_1 = new JLabel("Classificação 2:");
	private final JLabel lblImpureza = new JLabel("Impureza 2:");
	private final JTextField entClassificador = new JTextField();
	private final JTextField entUmidade2 = new JTextField();
	private final JTextField entImpureza = new JTextField();
	private final JLabel ad = new JLabel("Depósito");
	private final JLabel lblSilo_1 = new JLabel("Silo:");
	private final JLabel lblSilo_1_1 = new JLabel("Transgenia:");
	private final JTextField entTransgenia = new JTextField();
	private final JTextField entSilo = new JTextField();
	private final JButton btnAtualizar = new JButton("Atualizar");
	
	
	public TelaCadastroRomaneio(CadastroRomaneio romaneio, Window janela_pai) {
		entClassificador.setFont(new Font("Tahoma", Font.BOLD, 14));
		entClassificador.setColumns(10);

		
		 isto = this;
		 getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Cadastro Romaneio");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1087, 703);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
		 painelVizualizarRomaneio = new JPanel();
		painelPrincipal.add(painelVizualizarRomaneio, "cell 0 0 2 1,grow");
		painelVizualizarRomaneio.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel, "cell 0 1 2 1,grow");
		panel.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][][]"));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblNewLabel_1, "cell 1 0,alignx center");
		
		panel.add(ad, "cell 3 0,alignx center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblNewLabel, "cell 0 1,alignx trailing");
		
		panel.add(entClassificador, "cell 1 1,growx");
		lblSilo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblSilo_1, "cell 2 1,alignx trailing");
		entSilo.setFont(new Font("Tahoma", Font.BOLD, 14));
		entSilo.setColumns(10);
		
		panel.add(entSilo, "cell 3 1,growx");
		lblSilo_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblSilo_1_1, "cell 0 2,alignx trailing");
		entTransgenia.setFont(new Font("Tahoma", Font.BOLD, 14));
		entTransgenia.setColumns(10);
		
		panel.add(entTransgenia, "cell 1 2,growx");
		lblSilo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblSilo, "cell 0 3,alignx trailing");
		entUmidade2.setFont(new Font("Tahoma", Font.BOLD, 14));
		entUmidade2.setColumns(10);
		
		panel.add(entUmidade2, "cell 1 3,growx");
		lblImpureza.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblImpureza, "cell 0 4,alignx trailing");
		entImpureza.setFont(new Font("Tahoma", Font.BOLD, 14));
		entImpureza.setColumns(10);
		
		panel.add(entImpureza, "cell 1 4,growx");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CadastroRomaneio rom = getDadosAtualizar(romaneio);
				if(rom != null) {
				
					GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
					boolean atualizou = gerenciar.atualizarRomaneio(rom);
					if(atualizou) {
						//Criar tarefa
						GerenciarBancoTarefaGeral gerenciar_tarefa = new GerenciarBancoTarefaGeral();
						CadastroTarefaGeral tarefa = new CadastroTarefaGeral();
						
						tarefa.setNome_tarefa("edição de romaneio");
						tarefa.setDescricao_tarefa("Edição de dados no romaneio codigo: " + romaneio.getNumero_romaneio() );
						tarefa.setMensagem("ROMANEIO: " + romaneio.getNumero_romaneio() 
						+ " UMIDADE 2: " + rom.getUmidade2() + " IMPUREZA 2: " + rom.getImpureza2() + " classificador: " + rom.getClassificador() +
						" SILO: " + rom.getSilo() + " TRANSGENIA: " + rom.getTransgenia());
						tarefa.setCriador(login);
						tarefa.setExecutor(login);
						tarefa.setStatus_tarefa(1);
						tarefa.setPrioridade(1);
						tarefa.setTipo(1);
						
						GetData data = new GetData();
						tarefa.setHora(data.getHora());
						tarefa.setData(data.getData());
						tarefa.setHora_agendada(data.getHora());
						tarefa.setData_agendada(data.getData());
						
						boolean inseriu_tarefa = gerenciar_tarefa.inserirTarefaGeral(tarefa);
						if(inseriu_tarefa) {

						}else {
							JOptionPane.showMessageDialog(isto, "Tarefa Não Inserida, Consulte o administrador");

						}
						
						JOptionPane.showMessageDialog(isto, "Atualizado");
						((TelaRomaneios) janela_pai).pesquisarTodosOsRomaneios();
						((TelaRomaneios) janela_pai).pesquisar_tarefas();

						isto.dispose();
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o administrador");

					}
					
				}
				
			}
		});
		btnAtualizar.setBackground(new Color(0, 0, 255));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		panel.add(btnAtualizar, "cell 3 5,alignx right");
		
		
	
		
		
		rotinasEdicao(romaneio);
		carregarDocumento(romaneio.getCaminho_arquivo());
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void rotinasEdicao(CadastroRomaneio romaneio) {
		
		entClassificador.setText(romaneio.getClassificador());
		entSilo.setText(romaneio.getSilo());
		
		try {
		entImpureza.setText(Double.toString(romaneio.getImpureza2()));
		}catch(Exception e) {
			entImpureza.setText("0.0");
		}
		
		try {
		entUmidade2.setText(Double.toString(romaneio.getUmidade2()));
		}catch(Exception e) {
			entUmidade2.setText("0.0");
		}
		entTransgenia.setText(romaneio.getTransgenia());

	}
	
	public void carregarDocumento(String url) {
		// build a controller

		if (controller == null) {

			controller = new SwingController();

			PropertiesManager propriedades = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
			// Build a SwingViewFactory configured with the controller

			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, Boolean.FALSE);

			propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);

			factory = new SwingViewBuilder(controller, propriedades);
			// Use the factory to build a JPanel that is pre-configured
			// with a complete, active Viewer UI.

			/*
			 * controller.getDocumentViewController().setAnnotationCallback( new
			 * org.icepdf.ri.common.MyAnnotationCallback(controller.
			 * getDocumentViewController()));
			 */
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (painel_vizualizar == null) {

					painel_vizualizar = new JPanel();

					painel_vizualizar = factory.buildViewerPanel();
					controller.openDocument(servidor_unidade + url);
					// viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
					// viewerComponentPanel.setMaximumSize(new Dimension(400, 370));

					painel_vizualizar.setBounds(0, 0, 450, 650);
					painelVizualizarRomaneio.add(painel_vizualizar);
				} else {
					controller.openDocument(servidor_unidade + url);
					painel_vizualizar.repaint();
					painel_vizualizar.updateUI();
					painelVizualizarRomaneio.add(painel_vizualizar);

				}

			}
		});
	}

	public void fecharDocumento() {

		if (controller != null) {
			controller.closeDocument();
		}

	}

	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public CadastroRomaneio getDadosAtualizar(CadastroRomaneio antigo) {
		
		CadastroRomaneio romaneio = new CadastroRomaneio();
		romaneio.setId_romaneio(antigo.getId_romaneio());
	
		romaneio.setClassificador(entClassificador.getText());
		romaneio.setTransgenia(entTransgenia.getText());
		romaneio.setSilo(entSilo.getText());

		try {
			double impureza2 = Double.parseDouble(entImpureza.getText());
			romaneio.setImpureza2(impureza2);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Impureza Inválida");
			return null;
		}
		
		
		try {
			double umidade2 = Double.parseDouble(entUmidade2.getText());
			romaneio.setUmidade2(umidade2);
			return romaneio;

		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Umidade Inválida");
			return null;
		}
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
				  servidor_unidade = configs_globais.getServidorUnidade();
		
	}
	
}
