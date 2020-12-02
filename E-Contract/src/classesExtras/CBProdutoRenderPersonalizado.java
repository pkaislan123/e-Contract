package classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import cadastros.CadastroCliente;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;

public class CBProdutoRenderPersonalizado extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroProduto) {
	        	CadastroProduto produto = (CadastroProduto) value;
	            setText(produto.getId_produto() + "-" +  produto.getNome_produto());
	        }
	        return this;
	    }


}
