package goormthon.team28.startup_valley.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record TeamSummaryDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("profileImage")
        String profileImage,
        @JsonProperty("startAt")
        String startAt
) implements Serializable {
    public static TeamSummaryDto of(
            final Long id,
            final String name,
            final String profileImage,
            final LocalDate startAt
    ) {
        return TeamSummaryDto.builder()
                .id(id)
                .name(name)
                .profileImage(profileImage)
                .startAt(startAt.toString())
                .build();
    }
}
