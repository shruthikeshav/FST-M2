package Activities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    void  notEnoughFunds(){
        BankAccount account = new BankAccount(10);

        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(100));
    }

    @Test
    void enoughFunds(){
        BankAccount account = new BankAccount(100);

        assertDoesNotThrow(() -> account.withdraw(99));
    }
}