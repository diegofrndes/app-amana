package gui.swing.venda;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.ATextFieldValor;
import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import amana.Amana;

import dao.FabricaDao;

import modelo.Venda;
import dao.FabricaConexao;
public class ADialogPagamento extends JDialog {

	private static final long serialVersionUID = 1L;
	Venda venda;
	private APanelTopo painelTopo;
	private JLabel lblForma;
	private JLabel lblValor;
	private JLabel lblObs;
	private JComboBox jFormaComboBox;
	private ATextFieldValor txtValor;
	private boolean novoPagamento;
	private JTextField txtObs;
	private JButton btnCadastrar, btnCancelar;
	
	
	public ADialogPagamento(int id){
		novoPagamento = false;
		setTitle("Pagamento da Venda " + Integer.toString(id));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		venda = new Venda(id);
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
		setSize(326, 225);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        
		setVisible(true);
	}
	
	public void initComponents(){
		setLayout(null);
		painelTopo = new APanelTopo("Venda", "Pagamento", "imagens/32x32/pagamento.png");
		painelTopo.setBounds(0, 0, 320, 52);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Dados do Pagamento"));
		panel.setBounds(0, 52, 320, 115);
		
		lblForma = new JLabel("Forma");
		lblForma.setBounds(20,15, 50, 20);
		jFormaComboBox = new JComboBox();
		jFormaComboBox.addItem("DINHEIRO");
		jFormaComboBox.addItem("CHEQUE");
		jFormaComboBox.addItem("CART�O");
		jFormaComboBox.addItem("PROMISS�RIA");
		jFormaComboBox.addItem("DEP�SITO BANC�RIO");
		jFormaComboBox.addItem("B�NUS");
		jFormaComboBox.addItem("BOLETO BANC�RIO");
		jFormaComboBox.addItem("AVARIA");
		jFormaComboBox.setSelectedIndex(0);
		jFormaComboBox.setBounds(20,35, 135, 20);
		
		lblValor = new JLabel("Valor (R$)");
		lblValor.setBounds(165,15, 80, 20);
		txtValor = new ATextFieldValor();
		txtValor.setBounds(165, 35, 135, 20);
		lblObs = new JLabel("Observa��o");
		lblObs.setBounds(20,55, 100, 20);
		txtObs = new JTextField();
		txtObs.setDocument(new AmanaFieldDocument(60));
		txtObs.setBounds(20, 75, 280, 20);
		btnCadastrar = new JButton("Efetuar");
		btnCadastrar.setBounds(154, 170, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					venda.setFormaPagamento(jFormaComboBox.getSelectedItem().toString());
					venda.setObs(txtObs.getText());
					String aux = txtValor.getText();
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");
					BigDecimal valor = new BigDecimal(aux);
					venda.setValorRecebido(valor);
					venda.setUsuario(Amana.getUsuario().getLogin());
					if(valor.signum() == 0){
						JOptionPane.showMessageDialog(null, "Digite um valor v�lido.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);	
					} else {
						if(FabricaDao.getVendaDao().efetuarPagamento(venda) != -1){
							novoPagamento = true;
                			JOptionPane.showMessageDialog(null, "Pagamento efetuado com sucesso.", "Efetuar Pagamento", JOptionPane.INFORMATION_MESSAGE);      
                       		setVisible(false);
                       		removeAll();
                       		dispose();
                		}
                    	else{
                    		JOptionPane.showMessageDialog(null, "Erro ao tentar efetuar pagamento.\n", "Efetuar Pagamento", JOptionPane.ERROR_MESSAGE );    
                    	}
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "N�o foi poss�vel conectar ao servidor.\nO programa ser� finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            		//System.exit(0);
				} 
	
				
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 170, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				venda = null;
				removeAll();
				dispose();
			}
		});

		
		add(painelTopo);
		panel.add(lblForma);
		panel.add(jFormaComboBox);
		panel.add(lblValor);
		panel.add(txtValor);
		panel.add(lblObs);
		panel.add(txtObs);
		add(btnCancelar);
		add(btnCadastrar);
		
		add(panel);
	}
	
	public boolean isNovoPagamento(){
		return novoPagamento;
	}
}
