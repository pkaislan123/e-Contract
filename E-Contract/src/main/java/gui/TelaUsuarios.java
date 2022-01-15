package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Window;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
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
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
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
import main.java.views_personalizadas.TelaDetalharContaBancaria;
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
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

public class TelaUsuarios extends JFrame {

	private final JPanel contentPanel;
	private static ArrayList<CadastroLogin> usuarios_pesquisados = new ArrayList<>();
	private static ArrayList<CadastroLogin> usuarios_disponiveis = new ArrayList<>();
	private CadastroLogin login;
	private CadastroLogin usuarioSelecionado;
	private Log GerenciadorLog;
	private JDialog telaPai;
	private JTable tabela;
	private JPopupMenu jPopupMenuStatus;

	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private JTextField entPesquisa;
	private final JPanel panel_2 = new JPanel();

	public void pesquisar() {
		modelo.setNumRows(0);
		GerenciarBancoLogin listaUsuarios = new GerenciarBancoLogin();
		usuarios_pesquisados = listaUsuarios.getUsuarios();
		usuarios_disponiveis.clear();

		for (CadastroLogin usuario : listaUsuarios.getUsuarios()) {
			String nome, email, login, senha;
			int direitos, id, status;

			id = usuario.getId();
			login = usuario.getLogin();
			nome = usuario.getNome();
			email = usuario.getEmail();
			senha = usuario.getSenha();
			status = usuario.getStatus();

			String s_status = "";
			if (status == 0)
				s_status = "Inativo";
			else
				s_status = "Ativo";

			modelo.addRow(new Object[] { id, s_status, login, nome, email });

			usuarios_disponiveis.add(usuario);
		}

	}

	private TelaUsuarios isto;

	public TelaUsuarios(int flag_modo_tela, Window janela_pai) {

		// setModal(true);

		getDadosGlobais();
		isto = this;
		// flag_modo_tela == 1 //modo selecao

		TelaUsuarios isto = this;
		setTitle("Usuários");
		getContentPane().setLayout(new BorderLayout());

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 817, 468);
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		panel_2.setBackground(new Color(51, 0, 102));
		panel_2.setBounds(0, 0, 219, 523);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);

		JButton btnUsurio = new JButton("+ Usuário");
		btnUsurio.setBounds(41, 303, 120, 23);
		panel_2.add(btnUsurio);
		btnUsurio.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/add_usuario.png")));

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBounds(41, 269, 120, 23);
		panel_2.add(btnSelecionar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(41, 234, 120, 23);
		panel_2.add(btnEditar);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = 0;
				indiceDaLinha = tabela.getSelectedRow();

				String s_status = modelo.getValueAt(indiceDaLinha, 1).toString();

				if (login.getConfigs_privilegios().getNivel_privilegios() > 2) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				} else {

					if (s_status.equalsIgnoreCase("Inativo")) {
						JOptionPane.showMessageDialog(isto, "Desbloqueio o usuário para edição");
					} else {

						TelaCadastroUsuario cadastrarUsuario = new TelaCadastroUsuario(1,
								usuarios_disponiveis.get(indiceDaLinha), isto);
					}
				}

			}
		});
		btnEditar.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/editar.png")));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flag_modo_tela == 1) {
					int indiceDaLinha = tabela.getSelectedRow();
					usuarioSelecionado = usuarios_disponiveis.get(indiceDaLinha);

					((TelaCriarTarefa) telaPai).setExecutor(usuarioSelecionado);

					isto.dispose();
				}

			}
		});
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (login.getConfigs_privilegios().getNivel_privilegios() > 2) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				} else {
					TelaCadastroUsuario cadastrarUsuario = new TelaCadastroUsuario(0, null, isto);
				}
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(241, 183, 560, 202);
		panel.setLayout(null);
		contentPanel.add(panel);

		tabela = new JTable(modelo);

		tabela.setBackground(new Color(255, 255, 255));
		modelo.addColumn("Id");
		modelo.addColumn("Status");
		modelo.addColumn("Login");
		modelo.addColumn("Nome");
		modelo.addColumn("E-mail");

		tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(120);

		pesquisar();
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pesquisar();

			}
		});
		scrollPane.setBounds(0, 0, 560, 202);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(Color.WHITE);
		panel.add(scrollPane);

		entPesquisa = new JTextField();
		entPesquisa.setBounds(241, 144, 263, 28);
		contentPanel.add(entPesquisa);
		entPesquisa.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 0, 255));
		panel_1.setBounds(206, 25, 623, 77);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Gerenciar Usuários");
		lblNewLabel.setBackground(new Color(102, 0, 102));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(25, 11, 397, 46);
		panel_1.add(lblNewLabel);

		JButton btnNewButton = new JButton("Sair");
		btnNewButton.setBounds(681, 408, 120, 23);
		contentPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});

		if (flag_modo_tela == 0) {
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);

		}

		setMenuStatus();
		this.setResizable(false);
		this.setLocationRelativeTo(janela_pai);
		this.setUndecorated(true);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();

		// usuario logado
		login = dados.getLogin();
	}

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}

	public void setMenuStatus() {
		jPopupMenuStatus = new JPopupMenu();
		JMenuItem jMenuItemBloquear = new JMenuItem();
		JMenuItem jMenuItemDesloquear = new JMenuItem();

		jMenuItemBloquear.setText("Bloquear");
		jMenuItemDesloquear.setText("Desloquear");

		jMenuItemBloquear.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela.getSelectedRow();
				String s_status = modelo.getValueAt(indiceDaLinha, 1).toString();

				if (login.getConfigs_privilegios().getNivel_privilegios() > 2) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				} else {

					if (s_status.equals("Inativo")) {
						JOptionPane.showMessageDialog(isto, "Usuário já esta bloqueado!");
					} else {

						GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
						int id = Integer.parseInt(modelo.getValueAt(indiceDaLinha, 0).toString());
						if (gerenciar.bloquearUsuario(id)) {
							JOptionPane.showMessageDialog(isto, "Usuário foi bloqueado!");
							pesquisar();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao bloquear, consulte o administrador!");

						}

					}
				}

			}
		});

		jMenuItemDesloquear.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela.getSelectedRow();
				String s_status = modelo.getValueAt(indiceDaLinha, 1).toString();

				if (login.getConfigs_privilegios().getNivel_privilegios() > 2) {

					JOptionPane.showMessageDialog(null, "Requer Elevação de Direitos \n Reportado ao Administrador");
					GerenciadorLog.registrarLogDiario("aviso", "tentativa de criação de novo usuário");
				} else {

					if (s_status.equals("Ativo")) {
						JOptionPane.showMessageDialog(isto, "Usuário já esta Ativo!");
					} else {
						GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
						int id = Integer.parseInt(modelo.getValueAt(indiceDaLinha, 0).toString());
						if (gerenciar.desbloquearUsuario(id)) {
							JOptionPane.showMessageDialog(isto, "Usuário foi desbloqueado!");
							pesquisar();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao desbloquear, consulte o administrador!");

						}
					}
				}

			}
		});

		jPopupMenuStatus.add(jMenuItemBloquear);
		jPopupMenuStatus.add(jMenuItemDesloquear);

		tabela.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuStatus.show(tabela, e.getX(), e.getY());
				}
			}
		});

	}

}
