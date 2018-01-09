package gui.swing.pessoa;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.PessoaFisica;

public class ModeloTabelaPessoaFisica extends AbstractTableModel {  
    
	private static final long serialVersionUID = 1L;
	private List<PessoaFisica> clientes;  
      
    public ModeloTabelaPessoaFisica(List<PessoaFisica> clientes) {  
        this.clientes = clientes;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        PessoaFisica cliente = (PessoaFisica) clientes.get(rowIndex);  
        if (cliente != null) {  
            switch (columnIndex) {  
            	case 0:
            		return cliente.getId();
            	case 1:
                    return cliente.getNome();
                case 2:
                    return cliente.getRg();
                case 3:
                	String cpf = cliente.getCpf();
                    return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11));                       	
                case 4:
                    String telefone = cliente.getEndereco().getTelefone();
                	return (telefone.substring(0,4) + "-" + telefone.substring(4,8));
                case 5:
                	return cliente.getEmail();
                case 6:
                	return cliente.getLimite();
                case 7:
                	return cliente.getDebito();
                case 8:
                	return cliente.getCredito();
                
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (clientes != null) {  
            return this.clientes.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        // Id, Nome, Cpf, Login, Tipo, email, limite  
        return 9;  
    }  
      
    public PessoaFisica getPessoa(int row) {  
        if (row >= 0) {  
            return (PessoaFisica) this.clientes.get(row);  
        }  
        return null;  
    }  
    
}  
