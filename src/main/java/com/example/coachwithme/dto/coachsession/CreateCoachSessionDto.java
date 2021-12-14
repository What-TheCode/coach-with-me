package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.model.Location;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCoachSessionDto {

    @NonNull
    @Positive
    private int topicId;

    @FutureOrPresent(message = "Book a time in the future")
    private LocalDate date;

    @NotBlank(message = "Time cannot be empty")
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss[.SSS][.SS][.S]")
    private String time;

    private Location location;

    private String remarks;

    private int coachId;

    private int coacheeId;
}
