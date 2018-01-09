package modelo;

/**
 * A classe PessoaFisica e uma classe que herda os dados da classe Pessoa e
 * acrescenta os membros e metodos necessarios para representar uma Pessoa
 * Fisica.
 *
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public class PessoaFisica extends Pessoa {

    private String rg;
    private String ufRg;
    private String cpf;

    /**
     * Aloca um objeto do tipo PessoaFisica
     * 
     * @param id O id da pessoa
     */
    public PessoaFisica(int id) {
        super(id);
        setTipo(0);
    }

    /**
     * Retorna o rg da pessoa fisica representada pelo objeto.
     *
     * @return O rg da pessoa.
     */
    public String getRg() {
        return rg;
    }

    /**
     * Seta o rg da pessoa fisica representada pelo objeto.
     *
     * @param rg O valor do rg
     */
    public void setRg(String rg) {
        this.rg = rg;
    }

    /**
     * Retorna o estado em que o rg da pessoa fisica foi emitido.
     *
     * @return O uf do rg da pessoa fisica.
     */
    public String getUfRg() {
        return ufRg;
    }

    /**
     * Seta o estado em que o rg da pessoa fisica foi emitido.
     *
     * @param ufRg O estado de emissao do rg
     */
    public void setUfRg(String ufRg) {
        this.ufRg = ufRg;
    }

    /**
     * Retorna o cpf da pessoa fisica representada pelo objeto.
     *
     * @return O cpf da pessoa.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Seta o cpf da pessoa fisica representada pelo objeto.
     *
     * @param cpf O valor do cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
}

