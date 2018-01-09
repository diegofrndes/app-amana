package espectador.patrimonio;

import gui.swing.patrimonio.ModeloTabelaPatrimonio;

public interface EspectadorPanelPatrimonio {
	
	public ModeloTabelaPatrimonio getModeloTabelaPatrimonio();
	
	public void setModeloTabelaPatrimonio(ModeloTabelaPatrimonio modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
