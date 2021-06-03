
package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroClassificador;



public class ComboBoxRenderPersonalizadoClassificador extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroClassificador) {
	        	CadastroClassificador cc = (CadastroClassificador) value;
	            setText(cc.getNome_colaborador());
	        }
	        return this;
	    }


}
