package modelo;

/**
 * A classe PessoaJuridica e uma classe que herda os dados da classe Pessoa e
 * acrescenta os membros e metodos necessarios para representar uma Pessoa
 * Juridica.
 *
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public class PessoaJuridica extends Pessoa {

    private String ie;
    private String cnpj;

    /**
     * Aloca um objeto do tipo PessoaJuridica
     * 
     * @param id O id da pessoa
     */
    public PessoaJuridica(int id) {
        super(id);
        setTipo(1);
    }

    /**
     * Retorna a inscricao estaducal da pessoa fisica representada pelo objeto.
     *
     * @return A inscricao estadual da pessoa.
     */
    public String getIe() {
        return ie;
    }

    /**
     * Seta a inscricao estadual da pessoa juridica representada pelo objeto.
     *
     * @param rg O valor do rg
     */
    public void setIe(String ie) {
        this.ie = ie;
    }

    /**
     * Retorna o cnpj da pessoa juridica representada pelo objeto.
     *
     * @return O cnpj da pessoa.
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Seta o cnpj da pessoa juridica representada pelo objeto.
     *
     * @param cnpj O valor do cnpj
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}

