package gui.swing.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.swing.produto.ADialogEscolherItem;
import modelo.Item;

public class APanelDadosProduto extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private List<Item> itens;
	private List<Integer> quantidades;
	private JLabel lblNome;
	private JLabel lblValor;
	private JLabel lblItens;
	private ATextFieldNome jNomeTextField;
	private ATextFieldValor jValorTextField;
	private JList jItensList;
	
	private JButton btnAdd, btnRemover;
	
	public APanelDadosProduto(){
		itens = new ArrayList<Item>();
		quantidades = new ArrayList<Integer>();
		initComponents();
	}
	
	private void initComponents(){
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(20, 15, 50, 20);
		jNomeTextField = new ATextFieldNome();
		jNomeTextField.setBounds(20, 35, 135, 20);
		add(lblNome);
		add(jNomeTextField);
		
		lblValor = new JLabel("Preço (R$)");
		lblValor.setBounds(165, 15, 80, 20);
		jValorTextField = new ATextFieldValor();
		jValorTextField.setBounds(165, 35, 135, 20);
		//jValorTextField.setHorizontalAlignment(JTextField.RIGHT);
		add(lblValor);
		add(jValorTextField);
		
		lblItens = new JLabel("Itens");
		lblItens.setBounds(20, 55, 80, 20);
		jItensList = new JList();
		jItensList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		jItensList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				btnRemover.setEnabled(true);
			}
		});
		JScrollPane scroll = new JScrollPane(jItensList);
		scroll.setBounds(20, 75, 280, 100);
		
		btnAdd = new JButton();
		btnAdd.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/add.png")));
		btnAdd.setBounds(277, 177, 23, 20);
		btnAdd.setToolTipText("Adicionar Item");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ADialogEscolherItem tela = new ADialogEscolherItem();
				Item item = tela.getEscolhido();
				int quantidade = tela.getQuantidade();
				if(item != null){
					if(!itens.contains(item)){
						itens.add(item);
						quantidades.add(quantidade);
						atualizarTela();
					}
				}
			}
		});
		
		btnRemover = new JButton();
		btnRemover.setEnabled(false);
		btnRemover.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/remove.png")));
		btnRemover.setBounds(253, 177, 23, 20);
		btnRemover.setToolTipText("Remover Item");
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(btnRemover.isEnabled()){
						itens.remove(jItensList.getSelectedIndex());
						quantidades.remove(jItensList.getSelectedIndex());
						atualizarTela();
					}
				} catch (NullPointerException e){
					
				}
			}
		});
		
		add(scroll);
		add(btnAdd);
		add(btnRemover);
		add(lblItens);
		
	}


	private void atualizarTela(){
		
		DefaultListModel model = new DefaultListModel();
		for(int i = 0; i < itens.size(); i++){
			String nome = ((Item) itens.get(i)).getNome();
			String quant = "(" + Integer.toString((Integer) quantidades.get(i)) +")";
			model.add(i, nome + " " + quant);
		}
		jItensList.setModel(model);
		btnRemover.setEnabled(false);
	}
	
	public String getNome() {
		return jNomeTextField.getText();
	}


	public List<Item> getItens() {
		return itens;
	}

	public List<Integer> getQuantidades() {
		return quantidades;
	}

	public BigDecimal getValor() {
		String aux = jValorTextField.getText();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		BigDecimal valor = new BigDecimal(aux);
		return valor;
	}

	public void setVisualizarNome(String nome){
		this.jNomeTextField.setEditable(false);
		this.jNomeTextField.setText(nome);
	}
	
	public void setVisualizarValor(BigDecimal valor){
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
    	moneyFormat.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
    	String val = moneyFormat.format(valor);   
    	this.jValorTextField.setEditable(false);
    	this.jValorTextField.setText(val);
	}
	
	public void setNome(String nome){
		this.jNomeTextField.setText(nome);
		this.jNomeTextField.isValido();
	}
	
	public void setValor(BigDecimal valor){
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
    	moneyFormat.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
    	String val = moneyFormat.format(valor);   
    	this.jValorTextField.setText(val);
	}
	
	public void setVisualizarItens(List<Item> itens, List<Integer> quantidades){
		this.itens = itens;
		this.quantidades = quantidades;
		atualizarTela();
		btnAdd.setVisible(false);
		btnRemover.setVisible(false);
	}
	
	@Override
	public boolean isValido() {
		return jNomeTextField.isValido();
	}
	
	
}
