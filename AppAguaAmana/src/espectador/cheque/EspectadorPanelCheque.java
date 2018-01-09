package espectador.cheque;

import gui.swing.cheque.ModeloTabelaCheque;

public interface EspectadorPanelCheque {

	public ModeloTabelaCheque getModeloTabelaCheque();

	public void setModeloTabelaCheque(ModeloTabelaCheque modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();
	
}
