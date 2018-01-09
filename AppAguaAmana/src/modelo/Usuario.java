package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 * A classe Usuario representa um Usuario do sistema Amana.
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 * @version 1.0
 * @since AMANA 1.0
 */
public class Usuario {
	
	private int id;
    private String login;
    private String senha;
    private PessoaFisica pfUsuario;
    // tipo - 1 administrador
    // tipo - 2 escritório	
    // tipo - 3 producao
    private int tipoUsuario;
    private boolean flag;
    
    /**
     * Aloca um objeto do tipo Usuario
     * 
     * @param id O id do usuario
     * @param login O login do usuario
     * 
     */
    public Usuario(int id, String login) {
        this.id = id;
        this.login = login;
    	pfUsuario = new PessoaFisica(-1);
        tipoUsuario = 3;
    }
    
    /**
     * Aloca um objeto do tipo Usuario
     * 
     * @param id O id do usuario
     * @param login O login do usuario
     * @param senha A senha do usuario
     * 
     */
    public Usuario(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    	pfUsuario = new PessoaFisica(-1);
        tipoUsuario = 3;
    }
    
    /**
     * Retorna o id do usuario
     * 
     * @return O id do usuario
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Retorna o nome do usuario representado pelo objeto.
     *
     * @return O nome do usuario.
     */
    public String getNome() {
        return this.pfUsuario.getNome();
    }

    /**
     * Seta o nome do usuario representado pelo objeto.
     *
     * @param nome O valor do nome
     */
    public void setNome(String nome) {
        this.pfUsuario.setNome(nome);
    }

    /**
     * Retorna o endereco do usuario representado pelo objeto.
     *
     * @return O endereco do usuario.
     */
    public Endereco getEndereco() {
        return this.pfUsuario.getEndereco();
    }

    /**
     * Seta o endereco do usuario representado pelo objeto.
     *
     * @param endereco Um objeto do tipo Endereco
     */
    public void setEndereco(Endereco endereco) {
        this.pfUsuario.setEndereco(endereco);
    }

    /**
     * Retorna o rg do usuario representado pelo objeto.
     *
     * @return O rg do usuario
     */
    public String getRg() {
        return this.pfUsuario.getRg();
    }

    /**
     * Seta o rg do usuario representado pelo objeto.
     *
     * @param rg O rg do usuario
     */
    public void setRg(String rg) {
        this.pfUsuario.setRg(rg);
    }

    /**
     * Retorna o estado onde o rg do usuario foi emitido.
     *
     * @return ufrg O estado do rg do usuario representado pelo objeto
     */
    public String getUfRg() {
        return this.pfUsuario.getUfRg();
    }

    /**
     * Seta o estado onde o rg do usuario foi emitido.
     *
     * @param ufRg O estado onde o rg do usuario foi emitido
     */
    public void setUfRg(String ufRg) {
        this.pfUsuario.setUfRg(ufRg);
    }
    
    /**
     * Retorna o cpf do usuario representado pelo objeto.
     *
     * @return O cpf do usuario representado pelo objeto
     */
    public String getCpf() {
        return this.pfUsuario.getCpf();
    }

    /**
     * Seta o cpf do usuario representado pelo objeto.
     *
     * @param cpf O cpf do usuario representado pelo objeto
     */
    public void setCpf(String cpf) {
        this.pfUsuario.setCpf(cpf);
    }
    
    /**
     * Retorna o login do usuario representado pelo objeto.
     *
     * @return O login do usuario.
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Retorna a senha do usuario representado pelo objeto.
     *
     * @return A senha do usuario.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Encripta e seta a senha do usuario.
     *
     * @param senha A senha do usuario
     */
    public void setSenha(String senha) throws NoSuchAlgorithmException {
    	MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(senha.getBytes());
        Base64 encoder = new Base64();
        this.senha = encoder.encodeAsString(digest.digest());
    }

    /**
     * Retorna o tipo do usuario representado pelo objeto.
     *
     * @return O tipo do usuario
     */    
    public int getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Seta o tipo do usuario (por padrao o tipo e 3).
     *
     * @param tipo O tipo do usuario
     */
    public void setTipoUsuario(int tipo) {
        if (tipo < 0 || tipo > 3) {
            this.tipoUsuario = 3;
        } else {
            this.tipoUsuario = tipo;
        }
    }
    
    public boolean isFlag(){
    	return flag;
    }
    
    public void setFlag(boolean flag){
    	this.flag = flag;
    }
    
    @Override
    public boolean equals(Object obj){
        if( obj instanceof Usuario )
            return (((Usuario) obj).getId() == this.getId());
        else return false;
    }
    
}
