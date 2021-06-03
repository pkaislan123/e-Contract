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
import main.java.cadastros.CadastroClassificador;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.CadastroTransgenia;
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
import main.java.conexaoBanco.GerenciarBancoClassificadores;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSilo;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexaoBanco.GerenciarBancoTransgenia;
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
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoClassificador;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizadoSilo;
import main.java.classesExtras.ComboBoxPersonalizadoTransgenia;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizadoClassificador;
import main.java.classesExtras.ComboBoxRenderPersonalizadoSilo;
import main.java.classesExtras.ComboBoxRenderPersonalizadoTransgenia;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;



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
	private final JTextField entUmidade2 = new JTextField();
	private final JTextField entImpureza = new JTextField();
	private final JLabel ad = new JLabel("Depósito");
	private final JLabel lblSilo_1 = new JLabel("Local:");
	private final JLabel lblSilo_1_1 = new JLabel("Transgenia:");
	private final JButton btnAtualizar = new JButton("Atualizar");
	private final JLabel lblStatusMonsanto = new JLabel("Status Monsanto:");
	private final JComboBox cBStatusMonsanto = new JComboBox();
	private final JComboBox cBClassificador = new JComboBox();
	
	private ComboBoxPersonalizadoClassificador modelClassificadores = new ComboBoxPersonalizadoClassificador();
	private ComboBoxPersonalizadoSilo modelSilos = new ComboBoxPersonalizadoSilo();
	private ComboBoxPersonalizadoTransgenia modelTransgenias = new ComboBoxPersonalizadoTransgenia();

	private ComboBoxRenderPersonalizadoClassificador cbClassificadoresPersonalizados = new ComboBoxRenderPersonalizadoClassificador();
	private ComboBoxRenderPersonalizadoSilo cbSilosPersonalizados = new ComboBoxRenderPersonalizadoSilo();
	private ComboBoxRenderPersonalizadoTransgenia cbTransgeniasPersonalizados = new ComboBoxRenderPersonalizadoTransgenia();
	private final JComboBox cBTransgenia = new JComboBox();
	private final JComboBox cBLocal = new JComboBox();
	private final JLabel lblObservaes = new JLabel("Observações:");
	private final JTextArea entObservacao = new JTextArea();

	
	public TelaCadastroRomaneio(CadastroRomaneio romaneio, Window janela_pai) {

		
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
		
		panel.add(cBClassificador, "cell 1 1,growx");
		cBClassificador.setModel(modelClassificadores);
		cBClassificador.setRenderer(cbClassificadoresPersonalizados);
		lblStatusMonsanto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblStatusMonsanto, "cell 2 1,alignx trailing");
		cBStatusMonsanto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cBStatusMonsanto.addItem("FALTA ITS");
		cBStatusMonsanto.addItem("OK ITS");
		cBStatusMonsanto.addItem("PARTICIPANTE");
		cBStatusMonsanto.addItem("NÃO APLICÁVEL");

		

		panel.add(cBStatusMonsanto, "cell 3 1,growx");
		lblSilo_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblSilo_1_1, "cell 0 2,alignx trailing");
		
		panel.add(cBTransgenia, "cell 1 2,growx");
		cBTransgenia.setModel(modelTransgenias);
		cBTransgenia.setRenderer(cbTransgeniasPersonalizados);
		lblObservaes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblObservaes, "cell 2 2");
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacao.setWrapStyleWord(true);
		entObservacao.setLineWrap(true);
		entObservacao.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		panel.add(entObservacao, "cell 3 2 1 3,grow");
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
						
						CadastroTransgenia transgenia = (CadastroTransgenia) modelTransgenias.getSelectedItem();
						CadastroSilo silo = (CadastroSilo) modelSilos.getSelectedItem();
						CadastroClassificador clas = (CadastroClassificador) modelClassificadores.getSelectedItem();
						
						String status_monsanto = "";
						String observacao = rom.getObservacao();
						
						if(rom.getStatus_monsanto() == 0)
							status_monsanto = "Indefinido";
						else if(rom.getStatus_monsanto() == 1)
							status_monsanto = "OK ITS";
						else if(rom.getStatus_monsanto() == 2)
							status_monsanto = "PARTICIPANTE";
						else if(rom.getStatus_monsanto() == 3)
							status_monsanto = "NÃO APLICÁVEL";
						
						
						tarefa.setNome_tarefa("edição de romaneio");
						tarefa.setDescricao_tarefa("Edição de dados no romaneio codigo: " + romaneio.getNumero_romaneio() );
						tarefa.setMensagem("ROMANEIO: " + romaneio.getNumero_romaneio() 
						+ "| UMIDADE 2: " + rom.getUmidade2() + "| IMPUREZA 2: " + rom.getImpureza2() 
						+ "| Classificador: id: " + clas.getId() + " - "+ clas.getNome_colaborador()  +
						" SILO: id: " + silo.getId_silo() + " - " + silo.getNome_silo() + " - " + silo.getIdentificador() 
						+ " | TRANSGENIA: id" + transgenia.getId_transgenia() + " - " + transgenia.getNome() + " - " + transgenia.getDescricao()
						+ " | Status Monsanto: " + status_monsanto + "| Obs: " + observacao);
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
		lblSilo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel.add(lblSilo_1, "cell 0 5,alignx trailing");
		
		panel.add(cBLocal, "cell 1 5,growx");
		cBLocal.setModel(modelSilos);
		cBLocal.setRenderer(cbSilosPersonalizados);
		btnAtualizar.setBackground(new Color(0, 0, 255));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		panel.add(btnAtualizar, "cell 3 5,alignx right");
		
		
	
		
		
		carregarDocumento(romaneio.getCaminho_arquivo());
		
		pesquisar_classificadores();
		pesquisar_transgenias();
		pesquisar_silos();
		
		rotinasEdicao(romaneio);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void rotinasEdicao(CadastroRomaneio romaneio) {
		
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

		
		entObservacao.setText(romaneio.getObservacao());
		cBStatusMonsanto.setSelectedIndex(romaneio.getStatus_monsanto());
		

		GerenciarBancoClassificadores gerenciar_cl = new GerenciarBancoClassificadores();
		int id_classificador = romaneio.getId_classificador();
		if(id_classificador > 0) {
		CadastroClassificador clas = gerenciar_cl.getClassificador(id_classificador);
		if(clas != null) {
			modelClassificadores.setSelectedItem(clas);
		}
		
		}
		
		GerenciarBancoTransgenia gerenciar_tg = new GerenciarBancoTransgenia();
		int id_transgenia = romaneio.getId_transgenese();
		if(id_transgenia > 0) {
			CadastroTransgenia trans = gerenciar_tg.gettransgenia(id_transgenia);
			if(trans != null) {
				modelTransgenias.setSelectedItem(trans);
			}
		}
		
		GerenciarBancoSilo gerenciar_sl = new GerenciarBancoSilo();
		int id_silo = romaneio.getId_silo();
		if(id_silo > 0 ) {
			CadastroSilo sl = gerenciar_sl.getSilo(id_silo);
			if(sl != null) {
				modelSilos.setSelectedItem(sl);
			}
		}
		
		
		
		
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
	
		try {
		CadastroClassificador classificador = (CadastroClassificador) modelClassificadores.getSelectedItem();
		romaneio.setId_classificador(classificador.getId());
		
		}catch(Exception y) {
			JOptionPane.showMessageDialog(isto, "Selecione o Classificador");

			return null;
		}
		
		try {
		CadastroTransgenia transgenia = (CadastroTransgenia) modelTransgenias.getSelectedItem();
		romaneio.setId_transgenese(transgenia.getId_transgenia());
		}
		catch(Exception y) {
			JOptionPane.showMessageDialog(isto, "Selecione a Transgenia");

			return null;
		}
		
		try {
		CadastroSilo silo = (CadastroSilo) modelSilos.getSelectedItem();
		romaneio.setId_silo(silo.getId_silo());
		}
		catch(Exception y) {
			JOptionPane.showMessageDialog(isto, "Selecione o Silo");

			return null;
		}
		
		
		int status_monsanto = cBStatusMonsanto.getSelectedIndex();
		romaneio.setStatus_monsanto(status_monsanto);
		
		romaneio.setObservacao(entObservacao.getText());
		
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
	
	
	public void pesquisar_classificadores() {

		GerenciarBancoClassificadores gerenciar = new GerenciarBancoClassificadores();
		modelClassificadores.resetar();
		
		ArrayList<CadastroClassificador> lista_classificadores = gerenciar.busca_classificadores();
		for(CadastroClassificador cc : lista_classificadores) {
			modelClassificadores.addCC(cc);
		}
		

	}
	
	public void pesquisar_transgenias() {

		GerenciarBancoTransgenia gerenciar = new GerenciarBancoTransgenia();
		modelTransgenias.resetar();
		
		ArrayList<CadastroTransgenia> lista_transgenias = gerenciar.gettransgenia();
		for(CadastroTransgenia cc : lista_transgenias) {
			modelTransgenias.addCC(cc);
		}

	}
	
	public void pesquisar_silos() {

		GerenciarBancoSilo gerenciar = new GerenciarBancoSilo();
		modelSilos.resetar();

		ArrayList<CadastroSilo> lista_silos = gerenciar
				.getSilos();

		for (CadastroSilo cad : lista_silos) {
			modelSilos.addCC(cad);
		}

	}
}
