package main.java.classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import main.java.cadastros.CadastroProduto;



public class CBProdutoPersonalizado  extends AbstractListModel<CadastroProduto> implements ComboBoxModel<CadastroProduto> {
    private ArrayList<CadastroProduto> listaProdutos = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroProduto produto_selecionado;
    
@Override
public int getSize() {
     return listaProdutos.size();
}

@Override
public CadastroProduto getElementAt(int indice) {
	// TODO Auto-generated method stub
	CadastroProduto t = listaProdutos.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return produto_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	produto_selecionado = (CadastroProduto) item;	
}


public void addProduto(CadastroProduto produto) {
	listaProdutos.add(produto);
    fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    setSelectedItem(listaProdutos.get(getSize() - 1));
}

}
