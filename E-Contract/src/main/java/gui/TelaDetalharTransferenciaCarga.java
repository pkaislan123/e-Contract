package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
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
import main.java.conexaoBanco.GerenciarBancoAditivos;
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
import main.java.manipular.EditarAditivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoNotas;
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
import javax.swing.JScrollPane;



public class TelaDetalharTransferenciaCarga extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaDetalharTransferenciaCarga isto;
    private JDialog telaPai;

	public TelaDetalharTransferenciaCarga(int id_transferencia, String descricao, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Detalhes da transferencia");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 834, 338);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
	
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		painelPrincipal.add(scrollPane, "cell 0 1,grow");
		
		String texto_detalhado = "";
		
		GerenciarBancoTransferenciasCarga gerenciar = new GerenciarBancoTransferenciasCarga();
			CadastroContrato.CadastroTransferenciaCarga transferencia = gerenciar.getTransferencia(id_transferencia);
			
	    GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
	    CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
	    CadastroContrato destinatario = gerencia_contratos.getContrato(transferencia.getId_contrato_destinatario());
	    CadastroContrato.Carregamento carga = gerencia_contratos.getCarregamento(transferencia.getId_carregamento_remetente());

		CadastroCliente compradores[] = destinatario.getCompradores();
		CadastroCliente vendedores[] = destinatario.getVendedores();
		CadastroCliente corretores[] = destinatario.getCorretores();

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

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}


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
		
	    double quantidade = Double.parseDouble(transferencia.getQuantidade());
	    
		NumberFormat z = NumberFormat.getNumberInstance();

		if(descricao.equalsIgnoreCase("-Transferencia")) {
			texto_detalhado = "Transferência de volume deste contrato para o contrato :\n\n"
					+ destinatario.getCodigo() + "\n"
					+ nome_compradores + " X " + nome_vendedores + "\n"
					+ z.format(destinatario.getQuantidade()) + " " + destinatario.getMedida() + " de " + destinatario.getModelo_safra().getProduto().getNome_produto() +
					" " + destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra " + destinatario.getModelo_safra().getAno_plantio() + "/" + destinatario.getModelo_safra().getAno_colheita() + "\n\n";
			texto_detalhado  = texto_detalhado + "";
			          

				
		}
		
		textArea.setText(texto_detalhado);

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
}
