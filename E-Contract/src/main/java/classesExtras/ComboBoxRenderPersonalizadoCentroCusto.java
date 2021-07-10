package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroClassificador;
import main.java.cadastros.CentroCusto;



public class ComboBoxRenderPersonalizadoCentroCusto extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CentroCusto) {
	        	CentroCusto cc = (CentroCusto) value;
	            setText(cc.getNome_centro_custo());
	        }
	        return this;
	    }


}
