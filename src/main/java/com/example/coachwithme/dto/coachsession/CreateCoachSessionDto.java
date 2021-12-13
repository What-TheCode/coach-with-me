package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.model.Location;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

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
