package cc.ghast.diamondbank.api.sqlite;

import cc.ghast.diamondbank.api.vault.Transaction;
import cc.ghast.diamondbank.utils.BUtil;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author Ghast
 * @since 05-Apr-20
 */
public class Schema {
        private static final String CREATE_ACCOUNT = "INSERT INTO accounts (`uuid`,`date`,`balance`,`last_update`, `active`) VALUES (?,?,?,?,?)";
    private static final String CREATE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " uuid VARCHAR(255), date VARCHAR(255), balance DECIMAL, last_update VARCHAR(255), active BIT)";
    private static final String CREATE_TRANSACTION = "INSERT INTO transactions (`uuid`, `date`, `target`, `amount`) VALUES (?,?,?,?)";
    private static final String CREATE_TRANSACTIONS = "CREATE TABLE IF NOT EXISTS transactions (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " uuid VARCHAR(255), date VARCHAR(255), target VARCHAR(255), amount DECIMAL)";
    private static final String UPDATE_BALANCE = "UPDATE accounts SET `balance`=?, `last_update`=? WHERE `uuid`=?";
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";
    private static final String SELECT_ONE_ACCOUNT = "SELECT * FROM accounts WHERE `uuid`=?";
    private static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transactions";
    private static final String SELECT_ALL_TRANSACTIONS_USER = "SELECT * FROM transactions WHERE `uuid`=?";

    public static ExecutableStatement createData(Player player){
        ExecutableStatement query = Query.prepare(CREATE_ACCOUNT);
        query.append(player.getUniqueId().toString())
                .append(BUtil.fromDate(Calendar.getInstance().getTime()))
                .append(0)
                .append("N/A")
                .append(true);
        return query;
    }

    public static ExecutableStatement createServer(){
        return Query.prepare(CREATE_ACCOUNTS);
    }

    public static ExecutableStatement createTransactions(){
        return Query.prepare(CREATE_TRANSACTIONS);
    }

    public static ExecutableStatement createTransaction(Transaction transaction){
        ExecutableStatement query = Query.prepare(CREATE_TRANSACTION);
        query.append(transaction.getSender().toString())
                .append(BUtil.fromDate(transaction.getDate()))
                .append(transaction.getTarget().toString())
                .append(transaction.amount);
        return query;
    }

    public static ExecutableStatement updateBalance(UUID target, double amount){
        ExecutableStatement query = Query.prepare(UPDATE_BALANCE);
        query.append(amount).append(BUtil.fromDate(Calendar.getInstance().getTime())).append(target.toString());
        return query;
    }

    public static boolean accountExist(UUID uuid){
        ExecutableStatement query = Query.prepare(SELECT_ONE_ACCOUNT);
        query.append(uuid.toString());
        ResultSet set = query.executeQuery();
        try {
            return set.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static long getBalance(UUID uuid){
        ExecutableStatement query = Query.prepare(SELECT_ONE_ACCOUNT);
        query.append(uuid.toString());
        ResultSet set = query.executeQuery();
        double balance = 0;
        try {
            while (set.next()){
                balance = set.getDouble("balance");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Math.round(balance);
    }


}
