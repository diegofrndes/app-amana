package gui.swing.venda;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import apresentador.venda.ApresentadorVendaExcluida;
import espectador.venda.EspectadorVendaExcluida;
import gui.swing.componentes.ATextFieldCpf;
import gui.swing.componentes.ATextFieldNome;
import gui.swing.componentes.ATextFieldRg;
import modelo.VendaExcluida;

public class ADialogVendaExcluida extends JDialog implements EspectadorVendaExcluida{
	private static final long serialVersionUID = 1L;
	
	
	private JButton btnCancelar;
	private JLabel lblUsuario;
	private JTextField textUsuario;
	private JLabel lblMotivo;
	private JTextField textMotivo;
	private JLabel lblData;
	private JTextField textData;
	
	@SuppressWarnings("unused")
	private ApresentadorVendaExcluida apresentador;
	
	public static ADialogVendaExcluida getADialogVendaExcluida(VendaExcluida vendaExcluida) {
		return new ADialogVendaExcluida(vendaExcluida);
    }
    
	private ADialogVendaExcluida(VendaExcluida vendaExcluida){
		setTitle("Dados da Exclusão da Venda " + Integer.toString(vendaExcluida.getVenda().getId()));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVendaExcluida(this, vendaExcluida);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				apresentador = null;
                	removeAll();
    				dispose();
                }
        });
        setResizable(false);
        setSize(320, 200);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	private void initComponents(){
		setLayout(null);
		lblUsuario = new JLabel("Excluído Pelo Usuário: ");
		lblUsuario.setBounds(20,15,250,20);
		textUsuario = new JTextField();
		textUsuario.setBounds(20, 35, 280, 20);
		textUsuario.setEditable(false);
		add(lblUsuario);
		add(textUsuario);
		
		lblMotivo = new JLabel("Motivo:");
		lblMotivo.setBounds(20, 55, 50, 20);
		textMotivo = new JTextField();
		textMotivo.setBounds(20, 75, 280, 20);
		textMotivo.setEditable(false);
		add(lblMotivo);
		add(textMotivo);
		
		lblData = new JLabel("Data da exclusão:");
		lblData.setBounds(20, 95, 200, 20);
		textData = new JTextField();
		textData.setBounds(20, 115, 280, 20);
		textData.setEditable(false);
		add(lblData);
		add(textData);
				
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(222, 145, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				apresentador = null;
				removeAll();
				dispose();
			}
		});
		
		add(btnCancelar);
		
		//pack();
		
		btnCancelar.requestFocus();		
	
	}

	@Override
	public void setUsuario(String usuario) {
		textUsuario.setText(usuario);
	}

	@Override
	public void setMotivo(String motivo) {
		textMotivo.setText(motivo);
	}

	@Override
	public void setData(String data) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");                	
		try {
			String s = dateFormat.format(formato.parse(data));
			textData.setText(s);
		} catch (ParseException e) {
			textData.setText(data);
		}
    }

}