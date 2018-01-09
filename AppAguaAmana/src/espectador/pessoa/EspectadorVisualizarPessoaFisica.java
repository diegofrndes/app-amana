package espectador.pessoa;

import modelo.Endereco;

public interface EspectadorVisualizarPessoaFisica {
	
	public void setNome(String nome);

	public void setCpf(String cpf);
	
	public void setRg(String rg);
	
	public void setUfRg(String ufrg);
	
	public void setEndereco(Endereco endereco);
	
	public void setEmail(String email);
	
	public void setObs(String obs);
	
}
