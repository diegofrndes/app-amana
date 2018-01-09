package gui.swing.pessoa;

import gui.swing.componentes.ADialogAdmLogin;
import gui.swing.documentos.NumberFieldDocument;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import amana.Amana;

import dao.FabricaDao;
import dao.jdbc.JDBCPessoaDao;

import modelo.Pessoa;
import dao.FabricaConexao;

public class ADialogEscolherPessoaCodigo extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private Pessoa escolhida;
	private JLabel jCodLabel;
	private JTextField jCodTextField;
	private boolean chequeLimite;
	
	public ADialogEscolherPessoaCodigo(String title, String icone)
	{
		chequeLimite = true;
		escolhida = null;
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(icone)));
		//setLayout(new BorderLayout());
		initComponents(title);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				removeAll();
    				dispose();
                }
        });
        setResizable(false);
        setSize(160, 100);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	public ADialogEscolherPessoaCodigo(String title, String icone, boolean chequeLimite)
	{
		this.chequeLimite = chequeLimite; 
		escolhida = null;
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(icone)));
		//setLayout(new BorderLayout());
		initComponents(title);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				removeAll();
    				dispose();
                }
        });
        setResizable(false);
        setSize(160, 100);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}

	private void initComponents(String title){
		setLayout(null);
		jCodLabel = new JLabel("Código");
		jCodLabel.setBounds(20, 15, 120, 20);
		jCodTextField = new JTextField();
		jCodTextField.setDocument(new NumberFieldDocument(11));
		jCodTextField.setBounds(20, 35, 120, 20);
		jCodTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jCodTextField.getText().length() > 0){
					Pessoa temp = new JDBCPessoaDao().procurarPessoa(jCodTextField.getText());
					if(temp != null){
						BigDecimal debito = FabricaDao.getPessoaDao().debito(temp);
						if (!chequeLimite || (Amana.getUsuario().getTipoUsuario() == 1) || (debito.signum() == 1 || debito.signum() == 0)) {
							escolhida = temp; 
							setVisible(false);
				    		removeAll();
				    		dispose();
						} else {
							BigDecimal sub = temp.getLimite().add(debito);
							if (sub.signum() == -1 || sub.signum() == 0) {
								JOptionPane.showMessageDialog(null,
										"Limite de " + temp.getNome() + " ultrapassado.\nLimite: "
												+ temp.getLimite().toString()
												+ "\nDébito: " + debito.toString()
												+ "\n\n"
												+ "Para escolher o cliente com o limite ultrapassado\n"
												+ "digite, a seguir, o login e senha de administrador.\n", "Escolher Pessoa",
										JOptionPane.INFORMATION_MESSAGE);
								
								ADialogAdmLogin customDialog = new ADialogAdmLogin(null, null);
								customDialog.setSize(250, 150);
								customDialog.setVisible(true);
						
		                		if (customDialog.getValidAdmin()) {
		                			escolhida = temp; 
		    						setVisible(false);
		    			    		removeAll();
		    			    		dispose();
		                		} 				
							} else {
								escolhida = temp; 
								setVisible(false);
					    		removeAll();
					    		dispose();
							}
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Código Inexistente.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		add(jCodLabel);
		add(jCodTextField);
		
	}
	
	public Pessoa getEscolhida() {
		return escolhida;
	}
	
}
