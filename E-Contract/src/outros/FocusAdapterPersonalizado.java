package outros;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FocusAdapterPersonalizado extends FocusAdapter {

	private JTextFieldPersonalizado caixa_texto;
	
	public FocusAdapterPersonalizado(Component component) {
		caixa_texto = (JTextFieldPersonalizado) component;
	}

	
	

	
}
