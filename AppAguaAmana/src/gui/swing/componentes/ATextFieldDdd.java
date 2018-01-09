package gui.swing.componentes;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.JFormattedTextField;

import gui.swing.documentos.Formatos;

public class ATextFieldDdd extends JFormattedTextField implements Validador {

	private static final long serialVersionUID = 1L;

	public ATextFieldDdd(){
        super();
        this.setBackground(new Color(253, 254, 157));
        this.setFormatterFactory(Formatos.setFormatoDdd());
        
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
        String ddd = this.getText();
        ddd = ddd.replace(" ", "");
        ddd = ddd.replace("(", "");
        ddd = ddd.replace(")", "");
    	if(ddd.length() == 2)
    	{
         	setBackground(Color.WHITE);
        	return true;
        } else {
        	setBackground(new Color(253, 254, 157));
        	return false;
        }
    }
}