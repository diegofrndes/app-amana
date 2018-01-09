package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class APanelInformacoes extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblEmail;
	private JTextField jEmailTextField;
	private JLabel lblObs;
	private JTextField jObsTextField;
	
	public APanelInformacoes()
	{
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Informações"));
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(20, 15, 50, 20);
		jEmailTextField = new JTextField();
		jEmailTextField.setDocument(new AmanaFieldDocument(45));	
		jEmailTextField.setBounds(20, 35, 280, 20);
		add(lblEmail);
		add(jEmailTextField);
		
		lblObs = new JLabel("Observação");
		lblObs.setBounds(20, 55, 100, 20);
		jObsTextField = new JTextField();
		jObsTextField.setDocument(new AmanaFieldDocument(150));
		jObsTextField.setBounds(20, 75, 280, 20);
		
		add(lblObs);
		add(jObsTextField);
			
	}
	
	public String getEmail()
	{
		return jEmailTextField.getText();
	}
	
	public String getObs()
	{
		return jObsTextField.getText();
	}
	
	public void setVisualizarEmail(String email){
		this.jEmailTextField.setEditable(false);
		this.jEmailTextField.setText(email);
	}
	
	public void setVisualizarObs(String obs){
		this.jObsTextField.setEditable(false);
		this.jObsTextField.setText(obs);
	}
	
	public void setEmail(String email){
		this.jEmailTextField.setText(email);
	}
	
	public void setObs(String obs){
		this.jObsTextField.setText(obs);
	}
	
}
