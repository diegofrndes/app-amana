package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class APanelDadosEquipamento extends JPanel implements Validador {
	
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
    private JTextField txtNome;
    private ATextFieldLogin txtNumero;
    private JSpinner jFreq;

	public APanelDadosEquipamento(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados do Equipamento"));
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(20,15,50,20);
		txtNome = new JTextField();
		txtNome.setDocument(new AmanaFieldDocument(60));
		txtNome.setBounds(20, 35, 280, 20);
		add(lblNome);
		add(txtNome);
		
		lblDesc = new JLabel("Descrição");
		lblDesc.setBounds(20,55,50,20);
		txtDesc = new JTextField();
		txtDesc.setDocument(new AmanaFieldDocument(60));
		txtDesc.setBounds(20, 75, 280, 20);
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
		txtModelo.setDocument(new AmanaFieldDocument(45));
		txtModelo.setBounds(165, 115, 135, 20);
		add(lblModelo);
		add(txtModelo);
		
		lblNumero = new JLabel("Número de Série");
		lblNumero.setBounds(20,135,100,20);
		txtNumero = new ATextFieldLogin();
		txtNumero.setBounds(20, 155, 135, 20);
		add(lblNumero);
		add(txtNumero);
		
		lblFreq = new JLabel("Frequência de Manutenção");
		lblFreq.setBounds(165,135,135,20);
		lblFreq.setToolTipText("Meses");
		int min = 1;
		int max = 100;
		int step = 1;
		int initValue = 1;
		jFreq = new JSpinner(new SpinnerNumberModel(initValue, min, max, step));
		jFreq.setToolTipText("Meses");
		jFreq.setBounds(165, 155, 135, 20);
		add(lblFreq);
		add(jFreq);
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
	
	public void setVisualizarNumeroSerie(String numeroserie){
		txtNumero.setText(numeroserie);
		txtNumero.setEditable(false);
		txtNumero.setBackground(Color.WHITE);
	}
	
	public void setVisualizarDescricao(String desc){
		txtDesc.setText(desc);
		txtDesc.setEditable(false);
		txtDesc.setBackground(Color.WHITE);
	}
	
	public void setVisualizarFreq(int freq){
		jFreq.setModel(new SpinnerNumberModel(freq, freq, freq, 0));
	}
	
	public void setNome(String nome){
		txtNome.setText(nome);
	}
	
	public void setFabricante(String fabricante){
		txtFabricante.setText(fabricante);		
	}
	
	public void setModelo(String modelo){
		txtModelo.setText(modelo);
	}
	
	public void setNumeroSerie(String numeroserie){
		txtNumero.setText(numeroserie);
		txtNumero.isValido();
		//txtNumero.setEditable(false);
	}
	
	public void setDescricao(String desc){
		txtDesc.setText(desc);
	}
	
	public void setFreq(int freq){
		jFreq.setModel(new SpinnerNumberModel(freq, 1, 100, 1));
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
	
	public String getNumeroSerie(){
		return txtNumero.getText();
	}
	
	public String getDescricao(){
		return txtDesc.getText();
	}
	
	public int getFreq(){
		return Integer.parseInt(jFreq.getValue().toString());
	}
	
	@Override
	public boolean isValido() {
		return txtNumero.isValido();
	}
}
