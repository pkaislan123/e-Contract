package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableCellRenderer;
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
import javax.swing.table.TableCellRenderer;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroNFe;
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
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;
import net.miginfocom.swing.MigLayout;

public class TelaContratos extends JDialog {

	private static ArrayList<CadastroContrato> lista_contratos = new ArrayList<>();
	private JDialog telaPai;
	private JTable tabela;

	private final JPanel painelPrincipal = new JPanel();
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private JLabel lblTotalContratosEmAnalise,
	lblTotalContratosAssinar,
	lblTotalContratosConcluidos,
	lblTotalContratosAssinados;

	private TelaContratos isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;
    private JLabel lblTotalSacosKGs,lblNumContratos;

	private ContratoTableModel modelo_contratos = new ContratoTableModel();
	private TableRowSorter<ContratoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private JTextField entStatus;
	private int id_contrato_pai_para_replica_global = 0;

	private int flag_retorno_global;
	private JTextField entTransgenia;

	public TelaContratos(int flag_retorno, Window janela_pai) {
		//setModal(true);
		// setAlwaysOnTop(true);

		isto = this;
		flag_retorno_global = flag_retorno;
		// setResizable(false);
		setTitle("E-Contract - Contratos");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1074, 631);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(0, null, 0,isto);
			}
		});
		// btnContrato.setIcon(new
		// ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");
		btnContrato.setBounds(930, 539, 106, 28);
		painelPrincipal.add(btnContrato);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(30, 180, 1006, 266);
		painelPrincipal.add(panel);
		

		 tabela = new JTable(modelo_contratos);
		TableCellRenderer renderer = new EvenOddRenderer();
		tabela.setDefaultRenderer(Object.class, renderer);
		sorter = new TableRowSorter<ContratoTableModel>(modelo_contratos);

		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(70);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(10).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(11).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(12).setPreferredWidth(120);

		tabela.setRowSorter(sorter);

		tabela.setBackground(new Color(255, 255, 255));
		// tabela.setPreferredSize(new Dimension(0, 200));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		
		
		tabela.setRowHeight (30); 


		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// pesquisar();
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
				CadastroCliente corretores_contrato_selecionado[] = contrato_selecionado.getCorretores();
				CadastroCliente compradores_contrato_selecionado[] = contrato_selecionado.getCompradores();
				CadastroCliente vendedores_contrato_selecionado[] = contrato_selecionado.getVendedores();

				for (CadastroCliente corretor : corretores_contrato_selecionado) {
					if (corretor != null)
						System.out.println("Nome do corretor : " + corretor.getNome_empresarial() + " outro nome: "
								+ corretor.getNome_fantaia());
				}

				for (CadastroCliente comprador : compradores_contrato_selecionado) {
					if (comprador != null)
						System.out.println("Nome do comprador : " + comprador.getNome_empresarial() + " outro nome: "
								+ comprador.getNome_fantaia());
				}

				for (CadastroCliente vendedor : vendedores_contrato_selecionado) {
					if (vendedor != null)
						System.out.println("Nome do vendedor : " + vendedor.getNome_empresarial() + " outro nome: "
								+ vendedor.getNome_fantaia());
				}

				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado,isto);

			}
		});
		btnAbrir.setBounds(837, 535, 81, 36);
		getContentPane().add(btnAbrir);

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela.getSelectedRow();
				int id_contrato = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar.getContrato(id_contrato);

				if (flag_retorno == 1) {

					((TelaConfirmarCarregamento) telaPai).setContratoCarregamento(contrato_selecionado);
					isto.dispose();

				} else if (flag_retorno == 2) {
					if(contrato_selecionado.getStatus_contrato() != 3) {
					((TelaConfirmarTransferenciaPagamentoContratual) telaPai)
							.setContratoDestintario(contrato_selecionado);
					isto.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Não é possivel transferir um pagamento para um contrato finalizado");
					}

				} else if (flag_retorno == 3) {

					if(contrato_selecionado.getStatus_contrato() != 3) {
					((TelaReplicarCarregamento) telaPai).setSubContrato(contrato_selecionado);
					isto.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Não é possivel replicar um carregamento para um contrato finalizado");
					}

				} else if (flag_retorno == 4) {

					if(contrato_selecionado.getStatus_contrato() != 3) {

					((TelaReplicarPagamento) telaPai).setSubContrato(contrato_selecionado);
					isto.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Não é possivel replicar um pagamento para um contrato finalizado");
					}

				}
			}
		});
		btnSelecionar.setBounds(738, 539, 87, 28);
		painelPrincipal.add(btnSelecionar);

		JButton btnImportarTerceiros = new JButton("Importar");
		btnImportarTerceiros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importarContratoTerceiros();
			}
		});
		btnImportarTerceiros.setBounds(652, 539, 74, 28);
		painelPrincipal.add(btnImportarTerceiros);

		entNomeComprador = new JTextField();

		entNomeComprador.setBounds(115, 28, 456, 33);
		painelPrincipal.add(entNomeComprador);
		entNomeComprador.setColumns(10);

		JLabel lblNewLabel = new JLabel("Comprador:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 34, 75, 17);
		painelPrincipal.add(lblNewLabel);

		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVendedor.setBounds(41, 86, 64, 17);
		painelPrincipal.add(lblVendedor);

		entNomeVendedor = new JTextField();
		entNomeVendedor.setColumns(10);
		entNomeVendedor.setBounds(115, 80, 456, 33);
		painelPrincipal.add(entNomeVendedor);

		entProduto = new JTextField();
		entProduto.setColumns(10);
		entProduto.setBounds(115, 136, 195, 33);
		painelPrincipal.add(entProduto);

		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduto.setBounds(50, 142, 55, 17);
		painelPrincipal.add(lblProduto);

		JLabel lblSafra = new JLabel("Safra:");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSafra.setBounds(333, 142, 36, 17);
		painelPrincipal.add(lblSafra);

		entSafra = new JTextField();
		entSafra.setColumns(10);
		entSafra.setBounds(407, 136, 164, 33);
		painelPrincipal.add(entSafra);

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				filtrar();
			}
		});
		btnFiltrar.setBounds(950, 137, 89, 23);
		painelPrincipal.add(btnFiltrar);

		JButton btnLimparFiltros = new JButton("Limpar");
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));

				NumberFormat z = NumberFormat.getNumberInstance();

				double quantidade_sacos_total = 0;
				double quantidade_kg_total = 0;
				
				int total_contratos_em_analise = 0;
				int total_contratos_assinar = 0;
				int total_contratos_assinado = 0;
				int total_contratos_concluido = 0;
				  for(int row = 0;row < tabela.getRowCount();row++) {
					  
					  int index = tabela.convertRowIndexToModel(row);
					  CadastroContrato contrato = modelo_contratos.getValue(index);
					  double quantidade_sacos = 0;
						double quantidade_kg = 0;
					  
					  if(contrato.getMedida().equalsIgnoreCase("SACOS")) {
							quantidade_sacos = contrato.getQuantidade();
							quantidade_kg = contrato.getQuantidade() * 60;

						}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
							quantidade_kg = contrato.getQuantidade();
							quantidade_sacos = quantidade_kg / 60;
						}
						
						quantidade_sacos_total += quantidade_sacos;
						quantidade_kg_total += quantidade_kg;

