package pl.lotto.domain.numberreceiver;

import java.util.Set;

interface INumberValidator {
    String validation(Set<Integer> numbersFromUser);
}
