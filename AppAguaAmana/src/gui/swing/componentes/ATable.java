package gui.swing.componentes;

import gui.swing.config.ConfigAmana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ATable extends JPanel {
    
	private static final long serialVersionUID = 1L;
	private JToolBar barra;
    private JTable tabelaGenerica;
    private JButton anterior;
    private JButton primeiro;
    private JButton proximo;
    private JButton ultimo;
    private JLabel pagina;
    private int pag;
    private boolean maxPag;
    private int max;
    public ATable() {
    	super(new BorderLayout());
    	setMaxPag(false);
    	max = 3;
        tabelaGenerica = new JTable();
        anterior = new JButton(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/anterior.png")));
        anterior.setToolTipText("Anterior");
        primeiro = new JButton(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/primeiro.png")));
        primeiro.setToolTipText("-10");
        proximo = new JButton(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/proximo.png")));
        proximo.setToolTipText("Próximo");
        ultimo = new JButton(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/ultimo.png")));
        ultimo.setToolTipText("+10");
        pagina = new JLabel("0");
        pagina.setOpaque(true);
        pagina.setBackground(Color.WHITE);
        setPag(0);
        barra = new JToolBar();
        barra.setFloatable(false);
        barra.setBorder(null);
        barra.setRollover(true);
        barra.add(Box.createHorizontalGlue());
        barra.add(primeiro);
        barra.add(anterior);
        barra.add(pagina);
        barra.add(proximo);
        barra.add(ultimo);
        barra.setBackground(Color.white);
        tabelaGenerica.setShowHorizontalLines(true);
        tabelaGenerica.setShowVerticalLines(true);
        tabelaGenerica.setGridColor(new Color(238, 238, 238));
        tabelaGenerica.setBackground(Color.white);
        tabelaGenerica.setRowHeight(25);
        tabelaGenerica.setOpaque(true);
        tabelaGenerica.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaGenerica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tabelaGenerica.getTableHeader().setBorder(null);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabelaGenerica.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER); 
        JScrollPane scroll = new JScrollPane(tabelaGenerica);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.white);
        setBackground(Color.white);
        add(scroll);
        add(barra, BorderLayout.SOUTH);
    }

    public JTable getTabela() {
        return tabelaGenerica;
    }

    public void setModeloTabela(AbstractTableModel modelo) {
        tabelaGenerica.setModel(modelo);
    }

    public int getPag() {
        return pag;
    }

    public void setPag(int pag) {
    	if(maxPag){
    		if(pag > max)
    			this.pag = max;
    		else if(pag < 0)
    			this.pag = 0;
    		else this.pag = pag;
    		this.pagina.setText(Integer.toString(this.pag));	
    	}
    	else{
    		if(pag < 0)
    			this.pag = 0;
    		else this.pag = pag;
    		this.pagina.setText(Integer.toString(this.pag));
    	}
    }

    public int getLimite(){
    	return ConfigAmana.getLimiteLinhas();
    }
    
    public void setPrimeiroActionListener(ActionListener listener) {
        primeiro.addActionListener(listener);
    }

    public void setAnteriorActionListener(ActionListener listener) {
        anterior.addActionListener(listener);
    }

    public void setProximoActionListener(ActionListener listener) {
        proximo.addActionListener(listener);
    }

    public void setUltimoActionListener(ActionListener listener) {
        ultimo.addActionListener(listener);
    }

    public void setTabelaSelectionListener(ListSelectionListener listener) {
        tabelaGenerica.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void setDisablePag(boolean max){
    	if(max == true){
    		maxPag = max;
    		ultimo.setVisible(false);
        	primeiro.setVisible(false);
    	}
    	else{
        	proximo.setVisible(false);
        	anterior.setVisible(false);
        	ultimo.setVisible(false);
        	primeiro.setVisible(false);
        	pagina.setVisible(false);
    	}
    }

	private void setMaxPag(boolean maxPag) {
		this.maxPag = maxPag;
	}

}
