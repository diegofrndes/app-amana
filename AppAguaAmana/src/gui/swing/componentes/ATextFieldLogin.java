package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.JTextField;

public class ATextFieldLogin extends JTextField implements Validador {
	
	private static final long serialVersionUID = 1L;

	public ATextFieldLogin(){
		setDocument(new AmanaFieldDocument(30));
        this.setBackground(new Color(253, 254, 157));
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				isValido();
            }
			
		});
		
		addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				isValido();
            }
		});
	}
	
	@Override
	public boolean isValido() {
		if(getText().length() >= 4 && getText().length() <= 30){
		    setBackground(Color.WHITE);
            return true;
		} else{
			setBackground(new Color(253, 254, 157));
			return false;
		}
	}
	
}
