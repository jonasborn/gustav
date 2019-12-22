package de.jonasborn.gustav.web

class Results {

    public static Result of(int code, String message, Object value) {
        return new Result(code, message, value)
    }

}
