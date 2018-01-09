package apresentador.patrimonio;

import dao.FabricaDao;
import espectador.patrimonio.EspectadorEstimativaPatrimonio;

public class ApresentadorEstimativaPatrimonio {
	
	public ApresentadorEstimativaPatrimonio(EspectadorEstimativaPatrimonio espectador){
		espectador.setEstimativa(FabricaDao.getPatrimonioDao().estimativaPatrimonio());
	}
}
