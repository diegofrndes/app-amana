package gui.swing.componentes;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class APanelDadosPessoaFisica extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblNome;
	private ATextFieldNome jNomeTextField;
	private JLabel lblCpf;
	private ATextFieldCpf jCpfTextField;
	private JLabel lblIdentidade;
	private ATextFieldRg jIdentidadeTextField;
	private JLabel lblUfRg;
	private JComboBox jUfRgComboBox;
	
	public APanelDadosPessoaFisica(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados Pessoais"));
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(20,15,50,20);
		jNomeTextField = new ATextFieldNome();
		jNomeTextField.setBounds(20, 35, 280, 20);
		add(lblNome);
		add(jNomeTextField);
		
		lblCpf = new JLabel("CPF");
		lblCpf.setBounds(20, 55, 50, 20);
		jCpfTextField = new ATextFieldCpf();
		jCpfTextField.setBounds(20, 75, 100, 20);
		add(lblCpf);
		add(jCpfTextField);
		
		lblIdentidade = new JLabel("RG");
		lblIdentidade.setBounds(140, 55, 80, 20);
		jIdentidadeTextField = new ATextFieldRg();
		jIdentidadeTextField.setBounds(140, 75, 100, 20);
		add(lblIdentidade);
		add(jIdentidadeTextField);
		
		lblUfRg = new JLabel("UF");
		lblUfRg.setBounds(260, 55, 40, 20);
		jUfRgComboBox = new JComboBox();
		jUfRgComboBox.setModel(new javax.swing.DefaultComboBoxModel(
						new String[]{"AC", "AL", "AP", "AM", "BH", "CE", 
						"DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR", 
						"PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO",
						"RR", "SC", "SE", "SP", "TO"}));
		jUfRgComboBox.setSelectedItem("RN");
		jUfRgComboBox.setBounds(260, 75, 40, 20);
		add(lblUfRg);
		add(jUfRgComboBox);	
	}
	
	@Override
	public boolean isValido() {
		if( jNomeTextField.isValido() && jCpfTextField.isValido() && jIdentidadeTextField.isValido())
			return true;
		else return false;
	}
	
	public void setNome(String nome){
		jNomeTextField.setText(nome);
		jNomeTextField.isValido();
	}
	
	public void setCpf(String cpf){
		jCpfTextField.setText(cpf);
		jCpfTextField.isValido();
	}
	
	public void setIdentidade(String rg){
		jIdentidadeTextField.setText(rg); 
		jIdentidadeTextField.isValido();
	}
	
	public void setUfRg(String ufrg){
		jUfRgComboBox.setSelectedItem(ufrg);	
	}
	
	public void setVisualizarNome(String nome){
		jNomeTextField.setText(nome);
		jNomeTextField.setEditable(false);
		jNomeTextField.setBackground(Color.WHITE);
	}
	
	public void setVisualizarCpf(String cpf){
		jCpfTextField.setText(cpf);
		jCpfTextField.setEditable(false);
		jCpfTextField.setBackground(Color.WHITE);
	}
	
	public void setVisualizarIdentidade(String rg){
		jIdentidadeTextField.setText(rg); 
		jIdentidadeTextField.setEditable(false);
		jIdentidadeTextField.setBackground(Color.WHITE);
	}
	
	public void setVisualizarUfRg(String UfRg){
		jUfRgComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[]{UfRg}));
		jUfRgComboBox.setSelectedItem(UfRg);
		
		jUfRgComboBox.setEditable(false);
	
		
	}
	
	public String getNome(){
		return jNomeTextField.getText();
	}
	
	public String getCpf(){
		String cpf = jCpfTextField.getText();
		cpf = cpf.replace(".", "");
        cpf = cpf.replace("-","");
        cpf = cpf.replace(" ", "");
        return cpf;
	}
	
	public String getIdentidade(){
		return jIdentidadeTextField.getText();
	}
	
	public String getUfRg(){
		return jUfRgComboBox.getSelectedItem().toString();
	}
	
}
