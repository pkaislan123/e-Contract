package main.java.manipular;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class CriarArquivoAnotacao {

	private XWPFDocument document_global;

	public void criarDocumento() {
		document_global = new XWPFDocument();
		CTSectPr sectPr = document_global.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setLeft(BigInteger.valueOf(720L));
		pageMar.setTop(BigInteger.valueOf(1440L));
		pageMar.setRight(BigInteger.valueOf(720L));
		pageMar.setBottom(BigInteger.valueOf(1440L));

		document_global.createStyles();

	}

	public boolean criarArquivo(String caminh_completo_salvar_no_hd) {
		criarDocumento();
		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);

		// criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleRun = title.createRun();
		titleRun.setText("LD ARMAZÉNS GERAIS LTDA\r\n" + "CNPJ: 24.986.679/0001-06\r\n" + "");
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(12);

		try {
			CTP ctP = CTP.Factory.newInstance();

			// header text
			CTText t = ctP.addNewR().addNewT();

			XWPFParagraph cabecalho = new XWPFParagraph(ctP, document_global);
			XWPFRun cabecalhoRun = cabecalho.createRun();
			cabecalhoRun.setFontSize(16);
			cabecalhoRun.setFontFamily("Arial Black");
			cabecalhoRun.setText("LD ARMAZÉNS GERAIS");
			cabecalhoRun.setUnderline(UnderlinePatterns.SINGLE);
			cabecalhoRun.setColor("00A000");

			XWPFParagraph pars[] = new XWPFParagraph[1];

			pars[0] = cabecalho;

			pars[0].setAlignment(ParagraphAlignment.LEFT);

			XWPFHeaderFooterPolicy hfPolicy = document_global.createHeaderFooterPolicy();
			hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

			ctP = CTP.Factory.newInstance();
			t = ctP.addNewR().addNewT();

			// footer text
			t.setStringValue("Anotação");

			pars[0] = new XWPFParagraph(ctP, document_global);
			hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do sistema!");
			e.printStackTrace();
		}

		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		try {
			document_global.write(new FileOutputStream(caminh_completo_salvar_no_hd));
			document_global.write(saida_apos_edicao);
			document_global.close();
			return true;
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

}
