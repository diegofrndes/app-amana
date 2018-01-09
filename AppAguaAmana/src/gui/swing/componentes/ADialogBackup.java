package gui.swing.componentes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import dao.FabricaConexao;

public class ADialogBackup extends JDialog {

	private JProgressBar bar;

	public ADialogBackup(String diretorio, String arquivo){
		setTitle("Backup Amana ");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
            	public void windowClosing(WindowEvent we) {
            		setVisible(false);
		    		removeAll();
					dispose();
            	}
    	});
    	setResizable(false);
    	setSize(326, 140);

    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	//setModal(true);
    	setLayout(new GridBagLayout());
    	
    	bar = new JProgressBar();
    	bar.setIndeterminate(true);
    	add(bar);
   
    	Thread worker = new ThreadBackup(diretorio, arquivo, this,  bar);
        worker.start();
   
        setVisible(true);
    	
    }
}
