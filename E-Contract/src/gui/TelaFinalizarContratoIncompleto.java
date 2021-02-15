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
import java.awt.GraphicsConfiguration;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
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
import javax.swing.JComboBox;



public class TelaFinalizarContratoIncompleto extends JDialog {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos, lblNumeroEstrelasVendedor1, lblNumeroEstrelasComprador;
    private TelaFinalizarContratoIncompleto isto;
    private JDialog telaPai;
    private JPanelTransparent painelVendedor2;
    private JLabel lblNomeVendedor2, lblNumeroEstrelasVendedor2;
    private CadastroContrato sub_contrato_global;
    private JFrame telaPaiJFrame;
    
    private JComboBox cBNumeroEstrelasComprador, cBNumeroEstrelasVendedor1, cBNumeroEstrelasVendedor2, cBMotivoComprador, cBMotivoVendedor1, cBMotivoVendedor2;
    private JTextArea textAreaMotivoComprador, textAreaMotivoVendedor1, textAreaMotivoVendedor2;

    private JLabel lblNomeComprador, lblNomeVendedor1 ;
	public TelaFinalizarContratoIncompleto(JFrame janela_pai) {
		setModal(true);

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Finalizar Contrato");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1148, 684);
		painelPrincipal.kStartColor = Color.ORANGE;
		painelPrincipal.kEndColor = new Color(0, 153, 153);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnNewButton = new JButton("Visão Geral");
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(719, 595, 111, 28);
		painelPrincipal.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaFinalizarContratoIncompleto.class.getResource("/imagens/nao_cumpriu.png")));
		lblNewLabel.setBounds(10, 209, 215, 233);
		painelPrincipal.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Compradores:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(254, 37, 213, 17);
		painelPrincipal.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Vendedores:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1_1.setBounds(248, 264, 213, 17);
		painelPrincipal.add(lblNewLabel_1_1);
		
		 lblNomeComprador = new JLabel("Marcos Alexandre de Andrade Carvalho");
		 lblNomeComprador.setForeground(Color.WHITE);
		lblNomeComprador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNomeComprador.setBounds(264, 72, 342, 21);
		painelPrincipal.add(lblNomeComprador);
		
		 lblNomeVendedor1 = new JLabel("Marcos Alexandre de Andrade Carvalho");
		 lblNomeVendedor1.setForeground(Color.WHITE);
		lblNomeVendedor1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNomeVendedor1.setBounds(258, 292, 342, 21);
		painelPrincipal.add(lblNomeVendedor1);
		
		JLabel lblrecebera = new JLabel("receberá");
		lblrecebera.setForeground(Color.WHITE);
		lblrecebera.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblrecebera.setBounds(833, 168, 57, 19);
		painelPrincipal.add(lblrecebera);
		
		JLabel lblrecebera_1 = new JLabel("receberá");
		lblrecebera_1.setForeground(Color.WHITE);
		lblrecebera_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblrecebera_1.setBounds(833, 381, 57, 19);
		painelPrincipal.add(lblrecebera_1);
		
		
		  painelVendedor2 = new JPanelTransparent();
		
		  painelVendedor2.setTransparencia(1);
		  painelVendedor2.setVisible(false);
		painelVendedor2.setBounds(254, 430, 822, 137);
		painelVendedor2.setBackground(new Color(0, 255, 204));
		painelPrincipal.add(painelVendedor2);
		painelVendedor2.repaint();
		painelVendedor2.setLayout(null);
		
		JLabel lblrecebera_1_1 = new JLabel("receberá");
		lblrecebera_1_1.setForeground(Color.WHITE);
		lblrecebera_1_1.setBounds(575, 94, 57, 19);
		painelVendedor2.add(lblrecebera_1_1);
		lblrecebera_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		 lblNomeVendedor2 = new JLabel("Marcos Alexandre de Andrade Carvalho");
		 lblNomeVendedor2.setForeground(Color.WHITE);
		lblNomeVendedor2.setBounds(0, 12, 348, 21);
		painelVendedor2.add(lblNomeVendedor2);
		lblNomeVendedor2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		 lblNumeroEstrelasVendedor2 = new JLabel("");
		lblNumeroEstrelasVendedor2.setBounds(654, 83, 170, 30);
		painelVendedor2.add(lblNumeroEstrelasVendedor2);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Motivo do não cumprimento do contrato:");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_1_1.setBounds(16, 44, 256, 17);
		painelVendedor2.add(lblNewLabel_3_1_1);
		
		 textAreaMotivoVendedor2 = new JTextArea();
		 textAreaMotivoVendedor2.setText("Escreva um comentario particular aqui");
		textAreaMotivoVendedor2.setLineWrap(true);
		textAreaMotivoVendedor2.setWrapStyleWord(true);
		textAreaMotivoVendedor2.setBackground(new Color(102, 204, 102));
		textAreaMotivoVendedor2.setBounds(284, 45, 280, 83);
		painelVendedor2.add(textAreaMotivoVendedor2);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Número de Estrelas:");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1_1.setBounds(358, 15, 123, 17);
		painelVendedor2.add(lblNewLabel_2_1_1);
		
		 cBNumeroEstrelasVendedor2 = new JComboBox();
		 cBNumeroEstrelasVendedor2.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		String s_num_estrelas = cBNumeroEstrelasVendedor2.getSelectedItem().toString();
		 		int i_num_estrelas = Integer.parseInt(s_num_estrelas);
		 		
		 		//carregarImg
		 		lblNumeroEstrelasVendedor2.setIcon(carregarImg(i_num_estrelas));
		 	}
		 });
		cBNumeroEstrelasVendedor2.setBounds(492, 11, 73, 22);
		painelVendedor2.add(cBNumeroEstrelasVendedor2);
		cBNumeroEstrelasVendedor2.addItem("1");
		cBNumeroEstrelasVendedor2.addItem("2");
		cBNumeroEstrelasVendedor2.addItem("3");
		cBNumeroEstrelasVendedor2.addItem("4");
		cBNumeroEstrelasVendedor2.addItem("5");
		
		 cBMotivoVendedor2 = new JComboBox();
		 cBMotivoVendedor2.setBounds(6, 73, 266, 26);
		painelVendedor2.add(cBMotivoVendedor2);

		cBMotivoVendedor2.addItem("Cumpriu corretamente o contrato");

		cBMotivoVendedor2.addItem("Não poderá arcar com a quantidade de sacos do contrato");
		cBMotivoVendedor2.addItem("Desistiu do contrato, sem renegociar");
		cBMotivoVendedor2.addItem("Renegociou o contrato com a parte ou intermediario");
		cBMotivoVendedor2.addItem("Renegociou o contrato através de aditivo contratual");
		
		
		 lblNumeroEstrelasComprador = new JLabel("");
		lblNumeroEstrelasComprador.setBackground(new Color(255, 175, 175));
		lblNumeroEstrelasComprador.setBounds(906, 157, 170, 30);
		painelPrincipal.add(lblNumeroEstrelasComprador);
		
		 lblNumeroEstrelasVendedor1 = new JLabel("");
		lblNumeroEstrelasVendedor1.setBounds(906, 373, 170, 30);
		painelPrincipal.add(lblNumeroEstrelasVendedor1);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.setForeground(Color.WHITE);
		btnConcluir.setBackground(new Color(0, 102, 51));
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean fechar = gerenciar.atualizarStatusContrato(sub_contrato_global.getId(), 3);
				
				if(fechar) {
					JOptionPane.showMessageDialog(isto, "Contrato Finalizado com o status 'Cumprido Parcialmente'");
					GerenciarBancoPontuacao gerenciar_pontuacao = new GerenciarBancoPontuacao();
					
					CadastroCliente compradores[] = sub_contrato_global.getCompradores();

					CadastroPontuacao pontuar_comprador = new CadastroPontuacao();
					pontuar_comprador.setId_contrato(sub_contrato_global.getId());
					pontuar_comprador.setPontos(Integer.parseInt(cBNumeroEstrelasComprador.getSelectedItem().toString()));
					pontuar_comprador.setId_cliente(compradores[0].getId());
					pontuar_comprador.setDescricao(textAreaMotivoComprador.getText());
					pontuar_comprador.setMotivo(cBMotivoComprador.getSelectedIndex());

					CadastroCliente vendedores[] = sub_contrato_global.getVendedores();

					CadastroPontuacao pontuar_vendedor1 = new CadastroPontuacao();
					pontuar_vendedor1.setId_contrato(sub_contrato_global.getId());
					pontuar_vendedor1.setPontos(Integer.parseInt(cBNumeroEstrelasVendedor1.getSelectedItem().toString()));
					pontuar_vendedor1.setId_cliente(vendedores[0].getId());
					pontuar_vendedor1.setDescricao(textAreaMotivoVendedor1.getText());
					pontuar_vendedor1.setMotivo(cBMotivoVendedor1.getSelectedIndex());

					CadastroPontuacao pontuar_vendedor2 = new CadastroPontuacao();

					if (vendedores[1] != null) {
						pontuar_vendedor2.setId_contrato(sub_contrato_global.getId());
						pontuar_vendedor2.setPontos(Integer.parseInt(cBNumeroEstrelasVendedor2.getSelectedItem().toString()));
						pontuar_vendedor2.setId_cliente(vendedores[1].getId());
						pontuar_vendedor2.setDescricao(textAreaMotivoVendedor2.getText());
						pontuar_vendedor2.setMotivo(cBMotivoVendedor2.getSelectedIndex());

					}

					ArrayList<CadastroPontuacao> pontuacoes = new ArrayList<>();
					pontuacoes.add(pontuar_comprador);
					pontuacoes.add(pontuar_vendedor1);

					if (vendedores[1] != null) {
						pontuacoes.add(pontuar_vendedor2);

					}

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
									JOptionPane.showMessageDialog(isto,
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
							JOptionPane.showMessageDialog(isto,
									"Erro ao inserir pontuação de contrato\nConsulte o administrador");
							break;
						}
					} 
					}

					
					//((TelaGerenciarContrato) telaPai).travarContrato();
					((TelaGerenciarContrato) telaPaiJFrame).travarContrato();
					isto.dispose();
					
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao atualizar o status do contrato\nTente novamente, se persistir o erro consulte o administrador");
				}
			}
		});
		btnConcluir.setBounds(965, 595, 111, 28);
		painelPrincipal.add(btnConcluir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(128, 0, 0));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnCancelar.setBounds(842, 595, 111, 28);
		painelPrincipal.add(btnCancelar);
		
		JLabel lblNewLabel_2 = new JLabel("Número de Estrelas:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(616, 75, 123, 17);
		painelPrincipal.add(lblNewLabel_2);
		
		
		
		 cBNumeroEstrelasComprador = new JComboBox();
		 cBNumeroEstrelasComprador.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		String s_num_estrelas = cBNumeroEstrelasComprador.getSelectedItem().toString();
		 		int i_num_estrelas = Integer.parseInt(s_num_estrelas);
		 		
		 		//carregarImg
		 		lblNumeroEstrelasComprador.setIcon(carregarImg(i_num_estrelas));
		 		
		 	}
		 });
		cBNumeroEstrelasComprador.setBounds(750, 71, 73, 22);
		painelPrincipal.add(cBNumeroEstrelasComprador);
		cBNumeroEstrelasComprador.addItem("1");
		cBNumeroEstrelasComprador.addItem("2");
		cBNumeroEstrelasComprador.addItem("3");
		cBNumeroEstrelasComprador.addItem("4");
		cBNumeroEstrelasComprador.addItem("5");

		
		JLabel lblNewLabel_3 = new JLabel("Motivo do não cumprimento do contrato:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(274, 104, 256, 17);
		painelPrincipal.add(lblNewLabel_3);
		
		 textAreaMotivoComprador = new JTextArea();
		 textAreaMotivoComprador.setText("Escreva um comentario particular aqui");
		 textAreaMotivoComprador.setLineWrap(true);
		 textAreaMotivoComprador.setWrapStyleWord(true);
		textAreaMotivoComprador.setBackground(new Color(102, 204, 102));
		textAreaMotivoComprador.setBounds(543, 104, 280, 83);
		painelPrincipal.add(textAreaMotivoComprador);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(Color.BLACK);
		lblNewLabel_4.setBounds(256, 237, 567, 7);
		painelPrincipal.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3_1 = new JLabel("Motivo do não cumprimento do contrato:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_1.setBounds(268, 325, 256, 17);
		painelPrincipal.add(lblNewLabel_3_1);
		
		 textAreaMotivoVendedor1 = new JTextArea();
		 textAreaMotivoVendedor1.setText("Escreva um comentario particular aqui");
		textAreaMotivoVendedor1.setLineWrap(true);
		textAreaMotivoVendedor1.setWrapStyleWord(true);
		textAreaMotivoVendedor1.setBackground(new Color(102, 204, 102));
		textAreaMotivoVendedor1.setBounds(537, 325, 280, 83);
		painelPrincipal.add(textAreaMotivoVendedor1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Número de Estrelas:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(610, 296, 123, 17);
		painelPrincipal.add(lblNewLabel_2_1);
		
		 cBNumeroEstrelasVendedor1 = new JComboBox();
		 cBNumeroEstrelasVendedor1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		String s_num_estrelas = cBNumeroEstrelasVendedor1.getSelectedItem().toString();
		 		int i_num_estrelas = Integer.parseInt(s_num_estrelas);
		 		
		 		//carregarImg
		 		lblNumeroEstrelasVendedor1.setIcon(carregarImg(i_num_estrelas));
		 	}
		 });
		cBNumeroEstrelasVendedor1.setBounds(744, 292, 73, 22);
		painelPrincipal.add(cBNumeroEstrelasVendedor1);
		cBNumeroEstrelasVendedor1.addItem("1");
		cBNumeroEstrelasVendedor1.addItem("2");
		cBNumeroEstrelasVendedor1.addItem("3");
		cBNumeroEstrelasVendedor1.addItem("4");
		cBNumeroEstrelasVendedor1.addItem("5");
		
		 cBMotivoVendedor1 = new JComboBox();
		 cBMotivoVendedor1.setBounds(264, 354, 266, 26);
		painelPrincipal.add(cBMotivoVendedor1);
		
		cBMotivoVendedor1.addItem("Cumpriu corretamente o contrato");

		cBMotivoVendedor1.addItem("Não poderá arcar com a quantidade de sacos do contrato");
		cBMotivoVendedor1.addItem("Desistiu do contrato, sem renegociar");
		cBMotivoVendedor1.addItem("Renegociou o contrato com a parte ou intermediario");
		cBMotivoVendedor1.addItem("Renegociou o contrato através de aditivo contratual");


		 cBMotivoComprador = new JComboBox();
		 cBMotivoComprador.setBounds(264, 127, 266, 26);
		painelPrincipal.add(cBMotivoComprador);
		cBMotivoComprador.addItem("Cumpriu corretamente o contrato");
		cBMotivoComprador.addItem("Não poderá arcar com a quantidade de sacos do contrato");
		cBMotivoComprador.addItem("Desistiu do contrato, sem renegociar");
		cBMotivoComprador.addItem("Renegociou o contrato com a parte ou intermediario");
		cBMotivoComprador.addItem("Renegociou o contrato através de aditivo contratual");
		
		
		
     /*  // this.setUndecorated(true);
		 GraphicsConfiguration gc = janela_pai.getGraphicsConfiguration();
			Rectangle bounds = gc.getBounds();
			  
			  
			Point realLocation = new Point(); // holds final location of dialog.
			realLocation.x = (bounds.x + bounds.width / 2) - (isto.getWidth() / 2);
			realLocation.y = (bounds.y + bounds.height / 2 )- (isto.getHeight() / 2);
			  
			this.setLocation(realLocation);
		*/
		this.setLocationRelativeTo(janela_pai);
		
	}
	
	
	
	public void apresentarContratoIncompleto(CadastroContrato sub_contrato, Registros.RegistroPagamento registro_pag, Registros.RegistroCarregamento registro_carga) {
		CadastroCliente compradores[] = sub_contrato.getCompradores();
		sub_contrato_global = sub_contrato;
		String nome_comprador = "";
		if(compradores[0].getTipo_pessoa() == 0) {
			nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
		}else {
			nome_comprador = compradores[0].getNome_fantaia().toUpperCase();
		}
		
		URL url = getClass().getResource("/imagens/icone_1_estrelas.png");
		ImageIcon img = new ImageIcon(url);
		
		lblNomeComprador.setText(nome_comprador);
		lblNumeroEstrelasComprador.setIcon(img);
		
		CadastroCliente vendedores[] = sub_contrato.getVendedores();

		
		String nome_vendedor = "";
		if(vendedores[0].getTipo_pessoa() == 0) {
			nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
		}else {
			nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();
		}
		
		URL url2 = getClass().getResource("/imagens/icone_1_estrelas.png");
		ImageIcon img2 = new ImageIcon(url2);
		
		lblNomeVendedor1.setText(nome_vendedor);
		lblNumeroEstrelasVendedor1.setIcon(img2);
		
		if(vendedores[1] != null) {
			
			
			painelVendedor2.setVisible(true);
			
			String nome_vendedor2 = "";
			if(vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedor = vendedores[1].getNome_empresarial().toUpperCase();
			}else {
				nome_vendedor2 = vendedores[1].getNome_fantaia().toUpperCase();
			}
			
			URL url3 = getClass().getResource("/imagens/icone_1_estrelas.png");
			ImageIcon img3= new ImageIcon(url3);
			
			lblNomeVendedor2.setText(nome_vendedor2);
			lblNumeroEstrelasVendedor2.setIcon(img3);
		}
		
	}
	
	
	public ImageIcon carregarImg(int num_estrelas) {
		URL url3 = getClass().getResource("/imagens/icone_" + num_estrelas + "_estrelas.png");
		return  new ImageIcon(url3);
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}
}
