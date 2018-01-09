package gui.swing.produto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import modelo.Produto;

public class ADialogEscolherProduto extends JDialog {

	private static final long serialVersionUID = 1L;
	private APanelEscolherProduto painelEscolherProduto;
	private Produto escolhido;
	private int quantidade = 1;
		
	public ADialogEscolherProduto(){
		setEscolhido(null);
		setTitle("Adicionar Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		//initComponents();
		setLayout(new BorderLayout());
		painelEscolherProduto = new APanelEscolherProduto(this);
		add(painelEscolherProduto, BorderLayout.CENTER);
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

	public Produto getEscolhido() {
		return escolhido;
	}
		
	public void setEscolhido(Produto escolhido) {
		this.escolhido = escolhido;
	}
		
	public int getQuantidade() {
		return quantidade;
	}
		
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
