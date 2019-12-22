package de.jonasborn.gustav.exception


import de.jonasborn.gustav.web.Result
import de.jonasborn.gustav.web.Results

class ParameterRequiredException extends WebException {

    ParameterRequiredException(String message) {
        super(message)
    }

    ParameterRequiredException(String message, Throwable cause) {
        super(message, cause)
    }

    @Override
    Result toResult() {
        return Results.of(400, message, [:])
    }
}
