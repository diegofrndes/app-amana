package gui.swing.produto;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.JCalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import dao.FabricaDao;

import modelo.EntradaSaidaItem;
import modelo.Item;
import dao.FabricaConexao;
public class ADialogAnaliseItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private Item item;
	private APanelTopo painelTopo;
	private JLabel lblInicial;
	private gui.swing.componentes.JCalendar dataInicial;
	private JLabel lblFinal;
	private JCalendar dataFinal;
	private JButton botaoAtualizar;
	private JPanel chartPanel;
	private JFreeChart chart;
	private TimeSeriesCollection datasetAnalise;
	
	public ADialogAnaliseItem(Item item){
		this.item = item;
		setTitle("Análise De " + item.getNome());
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    
		addWindowListener(new WindowAdapter() {
            	public void windowClosing(WindowEvent we) {
            		setVisible(false);
            		removeAll();
            		datasetAnalise.removeAllSeries();
            		datasetAnalise = null;
            		if(chartPanel != null){
            			chartPanel.removeAll();
                		chart = null;
                		chartPanel = null;
            		}
            		dispose();
            	}
    	});
		
    	setResizable(false);
    	setSize(606, 500);
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	setModal(true);
    	setVisible(true);

	}
	
	void initComponents()
	{
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		JPanel pBlue = new JPanel();
		pBlue.setBorder(null);
		pBlue.setBackground(new Color(0,148,219));
		JSplitPane painelDividirTopoBotoes = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelDividirTopoBotoes.setBounds(0 , 52, 600, 37);
		painelDividirTopoBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(243,243,243)));
		painelDividirTopoBotoes.setLeftComponent(pBlue);
		painelDividirTopoBotoes.setDividerLocation(5);
		painelDividirTopoBotoes.setDividerSize(0);
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180,180,180)));
		lblInicial = new JLabel("Data Inicial: ");
		lblInicial.setBounds(5, 5, 60, 20);
		dataInicial = new JCalendar(true);
		dataInicial.setBounds(65, 5, 90, 20);
		lblFinal = new JLabel("Data Final: ");
		lblFinal.setBounds(160, 5, 60, 20);
		dataFinal = new JCalendar(true);
		dataFinal.setBounds(215, 5, 90, 20);
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setBounds(515, 5, 80, 20);
		botaoAtualizar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/atualizar.png")));
		botaoAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					//chartPanel.removeAll();
					//chartPanel = createDemoPanel();	
					createDataset();
				} catch (Exception e){
					setVisible(false);
            		removeAll();
					dispose();
					JOptionPane.showMessageDialog(null, "Erro ao tentar gerar análise.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
		    	}
			}
		});
		painelBotoes.setLayout(null);
		painelBotoes.add(lblInicial);
		painelBotoes.add(dataInicial);
		painelBotoes.add(lblFinal);
		painelBotoes.add(dataFinal);
		painelBotoes.add(botaoAtualizar);
		
		painelDividirTopoBotoes.setRightComponent(painelBotoes);
		
		painel.add(painelDividirTopoBotoes);
		
		painelTopo = new APanelTopo("Item", "Análise Do Estoque Do Item " + item.getNome(), "imagens/32x32/analise.png");
		painelTopo.setBounds(0,0,600,52);
		painel.add(painelTopo);
		datasetAnalise = new TimeSeriesCollection();
		try{
			chartPanel = createDemoPanel();
			chartPanel.setBounds(0,89, 595, 405);
			painel.add(chartPanel);
			add(painel);
		} catch (Exception e){
			//e.printStackTrace();
			setVisible(false);
    		removeAll();
			dispose();
			JOptionPane.showMessageDialog(null, "Erro ao tentar gerar análise.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    	}
	}
	
	/**
     * Creates a chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return A chart.
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            item.getNome(),  // title
            "Data",             // x-axis label
            "Quantidade",   // y-axis label
            dataset,            // data
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );

        chart.setBackgroundPaint(new Color(238,238,238));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
       
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setSeriesPaint(0, new Color(236,41,5));
            renderer.setBaseToolTipGenerator((XYToolTipGenerator)new ItemGenerator(item.getId()));
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }
        
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd/MMM/yyyy"));
        
        return chart;

    }
    
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    @SuppressWarnings("deprecation")
	private XYDataset createDataset() {
    	String dataInicialBanco = formatarDataBanco(dataInicial.getText()) + " 00:00:00";
    	String dataFinalBanco = formatarDataBanco(dataFinal.getText()) + " 23:59:59";
    	
        TimeSeries s1 = new TimeSeries(item.getNome(), Second.class);
       
        try{
        	int quantidade = FabricaDao.getItemDao().quantidade(item, dataInicialBanco);
        	List<EntradaSaidaItem> lista = FabricaDao.getItemDao().procurarMovimentacaoEstoque(item, dataInicialBanco, dataFinalBanco);
        	for(int i = 0; i < lista.size(); i++ ){
            	EntradaSaidaItem estoque = (EntradaSaidaItem) lista.get(i);
            	Date data = null;
            	int seg = 0;
            	int min = 0;
            	int hora = 0;
            	int dia = 0;
            	int mes = 0;
            	int ano = 0;
            	
            	SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");  
              
            	try  
            	{  
            		data = formatador.parse(estoque.getData());  
            	    SimpleDateFormat formatSeg = new SimpleDateFormat("ss");
            		seg = Integer.parseInt(formatSeg.format(data));
            		SimpleDateFormat formatMin = new SimpleDateFormat("mm");
            		min = Integer.parseInt(formatMin.format(data));
            		SimpleDateFormat formatHora = new SimpleDateFormat("kk");
            		hora = Integer.parseInt(formatHora.format(data));
            		SimpleDateFormat formatDia = new SimpleDateFormat("dd");
            		dia = Integer.parseInt(formatDia.format(data));
            		SimpleDateFormat formatMes = new SimpleDateFormat("MM");
            		mes = Integer.parseInt(formatMes.format(data));
            		SimpleDateFormat formatAno = new SimpleDateFormat("yyyy");
            		ano = Integer.parseInt(formatAno.format(data));
            	 }  
            	  catch(ParseException ex)  
            	  {   
            	      //throw new RuntimeException(ex);  
            	  }  
            	  if(data != null){
            		  Second sec = new Second(seg, min, hora, dia, mes, ano);
            		  quantidade = quantidade + estoque.getQuantidade();
            		  s1.addOrUpdate(sec, quantidade);
            	  }
            }
        } catch (NullPointerException e){
    		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		System.exit(0);
        }
        
        datasetAnalise.removeAllSeries();
        datasetAnalise.addSeries(s1);
        //dataset.addSeries(s2);

        datasetAnalise.setDomainIsPointsInTime(true);

        return datasetAnalise;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public JPanel createDemoPanel() {
        chart = createChart(createDataset());
        return new ChartPanel(chart);
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
