package de.jonasborn.gustav.exception


import de.jonasborn.gustav.web.Result

abstract class WebException extends Exception {

    WebException(String message) {
        super(message)
    }

    WebException(String message, Throwable cause) {
        super(message, cause)
    }

    public abstract Result toResult()

}
