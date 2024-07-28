package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResponseDto;

public class ResultMapper {
    public static ResponseDto mapToDto(ResultResponse resultResponse) {
        return ResponseDto.builder()
                .hash(resultResponse.getHash())
                .drawDate(resultResponse.getDrawDate())
                .hitNumbers(resultResponse.getHitNumbers())
                .isWinner(resultResponse.isWinner())
                .numbers(resultResponse.getWonNumbers())
                .wonNumbers(resultResponse.getWonNumbers())
                .build();
    }
}
