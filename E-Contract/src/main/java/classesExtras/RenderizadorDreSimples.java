package main.java.classesExtras;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import keeptoo.KGradientPanel;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import net.miginfocom.swing.MigLayout;

public class RenderizadorDreSimples implements ListCellRenderer<DreSimples> {

	@Override
	public Component getListCellRendererComponent(JList<? extends DreSimples> arg0, DreSimples dre, int arg2,
			boolean arg3, boolean arg4) {

		Locale ptBr = new Locale("pt", "BR");

		KGradientPanel painelPrincipal = new KGradientPanel();
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = new Color(255, 255, 255);
		painelPrincipal.setBackground(new Color(153, 153, 102));

		painelPrincipal.setLayout(new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
		
		JLabel lblMes = new JLabel("     FEVEREIRO     ",SwingConstants.CENTER);
		lblMes.setBorder(null);
		lblMes.setOpaque(true);
		lblMes.setBackground(new Color(0, 102, 153));
		lblMes.setForeground(Color.WHITE);
		lblMes.setFont(new Font("Arial", Font.BOLD, 18));
		painelPrincipal.add(lblMes, "cell 0 0,grow");
		
		JLabel lblSaldoInicial = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblSaldoInicial.setBorder(null);
		lblSaldoInicial.setOpaque(true);
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.BOLD, 18));
		lblSaldoInicial.setBackground(Color.WHITE);
		painelPrincipal.add(lblSaldoInicial, "cell 1 0,grow");
		
		JLabel lblReceitas = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblReceitas.setBorder(null);
		lblReceitas.setOpaque(true);
		lblReceitas.setForeground(Color.BLACK);
		lblReceitas.setFont(new Font("Arial", Font.BOLD, 18));
		lblReceitas.setBackground(new Color(204, 255, 153));
		painelPrincipal.add(lblReceitas, "cell 2 0,grow");
		
		JLabel lblDespesas = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblDespesas.setBorder(null);
		lblDespesas.setOpaque(true);
		lblDespesas.setForeground(Color.BLACK);
		lblDespesas.setFont(new Font("Arial", Font.BOLD, 18));
		lblDespesas.setBackground(new Color(255, 102, 102));
		painelPrincipal.add(lblDespesas, "cell 3 0,grow");
		
		JLabel lblTotal = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblTotal.setBorder(null);
		lblTotal.setOpaque(true);
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotal.setBackground(Color.WHITE);
		painelPrincipal.add(lblTotal, "cell 4 0,grow");
		
		JLabel lblLucro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblLucro.setBorder(null);
		lblLucro.setOpaque(true);
		lblLucro.setForeground(Color.BLACK);
		lblLucro.setFont(new Font("Arial", Font.BOLD, 18));
		lblLucro.setBackground(Color.WHITE);
		painelPrincipal.add(lblLucro, "cell 5 0,grow");
		
		JLabel lblLucratividade = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
		lblLucratividade.setBorder(null);
		lblLucratividade.setOpaque(true);
		lblLucratividade.setForeground(Color.BLACK);
		lblLucratividade.setFont(new Font("Arial", Font.BOLD, 18));
		lblLucratividade.setBackground(Color.WHITE);
		painelPrincipal.add(lblLucratividade, "cell 6 0,grow");





/************************************/


		int mes = dre.getMes();
		if (mes == 1) {
			lblMes.setText("     JANEIRO     ");
		} else if (mes == 2) {
			lblMes.setText("     FEVEREIRO     ");

		} else if (mes == 3) {
			lblMes.setText("     MARÇO     ");

		} else if (mes == 4) {
			lblMes.setText("     ABRIL     ");

		} else if (mes == 5) {
			lblMes.setText("     MAIO     ");

		} else if (mes == 6) {
			lblMes.setText("     JUNHO     ");

		} else if (mes == 7) {
			lblMes.setText("     JULHO     ");

		} else if (mes == 8) {
			lblMes.setText("     AGOSTO     ");

		} else if (mes == 9) {
			lblMes.setText("     SETEMBRO     ");

		} else if (mes == 10) {
			lblMes.setText("     OUTUBRO     ");

		} else if (mes == 11) {
			lblMes.setText("     NOVEMBRO     ");

		} else if (mes == 12) {
			lblMes.setText("     DEZEMBRO     ");

		}

		double saldo_inicial = dre.getSaldo_inicial(), despesa = dre.getDespesas(), receita = dre.getReceitas(),
				 total = dre.getTotal(),lucro = dre.getLucro() ;


		lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial));
		lblReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(receita));
		lblDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesa));
		lblTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(total));

		lblLucro.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro));
		 DecimalFormat df = new DecimalFormat("#,###.00");
		 lblLucratividade.setText(df.format(dre.getLucratividade()) + "%");

	
		
		

		return painelPrincipal;
	}
}
