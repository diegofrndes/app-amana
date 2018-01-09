package espectador.usuario;

import modelo.Endereco;

public interface EspectadorEditarUsuario {
	
	public String getNome();

    public Endereco getEndereco();

    public String getRg();

    public String getUfRg();
    
    public String getCpf();
    
    public String getLogin();

    public String getSenha();

    public int getTipoUsuario();
    
    public void setNome(String nome);

    public void setEndereco(Endereco endereco);

    public void setRg(String rg);

    public void setUfRg(String ufrg);
    
    public void setCpf(String cpf);
    
    public void setTipoUsuario(int tipo);
    
}
