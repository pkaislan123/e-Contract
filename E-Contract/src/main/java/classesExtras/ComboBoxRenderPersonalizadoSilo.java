

package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroSilo;



public class ComboBoxRenderPersonalizadoSilo extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroSilo) {
	        	CadastroSilo cc = (CadastroSilo) value;
	            setText(cc.getNome_silo() + " - " + cc.getDescricao());
	        }
	        return this;
	    }


}
