package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResultDto;

public class ResultMapper {
    public static ResultDto mapToDto(ResultResponse resultResponse) {
        return ResultDto.builder()
                .hash(resultResponse.getHash())
                .drawDate(resultResponse.getDrawDate())
                .hitNumbers(resultResponse.getHitNumbers())
                .isWinner(resultResponse.isWinner())
                .numbers(resultResponse.getWonNumbers())
                .wonNumbers(resultResponse.getWonNumbers())
                .build();
    }
}
