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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import keeptoo.KGradientPanel;
import main.java.cadastros.DreAgrupado;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import net.miginfocom.swing.MigLayout;

public class RenderizadorDreAgrupado implements ListCellRenderer<DreAgrupado> {

	@Override
	public Component getListCellRendererComponent(JList<? extends DreAgrupado> arg0, DreAgrupado dre, int arg2,
			boolean arg3, boolean arg4) {

		Locale ptBr = new Locale("pt", "BR");

		JPanel painelPrincipal = new JPanel();

		if (dre.getFlag() == -1) {
			painelPrincipal.setBackground(Color.WHITE);

			painelPrincipal.setLayout(new MigLayout("", "[grow]", "[grow]"));

			JLabel lblNewLabel = new JLabel("-> RECEITAS");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setOpaque(true);
			lblNewLabel.setBackground(new Color(0, 51, 0));
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			painelPrincipal.add(lblNewLabel, "cell 0 0,grow");
		} else if (dre.getFlag() == -2) {
			painelPrincipal.setBackground(Color.WHITE);

			painelPrincipal.setLayout(new MigLayout("", "[grow]", "[grow]"));

			JLabel lblNewLabel = new JLabel("-> DESPESAS");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setOpaque(true);
			lblNewLabel.setBackground(new Color(255, 102, 51));
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			painelPrincipal.add(lblNewLabel, "cell 0 0,grow");

		} else if (dre.getFlag() == 0) {

			painelPrincipal.setBackground(Color.WHITE);

			painelPrincipal.setLayout(new MigLayout("",
					"[200px:200px:200px][250px:250px:250px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px]",
					"[]"));

			JLabel lblGrupoContas = new JLabel("VENDA DE PRODUTOS AGRICOLAS", SwingConstants.CENTER);
			lblGrupoContas.setBorder(null);
			lblGrupoContas.setOpaque(true);
			lblGrupoContas.setBackground(Color.WHITE);
			lblGrupoContas.setForeground(Color.BLACK);
			lblGrupoContas.setFont(new Font("Arial", Font.BOLD, 11));
			painelPrincipal.add(lblGrupoContas, "cell 0 0,grow");

			JLabel lblConta = new JLabel("ENERGIA - INST. 3006018507 - BOMBA", SwingConstants.CENTER);
			lblConta.setBorder(null);
			lblConta.setOpaque(true);
			lblConta.setForeground(Color.BLACK);
			lblConta.setFont(new Font("Arial", Font.BOLD, 11));
			lblConta.setBackground(Color.WHITE);
			painelPrincipal.add(lblConta, "cell 1 0,grow");

			JLabel lblJaneiro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJaneiro.setBorder(null);
			lblJaneiro.setOpaque(true);
			lblJaneiro.setForeground(Color.BLACK);
			lblJaneiro.setFont(new Font("Arial", Font.BOLD, 12));
			lblJaneiro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJaneiro, "cell 2 0,grow");

			JLabel lblFevereiro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblFevereiro.setBorder(null);
			lblFevereiro.setOpaque(true);
			lblFevereiro.setForeground(Color.BLACK);
			lblFevereiro.setFont(new Font("Arial", Font.BOLD, 12));
			lblFevereiro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblFevereiro, "cell 3 0,grow");

