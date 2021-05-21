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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class TelaReplicarPagamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private CadastroContrato contrato_pai_local;
	private CadastroContrato sub_contrato;
	private CadastroContrato.CadastroPagamentoContratual pagamento_local;
	private JDialog telaPai;
	private JComboBox cBSubContratoSelecionado;
	private TelaReplicarPagamento isto;
    private JFrame telaPaiJFrame;
    
	public TelaReplicarPagamento(CadastroContrato contrato_pai,
			CadastroContrato.CadastroPagamentoContratual pagamento, Window janela_pai) {

		this.contrato_pai_local = contrato_pai;
		this.pagamento_local = pagamento;

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Replicar ");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 190);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selecione o sub-contrato ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 22, 156, 17);
		painelPrincipal.add(lblNewLabel);

		JButton btnNewButton = new JButton("Concluir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir();
			}
		});
		btnNewButton.setBounds(270, 117, 74, 28);
		painelPrincipal.add(btnNewButton);

		cBSubContratoSelecionado = new JComboBox();
		cBSubContratoSelecionado.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cBSubContratoSelecionado.setBounds(26, 68, 216, 31);
		painelPrincipal.add(cBSubContratoSelecionado);

		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContratos tela = new TelaContratos(4,isto);

				tela.setTelaPai(isto);
				tela.pesquisar_sub_contratos(contrato_pai_local.getId());
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(254, 67, 90, 28);
		painelPrincipal.add(btnNewButton_1);

		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
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

	public void concluir() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		// verificar se esse pagamento tem comprovantes anexados
		GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> documentos_anexados = gerenciar_docs
				.getDocumentosPorPai(pagamento_local.getId_pagamento());
		if(documentos_anexados.size() > 0) {
		for (CadastroDocumento doc : documentos_anexados) {

			CopiarArquivo copiar = new CopiarArquivo(doc, pagamento_local.getId_pagamento(), sub_contrato);

			String nome_arquivo = copiar.copiar_pagamento();

			if (nome_arquivo != null) {
				doc.setNome_arquivo(nome_arquivo);
				doc.setNome("comprovante de pagamento replicado");
				doc.setDescricao("comprovante de pagamento replicado do contrato pai deste sub_contrato");
				doc.setId_contrato_pai(sub_contrato.getId());

				boolean retorno = gerenciar.inserir_contrato_pagamento_contratual(sub_contrato.getId(),
						pagamento_local.getId_pagamento());
				if (retorno) {
					JOptionPane.showMessageDialog(isto, "Pagamento Replicado!");

					int anexo_replicado = gerenciar_docs.inserir_documento_padrao(doc);
					if (anexo_replicado > 1) {
						JOptionPane.showMessageDialog(isto, "Comprovante deste pagamento também foi replicado");

					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao replicar anexo!\nConsulte o administrador!");

					}

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao Replicar o Pagamento\nNão ha erros no banco de dados\nTente Novamente!");
					isto.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(isto,
						"O arquivo fisico não foi copiado! Replica cancelada!\nTente Novamente, se o erro persistir, consulte o administrador");

			}

		}
		}else {
			boolean retorno = gerenciar.inserir_contrato_pagamento_contratual(sub_contrato.getId(),
					pagamento_local.getId_pagamento());
			if (retorno) {
				JOptionPane.showMessageDialog(isto, "Pagamento Replicado!");

				
			} else {
				JOptionPane.showMessageDialog(isto,
						"Erro ao Replicar o Pagamento\nNão ha erros no banco de dados\nTente Novamente!");
				isto.dispose();
			}
		}

		isto.dispose();

	}
	

public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}	
       
}
