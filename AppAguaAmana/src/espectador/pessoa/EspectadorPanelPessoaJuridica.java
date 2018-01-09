package espectador.pessoa;

import gui.swing.pessoa.ModeloTabelaPessoaJuridica;

public interface EspectadorPanelPessoaJuridica {
	
	public ModeloTabelaPessoaJuridica getModeloTabelaPessoaJuridica();
	
	public void setModeloTabelaPessoaJuridica(ModeloTabelaPessoaJuridica modelo);
    
	public String getBusca();
    
	public String getFiltro();
    
	public int getStart();
    
	public int getLimite();

}
