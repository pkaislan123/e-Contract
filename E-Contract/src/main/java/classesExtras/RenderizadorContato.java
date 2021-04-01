package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.Contato;





public class RenderizadorContato extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof Contato) {
	        	Contato contato = (Contato) value;
	            
	            setText(contato.getNome() + " " + contato.getCargo() + " " + contato.getCelular());
	        }
	        return this;
	    }


}
