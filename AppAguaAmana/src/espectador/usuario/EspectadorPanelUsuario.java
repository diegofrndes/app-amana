package espectador.usuario;

import gui.swing.usuario.ModeloTabelaUsuario;

public interface EspectadorPanelUsuario {

	public ModeloTabelaUsuario getModeloTabelaUsuario();

	public void setModeloTabelaUsuario(ModeloTabelaUsuario modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
