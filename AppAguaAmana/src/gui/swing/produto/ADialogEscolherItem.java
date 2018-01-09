package gui.swing.produto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import modelo.Item;

public class ADialogEscolherItem extends JDialog {
		
	private static final long serialVersionUID = 1L;
	private APanelEscolherItem painelItemEscolher;
	private Item escolhido;
	private int quantidade = 1;
		
	public ADialogEscolherItem(){
		setEscolhido(null);
		setTitle("Escolher Item De Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		//initComponents();
		setLayout(new BorderLayout());
		painelItemEscolher = new APanelEscolherItem(this);
		add(painelItemEscolher, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
	    		removeAll();
	  			dispose();
	        }
	    });
	    setResizable(false);
	    setSize(1000, 600);
	       
	    Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
	    Dimension dw = getSize();  
	    setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
	    setModal(true);
	    setVisible(true);
	}

	public Item getEscolhido() {
		return escolhido;
	}
		
	public void setEscolhido(Item escolhido) {
		this.escolhido = escolhido;
	}
		
	public int getQuantidade() {
		return quantidade;
	}
		
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
		
}


