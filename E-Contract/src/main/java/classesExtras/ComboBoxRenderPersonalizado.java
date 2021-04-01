package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.conexaoBanco.GerenciarBancoProdutos;



public class ComboBoxRenderPersonalizado extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroSafra) {
	        	
	        	CadastroSafra safra = (CadastroSafra) value;
	            GerenciarBancoProdutos gerenciar = new GerenciarBancoProdutos();
	            CadastroProduto prod = gerenciar.getProduto(safra.getProduto().getId_produto());
	            
	            setText(safra.getCodigo() + "-" + safra.getProduto().getNome_produto() + " TRANSGENIA: " + prod.getTransgenia() + " " + safra.getAno_plantio() + "/" + safra.getAno_colheita());
	        }
	        return this;
	    }


}
