package gui.swing.pessoa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import modelo.Pessoa;


public class ADialogEscolherPessoa extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private APanelEscolherPessoa painelClienteEscolher;
	private Pessoa escolhida;
	
	public ADialogEscolherPessoa(String title)
	{
		setEscolhida(null);
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		setLayout(new BorderLayout());
		painelClienteEscolher = new APanelEscolherPessoa(this, true);
		add(painelClienteEscolher, BorderLayout.CENTER);
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
	
	public ADialogEscolherPessoa(String title, boolean chequeLimite)
	{
		setEscolhida(null);
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		setLayout(new BorderLayout());
		painelClienteEscolher = new APanelEscolherPessoa(this, chequeLimite);
		add(painelClienteEscolher, BorderLayout.CENTER);
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

	public Pessoa getEscolhida() {
		return escolhida;
	}

	public void setEscolhida(Pessoa escolhida) {
		this.escolhida = escolhida;
	}
	
}
