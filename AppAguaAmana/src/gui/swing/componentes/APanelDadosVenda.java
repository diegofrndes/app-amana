package gui.swing.componentes;

import gui.swing.documentos.AmanaFieldDocument;
import gui.swing.pessoa.ADialogEscolherPessoa;
import gui.swing.pessoa.ADialogEscolherPessoaCodigo;
import gui.swing.produto.ADialogEscolherProduto;
import gui.swing.produto.ADialogEscolherProdutoCodigo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.FabricaConexao;
import modelo.Item;
import modelo.Pessoa;
import modelo.Produto;

public class APanelDadosVenda extends JPanel implements Validador {
	
	private static final long serialVersionUID = 1L;
	private Pessoa cliente;
	private Pessoa transportador;
	private List<Produto> produtos;
	private List<Integer> quantidades;
	private JLabel lblNomeCliente;
	private ATextFieldNome jClienteTextField;
	//private JTextField jCodClienteTextField;
	private JButton btnEscolherCliente;
	private JLabel lblTransporte;
	private JLabel lblValor;
	private JLabel lblValorRecebido;
	private JLabel lblDebito;
	private JLabel lblDesconto;
	private ATextFieldValor jValorTextField;
	private ATextFieldValor jValorRecebidoTextField;
	private ATextFieldValor jDebitoTextField;
	private ATextFieldValor jDescontoTextField;
	private JTextField jTransporteTextField;
	
	private JLabel lblFormaPag;
	private JLabel lblObs;
	private JLabel lblProduto;
	private JTable tabelaProdutos;
	private JLabel lblSubtotal;
	private JLabel lblSubtotalValor;
	
	private JComboBox jFormaComboBox;
	private JTextField jObsTextField;
	
	private JLabel lblNomeTransportador;
	private ATextFieldNome jTransportadorTextField;
	private JButton btnEscolherTransportador;
	private JButton btnAdd, btnRemover;
	private JPanel focusPanel;
	
	private boolean tipoDesconto;
	
	public APanelDadosVenda(){
		tipoDesconto = false;
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Dados da Venda"));
		initComponents();
		for(int i = 0; i < getComponentCount(); i++){
			this.getComponent(i).addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e){
					if(e.getKeyCode() == KeyEvent.VK_F1){
						escolherClienteCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F2){
						escolherTransportadorCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F3){
						escolherProdutoCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F5){
						if(tipoDesconto){
							lblDesconto.setText("DESCONTO (R$)");
							lblDesconto.setToolTipText("F5 - Desconto (%)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = false;
						} else {
							lblDesconto.setText("DESCONTO (%)");
							lblDesconto.setToolTipText("F5 - Desconto (R$)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = true;	
						}
						atualizarTotal();
					}
				}
				
			});
		}
		
