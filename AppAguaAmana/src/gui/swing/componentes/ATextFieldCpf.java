package gui.swing.componentes;

import gui.swing.documentos.Formatos;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;


public class ATextFieldCpf extends JFormattedTextField implements Validador {

	private static final long serialVersionUID = 1L;

	public ATextFieldCpf(){
        super();
        this.setBackground(new Color(253, 254, 157));
        this.setFormatterFactory(Formatos.setFormatoCpf());
        this.setFocusLostBehavior(JFormattedTextField.COMMIT);
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
        String cpf = this.getText();
        cpf = cpf.replace(" ", "");
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
    	if(cpf.length() == 11) {
        	setBackground(Color.WHITE);
        	return true;
        } else {
        	setBackground(new Color(253, 254, 157));
        	return false;
        }
    }
}
