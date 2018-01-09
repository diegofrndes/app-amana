package apresentador.venda;

import espectador.venda.EspectadorVendaExcluida;
import modelo.VendaExcluida;

public class ApresentadorVendaExcluida{
	public ApresentadorVendaExcluida(EspectadorVendaExcluida espectador, VendaExcluida modelo){
		espectador.setData(modelo.getData());
		espectador.setMotivo(modelo.getMotivo());
		espectador.setUsuario(modelo.getUsuario());
	}
}
