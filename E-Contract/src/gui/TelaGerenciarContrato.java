package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FileUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.ContaBancaria;
import classesExtras.CBProdutoPersonalizado;
import classesExtras.Carregamento;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoLogin;
import conexaoBanco.GerenciarBancoProdutos;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.ManipularNotasFiscais;
import manipular.ManipularTxt;
import manipular.Nuvem;
import outros.DadosGlobais;
import outros.GetData;
import outros.JTextFieldPersonalizado;
import tratamento_proprio.Log;
import views_personalizadas.TelaEscolha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TelaGerenciarContrato extends JDialog {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelCarregamento = new JPanel();
	private JPanel painelListaTarefas = new JPanel();

	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");
	private final JLabel lblValorTotalPagamentos;
	InputStream stream = null;
	private final JButton btnEditarContrato = new JButton("Editar");
	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar");
	private final JLabel lblNewLabel = new JLabel("     Modelos de Pagamento");
	private CadastroContrato contrato_local;
	private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
	private SwingController controller = null;
	private SwingViewBuilder factory;
	private TelaGerenciarContrato isto;
	private String servidor_unidade;

	private CadastroCliente cliente_carregamento;
	private CadastroContrato contrato_carregamento;

	private CadastroNFe nota_fiscal;
	private CadastroCliente transportador = new CadastroCliente();
	private CadastroProduto produto = new CadastroProduto();
	private Carregamento carregamento_confirmar = new Carregamento();

	private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;
	private ArrayList<CadastroContrato.Carregamento> lista_carregamentos = null;
	private ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos_contratuais = null;


	private Double peso_total_cargas_nfe = 0.0;
	private Double peso_total_cargas = 0.0;
	private JLabel lblPesoTotalRealCargas, lblPesoTotalNotasFiscais, lblPesoTotal, lblPesoTotalRealRestante,
			lblPesoTotalNotasFiscaisRestante;
	
	
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_sub_contratos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_tarefas = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_carregamentos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_pagamentos_contratuais = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	
	
	private final JLabel lblNewLabel_1 = new JLabel(
			"*Pagamentos apenas informativos, assim como elaborados no contrato");

	private final JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
	private final JButton btnExcluirContrato = new JButton("Excluir");
	private JTable table_tarefas;
	private final JButton btnAdicionarCarregamento = new JButton("Adicionar");
	private final JScrollPane scrollPaneCarregamento;
	private final JLabel lblNewLabel_4 = new JLabel("     Carregamento");
	private JTable table_carregamento;

	private JLabel lblDataContrato, lblCorretor, lblCompradores, lblVendedores, lblValorSaco, lblQuantidade,
			lblValorTotal;
	private JTable table_pagamentos_contratuais;

	private JLabel lblTotalPagamentosRestantes, lblTotalPagamentosEfetuados, lblTotalPagamentos;

	
	
	
	public TelaGerenciarContrato(CadastroContrato contrato) {

		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();

		setModal(true);

		contrato_local = contrato;
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (contrato.getSub_contrato() == 0) {
			this.setTitle("E-Contract - Gerenciar Contrato");

		} else {
			this.setTitle("E-Contract - Gerenciar Sub-Contrato");

		}

		setBounds(100, 100, 1265, 669);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();

		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelDadosIniciais.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);

		if (contrato.getSub_contrato() == 0) {
			// não é um subcontrato

			JPanel painelSubContratos = new JPanel();

			painelSubContratos.setBackground(new Color(255, 255, 255));

			JTable tabela_sub_contratos = new JTable(modelo_sub_contratos);
			// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			tabela_sub_contratos.setBackground(new Color(255, 255, 255));

			modelo_sub_contratos.addColumn("ID");
			modelo_sub_contratos.addColumn("Código");
			modelo_sub_contratos.addColumn("Status");

			modelo_sub_contratos.addColumn("Quantidade");
			modelo_sub_contratos.addColumn("Medida");
			modelo_sub_contratos.addColumn("Produto");
			modelo_sub_contratos.addColumn("Safra");
			modelo_sub_contratos.addColumn("Valor Produto");
			modelo_sub_contratos.addColumn("Valor Total");
			modelo_sub_contratos.addColumn("Compradores");

			modelo_sub_contratos.addColumn("Vendedores");
			modelo_sub_contratos.addColumn("Corretores");
			modelo_sub_contratos.addColumn("Data do Contrato");

			tabela_sub_contratos.getColumnModel().getColumn(0).setPreferredWidth(40);
			tabela_sub_contratos.getColumnModel().getColumn(1).setPreferredWidth(90);
			tabela_sub_contratos.getColumnModel().getColumn(2).setPreferredWidth(170);
			tabela_sub_contratos.getColumnModel().getColumn(3).setPreferredWidth(80);
			tabela_sub_contratos.getColumnModel().getColumn(4).setPreferredWidth(80);
			tabela_sub_contratos.getColumnModel().getColumn(5).setPreferredWidth(70);
			tabela_sub_contratos.getColumnModel().getColumn(6).setPreferredWidth(70);
			tabela_sub_contratos.getColumnModel().getColumn(7).setPreferredWidth(90);
			tabela_sub_contratos.getColumnModel().getColumn(8).setPreferredWidth(80);
			tabela_sub_contratos.getColumnModel().getColumn(9).setPreferredWidth(150);
			tabela_sub_contratos.getColumnModel().getColumn(10).setPreferredWidth(150);
			tabela_sub_contratos.getColumnModel().getColumn(11).setPreferredWidth(100);
			tabela_sub_contratos.getColumnModel().getColumn(12).setPreferredWidth(80);

			JLabel lblSubcontratos = new JLabel("     Sub-Contratos");
			JButton btnAdicionarSubContrato = new JButton("Adicionar");
			JButton btnSelecionarSubContrato = new JButton("Abrir");

			painelPrincipal.addTab("Sub-Contratos", painelSubContratos);
			painelSubContratos.setLayout(null);
			JScrollPane scrollPaneSubContratos;

			scrollPaneSubContratos = new JScrollPane(tabela_sub_contratos);
			scrollPaneSubContratos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPaneSubContratos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneSubContratos.setBackground(Color.WHITE);
			scrollPaneSubContratos.setAutoscrolls(true);
			scrollPaneSubContratos.setBounds(26, 83, 1004, 132);

			painelSubContratos.add(scrollPaneSubContratos);
			lblSubcontratos.setOpaque(true);
			lblSubcontratos.setForeground(Color.WHITE);
			lblSubcontratos.setFont(new Font("Arial", Font.PLAIN, 18));
			lblSubcontratos.setBackground(new Color(0, 51, 0));
			lblSubcontratos.setBounds(0, 22, 230, 31);

			painelSubContratos.add(lblSubcontratos);
			btnAdicionarSubContrato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DadosGlobais dados = DadosGlobais.getInstance();
					dados.setTeraGerenciarContratoPai(isto);
					TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(1, contrato, 0);

				}
			});
			btnAdicionarSubContrato.setBounds(916, 226, 114, 23);

			painelSubContratos.add(btnAdicionarSubContrato);
			btnSelecionarSubContrato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int indiceDaLinha = tabela_sub_contratos.getSelectedRow();

					// abre a tela de gerenciar o contrato selecionado na lista de sub contratos
					TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(
							lista_sub_contratos.get(indiceDaLinha));

				}
			});
			btnSelecionarSubContrato.setBounds(785, 226, 121, 23);

			painelSubContratos.add(btnSelecionarSubContrato);

		}

		lblStatusContrato.setBackground(new Color(0, 128, 128));
		lblStatusContrato.setOpaque(true);
		lblStatusContrato.setForeground(Color.WHITE);
		lblStatusContrato.setFont(new Font("Arial", Font.BOLD, 18));
		lblStatusContrato.setBounds(554, 76, 470, 35);

		painelDadosIniciais.add(lblStatusContrato);
		btnEditarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// argumentos(CadastroModelo modelo, int tipoContrato, CadastroContrato
				// contrato_pai, int flag_edicao) {
				// TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai,
				// int flag_edicao) {

				fecharDocumento();
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.setTeraGerenciarContratoPai(isto);
				if (contrato.getSub_contrato() == 0) {
					// e um contrato pai, abre a tela em modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(0, contrato_local, 1);
				} else {
					// e um subcontrato, o tipo do contrato e 1, e entra no modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(1, contrato_local, 1);

				}
			}
		});

		btnEditarContrato.setBounds(429, 497, 89, 23);

		painelDadosIniciais.add(btnEditarContrato);

		int status = contrato.getStatus_contrato();
		if (status == 1) {
			lblStatusContrato.setText("Status do Contrato: " + "Recolher Assinaturas");

		} else if (status == 2) {
			lblStatusContrato.setText("Status do Contrato: " + "Assinado");

		} else if (status == 3) {
			lblStatusContrato.setText("Status do Contrato: " + "Cumprindo");

		}

		
		GetData data = new GetData();

		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		btnEnviarMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolha escolher = new TelaEscolha(contrato);
			}
		});

		btnEnviarMsg.setBounds(330, 497, 89, 23);

		painelDadosIniciais.add(btnEnviarMsg);
		lblTipoContrato.setOpaque(true);
		lblTipoContrato.setForeground(Color.WHITE);
		lblTipoContrato.setFont(new Font("Arial", Font.BOLD, 16));
		lblTipoContrato.setBackground(new Color(102, 0, 102));
		lblTipoContrato.setBounds(554, 122, 470, 29);

		painelDadosIniciais.add(lblTipoContrato);
		btnExcluirContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean permitir_exclusao = false;
				//Excluir o contrato
				//verificar se ha pagamentos contratuais relacionados a este contrato
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos = gerenciar.getPagamentosContratuais(contrato_local.getId());
				
				if(pagamentos.size() > 0) {
					permitir_exclusao = false;
					JOptionPane.showMessageDialog(null, "Exclusão não permitida!\nHá pagamentos contratuais adicionados a este contrato\nExclua os antes de tentar a exclusão!");
				}else {
					permitir_exclusao = true;
                        
					//verificar se ha carregamentos relacionados a este contrato
					ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar.getCarregamentos(contrato_local.getId());
					if(carregamentos.size() > 0) {
						permitir_exclusao = false;
						JOptionPane.showMessageDialog(null, "Exclusão não permitida!\nHá carregamentos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
					}else {
						permitir_exclusao = true;
						//verificar se ha subcontratos para este contrato
						
						ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());
						if(sub_contratos.size() > 0) {
							permitir_exclusao = false;
							JOptionPane.showMessageDialog(null, "Exclusão não permitida!\nHá sub-contratos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
						}else {
							permitir_exclusao = true;
							//JOptionPane.showMessageDialog(null, "Exclusão permitida!");
							
							excluir_contrato();

						}
						
						

					}
					
					
				}
				
			}
		});
		btnExcluirContrato.setBounds(231, 497, 89, 23);

		painelDadosIniciais.add(btnExcluirContrato);

		JPanel panel = new JPanel();
		panel.setBounds(554, 271, 161, 128);
		painelDadosIniciais.add(panel);

		modelo.addColumn("Id Pagamento");
		modelo.addColumn("Id Conta");
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");

		modelo.addColumn("Banco");

		modelo.addColumn("Codigo");
		modelo.addColumn("Agência");

		modelo.addColumn("Conta");
		modelo.addColumn("Valor");
		modelo.addColumn("Antecipado");
		modelo.addColumn("Cobre");

		modelo.addColumn("Data Pagamento");

		modelo_tarefas.addColumn("Id Tarefas");
		modelo_tarefas.addColumn("Status");

		modelo_tarefas.addColumn("Nome");
		modelo_tarefas.addColumn("Descrição");
		modelo_tarefas.addColumn("Mensagem");

		modelo_tarefas.addColumn("Data");

		modelo_tarefas.addColumn("Hora");
		modelo_tarefas.addColumn("Criador");

		modelo_tarefas.addColumn("Executor");

		modelo_tarefas.addColumn("Hora Agendada");
		modelo_tarefas.addColumn("Data Agendada");
		modelo_tarefas.addColumn("Prioridade");
		painelCarregamento.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Carregamento", painelCarregamento);
		painelCarregamento.setLayout(null);
		
		modelo_carregamentos.addColumn("Id Carregamento");
		modelo_carregamentos.addColumn("Data");
		modelo_carregamentos.addColumn("Contrato Destinado");
		modelo_carregamentos.addColumn("Cliente");
		modelo_carregamentos.addColumn("Transportador");
		modelo_carregamentos.addColumn("Veiculo");
		modelo_carregamentos.addColumn("Produto");
		modelo_carregamentos.addColumn("Peso Carga");
		modelo_carregamentos.addColumn("Peso Nota");
		modelo_carregamentos.addColumn("Peso Restante Nota");
		modelo_carregamentos.addColumn("Nota Fiscal");

		table_carregamento = new JTable(modelo_carregamentos);
		table_carregamento.setBackground(Color.WHITE);

		scrollPaneCarregamento = new JScrollPane(table_carregamento);
		scrollPaneCarregamento.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setBackground(Color.WHITE);
		scrollPaneCarregamento.setAutoscrolls(true);
		scrollPaneCarregamento.setBounds(69, 232, 978, 139);

		painelCarregamento.add(scrollPaneCarregamento);

		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		lblNewLabel_4.setBounds(0, 22, 158, 31);

		painelCarregamento.add(lblNewLabel_4);

		JLabel lblNewLabel_7 = new JLabel("Carregamentos desse contrato:");
		lblNewLabel_7.setBounds(71, 207, 278, 14);
		painelCarregamento.add(lblNewLabel_7);

		JButton btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(isto, "Excluir", "Deseja excluir o carregamento?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						int indiceDaLinha = table_carregamento.getSelectedRow();

						int id_carregamento_selecionado = Integer
								.parseInt(table_carregamento.getValueAt(indiceDaLinha, 0).toString());
						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

						if (gerenciar.removerCarregamento(contrato_local.getId(), id_carregamento_selecionado)) {
							JOptionPane.showMessageDialog(null, "Carregamento Excluido!");
							pesquisar_carregamentos();

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao remover o carregamento selecionado\nConsulte o administrador do sistema!");
						}
					} catch (NumberFormatException n) {
						JOptionPane.showMessageDialog(null, "Nenhum carregamento selecionado para excluir");

					}

				} else {

				}

			}
		});
		btnNewButton_1.setBounds(958, 382, 89, 23);
		painelCarregamento.add(btnNewButton_1);
		btnAdicionarCarregamento.setBounds(964, 508, 83, 23);
		painelCarregamento.add(btnAdicionarCarregamento);

		JLabel lblNewLabel_3 = new JLabel("Cargas:");
		lblNewLabel_3.setBounds(218, 419, 76, 23);
		painelCarregamento.add(lblNewLabel_3);

		lblPesoTotalRealCargas = new JLabel("");
		lblPesoTotalRealCargas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealCargas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalRealCargas.setBounds(158, 488, 314, 23);
		painelCarregamento.add(lblPesoTotalRealCargas);

		JLabel lblNewLabel_3_1 = new JLabel("Notas Fiscais:");
		lblNewLabel_3_1.setBounds(629, 419, 89, 23);
		painelCarregamento.add(lblNewLabel_3_1);

		lblPesoTotalNotasFiscais = new JLabel("");
		lblPesoTotalNotasFiscais.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscais.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscais.setBounds(610, 488, 283, 23);
		painelCarregamento.add(lblPesoTotalNotasFiscais);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(372, 60, 690, 112);
		painelCarregamento.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

		JLabel lblNewLabel_5 = new JLabel("Data:");
		lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNewLabel_5.setBorder(null);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_5, "cell 0 0,alignx right,aligny center");

		lblDataContrato = new JLabel("data contrato");
		lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblDataContrato, "cell 1 0,alignx center,aligny center");

		JLabel lblNewLabel_9 = new JLabel("Corretor:");
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_9, "cell 0 1,alignx right,growy");

		lblCorretor = new JLabel("corretor");
		lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblCorretor, "cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_6 = new JLabel("Compradores:");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_6, "cell 0 2,alignx right,growy");

		lblCompradores = new JLabel("compradores");
		lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblCompradores, "cell 1 2,alignx center,aligny center");

		JLabel lblNewLabel_8 = new JLabel("Vendedores:");
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_8, "cell 0 3,alignx right,aligny center");

		lblVendedores = new JLabel("vendedores");
		lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblVendedores, "cell 1 3,alignx center,aligny center");

		JLabel lblnewvalorsaco = new JLabel("Valor Unidade:");
		lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

		lblValorSaco = new JLabel("valor saco");
		lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblValorSaco, "cell 1 4,alignx center,aligny center");

		JLabel lblNewLabel_10 = new JLabel("Quantidade:");
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_10, "cell 2 4,alignx right,aligny center");

		lblQuantidade = new JLabel("quantidade");
		lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblQuantidade, "cell 3 4,alignx center,aligny center");

		JLabel lblNewLabel_11 = new JLabel("Valor Total:");
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_11, "cell 4 4,alignx right,aligny center");

		lblValorTotal = new JLabel("valortotal");
		lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblValorTotal, "cell 5 4,alignx center,aligny center");

		JLabel lblNewLabel_12 = new JLabel("Total:");
		lblNewLabel_12.setBounds(100, 459, 46, 14);
		painelCarregamento.add(lblNewLabel_12);

		lblPesoTotal = new JLabel("0.0 Kg");
		lblPesoTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotal.setBounds(158, 453, 314, 23);
		painelCarregamento.add(lblPesoTotal);

		JLabel lblNewLabel_13 = new JLabel("Total Carregado:");
		lblNewLabel_13.setBounds(57, 494, 101, 14);
		painelCarregamento.add(lblNewLabel_13);

		JLabel lblNewLabel_13_1 = new JLabel("Restante:");
		lblNewLabel_13_1.setBounds(93, 530, 65, 14);
		painelCarregamento.add(lblNewLabel_13_1);

		lblPesoTotalRealRestante = new JLabel("0.0 Kg");
		lblPesoTotalRealRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalRealRestante.setBounds(158, 524, 314, 23);
		painelCarregamento.add(lblPesoTotalRealRestante);

		JLabel lblNewLabel_13_2 = new JLabel("Total Carregado:");
		lblNewLabel_13_2.setBounds(506, 494, 101, 14);
		painelCarregamento.add(lblNewLabel_13_2);

		JLabel lblNewLabel_13_2_1 = new JLabel("Restante:");
		lblNewLabel_13_2_1.setBounds(542, 526, 65, 14);
		painelCarregamento.add(lblNewLabel_13_2_1);

		lblPesoTotalNotasFiscaisRestante = new JLabel("0 Kg | 0 Sacos");
		lblPesoTotalNotasFiscaisRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscaisRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscaisRestante.setBounds(610, 522, 283, 23);
		painelCarregamento.add(lblPesoTotalNotasFiscaisRestante);
		painelPagamentos.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(null);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(0, 51, 0));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setBounds(0, 22, 230, 31);

		PainelInformativo painel_informacoes_tab_pagamentos = new PainelInformativo();
		painelPagamentos.add(painel_informacoes_tab_pagamentos);

		painelPagamentos.add(lblNewLabel);

		JTable tabela_modelo_pagamentos = new JTable(modelo);
		// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_modelo_pagamentos.setBackground(new Color(255, 255, 255));

		tabela_modelo_pagamentos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_modelo_pagamentos.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela_modelo_pagamentos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(6).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(7).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(8).setPreferredWidth(90);

		JScrollPane scrollPane = new JScrollPane(tabela_modelo_pagamentos);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 85, 721, 132);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(255, 255, 255));
		painelPagamentos.add(scrollPane);
		lblNewLabel_1.setForeground(new Color(0, 51, 0));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.ORANGE);
		lblNewLabel_1.setBounds(314, 60, 445, 14);

		painelPagamentos.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Valor Total dos pagamentos:");
		lblNewLabel_2.setBounds(495, 238, 166, 14);
		painelPagamentos.add(lblNewLabel_2);

		lblValorTotalPagamentos = new JLabel("");
		lblValorTotalPagamentos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorTotalPagamentos.setBounds(658, 228, 101, 31);
		painelPagamentos.add(lblValorTotalPagamentos);

		JLabel lblNewLabel_7_1 = new JLabel("Pagamentos desse contrato:");
		lblNewLabel_7_1.setBounds(125, 264, 278, 14);
		painelPagamentos.add(lblNewLabel_7_1);

		table_pagamentos_contratuais = new JTable(modelo_pagamentos_contratuais);
		
		modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		modelo_pagamentos_contratuais.addColumn("Data");
		modelo_pagamentos_contratuais.addColumn("Valor");
		modelo_pagamentos_contratuais.addColumn("Depositante");
		modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		modelo_pagamentos_contratuais.addColumn("Favorecido");
		modelo_pagamentos_contratuais.addColumn("Conta Favorecido");



		JScrollPane scrollPanePagamentos = new JScrollPane(table_pagamentos_contratuais);
		scrollPanePagamentos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setAutoscrolls(true);
		scrollPanePagamentos.setBackground(new Color(255, 255, 255));
		scrollPanePagamentos.setBounds(125, 289, 976, 140);
		painelPagamentos.add(scrollPanePagamentos);

		JButton btnExcluirPagamento = new JButton("Excluir");
		btnExcluirPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(isto, "Excluir", "Deseja excluir o pagamento?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						int indiceDaLinha = table_pagamentos_contratuais.getSelectedRow();

						int id_pagamento_selecionado = Integer
								.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());
						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

						if (gerenciar.removerPagamentoContratual(contrato_local.getId(), id_pagamento_selecionado)) {
							JOptionPane.showMessageDialog(null, "Pagamento Excluido!");
							pesquisar_pagamentos();

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao remover o pagamento selecionado\nConsulte o administrador do sistema!");
						}
					} catch (NumberFormatException n) {
						JOptionPane.showMessageDialog(null, "Nenhum pagamento selecionado para excluir");

					}

				} else {

				}
			}
		});
		btnExcluirPagamento.setBounds(1012, 431, 89, 23);
		painelPagamentos.add(btnExcluirPagamento);

		JButton btnAdicionarPagamento = new JButton("Novo Pagamento");
		btnAdicionarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConfirmarPagamentoContratual tela_confirmar = new TelaConfirmarPagamentoContratual(contrato_local);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);
			}
		});
		btnAdicionarPagamento.setBounds(1060, 578, 153, 23);
		painelPagamentos.add(btnAdicionarPagamento);
		
		JLabel lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setBounds(148, 462, 45, 14);
		painelPagamentos.add(lblNewLabel_14);

		 lblTotalPagamentos = new JLabel("");
		lblTotalPagamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentos.setBounds(199, 453, 143, 23);
		painelPagamentos.add(lblTotalPagamentos);
		

		
		 lblTotalPagamentosEfetuados = new JLabel("");
		lblTotalPagamentosEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosEfetuados.setBounds(199, 487, 143, 23);
		painelPagamentos.add(lblTotalPagamentosEfetuados);
		
		JLabel lblNewLabel_14_1 = new JLabel("Efetuados:");
		lblNewLabel_14_1.setBounds(125, 493, 68, 14);
		painelPagamentos.add(lblNewLabel_14_1);
		
		 lblTotalPagamentosRestantes = new JLabel("");
		lblTotalPagamentosRestantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosRestantes.setBounds(199, 518, 143, 23);
		painelPagamentos.add(lblTotalPagamentosRestantes);
		
		JLabel lblNewLabel_14_2 = new JLabel("Restante:");
		lblNewLabel_14_2.setBounds(135, 527, 68, 14);
		painelPagamentos.add(lblNewLabel_14_2);
		painelListaTarefas.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Lista Tarefas", painelListaTarefas);
		painelListaTarefas.setLayout(null);

		table_tarefas = new JTable(modelo_tarefas);
		table_tarefas.setBackground(Color.WHITE);

		table_tarefas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_tarefas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_tarefas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_tarefas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(7).setPreferredWidth(90);

		JScrollPane scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setBackground(Color.WHITE);
		scrollPaneTarefas.setAutoscrolls(true);
		scrollPaneTarefas.setBounds(28, 55, 1002, 470);
		painelListaTarefas.add(scrollPaneTarefas);
		btnAdicionarCarregamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaConfirmarCarregamento tela_confirmar = new TelaConfirmarCarregamento(contrato_local);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);

			}
		});

		setPagamentos(contrato);

		setSubContratos(contrato_local);

		if (contrato_local.getSub_contrato() == 1) {
			// é um sub contrato
			lblTipoContrato.setText("Tipo Contrato: Sub-Contrato");
		} else {
			lblTipoContrato.setText("Tipo Contrato: Contrato Original");

		}

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);
		getTarefas();

		setarInformacoesPainelCarregamentos();
		pesquisar_carregamentos();
		pesquisar_pagamentos();

		this.setLocationRelativeTo(null);

		this.setVisible(true);

	}

	public void getTarefas() {
		modelo_tarefas.setNumRows(0);

		if (lista_tarefas != null) {
			lista_tarefas.clear();
		} else {
			lista_tarefas = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_tarefas = gerenciar.getTarefas(contrato_local.getId());

		/*
		 * modelo_tarefas.addColumn("Id Tarefas"); modelo_tarefas.addColumn("Status");
		 * 
		 * modelo_tarefas.addColumn("Nome"); modelo_tarefas.addColumn("Descrição");
		 * modelo_tarefas.addColumn("Mensagem");
		 * 
		 * modelo_tarefas.addColumn("Data");
		 * 
		 * 
		 * modelo_tarefas.addColumn("Hora"); modelo_tarefas.addColumn("Criador");
		 * 
		 * modelo_tarefas.addColumn("Executor");
		 * 
		 * modelo_tarefas.addColumn("Hora Agendada");
		 * modelo_tarefas.addColumn("Data Agendada");
		 * modelo_tarefas.addColumn("Prioridade");
		 * 
		 */
		for (CadastroContrato.CadastroTarefa tarefa : lista_tarefas) {

			String status_tarefa = "";
			String prioridade = "";

			if (tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
			}

			if (tarefa.getPrioridade() == 1) {
				prioridade = "Imediata";
			}

			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			modelo_tarefas.addRow(new Object[] { tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(),
					tarefa.getDescricao_tarefa(), tarefa.getMensagem(), tarefa.getData(), tarefa.getHora(),
					criador.getNome(), executor.getNome(), tarefa.getHora_agendada(), tarefa.getData_agendada(),
					prioridade

			});
		}

	}

	public void setSubContratos(CadastroContrato contrato_na_funcao) {
		modelo_sub_contratos.setNumRows(0);
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_sub_contratos.clear();

		for (CadastroContrato contrato : gerenciar.getSubContratos(contrato_na_funcao.getId())) {
			String cpf, cnpj, nome;

			CadastroCliente compradores[] = contrato.getCompradores();
			CadastroCliente vendedores[] = contrato.getVendedores();
			CadastroCliente corretores[] = contrato.getCorretores();

			String nome_corretores = "";
			String nome_vendedores = "";
			String nome_compradores = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretores = corretores[0].getNome_empresarial();
				} else {
					nome_corretores = corretores[0].getNome_fantaia();

				}
			}

			contrato.setNomes_corretores(nome_corretores);

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}
			contrato.setNomes_compradores(nome_compradores);

			for (CadastroCliente vendedor : contrato.getVendedores()) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
				}
			}
			contrato.setNomes_vendedores(nome_vendedores);

			int status = contrato.getStatus_contrato();
			String text_status = "";
			if (status == 1) {
				text_status = "Recolher Assinaturas".toUpperCase();

			} else if (status == 2) {
				text_status = "Assinado".toUpperCase();

			} else if (status == 3) {
				text_status = "Cumprindo".toUpperCase();

			}

			/*
			 * modelo.addColumn("ID"); modelo.addColumn("Código");
			 * modelo.addColumn("Status");
			 * 
			 * modelo.addColumn("Quantidade"); modelo.addColumn("Medida");
			 * modelo.addColumn("Produto"); modelo.addColumn("Safra");
			 * modelo.addColumn("Valor Produto"); modelo.addColumn("Valor Total");
			 * modelo.addColumn("Vendedores"); modelo.addColumn("Compradores");
			 * modelo.addColumn("Corretores"); modelo.addColumn("Data do Contrato");
			 * 
			 */

			if (contrato.getSub_contrato() == 1) {

				modelo_sub_contratos.addRow(new Object[] { contrato.getId(), contrato.getCodigo(), text_status,
						contrato.getQuantidade(), contrato.getMedida().toUpperCase(),
						contrato.getModelo_safra().getProduto().getNome_produto(),
						contrato.getModelo_safra().getAno_colheita() + "/"
								+ contrato.getModelo_safra().getAno_plantio(),
						"R$ " + contrato.getValor_produto(), "R$ " + contrato.getValor_a_pagar(),
						contrato.getNomes_compradores(), contrato.getNomes_vendedores(), contrato.getNomes_corretores(),
						contrato.getData_contrato()

				});
				lista_sub_contratos.add(contrato);
			}
		}
	}

	public void setPagamentos(CadastroContrato contrato) {
		String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, antecipado, cobre = "", data_pagamento;

		modelo.setNumRows(0);
		float valor_total_pagamentos = 0;

		if (contrato.getPagamentos() != null) {
			for (CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {

				ContaBancaria conta_bc = pag.getConta();

				if (conta_bc != null) {
					id = Integer.toString(conta_bc.getId_conta());
					cpf = conta_bc.getCpf_titular();
					banco = conta_bc.getBanco();
					nome = conta_bc.getNome();
					codigo = conta_bc.getCodigo();
					agencia = conta_bc.getAgencia();
					conta = conta_bc.getConta();
				} else {
					id = "00";
					cpf = "Há Informar";
					banco = "Há Informar";
					nome = "Há Informar";
					codigo = "Há Informar";
					agencia = "Há Informar";
					conta = "Há Informar";
				}
				
				
				if(pag.getPagamento_adiantado() == 1) {
					antecipado = "SIM";
				}else {
					antecipado = "NÃO";
				}
				
				cobre = calculoCobertura(Double.parseDouble(pag.getValor().toPlainString()));
				valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
				System.out.println("o valor total agora e: " + valor_total_pagamentos);

				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(pag.getValor_string()));
				System.out.println(valorString);

				modelo.addRow(new Object[] { pag.getId(), id, cpf, nome, banco, codigo, agencia, conta, valorString, antecipado, cobre,
						pag.getData_pagamento() });
			}
		}

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		System.out.println(valorString);

		lblValorTotalPagamentos.setText(valorString);
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

			propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 0.85f);

			factory = new SwingViewBuilder(controller, propriedades);
			// Use the factory to build a JPanel that is pre-configured
			// with a complete, active Viewer UI.

			/*controller.getDocumentViewController().setAnnotationCallback(
					new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
*/
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (painel_vizualizar == null) {

					painel_vizualizar = new JPanel();
					painel_vizualizar = factory.buildViewerPanel();
					controller.openDocument(url);
					// viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
					// viewerComponentPanel.setMaximumSize(new Dimension(400, 370));

					controller.openDocument(url);
					painel_vizualizar.setBounds(10, 25, 508, 461);

					painelDadosIniciais.add(painel_vizualizar);
				} else {
					controller.openDocument(url);
					painel_vizualizar.repaint();
					painel_vizualizar.updateUI();
					painelDadosIniciais.add(painel_vizualizar);

				}

			}
		});
	}

	public void fecharDocumento() {

		if (controller != null) {
			controller.closeDocument();
		}

	}

	public void atualizarContratoLocal() {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		contrato_local = gerenciar.getContrato(contrato_local.getId());
		setPagamentos(contrato_local);
		setSubContratos(contrato_local);

		// criar acopia temporaria

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public String criarCopiaTemporaria(String url, String codigo) throws IOException {
		ManipularTxt manipular = new ManipularTxt();
		return manipular.copiar(url, codigo);
	}

	public void pesquisar_carregamentos() {

		ArrayList<CadastroNFe> notas_fiscais_carregamentos = new ArrayList<>();
		modelo_carregamentos.setNumRows(0);
		peso_total_cargas_nfe = 0.0;
		peso_total_cargas = 0.0;

		if (lista_carregamentos != null) {
			lista_carregamentos.clear();
		} else {
			lista_carregamentos = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());

		/*
		 * modelo_carregamentos.addColumn("Id Carregamento");
		 * modelo_carregamentos.addColumn("Data");
		 * modelo.addColumn("Contrato Destinado"); modelo.addColumn("Cliente");
		 * modelo.addColumn("Transportador"); modelo.addColumn("Veiculo");
		 * modelo.addColumn("Produto"); modelo.addColumn("Peso Real Carga");
		 * modelo.addColumn("Nota Fiscal");
		 * 
		 */
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {

			// pegar dados do contrato
			CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

			// pegar cliente
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente cliente_carregamento = gerenciar_clientes.getCliente(carregamento.getId_cliente());
			String nome_cliente;
			if (cliente_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_carregamento.getNome_empresarial();
			} else {
				nome_cliente = cliente_carregamento.getNome_fantaia();
			}

			// pegar transportador e veiculo
			CadastroCliente transportador_carregamento = gerenciar_clientes
					.getCliente(carregamento.getId_transportador());
			CadastroCliente.Veiculo veiculo_carregamento = null;
			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
					veiculo_carregamento = veiculo;
					break;
				}
			}

			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			NumberFormat z = NumberFormat.getNumberInstance();
			// pegar a nota
			ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
			CadastroNFe nota = manipular.getNotaFiscal(carregamento.getCodigo_nota_fiscal());

			// definir peso carregamento
			double peso_carregado = carregamento.getPeso_real_carga();
			// definir peso da nota
			double peso_nota = Double.parseDouble(nota.getQuantidade().replace(".", "").replace(",", "."));
			// definir peso restante para nota
			double peso_nota_restante = peso_carregado - peso_nota;

			modelo_carregamentos.addRow(new Object[] { carregamento.getId_carregamento(), carregamento.getData(),
					contrato_destinatario.getCodigo(), nome_cliente,
					transportador_carregamento.getNome() + " " + transportador_carregamento.getSobrenome(),
					veiculo_carregamento.getPlaca_trator(), produto_carregamento.getNome_produto(),
					z.format(peso_carregado) + " Kg", z.format(peso_nota) + " Kg", z.format(peso_nota_restante) + " Kg",
					carregamento.getCodigo_nota_fiscal()

			});

			notas_fiscais_carregamentos.add(nota);

			peso_total_cargas += carregamento.getPeso_real_carga();
		}

		// faz a soma dos pesos das notas fiscais
		for (CadastroNFe nfe : notas_fiscais_carregamentos) {

			if (nfe != null) {
				peso_total_cargas_nfe += Double.parseDouble(nfe.getQuantidade().replace(".", "").replace(",", "."));
			}

		}

		double peso_total_kg = 0, peso_total_sacos = 0, peso_carregado_kg = 0, peso_carregado_sacos = 0,
				peso_restante_kg = 0, peso_restante_sacos = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		if (contrato_local.getMedida().equals("KG")) {
			peso_total_kg = contrato_local.getQuantidade();
			peso_total_sacos = peso_total_kg / 60;

			peso_carregado_kg = peso_total_cargas;
			peso_carregado_sacos = peso_total_cargas / 60;

			peso_restante_kg = peso_total_kg - peso_carregado_kg;
			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;

		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			peso_total_sacos = contrato_local.getQuantidade();
			peso_total_kg = peso_total_sacos * 60;

			peso_carregado_sacos = peso_total_cargas / 60;
			peso_carregado_kg = peso_total_cargas;

			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;
			peso_restante_kg = peso_total_kg - peso_carregado_kg;

		}

		lblPesoTotal.setText(z.format(peso_total_kg) + " Kg " + " | " + z.format(peso_total_sacos) + " Sacos");
		// peso total das cargas
		lblPesoTotalRealCargas
				.setText(z.format(peso_carregado_kg) + " Kg" + " | " + z.format(peso_carregado_sacos) + " Sacos");
		// peso restante
		lblPesoTotalRealRestante
				.setText(z.format(peso_restante_kg) + " Kg" + " | " + z.format(peso_restante_sacos) + " Sacos");

		double peso_total_nf_kg = peso_total_cargas_nfe;
		double peso_total_nf_sacos = peso_total_nf_kg / 60;

		double peso_total_nf_kg_restante = peso_carregado_kg - peso_total_nf_kg;
		double peso_total_nf_sacos_restante = peso_carregado_sacos - peso_total_nf_sacos;

		lblPesoTotalNotasFiscais
				.setText(z.format(peso_total_nf_kg) + " Kg" + " | " + z.format(peso_total_nf_sacos) + " Sacos");

		lblPesoTotalNotasFiscaisRestante.setText(z.format(peso_total_nf_kg_restante) + " Kg" + " | "
				+ z.format(peso_total_nf_sacos_restante) + " Sacos");

	}
	
	
	
	
	public void pesquisar_pagamentos() {

		modelo_pagamentos_contratuais.setNumRows(0);

		double valor_total_pagamentos = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_pagamentos_restantes = 0;
		
		if (lista_pagamentos_contratuais != null) {
			lista_pagamentos_contratuais.clear();
		} else {
			lista_pagamentos_contratuais = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_pagamentos_contratuais = gerenciar.getPagamentosContratuais(contrato_local.getId());

		/*
		
		modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		modelo_pagamentos_contratuais.addColumn("Data");
		modelo_pagamentos_contratuais.addColumn("Valor");
		modelo_pagamentos_contratuais.addColumn("Depositante");
		modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		modelo_pagamentos_contratuais.addColumn("Favorecido");
		modelo_pagamentos_contratuais.addColumn("Conta Favorecido");
		 */
		for (CadastroContrato.CadastroPagamentoContratual pagamento : lista_pagamentos_contratuais) {

			// pegar data do pagmento
			String data = pagamento.getData_pagamento();
			double valor_pagamento = pagamento.getValor_pagamento();
			

			// pegar depositante
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente depositante = gerenciar_clientes.getCliente(pagamento.getId_depositante());
			String nome_depositante = "" ;
			if (depositante.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_depositante = depositante.getNome_empresarial();
			} else {
				nome_depositante = depositante.getNome_fantaia();
			}
			
			//pegar conta do depositante
			ContaBancaria conta_depositante = gerenciar_clientes.getConta(pagamento.getId_conta_depositante());
			

			// pegar favorecido
						CadastroCliente favorecido = gerenciar_clientes.getCliente(pagamento.getId_favorecido());
						String nome_favorecido = "";
						if (favorecido.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_favorecido = favorecido.getNome_empresarial();
						} else {
							nome_favorecido = favorecido.getNome_fantaia();
						}

						//pegar conta do favorecido
						ContaBancaria conta_favorecido = gerenciar_clientes.getConta(pagamento.getId_conta_favorecido());
						
						Locale ptBr = new Locale("pt", "BR");
						String valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_pagamento);


			modelo_pagamentos_contratuais.addRow(new Object[] { pagamento.getId_pagamento(), data,
					valorString, nome_depositante, conta_depositante.getNome() , nome_favorecido, conta_favorecido.getNome()

			});

			valor_total_pagamentos_efetuados += valor_pagamento;

		}
		
		
		
		Locale ptBr = new Locale("pt", "BR");
		String valor = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_pagamentos);
		
		
		lblTotalPagamentos.setText(valor);
		
		 valor = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_pagamentos_efetuados);
		
		lblTotalPagamentosEfetuados.setText(valor);
		
		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados;
		 valor = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_pagamentos_restantes);
		
		lblTotalPagamentosRestantes.setText(valor);

	}
	

	public void setarInformacoesPainelCarregamentos() {

		lblDataContrato.setText(contrato_local.getData_contrato());

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		CadastroCliente corretores[] = contrato_local.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}
		lblCorretor.setText(nome_corretores);

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		lblCompradores.setText(nome_compradores);

		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores += vendedor.getNome_empresarial();
				} else {
					nome_vendedores += vendedor.getNome_fantaia();

				}
				nome_vendedores += " ,";

			}
		}
		lblVendedores.setText(nome_vendedores);

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

		lblValorSaco.setText(valorString);

		NumberFormat z = NumberFormat.getNumberInstance();

		lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

		String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
		lblValorTotal.setText(valor_total);

	}

	public class PainelInformativo extends JPanel {

		private JLabel _lblDataContrato, _lblCorretor, _lblCompradores, _lblVendedores, _lblValorSaco, _lblQuantidade,
				_lblValorTotal;

		public PainelInformativo() {

			setBackground(Color.WHITE);
			setBounds(815, 47, 380, 164);
			setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

			JLabel _lblNewLabel_5 = new JLabel("Data:");
			_lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
			_lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			_lblNewLabel_5.setBorder(null);
			_lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_5, "cell 0 0,alignx right,aligny center");

			_lblDataContrato = new JLabel("data contrato");
			_lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblDataContrato, "cell 1 0,alignx center,aligny center");

			JLabel _lblNewLabel_9 = new JLabel("Corretor:");
			_lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_9, "cell 0 1,alignx right,growy");

			_lblCorretor = new JLabel("corretor");
			_lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCorretor, "cell 1 1,alignx center,aligny center");

			JLabel _lblNewLabel_6 = new JLabel("Compradores:");
			_lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_6, "cell 0 2,alignx right,growy");

			_lblCompradores = new JLabel("compradores");
			_lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCompradores, "cell 1 2,alignx center,aligny center");

			JLabel _lblNewLabel_8 = new JLabel("Vendedores:");
			_lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_8, "cell 0 3,alignx right,aligny center");

			_lblVendedores = new JLabel("vendedores");
			_lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblVendedores, "cell 1 3,alignx center,aligny center");

			JLabel _lblnewvalorsaco = new JLabel("Valor Unidade:");
			_lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

			_lblValorSaco = new JLabel("valor saco");
			_lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorSaco, "cell 1 4,alignx center,aligny center");

			JLabel _lblNewLabel_10 = new JLabel("Quantidade:");
			_lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_10, "cell 0 5,alignx right,aligny center");

			_lblQuantidade = new JLabel("quantidade");
			_lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblQuantidade, "cell 1 5,alignx center,aligny center");

			JLabel _lblNewLabel_11 = new JLabel("Valor Total:");
			_lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_11, "cell 0 6,alignx right,aligny center");

			_lblValorTotal = new JLabel("valortotal");
			_lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorTotal, "cell 1 6,alignx center,aligny center");

			setarInformacoesPainelCarregamentosInformativos();
		}

		public void setarInformacoesPainelCarregamentosInformativos() {

			_lblDataContrato.setText(contrato_local.getData_contrato());

			CadastroCliente compradores[] = contrato_local.getCompradores();
			CadastroCliente vendedores[] = contrato_local.getVendedores();
			CadastroCliente corretores[] = contrato_local.getCorretores();

			String nome_corretores = "";
			String nome_vendedores = "";
			String nome_compradores = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretores = corretores[0].getNome_empresarial();
				} else {
					nome_corretores = corretores[0].getNome_fantaia();

				}
			}
			_lblCorretor.setText(nome_corretores);

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}
			_lblCompradores.setText(nome_compradores);

			for (CadastroCliente vendedor : vendedores) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
					nome_vendedores += " ,";

				}
			}
			_lblVendedores.setText(nome_vendedores);

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

			_lblValorSaco.setText(valorString);

			NumberFormat z = NumberFormat.getNumberInstance();

			_lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

			String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
			_lblValorTotal.setText(valor_total);

		}
	}
	
	
	public void excluir_contrato() {
		
		
		if (JOptionPane.showConfirmDialog(isto, 
	            "Deseja realmente excluir o contrato?", "Excluir", 
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			    
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean excluido = gerenciar.remover_contrato_rotina(contrato_local.getId());
			if(excluido) {
				
				//excluir diretorio do arquivo
				ManipularTxt manipular = new ManipularTxt();
				
				boolean exclusao_diretorio = manipular.limparDiretorio(new File(configs_globais.getServidorUnidade() + contrato_local.getCaminho_diretorio_contrato()));
				if(exclusao_diretorio) {
					JOptionPane.showMessageDialog(null, "Contrato Excluido!");
					
					//excluir diretorio da nuvem
					Nuvem nuvem = new Nuvem();
			         nuvem.abrir();
			         nuvem.testar();
			         nuvem.listar();
			        boolean exclusao_nuvem =  nuvem.deletarArquivo(contrato_local.getNome_arquivo());
			        if(exclusao_nuvem) {
			          JOptionPane.showMessageDialog(null, "Contrato excluido");
			  		isto.dispose();

			        }else {
				      JOptionPane.showMessageDialog(null, "Contrato excluido, diretorio local apagado, mas o arquivo ainda está na nuvem\nConsulte o administrador!");

			        }
					
				}else {
					JOptionPane.showMessageDialog(null, "Contrato excluido, mas o diretorio nao pode ser apagado!\nConsulte o administrador");
				}
				
				
			}else {
				JOptionPane.showMessageDialog(null, "Erro ao excluir o contrato\nConsulte o administrador!");
				

			}

	        }
		

		
		
	}
	
	public String calculoCobertura(double valor_pagamento) {
		String retorno = "";
		//quantidade
		double quantidade = contrato_local.getQuantidade();
		double preco = contrato_local.getValor_produto();

		
		
		//unidade de medida
		if(contrato_local.getMedida().equalsIgnoreCase("TON")) {
			//unidade em toneladas
			

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado) ;
			
		}else if(contrato_local.getMedida().equalsIgnoreCase("SACOS")) {
			//unidade em sacos
	
			
			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado) ;
			
		}else if(contrato_local.getMedida().equalsIgnoreCase("KG")) {
			//unidade em quilogramas
			

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado) ;
		}
		
		
		
		return retorno;
	}
}
