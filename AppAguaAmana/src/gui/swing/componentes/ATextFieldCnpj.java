package gui.swing.componentes;

import gui.swing.documentos.Formatos;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;


/**
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public class ATextFieldCnpj extends JFormattedTextField implements Validador {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ATextFieldCnpj(){
        super();
        this.setBackground(new Color(253, 254, 157));
        this.setFormatterFactory(Formatos.setFormatoCnpj());
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
        String cnpj = this.getText();
        cnpj = cnpj.replace(" ", "");
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");
   
    	if(cnpj.length() == 14) {
        	setBackground(Color.WHITE);
        	return true;
        } else {
        	setBackground(new Color(253, 254, 157));
        	return false;
        }
    }
}