if(contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				}else if(contrato.getStatus_contrato() == 1) {
					total_contratos_assinar ++;
				}else if(contrato.getStatus_contrato() == 2){
					total_contratos_assinado++;
				}else if(contrato.getStatus_contrato() == 4) {
					total_contratos_concluido++;
				}

		              
		          }
				  lblNumContratos.setText(tabela.getRowCount() + "");
					lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
				
					lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
					lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
					lblTotalContratosConcluidos.setText(total_contratos_assinado+ "");
					lblTotalContratosAssinados.setText(total_contratos_concluido + "");
			}
		});
		btnLimparFiltros.setBounds(851, 136, 89, 23);
		painelPrincipal.add(btnLimparFiltros);

		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigo.setBounds(604, 37, 48, 17);
		painelPrincipal.add(lblCdigo);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStatus.setBounds(604, 89, 48, 17);
		painelPrincipal.add(lblStatus);

		entCodigo = new JTextField();
		entCodigo.setColumns(10);
		entCodigo.setBounds(662, 28, 164, 33);
		painelPrincipal.add(entCodigo);

		entStatus = new JTextField();
		entStatus.setColumns(10);
		entStatus.setBounds(662, 80, 164, 33);
		painelPrincipal.add(entStatus);

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (flag_retorno == 3 || flag_retorno == 4 ) {
					pesquisar_sub_contratos(id_contrato_pai_para_replica_global);

				} else {
					pesquisar();

				}
			}
		});
		btnRefazerPesquisa.setBounds(894, 60, 126, 28);
		painelPrincipal.add(btnRefazerPesquisa);
		
		JLabel lblTransgnese = new JLabel("Transgênese:");
		lblTransgnese.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTransgnese.setBounds(570, 143, 82, 17);
		painelPrincipal.add(lblTransgnese);
		
		entTransgenia = new JTextField();
		entTransgenia.setColumns(10);
		entTransgenia.setBounds(662, 136, 164, 33);
		painelPrincipal.add(entTransgenia);
		
		JButton btnImportarManualmente = new JButton("Importar Manualmente");
		btnImportarManualmente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo\ndo contrato de terceiro");

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setPreferredSize(new Dimension(800, 600));
				fileChooser.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo PDF", "pdf");

				int result = fileChooser.showOpenDialog(isto);

				File file = fileChooser.getSelectedFile();

				
				
							//abrir tela de adicionar novo contrato
							TelaImportarContratoManual tela = new TelaImportarContratoManual( 4, null, 0, file);
							tela.setVisible(true);
						
			
			}
		});
		btnImportarManualmente.setBounds(488, 539, 152, 28);
		painelPrincipal.add(btnImportarManualmente);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 51));
		panel_1.setBounds(430, 450, 254, 33);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));
		
		JLabel lblNewLabel_1 = new JLabel("Número de Contratos:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_1, "cell 0 0");
		
		
		 lblNumContratos = new JLabel("999");
		lblNumContratos.setForeground(Color.WHITE);
		lblNumContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(lblNumContratos, "flowx,cell 1 0");
		
		JLabel lblNewLabel_3 = new JLabel("Contratos");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_3, "cell 1 0");
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(new Color(102, 51, 0));
		panel_1_1.setBounds(700, 450, 338, 33);
		painelPrincipal.add(panel_1_1);
		panel_1_1.setLayout(new MigLayout("", "[][]", "[]"));
		
		JLabel lblNewLabel_2 = new JLabel("Total Sacos:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel_2.setForeground(Color.WHITE);
		panel_1_1.add(lblNewLabel_2, "cell 0 0");
		
		 lblTotalSacosKGs = new JLabel("99.999,66 SCs / 999999999999 KGs");
		lblTotalSacosKGs.setForeground(Color.WHITE);
		lblTotalSacosKGs.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1_1.add(lblTotalSacosKGs, "cell 1 0");
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(30, 458, 25, 17);
		lblNewLabel_4.setBackground(new Color(255,69,0));
		painelPrincipal.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Contrato em Análise:");
		lblNewLabel_5.setBounds(60, 458, 115, 16);
		painelPrincipal.add(lblNewLabel_5);
		
		JLabel lblNewLabel_4_1 = new JLabel("");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setBackground(Color.yellow);
		lblNewLabel_4_1.setBounds(30, 484, 25, 17);
		painelPrincipal.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Recolher Assinatura:");
		lblNewLabel_5_1.setBounds(60, 484, 115, 16);
		painelPrincipal.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("");
		lblNewLabel_4_2.setOpaque(true);
		lblNewLabel_4_2.setBackground(new Color(95,159,159));
		lblNewLabel_4_2.setBounds(30, 508, 25, 17);
		painelPrincipal.add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_5_2 = new JLabel("Assinado:");
		lblNewLabel_5_2.setBounds(60, 508, 112, 16);
		painelPrincipal.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_4_3 = new JLabel("");
		lblNewLabel_4_3.setOpaque(true);
		lblNewLabel_4_3.setBackground(new Color(0,100,0));
		lblNewLabel_4_3.setBounds(30, 535, 25, 17);
		painelPrincipal.add(lblNewLabel_4_3);
		
		JLabel lblNewLabel_5_3 = new JLabel("Concluido:");
		lblNewLabel_5_3.setBounds(60, 535, 112, 16);
		painelPrincipal.add(lblNewLabel_5_3);
		
		 lblTotalContratosEmAnalise = new JLabel("999");
		lblTotalContratosEmAnalise.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalContratosEmAnalise.setBounds(187, 458, 55, 16);
		painelPrincipal.add(lblTotalContratosEmAnalise);
		
		 lblTotalContratosAssinar = new JLabel("999");
		lblTotalContratosAssinar.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalContratosAssinar.setBounds(187, 484, 55, 16);
		painelPrincipal.add(lblTotalContratosAssinar);
		
		 lblTotalContratosAssinados = new JLabel("999");
		lblTotalContratosAssinados.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalContratosAssinados.setBounds(187, 508, 55, 16);
		painelPrincipal.add(lblTotalContratosAssinados);
		
		 lblTotalContratosConcluidos = new JLabel("999");
		lblTotalContratosConcluidos.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalContratosConcluidos.setBounds(187, 535, 55, 16);
		painelPrincipal.add(lblTotalContratosConcluidos);

		if (flag_retorno == 1 || flag_retorno == 2 || flag_retorno == 3 || flag_retorno == 4) {
			// selecionar contrato para carregamento
			btnAbrir.setEnabled(false);
			btnAbrir.setVisible(false);

		} else {
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);
		}
		
		if (flag_retorno == 3 || flag_retorno == 4) {

		} else
			pesquisar();
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();
		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;

		NumberFormat z = NumberFormat.getNumberInstance();


		for (CadastroContrato contrato : gerenciar.getContratos()) {

			if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3 || contrato.getSub_contrato() == 4 || contrato.getSub_contrato() == 5) {

				modelo_contratos.onAdd(contrato);
				double quantidade_sacos = 0;
				double quantidade_kg = 0;
				
				if(contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = contrato.getQuantidade() * 60;

				}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}
				
				if(contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				}else if(contrato.getStatus_contrato() == 1) {
					total_contratos_assinar ++;
				}else if(contrato.getStatus_contrato() == 2){
					total_contratos_assinado++;
				}else if(contrato.getStatus_contrato() == 4) {
					total_contratos_concluido++;
				}
				quantidade_sacos_total += quantidade_sacos;
				quantidade_kg_total += quantidade_kg;
				lista_contratos.add(contrato);
			}

		}
		
		lblNumContratos.setText(lista_contratos.size() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
		lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
		lblTotalContratosConcluidos.setText(total_contratos_concluido + "");
		lblTotalContratosAssinados.setText(total_contratos_assinado + "");
	}

	public void pesquisar_sub_contratos(int id_contrato_pai) {
		
		id_contrato_pai_para_replica_global = id_contrato_pai;
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();
		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		NumberFormat z = NumberFormat.getNumberInstance();

		ArrayList<CadastroContrato> sub_contratos = gerenciar.getInfoSubContratos(id_contrato_pai);

		for (CadastroContrato contrato : sub_contratos) {

			if (contrato.getSub_contrato() == 1) {

				modelo_contratos.onAdd(contrato);
				double quantidade_sacos = 0;
				double quantidade_kg = 0;
				
				if(contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = contrato.getQuantidade() * 60;

				}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}
				
				if(contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				}else if(contrato.getStatus_contrato() == 1) {
					total_contratos_assinar ++;
				}else if(contrato.getStatus_contrato() == 2){
					total_contratos_assinado++;
				}else if(contrato.getStatus_contrato() == 4) {
					total_contratos_concluido++;
				}
				quantidade_sacos_total += quantidade_sacos;
				quantidade_kg_total += quantidade_kg;
				lista_contratos.add(contrato);
			}

		}

		
		
		lblNumContratos.setText(lista_contratos.size() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
		lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
		lblTotalContratosConcluidos.setText(total_contratos_concluido  + "");
		lblTotalContratosAssinados.setText(total_contratos_assinado + "");
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo PDF", "pdf");

		int result = fileChooser.showOpenDialog(isto);

		File[] files = fileChooser.getSelectedFiles();

		for (File arquivo : files) {
			ManipularArquivoTerceiros manipular = new ManipularArquivoTerceiros();
			CadastroContrato contrato_importar = manipular.filtrar(arquivo);

		}
	}

	public void filtrar() {
		NumberFormat z = NumberFormat.getNumberInstance();

		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = entStatus.getText().toUpperCase();
		String transgenese = entTransgenia.getText().toUpperCase();

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 1));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 2));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 3));

		if (checkString(status))
			filters.add(RowFilter.regexFilter(status, 4));

		if (checkString(produto))
			filters.add(RowFilter.regexFilter(produto, 7));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 8));
		

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 8));

		sorter.setRowFilter(RowFilter.andFilter(filters));
		
		
		  for(int row = 0;row < tabela.getRowCount();row++) {
			  
			  int index = tabela.convertRowIndexToModel(row);
			  CadastroContrato contrato = modelo_contratos.getValue(index);
			  double quantidade_sacos = 0;
				double quantidade_kg = 0;
			  
			  if(contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = contrato.getQuantidade() * 60;

				}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}
				
			  if(contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				}else if(contrato.getStatus_contrato() == 1) {
					total_contratos_assinar ++;
				}else if(contrato.getStatus_contrato() == 2){
					total_contratos_assinado++;
				}else if(contrato.getStatus_contrato() == 4) {
					total_contratos_concluido++;
				}
			  
				quantidade_sacos_total += quantidade_sacos;
				quantidade_kg_total += quantidade_kg;


              
          }
		  lblNumContratos.setText(tabela.getRowCount() + "");
			lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
			lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
			lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
			lblTotalContratosConcluidos.setText(total_contratos_concluido + "");
			lblTotalContratosAssinados.setText(total_contratos_assinado + "");
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	
	
	class EvenOddRenderer implements TableCellRenderer {

		  public  final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
		        table, value, isSelected, hasFocus, row, column);
		    ((JLabel) renderer).setOpaque(true);
		   
		      String dados = (String) table.getValueAt(row, 4);
		   
		     if(isSelected) {
				    renderer.setBackground(new Color  (139,69,19)); //marrom
				  

		     }else {
		     if(dados.equalsIgnoreCase("RECOLHER ASSINATURAS")) {
				    renderer.setBackground(Color.yellow);

		     }else if(dados.equalsIgnoreCase("Em Aprovação")){
				    renderer.setBackground(new Color(255,69,0)); //laranja
				    
		     }
		     else if(dados.equalsIgnoreCase("Assinado")){
				    renderer.setBackground(new Color(95,159,159)); //verde


		     } else if (dados.equalsIgnoreCase("Cumprindo")) {
				    renderer.setBackground(new Color(0,100,0)); //verde


				} 

		     }
		    
		  
		    return renderer;
		  }
		}
	
	public class ContratoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int codigo = 1;
		private final int compradores = 2;
		private final int vendedores = 3;
		private final int status = 4;
		private final int quantidade = 5;
		private final int medida = 6;
		private final int produto = 7;
		private final int transgenia = 8;
		private final int safra = 9;
		private final int valor_produto = 10;
		private final int valor_total = 11;
		private final int corretores = 12;
		private final int data_contrato = 13;

		List<Color> rowColours = Arrays.asList(
		        Color.RED,
		        Color.GREEN,
		        Color.CYAN
		    );

		
		private final String colunas[] = { "ID", "Código", "Compradores:", "Vendedores:", "Status:", "Quantidade:",
				"Medida:", "Produto:", "Transgênese", "Safra:", "Valor Produto:", "Valor Total:", "Corretores:", "Data Contrato" };
		private final ArrayList<CadastroContrato> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ContratoTableModel() {

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
			case codigo:
				return String.class;
			case compradores:
				return String.class;
			case vendedores:
				return String.class;
			case status:
				return String.class;
			case quantidade:
				return String.class;
			case medida:
				return String.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case safra:
				return String.class;
			case valor_produto:
				return String.class;
			case valor_total:
				return String.class;
			case corretores:
				return String.class;
			case data_contrato:
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
			Locale ptBr = new Locale("pt", "BR");
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroContrato contrato = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return contrato.getId();
			case codigo:
				return contrato.getCodigo();
			case compradores: {

				return contrato.getNomes_compradores().toUpperCase();

			}
			case vendedores: {
				return contrato.getNomes_vendedores().toUpperCase();
			}
			case status: {
				int status = contrato.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					return "Recolher Assinaturas".toUpperCase();

				} else if (status == 2) {
					return "Assinado".toUpperCase();

				} else if (status == 3) {
					return "Cumprindo".toUpperCase();

				}else if(status == 0) {
					return "Em Aprovação".toUpperCase();

				}
			}
			case quantidade: {
				/*double quantidade_sacos = 0;
				double quantidade_kg = 0;
				
				if(contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
				}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}*/
				String t = z.format(contrato.getQuantidade());
				return t;
			}
			case medida:
				return contrato.getMedida();
			case produto:
				return contrato.getProduto().toUpperCase();
			case transgenia:{
				return contrato.getModelo_safra().getProduto().getTransgenia().toUpperCase();
			}
			case safra:
				return contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();
			case valor_produto: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto());
				return valorString;

			}
			case valor_total: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar());
				return valorString;

			}
			case corretores: {
				String corretores = "";
				if (contrato.getNomes_corretores() != null) {
					corretores = contrato.getNomes_corretores().toUpperCase();
				}
				return corretores;
			}
			case data_contrato:
				return contrato.getData_contrato();
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
			CadastroContrato contrato = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato contrato) {
			return dados.indexOf(contrato);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroContrato contrato) {
			dados.add(contrato);
			fireTableRowsInserted(indexOf(contrato), indexOf(contrato));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato> dadosIn) {
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
		public void onRemove(CadastroContrato contrato) {
			int indexBefore = indexOf(contrato);// pega o indice antes de apagar
			dados.remove(contrato);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroContrato onGet(int row) {
			return dados.get(row);
		}
	}
}
