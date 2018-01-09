/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.swing.documentos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Diego
 */
public class NumberFieldDocument extends PlainDocument{
    
	private static final long serialVersionUID = 1L;
	private int iMaxLength;
  
    public NumberFieldDocument(int maxlen) {
        super();
        iMaxLength = maxlen;
    }
 
    public void insertString(int offset, String str, AttributeSet attr)
    throws BadLocationException {
       
        if (iMaxLength <= 0)        // aceitara qualquer no. de caracteres
        {
            super.insertString(offset, str, attr);
            return;
        }
   
        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength){    // se o comprimento final for menor...
            for( int i = 0; i < str.length(); i++ )
                if( Character.isDigit( str.charAt( i ) ) == false )
                    return;
            super.insertString( offset, str, attr );
        }
        else
        {
            if (getLength() == iMaxLength) return; // nada a fazer
            String newStr = str.substring(0, (iMaxLength - getLength()));
            super.insertString(offset, newStr,(javax.swing.text.AttributeSet) attr);
        }
   }
}
