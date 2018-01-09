package espectador.pessoa;

import gui.swing.pessoa.ModeloTabelaPessoaFisica;

public interface EspectadorPanelPessoaFisica {
	public ModeloTabelaPessoaFisica getModeloTabelaPessoaFisica();
	public void setModeloTabelaPessoaFisica(ModeloTabelaPessoaFisica modelo);
    public String getBusca();
    public String getFiltro();
    public int getStart();
    public int getLimite();
}
