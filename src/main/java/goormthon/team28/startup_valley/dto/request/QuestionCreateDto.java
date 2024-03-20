package goormthon.team28.startup_valley.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QuestionCreateDto(
        @JsonProperty("memberId")
        Long memberId,
        @JsonProperty("content")
        String content
) {
}
