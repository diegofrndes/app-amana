package espectador.pessoa;

import modelo.Endereco;

public interface EspectadorEditarPessoaJuridica {

	public String getNome();

    public Endereco getEndereco();

    public String getIe();

    public String getCnpj();
    
    public String getEmail();
    
    public String getObs();

	public void setNome(String nome);

	public void setCnpj(String cnpj);
	
	public void setIe(String ie);
	
	public void setEndereco(Endereco endereco);
	
	public void setEmail(String email);
	
	public void setObs(String obs);
	
}
