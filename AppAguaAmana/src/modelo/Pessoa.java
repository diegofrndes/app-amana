package modelo;

import java.math.BigDecimal;

/**
 * A classe Pessoa representa uma Pessoa.
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 * @version 1.0
 * @since AMANA 1.0
 */
public class Pessoa {
	private int id;
    private String nome;
    private Endereco endereco;
    private String email;
    private String obs;
    private int tipo;
    private BigDecimal limite;
    private BigDecimal debito;
    private BigDecimal credito;
    
    /**
     * Aloca um objeto do tipo Pessoa com o id passado pelo construtor
     * 
     * @param id O id da pessoa
     */
    public Pessoa(int id) {
        this.id = id;
        debito = new BigDecimal(0.00);
        credito = new BigDecimal(0.00);
    }
    
    
    /**
     * Retorna o id da pessoa representada pelo objeto.
     *
     * @return O id da pessoa.
     */
    public int getId(){
    	return this.id;
    }
    
    /**
     * Retorna o nome da pessoa representada pelo objeto.
     *
     * @return O nome da pessoa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Seta o nome da pessoa representada pelo objeto.
     *
     * @param nome O valor do nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o endereco da pessoa representada pelo objeto.
     *
     * @return O endereco da pessoa.
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Seta o endereço da pessoa representada pelo objeto.
     *
     * @param endereco Um objeto do tipo Endereco
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o e-mail da pessoa representada pelo objeto.
     *
     * @return O e-mail da pessoa.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seta o e-mail da pessoa representada pelo objeto.
     *
     * @param email O valor do e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a observacao da pessoa representada pelo objeto.
     *
     * @return A observacao da pessoa.
     */
    public String getObs() {
        return obs;
    }

    /**
     * Seta a observacao da pessoa representada pelo objeto.
     *
     * @param obs O valor da observacao
     */
    public void setObs(String obs) {
        this.obs = obs;
    }

    /**
     * Retorna o tipo da pessoa representada pelo objeto.
     *
     * @return O tipo da pessoa.
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Seta o tipo da pessoa representada pelo objeto.
     *
     * @param tipo O valor do tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


	public BigDecimal getLimite() {
		return limite;
	}


	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}


	public BigDecimal getDebito() {
		return debito;
	}


	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}
	
	public BigDecimal getCredito() {
		return credito;
	}


	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}
	

}
