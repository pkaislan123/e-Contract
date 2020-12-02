package classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import cadastros.CadastroSafra;

public class ComboBoxRenderPersonalizado extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroSafra) {
	        	CadastroSafra safra = (CadastroSafra) value;
	            setText(safra.getCodigo() + "-" + safra.getProduto().getNome_produto() + " " + safra.getAno_plantio() + "/" + safra.getAno_colheita());
	        }
	        return this;
	    }


}
