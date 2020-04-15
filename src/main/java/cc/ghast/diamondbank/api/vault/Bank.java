package cc.ghast.diamondbank.api.vault;

import java.util.UUID;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class Bank {
    private final UUID uuid;
    private long balance;

    public Bank(UUID uuid, long balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void addBalance(long balance) {
        this.balance += balance;
    }

    public void removeBalance(long balance) {
        this.balance -= balance;
    }
}
