package ru.informatics.jojomafia.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.informatics.jojomafia.utils.ModelBuilder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorDto implements Dto{

    private String errorMessage;

    private ErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static class Builder implements ModelBuilder<ErrorDto> {

        private String errorMessage;

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        @Override
        public ErrorDto build() {
            return new ErrorDto(this.errorMessage);
        }
    }
}
