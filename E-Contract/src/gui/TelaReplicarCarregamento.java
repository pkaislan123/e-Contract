package gui;

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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroDocumento;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoDocumento;
import manipular.CopiarArquivo;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class TelaReplicarCarregamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private CadastroContrato contrato_pai_local;
	private CadastroContrato sub_contrato;
	private CadastroContrato.Carregamento carregamento_local;
	private JDialog telaPai;
	private JComboBox cBSubContratoSelecionado;
	private TelaReplicarCarregamento isto;
	   private JFrame telaPaiJFrame;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	public TelaReplicarCarregamento(CadastroContrato contrato_pai, CadastroContrato.Carregamento carregamento) {
		setModal(true);

		this.contrato_pai_local = contrato_pai;
		this.carregamento_local = carregamento;

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
		modelo.addColumn("Id");
		modelo.addColumn("Codigo");
		modelo.addColumn("Compradores");
		modelo.addColumn("Vendedores");
		modelo.addColumn("Produto");
		modelo.addColumn("Safra");
		modelo.addColumn("Quantidade");
		modelo.addColumn("Safra");
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
				TelaContratos tela = new TelaContratos(3, isto);

				tela.setTelaPai(isto);
				tela.pesquisar_sub_contratos(contrato_pai_local.getId());
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(254, 67, 90, 28);
		painelPrincipal.add(btnNewButton_1);

		this.setLocationRelativeTo(null);

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
		String url = carregamento_local.getCaminho_nota_fiscal();

		if (telaPaiJFrame instanceof TelaGerenciarContrato) {
			String sub_url = url.substring(0, url.length() - 1);
			String conteudo[] = sub_url.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}

			carregamento_local.setCaminho_nota_fiscal(url_final);
		}

		carregamento_local.setId_contrato(sub_contrato.getId());

		// verificar se esse carregamento tem comprovantes anexados
		GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> documentos_anexados = gerenciar_docs
				.getDocumentosPorPai(carregamento_local.getId_carregamento());
		for (CadastroDocumento doc : documentos_anexados) {

			CopiarArquivo copiar = new CopiarArquivo(doc, carregamento_local.getId_carregamento(), sub_contrato);

			String nome_arquivo = copiar.copiar_carregamento();

			if (nome_arquivo != null) {

				doc.setNome("comprovante de carregamento replicado");
				doc.setDescricao("comprovante de carregamento replicado do contrato pai deste sub_contrato");
				doc.setId_contrato_pai(sub_contrato.getId());
				doc.setNome_arquivo(nome_arquivo);

				int anexo_replicado = gerenciar_docs.inserir_documento_padrao(doc);
				if (anexo_replicado > 1) {
					JOptionPane.showMessageDialog(null, "Comprovante deste carregamento também foi replicado");

					boolean retorno = gerenciar.inserirCarregamento(sub_contrato.getId(), carregamento_local);
					if (retorno) {
						JOptionPane.showMessageDialog(null, "Carregamento Replicado!");

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao Replicar o Carregamento\nNão ha erros no banco de dados\nTente Novamente!");
						isto.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao replicar anexo!\nConsulte o administrador!");

				}
			} else {
				JOptionPane.showMessageDialog(null,
						"O arquivo fisico não foi copiao! Replica cancelada!\nTente Novamente, se o erro persistir, consulte o administrador");

			}

		}

		isto.dispose();

	}
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}
}
