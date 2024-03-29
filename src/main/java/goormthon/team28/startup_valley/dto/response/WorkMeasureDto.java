package goormthon.team28.startup_valley.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record WorkMeasureDto(
        @JsonProperty("userNickName")
        String userNickname,
        @JsonProperty("totalTime")
        Long totalTime,
        @JsonProperty("workDateCount")
        Integer workDateCount,
        @JsonProperty("workMaxTime")
        Long workMaxTime,
        @JsonProperty("workDateList")
        List<WorkDateDto> workDateDtoList
) implements Serializable {
    public static WorkMeasureDto of(
            final String userNickname,
            final Long totalTime,
            final Integer workDateCount,
            final Long workMaxTime,
            final List<WorkDateDto> workDateDtoList
    ) {
        return WorkMeasureDto.builder()
                .userNickname(userNickname)
                .totalTime(totalTime)
                .workDateCount(workDateCount)
                .workMaxTime(workMaxTime)
                .workDateDtoList(workDateDtoList)
                .build();
    }
}
