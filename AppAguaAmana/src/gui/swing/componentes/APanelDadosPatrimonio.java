package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Color;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class APanelDadosPatrimonio extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblDesc;
    private JLabel lblFabricante;
    private JLabel lblNome;
    private JLabel lblModelo;
    private JLabel lblNumero;
    private JLabel lblFreq;
    private JTextField txtDesc;
    private JTextField txtFabricante;
    private JTextField txtModelo;
    private ATextFieldNome txtNome;
    private ATextFieldValor txtValor;
    private JSpinner jQuant;

	public APanelDadosPatrimonio(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados do Bem Patrimonial"));
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(20,15,50,20);
		txtNome = new ATextFieldNome();
		txtNome.setBounds(20, 35, 280, 20);
		add(lblNome);
		add(txtNome);
		
		lblDesc = new JLabel("Descrição");
		lblDesc.setBounds(20,55,50,20);
		txtDesc = new JTextField();
		txtDesc.setBounds(20, 75, 280, 20);
		txtDesc.setDocument(new AmanaFieldDocument(60));
		add(lblDesc);
		add(txtDesc);
		
		lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(20,95,60,20);
		txtFabricante = new JTextField();
		txtFabricante.setDocument(new AmanaFieldDocument(45));
		txtFabricante.setBounds(20, 115, 135, 20);
		add(lblFabricante);
		add(txtFabricante);
		
		lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(165,95,60,20);
		txtModelo = new JTextField();
		txtModelo.setBounds(165, 115, 135, 20);
		txtModelo.setDocument(new AmanaFieldDocument(45));
		add(lblModelo);
		add(txtModelo);
		
		lblNumero = new JLabel("Valor (R$)");
		lblNumero.setBounds(20,135,100,20);
		txtValor = new ATextFieldValor();
		txtValor.setBounds(20, 155, 135, 20);
		add(lblNumero);
		add(txtValor);
		
		lblFreq = new JLabel("Quantidade");
		lblFreq.setBounds(165,135,135,20);
		lblFreq.setToolTipText("Meses");
		int min = 0;
		int max = 1000000;
		int step = 1;
		int initValue = 1;
		jQuant = new JSpinner(new SpinnerNumberModel(initValue, min, max, step));
		jQuant.setToolTipText("Meses");
		jQuant.setBounds(165, 155, 135, 20);
		add(lblFreq);
		add(jQuant);
	}
	
	public void setVisualizarNome(String nome){
		txtNome.setText(nome);
		txtNome.setEditable(false);
		txtNome.setBackground(Color.WHITE);
	}
	
	public void setVisualizarFabricante(String fabricante){
		txtFabricante.setText(fabricante);
		txtFabricante.setEditable(false);
		txtFabricante.setBackground(Color.WHITE);
		
	}
	
	public void setVisualizarModelo(String modelo){
		txtModelo.setText(modelo);
		txtModelo.setEditable(false);
		txtModelo.setBackground(Color.WHITE);
	}
	
	public void setVisualizarValor(String valor){
		txtValor.setText(valor);
		txtValor.setEditable(false);
		txtValor.setBackground(Color.WHITE);
	}
	
	public void setVisualizarDescricao(String desc){
		txtDesc.setText(desc);
		txtDesc.setEditable(false);
		txtDesc.setBackground(Color.WHITE);
	}
	
	public void setVisualizarQuantidade(int quant){
		jQuant.setModel(new SpinnerNumberModel(quant, quant, quant, 0));
	}
	
	public void setNome(String nome){
		txtNome.setText(nome);
		txtNome.isValido();
	}
	
	public void setFabricante(String fabricante){
		txtFabricante.setText(fabricante);		
	}
	
	public void setModelo(String modelo){
		txtModelo.setText(modelo);
	}
	
	public void setValor(String valor){
		txtValor.setText(valor);
		//txtValor.isValido();
		//txtValor.setEditable(false);
	}
	
	public void setDescricao(String desc){
		txtDesc.setText(desc);
	}
	
	public void setQuantidade(int freq){
		jQuant.setModel(new SpinnerNumberModel(freq, 0, 1000000, 1));
	}
	
	public String getNome(){
		return txtNome.getText();
	}
	
	public String getFabricante(){
		return txtFabricante.getText();
	}
	
	public String getModelo(){
		return txtModelo.getText();
	}
	
	public BigDecimal getValor(){
		String aux = txtValor.getText();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		BigDecimal valor = new BigDecimal(aux);
		return valor;
	}
	
	public String getDescricao(){
		return txtDesc.getText();
	}
	
	public int getQuantidade(){
		return Integer.parseInt(jQuant.getValue().toString());
	}

	@Override
	public boolean isValido() {
		return txtNome.isValido();
	}
	
}
