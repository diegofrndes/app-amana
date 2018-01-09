package gui.swing.componentes;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;

import gui.swing.documentos.Formatos;

public class ATextFieldTelefone extends JFormattedTextField implements Validador {

	private static final long serialVersionUID = 1L;

	public ATextFieldTelefone(){
        super();
        this.setBackground(new Color(253, 254, 157));
        this.setFormatterFactory(Formatos.setFormatoTel());
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
        String telefone = this.getText();
        telefone = telefone.replace(" ", "");
        telefone = telefone.replace("-", "");
    	if(telefone.length() == 8){
        	this.setBackground(Color.WHITE);
        	return true;
        } else {
        	this.setBackground(new Color(253, 254, 157));	
        	return false;
        }
    }
}