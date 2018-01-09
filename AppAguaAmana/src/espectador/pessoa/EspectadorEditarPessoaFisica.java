package espectador.pessoa;

import modelo.Endereco;

public interface EspectadorEditarPessoaFisica {
	
	public void setNome(String nome);

	public void setCpf(String cpf);
	
	public void setRg(String rg);
	
	public void setUfRg(String ufrg);
	
	public void setEndereco(Endereco endereco);
	
	public void setEmail(String email);
	
	public void setObs(String obs);	

	public String getNome();

    public Endereco getEndereco();

    public String getRg();

    public String getUfRg();
    
    public String getCpf();
    
    public String getEmail();
    
    public String getObs();

}
