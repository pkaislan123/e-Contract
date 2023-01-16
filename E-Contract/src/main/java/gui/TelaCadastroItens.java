package main.java.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroItem;
import main.java.conexaoBanco.GerenciarBancoItens;

import javax.swing.JOptionPane;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class TelaCadastroItens extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entNomeItem;
	private JTextField textCodigo;
	private JTextField entCodigoTipoItem;

	public TelaCadastroItens(int flag_tipo_tela, CadastroItem itemEdicao, Window janela_pai) {
		TelaCadastroItens isto = this;

		setResizable(false);
		setTitle("E-Contract - Cadastro Produto");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 471, 315);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[141px][grow]", "[36px][41px][30px][100px:100px:100px][][]"));

		JLabel lblCodigoParaContratos = new JLabel("       Codigo:");
		lblCodigoParaContratos.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblCodigoParaContratos, "cell 0 0,alignx trailing");

		textCodigo = new JTextField();
		textCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		textCodigo.setEditable(false);
		textCodigo.setEnabled(false);
		textCodigo.setColumns(10);
		painelPrincipal.add(textCodigo, "cell 1 0,growx");

		JLabel lblNomeProduto = new JLabel("Nome Item:");
		lblNomeProduto.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblNomeProduto, "cell 0 1,alignx right,growy");

		entNomeItem = new JTextField();
		painelPrincipal.add(entNomeItem, "cell 1 1,growx,aligny center");
		entNomeItem.setColumns(10);

		entCodigoTipoItem = new JTextField();
		entCodigoTipoItem.setToolTipText("");
		entCodigoTipoItem.setHorizontalAlignment(SwingConstants.RIGHT);
		entCodigoTipoItem.setColumns(10);
		painelPrincipal.add(entCodigoTipoItem, "flowx,cell 1 2,growx,aligny center");

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		painelPrincipal.add(lblDescrio, "cell 0 3,alignx right,aligny top");

		JTextArea entDescricao = new JTextArea();
		entDescricao.setLineWrap(true);
		entDescricao.setWrapStyleWord(true);
		entDescricao.setBackground(SystemColor.inactiveCaptionBorder);
		painelPrincipal.add(entDescricao, "cell 1 3,grow");

		if (flag_tipo_tela == 0) {
			textCodigo.setText(String.format("%010d", itemEdicao.getId_item()));
			entNomeItem.setText(itemEdicao.getNome());
			entDescricao.setText(itemEdicao.getDescricao());
			entCodigoTipoItem.setText(String.format("%010d", itemEdicao.getTipo().getId_tipo_item()));
		}

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(0, 0, 102));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoItens gerenciar = new GerenciarBancoItens();
				CadastroItem item = new CadastroItem();

				String nome, descricao;
				int id_tem, id_tipoItem;

				nome = entNomeItem.getText();
				descricao = entDescricao.getText();
				id_tipoItem = (entCodigoTipoItem.getText().isEmpty() ? 0 : Integer.parseInt(entCodigoTipoItem.getText().replaceAll("[\\D]","0")));

				if (!nome.isEmpty()) {
					item.setNome(nome);
					item.setDescricao(descricao);
					item.getTipo().setId_tipo_item(id_tipoItem);
					try {
						if (flag_tipo_tela == 0) {
							item.setId_item(itemEdicao.getId_item());
							if (gerenciar.atualizarItem(item)) {
								JOptionPane.showMessageDialog(isto, "Cadastro atualizado com sucesso!",
										"Item Código: " + String.format("%010d", item.getId_item()),
										JOptionPane.INFORMATION_MESSAGE);
								((TelaFazendaItens) janela_pai).pesquisar();
								isto.dispose();
							}

						} else {
							if ((id_tem = gerenciar.inserirItem(item)) > 0) {
								JOptionPane.showMessageDialog(isto, "Cadastrado realizado com sucesso!",
										"Item Código: " + String.format("%010d", id_tem),
										JOptionPane.INFORMATION_MESSAGE);
								((TelaFazendaItens) janela_pai).pesquisar();
								isto.dispose();
							}
						}

					} catch (Exception t) {
						JOptionPane.showMessageDialog(isto, "Falha ao salvar o item!");
					}
				} else {
					JOptionPane.showMessageDialog(isto, "O campo 'Nome' é de preencimento obrigatório!");
				}
			}

		});
		painelPrincipal.add(btnSalvar, "cell 1 5,alignx right,growy");

		JLabel lblTransgenia = new JLabel("Tipo:");
		lblTransgenia.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		painelPrincipal.add(lblTransgenia, "cell 0 2,alignx trailing,aligny top");

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPesquisar.setForeground(Color.WHITE);
		btnPesquisar.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnPesquisar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnPesquisar, "cell 1 2,alignx center,aligny center");

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);

	}
}
