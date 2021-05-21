package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
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
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
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
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
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

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;



public class TelaConfirmarTransferenciaPagamentoContratual extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private TelaConfirmarTransferenciaPagamentoContratual isto;
    private JDialog telaPai;
    private JTextField entData;
    private JTextField entValor;
    private JTextField entContratoRemetente;
    private JCheckBox chkBoxDataAtual ;
    private CadastroContrato contrato_destinatario;
    private JComboBox cBContratoDestinatario ;
    private JFrame telaPaiJFrame;
    private CadastroCliente depositante, favorecido;
    private JTextArea textAreaDescricaoTransferencia;
    private JComboBox cBDepositante,cBContaDepositante,cBFavorecido, cBContaFavorecido;
	private ContaBancaria conta_favorecido;
	private ContaBancaria conta_depositante;
	public TelaConfirmarTransferenciaPagamentoContratual(int modo_operacao, CadastroContrato.CadastroPagamentoContratual transferencia_antiga, CadastroContrato contrato_remetente, JFrame janela_pai) {
	//	setModal(true);

		 isto = this;
	
		 
		 GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		 
		setResizable(true);
		if(modo_operacao == 0) {
		setTitle("E-Contract - Cadastro de Transferencia de Pagamento");
		}else {
			setTitle("E-Contract - Edição de Transferencia de Pagamento");	
		}
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 654, 537);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][][][][][][][::10px][][][100px:n,grow][][]"));
		
		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 0,alignx trailing");
		
		entData = new JTextField();
		painelPrincipal.add(entData, "flowx,cell 1 0,growx");
		entData.setColumns(10);
		
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);

		
		entData.setText(pegarData());

		 chkBoxDataAtual = new JCheckBox("Hoje");
		chkBoxDataAtual.setSelected(true);
		painelPrincipal.add(chkBoxDataAtual, "cell 1 0");
		
		
		
		 chkBoxDataAtual = new JCheckBox("Data Atual");
		chkBoxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chkBoxDataAtual.isSelected()) {
					chkBoxDataAtual.setSelected(true);
					pegarData();
					entData.setEditable(false);

				}else {
					chkBoxDataAtual.setSelected(false);
					pegarData();
					entData.setEditable(true);
				}
			}
		});
		
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblValor, "cell 0 1,alignx trailing");
		
		entValor = new JTextField();
		entValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		Locale ptBr = new Locale("pt", "BR");
		
		painelPrincipal.add(entValor, "cell 1 1 2 1,growx");
		entValor.setColumns(10);
		
		
		
		JLabel lblContratoRemetente = new JLabel("Contrato Remetente:");
		lblContratoRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblContratoRemetente, "cell 0 2,alignx trailing");
		
		entContratoRemetente = new JTextField();
		entContratoRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		entContratoRemetente.setEditable(false);
		entContratoRemetente.setColumns(10);
		entContratoRemetente.setText(contrato_remetente.getId() + "-" + contrato_remetente.getCodigo());
		painelPrincipal.add(entContratoRemetente, "cell 1 2 2 1,growx");
		
		JLabel lblContratoDestinatario = new JLabel("Contrato Destinatario:");
		lblContratoDestinatario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblContratoDestinatario, "cell 0 3,alignx trailing");
		
		 cBContratoDestinatario = new JComboBox();
		cBContratoDestinatario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(cBContratoDestinatario, "flowx,cell 1 3,growx");
		
		JButton btnSelecionarContratoDestinatario = new JButton("Selecionar Contrato");
		btnSelecionarContratoDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContratos contrato = new TelaContratos(2, isto);
				contrato.setTelaPai(isto);
				contrato.pesquisar_contratos_e_sub_contratos();
				contrato.setVisible(true);
			}
		});
		painelPrincipal.add(btnSelecionarContratoDestinatario, "cell 2 3");
		
		JLabel lblNewLabel_1_1 = new JLabel("New label");
		lblNewLabel_1_1.setVisible(false);
		painelPrincipal.add(lblNewLabel_1_1, "cell 0 4");
		
		JLabel lblDepositante = new JLabel("Depositante:");
		lblDepositante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblDepositante, "cell 0 5,alignx trailing");
		
		
		
		 cBDepositante = new JComboBox();
		painelPrincipal.add(cBDepositante, "cell 1 5,growx");
		
		JButton btnSelecionarDepositante = new JButton("Selecionar Depositante");
		btnSelecionarDepositante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//selecionar depositante
				TelaCliente tela = new TelaCliente(0, 28, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
				
			}
		});
		painelPrincipal.add(btnSelecionarDepositante, "cell 2 5");
		
		JLabel cBContaDepositantehgh = new JLabel("Conta Depositante:");
		cBContaDepositantehgh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(cBContaDepositantehgh, "cell 0 6,alignx trailing");
		
		cBContaDepositante = new JComboBox();
		painelPrincipal.add(cBContaDepositante, "cell 1 6,growx");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setVisible(false);
		painelPrincipal.add(lblNewLabel_1, "cell 0 7");
		
		JLabel lblFavorecido = new JLabel("Favorecido:");
		lblFavorecido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblFavorecido, "cell 0 9,alignx trailing");
		
		 cBFavorecido = new JComboBox();
		painelPrincipal.add(cBFavorecido, "cell 1 9,growx");
		
		JButton btnSelecionarFavorecido = new JButton("Selecionar Favorecido");
		btnSelecionarFavorecido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//selecionar depositante
				TelaCliente tela = new TelaCliente(0, 29, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		painelPrincipal.add(btnSelecionarFavorecido, "cell 2 9,growx");
		
		JLabel lblContaFavorecido = new JLabel("Conta Favorecido:");
		lblContaFavorecido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblContaFavorecido, "cell 0 10,alignx trailing");
		
		 cBContaFavorecido = new JComboBox();
		painelPrincipal.add(cBContaFavorecido, "cell 1 10,growx");
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblDescrio, "cell 0 11,alignx right");
		
		 textAreaDescricaoTransferencia = new JTextArea();
		textAreaDescricaoTransferencia.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaDescricaoTransferencia.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textAreaDescricaoTransferencia.setLineWrap(true);
		textAreaDescricaoTransferencia.setWrapStyleWord(true);
		painelPrincipal.add(textAreaDescricaoTransferencia, "cell 1 11 2 1,grow");
		
		JButton btnTransferir = new JButton("Concluir");
		btnTransferir.setForeground(Color.WHITE);
		btnTransferir.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTransferir.setBackground(new Color(0, 51, 0));
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean transferiou = gerenciar.inserirPagamento(contrato_remetente.getId(), getDadosSalvar(contrato_remetente));
				if(transferiou) {
					//((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();
					JOptionPane.showMessageDialog(isto, "Transferencia Efetuada");
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_pagamentos(true);
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao realizar Transferencia Efetuada\nConsulte o administrador");
					isto.dispose();

				}
				
				
				
			}
		});
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean atualizou = gerenciar.atualizarTransferenciaContratual(contrato_remetente.getId(), getDadosAtualizar(contrato_remetente,transferencia_antiga));
				if(atualizou) {
					//((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();
					JOptionPane.showMessageDialog(isto, "Transferencia Atualizada");
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_pagamentos(true);
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao realizar atualização de Transferencia\nConsulte o administrador");
					isto.dispose();

				}
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAtualizar.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnAtualizar, "cell 1 13,growx");
		painelPrincipal.add(btnTransferir, "cell 2 13,growx");
		
		
		
		if(modo_operacao == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else if(modo_operacao == 1) {
			
			rotinasEdicao(contrato_remetente, transferencia_antiga);
			btnTransferir.setEnabled(false);
			btnTransferir.setVisible(false);
		}
		

			
		
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}

	
	public void setContratoDestintario(CadastroContrato destinatario) {

		this.contrato_destinatario = destinatario;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBContratoDestinatario.removeAllItems();
				cBContratoDestinatario.repaint();
				cBContratoDestinatario.updateUI();
				cBContratoDestinatario.addItem(destinatario.getId() +"-" + destinatario.getCodigo());

				cBContratoDestinatario.repaint();
				cBContratoDestinatario.updateUI();

			}
		});
	}
	
	public void setDepositante(CadastroCliente _depositante) {

		this.depositante = _depositante;

		
				cBDepositante.removeAllItems();
				cBDepositante.repaint();
				cBDepositante.updateUI();

				if (_depositante.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBDepositante.addItem(_depositante.getNome_empresarial());

				} else {
					cBDepositante.addItem(_depositante.getNome_fantaia());

				}

				cBContaDepositante.removeAllItems();
				cBContaDepositante.repaint();
				cBContaDepositante.updateUI();

				GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
				ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(_depositante.getId());

				for (ContaBancaria conta : contas_depositante) {
					cBContaDepositante.addItem(conta.getId_conta() + "-" + conta.getNome());
				}

				cBContaDepositante.repaint();
				cBContaDepositante.updateUI();

	}

	public void setFavorecido(CadastroCliente _favorecido) {

		this.favorecido = _favorecido;

		
				cBFavorecido.removeAllItems();
				cBFavorecido.repaint();
				cBFavorecido.updateUI();

				if (_favorecido.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBFavorecido.addItem(_favorecido.getNome_empresarial());

				} else {
					cBFavorecido.addItem(_favorecido.getNome_fantaia());

				}
				cBFavorecido.repaint();
				cBFavorecido.updateUI();

				cBContaFavorecido.removeAllItems();
				cBContaFavorecido.repaint();
				cBContaFavorecido.updateUI();

				GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
				ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(_favorecido.getId());

				for (ContaBancaria conta : contas_depositante) {
					cBContaFavorecido.addItem(conta.getId_conta() + "-" + conta.getNome());
				}

				cBContaFavorecido.repaint();
				cBContaFavorecido.updateUI();

			
	}

	public CadastroContrato.CadastroPagamentoContratual getDadosSalvar(CadastroContrato contrato_remetente){
		String descricao, data;
		
		descricao = textAreaDescricaoTransferencia.getText();
		data = entData.getText();
		
		CadastroContrato.CadastroPagamentoContratual transferencia = new CadastroContrato.CadastroPagamentoContratual();
		
		transferencia.setTipo(3);
		transferencia.setData_pagamento(data);
		transferencia.setDescricao(descricao);
		transferencia.setValor_pagamento(Double.parseDouble(entValor.getText()));
		

		String s_conta_depositante = cBContaDepositante.getSelectedItem().toString();
		String separados[] = s_conta_depositante.split("-");
		int id_conta_depositante = Integer.parseInt(separados[0]);

		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(depositante.getId());

		for (ContaBancaria conta : contas_depositante) {
			if (conta.getId_conta() == id_conta_depositante) {
				conta_depositante = conta;
			
				break;
			}

		}
		transferencia.setId_depositante(depositante.getId());
		transferencia.setId_conta_depositante(conta_depositante.getId_conta());
		
		String s_conta_favorecido = cBContaFavorecido.getSelectedItem().toString();
		String separados_favorecido[] = s_conta_favorecido.split("-");
		int id_conta_favorecido = Integer.parseInt(separados_favorecido[0]);

		GerenciarBancoClientes gerenciar_contas_favorecidos = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_favorecidos = gerenciar_contas_favorecidos.getContas(favorecido.getId());

		for (ContaBancaria conta : contas_favorecidos) {
			if (conta.getId_conta() == id_conta_favorecido) {
				conta_favorecido = conta;
				
				break;
			}

		}
		transferencia.setId_favorecido(favorecido.getId());
		transferencia.setId_conta_favorecido(conta_favorecido.getId_conta());
		transferencia.setId_contrato_destinatario(contrato_destinatario.getId());
		transferencia.setId_contrato_remetente(contrato_remetente.getId());
			
		return transferencia;
	}
	
	
	
	public CadastroContrato.CadastroPagamentoContratual getDadosAtualizar(CadastroContrato contrato_remetente, CadastroContrato.CadastroPagamentoContratual transferencia_antiga){
		String descricao, data;
		
		descricao = textAreaDescricaoTransferencia.getText();
		data = entData.getText();
		
		CadastroContrato.CadastroPagamentoContratual transferencia = new CadastroContrato.CadastroPagamentoContratual();
		transferencia.setId_pagamento(transferencia_antiga.getId_pagamento());
		
		transferencia.setTipo(3);
		transferencia.setData_pagamento(data);
		transferencia.setDescricao(descricao);
		transferencia.setValor_pagamento(Double.parseDouble(entValor.getText()));
		

		String s_conta_depositante = cBContaDepositante.getSelectedItem().toString();
		String separados[] = s_conta_depositante.split("-");
		int id_conta_depositante = Integer.parseInt(separados[0]);

		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(depositante.getId());

		for (ContaBancaria conta : contas_depositante) {
			if (conta.getId_conta() == id_conta_depositante) {
				conta_depositante = conta;
			
				break;
			}

		}
		transferencia.setId_depositante(depositante.getId());
		transferencia.setId_conta_depositante(conta_depositante.getId_conta());
		
		String s_conta_favorecido = cBContaFavorecido.getSelectedItem().toString();
		String separados_favorecido[] = s_conta_favorecido.split("-");
		int id_conta_favorecido = Integer.parseInt(separados_favorecido[0]);

		GerenciarBancoClientes gerenciar_contas_favorecidos = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_favorecidos = gerenciar_contas_favorecidos.getContas(favorecido.getId());

		for (ContaBancaria conta : contas_favorecidos) {
			if (conta.getId_conta() == id_conta_favorecido) {
				conta_favorecido = conta;
				
				break;
			}

		}
		transferencia.setId_favorecido(favorecido.getId());
		transferencia.setId_conta_favorecido(conta_favorecido.getId_conta());
		transferencia.setId_contrato_destinatario(contrato_destinatario.getId());
		transferencia.setId_contrato_remetente(contrato_remetente.getId());
			
		return transferencia;
	}
	
	
	public void rotinasEdicao(CadastroContrato contrato_remetente, CadastroContrato.CadastroPagamentoContratual transferencia) {
		
		entValor.setText(Double.toString(transferencia.getValor_pagamento()));
		
		setContratoDestintario(new GerenciarBancoContratos().getContrato(transferencia.getId_contrato_destinatario()));

		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		CadastroCliente depositante = gerenciar_clientes.getCliente(transferencia.getId_depositante());
		setDepositante(depositante);
		
		
		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		if(transferencia.getId_conta_depositante() > 0) {
		
		ContaBancaria cb_depositante = gerenciar_contas.getConta(transferencia.getId_conta_depositante());
		if(cb_depositante != null) {

		conta_depositante = cb_depositante;
		for(int i = 0; i < cBContaDepositante.getItemCount(); i++) {
			if(cBContaDepositante.getItemAt(i).toString().contains(cb_depositante.getId_conta()+"")) {
				cBContaDepositante.setSelectedIndex(i);
				break;
			}
		}
		}
		}
		
		
		CadastroCliente favorecido = gerenciar_clientes.getCliente(transferencia.getId_favorecido());
		setFavorecido(favorecido);
		
		if(transferencia.getId_conta_favorecido() > 0) {
			
			ContaBancaria cb_favorecido = gerenciar_contas.getConta(transferencia.getId_conta_favorecido());
			if(cb_favorecido != null) {
			conta_favorecido = cb_favorecido;
			for(int i = 0; i < cBContaFavorecido.getItemCount(); i++) {
				if(cBContaFavorecido.getItemAt(i).toString().contains(cb_favorecido.getId_conta()+"")) {
					cBContaFavorecido.setSelectedIndex(i);
					break;
				}
			}

			}
			
			}

		chkBoxDataAtual.setSelected(false);
		entData.setEnabled(true);
		entData.setEditable(true);
		entData.setText(transferencia.getData_pagamento());
		textAreaDescricaoTransferencia.setText(transferencia.getDescricao());
		
		
	}
	
	public void setTelaPag(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	public void setTelaPag(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}
}
