package gui.swing.config;

import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.FabricaConexao;


public class ConfigConexaoTela extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JLabel ipLabel;
	private JLabel portaLabel;
    private JTextField ipTextField;
	private JTextField portaTextField;
    private JButton btnCancelar, btnSalvar;

	public ConfigConexaoTela(){
		setLayout(null);
		initComponents();
		setTitle("Servidor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
                	removeAll();
    				dispose();
                }
        });
       
		setResizable(false);
        setSize(180, 160);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	private void initComponents(){
		ipLabel = new JLabel("Ip:");
		ipLabel.setBounds(7, 4, 80, 25);
		portaLabel = new JLabel("Porta:");
		portaLabel.setBounds(7, 51, 60, 10);
		ipTextField = new JTextField();
		ipTextField.setDocument(new AmanaFieldDocument(200));
		ipTextField.setText(FabricaConexao.getIp());
		ipTextField.setBounds(7, 25, 160, 20);
		portaTextField = new JTextField();
		portaTextField.setDocument(new AmanaFieldDocument(200));
		portaTextField.setText(FabricaConexao.getPorta());
		portaTextField.setBounds(7, 64, 160, 20);

		btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(7, 94, 78, 22);
        btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				BufferedWriter out = null;
				try{
				    out = new BufferedWriter(new FileWriter("imagensconfig/config.ini"));
				    out.write("*****Arquivo de configurações*****");
				    out.newLine();
				    out.write("ip=" + ipTextField.getText());
				    out.newLine();
				    out.write("porta=" + portaTextField.getText());
				    out.newLine();
				    out.write("banco=amana");
				    out.newLine();
				    JOptionPane.showMessageDialog(null, "Configurações salvas com sucesso.", "Configurações do Servidor", JOptionPane.INFORMATION_MESSAGE);      
				    setVisible(false);
					removeAll();
					dispose();
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo de configuração.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
				} finally{
					if(out != null)
						try {
							out.close();
						} catch (IOException e) { /* Ignored */ }
					try {
			            // TODO code application logic here
			            FabricaConexao.carregarUrl();
			        } catch (IOException ex) {
			            JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo de configuração.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			            System.exit(0);
			        }
				}
			}
		});

        //btnConectar.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/conectar.png")));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(90, 94, 78, 22);
        //btnCancelar.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/cancelar.png")));
        btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				removeAll();
				dispose();
			}
		});

		add(ipLabel);
		add(portaLabel);
		add(btnCancelar);
		add(btnSalvar);
		add(ipTextField);
		add(portaTextField);
	}
}
