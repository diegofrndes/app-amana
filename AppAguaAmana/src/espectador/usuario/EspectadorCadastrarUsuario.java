package espectador.usuario;

import modelo.Endereco;

public interface EspectadorCadastrarUsuario {
	
	public String getNome();

    public Endereco getEndereco();

    public String getRg();

    public String getUfRg();
    
    public String getCpf();
    
    public String getLogin();

    public String getSenha();

    public int getTipoUsuario();

}
