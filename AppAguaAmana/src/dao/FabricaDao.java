package dao;

import dao.jdbc.JDBCChequeDao;
import dao.jdbc.JDBCEquipamentoDao;
import dao.jdbc.JDBCItemDao;
import dao.jdbc.JDBCPatrimonioDao;
import dao.jdbc.JDBCPessoaDao;
import dao.jdbc.JDBCPessoaFisicaDao;
import dao.jdbc.JDBCPessoaJuridicaDao;
import dao.jdbc.JDBCProdutoDao;
import dao.jdbc.JDBCUsuarioDao;
import dao.jdbc.JDBCVendaDao;

/**
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public class FabricaDao {
    public static UsuarioDao getUsuarioDao(){
        return new JDBCUsuarioDao();
    }
    
    public static PatrimonioDao getPatrimonioDao(){
        return new JDBCPatrimonioDao();
    }
    
    public static EquipamentoDao getEquipamentoDao(){
        return new JDBCEquipamentoDao();
    }
    
    public static PessoaFisicaDao getPessoaFisicaDao(){
        return new JDBCPessoaFisicaDao();
    }
    
    public static PessoaJuridicaDao getPessoaJuridicaDao(){
        return new JDBCPessoaJuridicaDao();
    }
    
    public static PessoaDao getPessoaDao(){
        return new JDBCPessoaDao();
    }
    
    public static ProdutoDao getProdutoDao(){
        return new JDBCProdutoDao();
    }
    
    public static ItemDao getItemDao(){
        return new JDBCItemDao();
    }

    public static VendaDao getVendaDao(){
        return new JDBCVendaDao();
    }
    
    public static ChequeDao getChequeDao(){
        return new JDBCChequeDao();
    }

}
