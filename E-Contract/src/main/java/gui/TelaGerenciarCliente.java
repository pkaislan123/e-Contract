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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

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
import javafx.stage.Stage;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JComboBox;

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
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
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
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
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
import main.java.relatoria.RelatorioContratoCarregamentoSimplificado;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaEmEsperaRelatoria;
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

public class TelaGerenciarCliente extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private TelaGerenciarCliente isto;
	private KGradientPanel menu_lateral;
	private JPanel panel_docs;
	private JTree arvore_documentos;
	private TelaEmEsperaRelatoria telaEmEsperaRelatoria;
	private FileChooser fileChooser;

	DefaultMutableTreeNode no_comprovantes;
	DefaultMutableTreeNode no_docs_pessoais;
	DefaultMutableTreeNode no_outros;
	private DefaultMutableTreeNode no_selecionado;
	private CadastroCliente cliente_local;
	private JComboBox cBTipoDocumento;
	private TelaTodasNotasFiscais telaTodasNotasFiscais;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JTextArea entDescricaoDocumento;
	private TelaRomaneios telaRomaneio;
	private JLabel lblTipoPessoa, lblTipoIdentificacao, lblIdentificacao, lblIe, lblIE, lblStatus, lblEndereco;
	private JLabel lblTotalContratosConcluidosComprador, lblTotalContratosComprador, lblTotalContratosAbertosComprador;
	private  Map<String, Integer> mapa_quantidades_globais;
	private JLabel lblTotalContratosConcluidosVendedor,lblTotalContratosCanceladosVendedor,lblTotalContratosCanceladosComprador, lblTotalContratosVendedor, lblTotalContratosAbertosVendedor,
			lblNivel;

	public TelaGerenciarCliente(CadastroCliente cliente_selecionado, Window janela_pai) {
		// setModal(true);

		getDadosGlobais();

		isto = this;
		cliente_local = cliente_selecionado;
		setResizable(true);
		setTitle("E-Contract - Gerenciar Cliente");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1302, 703);
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		panel_docs = new JPanel();
		panel_docs.setBackground(Color.WHITE);

		JPanel painelDocumentos = new JPanel();
		painelDocumentos.setBackground(new Color(0, 102, 153));
		painelDocumentos.setVisible(false);

		JPanel painelDeposito = new JPanel();
		painelDeposito.setBackground(new Color(0, 102, 153));
		painelDeposito.setVisible(false);

		JPanel painelContratos = new JPanel();
		painelContratos.setVisible(false);
		painelContratos.setBackground(Color.WHITE);
		painelContratos.setForeground(Color.WHITE);
		painelContratos.setBounds(198, 154, 1088, 510);
		painelPrincipal.add(painelContratos);
		painelContratos.setLayout(new MigLayout("", "[grow][grow]", "[504px]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(255, 255, 204));
		painelContratos.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[180px][7px][138px]", "[19px][24px][25px][24px][19px][33px][33px][][][][][][][][][][]"));

		JLabel lblNewLabel_1 = new JLabel("Contratos como vendedor:");
		panel_1.add(lblNewLabel_1, "cell 0 0 3 1,alignx left,aligny top");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel lblNewLabelT = new JLabel("Total Contratos:");
		panel_1.add(lblNewLabelT, "cell 0 1,alignx right,aligny center");
		lblNewLabelT.setFont(new Font("Arial", Font.PLAIN, 16));

		lblTotalContratosVendedor = new JLabel("New label");
		panel_1.add(lblTotalContratosVendedor, "cell 1 1 2 1,grow");
		lblTotalContratosVendedor.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblAbertos = new JLabel("Abertos:");
		panel_1.add(lblAbertos, "cell 0 2,alignx right,aligny top");
		lblAbertos.setFont(new Font("Arial", Font.PLAIN, 16));

		lblTotalContratosAbertosVendedor = new JLabel("New label");
		panel_1.add(lblTotalContratosAbertosVendedor, "cell 1 2 2 1,grow");
		lblTotalContratosAbertosVendedor.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblConcluidos = new JLabel("Concluidos:");
		panel_1.add(lblConcluidos, "cell 0 3,alignx right,aligny center");
		lblConcluidos.setFont(new Font("Arial", Font.PLAIN, 16));

		lblTotalContratosConcluidosVendedor = new JLabel("New label");
		panel_1.add(lblTotalContratosConcluidosVendedor, "cell 1 3 2 1,grow");
		lblTotalContratosConcluidosVendedor.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblCancelados = new JLabel("Cancelados:");
		lblCancelados.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1.add(lblCancelados, "cell 0 4,alignx right,aligny top");
		
				JButton btnVerRelatorioSimplificado = new JButton("Ver Relatorio Simplificado");
				btnVerRelatorioSimplificado.setFont(new Font("SansSerif", Font.BOLD, 16));
				btnVerRelatorioSimplificado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {


						telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

						new Thread() {
							@Override
							public void run() {
								telaEmEsperaRelatoria.setVisible(true);
							}
						}.start();
						int _tipo_contrato = 0;
						boolean _contrato = true;
						boolean _unir_contratos = false;
					
								boolean _contrato_como_comprador = false;
								boolean _pagamento = true;
								boolean _pagamento_como_depositante = false;
								
								boolean _pagamento_como_favorecido  = true;
								boolean _incluir_sem_pagamentos = true;
								
								boolean _incluir_transferencias_pagamentos = true;
								boolean _unir_pagamentos = true;
								boolean _incluir_comissao_pagamento = true;
								boolean _carregamento = true;
								boolean _carregamento_como_comprador = false;
								boolean _carregamento_como_vendedor = true;
								
								boolean _unir_carregamentos = true;
								boolean _controle_nf_venda_carregamentos = true;
								
								boolean _incluir_transferencias_carregamentos = true;
								boolean _incluir_sem_carregamentos = true;
								boolean _recebimento = true;
								
								boolean _recebimento_como_comprador = false;
								boolean _recebimento_como_vendedor = true;
								boolean _unir_recebimentos  =true;
								
								boolean _controle_nf_venda_recebimentos = true;
								boolean _incluir_sem_recebimentos = true;
								int _id_safra = 0;
								boolean _sub_contratos = false;
								boolean _incluir_comissao = false;
								boolean _incluir_ganhos_potenciais = false;
								boolean _somar_sub_contratos = false;
								ArrayList<CadastroCliente> _clientes_globais = new ArrayList<>();
								
								CadastroCliente contra_parte = new CadastroCliente();
								contra_parte.setId(0);
								CadastroCliente cliente_alvo2_relatorio =  new CadastroCliente();
								cliente_alvo2_relatorio.setId(0);
								CadastroGrupo _grupo_alvo = null;
								int _participacao = -1;
								
								_clientes_globais.add(cliente_local);
						
								
						RelatorioContratos relatar = new RelatorioContratos( _tipo_contrato,  _contrato,  _unir_contratos,
								 _contrato_como_comprador,  _pagamento,  _pagamento_como_depositante,
								 _pagamento_como_favorecido,  _incluir_sem_pagamentos,
								 _incluir_transferencias_pagamentos,  _unir_pagamentos,  _incluir_comissao_pagamento,
								 _carregamento,  _carregamento_como_comprador,  _carregamento_como_vendedor,
								 _unir_carregamentos,  _controle_nf_venda_carregamentos,
								 _incluir_transferencias_carregamentos,  _incluir_sem_carregamentos,  _recebimento,
								 _recebimento_como_comprador,  _recebimento_como_vendedor,  _unir_recebimentos,
								 _controle_nf_venda_recebimentos,  _incluir_sem_recebimentos,  _id_safra,
								 _sub_contratos,  _incluir_comissao,  _incluir_ganhos_potenciais,
								 _somar_sub_contratos, _clientes_globais, contra_parte, cliente_alvo2_relatorio,
								 _grupo_alvo,  _participacao);

						new Thread() {
							@Override
							public void run() {

								relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

								relatar(isto, relatar);
							}
						}.start();

					}
				});
						
						 lblTotalContratosCanceladosVendedor = new JLabel("New label");
						lblTotalContratosCanceladosVendedor.setFont(new Font("Arial", Font.PLAIN, 16));
						panel_1.add(lblTotalContratosCanceladosVendedor, "cell 1 4 2 1");
				
						JButton btnVerRelatorioComoVendedor = new JButton("Ver Relatorio Completo");
						btnVerRelatorioComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 16));
						btnVerRelatorioComoVendedor.setForeground(Color.WHITE);
						btnVerRelatorioComoVendedor.setBackground(new Color(0, 0, 102));
						panel_1.add(btnVerRelatorioComoVendedor, "cell 0 15,alignx left,aligny top");
						btnVerRelatorioComoVendedor.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								
								
										
										telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

										new Thread() {
											@Override
											public void run() {
												telaEmEsperaRelatoria.setVisible(true);
											}
										}.start();
										int _tipo_contrato = 0;
										boolean _contrato = true;
										boolean _unir_contratos = false;
									
												boolean _contrato_como_comprador = false;
												boolean _pagamento = true;
												boolean _pagamento_como_depositante = false;
												
												boolean _pagamento_como_favorecido  =true;
												boolean _incluir_sem_pagamentos = true;
												
												boolean _incluir_transferencias_pagamentos = true;
												boolean _unir_pagamentos = false;
												boolean _incluir_comissao_pagamento = true;
												boolean _carregamento = true;
												boolean _carregamento_como_comprador = false;
												boolean _carregamento_como_vendedor = true;
												
												boolean _unir_carregamentos = false;
												boolean _controle_nf_venda_carregamentos = true;
												
												boolean _incluir_transferencias_carregamentos = true;
												boolean _incluir_sem_carregamentos = true;
												boolean _recebimento = true;
												
												boolean _recebimento_como_comprador = false;
												boolean _recebimento_como_vendedor = true;
												boolean _unir_recebimentos  =false;
												
												boolean _controle_nf_venda_recebimentos = true;
												boolean _incluir_sem_recebimentos = true;
												int _id_safra = 0;
												boolean _sub_contratos = false;
												boolean _incluir_comissao = false;
												boolean _incluir_ganhos_potenciais = false;
												boolean _somar_sub_contratos = false;
												ArrayList<CadastroCliente> _clientes_globais = new ArrayList<>();
												
												CadastroCliente contra_parte = new CadastroCliente();
												contra_parte.setId(0);
												CadastroCliente cliente_alvo2_relatorio =  new CadastroCliente();
												cliente_alvo2_relatorio.setId(0);
												CadastroGrupo _grupo_alvo = null;
												int _participacao = -1;
												
												_clientes_globais.add(cliente_local);
										
												
										RelatorioContratos relatar = new RelatorioContratos( _tipo_contrato,  _contrato,  _unir_contratos,
												 _contrato_como_comprador,  _pagamento,  _pagamento_como_depositante,
												 _pagamento_como_favorecido,  _incluir_sem_pagamentos,
												 _incluir_transferencias_pagamentos,  _unir_pagamentos,  _incluir_comissao_pagamento,
												 _carregamento,  _carregamento_como_comprador,  _carregamento_como_vendedor,
												 _unir_carregamentos,  _controle_nf_venda_carregamentos,
												 _incluir_transferencias_carregamentos,  _incluir_sem_carregamentos,  _recebimento,
												 _recebimento_como_comprador,  _recebimento_como_vendedor,  _unir_recebimentos,
												 _controle_nf_venda_recebimentos,  _incluir_sem_recebimentos,  _id_safra,
												 _sub_contratos,  _incluir_comissao,  _incluir_ganhos_potenciais,
												 _somar_sub_contratos, _clientes_globais, contra_parte, cliente_alvo2_relatorio,
												 _grupo_alvo,  _participacao);

										new Thread() {
											@Override
											public void run() {

												relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

												relatar(isto, relatar);
											}
										}.start();

							}
						});
				btnVerRelatorioSimplificado.setForeground(Color.WHITE);
				btnVerRelatorioSimplificado.setBackground(new Color(0, 0, 204));
				panel_1.add(btnVerRelatorioSimplificado, "cell 0 16,alignx left,aligny top");

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBackground(new Color(204, 255, 153));
		painelContratos.add(panel_1_1, "cell 1 0,grow");
		panel_1_1.setLayout(new MigLayout("", "[180px][7px][19px][51px][225px]", "[19px][24px][25px][24px][33px][33px][][][][][][][][][][][]"));

		JLabel lblNewLabel_1_1 = new JLabel("Contratos como comprador:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1_1.add(lblNewLabel_1_1, "cell 0 0 3 1,growx,aligny top");

		JLabel lblNewLabelT_1 = new JLabel("Total Contratos:");
		lblNewLabelT_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblNewLabelT_1, "cell 0 1,alignx right,aligny center");

		lblTotalContratosComprador = new JLabel("New label");
		lblTotalContratosComprador.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblTotalContratosComprador, "cell 1 1 4 1,alignx left,growy");

		JLabel lblAbertos_1 = new JLabel("Abertos:");
		lblAbertos_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblAbertos_1, "cell 0 2,alignx right,aligny top");

		lblTotalContratosAbertosComprador = new JLabel("New label");
		lblTotalContratosAbertosComprador.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblTotalContratosAbertosComprador, "cell 1 2 4 1,alignx left,growy");

		JLabel lblConcluidos_1 = new JLabel("Concluidos:");
		lblConcluidos_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblConcluidos_1, "cell 0 3,alignx right,aligny center");

		lblTotalContratosConcluidosComprador = new JLabel("New label");
		lblTotalContratosConcluidosComprador.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_1_1.add(lblTotalContratosConcluidosComprador, "cell 1 3 4 1,alignx left,growy");
		
				JButton btnVerRelatorioSimplificado_1 = new JButton("Ver Relatorio Simplificado");
				btnVerRelatorioSimplificado_1.setFont(new Font("SansSerif", Font.BOLD, 16));
				btnVerRelatorioSimplificado_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						

						
						telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

						new Thread() {
							@Override
							public void run() {
								telaEmEsperaRelatoria.setVisible(true);
							}
						}.start();
						int _tipo_contrato = 1;
						boolean _contrato = true;
						boolean _unir_contratos = false;
					
								boolean _contrato_como_comprador = true;
								boolean _pagamento = true;
								boolean _pagamento_como_depositante = true;
								
								boolean _pagamento_como_favorecido  = false;
								boolean _incluir_sem_pagamentos = true;
								
								boolean _incluir_transferencias_pagamentos = true;
								boolean _unir_pagamentos = true;
								boolean _incluir_comissao_pagamento = true;
								boolean _carregamento = true;
								boolean _carregamento_como_comprador = true;
								boolean _carregamento_como_vendedor = false;
								
								boolean _unir_carregamentos = true;
								boolean _controle_nf_venda_carregamentos = true;
								
								boolean _incluir_transferencias_carregamentos = true;
								boolean _incluir_sem_carregamentos = true;
								boolean _recebimento = true;
								
								boolean _recebimento_como_comprador = true;
								boolean _recebimento_como_vendedor = false;
								boolean _unir_recebimentos  = true;
								
								boolean _controle_nf_venda_recebimentos = true;
								boolean _incluir_sem_recebimentos = true;
								int _id_safra = 0;
								boolean _sub_contratos = true;
								boolean _incluir_comissao = true;
								boolean _incluir_ganhos_potenciais = true;
								boolean _somar_sub_contratos = false;
								ArrayList<CadastroCliente> _clientes_globais = new ArrayList<>();
								
								CadastroCliente contra_parte = new CadastroCliente();
								contra_parte.setId(0);
								CadastroCliente cliente_alvo2_relatorio =  new CadastroCliente();
								cliente_alvo2_relatorio.setId(0);
								CadastroGrupo _grupo_alvo = null;
								int _participacao = -1;
								
								_clientes_globais.add(cliente_local);
						
								
						RelatorioContratos relatar = new RelatorioContratos( _tipo_contrato,  _contrato,  _unir_contratos,
								 _contrato_como_comprador,  _pagamento,  _pagamento_como_depositante,
								 _pagamento_como_favorecido,  _incluir_sem_pagamentos,
								 _incluir_transferencias_pagamentos,  _unir_pagamentos,  _incluir_comissao_pagamento,
								 _carregamento,  _carregamento_como_comprador,  _carregamento_como_vendedor,
								 _unir_carregamentos,  _controle_nf_venda_carregamentos,
								 _incluir_transferencias_carregamentos,  _incluir_sem_carregamentos,  _recebimento,
								 _recebimento_como_comprador,  _recebimento_como_vendedor,  _unir_recebimentos,
								 _controle_nf_venda_recebimentos,  _incluir_sem_recebimentos,  _id_safra,
								 _sub_contratos,  _incluir_comissao,  _incluir_ganhos_potenciais,
								 _somar_sub_contratos, _clientes_globais, contra_parte, cliente_alvo2_relatorio,
								 _grupo_alvo,  _participacao);

						new Thread() {
							@Override
							public void run() {

								relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

								relatar(isto, relatar);
							}
						}.start();
						
					}
				});
				
						JButton btnVerRelatorioSem = new JButton("Ver Relatorio Completo");
						btnVerRelatorioSem.setFont(new Font("SansSerif", Font.BOLD, 16));
						btnVerRelatorioSem.setBackground(new Color(0, 0, 102));
						btnVerRelatorioSem.setForeground(Color.WHITE);
						btnVerRelatorioSem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

								new Thread() {
									@Override
									public void run() {
										telaEmEsperaRelatoria.setVisible(true);
									}
								}.start();
								int _tipo_contrato = 1;
								boolean _contrato = true;
								boolean _unir_contratos = false;
							
										boolean _contrato_como_comprador = true;
										boolean _pagamento = true;
										boolean _pagamento_como_depositante = true;
										
										boolean _pagamento_como_favorecido  = false;
										boolean _incluir_sem_pagamentos = true;
										
										boolean _incluir_transferencias_pagamentos = true;
										boolean _unir_pagamentos = false;
										boolean _incluir_comissao_pagamento = true;
										boolean _carregamento = true;
										boolean _carregamento_como_comprador = true;
										boolean _carregamento_como_vendedor = false;
										
										boolean _unir_carregamentos = false;
										boolean _controle_nf_venda_carregamentos = true;
										
										boolean _incluir_transferencias_carregamentos = true;
										boolean _incluir_sem_carregamentos = true;
										boolean _recebimento = true;
										
										boolean _recebimento_como_comprador = true;
										boolean _recebimento_como_vendedor = false;
										boolean _unir_recebimentos  = false;
										
										boolean _controle_nf_venda_recebimentos = true;
										boolean _incluir_sem_recebimentos = true;
										int _id_safra = 0;
										boolean _sub_contratos = true;
										boolean _incluir_comissao = true;
										boolean _incluir_ganhos_potenciais = true;
										boolean _somar_sub_contratos = false;
										ArrayList<CadastroCliente> _clientes_globais = new ArrayList<>();
										
										CadastroCliente contra_parte = new CadastroCliente();
										contra_parte.setId(0);
										CadastroCliente cliente_alvo2_relatorio =  new CadastroCliente();
										cliente_alvo2_relatorio.setId(0);
										CadastroGrupo _grupo_alvo = null;
										int _participacao = -1;
										
										_clientes_globais.add(cliente_local);
								
										
								RelatorioContratos relatar = new RelatorioContratos( _tipo_contrato,  _contrato,  _unir_contratos,
										 _contrato_como_comprador,  _pagamento,  _pagamento_como_depositante,
										 _pagamento_como_favorecido,  _incluir_sem_pagamentos,
										 _incluir_transferencias_pagamentos,  _unir_pagamentos,  _incluir_comissao_pagamento,
										 _carregamento,  _carregamento_como_comprador,  _carregamento_como_vendedor,
										 _unir_carregamentos,  _controle_nf_venda_carregamentos,
										 _incluir_transferencias_carregamentos,  _incluir_sem_carregamentos,  _recebimento,
										 _recebimento_como_comprador,  _recebimento_como_vendedor,  _unir_recebimentos,
										 _controle_nf_venda_recebimentos,  _incluir_sem_recebimentos,  _id_safra,
										 _sub_contratos,  _incluir_comissao,  _incluir_ganhos_potenciais,
										 _somar_sub_contratos, _clientes_globais, contra_parte, cliente_alvo2_relatorio,
										 _grupo_alvo,  _participacao);

								new Thread() {
									@Override
									public void run() {

										relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

										relatar(isto, relatar);
									}
								}.start();
								
}
						});
						
						JLabel lblCancelados_1 = new JLabel("Cancelados:");
						lblCancelados_1.setFont(new Font("Arial", Font.PLAIN, 16));
						panel_1_1.add(lblCancelados_1, "cell 0 4,alignx right");
						
						 lblTotalContratosCanceladosComprador = new JLabel("New label");
						lblTotalContratosCanceladosComprador.setFont(new Font("Arial", Font.PLAIN, 16));
						panel_1_1.add(lblTotalContratosCanceladosComprador, "cell 1 4 3 1");
						panel_1_1.add(btnVerRelatorioSem, "cell 4 15,alignx left,aligny top");
				btnVerRelatorioSimplificado_1.setForeground(Color.WHITE);
				btnVerRelatorioSimplificado_1.setBackground(new Color(0, 0, 204));
				panel_1_1.add(btnVerRelatorioSimplificado_1, "cell 4 16,alignx left,aligny top");

		JPanel painelDadosIniciais = new JPanel();
		painelDadosIniciais.setBackground(new Color(0, 128, 128));
		painelDadosIniciais.setBounds(198, 153, 1088, 511);
		painelPrincipal.add(painelDadosIniciais);
		painelDadosIniciais.setLayout(null);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(255, 102, 0));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, cliente_selecionado, isto);
				telaEdicao.setTelaPai(isto);
				telaEdicao.setVisible(true);
			}
		});
		btnEditar.setBounds(971, 461, 73, 33);
		painelDadosIniciais.add(btnEditar);

		JPanel painelInfo = new JPanel();
		painelInfo.setBackground(new Color(0, 128, 128));
		painelInfo.setBounds(22, 11, 696, 275);
		painelDadosIniciais.add(painelInfo);
		painelInfo.setLayout(new MigLayout("", "[][]", "[][][][][][][]"));

		JLabel lblNewLabel = new JLabel("TIpo Pessoa:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblNewLabel, "cell 0 0");

		lblTipoPessoa = new JLabel("Juridica");
		lblTipoPessoa.setForeground(Color.WHITE);
		lblTipoPessoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblTipoPessoa, "cell 1 0");

		lblTipoIdentificacao = new JLabel("CPF/CNPJ:");
		lblTipoIdentificacao.setForeground(Color.WHITE);
		lblTipoIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblTipoIdentificacao, "cell 0 1,alignx right");

		lblIdentificacao = new JLabel("");
		lblIdentificacao.setForeground(Color.WHITE);
		lblIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblIdentificacao, "cell 1 1");

		lblIe = new JLabel("IE:");
		lblIe.setForeground(Color.WHITE);
		lblIe.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblIe, "cell 0 3,alignx right");

		lblIE = new JLabel("120.927.986-00");
		lblIE.setForeground(Color.WHITE);
		lblIE.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblIE, "cell 1 3,alignx left");

		JLabel lblnasdad = new JLabel("Status:");
		lblnasdad.setForeground(Color.WHITE);
		lblnasdad.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblnasdad, "cell 0 4,alignx right");

		lblStatus = new JLabel("");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblStatus, "cell 1 4,alignx left");

		JLabel lblEndereo = new JLabel("Endereço:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblEndereo, "cell 0 6,alignx right");

		lblEndereco = new JLabel("Rodovia MG 188, km 242, Zona Rural, Guarda-Mor/MG 38570-000");
		lblEndereco.setForeground(Color.WHITE);
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblEndereco, "cell 1 6");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(45, 67, 115, 20);

		JButton btnNewButton = new JButton("Baixar Notas");
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaEscolherDataBaixarNotas tela = new TelaEscolherDataBaixarNotas(cliente_local, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBounds(789, 461, 126, 33);
		painelDadosIniciais.add(btnNewButton);

		JButton btnAcessarNfs = new JButton("Ver NF's");
		btnAcessarNfs.setBackground(new Color(51, 0, 102));
		btnAcessarNfs.setForeground(Color.WHITE);
		btnAcessarNfs.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAcessarNfs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0, 1, isto);
				telaTodasNotasFiscais.setTelaPai(isto);
				telaTodasNotasFiscais.limpar();
				telaTodasNotasFiscais.desabilitarBtnSelecionar();
				telaTodasNotasFiscais.setClienteSelecionado(cliente_selecionado);
				telaTodasNotasFiscais.pesquisar_notas();
				telaTodasNotasFiscais.setVisible(true);

			}
		});
		btnAcessarNfs.setBounds(645, 461, 93, 33);
		painelDadosIniciais.add(btnAcessarNfs);
		painelDeposito.setEnabled(false);
		painelDeposito.setBounds(198, 153, 1088, 511);
		painelPrincipal.add(painelDeposito);
		painelDeposito.setLayout(null);

		JButton btnNewButton_2 = new JButton("Romaneios\r\n");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(new Color(0, 0, 51));
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaRomaneios telaRomaneio;
				telaRomaneio = new TelaRomaneios(1, isto);
				telaRomaneio.setTelaPai(isto);
				telaRomaneio.setClienteSelecionado(cliente_selecionado);
				telaRomaneio.pesquisarTodosOsRomaneios();
				telaRomaneio.setVisible(true);

			}
		});
		btnNewButton_2.setBounds(955, 455, 115, 33);
		painelDeposito.add(btnNewButton_2);
		painelDocumentos.setEnabled(false);
		painelDocumentos.setBounds(198, 153, 1088, 511);
		painelPrincipal.add(painelDocumentos);
		painelDocumentos.setLayout(new MigLayout("", "[grow][]", "[grow]"));

		painelDocumentos.add(panel_docs, "cell 0 0,grow");

		JPanel painelInserirDocumento = new JPanel();
		painelInserirDocumento.setBackground(new Color(0, 102, 153));
		painelDocumentos.add(painelInserirDocumento, "cell 1 0,growx,aligny center");
		painelInserirDocumento
				.setLayout(new MigLayout("", "[50px][231px][18px][89px]", "[27px][22px][85px][39px][23px]"));

		JLabel lblNewLabel_15 = new JLabel("Nome:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInserirDocumento.add(lblNewLabel_15, "cell 0 0,alignx right,aligny top");

		JLabel lblNewLabel_16 = new JLabel("Descrição:");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInserirDocumento.add(lblNewLabel_16, "cell 0 2,alignx right,aligny top");

		entDescricaoDocumento = new JTextArea();
		entDescricaoDocumento.setFont(new Font("Arial", Font.BOLD, 16));
		painelInserirDocumento.add(entDescricaoDocumento, "cell 1 2 3 1,grow");

		JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInserirDocumento.add(lblNewLabel_17, "cell 0 3,alignx right,aligny center");

		entCaminhoDocumento = new JTextField();
		entCaminhoDocumento.setFont(new Font("Tahoma", Font.BOLD, 16));
		entCaminhoDocumento.setColumns(10);
		painelInserirDocumento.add(entCaminhoDocumento, "cell 1 3,grow");

		entNomeDocumento = new JTextField();
		entNomeDocumento.setFont(new Font("Arial", Font.BOLD, 16));
		entNomeDocumento.setColumns(10);
		painelInserirDocumento.add(entNomeDocumento, "cell 1 0 3 1,grow");

		JButton btnSelecionarDocumento = new JButton("Selecionar");
		btnSelecionarDocumento.setBackground(new Color(0, 0, 102));
		btnSelecionarDocumento.setForeground(Color.WHITE);
		btnSelecionarDocumento.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSelecionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumento();
			}
		});
		painelInserirDocumento.add(btnSelecionarDocumento, "cell 3 3,growx,aligny center");

		JButton btnAdicionarDocumento = new JButton("Adicionar");
		btnAdicionarDocumento.setBackground(new Color(0, 51, 0));
		btnAdicionarDocumento.setForeground(Color.WHITE);
		btnAdicionarDocumento.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdicionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarNovoDocumento();
			}
		});
		painelInserirDocumento.add(btnAdicionarDocumento, "cell 3 4,growx,aligny top");

		JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		lblNewLabel_16_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelInserirDocumento.add(lblNewLabel_16_1, "cell 0 1,alignx right,aligny center");

		cBTipoDocumento = new JComboBox();
		cBTipoDocumento.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInserirDocumento.add(cBTipoDocumento, "cell 1 1 3 1,grow");
		cBTipoDocumento.addItem("Documento Pessoal");
		cBTipoDocumento.addItem("Comprovantes");
		cBTipoDocumento.addItem("Outros");
		panel_docs.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_docs.add(panel_2, "cell 0 0,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		JLabel lblNewLabel_18 = new JLabel("Documentos deste Cliente:");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_18, "cell 0 0");

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
		arvore_documentos.setFont(new Font("SansSerif", Font.PLAIN, 16));

		arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
				// lblNoSelecionado.setText(no_selecionado.getUserObject().toString());
			}
		});

		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemVizualizar = new JMenuItem();
		JMenuItem jMenuItemExcluir = new JMenuItem();

		jMenuItemVizualizar.setText("Vizualizar");
		jMenuItemExcluir.setText("Excluir");

		jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				String nome_arquivo = no_selecionado.getUserObject().toString();

				String quebra[] = nome_arquivo.split("@");

				String nome_official = "";
				for (int i = 1; i < quebra.length; i++) {
					nome_official += quebra[i];
				}

				String nome_pasta = "";
				if (cliente_local.getTipo_pessoa() == 0)
					nome_pasta = cliente_local.getNome_empresarial();
				else
					nome_pasta = cliente_local.getNome_fantaia();

				nome_pasta = trimar(nome_pasta);

				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta
						+ "\\DADOS PESSOAS\\" + nome_official;

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

		jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este Documento", "Exclusão",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					String nome_arquivo = no_selecionado.getUserObject().toString();

					String quebra[] = nome_arquivo.split("@");

					String nome_official = "";
					for (int i = 1; i < quebra.length; i++) {
						nome_official += quebra[i];
					}

					String nome_pasta = "";
					if (cliente_local.getTipo_pessoa() == 0)
						nome_pasta = cliente_local.getNome_empresarial();
					else
						nome_pasta = cliente_local.getNome_fantaia();

					nome_pasta = trimar(nome_pasta);

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_completo = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\"
							+ nome_pasta + "\\DADOS PESSOAS\\" + nome_official;

					boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
					if (excluido) {

						GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
						boolean excluir_documento = gerenciar_docs.removerDocumento(Integer.parseInt(quebra[0]));

						if (excluir_documento) {
							JOptionPane.showMessageDialog(null, "Documento Excluido!");

						} else {
							JOptionPane.showMessageDialog(null,
									"Arquivo fisico apagado, mas as informações\ndeste documento ainda estão no banco de dados\nConsulte o administrador");

						}

						atualizarArvoreDocumentos();

					} else {
						JOptionPane.showMessageDialog(null, "Erro ao excluir o documento\nConsulte o administrador!");
					}

				} else {

				}

			}

		});

		jPopupMenu.add(jMenuItemVizualizar);
		jPopupMenu.add(jMenuItemExcluir);

		arvore_documentos.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenu.show(arvore_documentos, e.getX(), e.getY());
				}
			}
		});

		arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
			ImageIcon icone_docs_pessoais = new ImageIcon(
					TelaGerenciarCliente.class.getResource("/imagens/icone_docs_pessoais.png"));
			ImageIcon icone_comprovantes = new ImageIcon(
					TelaGerenciarCliente.class.getResource("/imagens/icone_comprovantes.png"));
			ImageIcon icone_outros = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_outros.png"));

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
					boolean isLeaf, int row, boolean focused) {

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				String s = node.getUserObject().toString();
				if ("Documentos Pessoais".equals(s)) {
					setOpenIcon(icone_docs_pessoais);
					setClosedIcon(icone_docs_pessoais);

				} else if ("Comprovantes".equals(s)) {
					setOpenIcon(icone_comprovantes);
					setClosedIcon(icone_comprovantes);

				} else if ("Outros".equals(s)) {
					setOpenIcon(icone_outros);
					setClosedIcon(icone_outros);

				}
				super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, hasFocus);

				return this;
			}

		});

		arvore_documentos.setShowsRootHandles(true);
		arvore_documentos.setRootVisible(false);
		panel_2.add(arvore_documentos, "cell 0 1,grow");

		expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 255, 204);
		menu_lateral.kEndColor = Color.BLUE;
		menu_lateral.setBounds(0, 0, 200, 664);
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
		panelTopo.kStartColor = new Color(0, 102, 153);
		panelTopo.kEndColor = new Color(0, 51, 51);
		panelTopo.setBounds(172, 6, 1114, 149);
		painelPrincipal.add(panelTopo);
		panelTopo.setLayout(null);

		String nome = "";
		if (cliente_selecionado.getTipo_pessoa() == 0)
			nome = cliente_selecionado.getNome_empresarial();
		else
			nome = cliente_selecionado.getNome_fantaia();

		JLabel lblNomeUsuario = new JLabel(nome.toUpperCase());
		lblNomeUsuario.setForeground(Color.WHITE);
		lblNomeUsuario.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblNomeUsuario.setBounds(44, 32, 808, 81);
		panelTopo.add(lblNomeUsuario);

		lblNivel = new JLabel("");
		lblNivel.setBounds(909, 14, 170, 30);
		panelTopo.add(lblNivel);

		JButton btnNewButton_1 = new JButton("Entenda a pontuação");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaMostrarPontuacao tela = new TelaMostrarPontuacao(cliente_local, isto);
				tela.setTelaPai(isto);
				tela.setInfoPontuacao(cliente_local.getId());

				tela.pesquisar_pontuacao();
				tela.setVisible(true);

			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 0, 102));
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton_1.setBounds(888, 56, 191, 31);
		panelTopo.add(btnNewButton_1);

		JLabel btnDeposito = new JLabel("Deposito");
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

				painelDeposito.setEnabled(false);
				painelDeposito.setVisible(false);

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

				painelDeposito.setEnabled(false);
				painelDeposito.setVisible(false);

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

				painelDeposito.setEnabled(false);
				painelDeposito.setVisible(false);

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

				painelDeposito.setEnabled(true);
				painelDeposito.setVisible(true);

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
		setInfo();
		atualizarArvoreDocumentos();
		
		boolean pesquisar= true;
		if(pesquisar) {
			mapa_quantidades_globais = new GerenciarBancoContratos().getNumContratos(cliente_local.getId());

		 setInfoContratosComoVendedor();
		setInfoContratosComoComprador();
		}
		setInfoPontuacao();

		this.setLocationRelativeTo(janela_pai);

	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(cliente_local.getId());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

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
				JPopupMenu jPopupMenu = new JPopupMenu();
				JMenuItem jMenuItemVizualizar = new JMenuItem();
				JMenuItem jMenuItemExcluir = new JMenuItem();

				jMenuItemVizualizar.setText("Vizualizar");
				jMenuItemExcluir.setText("Excluir");

				jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						String nome_arquivo = no_selecionado.getUserObject().toString();

						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = "";
						if (cliente_local.getTipo_pessoa() == 0)
							nome_pasta = cliente_local.getNome_empresarial();
						else
							nome_pasta = cliente_local.getNome_fantaia();

						nome_pasta = trimar(nome_pasta);

						String unidade_base_dados = configs_globais.getServidorUnidade();
						String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\"
								+ nome_pasta + "\\DADOS PESSOAS\\" + nome_official;

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

				jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este Documento", "Exclusão",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							String nome_arquivo = no_selecionado.getUserObject().toString();

							String quebra[] = nome_arquivo.split("@");

							String nome_official = "";
							for (int i = 1; i < quebra.length; i++) {
								nome_official += quebra[i];
							}

							String nome_pasta = "";
							if (cliente_local.getTipo_pessoa() == 0)
								nome_pasta = cliente_local.getNome_empresarial();
							else
								nome_pasta = cliente_local.getNome_fantaia();

							nome_pasta = trimar(nome_pasta);

							String unidade_base_dados = configs_globais.getServidorUnidade();
							String caminho_completo = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\"
									+ nome_pasta + "\\DADOS PESSOAS\\" + nome_official;

							boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
							if (excluido) {

								GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
								boolean excluir_documento = gerenciar_docs
										.removerDocumento(Integer.parseInt(quebra[0]));

								if (excluir_documento) {
									JOptionPane.showMessageDialog(null, "Documento Excluido!");

								} else {
									JOptionPane.showMessageDialog(null,
											"Arquivo fisico apagado, mas as informações\ndeste documento ainda estão no banco de dados\nConsulte o administrador");

								}

								atualizarArvoreDocumentos();

							} else {
								JOptionPane.showMessageDialog(null,
										"Erro ao excluir o documento\nConsulte o administrador!");
							}

						} else {

						}

					}

				});

				jPopupMenu.add(jMenuItemVizualizar);
				jPopupMenu.add(jMenuItemExcluir);

				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							no_docs_pessoais.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// outros
							no_outros.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

			}
		});

	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
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

			// pegar nome da pasta
			String nome_pasta = "";
			if (cliente_local.getTipo_pessoa() == 0)
				nome_pasta = cliente_local.getNome_empresarial();
			else
				nome_pasta = cliente_local.getNome_fantaia();

			nome_pasta = trimar(nome_pasta);
			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\clientes\\" + nome_pasta
					+ "\\DADOS PESSOAS";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
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
				} else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
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
									// model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root,
									// root.getChildCount());

									no_docs_pessoais.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 2) {
									// pagamentos
									no_comprovantes.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 3) {
									// carregamentos
									no_outros.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 4) {
									// outros
									no_outros.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								}

							}
						}
						model.reload(); // this notifies the listeners and changes the GUI

						expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

					}

				});

			}
		}.start();

	}

	public void atualizarInfo() {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		cliente_local = gerenciar.getCliente(cliente_local.getId());

		// lblIe, lblIE, lblStatus, lblEndereco;
		if (cliente_local.getTipo_pessoa() == 0) {
			lblTipoPessoa.setText("Fisica");
			lblTipoIdentificacao.setText("CPF:");
			lblIdentificacao.setText(cliente_local.getCpf());
		} else {
			lblTipoPessoa.setText("Juridica");
			lblTipoIdentificacao.setText("CNPJ:");
			lblIdentificacao.setText(cliente_local.getCnpj());
		}

		lblIE.setText(cliente_local.getIe());
		String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", "
				+ cliente_local.getBairro() + ", " + cliente_local.getCidade() + "/" + cliente_local.getUf() + " Cep: "
				+ cliente_local.getCep();
		lblEndereco.setText(endereco_completo);

	}

	public void setInfo() {

		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		cliente_local = gerenciar.getCliente(cliente_local.getId());

		// lblIe, lblIE, lblStatus, lblEndereco;
		if (cliente_local.getTipo_pessoa() == 0) {
			lblTipoPessoa.setText("Fisica");
			lblTipoIdentificacao.setText("CPF:");
			lblIdentificacao.setText(cliente_local.getCpf());
		} else {
			lblTipoPessoa.setText("Juridica");
			lblTipoIdentificacao.setText("CNPJ:");
			lblIdentificacao.setText(cliente_local.getCnpj());
		}

		lblIE.setText(cliente_local.getIe());
		String endereco_completo = cliente_local.getRua() + ", Nº: " + cliente_local.getNumero() + ", "
				+ cliente_local.getBairro() + ", " + cliente_local.getCidade() + "/" + cliente_local.getUf() + " Cep: "
				+ cliente_local.getCep();
		lblEndereco.setText(endereco_completo);

	}

	public void setInfoContratosComoVendedor() {

		int num_contratos = mapa_quantidades_globais.get("num_contratos_vendedor");
		int num_contratos_concuido = mapa_quantidades_globais.get("num_contratos_vendedor_concluido");
		int num_contratos_cancelados = mapa_quantidades_globais.get("num_contratos_vendedor_cancelado");
		int num_contratos_abertos = num_contratos - num_contratos_concuido - num_contratos_cancelados;

		lblTotalContratosVendedor.setText(num_contratos + "");
		lblTotalContratosConcluidosVendedor.setText(num_contratos_concuido + "");
		lblTotalContratosAbertosVendedor.setText(num_contratos_abertos + "");
		lblTotalContratosCanceladosVendedor.setText(num_contratos_cancelados + "");
	}

	public void setInfoContratosComoComprador() {

		int num_contratos = mapa_quantidades_globais.get("num_contratos_comprador");
		int num_contratos_concuido = mapa_quantidades_globais.get("num_contratos_comprador_concluido");
		int num_contratos_cancelados = mapa_quantidades_globais.get("num_contratos_comprador_cancelado");
		int num_contratos_abertos = num_contratos - num_contratos_concuido - num_contratos_cancelados;


		lblTotalContratosComprador.setText(num_contratos + "");
		lblTotalContratosConcluidosComprador.setText(num_contratos_concuido + "");
		lblTotalContratosAbertosComprador.setText(num_contratos_abertos + "");
		lblTotalContratosCanceladosComprador.setText(num_contratos_cancelados + "");

	}

	public void setInfoPontuacao() {

		GerenciarBancoPontuacao gerenciar = new GerenciarBancoPontuacao();
		ArrayList<CadastroPontuacao> pontos_global = gerenciar.getPontuacaoPorCliente(cliente_local.getId());

		int pontuacao_total = 0;
		int num_pontuacao = pontos_global.size();
		int num_estrelas;

		if (pontos_global.size() > 0) {
			for (CadastroPontuacao ponto : pontos_global) {
				pontuacao_total = pontuacao_total + ponto.getPontos();
			}

			num_estrelas = pontuacao_total / num_pontuacao;

			URL url = getClass().getResource("/imagens/icone_" + num_estrelas + "_estrelas.png");
			ImageIcon img = new ImageIcon(url);
			lblNivel.setIcon(img);

		} else {

			lblNivel.setText("Sem pontuação");
		}

	}

	public String trimar(String texto) {
		String aplicar_rtrim = texto.replaceAll("\\s+$", "");
		String aplicar_ltrim = aplicar_rtrim.replaceAll("^\\s+", "");
		return aplicar_ltrim;

	}

	public void relatar(Window isto, RelatorioContratos relatar) {

		String contrato_alterado = relatar.preparar();
			ConverterPdf converter_pdf = new ConverterPdf();
			String pdf_alterado = converter_pdf.word_pdf_file2(contrato_alterado);
			telaEmEsperaRelatoria.setInfo("Arquivo PDF Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
		

	}

	
	

	public void gerarWord(String url_origem) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Word", "*.docx"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						manipular_ultima_pasta.copiarNFe(url_origem, caminho_arquivo);
						Runtime.getRuntime().exec("explorer " + caminho_arquivo);

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}

	public void gerarExcel(String url_origem) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						manipular_ultima_pasta.copiarNFe(url_origem, caminho_arquivo);
						Runtime.getRuntime().exec("explorer " + caminho_arquivo);

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}
}
