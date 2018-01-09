package modelo;

/**
 * A classe Endereco representa um endereco com os padroes de enderecamento 
 * utilizado pelo Brasil.
 *
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public class Endereco {

    private String bairro;
    private String rua;
    private String numero;
    private String ddd;
    private String telefone;
    private String cep;
    private String uf;
    private String cidade;

    /**
     * Aloca um objeto do tipo Endereco e inicializa os campos, criando um
     * endereco vazio.
     *
     * @param bairro O valor do bairro
     * @param rua O valor da rua
     * @param numero O valor do numero
     * @param ddd O valor do ddd
     * @param telefone O valor do telefone
     * @param cep O valor do cep
     * @param uf O valor do uf
     * @param cidade O valor da cidade
     */
    public Endereco(String bairro, String rua, String numero, String ddd, 
    		String telefone, String cep, String uf, String cidade) 
    {
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.ddd = ddd;
        this.telefone = telefone;
        this.cep = cep;
        this.uf = uf;
        this.cidade = cidade;
    }

    /**
     * Retorna o bairro do endereco representado pelo objeto.
     *
     * @return O bairro do endereco.
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Retorna a rua do endereço representado pelo objeto.
     *
     * @return A rua do endereço.
     */
    public String getRua() {
        return rua;
    }

    /**
     * Retorna o numero do endereco representado pelo objeto.
     *
     * @return O numero do endereco.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Retorna o ddd do endereco representado pelo objeto.
     *
     * @return O ddd do endereco.
     */
    public String getDdd() {
        return ddd;
    }

    /**
     * Seta o ddd do endereco representado pelo objeto.
     *
     * @param ddd O valor do ddd
     */
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    /**
     * Retorna o telefone do endereco representado pelo objeto.
     *
     * @return O telefone do endereco.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Retorna o cep do endereco representado pelo objeto.
     *
     * @return O cep do endereco.
     */
    public String getCep() {
        return cep;
    }

    /**
     * Retorna o estado do endereco representado pelo objeto.
     *
     * @return O estado do endereco.
     */
    public String getUf() {
        return uf;
    }

    /**
     * Retorna a cidade do endereco representado pelo objeto.
     *
     * @return A cidade do endereco.
     */
    public String getCidade() {
        return cidade;
    }
    
     /**
     * Compara se um endereco e igual ao outro.
     *
     * @return True se o endereco for igual, false caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Endereco) {
            return ( ((Endereco) obj).getBairro().equals(this.getBairro()) 
                   && ((Endereco) obj).getRua().equals(this.getRua())
                   && ((Endereco) obj).getCep().equals(this.getCep())
                   && ((Endereco) obj).getCidade().equals(this.getCidade())
                   && ((Endereco) obj).getDdd().equals(this.getDdd())
                   && ((Endereco) obj).getNumero().equals(this.getNumero())
                   && ((Endereco) obj).getTelefone().equals(this.getTelefone())
                   && ((Endereco) obj).getUf().equals(this.getUf())
                   );
        } else {
            return false;
        }
    }
}
