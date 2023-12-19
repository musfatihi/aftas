package ma.fatihii.aftas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.fatihii.aftas.dto.competition.CompetitionReq;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalTime;


public class StartEndTimeValidator implements ConstraintValidator<StartEndTimeValid, CompetitionReq> {

    private String startTime;
    private String endTime;

    @Override
    public void initialize(StartEndTimeValid constraintAnnotation) {
        startTime = constraintAnnotation.first();
        endTime = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(CompetitionReq req, ConstraintValidatorContext constraintValidatorContext) {

        return req.getEndTime().isAfter(req.getStartTime());
    }
}
