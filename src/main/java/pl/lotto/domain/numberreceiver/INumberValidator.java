package pl.lotto.domain.numberreceiver;

import java.util.Set;

interface INumberValidator {
    boolean areAllNumbersInRange(Set<Integer> numbersFromUser);
}
