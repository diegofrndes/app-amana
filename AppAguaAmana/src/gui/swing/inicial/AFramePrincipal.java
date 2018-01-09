package gui.swing.inicial;

import gui.swing.config.ConfigAmana;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import dao.FabricaConexao;

public class AFramePrincipal extends JFrame {
		
	private static final long serialVersionUID = 1L;

	// Thread para verificar conexao
    private class ConexaoThread extends Thread {
    	
        private boolean threadDone = false;

        public void run() {
            while (!threadDone) {
            	try {
                    Thread.sleep(ConfigAmana.getTempoAtualizacao() * 1000); // 30 segundos
                } catch (InterruptedException ex) {
                }
            	statusBar.atualizarEquipamento();  
            	statusBar.atualizarItem();
            }
        }

        public void done() {
            threadDone = true;
        }
    }

    // Thread para atualizar data/hora
    private class RelogioThread extends Thread {
    	
        private boolean threadDone = false;

        public void run() {
            while (!threadDone) {
            	try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            	statusBar.atualizarData();
            }
        }

        public void done() {
            threadDone = true;
        }
    }

    private static ConexaoThread verificaConexao;
    private static RelogioThread verificaRelogio;
	private static volatile AFramePrincipal principalTela;
	private JPanel painel;
	private APanelTopoPrincipal painelTopo;
	private APanelCentral painelCentral;
	private AStatusBar statusBar;
	private Image icon;
	
    public static AFramePrincipal getAFramePrincipal() {
        if (principalTela == null) {
            synchronized (AFramePrincipal.class) {
                if (principalTela == null) {
                	ConfigAmana.carregarPropriedades();
                	principalTela = new AFramePrincipal();
                }
            }
        }
        return principalTela;
    }
    
    public static void closeAFramePrincipal() {
        if (principalTela != null) {
            synchronized (AFramePrincipal.class) {
                if (principalTela != null) {
                    verificaConexao.done();
                    verificaConexao = null;
                    verificaRelogio.done();
                    verificaRelogio = null;
                	principalTela.removeAll();
                	principalTela.dispose();
                	principalTela = null;
                }
            }
        }
    }
    
	private AFramePrincipal(){
		super(FabricaConexao.getNome());
		icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png"));
        this.setIconImage(icon);
		setMinimumSize(new Dimension(600,500));
        //setUndecorated(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    	//pack();
    	
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                Object[] options = {"Sim", "Não"};
                int j = JOptionPane.showOptionDialog(null, "Deseja Sair do Programa?", FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (j == JOptionPane.YES_OPTION) {
                	verificaConexao.done();
                    verificaConexao = null;
                    verificaRelogio.done();
                    verificaRelogio = null;
                    removeAll();
                	dispose();
                    System.exit(0);
                }
            }
        });
        //setEnabled(false);
        setVisible(true);

        verificaRelogio = new RelogioThread();
        verificaRelogio.start();
        verificaConexao = new ConexaoThread();
        verificaConexao.start();
	}
	
	private void initComponents(){
		painel = new JPanel();
		JScrollPane scroll = new JScrollPane(painel);
		scroll.setBorder(null);
		painel.setBorder(null);
		painel.setLayout(new BorderLayout());
		painel.setPreferredSize(new Dimension(930,600));
		painelTopo = new APanelTopoPrincipal();
		painel.add(painelTopo, BorderLayout.NORTH);
		painelCentral = new APanelCentral();
		painel.add(painelCentral, BorderLayout.CENTER);
		statusBar = new AStatusBar();
		painel.add(statusBar, BorderLayout.SOUTH);
		add(scroll);
		pack();
	}

}
