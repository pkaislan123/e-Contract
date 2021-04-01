package main.java.classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import main.java.cadastros.CadastroCliente;


public class CBLocalRetiradaPersonalizado  extends AbstractListModel<CadastroCliente> implements ComboBoxModel<CadastroCliente> {
    private ArrayList<CadastroCliente> listaArmazens = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroCliente armazem_selecionado;
    
@Override
public int getSize() {
     return listaArmazens.size();
}



@Override
public CadastroCliente getElementAt(int indice) {
	// TODO Auto-generated method stub
	CadastroCliente t = listaArmazens.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return armazem_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	armazem_selecionado = (CadastroCliente) item;	
}


public void addArmazem(CadastroCliente armazem) {
	listaArmazens.add(armazem);
 
}

}
