package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoTransferencias;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;



public class TelaConfirmarTransferenciaPagamentoContratual extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private TelaConfirmarTransferenciaPagamentoContratual isto;
    private JDialog telaPai;
    private JTextField entData;
    private JTextField entValor;
    private JTextField entContratoRemetente;
    private JCheckBox chkBoxDataAtual ;
    private CadastroContrato contrato_destinatario;
    private JComboBox cBContratoDestinatario ;
    

	public TelaConfirmarTransferenciaPagamentoContratual(CadastroContrato contrato_remetente, int id_pagamento_contratual) {
		setModal(true);

		 isto = this;
	
		 
		 GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		 CadastroContrato.CadastroPagamentoContratual pagamento_contratual = gerenciar.getPagamentoContratual(id_pagamento_contratual);
		 
		setResizable(true);
		setTitle("E-Contract - Transferencia de Pagamento");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 468, 297);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow]", "[][][][][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 0,alignx trailing");
		
		entData = new JTextField();
		painelPrincipal.add(entData, "flowx,cell 1 0,growx");
		entData.setColumns(10);
		
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);

		
		entData.setText(pegarData());

		 chkBoxDataAtual = new JCheckBox("Hoje");
		chkBoxDataAtual.setSelected(true);
		painelPrincipal.add(chkBoxDataAtual, "cell 1 0");
		
		
		
		 chkBoxDataAtual = new JCheckBox("Data Atual");
		chkBoxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chkBoxDataAtual.isSelected()) {
					chkBoxDataAtual.setSelected(true);
					pegarData();
					entData.setEditable(false);

				}else {
					chkBoxDataAtual.setSelected(false);
					pegarData();
					entData.setEditable(true);
				}
			}
		});
		
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblValor, "cell 0 1,alignx trailing");
		
		entValor = new JTextField();
		entValor.setEditable(false);
		entValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento_contratual.getValor_pagamento());
		
		entValor.setText(valorString);
		painelPrincipal.add(entValor, "cell 1 1,growx");
		entValor.setColumns(10);
		
		
		
		JLabel lblContratoRemetente = new JLabel("Contrato Remetente:");
		lblContratoRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblContratoRemetente, "cell 0 2,alignx trailing");
		
		entContratoRemetente = new JTextField();
		entContratoRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		entContratoRemetente.setEditable(false);
		entContratoRemetente.setColumns(10);
		entContratoRemetente.setText(contrato_remetente.getId() + "-" + contrato_remetente.getCodigo());
		painelPrincipal.add(entContratoRemetente, "cell 1 2,growx");
		
		JLabel lblContratoDestinatario = new JLabel("Contrato Destinatario:");
		lblContratoDestinatario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblContratoDestinatario, "cell 0 3,alignx trailing");
		
		 cBContratoDestinatario = new JComboBox();
		cBContratoDestinatario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(cBContratoDestinatario, "flowx,cell 1 3,growx");
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblDescrio, "cell 0 4,alignx right");
		
		JTextArea textAreaDescricaoTransferencia = new JTextArea();
		textAreaDescricaoTransferencia.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textAreaDescricaoTransferencia.setLineWrap(true);
		textAreaDescricaoTransferencia.setWrapStyleWord(true);
		painelPrincipal.add(textAreaDescricaoTransferencia, "cell 1 4,grow");
		
		JButton btnTransferir = new JButton("Concluir");
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String descricao, data;
				
				descricao = textAreaDescricaoTransferencia.getText();
				data = entData.getText();
				
				CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia = new CadastroContrato.CadastroTransferenciaPagamentoContratual();
				transferencia.setData(data);
				transferencia.setDescricao(descricao);
				transferencia.setId_contrato_destinatario(contrato_destinatario.getId());
				transferencia.setId_contrato_remetente(contrato_remetente.getId());
				transferencia.setId_pagamento_contratual(id_pagamento_contratual);
				
				GerenciarBancoTransferencias gerenciar = new GerenciarBancoTransferencias();
				int transferiou = gerenciar.inserirTransferencia(transferencia);
				if(transferiou > 0) {
					((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();

					JOptionPane.showMessageDialog(null, "Transferencia Efetuada");
					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao realizar Transferencia Efetuada\nConsulte o administrador");
					isto.dispose();

				}
				
				
				
			}
		});
		painelPrincipal.add(btnTransferir, "cell 1 5,alignx right");
		
		JButton btnSelecionarContratoDestinatario = new JButton("Selecionar");
		btnSelecionarContratoDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContratos contrato = new TelaContratos(2);
				contrato.setTelaPai(isto);
				contrato.setVisible(true);
			}
		});
		painelPrincipal.add(btnSelecionarContratoDestinatario, "cell 1 3");
		
		
		
		
		

			
		
		
		

		this.setLocationRelativeTo(null);

		
		
		
	}
	
	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}

	
	public void setContratoDestintario(CadastroContrato destinatario) {

		this.contrato_destinatario = destinatario;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBContratoDestinatario.removeAllItems();
				cBContratoDestinatario.repaint();
				cBContratoDestinatario.updateUI();
				cBContratoDestinatario.addItem(destinatario.getId() +"-" + destinatario.getCodigo());

				cBContratoDestinatario.repaint();
				cBContratoDestinatario.updateUI();

			}
		});
	}
	
	public void setTelaPag(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
}
