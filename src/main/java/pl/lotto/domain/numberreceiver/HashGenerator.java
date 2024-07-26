package pl.lotto.domain.numberreceiver;

import java.util.UUID;

class HashGenerator implements IHashGenerable {
    @Override
    public String getHash() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
