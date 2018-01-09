package gui.swing.venda;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import dao.FabricaConexao;

public class AFrameReportPagamentosAmana extends JFrame {

	private static final long serialVersionUID = 1L;
	int id = 0;
	JasperPrint print = null;
    JRViewer viewer = null;
    String data_inicial = "";
    String data_final = "";
    
	public AFrameReportPagamentosAmana(String data_inicial, String data_final) {
		setTitle("Relatório de Pagamentos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		this.data_inicial = data_inicial;
		this.data_final = data_final;
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
		//setAlwaysOnTop(false);
	}
	
	public void initComponents(){
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
			java.util.Date parsedDate = null;
			java.util.Date parsedDate2 = null;
			Timestamp time_inicial = null;
			Timestamp time_final = null;
			
			try {
				parsedDate = dateFormat.parse(data_inicial);
				parsedDate2 = dateFormat2.parse(data_final);
				time_inicial = new Timestamp(parsedDate.getTime());
				time_final = new Timestamp(parsedDate2.getTime());
				
			} catch (ParseException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível gerar relatório.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
				dispose();
			}
			//java.sql.Timestamp timestamp = 
			
			parametros.put("DATA_INICIAL", time_inicial);
			parametros.put("DATA_FINAL", time_final);
			
			print = JasperFillManager.fillReport("./relatorios-src/pagamentos_amana.jasper", parametros, FabricaConexao.getConexao());
            viewer = new JRViewer(print);
            add(viewer);
            pack();
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível gerar relatório.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
}
