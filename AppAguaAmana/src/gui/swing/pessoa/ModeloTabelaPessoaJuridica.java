package gui.swing.pessoa;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.PessoaJuridica;

public class ModeloTabelaPessoaJuridica extends AbstractTableModel {  
    
	private static final long serialVersionUID = 1L;
	private List<PessoaJuridica> clientes;  
      
    public ModeloTabelaPessoaJuridica(List<PessoaJuridica> clientes) {  
        this.clientes = clientes;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        PessoaJuridica cliente = (PessoaJuridica) clientes.get(rowIndex);  
        if (cliente != null) {  
            switch (columnIndex) {  
            	case 0:
            		return cliente.getId();
            	case 1:
                    return cliente.getNome();
                case 2:
                    return cliente.getIe();
                case 3:
                	String cnpj = cliente.getCnpj();
                	return (cnpj.substring(0, 2) + "." 
                			+ cnpj.substring(2, 5) + "."
                			+ cnpj.substring(5, 8) + "/"
                			+ cnpj.substring(8, 12) + "-"
                			+ cnpj.substring(12, 14));
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
        // Id, Nome, Cpf, Login, Tipo  
        return 9;  
    }  
      
    public PessoaJuridica getPessoa(int row) {  
        if (row >= 0) {  
            return (PessoaJuridica) this.clientes.get(row);  
        }  
        return null;  
    }  
    
}  
