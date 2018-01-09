package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JTextField;

public class ATextFieldNome extends JTextField implements Validador {

	private static final long serialVersionUID = 1L;

	public ATextFieldNome(){
        super();
        this.setBackground(new Color(253, 254, 157));
        this.setDocument(new AmanaFieldDocument(60));
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
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
    
    public boolean isValido() {
        if(this.getText().length() >= 2 && this.getText().length() <= 60){
        	setBackground(Color.WHITE);
        	return true;
        } else {
        	setBackground(new Color(253, 254, 157));
        	return false;
        }
    }

}
