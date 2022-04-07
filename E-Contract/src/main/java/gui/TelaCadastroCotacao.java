package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroCotacao;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoCotacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroCotacao extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();

	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
	private JComboBox cBProduto, cBAnoPlantio;
	private TelaCadastroCotacao isto;
	private JTextField entUnidade;
	private JTextField entQuantidade;
	private JTextField entValor;
	private JTextField entLocalidade;
	private JTextField entIndicador;
	private JTextField entMedida;

	public TelaCadastroCotacao(int flag_edicao, CadastroCotacao cotacao_antiga, Window janela_pai) {
		// setAlwaysOnTop(true);

		// setModal(true);

		isto = this;

		setResizable(false);
		if (flag_edicao == 0)
			setTitle("E-Contract - Nova Cotação");
		else {
			setTitle("E-Contract - Editar Cotação");

		}

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 386);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[128px][305px,grow][89px]", "[][][33px][][][][][33px][23px][]"));

		JLabel lblMedida = new JLabel("Medida:");
		lblMedida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblMedida, "cell 0 0,alignx trailing");

		entMedida = new JTextField();
		entMedida.setFont(new Font("SansSerif", Font.BOLD, 16));
		entMedida.setText("Saco");
		entMedida.setColumns(10);
		painelPrincipal.add(entMedida, "cell 1 0,growx");

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblQuantidade, "cell 0 1,alignx trailing");

		entQuantidade = new JTextField();
		entQuantidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		entQuantidade.setText("60");
		entQuantidade.setColumns(10);
		painelPrincipal.add(entQuantidade, "cell 1 1,growx");

		JLabel lblAnoPlantio = new JLabel("Unidade:");
		lblAnoPlantio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblAnoPlantio, "cell 0 2 1 2,alignx trailing,growy");

		entUnidade = new JTextField();
		entUnidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		entUnidade.setText("Quilogramas");
		painelPrincipal.add(entUnidade, "cell 1 2 1 2,growx");
		entUnidade.setColumns(10);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblValor, "cell 0 4,alignx trailing");

		entValor = new JTextField();
		entValor.setFont(new Font("SansSerif", Font.BOLD, 16));
		entValor.setColumns(10);
		painelPrincipal.add(entValor, "cell 1 4,growx");

		JLabel lblLocalidade = new JLabel("Localidade:");
		lblLocalidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblLocalidade, "cell 0 5,alignx trailing");

		entLocalidade = new JTextField();
		entLocalidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		entLocalidade.setColumns(10);
		painelPrincipal.add(entLocalidade, "cell 1 5,growx");

		JLabel lblIndicador = new JLabel("Indicador:");
		lblIndicador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblIndicador, "cell 0 6,alignx trailing");

		entIndicador = new JTextField();
		entIndicador.setFont(new Font("SansSerif", Font.BOLD, 16));
		entIndicador.setColumns(10);
		painelPrincipal.add(entIndicador, "cell 1 6,growx");

		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblProduto, "cell 0 7,alignx right,growy");

		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();
		cBProduto = new JComboBox();
		cBProduto.setFont(new Font("SansSerif", Font.BOLD, 16));
		cBProduto.setModel(modelProduto);
		cBProduto.setRenderer(cBProdutoPersonalizado);
		painelPrincipal.add(cBProduto, "cell 1 7,grow");

		cBProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();

			}

		});

		GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();

		for (CadastroProduto produto : produtos) {
			modelProduto.addProduto(produto);

		}

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CadastroCotacao cotacao = getDadosSalvar();
				if (cotacao != null) {
					GerenciarBancoCotacao gerenciar = new GerenciarBancoCotacao();
					boolean cadastrado = gerenciar.inserir_cotacao(cotacao);

					if (cadastrado) {
						JOptionPane.showMessageDialog(isto, "Cotação Cadastrada");
						((TelaCotacoes) janela_pai).pesquisar_cotacoes();
						isto.dispose();
					}
				}

			}
		});

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CadastroCotacao cotacao_atualizada = getDadosAtualizar(cotacao_antiga);
				GerenciarBancoCotacao gerenciar = new GerenciarBancoCotacao();

				if (cotacao_atualizada != null) {
					boolean atualizado = gerenciar.atualizar_cotacao(cotacao_atualizada);

					if (atualizado) {
						JOptionPane.showMessageDialog(isto, "Cotação Atualizada");
						((TelaCotacoes) janela_pai).pesquisar_cotacoes();
						isto.dispose();
					}
				}

			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnAtualizar, "cell 1 9,alignx right,aligny bottom");
		painelPrincipal.add(btnSalvar, "cell 2 9,grow");

		CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();

		this.setLocationRelativeTo(janela_pai);

		if (flag_edicao == 1) {
			rotinasEdicao(cotacao_antiga);

			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
		} else {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}

		this.setVisible(true);

	}

	public void rotinasEdicao(CadastroCotacao cotacao_antiga) {

		entLocalidade.setText(cotacao_antiga.getLocalidade());
		entUnidade.setText(cotacao_antiga.getUnidade());
		entIndicador.setText(cotacao_antiga.getIndicador());
		entMedida.setText(cotacao_antiga.getMedida());

		entValor.setText(cotacao_antiga.getValor() + "");

		entQuantidade.setText(cotacao_antiga.getQuantidade() + "");

		modelProduto.setSelectedItem(cotacao_antiga.getProduto());
	}

	public CadastroCotacao getDadosSalvar() {
		CadastroCotacao nova_cotacao = new CadastroCotacao();

		String localidade, unidade, indicador, medida;

		localidade = entLocalidade.getText();
		unidade = entUnidade.getText();
		indicador = entIndicador.getText();
		medida = entMedida.getText();

		nova_cotacao.setLocalidade(localidade);
		nova_cotacao.setUnidade(unidade);
		nova_cotacao.setIndicador(indicador);
		nova_cotacao.setMedida(medida);

		double valor, quantidade;

		try {

			valor = Double.parseDouble(entValor.getText());

			nova_cotacao.setValor(valor);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Incorreto");
			return null;
		}

		try {

			quantidade = Double.parseDouble(entQuantidade.getText());

			nova_cotacao.setQuantidade(quantidade);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Quantidade Incorreta");
			return null;
		}

		CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();

		nova_cotacao.setProduto(produto);

		return nova_cotacao;
	}

	public CadastroCotacao getDadosAtualizar(CadastroCotacao cotacao_antiga) {
		CadastroCotacao nova_cotacao = new CadastroCotacao();

		nova_cotacao.setId_cotacao(cotacao_antiga.getId_cotacao());

		String localidade, unidade, indicador, medida;

		localidade = entLocalidade.getText();
		unidade = entUnidade.getText();
		indicador = entIndicador.getText();
		medida = entMedida.getText();

		nova_cotacao.setLocalidade(localidade);
		nova_cotacao.setUnidade(unidade);
		nova_cotacao.setIndicador(indicador);
		nova_cotacao.setMedida(medida);

		double valor, quantidade;

		try {

			valor = Double.parseDouble(entValor.getText());

			nova_cotacao.setValor(valor);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Incorreto");
			return null;
		}

		try {

			quantidade = Double.parseDouble(entQuantidade.getText());

			nova_cotacao.setQuantidade(quantidade);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Quantidade Incorreta");
			return null;
		}

		CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();

		nova_cotacao.setProduto(produto);

		return nova_cotacao;
	}

}
