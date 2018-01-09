package gui.swing.componentes;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;

import gui.swing.documentos.AmanaFieldDocument;;

public class ATextFieldSenha extends JPasswordField implements Validador {

	private static final long serialVersionUID = 1L;

	public ATextFieldSenha(){
		setDocument(new AmanaFieldDocument(20));
        this.setBackground(new Color(253, 254, 157));
        addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
                isValido();
            }
		});
		
		addFocusListener( new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
			   isValido();
            }
		});
	
	}
	
	@Override
	public boolean isValido() {
		if(getPassword().length >=4 && getPassword().length <= 20){
            setBackground(Color.WHITE);
            return true;
		}
        else {
        	setBackground(new Color(253, 254, 157));
        	return false;
        }
	}

}
