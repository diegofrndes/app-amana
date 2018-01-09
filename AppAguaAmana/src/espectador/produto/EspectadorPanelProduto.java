package espectador.produto;

import gui.swing.produto.ModeloTabelaProduto;

public interface EspectadorPanelProduto {
	
	public ModeloTabelaProduto getModeloTabelaProduto();
	
	public void setModeloTabelaProduto(ModeloTabelaProduto modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
