package espectador.venda;

import gui.swing.venda.ModeloTabelaVenda;

public interface EspectadorPanelVenda {

	public ModeloTabelaVenda getModeloTabelaVenda();

	public void setModeloTabelaVenda(ModeloTabelaVenda modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
