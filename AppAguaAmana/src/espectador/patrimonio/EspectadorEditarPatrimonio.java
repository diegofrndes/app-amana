package espectador.patrimonio;

import java.math.BigDecimal;

public interface EspectadorEditarPatrimonio {

	public String getNome();
	
	public String getDescricao();
	
	public String getModelo();
	
	public String getFabricante();
	
	public int getQuantidade();
	
	public BigDecimal getValor();
	
	public void setNome(String nome);
	
	public void setDescricao(String descricao);
	
	public void setModelo(String modelo);
	
	public void setFabricante(String fabricante);
	
	public void setQuantidade(int quantidade);
	
	public void setValor(BigDecimal valor);
	
}