			JLabel lblMarco = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblMarco.setBorder(null);
			lblMarco.setOpaque(true);
			lblMarco.setForeground(Color.BLACK);
			lblMarco.setFont(new Font("Arial", Font.BOLD, 12));
			lblMarco.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblMarco, "cell 4 0,grow");

			JLabel lblAbril = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblAbril.setBorder(null);
			lblAbril.setOpaque(true);
			lblAbril.setForeground(Color.BLACK);
			lblAbril.setFont(new Font("Arial", Font.BOLD, 12));
			lblAbril.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblAbril, "cell 5 0,grow");

			JLabel lblMaio = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblMaio.setBorder(null);
			lblMaio.setOpaque(true);
			lblMaio.setForeground(Color.BLACK);
			lblMaio.setFont(new Font("Arial", Font.BOLD, 12));
			lblMaio.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblMaio, "cell 6 0,grow");

			JLabel lblJunho = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJunho.setOpaque(true);
			lblJunho.setForeground(Color.BLACK);
			lblJunho.setFont(new Font("Arial", Font.BOLD, 12));
			lblJunho.setBorder(null);
			lblJunho.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJunho, "cell 7 0,grow");

			JLabel lblJulho = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJulho.setOpaque(true);
			lblJulho.setForeground(Color.BLACK);
			lblJulho.setFont(new Font("Arial", Font.BOLD, 12));
			lblJulho.setBorder(null);
			lblJulho.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJulho, "cell 8 0,grow");

			JLabel lblAgosto = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblAgosto.setOpaque(true);
			lblAgosto.setForeground(Color.BLACK);
			lblAgosto.setFont(new Font("Arial", Font.BOLD, 12));
			lblAgosto.setBorder(null);
			lblAgosto.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblAgosto, "cell 9 0,grow");

			JLabel lblSetembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblSetembro.setOpaque(true);
			lblSetembro.setForeground(Color.BLACK);
			lblSetembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblSetembro.setBorder(null);
			lblSetembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblSetembro, "cell 10 0,grow");

			JLabel lblOutubro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblOutubro.setOpaque(true);
			lblOutubro.setForeground(Color.BLACK);
			lblOutubro.setFont(new Font("Arial", Font.BOLD, 12));
			lblOutubro.setBorder(null);
			lblOutubro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblOutubro, "cell 11 0,grow");

			JLabel lblNovembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblNovembro.setOpaque(true);
			lblNovembro.setForeground(Color.BLACK);
			lblNovembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblNovembro.setBorder(null);
			lblNovembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblNovembro, "cell 12 0,grow");

			JLabel lblDezembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblDezembro.setOpaque(true);
			lblDezembro.setForeground(Color.BLACK);
			lblDezembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblDezembro.setBorder(null);
			lblDezembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblDezembro, "cell 13 0,grow");

			
			JLabel lblTotal = new JLabel("R$ 0.0", SwingConstants.CENTER);
			lblTotal.setOpaque(true);
			lblTotal.setForeground(Color.BLACK);
			lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
			lblTotal.setBorder(null);
			lblTotal.setBackground(Color.WHITE);
			painelPrincipal.add(lblTotal, "cell 14 0,grow");
			
			
			lblGrupoContas.setText(dre.getNome_grupo_contas());
			lblConta.setText(dre.getNome_conta());

			lblJaneiro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_janeiro()));

			lblFevereiro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_fevereiro()));

			lblMarco.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_marco()));

			lblAbril.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_abril()));

			lblMaio.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_maio()));

			lblJunho.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_junho()));

			lblJulho.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_julho()));

			lblAgosto.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_agosto()));

			lblSetembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_setembro()));

			lblOutubro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_outubro()));

			lblNovembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_novembro()));

			lblDezembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_despesas_dezembro()));
			
			
			double total = dre.getValor_despesas_janeiro() + dre.getValor_despesas_fevereiro() + dre.getValor_despesas_marco()
			+ dre.getValor_despesas_abril() + dre.getValor_despesas_maio() + dre.getValor_despesas_junho()
			+ dre.getValor_despesas_julho() + dre.getValor_despesas_agosto() + dre.getValor_despesas_setembro()
			+ dre.getValor_despesas_outubro() + dre.getValor_despesas_novembro() + dre.getValor_despesas_dezembro();
			
			lblTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(total));
		} else if (dre.getFlag() == 1) {

			painelPrincipal.setBackground(Color.WHITE);

			painelPrincipal.setLayout(new MigLayout("", "[200px:200px:200px][250px:250px:250px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px]", "[]"));
			
			JLabel lblGrupoContas = new JLabel("VENDA DE PRODUTOS AGRICOLAS",SwingConstants.CENTER);
			lblGrupoContas.setBorder(null);
			lblGrupoContas.setOpaque(true);
			lblGrupoContas.setBackground(Color.WHITE);
			lblGrupoContas.setForeground(Color.BLACK);
			lblGrupoContas.setFont(new Font("Arial", Font.BOLD, 11));
			painelPrincipal.add(lblGrupoContas, "cell 0 0,grow");
			
			JLabel lblConta = new JLabel("ENERGIA - INST. 3006018507 - BOMBA", SwingConstants.CENTER);
			lblConta.setBorder(null);
			lblConta.setOpaque(true);
			lblConta.setForeground(Color.BLACK);
			lblConta.setFont(new Font("Arial", Font.BOLD, 11));
			lblConta.setBackground(Color.WHITE);
			painelPrincipal.add(lblConta, "cell 1 0,grow");
			
			JLabel lblJaneiro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJaneiro.setBorder(null);
			lblJaneiro.setOpaque(true);
			lblJaneiro.setForeground(Color.BLACK);
			lblJaneiro.setFont(new Font("Arial", Font.BOLD, 12));
			lblJaneiro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJaneiro, "cell 2 0,grow");
			
			JLabel lblFevereiro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblFevereiro.setBorder(null);
			lblFevereiro.setOpaque(true);
			lblFevereiro.setForeground(Color.BLACK);
			lblFevereiro.setFont(new Font("Arial", Font.BOLD, 12));
			lblFevereiro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblFevereiro, "cell 3 0,grow");
			
			JLabel lblMarco = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblMarco.setBorder(null);
			lblMarco.setOpaque(true);
			lblMarco.setForeground(Color.BLACK);
			lblMarco.setFont(new Font("Arial", Font.BOLD, 12));
			lblMarco.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblMarco, "cell 4 0,grow");
			
			JLabel lblAbril = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblAbril.setBorder(null);
			lblAbril.setOpaque(true);
			lblAbril.setForeground(Color.BLACK);
			lblAbril.setFont(new Font("Arial", Font.BOLD, 12));
			lblAbril.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblAbril, "cell 5 0,grow");
			
			JLabel lblMaio = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblMaio.setBorder(null);
			lblMaio.setOpaque(true);
			lblMaio.setForeground(Color.BLACK);
			lblMaio.setFont(new Font("Arial", Font.BOLD, 12));
			lblMaio.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblMaio, "cell 6 0,grow");
			
			JLabel lblJunho = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJunho.setOpaque(true);
			lblJunho.setForeground(Color.BLACK);
			lblJunho.setFont(new Font("Arial", Font.BOLD, 12));
			lblJunho.setBorder(null);
			lblJunho.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJunho, "cell 7 0,grow");
			
			JLabel lblJulho = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblJulho.setOpaque(true);
			lblJulho.setForeground(Color.BLACK);
			lblJulho.setFont(new Font("Arial", Font.BOLD, 12));
			lblJulho.setBorder(null);
			lblJulho.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblJulho, "cell 8 0,grow");
			
			JLabel lblAgosto = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblAgosto.setOpaque(true);
			lblAgosto.setForeground(Color.BLACK);
			lblAgosto.setFont(new Font("Arial", Font.BOLD, 12));
			lblAgosto.setBorder(null);
			lblAgosto.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblAgosto, "cell 9 0,grow");
			
			JLabel lblSetembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblSetembro.setOpaque(true);
			lblSetembro.setForeground(Color.BLACK);
			lblSetembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblSetembro.setBorder(null);
			lblSetembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblSetembro, "cell 10 0,grow");
			
			JLabel lblOutubro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblOutubro.setOpaque(true);
			lblOutubro.setForeground(Color.BLACK);
			lblOutubro.setFont(new Font("Arial", Font.BOLD, 12));
			lblOutubro.setBorder(null);
			lblOutubro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblOutubro, "cell 11 0,grow");
			
			JLabel lblNovembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblNovembro.setOpaque(true);
			lblNovembro.setForeground(Color.BLACK);
			lblNovembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblNovembro.setBorder(null);
			lblNovembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblNovembro, "cell 12 0,grow");
			
			JLabel lblDezembro = new JLabel("R$ 99.999.999,00", SwingConstants.CENTER);
			lblDezembro.setOpaque(true);
			lblDezembro.setForeground(Color.BLACK);
			lblDezembro.setFont(new Font("Arial", Font.BOLD, 12));
			lblDezembro.setBorder(null);
			lblDezembro.setBackground(new Color(204, 255, 204));
			painelPrincipal.add(lblDezembro, "cell 13 0,grow");

			JLabel lblTotal = new JLabel("R$ 0.0", SwingConstants.CENTER);
			lblTotal.setOpaque(true);
			lblTotal.setForeground(Color.BLACK);
			lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
			lblTotal.setBorder(null);
			lblTotal.setBackground(Color.WHITE);
			painelPrincipal.add(lblTotal, "cell 14 0,grow");
			
			
			
			lblGrupoContas.setText(dre.getNome_grupo_contas());
			lblConta.setText(dre.getNome_conta());

			lblJaneiro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_janeiro()));

			lblFevereiro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_fevereiro()));

			lblMarco.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_marco()));

			lblAbril.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_abril()));

			lblMaio.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_maio()));

			lblJunho.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_junho()));

			lblJulho.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_julho()));

			lblAgosto.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_agosto()));

			lblSetembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_setembro()));

			lblOutubro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_outubro()));

			lblNovembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_novembro()));

			lblDezembro.setText(NumberFormat.getCurrencyInstance(ptBr).format(dre.getValor_receitas_dezembro()));
			
			double total = dre.getValor_receitas_janeiro() + dre.getValor_receitas_fevereiro() + dre.getValor_receitas_marco()
			+ dre.getValor_receitas_abril() + dre.getValor_receitas_maio() + dre.getValor_receitas_junho()
			+ dre.getValor_receitas_julho() + dre.getValor_receitas_agosto() + dre.getValor_receitas_setembro()
			+ dre.getValor_receitas_outubro() + dre.getValor_receitas_novembro() + dre.getValor_receitas_dezembro();
			 
			
			lblTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(total));

		}

		return painelPrincipal;
	}
}
