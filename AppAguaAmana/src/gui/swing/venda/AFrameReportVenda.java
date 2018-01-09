package gui.swing.venda;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dao.FabricaConexao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class AFrameReportVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	int id = 0;
	boolean exibir_debito = false;
	private BigDecimal debito;
	JasperPrint print = null;
    JRViewer viewer = null;
    
	public AFrameReportVenda(int id, boolean exibir_debito, BigDecimal debito) {
		this.id = id;
		this.exibir_debito = exibir_debito;
		this.debito = debito;
		setTitle("NOTA DE CONFERÊNCIA Nº " + Integer.toString(id));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    	        	removeAll();
    				print = null;
    				viewer = null;
    	        	dispose();
                }
        });
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setAlwaysOnTop(true);
		setVisible(true);
	}
	
	public void initComponents(){
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("NUM_VENDA", id);
			parametros.put("EXIBIR_DEBITO", exibir_debito);
			parametros.put("DEBITO", debito);
			
			print = JasperFillManager.fillReport("./relatorios-src/venda.jasper", parametros, FabricaConexao.getConexao());
            viewer = new JRViewer(print);
            add(viewer);
            pack();
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível gerar nota de conferência.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
}
