package gui.swing.componentes;

import gui.swing.pessoa.ADialogEscolherPessoa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import modelo.Pessoa;

public class APanelDadosItem extends JPanel implements Validador {

	private static final long serialVersionUID = 1L;
	private JLabel lblFornecedor;
	private ATextFieldNome jFornecedorTextField;

	private JButton btnEscolher;
	private JLabel lblNome;
	private ATextFieldNome jNomeTextField;
	private JLabel lblLimiteCritico;
	private JSpinner jLimiteCriticoSpinner;

	Pessoa fornecedor;
	
	public APanelDadosItem()
	{
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados do Item"));
		fornecedor = null;
		
		lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setBounds(20,15,100,20);
		
		jFornecedorTextField = new ATextFieldNome();
		jFornecedorTextField.setBounds(20, 35, 255, 20);
		jFornecedorTextField.setEditable(false);
		btnEscolher = new JButton("...");
		btnEscolher.setBounds(280, 35, 20, 20);
		btnEscolher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ADialogEscolherPessoa tela = new ADialogEscolherPessoa("Escolher Fornecedor");
				fornecedor = tela.getEscolhida();
				if(fornecedor != null){
					jFornecedorTextField.setText(fornecedor.getNome());
					jFornecedorTextField.isValido();
				}
			}
		});
		add(lblFornecedor);
		add(jFornecedorTextField);
		add(btnEscolher);
		
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(20,55,50,20);
		jNomeTextField = new ATextFieldNome();
		jNomeTextField.setBounds(20, 75, 135, 20);
		add(lblNome);
		add(jNomeTextField);
		
		lblLimiteCritico = new JLabel("Limite Crítico");
		lblLimiteCritico.setBounds(165, 55, 135, 20);
		jLimiteCriticoSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10000000, 1));
		jLimiteCriticoSpinner.setBounds(165, 75, 135, 20);
		add(lblLimiteCritico);
		add(jLimiteCriticoSpinner);
		
	}

	@Override
	public boolean isValido() {
		return jNomeTextField.isValido() && fornecedor != null;
	}
	
	public String getNome()
	{
		return jNomeTextField.getText();
	}
	
	public int getLimiteCritico()
	{
		return Integer.parseInt(jLimiteCriticoSpinner.getValue().toString());
	}
	
	public Pessoa getFornecedor(){
		return fornecedor;
	}
	
	public void setNome(String nome){
		jNomeTextField.setText(nome);
		jNomeTextField.setEditable(false);
		jNomeTextField.isValido();
	}
	
	public void setLimiteCritico(int limite){
		jLimiteCriticoSpinner.setValue(limite);
	}
	
	public void setFornecedor(Pessoa fornecedor){
		this.fornecedor = fornecedor;
		if(fornecedor != null){
			jFornecedorTextField.setText(fornecedor.getNome());
			jFornecedorTextField.isValido();
		}
		
	}
}
