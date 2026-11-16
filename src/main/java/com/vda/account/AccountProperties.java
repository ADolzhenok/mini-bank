package com.vda.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    private final int defaultAmount;
    private final double transferCommission;

    public AccountProperties(
        @Value("${account.default-amount}") int defaultAmount,
        @Value("${account.transfer-commission}") double transferCommission
    ){
        validProperties(defaultAmount, transferCommission);
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    private void validProperties(int amount, double transferCommission){
        if (amount < 0)
            throw new IllegalStateException("Property 'defaultAmount' must be not negative");
        if (transferCommission < 0 || transferCommission > 1)
            throw new IllegalStateException("Property 'transferCommission' must be in range [0,1]");
    }
}
