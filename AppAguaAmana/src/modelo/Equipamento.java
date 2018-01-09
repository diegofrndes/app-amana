package modelo;
/*
* A classe Equipamento representa um equipamento do sistema Amana.
*
* @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
* @version 1.0
* @since AMANA 1.0
*/
public class Equipamento {

   private int id;
   private String nome;
   private String descricao;
   private String modelo;
   private String fabricante;
   private String numeroserie;
   private String ultmanutencao;
   private String proxmanutencao;   
   private int freq;
   private boolean flag;
   
   /**
    * Aloca um objeto do tipo Equipamento e inicializa os campos, criando um
    * equipamento vazio.
    */
   public Equipamento(int id, String numeroserie) {
       this.id = id;
	   this.numeroserie = numeroserie;
	   ultmanutencao = "";
	   proxmanutencao = "";
   }

   /**
    * Retorna o id do equipamento representado pelo objeto.
    *
    * @return O id do equipamento.
    */
   public int getId() {
       return id;
   }

   /**
    * Retorna o nome do equipamento representado pelo objeto.
    *
    * @return O nome do equipamento.
    */
   public String getNome() {
       return nome;
   }

   /**
    * Seta o nome do equipamento representado pelo objeto.
    *
    * @param nome O valor do nome
    */
   public void setNome(String nome) {
       this.nome = nome;
   }

   /**
    * Retorna a descrição do equipamento representado pelo objeto.
    *
    * @return A descrição do equipamento.
    */
   public String getDescricao() {
       return descricao;
   }

   /**
    * Seta a descrição do equipamento representado pelo objeto.
    *
    * @param descricao O valor da descrição
    */
   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }

   /**
    * Retorna o modelo do equipamento representado pelo objeto.
    *
    * @return O modelo do equipamento.
    */
   public String getModelo() {
       return modelo;
   }

   /**
    * Seta o modelo do equipamento representado pelo objeto.
    *
    * @param modelo O valor do modelo
    */
   public void setModelo(String modelo) {
       this.modelo = modelo;
   }

   /**
    * Retorna o fabricante do equipamento representado pelo objeto.
    *
    * @return O fabricante do equipamento.
    */
   public String getFabricante() {
       return fabricante;
   }

   /**
    * Seta o fabricante do equipamento representado pelo objeto.
    *
    * @param fabricante O valor do fabricante
    */
   public void setFabricante(String fabricante) {
       this.fabricante = fabricante;
   }

   /**
    * Retorna o numero de série do equipamento representado pelo objeto.
    *
    * @return O número de série equipamento.
    */
   public String getNumeroserie() {
       return numeroserie;
   }

   public void setNumeroserie(String numeroserie){
	   this.numeroserie = numeroserie;
   }
   
   /**
    * Retorna a data da última manutenção (no formato mysql) do equipamento
    * representado pelo objeto.
    *
    * @return A data da última manutenção do equipamento ( formato mysql ).
    
   public String getUltmanutencao() {
       return ultmanutencao;
   }

   /**
    * Seta a data da última manutenção (no formato mysql) do equipamento
    * representado pelo objeto.
    *
    * @param ultmanutencao O valor da última manutenção
    
   public void setUltmanutencao(String ultmanutencao) {
       this.ultmanutencao = ultmanutencao;
   }
	*/
   
   /**
    * Retorna a frequência ( em meses ) que o equipamento precisa de
    * manutenção.
    *
    * @return A frequência ( em meses) de manutenção do equipamento.
    */
   public int getFreq() {
       return freq;
   }

   /**
    * Seta a frequência ( em meses ) que o equipamento precisa de manutenção.
    *
    * @param freq O valor da frequência
    */
   public void setFreq(int freq) {
       this.freq = freq;
   }
   
   public boolean isFlag() {
       return flag;
   }
   
   /**
    * Compara se um equipamento é igual a outro.
    *
    * @return True se o equipamento for igual, false caso contrário.
    */
   @Override
   public boolean equals(Object obj){
       if( obj instanceof Equipamento )
           return (((Equipamento) obj).getId() == this.getId());
       else return false;
   }

   public String getUltmanutencao() {
	   return ultmanutencao;
   }

   public void setUltmanutencao(String ultmanutencao) {
	   this.ultmanutencao = ultmanutencao;
   }

   public String getProxmanutencao() {
	   return proxmanutencao;
   }

   public void setProxmanutencao(String proxmanutencao) {
	   this.proxmanutencao = proxmanutencao;
   }

}
