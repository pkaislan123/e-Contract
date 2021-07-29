
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

public class TelaReplicarRecebimento extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private CadastroContrato contrato_pai_local;
	private CadastroContrato sub_contrato;
	private CadastroContrato.Recebimento recebimento_local;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private JComboBox cBSubContratoSelecionado;
	private TelaReplicarRecebimento isto;

	public TelaReplicarRecebimento(CadastroContrato contrato_pai, CadastroContrato.Recebimento recebimento,
			Window janela_pai) {

		this.contrato_pai_local = contrato_pai;
		this.recebimento_local = recebimento;

		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Replicar Recebimento ");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 190);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[232px][90px]", "[][17px][32px][28px][]"));

		JLabel lblNewLabel = new JLabel("Selecione o sub-contrato ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		cBSubContratoSelecionado = new JComboBox();
		cBSubContratoSelecionado.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(cBSubContratoSelecionado, "cell 0 2,growx,aligny bottom");

		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.setBackground(new Color(0, 0, 102));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaContratos tela = new TelaContratos(7, isto);
				tela.pesquisar_sub_contratos(contrato_pai_local.getId());
				tela.setVisible(true);

			}
		});
		painelPrincipal.add(btnNewButton_1, "cell 1 2,growx,aligny top");
		JButton btnNewButton = new JButton("Concluir");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir();
			}
		});

		painelPrincipal.add(btnNewButton, "cell 1 4,growx,aligny top");

		this.setLocationRelativeTo(janela_pai);

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
	
	
	
	public CadastroContrato.Recebimento tratarRecebimentoReplicar(CadastroContrato.Recebimento carga) {

		if(carga.getNf_venda_aplicavel() == 0) {
		carga.setNf_venda_aplicavel(0);
		carga.setPeso_nf_venda(0.0);
		carga.setCaminho_nf_venda("");
		carga.setValor_nf_venda(BigDecimal.ZERO);
		carga.setNome_destinatario_nf_venda("");
		carga.setNome_remetente_nf_venda("");
		
		}
		
		
		if(carga.getNf_remessa_aplicavel() == 0) {
			carga.setNf_remessa_aplicavel(0);
			carga.setPeso_nf_remessa(0.0);
			carga.setCaminho_nf_remessa("");
			carga.setValor_nf_remessa(BigDecimal.ZERO);
			carga.setNome_remetente_nf_remessa("");
			carga.setNome_destinatario_nf_remessa("");
			
			}
		
	
		
		return carga;
		
	}

	public void concluir() {

		GerenciarBancoContratos gerenciar2 = new GerenciarBancoContratos();

		recebimento_local.setId_contrato_recebimento(sub_contrato.getId());

		int retorno = gerenciar2.inserirRecebimento(sub_contrato.getId(),tratarRecebimentoReplicar( recebimento_local));
		if (retorno > 0) {
			JOptionPane.showMessageDialog(isto, "Recebimento Replicado!");
			// gerar pastas e arquivos
			recebimento_local.setId_recebimento(retorno);
			isto.dispose();

			// gerarPastasEArquivos();

		} else {
			JOptionPane.showMessageDialog(isto,
					"Erro ao Replicar o Recebimento\nNão há erros no banco de dados\nTente Novamente!");
			isto.dispose();
		}

	}

	public void getDadosGlobais() {
		// gerenciador de logasd

		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();
		servidor_unidade = configs_globais.getServidorUnidade();

	}

}
