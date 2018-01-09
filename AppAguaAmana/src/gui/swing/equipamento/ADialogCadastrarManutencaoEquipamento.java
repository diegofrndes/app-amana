package gui.swing.equipamento;

import gui.swing.componentes.JCalendar;
import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.FabricaDao;

import modelo.Equipamento;
import modelo.ManutencaoEquipamento;
import dao.FabricaConexao;

public class ADialogCadastrarManutencaoEquipamento extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private boolean novaManutencao;
	private JCalendar calendario;
	private Equipamento equipamento;
	private JLabel lblData;
	private JLabel lblObs;
	private JLabel lblTipo;
	private JComboBox jComboBoxTipo;
	private JTextField txtObs;
	
	private JButton btnCancelar, btnAdicionar;

	public static ADialogCadastrarManutencaoEquipamento getADialogCadastrarManutencaoEquipamento(Equipamento equipamento){
		return new ADialogCadastrarManutencaoEquipamento(equipamento);
	}
	
	private ADialogCadastrarManutencaoEquipamento(Equipamento equipamento){
		setTitle("Nova Manutenção");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/16x16/manutencao.png")));
		novaManutencao = false;
		this.equipamento = equipamento;
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
        setSize(220, 165);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
   }
	
	private void initComponents(){
		novaManutencao = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		lblData = new JLabel("Data:");
		lblData.setBounds(20, 15, 50, 20);
		calendario = new JCalendar(true);
		calendario.setBounds(20, 35, 85, 20);
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(115, 15, 50, 20);
		jComboBoxTipo = new JComboBox();
		jComboBoxTipo.addItem("Preventiva");
		jComboBoxTipo.addItem("Corretiva");
		
		jComboBoxTipo.setBounds(115, 35, 85, 20);
		
		
		lblObs = new JLabel("Descrição:");
		lblObs.setBounds(20, 55, 100, 20);
		txtObs = new JTextField();
		txtObs.setDocument(new AmanaFieldDocument(80));
		txtObs.setBounds(20, 75, 180, 20);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(120,105,80,20);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				removeAll();
				dispose();	
			}
		});
		
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(20,105,80,20);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					ManutencaoEquipamento manutencao = null;
					if(jComboBoxTipo.getSelectedItem().toString().equals("Preventiva"))
					{
						manutencao= new ManutencaoEquipamento(-1, false); 
					} else {
						manutencao= new ManutencaoEquipamento(-1, true); 	
					}
					manutencao.setData(formatarDataBanco(calendario.getText()));
					manutencao.setObs(txtObs.getText());
					if(FabricaDao.getEquipamentoDao().adicionarManutencao(equipamento, manutencao) != -1){
						novaManutencao = true;
                		setVisible(false);
                		removeAll();
                		dispose();
                	}
                    else{
                    	JOptionPane.showMessageDialog(null, "Erro ao tentar adicionar manutenção.\n", "Nova Manutenção", JOptionPane.ERROR_MESSAGE );    
                    }
                } catch (NullPointerException ex) {
            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            		System.exit(0);
				}		
			}
		});
		
		//painelManutencao.add(calendario);
		painel.add(lblData);
		painel.add(calendario);
		painel.add(lblTipo);
		painel.add(jComboBoxTipo);
		painel.add(lblObs);
		painel.add(txtObs);
		painel.add(btnCancelar);
		painel.add(btnAdicionar);	
		
		add(painel);
		pack();
		
	}
	
	public boolean isNovaManutencao(){
		return novaManutencao;
	}
	
	public static String formatarDataBanco(String data) {
        data = data.replaceAll(" ", "");
        String[] partes = data.split("/");
        String result = new String();
        if (data.length() < 8) {
            result = "";
        } else {
            result = result.concat(partes[2] + "-");
            result = result.concat(partes[1] + "-");
            result = result.concat(partes[0]);
        }
        return result;
    }
}
