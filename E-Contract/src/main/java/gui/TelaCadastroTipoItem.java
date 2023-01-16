package main.java.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import main.java.cadastros.CadastroItem;
import main.java.conexaoBanco.GerenciarBancoTipoItem;

import javax.swing.JOptionPane;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class TelaCadastroTipoItem extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entNomeTipoItem;
	private JTextArea entDescricao;
	private JTextField entCodigo;

	public TelaCadastroTipoItem(int flag_tipo_tela, CadastroItem.Tipo tipo_item, Window janela_pai) {

		TelaCadastroTipoItem isto = this;

		setResizable(false);

		setTitle((flag_tipo_tela == 1) ? "E-Contract - Cadastro Tipo Item" : "E-Contract - Editar Cadastro Tipo Item");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 471, 315);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal
				.setLayout(new MigLayout("", "[141px][][grow]", "[36px][][][41px][30px][100px:100px:100px][][]"));

		JLabel lblCodigoTipoItem = new JLabel("       Código:");
		lblCodigoTipoItem.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblCodigoTipoItem, "cell 0 0,alignx right,growy");

		entCodigo = new JTextField();
		entCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		entCodigo.setEnabled(false);
		entCodigo.setEditable(false);
		entCodigo.setColumns(10);
		painelPrincipal.add(entCodigo, "cell 2 0,grow");

		JLabel lblNomeTipoItem = new JLabel("Nome Tipo Item:");
		lblNomeTipoItem.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblNomeTipoItem, "cell 0 1,alignx left,growy");

		entNomeTipoItem = new JTextField();
		painelPrincipal.add(entNomeTipoItem, "cell 2 1,growx,aligny top");
		entNomeTipoItem.setColumns(10);

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		painelPrincipal.add(lblDescrio, "cell 0 3,alignx right,aligny top");

		entDescricao = new JTextArea();
		entDescricao.setLineWrap(true);
		entDescricao.setWrapStyleWord(true);
		entDescricao.setBackground(SystemColor.inactiveCaptionBorder);
		painelPrincipal.add(entDescricao, "cell 2 3 1 3,grow");

		if (flag_tipo_tela == 0) {
			entCodigo.setText(String.format("%010d", tipo_item.getId_tipo_item()));
			entNomeTipoItem.setText(tipo_item.getNome());
			entDescricao.setText(tipo_item.getDescricao());
		}

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(0, 0, 102));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoTipoItem gerenciar = new GerenciarBancoTipoItem();
				CadastroItem.Tipo tipoItem = new CadastroItem.Tipo();

				String nome, descricao;
				int codigo;
				nome = entNomeTipoItem.getText();
				descricao = entDescricao.getText();

				if (!nome.isEmpty()) {

					try {
						tipoItem.setNome(nome);
						tipoItem.setDescricao(descricao);
						if (flag_tipo_tela == 0) {
							tipoItem.setId_tipo_item(tipo_item.getId_tipo_item());
							if (gerenciar.atualizarTipoItens(tipoItem)) {
								JOptionPane.showMessageDialog(isto, "Cadastro atualizado com sucesso!",
										"Tipo Item Código: " + String.format("%010d", tipoItem.getId_tipo_item()),
										JOptionPane.INFORMATION_MESSAGE);
								 ((TelaFazendaTipoItens) janela_pai).pesquisar();
								isto.dispose();
							}
						} else {
							if ((codigo = gerenciar.inserirTipoItem(tipoItem)) > 0) {
								JOptionPane.showMessageDialog(isto, "Cadastrado realizado com sucesso!",
										"Tipo Item Código: " + String.format("%010d", codigo),
										JOptionPane.INFORMATION_MESSAGE);
								((TelaFazendaTipoItens) janela_pai).pesquisar();
								isto.dispose();
							} else {
								JOptionPane.showMessageDialog(isto, "Falha ao salvar o tipo de item!");
							}
						}

					} catch (Exception t) {
						JOptionPane.showMessageDialog(isto, "O campo 'Nome' é de preencimento obrigatório!");

					}

				} else {
					JOptionPane.showMessageDialog(isto, "Código Invalido!");

				}

			}
		});
		painelPrincipal.add(btnSalvar, "cell 2 7,alignx right,growy");

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);

	}
}
