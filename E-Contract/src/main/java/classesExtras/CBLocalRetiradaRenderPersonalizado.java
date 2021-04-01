package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroCliente;



public class CBLocalRetiradaRenderPersonalizado extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroCliente) {
	        	CadastroCliente armazem = (CadastroCliente) value;
	            setText(armazem.getNome_fantaia());
	        }
	        return this;
	    }


}
