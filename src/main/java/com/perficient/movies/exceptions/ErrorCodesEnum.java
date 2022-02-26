package com.perficient.movies.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCodesEnum {

    ACTOR_NOT_FOUND("000", HttpStatus.NOT_FOUND),

    DIRECTOR_NOT_FOUND("001", HttpStatus.NOT_FOUND),

    DATA_INCOMPLETE("002", HttpStatus.BAD_REQUEST),

    MOVIE_NOT_FOUND("003", HttpStatus.NOT_FOUND),

    ACTOR_NOT_FOUND_IN_MOVIE("004", HttpStatus.PRECONDITION_FAILED),

    DIRECTOR_NOT_FOUND_IN_MOVIE("004", HttpStatus.PRECONDITION_FAILED),

    AWARD_NOT_FOUND("005", HttpStatus.NOT_FOUND),

    AWARD_ALREADY_HAS_A_WINNER("006", HttpStatus.PRECONDITION_FAILED);

    private String code;

    private HttpStatus status;

}
