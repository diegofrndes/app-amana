package espectador.pessoa;

import modelo.Endereco;

public interface EspectadorVisualizarPessoaJuridica {

	public void setNome(String nome);

	public void setCnpj(String cnpj);
	
	public void setIe(String ie);
	
	public void setEndereco(Endereco endereco);
	
	public void setEmail(String email);
	
	public void setObs(String obs);
	
}
