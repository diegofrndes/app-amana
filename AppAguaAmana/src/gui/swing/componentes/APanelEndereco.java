package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Endereco;

import gui.swing.documentos.Formatos;

public class APanelEndereco extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblBairro;
	private JTextField jBairroTextField;
	private JLabel lblRua;
	private JTextField jRuaTextField;
	private JLabel lblUf;
	private JComboBox jUfComboBox;
	private JLabel lblNumero;
	private JTextField jNumeroTextField;
	private JLabel lblCidade;
	private JTextField jCidadeTextField;
	private JLabel lblCep;
	private JFormattedTextField jCepTextField;
	private JLabel lblDdd;
	private ATextFieldDdd jDddTextField;
	private JLabel lblTelefone;
	private ATextFieldTelefone jTelefoneTextField;
	
	public APanelEndereco(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Endereço"));
		
		lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(20, 15, 50, 20);
		jBairroTextField = new JTextField();
		jBairroTextField.setDocument(new AmanaFieldDocument(60));
		jBairroTextField.setBounds(20, 35, 280, 20);
		add(lblBairro);
		add(jBairroTextField);
		
		lblRua = new JLabel("Rua");
		lblRua.setBounds(20, 55, 50, 20);
		jRuaTextField = new JTextField();
		jRuaTextField.setDocument(new AmanaFieldDocument(60));
		jRuaTextField.setBounds(20, 75, 280, 20);
		add(lblRua);
		add(jRuaTextField);
		
		lblNumero = new JLabel("Número");
		lblNumero.setBounds(20,95,50,20);
		jNumeroTextField = new JTextField();
		jNumeroTextField.setDocument(new AmanaFieldDocument(8));
		jNumeroTextField.setBounds(20, 115, 100, 20);
		add(lblNumero);
		add(jNumeroTextField);
		
		lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(140,95,50,20);
		jCidadeTextField = new JTextField();
		jCidadeTextField.setDocument(new AmanaFieldDocument(30));
		jCidadeTextField.setBounds(140, 115, 100, 20);
		add(lblCidade);
		add(jCidadeTextField);
		
		lblUf = new JLabel("UF");
		lblUf.setBounds(260, 95, 40, 20);
		jUfComboBox = new JComboBox();
		jUfComboBox.setModel(new javax.swing.DefaultComboBoxModel(
						new String[]{"AC", "AL", "AP", "AM", "BH", "CE", 
						"DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR", 
						"PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO",
						"RR", "SC", "SE", "SP", "TO"}));
		jUfComboBox.setSelectedItem("RN");
		jUfComboBox.setBounds(260, 115, 40, 20);
		add(lblUf);
		add(jUfComboBox);
		
		lblCep = new JLabel("CEP");
		lblCep.setBounds(20, 135, 50, 20);
		jCepTextField = new JFormattedTextField(Formatos.setFormatoCep());
		jCepTextField.setBounds(20, 155, 100 ,20);
		add(lblCep);
		add(jCepTextField);
		
		lblDdd = new JLabel("DDD");
		lblDdd.setBounds(140, 135, 50, 20);
		jDddTextField = new ATextFieldDdd();
		jDddTextField.setBounds(140, 155, 40, 20);
		add(lblDdd);
		add(jDddTextField);
		
		lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(200, 135, 50, 20);
		jTelefoneTextField = new ATextFieldTelefone();
		jTelefoneTextField.setBounds(200, 155, 100, 20);
		add(lblTelefone);
		add(jTelefoneTextField);
		
	}
	
	public void setEndereco(Endereco endereco){
		jBairroTextField.setText(endereco.getBairro());
		jCepTextField.setText(endereco.getCep());
		jCidadeTextField.setText(endereco.getCidade());
		jDddTextField.setText(endereco.getDdd());
		jDddTextField.isValido();
		jNumeroTextField.setText(endereco.getNumero());
		jRuaTextField.setText(endereco.getRua());
		jTelefoneTextField.setText(endereco.getTelefone());
		jTelefoneTextField.setBackground(Color.WHITE);
		jTelefoneTextField.isValido();
		jUfComboBox.setSelectedItem(endereco.getUf());
	
	}
	
	public void setVisualizarEndereco(Endereco endereco){
		jBairroTextField.setText(endereco.getBairro());
		jBairroTextField.setEditable(false);
		jBairroTextField.setBackground(Color.WHITE);
		jCepTextField.setText(endereco.getCep());
		jCepTextField.setEditable(false);
		jCepTextField.setBackground(Color.WHITE);
		jCidadeTextField.setText(endereco.getCidade());
		jCidadeTextField.setEditable(false);
		jCidadeTextField.setBackground(Color.WHITE);
		jDddTextField.setText(endereco.getDdd());
		jDddTextField.setEditable(false);
		jDddTextField.setBackground(Color.WHITE);
		jNumeroTextField.setText(endereco.getNumero());
		jNumeroTextField.setEditable(false);
		jNumeroTextField.setBackground(Color.WHITE);
		jRuaTextField.setText(endereco.getRua());
		jRuaTextField.setEditable(false);
		jRuaTextField.setBackground(Color.WHITE);
		jTelefoneTextField.setText(endereco.getTelefone());
		jTelefoneTextField.setEditable(false);
		jTelefoneTextField.setBackground(Color.WHITE);
		jUfComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[]{endereco.getUf()}));
		jUfComboBox.setSelectedItem(endereco.getUf());
		jUfComboBox.setEditable(false);
	}
	
	public Endereco getEndereco(){
		String ddd = this.jDddTextField.getText();
        ddd = ddd.replace("(", "");
        ddd = ddd.replace(")", "");
        ddd = ddd.replace(" ", "");
        String telefone = this.jTelefoneTextField.getText();
        telefone = telefone.replace("-", "");
        telefone = telefone.replace(" ", "");
        String cep = jCepTextField.getText().replace("-", "");
        cep = cep.replace(" ", "");
		Endereco endereco = new Endereco(
				this.jBairroTextField.getText(),
				this.jRuaTextField.getText(),
				this.jNumeroTextField.getText(),
				ddd,
				telefone,
				cep,
				this.jUfComboBox.getSelectedItem().toString(),
				this.jCidadeTextField.getText());
		return endereco;
	}

	@Override
	public boolean isValido() {
		return( jDddTextField.isValido() && jTelefoneTextField.isValido() );
	}

}
