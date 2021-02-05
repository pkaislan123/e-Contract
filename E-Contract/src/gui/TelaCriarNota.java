package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Locale;
import org.freixas.jcalendar.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import outros.DadosGlobais;
import tratamento_proprio.Log;

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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroNota;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoNotas;
import keeptoo.KGradientPanel;
import manipular.ConfiguracoesGlobais;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class TelaCriarNota extends JDialog {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaCriarNota isto;
	private JDialog telaPai;
	private JTextField entNome;
	private JTextField entDescricao;
	private JTextField entTempoNotificacao;
	private JPanel painelNotificar;
	private JRadioButton rBHoras, rBDias, rBMinutos;
	private JLabel lblDataLembrete;
	private  JButton btnDefinirTempo;
    private JCheckBox chkBoxSim,chkBoxNao, chckBoxNaoNotificar;
    private Calendar data_selecionada;
    private JComboBox cBTipo ;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaCriarNota instance ;
	private CadastroNota nota_global;
	private JTextArea textAreaAnotacao;
	
	public TelaCriarNota(int flag_modo_operacao, CadastroNota nota,  Window janela_pai) {
		// setModal(true);
         getDadosGlobais();
		isto = this;
         instance = this;
		setResizable(true);
		nota_global = nota;
		if(flag_modo_operacao == 1) {
		setTitle("E-Contract - Criar Anotação");
		}else {
			setTitle("E-Contract - Editar Anotação");

		}
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1204, 660);
		painelPrincipal.kEndColor = new Color(102, 153, 0);
		painelPrincipal.kStartColor = new Color(51, 255, 0);
		painelPrincipal.setBackground(new Color(51, 153, 204));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaCriarNota.class.getResource("/imagens/icone_grande_anotacao.png")));
		lblNewLabel_1.setBounds(28, 18, 96, 96);
		painelPrincipal.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(77, 134, 59, 25);
		painelPrincipal.add(lblNewLabel);

		entNome = new JTextField();
		entNome.setBounds(138, 126, 452, 33);
		painelPrincipal.add(entNome);
		entNome.setColumns(10);

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescrio.setBounds(43, 179, 93, 25);
		painelPrincipal.add(lblDescrio);

		entDescricao = new JTextField();
		entDescricao.setColumns(10);
		entDescricao.setBounds(138, 171, 452, 33);
		painelPrincipal.add(entDescricao);

		JLabel lblDescrio_1 = new JLabel("Texto:");
		lblDescrio_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescrio_1.setBounds(277, 246, 58, 25);
		painelPrincipal.add(lblDescrio_1);

		 textAreaAnotacao = new JTextArea();
		textAreaAnotacao.setBackground(new Color(51, 255, 204));
		textAreaAnotacao.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaAnotacao.setLineWrap(true);
		textAreaAnotacao.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textAreaAnotacao);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(43, 283, 548, 270);
		painelPrincipal.add(scrollPane);

		JLabel lblNewLabel_4 = new JLabel("     Nova Anotação");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		lblNewLabel_4.setBounds(93, 84, 147, 22);
		painelPrincipal.add(lblNewLabel_4);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String nome, descricao, texto;
				
				int notificar, lembrar, uni_tempo = 0, tempo_notificacao = 0, tipo;
				
				
				
				if(chckBoxNaoNotificar.isSelected()) {
					notificar = 0;
				}else {
					
				   if(rBMinutos.isSelected()) {
					   uni_tempo = 1;
				   }else if(rBHoras.isSelected()) {
					   uni_tempo = 2;
				   }else if(rBDias.isSelected()) {
					   uni_tempo = 3;
				   }
					
					tempo_notificacao = Integer.parseInt(entTempoNotificacao.getText()) ;
					
					notificar = 1;
				}
				
				nome = entNome.getText();
				descricao = entDescricao.getText();
				texto = textAreaAnotacao.getText();
				
				Date date_lembrete = null;
				LocalTime hora_lembrete = null;

				if(chkBoxSim.isSelected()){
					lembrar = 1;
					
					date_lembrete = data_selecionada.getTime(); 
					LocalDateTime data_completa = toLocalDateTime(data_selecionada);
					hora_lembrete = data_completa.toLocalTime();
					
		      	}else {
		      		lembrar = 0;
		      	}

				
				
			

				Date date_hoje = new Date();

				

				/*cBTipo.addItem("Anotação comum");
				cBTipo.addItem("Tópico Fixo");
				cBTipo.addItem("Tópico Fixo Broadcast");
				cBTipo.addItem("Lembrete");
*/                   
				tipo = cBTipo.getSelectedIndex() + 1;
				
			        CadastroNota nota = new CadastroNota();
			         nota.setData_nota(date_hoje);
			        nota.setData_lembrete(date_lembrete);
			        nota.setHora_lembrete(hora_lembrete);
			         nota.setNome(nome);
			         nota.setDescricao(descricao);
			         nota.setTexto(texto);
			         nota.setNotificar(notificar);
			         nota.setUni_tempo(uni_tempo);
			         nota.setTempo_notificacao(tempo_notificacao);
			         nota.setLembrar(lembrar);
			         nota.setTipo(tipo);
			         nota.setId_usuario_pai(login.getId());
			         
			         GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
			         int salvou = gerenciar.inserirnota(nota);
			         if(salvou > 0) {
			        	 JOptionPane.showMessageDialog(isto, "Anotação criada com sucesso!");
			        	 ((TelaNotas) telaPai).atualizarLista();
			        	 isto.dispose();
			         }else {
			        	 JOptionPane.showMessageDialog(isto, "Erro ao salvar anotação\nConsulte o administrador!");
			         }
			         
				
			}
		});
		btnSalvar.setBounds(1073, 565, 90, 28);
		painelPrincipal.add(btnSalvar);

		JLabel lblLembrete = new JLabel("Notificar me a cada:");
		lblLembrete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLembrete.setBounds(602, 134, 180, 25);
		painelPrincipal.add(lblLembrete);

		 chckBoxNaoNotificar = new JCheckBox("Não notificar");
		chckBoxNaoNotificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckBoxNaoNotificar.isSelected()) {
					chckBoxNaoNotificar.setSelected(true);
					painelNotificar.setEnabled(false);
					for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
						Component c = painelNotificar.getComponent(i);
						c.setEnabled(false);
					}
				} else {
					chckBoxNaoNotificar.setSelected(false);
					painelNotificar.setEnabled(true);
					for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
						Component c = painelNotificar.getComponent(i);
						c.setEnabled(true);
					}
				}

			}
		});
		chckBoxNaoNotificar.setSelected(true);
		chckBoxNaoNotificar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chckBoxNaoNotificar.setBounds(788, 88, 104, 18);
		painelPrincipal.add(chckBoxNaoNotificar);

		JLabel lblColas = new JLabel("Colas:");
		lblColas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblColas.setBounds(603, 291, 59, 25);
		painelPrincipal.add(lblColas);

		JButton btnNewButton_1 = new JButton("Pesquisa Informações");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaHome tela = new TelaHome(isto);
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(1010, 292, 153, 28);
		painelPrincipal.add(btnNewButton_1);

		JTextArea textAreaCola = new JTextArea();
		textAreaCola.setWrapStyleWord(true);
		textAreaCola.setLineWrap(true);
		textAreaCola.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaCola.setBackground(new Color(255, 255, 102));

		JScrollPane scrollPane_1 = new JScrollPane(textAreaCola);
		scrollPane_1.setBounds(603, 328, 560, 225);
		painelPrincipal.add(scrollPane_1);

		painelNotificar = new JPanel();
		painelNotificar.setEnabled(false);
		painelNotificar.setBounds(788, 119, 317, 40);
		painelPrincipal.add(painelNotificar);
		painelNotificar.setLayout(new MigLayout("", "[][][][]", "[]"));

		entTempoNotificacao = new JTextField();
		painelNotificar.add(entTempoNotificacao, "cell 0 0");
		entTempoNotificacao.setColumns(10);

		rBMinutos = new JRadioButton("Minutos");
		rBMinutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBMinutos.isSelected()) {
					rBMinutos.setSelected(true);
					rBHoras.setSelected(false);
					rBDias.setSelected(false);

				}
			}
		});
		rBMinutos.setSelected(true);
		painelNotificar.add(rBMinutos, "cell 1 0");

		rBHoras = new JRadioButton("Horas");
		rBHoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBHoras.isSelected()) {
					rBHoras.setSelected(true);
					rBMinutos.setSelected(false);
					rBDias.setSelected(false);

				}
			}
		});
		painelNotificar.add(rBHoras, "cell 2 0");

		rBDias = new JRadioButton("Dias");
		rBDias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rBDias.isSelected()) {
					rBDias.setSelected(true);
					rBHoras.setSelected(false);
					rBMinutos.setSelected(false);

				}
			}
		});
		painelNotificar.add(rBDias, "cell 3 0");

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipo.setBounds(612, 179, 46, 25);
		painelPrincipal.add(lblTipo);

		 cBTipo = new JComboBox();
		cBTipo.setBounds(664, 167, 165, 37);
		cBTipo.addItem("Anotação comum");
		cBTipo.addItem("Tópico Fixo");
		cBTipo.addItem("Tópico Fixo Broadcast");

		painelPrincipal.add(cBTipo);

		JLabel lblLembrete_1 = new JLabel("Lembrete:");
		lblLembrete_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLembrete_1.setBounds(835, 179, 104, 25);
		painelPrincipal.add(lblLembrete_1);

	    
		 chkBoxNao = new JCheckBox("Não");
		chkBoxNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChkBoxNaoLembrarSelecionado();
			}
		});
		chkBoxNao.setSelected(true);
		chkBoxNao.setBounds(939, 179, 45, 18);
		painelPrincipal.add(chkBoxNao);

		for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
			Component c = painelNotificar.getComponent(i);
			c.setEnabled(false);
		}

	
		     chkBoxSim = new JCheckBox("Sim");
		    chkBoxSim.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		
		    		setChkBoxSimLembrarSelecionado();
		    		
		    		
		    	}
		    });
		    chkBoxSim.setBounds(996, 179, 45, 18);
		    painelPrincipal.add(chkBoxSim);
		    
		    
		    btnDefinirTempo = new JButton("Definir Tempo");
		    btnDefinirTempo.setEnabled(false);
		    btnDefinirTempo.setVisible(false);
		    btnDefinirTempo.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		TelaDefinirTempo tempo = new TelaDefinirTempo(isto, data_selecionada);
		    		tempo.setTelaPai(isto);
		    		tempo.setVisible(true);
		    	}
		    });
		    btnDefinirTempo.setBounds(1053, 173, 106, 28);
		    painelPrincipal.add(btnDefinirTempo);
		    
		     lblDataLembrete = new JLabel("Lembrar em: 10/02/2021 as 14:20");
		     lblDataLembrete.setVisible(false);
		     lblDataLembrete.setEnabled(false);
		    lblDataLembrete.setFont(new Font("SansSerif", Font.BOLD, 12));
		    lblDataLembrete.setForeground(Color.WHITE);
		    lblDataLembrete.setBounds(837, 216, 326, 16);
		    painelPrincipal.add(lblDataLembrete);
		    
		    JButton btnAtualizar = new JButton("Atualizar");
		    btnAtualizar.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		
		    		String nome, descricao, texto;
					
					int notificar, lembrar, uni_tempo = 0, tempo_notificacao = 0, tipo;
					
					
					
					if(chckBoxNaoNotificar.isSelected()) {
						notificar = 0;
					}else {
						
					   if(rBMinutos.isSelected()) {
						   uni_tempo = 1;
					   }else if(rBHoras.isSelected()) {
						   uni_tempo = 2;
					   }else if(rBDias.isSelected()) {
						   uni_tempo = 3;
					   }
						
						tempo_notificacao = Integer.parseInt(entTempoNotificacao.getText()) ;
						
						notificar = 1;
					}
					
					nome = entNome.getText();
					descricao = entDescricao.getText();
					texto = textAreaAnotacao.getText();
					
					Date date_lembrete = null;
					LocalTime hora_lembrete = null;

					if(chkBoxSim.isSelected()){
						lembrar = 1;
						
						date_lembrete = data_selecionada.getTime(); 
						LocalDateTime data_completa = toLocalDateTime(data_selecionada);
						hora_lembrete = data_completa.toLocalTime();
						
			      	}else {
			      		lembrar = 0;
			      	}

					
					
				

					Date date_hoje = new Date();

					

					/*cBTipo.addItem("Anotação comum");
					cBTipo.addItem("Tópico Fixo");
					cBTipo.addItem("Tópico Fixo Broadcast");
					cBTipo.addItem("Lembrete");
	*/                   
					tipo = cBTipo.getSelectedIndex() + 1;
					
					nota_global.setData_lembrete(date_lembrete);
					nota_global.setHora_lembrete(hora_lembrete);
					nota_global.setNome(nome);
					nota_global.setDescricao(descricao);
					nota_global.setTexto(texto);
					nota_global.setNotificar(notificar);
					nota_global.setUni_tempo(uni_tempo);
					nota_global.setTempo_notificacao(tempo_notificacao);
					nota_global.setLembrar(lembrar);
					nota_global.setTipo(tipo);
					nota_global.setId_usuario_pai(login.getId());
				         
				      
		    		
		    		GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
		    		boolean atualizou = gerenciar.atualizarStatusnota(nota_global);
		    		if(atualizou) {
		    			JOptionPane.showMessageDialog(null, "Anotação atualizada");
			        	 ((TelaNotas) telaPai).atualizarLista();

		    			isto.dispose();
		    		}else {
		    			JOptionPane.showMessageDialog(null, "Erro ao atualizar a anotação\nConsulte o administrado!");
			        	 ((TelaNotas) telaPai).atualizarLista();

		    			isto.dispose();
		    		}
		    		
		    	}
		    });
		    btnAtualizar.setBounds(1073, 565, 90, 28);
		    painelPrincipal.add(btnAtualizar);
		    
		    if(flag_modo_operacao == 1) {
		    	btnAtualizar.setEnabled(false);
		    	btnAtualizar.setVisible(false);
		    	
		    }else {
		    	btnSalvar.setEnabled(false);
		    	btnSalvar.setVisible(false);
		    	rotinasEdicao();
		    }

		this.setLocationRelativeTo(janela_pai);

	}
	
	public void rotinasEdicao() {
		entNome.setText(nota_global.getNome());
		entDescricao.setText(nota_global.getDescricao());
		textAreaAnotacao.setText(nota_global.getTexto());
		
		
		//set tip
		cBTipo.setSelectedIndex(nota_global.getTipo() -1);
		
		
		//set notificar
		if(nota_global.getNotificar() == 1) {
			
				chckBoxNaoNotificar.setSelected(false);
				painelNotificar.setEnabled(true);
				for (int i = 0; i < painelNotificar.getComponentCount(); i++) {
					Component c = painelNotificar.getComponent(i);
					c.setEnabled(true);
				}
				
				if(nota_global.getUni_tempo() == 1) {
					rBMinutos.setSelected(true);
					rBHoras.setSelected(false);
					rBDias.setSelected(false);

				}else if(nota_global.getUni_tempo() == 2) {
					rBHoras.setSelected(true);
					rBMinutos.setSelected(false);
					rBDias.setSelected(false);

				}else {
					rBDias.setSelected(true);
					rBMinutos.setSelected(false);
					rBHoras.setSelected(false);

				}
				
				entTempoNotificacao.setText(Integer.toString(nota_global.getTempo_notificacao()));
			
		}
		
		
		
		
	}

	private class MyDateListener implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {
				System.out.println(c.getTime());
			} else {
				System.out.println("No time selected.");
			}
		}

	}

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public static LocalDateTime toLocalDateTime(Calendar calendar) {
	      if (calendar == null) {
	          return null;
	      }
	      TimeZone tz = calendar.getTimeZone();
	      ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
	      return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	  }
	
	public void setData(Calendar date) {
		String strDateLembrete = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		 strDateLembrete = dateFormat.format(date.getTime());
			lblDataLembrete.setText(strDateLembrete);

		data_selecionada = date;
	}
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
	public void setChkBoxSimLembrarSelecionado() {
		if(chkBoxSim.isSelected()) {
			chkBoxSim.setSelected(true);
			chkBoxNao.setSelected(false);
			
			btnDefinirTempo.setEnabled(true);
			btnDefinirTempo.setVisible(true);

			lblDataLembrete.setEnabled(true);
			lblDataLembrete.setVisible(true);

		}else {
			chkBoxNao.setSelected(true);
			chkBoxSim.setSelected(false);

			btnDefinirTempo.setEnabled(false);
			btnDefinirTempo.setVisible(false);

			lblDataLembrete.setEnabled(false);
			lblDataLembrete.setVisible(false);
		}
	}
	
	public void setChkBoxNaoLembrarSelecionado() {
		if(chkBoxNao.isSelected()) {
			chkBoxNao.setSelected(true);
			chkBoxSim.setSelected(false);

			btnDefinirTempo.setEnabled(false);
			btnDefinirTempo.setVisible(false);

			lblDataLembrete.setEnabled(false);
			lblDataLembrete.setVisible(false);

		}else {
			chkBoxSim.setSelected(true);
			chkBoxNao.setSelected(false);
			
			btnDefinirTempo.setEnabled(true);
			btnDefinirTempo.setVisible(true);

			lblDataLembrete.setEnabled(true);
			lblDataLembrete.setVisible(true);
		}
	}
	
}
