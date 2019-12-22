package de.jonasborn.gustav.web

import io.javalin.Javalin

interface MultiHandler {

    public void register(Javalin javalin)

}