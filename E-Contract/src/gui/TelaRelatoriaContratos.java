package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import relatoria.RelatorioContratoIndividual;
import relatoria.RelatorioContratos;

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
import cadastros.CadastroGrupo;
import cadastros.CadastroSafra;
import classesExtras.ComboBoxPersonalizado;
import classesExtras.ComboBoxRenderPersonalizado;
import conexaoBanco.GerenciarBancoSafras;
import manipular.ConverterPdf;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;



public class TelaRelatoriaContratos extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private JComboBox cBSafra ;
	private CadastroGrupo grupo_alvo;
	private CadastroCliente cliente_alvo;
	private JComboBox cBAlvo;
	JCheckBox chkBoxContratos, chkBoxContratosComoComprador, chkBoxContratosComoVendedor,
	chkBoxContratosComoCorretor, chckbxPagamentos, chkBoxComoDepositante,chkBoxComoFavorecido, chckbxCarregamentos, chckbxCarregamentpComoVendedor , chckbxCarregamentoComoComprador;
	
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JCheckBox chckbxTodasAsSafras;

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}


	public TelaRelatoriaContratos() {
		setModal(true);

		TelaRelatoriaContratos isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Relatoria");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 805, 464);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Safra:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(24, 66, 46, 14);
		painelPrincipal.add(lblNewLabel);
		
		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();

		cBSafra = new JComboBox();
		cBSafra.setEnabled(false);
		cBSafra.setModel(modelSafra);
		cBSafra.setRenderer(cBSafraPersonalizado);
		cBSafra.setBounds(92, 58, 316, 28);
		painelPrincipal.add(cBSafra);

		cBSafra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroSafra produto = (CadastroSafra) modelSafra.getSelectedItem();
				

			}

		});
		
		
		JLabel lblNewLabel_1 = new JLabel("Cliente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(24, 102, 46, 14);
		painelPrincipal.add(lblNewLabel_1);
		
		 cBAlvo = new JComboBox();
		cBAlvo.setBounds(92, 97, 217, 28);
		painelPrincipal.add(cBAlvo);
		
		JButton btnNewButton = new JButton("Gerar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean gerar = false;
				
				boolean contrato = false, contrato_como_comprador= false, contrato_como_vendedor= false, contrato_como_corretor = false;
				boolean pagamento = false, pagamento_como_despositante = false, pagamento_como_favorecido = false;
				boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
				Date hoje = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				int id_safra = -1;
				
				if(chckbxTodasAsSafras.isSelected()) {
					//todas as safras selecionadas
					id_safra = 0;
					gerar = true;
				}else {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
                    if(safra == null) {
                    	JOptionPane.showMessageDialog(null, "Marque a caixa Todas as safras ou\nSelecione uma safra");
                    	gerar = false;
                    }else {
                    	id_safra = safra.getId_safra();
                    	gerar = true;
                    }
					
				}
				
				if(chkBoxContratos.isSelected()) {
					contrato = true;
					
					if(chkBoxContratosComoCorretor.isSelected()) {
						contrato_como_corretor = true;
					}
					
					if(chkBoxContratosComoComprador.isSelected()) {
						contrato_como_comprador = true;
					}
					
					if(chkBoxContratosComoVendedor.isSelected()) {
						contrato_como_vendedor = true;
					}
				}else {
					
				}
				
				
				if(chckbxPagamentos.isSelected()) {
					pagamento = true;
					
					if(chkBoxComoDepositante.isSelected()) {
						pagamento_como_despositante  = true;
					}
					
					if(chkBoxComoFavorecido.isSelected()) {
						pagamento_como_favorecido = true;
					}
					
					
					
				}
				
				if(chckbxCarregamentos.isSelected()) {
					carregamento = true;
					
					if(chckbxCarregamentoComoComprador.isSelected()) {
						carregamento_como_comprador  = true;
					}
					
					if(chckbxCarregamentpComoVendedor.isSelected()) {
						carregamento_como_vendedor = true;
					}
					
					
					
				}
				
				
				
				
				if(gerar) {
					RelatorioContratos relatar = new RelatorioContratos
							(contrato, contrato_como_comprador, contrato_como_vendedor, contrato_como_corretor
									, pagamento, pagamento_como_despositante, pagamento_como_favorecido
									,carregamento, carregamento_como_comprador, carregamento_como_vendedor
									,id_safra, cliente_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();
			         
					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado);
				}else {
					
				}
				
				
				
				
				
				
				
			}
		});
		btnNewButton.setBounds(521, 391, 89, 23);
		painelPrincipal.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaCliente cliente = new TelaCliente(0, 11);
				cliente.setTelaPai(isto);
				cliente.setVisible(true);
				
				
				
			}
		});
		btnNewButton_1.setBounds(319, 100, 89, 23);
		painelPrincipal.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Opções:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(24, 142, 51, 23);
		painelPrincipal.add(lblNewLabel_2);
		
		
		
		 chkBoxContratos = new JCheckBox("Contratos");
		 chkBoxContratos.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		if(chkBoxContratos.isSelected()) {
		 			chkBoxContratos.setSelected(true);
		 			chkBoxContratosComoComprador.setEnabled(true);
		 			chkBoxContratosComoVendedor.setEnabled(true);
		 			chkBoxContratosComoCorretor.setEnabled(true);
		 		}else {
		 			chkBoxContratos.setSelected(false);
		 			chkBoxContratosComoComprador.setEnabled(false);
		 			chkBoxContratosComoVendedor.setEnabled(false);
		 			chkBoxContratosComoCorretor.setEnabled(false);
		 		}
		 		
		 	}
		 });
		chkBoxContratos.setBounds(34, 172, 110, 23);
		painelPrincipal.add(chkBoxContratos);
		
		 chkBoxContratosComoComprador = new JCheckBox("Alvo como Comprador");
		 chkBoxContratosComoComprador.setEnabled(false);
		chkBoxContratosComoComprador.setBounds(57, 205, 168, 23);
		painelPrincipal.add(chkBoxContratosComoComprador);
		
		 chkBoxContratosComoVendedor = new JCheckBox("Alvo como Vendedor");
		 chkBoxContratosComoVendedor.setEnabled(false);
		chkBoxContratosComoVendedor.setBounds(57, 241, 168, 23);
		painelPrincipal.add(chkBoxContratosComoVendedor);
		
		 chkBoxContratosComoCorretor = new JCheckBox("Avldo como Corretor");
		 chkBoxContratosComoCorretor.setEnabled(false);
		chkBoxContratosComoCorretor.setBounds(57, 279, 168, 23);
		painelPrincipal.add(chkBoxContratosComoCorretor);
		
		 chckbxPagamentos = new JCheckBox("Pagamentos");
		 chckbxPagamentos.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		if(chckbxPagamentos.isSelected()) {
		 			chckbxPagamentos.setSelected(true);
		 			chkBoxComoDepositante.setEnabled(true);
		 			chkBoxComoFavorecido.setEnabled(true);
		 		}else {
		 			chckbxPagamentos.setSelected(false);
		 			chkBoxComoDepositante.setEnabled(false);
		 			chkBoxComoFavorecido.setEnabled(false);
		 		}
		 		
		 	}
		 });
		chckbxPagamentos.setBounds(280, 172, 110, 23);
		painelPrincipal.add(chckbxPagamentos);
		
		 chkBoxComoDepositante = new JCheckBox("Alvo como Depositante");
		 chkBoxComoDepositante.setEnabled(false);
		chkBoxComoDepositante.setBounds(304, 205, 168, 23);
		painelPrincipal.add(chkBoxComoDepositante);
		
		 chkBoxComoFavorecido = new JCheckBox("Alvo como Favorecido");
		 chkBoxComoFavorecido.setEnabled(false);
		chkBoxComoFavorecido.setBounds(304, 241, 168, 23);
		painelPrincipal.add(chkBoxComoFavorecido);
		
		 chckbxCarregamentos = new JCheckBox("Carregamentos");
		 chckbxCarregamentos.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 
		 		if(chckbxCarregamentos.isSelected()) {
		 			chckbxCarregamentos.setSelected(true);
		 			chckbxCarregamentoComoComprador.setEnabled(true);
		 			chckbxCarregamentpComoVendedor.setEnabled(true);
		 		}else {
		 			chckbxCarregamentos.setSelected(false);
		 			chckbxCarregamentoComoComprador.setEnabled(false);
		 			chckbxCarregamentpComoVendedor.setEnabled(false);
		 		}
		 	}
		 });
		chckbxCarregamentos.setBounds(550, 172, 120, 23);
		painelPrincipal.add(chckbxCarregamentos);
		
		 chckbxCarregamentoComoComprador = new JCheckBox("Alvo como Comprador");
		 chckbxCarregamentoComoComprador.setEnabled(false);
		chckbxCarregamentoComoComprador.setBounds(574, 205, 168, 23);
		painelPrincipal.add(chckbxCarregamentoComoComprador);
		
		 chckbxCarregamentpComoVendedor = new JCheckBox("Alvo como Vendedor");
		 chckbxCarregamentpComoVendedor.setEnabled(false);
		chckbxCarregamentpComoVendedor.setBounds(574, 241, 168, 23);
		painelPrincipal.add(chckbxCarregamentpComoVendedor);
		
		chckbxTodasAsSafras = new JCheckBox("Todas as Safras");
		chckbxTodasAsSafras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chckbxTodasAsSafras.isSelected()) {
					chckbxTodasAsSafras.setSelected(true);
					cBSafra.setEnabled(false);
				}else {
					chckbxTodasAsSafras.setSelected(false);
					cBSafra.setEnabled(true);
				}
				
			}
		});
		chckbxTodasAsSafras.setSelected(true);
		chckbxTodasAsSafras.setBounds(424, 61, 120, 23);
		painelPrincipal.add(chckbxTodasAsSafras);
		
		
	
		
		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
	
	public void setGrupoAlvo(CadastroGrupo grupo) {
		this.grupo_alvo = grupo;
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	
		    	cBAlvo.removeAllItems();
		    	cBAlvo.updateUI();
		    	cBAlvo.repaint();
		    	
		    
		    	
		    	cBAlvo.addItem(grupo.getId_grupo()+ " " + grupo.getNome_grupo());
		    	cBAlvo.updateUI();
		    	cBAlvo.repaint();
				   
				   
			    } 
			}); 
	}
	
	public void setClienteAlvo(CadastroCliente cliente) {
		this.cliente_alvo = cliente;
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	
		    	cBAlvo.removeAllItems();
		    	cBAlvo.updateUI();
		    	cBAlvo.repaint();
		    	
		    	String nome = "";
		    	
		    	if(cliente_alvo.getTipo_pessoa() == 0) {
		    		nome = cliente_alvo.getNome_empresarial();
		    	}else {
		    		nome = cliente_alvo.getNome_fantaia();

		    	}
		    	
		    	cBAlvo.addItem(cliente_alvo.getId() + " " + nome);
		    	cBAlvo.updateUI();
		    	cBAlvo.repaint();
				   
			    } 
			}); 
	}
}