		produtos = new ArrayList<Produto>();
		quantidades = new ArrayList<Integer>();
		atualizarTela();
	}
	
	@SuppressWarnings("serial")
	private void initComponents(){
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(5, 15, 295, 352);
		lblNomeCliente = new JLabel("Cliente");
		lblNomeCliente.setBounds(15, 0, 100, 20);
		panel.add(lblNomeCliente);
		
		jClienteTextField = new ATextFieldNome();
		jClienteTextField.setEditable(false);
		jClienteTextField.setFocusable(false);
		jClienteTextField.setBounds(15, 20, 255, 20);
		panel.add(jClienteTextField);
		
		btnEscolherCliente = new JButton("...");
		btnEscolherCliente.setBounds(275, 20, 20, 20);
		btnEscolherCliente.setToolTipText("Escolher Cliente");
		btnEscolherCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escolherCliente();
			}
		});
		//btnEscolherCliente.setFocusable(false);
		panel.add(btnEscolherCliente);
		
		lblNomeTransportador = new JLabel("Transportador");
		lblNomeTransportador.setBounds(15, 40, 80, 20);
		panel.add(lblNomeTransportador);
		
		jTransportadorTextField = new ATextFieldNome();
		jTransportadorTextField.setEditable(false);
		jTransportadorTextField.setFocusable(false);
		jTransportadorTextField.setBounds(15, 60, 255, 20);
		panel.add(jTransportadorTextField);
		
		btnEscolherTransportador = new JButton("...");
		btnEscolherTransportador.setBounds(275, 60, 20, 20);
		btnEscolherTransportador.setToolTipText("Escolher Transportador");
		btnEscolherTransportador.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escolherTransportador();
			}
		});
		//btnEscolherTransportador.setFocusable(false);
		panel.add(btnEscolherTransportador);	
		
		lblTransporte = new JLabel("Descrição do Veículo");
		lblTransporte.setBounds(15, 80, 150, 20);
		panel.add(lblTransporte);
		
		jTransporteTextField = new JTextField();
		jTransporteTextField.setDocument(new AmanaFieldDocument(60));
		jTransporteTextField.setBounds(15, 100, 280, 20);
		panel.add(jTransporteTextField);
		
		lblFormaPag = new JLabel("Forma de Pagamento");
		lblFormaPag.setBounds(15, 120, 150, 20);
		panel.add(lblFormaPag);
		jFormaComboBox = new JComboBox();
		jFormaComboBox.addItem("DINHEIRO");
		jFormaComboBox.addItem("CHEQUE");
		jFormaComboBox.addItem("CARTÃO");
		jFormaComboBox.addItem("PROMISSÓRIA");
		jFormaComboBox.addItem("DEPÓSITO BANCÁRIO");
		jFormaComboBox.addItem("BÔNUS");
		jFormaComboBox.addItem("BOLETO BANCÁRIO");
		jFormaComboBox.addItem("AVARIA");
		jFormaComboBox.setSelectedIndex(0);
		jFormaComboBox.setBounds(15, 140, 135, 20);
		panel.add(jFormaComboBox);
		
		lblObs = new JLabel("Observação");
		lblObs.setBounds(160, 120, 100, 20);
		jObsTextField = new JTextField();
		jObsTextField.setDocument(new AmanaFieldDocument(60));
		jObsTextField.setBounds(160, 140, 135, 20);
		panel.add(lblObs);
		panel.add(jObsTextField);
		
		lblProduto = new JLabel("Produtos");
		lblProduto.setBounds(15, 160, 100, 20);
		panel.add(lblProduto);
		tabelaProdutos = new JTable(){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
		        return false;
		    }		
		};
		tabelaProdutos.setFocusable(false);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tabelaProdutos.getSelectedRow() != -1){
					btnRemover.setEnabled(true);
				}
			}
		});
				
		JScrollPane scroll = new JScrollPane(tabelaProdutos);
		scroll.setBounds(15, 180, 280, 150);
		
		btnAdd = new JButton();
		btnAdd.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/add.png")));
		btnAdd.setBounds(272, 333, 23, 20);
		btnAdd.setToolTipText("Adicionar Produto");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escolherProduto();
			}
		});
		//btnAdd.setFocusable(false);
		
		btnRemover = new JButton();
		btnRemover.setEnabled(false);
		btnRemover.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/remove.png")));
		btnRemover.setBounds(248, 333, 23, 20);
		btnRemover.setToolTipText("Remover Produto");
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(btnRemover.isEnabled()){
						produtos.remove(tabelaProdutos.getSelectedRow());
						quantidades.remove(tabelaProdutos.getSelectedRow());
						atualizarTela();
					}
				} catch (NullPointerException e){
					
				}
			}
		});
		//btnRemover.setFocusable(false);
		
		panel.add(scroll);
		panel.add(btnAdd);
		panel.add(btnRemover);
		
		focusPanel = new JPanel();
		focusPanel.setBorder(null);
		focusPanel.setLayout(null);	
		focusPanel.setBounds(311, 10, 180, 355);
		
		lblDesconto = new JLabel("DESCONTO (R$)");
		lblDesconto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDesconto.setBounds(20, 8, 150, 18);
		lblDesconto.setToolTipText("F5 - Desconto (%)");
		lblDesconto.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent evt){
				if(tipoDesconto){
					lblDesconto.setText("DESCONTO (R$)");
					lblDesconto.setToolTipText("F5 - Desconto (%)");
					tipoDesconto = false;
				} else {
					lblDesconto.setText("DESCONTO (%)");
					lblDesconto.setToolTipText("F5 - Desconto (R$)");
					tipoDesconto = true;	
				}
				atualizarTotal();
			}
		});
		lblDesconto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		focusPanel.add(lblDesconto);

		jDescontoTextField = new ATextFieldValor();
		jDescontoTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jDescontoTextField.setBounds(20, 41, 145, 40);
		jDescontoTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		jDescontoTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarTotal();
			}
		});
		
		jDescontoTextField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				atualizarTotal();
			}
		});
		
		focusPanel.add(jDescontoTextField);

		lblValorRecebido = new JLabel("RECEBIDO (R$)");
		lblValorRecebido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblValorRecebido.setBounds(20, 96, 150, 18);
		focusPanel.add(lblValorRecebido);
		
		jValorRecebidoTextField = new ATextFieldValor();
		jValorRecebidoTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jValorRecebidoTextField.setBounds(20, 129, 145, 40);
		jValorRecebidoTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		jValorRecebidoTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarTotal();
			}
		});
		
		jValorRecebidoTextField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				atualizarTotal();
			}
		});

		focusPanel.add(jValorRecebidoTextField);
		
		
		lblDebito = new JLabel("DÉBITO (R$)");
		lblDebito.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDebito.setBounds(20, 184, 150, 18);
		focusPanel.add(lblDebito);
		jDebitoTextField = new ATextFieldValor();
		jDebitoTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jDebitoTextField.setBounds(20, 217, 145, 40);
		jDebitoTextField.setHorizontalAlignment(JTextField.RIGHT);
		jDebitoTextField.setEditable(false);
		jDebitoTextField.setFocusable(false);
		focusPanel.add(jDebitoTextField);
		
		lblValor = new JLabel("TOTAL (R$)");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblValor.setBounds(20, 272, 150, 18);
		focusPanel.add(lblValor);
		jValorTextField = new ATextFieldValor();
		jValorTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jValorTextField.setBounds(20, 305, 145, 40);
		jValorTextField.setHorizontalAlignment(JTextField.RIGHT);
		jValorTextField.setBackground(new Color(200, 225, 255));
		jValorTextField.setEditable(false);
		jValorTextField.setFocusable(false);
		focusPanel.add(jValorTextField);
		
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setBounds(315, 15, 1, 352);
		
		for(int i = 0; i < panel.getComponentCount(); i++){
			panel.getComponent(i).addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e){
					if(e.getKeyCode() == KeyEvent.VK_F1){
						escolherClienteCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F2){
						escolherTransportadorCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F3){
						escolherProdutoCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F5){
						if(tipoDesconto){
							lblDesconto.setText("DESCONTO (R$)");
							lblDesconto.setToolTipText("F5 - Desconto (%)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = false;
						} else {
							lblDesconto.setText("DESCONTO (%)");
							lblDesconto.setToolTipText("F5 - Desconto (R$)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = true;	
						}
						atualizarTotal();
					}
				}
				
			});
		}
		
		for(int i = 0; i < focusPanel.getComponentCount(); i++){
			focusPanel.getComponent(i).addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e){
					if(e.getKeyCode() == KeyEvent.VK_F1){
						escolherClienteCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F2){
						escolherTransportadorCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F3){
						escolherProdutoCodigo();
					}
					else if(e.getKeyCode() == KeyEvent.VK_F5){
						if(tipoDesconto){
							lblDesconto.setText("DESCONTO (R$)");
							lblDesconto.setToolTipText("F5 - Desconto (%)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = false;
						} else {
							lblDesconto.setText("DESCONTO (%)");
							lblDesconto.setToolTipText("F5 - Desconto (R$)");
							jDescontoTextField.setText("0,00");
							tipoDesconto = true;	
						}
						atualizarTotal();
					}
				}
				
			});
		}
		
		
		lblSubtotal = new JLabel("Subtotal :");
		lblSubtotal.setBounds(20, 333, 50, 20);
		panel.add(lblSubtotal);
		
		lblSubtotalValor = new JLabel("");
		lblSubtotalValor.setBounds(70, 333, 200, 20);
		panel.add(lblSubtotalValor);
		
		add(separator);
		add(panel);
		add(focusPanel);
		//pack();
		//jTransporteTextField.requestFocus();
	}
	
	public void escolherClienteCodigo(){
		ADialogEscolherPessoaCodigo tela = new ADialogEscolherPessoaCodigo("Cliente", "imagens/16x16/cliente.png");
		Pessoa temp = tela.getEscolhida();
		if(temp != null){
			cliente = temp;
			jClienteTextField.setText(cliente.getNome());
			jClienteTextField.isValido();
			transportador = temp;
			jTransportadorTextField.setText(transportador.getNome());
			jTransportadorTextField.isValido();
		}
	}
	
	public void escolherCliente(){
		ADialogEscolherPessoa tela = new ADialogEscolherPessoa("Escolher Cliente");
		Pessoa temp = tela.getEscolhida();
		if(temp != null){
			cliente = temp;
			jClienteTextField.setText(cliente.getNome());
			jClienteTextField.isValido();
			transportador = temp;
			jTransportadorTextField.setText(transportador.getNome());
			jTransportadorTextField.isValido();
		}
	}

	public void escolherTransportadorCodigo(){
		ADialogEscolherPessoaCodigo tela = new ADialogEscolherPessoaCodigo("Transportador", "imagens/16x16/transportador.png", false);
		Pessoa temp = tela.getEscolhida();
		if(temp != null){
			transportador = temp;
			jTransportadorTextField.setText(transportador.getNome());
			jTransportadorTextField.isValido();
		}
	}

	public void escolherTransportador(){
		ADialogEscolherPessoa tela = new ADialogEscolherPessoa("Escolher Transportador", false);
		Pessoa temp = tela.getEscolhida();
		if(temp != null){
			transportador = temp;
			jTransportadorTextField.setText(transportador.getNome());
			jTransportadorTextField.isValido();
		}
	}

	public void escolherProdutoCodigo(){
		ADialogEscolherProdutoCodigo tela = new ADialogEscolherProdutoCodigo();
		Produto produto = tela.getEscolhido();
		int quantidade = tela.getQuantidade();
		if(produto != null){
			if(!produtos.contains(produto)){
				List<Item> itens = new ArrayList<Item>();
				List<Integer> est = new ArrayList<Integer>();
				produtos.add(produto);
				quantidades.add(quantidade);
				for(int i = 0; i < produtos.size(); i++){
					for(int j = 0; j < produtos.get(i).getItens().size(); j++){
						if(!itens.contains(produtos.get(i).getItens().get(j))){
							itens.add(produtos.get(i).getItens().get(j));
							est.add(produtos.get(i).getQuantidades().get(j)*quantidades.get(i));	
						} else {
							int index = itens.indexOf(produtos.get(i).getItens().get(j)); 
							est.set(index, est.get(index) + produtos.get(i).getQuantidades().get(j)*quantidades.get(i));		
						}						
					}
				}
				//System.out.println();		
				for(int i = 0; i < itens.size(); i++){
					if(itens.get(i).getQuantidade() < est.get(i)){
						JOptionPane.showMessageDialog(null, "Não foi possível adicionar essa quantidade de produto.\nQuantidade insuficiente de " + itens.get(i).getNome() + " no estoque.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
						produtos.remove(produtos.size()-1);
						quantidades.remove(quantidades.size()-1);
						break;
					}
				}
				atualizarTela();
			}
		}
	}
	
	public void escolherProduto(){
		ADialogEscolherProduto tela = new ADialogEscolherProduto();
		Produto produto = tela.getEscolhido();
		int quantidade = tela.getQuantidade();
		if(produto != null){
			if(!produtos.contains(produto)){
				List<Item> itens = new ArrayList<Item>();
				List<Integer> est = new ArrayList<Integer>();
				produtos.add(produto);
				quantidades.add(quantidade);
				for(int i = 0; i < produtos.size(); i++){
					for(int j = 0; j < produtos.get(i).getItens().size(); j++){
						if(!itens.contains(produtos.get(i).getItens().get(j))){
							itens.add(produtos.get(i).getItens().get(j));
							est.add(produtos.get(i).getQuantidades().get(j)*quantidades.get(i));	
						} else {
							int index = itens.indexOf(produtos.get(i).getItens().get(j)); 
							est.set(index, est.get(index) + produtos.get(i).getQuantidades().get(j)*quantidades.get(i));		
						}						
					}
				}
				System.out.println();
				
				for(int i = 0; i < itens.size(); i++){
					if(itens.get(i).getQuantidade() < est.get(i)){
						JOptionPane.showMessageDialog(null, "Não foi possível adicionar essa quantidade de produto.\nQuantidade insuficiente de " + itens.get(i).getNome() + " no estoque.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
						produtos.remove(produtos.size()-1);
						quantidades.remove(quantidades.size()-1);
						break;
					}
				}
				atualizarTela();
			}
		}
	}
	
	public void atualizarTela(){
		btnRemover.setEnabled(false);
		String colunas[] = new String[]{"QTD.","DESCRIMINAÇÃO", "UNIT.", "TOTAL"};
		DefaultTableModel modelo = new DefaultTableModel(null, colunas);
		for(int i = 0; i < produtos.size(); i++){
			String dados[] = new String[4];
			Produto produto = produtos.get(i);
			int quantidade = quantidades.get(i);
			dados[0] = Integer.toString(quantidade);
			dados[1] = produto.getNome();
			dados[2] = produto.getValor().toString();
			BigDecimal total = produto.getValor().multiply(new BigDecimal(quantidade));
			dados[3] = total.toString();
 			modelo.addRow(dados);	
		}
		modelo.setColumnIdentifiers(colunas);
		
		tabelaProdutos.setModel(modelo);
		TableColumnModel columnModel = tabelaProdutos.getColumnModel();
		//tabelaProdutos.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
		tabelaProdutos.getTableHeader().setBorder(null);
                
        //columnModel.getColumn(0).setHeaderValue("Nome");
        columnModel.getColumn(0).setPreferredWidth(35);

        //columnModel.getColumn(1).setHeaderValue("CPF");
        columnModel.getColumn(1).setPreferredWidth(135);

        //columnModel.getColumn(2).setHeaderValue("Telefone");
        columnModel.getColumn(2).setPreferredWidth(45);
        
        //columnModel.getColumn(3).setHeaderValue("Usuário");
        columnModel.getColumn(3).setPreferredWidth(65);
		
        atualizarTotal();
	}
	
	public void atualizarTotal(){
		BigDecimal total = new BigDecimal(0.00);
		
		for(int i = 0; i < produtos.size(); i++ ){
			Produto produto = produtos.get(i);
			int quantidade = quantidades.get(i);
			total = total.add(produto.getValor().multiply(new BigDecimal(quantidade)));
		}
		
		NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
    	moneyFormat1.setMinimumFractionDigits(2);   
    	lblSubtotalValor.setText(moneyFormat1.format(total));
    	
    	BigDecimal desconto = null;
    	if(tipoDesconto){
    		String auxDesconto = jDescontoTextField.getText();
        	auxDesconto = auxDesconto.replace(".", "");
        	auxDesconto = auxDesconto.replace(",", ".");
        	BigDecimal porc = new BigDecimal(auxDesconto);
        	if(porc.compareTo(new BigDecimal(100.00f)) == 1){
        		porc = new BigDecimal(100.00f);
    			porc.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    			jDescontoTextField.setText("100,00");
    		}
    		BigDecimal mult = total.multiply(porc);
    		desconto = mult.divide(new BigDecimal(100));
    			
    	} else {
    		String auxDesconto = jDescontoTextField.getText();
        	auxDesconto = auxDesconto.replace(".", "");
        	auxDesconto = auxDesconto.replace(",", ".");
        	desconto = new BigDecimal(auxDesconto);
        	if(desconto.compareTo(total) == 1){
        		desconto = total;
    			//valorRecebido.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    			jDescontoTextField.setText(desconto.toString());
    		}
        	
    	}
    	
		BigDecimal totalFinal = total.subtract(desconto);
		BigDecimal resultado = totalFinal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		String aux = jValorRecebidoTextField.getText();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		BigDecimal valorRecebido = new BigDecimal(aux);
		valorRecebido.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		if(valorRecebido.compareTo(resultado) == 1){
			valorRecebido = resultado;
			valorRecebido.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			jValorRecebidoTextField.setText(valorRecebido.toString());
		}
	
		BigDecimal debito = valorRecebido.subtract(resultado);
		//System.out.println(debito.toString());
		if(debito.signum() == -1){
			jDebitoTextField.setText(debito.toString());
			jDebitoTextField.setBackground(new Color(255, 215, 215));
		} else {
			jDebitoTextField.setText("0,00");
			jDebitoTextField.setBackground(Color.WHITE);
		}
		
		jValorTextField.setText(resultado.toString());		
	}

	public Pessoa getCliente(){
		return cliente;
	}
	
	public Pessoa getTransportador(){
		return transportador;
	}
	
	public String getTransporte() {
		return jTransporteTextField.getText();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public List<Integer> getQuantidades() {
		return quantidades;
	}

	public BigDecimal getDesconto() {
		BigDecimal total = new BigDecimal(0.00);
		total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		for(int i = 0; i < produtos.size(); i++ ){
			Produto produto = produtos.get(i);
			int quantidade = quantidades.get(i);
			total = total.add(produto.getValor().multiply(new BigDecimal(quantidade)));
		}
		
    	BigDecimal desconto = null;
    	if(tipoDesconto){
    		String auxDesconto = jDescontoTextField.getText();
        	auxDesconto = auxDesconto.replace(".", "");
        	auxDesconto = auxDesconto.replace(",", ".");
        	BigDecimal porc = new BigDecimal(auxDesconto);
        	if(porc.compareTo(new BigDecimal(100.00f)) == 1){
        		porc = new BigDecimal(100.00f);
    			porc.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    			jDescontoTextField.setText("100,00");
    		}
    		BigDecimal mult = total.multiply(porc);
    		desconto = mult.divide(new BigDecimal(100));
    	} else {
    		String auxDesconto = jDescontoTextField.getText();
        	auxDesconto = auxDesconto.replace(".", "");
        	auxDesconto = auxDesconto.replace(",", ".");
        	desconto = new BigDecimal(auxDesconto);
        	if(desconto.compareTo(total) == 1){
        		desconto = total;
        		jDescontoTextField.setText(desconto.toString());
    		}
    	}
    	desconto.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    	return desconto;
	}

	public BigDecimal getValorRecebido() {
		String aux = jValorRecebidoTextField.getText();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		BigDecimal valor = new BigDecimal(aux);
		return valor;
	}

	public BigDecimal getValor() {
		String aux = jValorTextField.getText();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		BigDecimal valor = new BigDecimal(aux);
		return valor;
	}

	public String getFormaPagamento() {
		return jFormaComboBox.getSelectedItem().toString();
	}

	public String getObs() {
		return jObsTextField.getText();
	}
	
	public boolean isValido() {
		return (jClienteTextField.isValido() && jTransportadorTextField.isValido()); 
	}
	
}
