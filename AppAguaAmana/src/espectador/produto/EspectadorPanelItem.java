package espectador.produto;

import gui.swing.produto.ModeloTabelaItem;


public interface EspectadorPanelItem {
	public ModeloTabelaItem getModeloTabelaItem();
	public void setModeloTabelaItem(ModeloTabelaItem modelo);
    public String getBusca();
    public String getFiltro();
    public int getStart();
    public int getLimite();
}
