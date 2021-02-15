package classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import cadastros.Contato;

public class ComboBoxContato extends AbstractListModel<Contato> implements ComboBoxModel<Contato> {
    private ArrayList<Contato> lista_contatos = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private Contato contato_selecionado;
    
@Override
public int getSize() {
     return lista_contatos.size();
}

@Override
public Contato getElementAt(int indice) {
	// TODO Auto-generated method stub
	Contato t = lista_contatos.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return contato_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	contato_selecionado = (Contato) item;	
}


public void addContato(Contato contato) {
	lista_contatos.add(contato);
    //fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    //setSelectedItem(lista_safras.get(getSize() - 1));
}

public void resetar() {
	lista_contatos.clear();
}

}
