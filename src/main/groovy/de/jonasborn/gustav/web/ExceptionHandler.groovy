package de.jonasborn.gustav.web

import de.jonasborn.gustav.exception.WebException
import io.javalin.http.Context
import org.jetbrains.annotations.NotNull

class ExceptionHandler implements io.javalin.http.ExceptionHandler<WebException> {
    @Override
    void handle(@NotNull WebException exception, @NotNull Context ctx) {
        exception.toResult().into(ctx)
    }
}
