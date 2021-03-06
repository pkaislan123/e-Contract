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
import main.java.cadastros.Lancamento;
import main.java.outros.TratarDados;
import net.miginfocom.swing.MigLayout;

import javax.swing.JTextArea;

public class RenderizadorExtrato implements ListCellRenderer<FinanceiroPagamentoCompleto> {

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends FinanceiroPagamentoCompleto> arg0,
			FinanceiroPagamentoCompleto pag_completo, int arg2, boolean arg3, boolean arg4) {

		String chara = "";

		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));
		painelPrincipal
				.setLayout(new MigLayout("", "[:400px:400px][][grow]", "[][][][][][][][grow][:50px:50px][][][]"));
		painelPrincipal.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		JLabel lblTipoLancamento = new JLabel("");
		lblTipoLancamento.setOpaque(true);
		lblTipoLancamento.setBackground(new Color(0, 51, 0));
		lblTipoLancamento.setForeground(Color.WHITE);
		lblTipoLancamento.setFont(new Font("Tahoma", Font.BOLD, 20));
		painelPrincipal.add(lblTipoLancamento, "cell 0 0 3 1,growx");

		if (pag_completo.getLancamento().getTipo_lancamento() == 0) {
			// despesa
			lblTipoLancamento.setBackground(new Color(204, 0, 0));
			lblTipoLancamento.setText("DESPESA id: " + pag_completo.getLancamento().getId_lancamento());
			chara = "-";

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 1) {
			lblTipoLancamento.setBackground(new Color(0, 51, 0));
			lblTipoLancamento.setText("RECEITA id: " + pag_completo.getLancamento().getId_lancamento());
			chara = "+";

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 2) {
			lblTipoLancamento.setBackground(new Color(51, 0, 255));

			if (pag_completo.getFpag().getId_pagador() == pag_completo.getId_caixa()) {

				// essa caixa e o pagador
				chara = "-";
				lblTipoLancamento
						.setText("TRANSFERÊNCIA NEGATIVA id: " + pag_completo.getLancamento().getId_lancamento());

			} else if (pag_completo.getFpag().getId_recebedor() == pag_completo.getLancamento()
					.getId_cliente_fornecedor()) {

				lblTipoLancamento
						.setText("TRANSFERÊNCIA POSITIVA id: " + pag_completo.getLancamento().getId_lancamento());

				chara = "+";

			}

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 3) {
			
			
			if(pag_completo.getFpag().getTipo_pagamento() == 1) {
				lblTipoLancamento.setBackground(new Color(204, 0, 0));
				lblTipoLancamento.setText("DESPESA DE EMPRÉSTIMO id: " + pag_completo.getLancamento().getId_lancamento());
				chara = "-";
			}else {
				lblTipoLancamento.setBackground(new Color(0, 51, 0));
				lblTipoLancamento.setText("RECEITA DE EMPRÉSTIMO id: " + pag_completo.getLancamento().getId_lancamento());
				chara = "+";
			}
			
		

		}

		JLabel lblPagamentoId = new JLabel("");
		lblPagamentoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblPagamentoId, "cell 0 1");
		lblPagamentoId.setText("Pagamento ID: " + pag_completo.getFpag().getId_pagamento());

		JLabel lblIdentificador = new JLabel("Identificador");
		lblIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblIdentificador, "cell 0 1");
		lblIdentificador.setText("Identificador: " + pag_completo.getFpag().getIdentificador());

		JLabel lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblData, "flowx,cell 0 2");
		lblData.setText("00/00/000");
		lblData.setText("Data: " + pag_completo.getFpag().getData_pagamento());

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

		JLabel lblFormaPagamento = new JLabel("Forma Pagamento");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFormaPagamento, "cell 0 3");
		lblFormaPagamento.setText(pag_completo.getNome_forma_pagamento());

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 0 4");
		int id_status = pag_completo.getFpag().getStatus_pagamento();
		String status = "";
		if (id_status == 0) {
			// cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
			// cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
			status = ("A - Compensar|Realizar|Concluir;");
		} else if (id_status == 1) {
			status += ("Compensado|Realizado|Concluído;");

		}
		lblStatus.setText(status);

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setOpaque(false);
		textAreaDescricao.setBackground(Color.WHITE);
		textAreaDescricao.setBorder(null);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		painelPrincipal.add(textAreaDescricao, "cell 0 5 1 3,grow");
		textAreaDescricao.setText(pag_completo.getFpag().getDescricao());

		JLabel lblPagador = new JLabel("Pagador:");
		lblPagador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblPagador, "cell 0 8");
		if (pag_completo.getLancamento().getTipo_lancamento() == 0) {
			// despesa
			lblPagador.setText("Quem recebeu: " + pag_completo.getNome_recebedor());

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 1) {
			// receita
			lblPagador.setText("Quem pagou: " + pag_completo.getNome_pagador());

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 2) {
			// receita

			if (pag_completo.getFpag().getId_pagador() == pag_completo.getId_caixa()) {

				lblPagador.setText("Destinatário: " + pag_completo.getNome_recebedor());

			} else if (pag_completo.getFpag().getId_recebedor() == pag_completo.getLancamento()
					.getId_cliente_fornecedor()) {

				lblPagador.setText("Remetente: " + pag_completo.getNome_pagador());

			}

		} else if (pag_completo.getLancamento().getTipo_lancamento() == 3) {
			// emprestimo
			
			if(pag_completo.getFpag().getTipo_pagamento() == 1) {
				//pag de parcela de emprestimo
				lblPagador.setText("Quem Recebeu: " + pag_completo.getNome_recebedor());

			}else {
				lblPagador.setText("Quem pagou: " + pag_completo.getNome_pagador());

			}
			

		}

		JButton btnAbrirLancamento = new JButton("Abrir Lançamento");
		btnAbrirLancamento.setForeground(Color.WHITE);
		btnAbrirLancamento.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnAbrirLancamento, "cell 0 11,alignx center");

		JLabel lblNewLabel_4 = new JLabel("Saldo:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_4, "cell 1 10,alignx left");

		JLabel lblSaldo = new JLabel("R$ 100.000.000,00");
		lblSaldo.setForeground(Color.BLACK);
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblSaldo, "cell 2 10,alignx left");
		lblSaldo.setText(NumberFormat.getCurrencyInstance(ptBr).format(pag_completo.getSaldo_atual()));

		return painelPrincipal;

	}

}
