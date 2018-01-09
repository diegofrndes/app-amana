package gui.swing.usuario;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Usuario;

public class ModeloTabelaUsuario extends AbstractTableModel {  
    
	private static final long serialVersionUID = 1L;
	private List<Usuario> usuarios;  
      
    public ModeloTabelaUsuario(List<Usuario> usuarios) {  
        this.usuarios = usuarios;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Usuario usuario = (Usuario) usuarios.get(rowIndex);  
        if (usuario != null) {  
            switch (columnIndex) {  
            	//Nome, Cpf, Telefone, Login, Tipo  
            	case 0:
                    return usuario.getNome();
                case 1:
                	String cpf = usuario.getCpf();
                    return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11));                       	
                case 2:
                	String tel = "(" + usuario.getEndereco().getDdd() + ")"
                				 +" " + usuario.getEndereco().getTelefone().substring(0, 4) + 
                				 "-" + usuario.getEndereco().getTelefone().substring(4, 8);
                    return tel;
                case 3:
                	return usuario.getLogin();
                case 4:
                	if(usuario.getTipoUsuario() == 1){
                		return "Administração";
                	}
                	else if (usuario.getTipoUsuario() == 2){
                		return "Escritório";
                	}
                	else if(usuario.getTipoUsuario() == 3){
                		return "Produção";
                	}
                	else return "Desconhecido";
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (usuarios != null) {  
            return this.usuarios.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        //Nome, Cpf, Rg, Login, Tipo  
        return 5;  
    }  
      
    public Usuario getUsuario(int row) {  
        if (row >= 0) {  
            return (Usuario) this.usuarios.get(row);  
        }  
        return null;  
    }  
    
}  
