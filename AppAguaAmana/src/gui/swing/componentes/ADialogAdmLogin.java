package gui.swing.componentes;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;

import modelo.Usuario;
import dao.FabricaConexao;
import dao.FabricaDao;

import java.beans.*; //property change stuff
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.*;
import java.awt.event.*;

/* 1.4 example used by DialogDemo.java. */
public class ADialogAdmLogin extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    
	private static final long serialVersionUID = 1L;
	private boolean valid = false;
	
	private JTextField loginField;
    private JPasswordField textField;
    //private Component dd;

    private JOptionPane optionPane;

    private String btnString1 = "Confirmar";
    private String btnString2 = "Cancelar";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public boolean getValidAdmin() {
        return valid;
    }

    /** Creates the reusable dialog. */
    public ADialogAdmLogin(JFrame aFrame, Component parent) {
        super(aFrame, true);
        //dd = parent;

        setTitle("Permissão de Administrador");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/16x16/usuario.png")));
        setLocationRelativeTo(null);
        loginField = new JTextField(50);
        textField = new JPasswordField(20);

        //Create an array of the text and components to be displayed.
        String msgString1 = "Login:";
        String msgString2 = "Senha:";
        
        //String msgString2 = "(The answer is \"" + magicWord
        //                      + "\".)";
        Object[] array = {msgString1, loginField,msgString2, textField};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);
        //Make this dialog display it.
        setContentPane(optionPane);
        //optionPane.setPreferredSize(new Dimension(600,500));
        setResizable(false);
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                loginField.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
        textField.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                Usuario usuario = FabricaDao.getUsuarioDao().fazerLogin(loginField.getText(), new String(getSenha()));
            	if (usuario != null && usuario.getTipoUsuario() == 1) {
                    //we're done; clear and dismiss the dialog
                    valid = true;
                	clearAndHide();
                } else {
                    //text was invalid
                	loginField.selectAll();
        			JOptionPane.showMessageDialog(ADialogAdmLogin.this, 
        					"Usuário e/ou senha incorreto(s).", 
        					"Tente Novamente", 
        					JOptionPane.ERROR_MESSAGE);	

                    valid = false;
                    loginField.requestFocusInWindow();
                }
            } else { //user closed dialog or clicked cancel
                //dd.setLabel("It's OK.  "
                //         + "We won't force you to type "
                //         + magicWord + ".");
                valid = false;
                clearAndHide();
            }
        }
    }

    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        textField.setText(null);
        setVisible(false);
    }
    
    public String getSenha() {
    	try {
    		String senha = new String(this.textField.getPassword());
        	MessageDigest digest;
    		digest = MessageDigest.getInstance("MD5");
    		digest.update(senha.getBytes());
            Base64 encoder = new Base64();
            senha = encoder.encodeAsString(digest.digest());
            return senha;
        
    	} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível encriptar a senha.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		return "";
    	}
     }

}