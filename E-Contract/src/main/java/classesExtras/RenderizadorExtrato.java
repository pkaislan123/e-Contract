package main.java.classesExtras;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import keeptoo.KGradientPanel;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.outros.TratarDados;
import net.miginfocom.swing.MigLayout;


import javax.swing.JTextArea;

public class RenderizadorExtrato implements ListCellRenderer<FinanceiroPagamentoCompleto> {
	

	
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends FinanceiroPagamentoCompleto> arg0, FinanceiroPagamentoCompleto pag_completo, int arg2, boolean arg3,
			boolean arg4) {
		
		String chara = "";
		
		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));
		painelPrincipal.setLayout(new MigLayout("", "[:400px:400px][][grow]", "[][][][][][][grow][:50px:50px][][]"));

		painelPrincipal.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		JLabel lblTipoLancamento = new JLabel("");
		lblTipoLancamento.setOpaque(true);
		lblTipoLancamento.setBackground(new Color(204, 0, 0));
		lblTipoLancamento.setForeground(Color.WHITE);
		lblTipoLancamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblTipoLancamento, "cell 0 0 3 1,growx");

		
		
		if(pag_completo.getLancamento().getTipo_lancamento() == 0) {
			//despesa
			lblTipoLancamento.setBackground(new Color(204, 0, 0));
			lblTipoLancamento.setText("DESPESA id: " + pag_completo.getLancamento().getId_lancamento());
			chara = "-";
			
		}else if(pag_completo.getLancamento().getTipo_lancamento() == 1) {
			lblTipoLancamento.setBackground(new Color(0, 51, 0));
			lblTipoLancamento.setText("RECEITA id: " + pag_completo.getLancamento().getId_lancamento());
			chara = "+";
			

		}else if(pag_completo.getLancamento().getTipo_lancamento() == 3) {
			lblTipoLancamento.setBackground(new Color(0, 51, 0));
			lblTipoLancamento.setText("RECEITA DE EMPRÉSTIMO id: " + pag_completo.getLancamento().getId_lancamento());
			chara = "+";

		}
		


		JLabel lblIdentificador = new JLabel("Identificador");
		lblIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblIdentificador, "cell 0 1");
		lblIdentificador.setText("Identificador: " + pag_completo.getFpag().getIdentificador());

		
		JLabel lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblData, "flowx,cell 0 2");
		lblData.setText("");
		lblData.setText("Data: " + pag_completo.getFpag().getData_pagamento());

		JLabel lblFormaPagamento = new JLabel("Forma Pagamento");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFormaPagamento, "cell 0 3");
		lblFormaPagamento.setText(pag_completo.getNome_forma_pagamento());
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 0 4");
		
		int id_status = pag_completo.getFpag().getStatus_pagamento();
		String status = "";
		if(id_status == 0) {
			//cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
			//cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
			status = ("A - Compensar|Realizar|Concluir;");
		}else if(id_status == 1) {
			status += ("Compensado|Realizado|Concluído;");

		}
		lblStatus.setText(status);
		
		JLabel lblNewLabel_2 = new JLabel("Valor:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_2, "cell 1 2,alignx left");
		
		JLabel lblValor = new JLabel("");
		lblValor.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblValor, "cell 2 2,alignx left");
		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(pag_completo.getFpag().getValor());
		lblValor.setText(chara + valorString);
		
		
		
		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setOpaque(false);
		textAreaDescricao.setBackground(Color.WHITE);
		textAreaDescricao.setBorder(null);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setForeground(Color.black);
		painelPrincipal.add(textAreaDescricao, "cell 0 3 1 3,grow");
		textAreaDescricao.setText(pag_completo.getFpag().getDescricao());
		

		JLabel lblNewLabel_4 = new JLabel("Saldo:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_4, "cell 1 7,alignx right");

		JLabel lblSaldo = new JLabel("");
		lblSaldo.setForeground(Color.BLACK);
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblSaldo, "cell 2 7,alignx left");
		lblSaldo.setText(NumberFormat.getCurrencyInstance(ptBr).format(pag_completo.getSaldo_atual()));
	

	
		
		return painelPrincipal;

	
	}
	


}


