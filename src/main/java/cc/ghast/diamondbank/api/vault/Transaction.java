package cc.ghast.diamondbank.api.vault;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ghast
 * @since 13-Apr-20
 */
public class Transaction {

    private final UUID sender, target;
    private final Date date;
    public final double amount;

    public Transaction(double amount, UUID sender, UUID target) {
        this.amount = amount;
        this.sender = sender;
        this.target = target;
        this.date = Calendar.getInstance().getTime();
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getTarget() {
        return target;
    }

    public Date getDate() {
        return date;
    }


}
