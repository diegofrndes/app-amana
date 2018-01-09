package espectador.patrimonio;

import java.math.BigDecimal;

public interface EspectadorVisualizarPatrimonio {
	
	public void setNome(String nome);
	
	public void setDescricao(String descricao);
	
	public void setModelo(String modelo);
	
	public void setFabricante(String fabricante);
	
	public void setQuantidade(int quantidade);
	
	public void setValor(BigDecimal valor);

}
