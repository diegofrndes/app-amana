package gui.swing.equipamento;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.ATable;
import gui.swing.inicial.AFramePrincipal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import dao.FabricaDao;

import modelo.Equipamento;
import dao.FabricaConexao;
public class ADialogManutencaoEquipamento extends JDialog {
	
	private ATable painelTabela;
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private boolean novaManutencao = false;
	private Equipamento equip;
	
	public static ADialogManutencaoEquipamento getADialogManutencaoEquipamento(Equipamento equipamento) {
		return new ADialogManutencaoEquipamento(equipamento);
    }
	
    private JButton btnCadastrar;
    private JButton btnExcluir;
    private JButton btnCancelar;
     
	private ADialogManutencaoEquipamento(Equipamento equipamento){
		equip = equipamento;
		setTitle("Histórico de Manutenção (" + equipamento.getNome() + " " + equipamento.getNumeroserie() + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		painelTabela.getTabela().setDefaultRenderer(Object.class, new ManutencaoEquipamentoCellRenderer());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        buscar();
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				removeAll();
    				dispose();
                }
        });
        setResizable(false);
        setSize(450, 512);
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
		
		painelTopo = new APanelTopo("Equipamento", "Manutenção", "imagens/32x32/equipamento_man.png");
		painelTopo.setBounds(0,0,450,52);
		painel.add(painelTopo);
	
		painelTabela = new ATable();
		painelTabela.setBounds(0, 52, 445, 400);
		painelTabela.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		painel.add(painelTabela);
		add(painel);
		painelTabela.getTabela().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(painelTabela.getTabela().getSelectedRow() != -1){
					btnExcluir.setEnabled(true);
				}
			}
		});

		this.painelTabela.setPrimeiroActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() - 10);
				buscar();
			}
		});
		
		this.painelTabela.setAnteriorActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() - 1);
				buscar();
			}
		});
		
		this.painelTabela.setProximoActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() + 1);
				buscar();
			}
		});
		
		this.painelTabela.setUltimoActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() + 10);
				buscar();
			}
		});
		
		btnCadastrar = new JButton("Nova");
		btnCadastrar.setBounds(192, 457, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		
				ADialogCadastrarManutencaoEquipamento tela = ADialogCadastrarManutencaoEquipamento.getADialogCadastrarManutencaoEquipamento(equip);
				boolean nM = tela.isNovaManutencao();
				if(nM){
					novaManutencao = true;
					buscar();
				}
			}
		});
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(277, 457, 80, 20);
		btnExcluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnExcluir.isEnabled()){
					Object[] options = {"Sim", "Não"};
	                int j = JOptionPane.showOptionDialog(null, "Deseja excluir a manutenção?", FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	                if (j == JOptionPane.YES_OPTION) {
	                	try{
	                		ModeloTabelaManutencao modelo = (ModeloTabelaManutencao) painelTabela.getTabela().getModel();
	                		if(FabricaDao.getEquipamentoDao().removerManutencao(modelo.getManutencao(painelTabela.getTabela().getSelectedRow()))){      
	                			novaManutencao = true;
	                			JOptionPane.showMessageDialog(null, "Manutenção excluída com sucesso.", "Excluir Equipamento", JOptionPane.INFORMATION_MESSAGE);      
	                			buscar();
	                		}
	                		else{
	                			JOptionPane.showMessageDialog(null, "Não foi possível excluir a manutenção.", "Excluir Manutenção", JOptionPane.ERROR_MESSAGE );
	                		}
	                	}
	                	catch (NullPointerException ex) {
	                		JOptionPane.showMessageDialog(null, "Não foi possível excluir a manutenção.", "Excluir Manutenção", JOptionPane.ERROR_MESSAGE );
	                	}
	                }
				}
			
        	}
		});
		
		
		btnCancelar = new JButton("Fechar");
		btnCancelar.setBounds(362, 457, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				removeAll();
				dispose();
			}
		});
		painel.add(btnCadastrar);
		painel.add(btnExcluir);
		painel.add(btnCancelar);
		
        pack();

	}
	
	public void setModelo(ModeloTabelaManutencao model) {
        painelTabela.setModeloTabela(model);
        TableColumnModel columnModel = painelTabela.getTabela().getColumnModel();
        painelTabela.getTabela().getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        painelTabela.getTabela().getTableHeader().setBorder(null);
                
        columnModel.getColumn(0).setHeaderValue("Data");
        columnModel.getColumn(0).setPreferredWidth(100);

        columnModel.getColumn(1).setHeaderValue("Tipo");
        columnModel.getColumn(1).setPreferredWidth(100);
        
        columnModel.getColumn(2).setHeaderValue("Descrição");
        columnModel.getColumn(2).setPreferredWidth(350);

    }

	private void buscar(){
		try{
			
			setModelo(new ModeloTabelaManutencao(FabricaDao.getEquipamentoDao().procurarManutencao(equip, painelTabela.getPag(), painelTabela.getLimite())));
			btnExcluir.setEnabled(false);
		} catch(NullPointerException ex){
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		AFramePrincipal.closeAFramePrincipal();
			System.exit(0);
		}
	}
	
	public boolean isNovaManutencao(){
		return novaManutencao;
	}
}
