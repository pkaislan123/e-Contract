package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
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
import outros.JPanelTransparent;

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
import cadastros.CadastroPontuacao;
import cadastros.Registros;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoPontuacao;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;

public class TelaFinalizarContrato extends JDialog {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos, lblNumeroEstrelasVendedor1,
			lblNumeroEstrelasComprador;
	private TelaFinalizarContrato isto;
	private JDialog telaPai;
	private JPanelTransparent painelVendedor2;
	private JLabel lblNomeVendedor2, lblNumeroEstrelasVendedor2;
	private CadastroContrato sub_contrato_global;

	private JLabel lblNomeComprador, lblNomeVendedor1;

	public TelaFinalizarContrato() {
		setModal(true);

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Finalizar Contrato");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 813, 360);
		painelPrincipal.kStartColor = Color.ORANGE;
		painelPrincipal.kEndColor = Color.BLUE;
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JButton btnNewButton = new JButton("Visão Geral");
		btnNewButton.setBounds(401, 318, 111, 28);
		painelPrincipal.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel
				.setIcon(new ImageIcon(TelaFinalizarContrato.class.getResource("/imagens/compromisso_cumprido.png")));
		lblNewLabel.setBounds(27, 80, 215, 233);
		painelPrincipal.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Compradores:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(254, 37, 213, 17);
		painelPrincipal.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Vendedores:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(254, 129, 213, 17);
		painelPrincipal.add(lblNewLabel_1_1);

		lblNomeComprador = new JLabel("Marcos Alexandre de Andrade Carvalho");
		lblNomeComprador.setForeground(Color.WHITE);
		lblNomeComprador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNomeComprador.setBounds(264, 71, 278, 21);
		painelPrincipal.add(lblNomeComprador);

		lblNomeVendedor1 = new JLabel("Marcos Alexandre de Andrade Carvalho");
		lblNomeVendedor1.setForeground(Color.WHITE);
		lblNomeVendedor1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNomeVendedor1.setBounds(264, 157, 278, 21);
		painelPrincipal.add(lblNomeVendedor1);

		JLabel lblrecebera = new JLabel("receberá");
		lblrecebera.setForeground(Color.WHITE);
		lblrecebera.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblrecebera.setBounds(515, 103, 57, 19);
		painelPrincipal.add(lblrecebera);

		JLabel lblrecebera_1 = new JLabel("receberá");
		lblrecebera_1.setForeground(Color.WHITE);
		lblrecebera_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblrecebera_1.setBounds(515, 189, 57, 19);
		painelPrincipal.add(lblrecebera_1);

		painelVendedor2 = new JPanelTransparent();

		painelVendedor2.setTransparencia(1);
		painelVendedor2.setVisible(false);
		painelVendedor2.setBounds(256, 220, 535, 64);
		painelVendedor2.setBackground(new Color(0, 255, 204));
		painelPrincipal.add(painelVendedor2);
		painelVendedor2.repaint();
		painelVendedor2.setLayout(null);

		JLabel lblrecebera_1_1 = new JLabel("receberá");
		lblrecebera_1_1.setForeground(Color.WHITE);
		lblrecebera_1_1.setBounds(257, 37, 57, 19);
		painelVendedor2.add(lblrecebera_1_1);
		lblrecebera_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblNomeVendedor2 = new JLabel("Marcos Alexandre de Andrade Carvalho");
		lblNomeVendedor2.setForeground(Color.WHITE);
		lblNomeVendedor2.setBounds(6, 6, 278, 21);
		painelVendedor2.add(lblNomeVendedor2);
		lblNomeVendedor2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblNumeroEstrelasVendedor2 = new JLabel("");
		lblNumeroEstrelasVendedor2.setBounds(336, 26, 170, 30);
		painelVendedor2.add(lblNumeroEstrelasVendedor2);

		lblNumeroEstrelasComprador = new JLabel("");
		lblNumeroEstrelasComprador.setBackground(new Color(255, 175, 175));
		lblNumeroEstrelasComprador.setBounds(588, 92, 170, 30);
		painelPrincipal.add(lblNumeroEstrelasComprador);

		lblNumeroEstrelasVendedor1 = new JLabel("");
		lblNumeroEstrelasVendedor1.setBounds(588, 181, 170, 30);
		painelPrincipal.add(lblNumeroEstrelasVendedor1);

		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean fechar = gerenciar.atualizarStatusContrato(sub_contrato_global.getId(), 3);

				if (fechar) {
					JOptionPane.showMessageDialog(null, "Contrato Finalizado com o status 'Cumprido Corretamente'");

					CadastroCliente compradores[] = sub_contrato_global.getCompradores();

					CadastroPontuacao pontuar_comprador = new CadastroPontuacao();
					pontuar_comprador.setId_contrato(sub_contrato_global.getId());
					pontuar_comprador.setPontos(5);
					pontuar_comprador.setId_cliente(compradores[0].getId());
					pontuar_comprador.setMotivo(1);
					pontuar_comprador.setDescricao("Conclui o contrato corretamente!");

					CadastroCliente vendedores[] = sub_contrato_global.getVendedores();

					CadastroPontuacao pontuar_vendedor1 = new CadastroPontuacao();
					pontuar_vendedor1.setId_contrato(sub_contrato_global.getId());
					pontuar_vendedor1.setPontos(5);
					pontuar_vendedor1.setMotivo(1);

					pontuar_vendedor1.setId_cliente(vendedores[0].getId());
					pontuar_vendedor1.setDescricao("Conclui o contrato corretamente!");

					CadastroPontuacao pontuar_vendedor2 = new CadastroPontuacao();

					if (vendedores[1] != null) {
						pontuar_vendedor2.setId_contrato(sub_contrato_global.getId());
						pontuar_vendedor2.setPontos(5);
						pontuar_vendedor2.setId_cliente(vendedores[1].getId());
						pontuar_vendedor2.setDescricao("Conclui o contrato corretamente!");
						pontuar_vendedor2.setMotivo(1);

					}

					ArrayList<CadastroPontuacao> pontuacoes = new ArrayList<>();
					pontuacoes.add(pontuar_comprador);
					pontuacoes.add(pontuar_vendedor1);

					if (vendedores[1] != null) {
						pontuacoes.add(pontuar_vendedor2);

					}

					GerenciarBancoPontuacao gerenciar_pontuacao = new GerenciarBancoPontuacao();
					boolean prosseguir = true;

					for (CadastroPontuacao ponto : pontuacoes) {

						// primeiro verifica se existe uma pontuacao para esta combinacao contrato
						// cliente
						ArrayList<CadastroPontuacao> lista_pontuacao = gerenciar_pontuacao
								.getPontuacaoPorContratoCliente(sub_contrato_global.getId(), ponto.getId_cliente());
						if (lista_pontuacao.size() > 0) {
							for (CadastroPontuacao pontos_antigos : lista_pontuacao) {

								boolean removido = gerenciar_pontuacao
										.removerPontuacao(pontos_antigos.getId_pontuacao());
								if (removido) {
									prosseguir = true;
								} else {
									JOptionPane.showMessageDialog(null,
											"Erro ao remover pontuacao antiga\nConsulte o administrador!");
									prosseguir = false;
									break;
								}

							}
						}

					}

					for (CadastroPontuacao ponto : pontuacoes) {
					if (prosseguir) {

						int result = gerenciar_pontuacao.inserirPontuacao(ponto);
						if (result > 0) {

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao inserir pontuação de contrato\nConsulte o administrador");
							break;
						}
					} 
					}

					((TelaGerenciarContrato) telaPai).travarContrato();
					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(null,
							"Erro ao atualizar o status do contrato\nTente novamente, se persistir o erro consulte o administrador");
				}
			}
		});
		btnConcluir.setBounds(647, 318, 111, 28);
		painelPrincipal.add(btnConcluir);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnCancelar.setBounds(524, 318, 111, 28);
		painelPrincipal.add(btnCancelar);

		this.setUndecorated(true);
		this.setLocationRelativeTo(null);

	}

	public void apresentarContratoCompleto(CadastroContrato sub_contrato, Registros.RegistroPagamento registro_pag,
			Registros.RegistroCarregamento registro_carga) {
		CadastroCliente compradores[] = sub_contrato.getCompradores();
		sub_contrato_global = sub_contrato;
		String nome_comprador = "";
		if (compradores[0].getTipo_pessoa() == 0) {
			nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
		} else {
			nome_comprador = compradores[0].getNome_fantaia().toUpperCase();
		}

		URL url = getClass().getResource("/imagens/icone_5_estrelas.png");
		ImageIcon img = new ImageIcon(url);

		lblNomeComprador.setText(nome_comprador);
		lblNumeroEstrelasComprador.setIcon(img);

		CadastroCliente vendedores[] = sub_contrato.getVendedores();

		String nome_vendedor = "";
		if (vendedores[0].getTipo_pessoa() == 0) {
			nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
		} else {
			nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();
		}

		URL url2 = getClass().getResource("/imagens/icone_5_estrelas.png");
		ImageIcon img2 = new ImageIcon(url2);

		lblNomeVendedor1.setText(nome_vendedor);
		lblNumeroEstrelasVendedor1.setIcon(img2);

		if (vendedores[1] != null) {

			painelVendedor2.setVisible(true);

			String nome_vendedor2 = "";
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedor = vendedores[1].getNome_empresarial().toUpperCase();
			} else {
				nome_vendedor2 = vendedores[1].getNome_fantaia().toUpperCase();
			}

			URL url3 = getClass().getResource("/imagens/icone_5_estrelas.png");
			ImageIcon img3 = new ImageIcon(url3);

			lblNomeVendedor2.setText(nome_vendedor2);
			lblNumeroEstrelasVendedor2.setIcon(img3);
		}

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
