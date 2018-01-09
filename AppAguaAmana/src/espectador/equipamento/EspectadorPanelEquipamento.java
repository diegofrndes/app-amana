package espectador.equipamento;

import gui.swing.equipamento.ModeloTabelaEquipamento;

public interface EspectadorPanelEquipamento {

	public ModeloTabelaEquipamento getModeloTabelaEquipamento();
	
	public void setModeloTabelaEquipamento(ModeloTabelaEquipamento modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
