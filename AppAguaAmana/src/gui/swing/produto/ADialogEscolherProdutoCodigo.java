package gui.swing.produto;

import gui.swing.documentos.NumberFieldDocument;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.jdbc.JDBCProdutoDao;

import modelo.Produto;
import dao.FabricaConexao;
public class ADialogEscolherProdutoCodigo extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private Produto escolhido;
	private int quantidade;
	
	private JLabel jCodLabel;
	private JTextField jCodTextField;
	private JLabel jQuantLabel;
	private JTextField jQuantTextField;
	
	
	public ADialogEscolherProdutoCodigo()
	{
		escolhido = null;
		quantidade = 1;
		setTitle("Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/16x16/produto.png")));
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				removeAll();
    				dispose();
                }
        });
        setResizable(false);
        setSize(160, 150);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}

	private void initComponents(){
		setLayout(null);
		jCodLabel = new JLabel("Código");
		jCodLabel.setBounds(20, 15, 120, 20);
		jCodTextField = new JTextField();
		jCodTextField.setDocument(new NumberFieldDocument(11));
		jCodTextField.setBounds(20, 35, 120, 20);
		jCodTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jCodTextField.getText().length() > 0 && jQuantTextField.getText().length() > 0){
					Produto temp = new JDBCProdutoDao().procurarProduto(jCodTextField.getText());
					if(temp != null){
						escolhido = temp;
						quantidade = Integer.valueOf(jQuantTextField.getText());
						setVisible(false);
			    		removeAll();
			    		dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Código Inexistente.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		add(jCodLabel);
		add(jCodTextField);
		
		jQuantLabel = new JLabel("Quantidade");
		jQuantLabel.setBounds(20, 55, 120, 20);
		jQuantTextField = new JTextField();
		jQuantTextField.setDocument(new NumberFieldDocument(11));
		jQuantTextField.setBounds(20, 75, 120, 20);
		jQuantTextField.setText("1");
		jQuantTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jCodTextField.getText().length() > 0 && jQuantTextField.getText().length() > 0){
					Produto temp = new JDBCProdutoDao().procurarProduto(jCodTextField.getText());
					if(temp != null){
						escolhido = temp;
						quantidade = Integer.valueOf(jQuantTextField.getText());
						setVisible(false);
			    		removeAll();
			    		dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Código Inexistente.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		add(jQuantTextField);
		add(jQuantLabel);
		
		
	}
	
	public Produto getEscolhido() {
		return escolhido;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
}
