package gui.swing.componentes;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class APanelDadosPessoaJuridica extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblNome;
	private ATextFieldNome jNomeTextField;
	private JLabel lblCpf;
	private ATextFieldCnpj jCnpjTextField;
	private JLabel lblIe;
	private ATextFieldIe jIeTextField;
	
	public APanelDadosPessoaJuridica(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados Pessoais"));
		
		lblNome = new JLabel("Firma");
		lblNome.setBounds(20,15,50,20);
		jNomeTextField = new ATextFieldNome();
		jNomeTextField.setBounds(20, 35, 280, 20);
		add(lblNome);
		add(jNomeTextField);
		
		lblCpf = new JLabel("CNPJ");
		lblCpf.setBounds(20, 55, 50, 20);
		jCnpjTextField = new ATextFieldCnpj();
		jCnpjTextField.setBounds(20, 75, 135, 20);
		add(lblCpf);
		add(jCnpjTextField);
		
		lblIe = new JLabel("Inscrição Estadual");
		lblIe.setBounds(165, 55, 135, 20);
		jIeTextField = new ATextFieldIe();
		jIeTextField.setBounds(165, 75, 135, 20);
		add(lblIe);
		add(jIeTextField);
		
	}
	
	@Override
	public boolean isValido() {
		if( jNomeTextField.isValido() && jCnpjTextField.isValido() && jIeTextField.isValido())
			return true;
		else return false;
	}
	
	public void setNome(String nome){
		jNomeTextField.setText(nome);
		jNomeTextField.isValido();
	}
	
	public void setCnpj(String cnpj){
		jCnpjTextField.setText(cnpj);
		jCnpjTextField.isValido();
	}
	
	public void setIe(String ie){
		jIeTextField.setText(ie); 
		jIeTextField.isValido();
	}
	
	public void setVisualizarNome(String nome){
		jNomeTextField.setText(nome);
		jNomeTextField.setEditable(false);
		jNomeTextField.setBackground(Color.WHITE);
	}
	
	public void setVisualizarCnpj(String cnpj){
		jCnpjTextField.setText(cnpj);
		jCnpjTextField.setEditable(false);
		jCnpjTextField.setBackground(Color.WHITE);
	}
	
	public void setVisualizarIe(String ie){
		jIeTextField.setText(ie); 
		jIeTextField.setEditable(false);
		jIeTextField.setBackground(Color.WHITE);
	}
	
	public String getNome(){
		return jNomeTextField.getText();
	}
	
	public String getCnpj(){
		String cnpj = jCnpjTextField.getText();
		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("-","");
		cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace(" ", "");
        return cnpj;
	}
	
	public String getIe(){
		return jIeTextField.getText();
	}
	
}
