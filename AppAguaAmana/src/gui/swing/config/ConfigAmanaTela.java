package gui.swing.config;

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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ConfigAmanaTela extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel linhasLabel;
	private JLabel portaLabel;
    private JSpinner linhasSpinner;
	private JSpinner portaSpinner;
    private JButton btnCancelar, btnSalvar;

	public ConfigAmanaTela(){
		setLayout(null);
		initComponents();
		setTitle("Configurações");
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
		linhasLabel = new JLabel("Linhas por página (Tabela):");
		linhasLabel.setBounds(7, 4, 160, 25);
		portaLabel = new JLabel("Tempo de atualização (seg):");
		portaLabel.setBounds(7, 45, 160, 20);
		linhasSpinner = new JSpinner(new SpinnerNumberModel(
				ConfigAmana.getLimiteLinhas(), //initial value
                1, //min
                100, //max
                1));
		linhasSpinner.setBounds(7, 25, 160, 20);
		
		portaSpinner = new JSpinner(new SpinnerNumberModel(
				ConfigAmana.getTempoAtualizacao(), //initial value
                1, //min
                3600, //max
                1));
		portaSpinner.setBounds(7, 64, 160, 20);

		btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(7, 94, 70, 22);
        btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedWriter out = null;
				try{
				    out = new BufferedWriter(new FileWriter("config/amana.properties"));
				    out.write("*****Propriedades*****");
				    out.newLine();
				    out.write("linhas=" + linhasSpinner.getValue().toString());
				    out.newLine();
				    out.write("tempo=" + portaSpinner.getValue().toString());
				    out.newLine();
				    JOptionPane.showMessageDialog(null, "Configurações salvas com sucesso.", "Configurações Amana", JOptionPane.INFORMATION_MESSAGE);      
				    setVisible(false);
					removeAll();
					dispose();
					
				} catch (IOException e) {
				} finally{
					if(out != null)
						try {
							out.close();
						} catch (IOException e) { /* Ignored */ }
					    ConfigAmana.carregarPropriedades();
				}
			}
		});

        //btnConectar.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/conectar.png")));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(98, 94, 70, 22);
        //btnCancelar.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/cancelar.png")));
        btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				removeAll();
				dispose();
			}
		});

		add(linhasLabel);
		add(portaLabel);
		add(btnCancelar);
		add(btnSalvar);
		add(linhasSpinner);
		add(portaSpinner);
	}
}

