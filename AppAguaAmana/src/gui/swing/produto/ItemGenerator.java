package gui.swing.produto;

import java.text.SimpleDateFormat;
import java.util.Date;

import modelo.Item;

import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.xy.XYDataset;

import dao.FabricaDao;

public class ItemGenerator implements XYToolTipGenerator {
   private int id;
   public ItemGenerator(int id){
	   this.id = id;
   }

   @Override
   public String generateToolTip(XYDataset dataset, int series, int category) {
	  String resultado = "";   
      
      try{
    	  Date data = new Date((long) dataset.getXValue(series, category));
    	  Number quantidade = (int)dataset.getYValue(series, category);
          SimpleDateFormat formatador = new SimpleDateFormat("dd-MMM-yyyy kk:mm:ss");
          SimpleDateFormat formatadorBanco = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
          String valorData = formatador.format(data);
          resultado = "<html>Data: " + valorData;
          resultado = resultado + "<br>Quantidade: " + quantidade;
          Item temp = new Item(id);
          String motivo = FabricaDao.getItemDao().motivo(temp, formatadorBanco.format(data));
          resultado = resultado + "<br>Descrição: " + motivo + "</html>";  
      } catch (Exception e){
    	  return "";
      }
      
      return resultado;
   }
}