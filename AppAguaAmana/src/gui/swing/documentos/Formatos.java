/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.swing.documentos;

import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Diego
 */
public class Formatos {
    
    public static DefaultFormatterFactory setFormatoPlaca() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("UUU-####");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoIe() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##.###.###-#");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoCnpj() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##.###.###/####-##");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoCpf() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("###.###.###-##");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoIdentidade() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("#######");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoCep() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("#####-###");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoDdd() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("(##)");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    
    public static DefaultFormatterFactory setFormatoTel() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("####-####");
        } catch (ParseException pe) { }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
}
