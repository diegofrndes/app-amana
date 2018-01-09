package gui.swing.venda;

import gui.swing.componentes.APanelTopo;
import gui.swing.inicial.AFramePrincipal;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import dao.FabricaDao;

import modelo.Venda;
import dao.FabricaConexao;
public class ADialogCancelarPagamento extends JDialog {

	private static final long serialVersionUID = 1L;
	private Venda venda;
	private APanelTopo painelTopo;
	private boolean novoPagamento;
	private JButton btnExcluir, btnFechar;
	private JTable tabela;
	
	public ADialogCancelarPagamento(Venda ven){
		this.venda = ven;
		novoPagamento = false;
		setTitle("Pagamentos da Venda " + Integer.toString(venda.getId()));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				venda = null;
				removeAll();
				dispose();
			}
		});
		
		setModal(true);
		setResizable(false);
		setSize(400, 400);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        
		setVisible(true);
	}
	
	public void initComponents(){
		setLayout(null);
		painelTopo = new APanelTopo("Pagamento", "Cancelar", "imagens/32x32/cancelar_pagamento.png");
		painelTopo.setBounds(0, 0, 394, 52);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Pagamentos"));
		panel.setBounds(0, 52, 394, 290);
		
        tabela = new JTable();
        tabela.setShowHorizontalLines(true);
        tabela.setShowVerticalLines(true);
        tabela.setGridColor(new Color(238, 238, 238));
        tabela.setBackground(Color.white);
        tabela.setRowHeight(25);
        tabela.setOpaque(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tabela.setDefaultRenderer(Object.class, new PagamentoCellRenderer());
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.white);
        setBackground(Color.white);
        scroll.setBounds(0, 0, 394, 290);
        panel.add(scroll);
        
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tabela.getSelectedRow() != -1){
					btnExcluir.setEnabled(true);
				}
			}
		});

		btnExcluir = new JButton("Cancelar Pagamento");
		btnExcluir.setBounds(188, 345, 120, 20);
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnExcluir.isEnabled()){
					Object[] options = {"Sim", "Não"};
	                int j = JOptionPane.showOptionDialog(null, "Deseja cancelar o pagamento?", FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	                if (j == JOptionPane.YES_OPTION) {
	                	try{
	                		ModeloTabelaPagamento modelo = (ModeloTabelaPagamento) tabela.getModel();
	                		if(FabricaDao.getVendaDao().cancelarPagamento(modelo.getPagamento(tabela.getSelectedRow()))){      
	                			novoPagamento = true;
	                			JOptionPane.showMessageDialog(null, "Pagamento cancelado com sucesso.", "Cancelar Pagamento", JOptionPane.INFORMATION_MESSAGE);      
	                			setVisible(false);
	            				venda = null;
	            				removeAll();
	            				dispose();
	                			//buscar();
	                		}
	                		else{
	                			JOptionPane.showMessageDialog(null, "Não foi possível cancelar o pagamento.", "Cancelar Pagamento", JOptionPane.ERROR_MESSAGE );
	                		}
	                	}
	                	catch (NullPointerException ex) {
	                		JOptionPane.showMessageDialog(null, "Não foi possível cancelar o pagamento.", "Cancelar Pagamento", JOptionPane.ERROR_MESSAGE );
	                	}
	                }
				}
			
        	}
		});
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(313, 345, 80, 20);
		btnFechar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				venda = null;
				removeAll();
				dispose();
			}
		});

		buscar();
		
		add(painelTopo);
		add(btnExcluir);
		add(btnFechar);
		
		add(panel);
		
	}
	
	public boolean isNovoPagamento(){
		return novoPagamento;
	}
	
	public void setModelo(ModeloTabelaPagamento model) {
		tabela.setModel(model);
        TableColumnModel columnModel = tabela.getColumnModel();
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        tabela.getTableHeader().setBorder(null);
                
        columnModel.getColumn(0).setHeaderValue("Forma");
        columnModel.getColumn(0).setPreferredWidth(80);

        columnModel.getColumn(1).setHeaderValue("Usuário");
        columnModel.getColumn(1).setPreferredWidth(80);

        columnModel.getColumn(2).setHeaderValue("Valor");
        columnModel.getColumn(2).setPreferredWidth(80);
        
        columnModel.getColumn(3).setHeaderValue("Data");
        columnModel.getColumn(3).setPreferredWidth(80);
        
        columnModel.getColumn(4).setHeaderValue("Hora");
        columnModel.getColumn(4).setPreferredWidth(80);
        
    }

	private void buscar(){
		try{
			
			setModelo(new ModeloTabelaPagamento(FabricaDao.getVendaDao().procurarPagamentos(venda)));
			btnExcluir.setEnabled(false);
		} catch(NullPointerException ex){
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		AFramePrincipal.closeAFramePrincipal();
			System.exit(0);
		}
	}

}
