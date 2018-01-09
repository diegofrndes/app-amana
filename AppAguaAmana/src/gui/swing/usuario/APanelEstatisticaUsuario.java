package gui.swing.usuario;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import modelo.UsuarioTipo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
public class APanelEstatisticaUsuario extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public APanelEstatisticaUsuario(){
		setLayout(new BorderLayout());
	}
	
	public void setChart(int estatisticas[]){
		if(estatisticas != null){
			removeAll();
			DefaultPieDataset result = new DefaultPieDataset();
			int count = 0;
			for (int i = 0; i < estatisticas.length; i++){
				if(estatisticas[i] > 0){
					result.setValue(UsuarioTipo.getTipoUsuario(i + 1), 
									estatisticas[i]);
					count = count + estatisticas[i];
				}
			}
			
			// This will create the dataset 
			PieDataset dataset = result;
			// based on the dataset we create the chart
			JFreeChart chart = createChart(dataset, 
			"Usuários (" + Integer.toString(count) + ")");
			// we put the chart into a panel
			ChartPanel chartPanel = new ChartPanel(chart);
			// default size
			chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
			// add it to our application
			add(chartPanel, BorderLayout.CENTER);
        }
	}
	
	private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart(title,          // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);
        
        return chart;
    }
	
}
