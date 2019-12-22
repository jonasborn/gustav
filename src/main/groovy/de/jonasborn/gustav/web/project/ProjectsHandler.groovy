package de.jonasborn.gustav.web.project


import de.jonasborn.gustav.ludwig.Predict
import de.jonasborn.gustav.web.command.CommandHandler
import de.jonasborn.gustav.util.AssertUtils
import de.jonasborn.gustav.web.MultiHandler
import de.jonasborn.gustav.web.Results
import de.jonasborn.gustav.web.file.FileHandler
import de.jonasborn.gustav.web.proxy.ProxyHandler
import io.javalin.Javalin
import io.javalin.http.Handler

class ProjectsHandler implements MultiHandler {

    File base
    de.jonasborn.gustav.project.Projects projects

    ProjectsHandler(File base, de.jonasborn.gustav.project.Projects projects) {
        this.base = base
        this.projects = projects
    }

    @Override
    void register(Javalin javalin) {
        javalin.get("/project/index", index())
        javalin.get("/project/delete", delete())
        javalin.get("/project/create", create(javalin))
    }

    public Handler index() {
        return {
            Results.of(200, "Projects found", projects.collect { it.id.toString() })
        } as Handler
    }

    public Handler delete() {
        return {
            def id = UUID.fromString(it.queryParam("id"))
            def s = projects.delete(id)
            if (s) {
                return Results.of(200, "Project found and removed", ["id": id]).into(it)
            } else {
                return Results.of(404, "No project with this id found", ["id": id])
            }
        } as Handler
    }

    public Handler create(Javalin javalin) {
        return {
            def name = it.queryParam("name")
            AssertUtils.parameterPresent("name", name)

            def id = UUID.randomUUID()
            def p = projects.create(id, name, new File(base, id.toString()))
            new FileHandler(p).register(javalin)
            new CommandHandler(p, "predict",  ["/home/jonas/.local/bin/ludwig", "predict"], new Predict()).register(javalin)
            new ProxyHandler(p, name, "proxy").register(javalin)

            return Results.of(200, "Project created", ["name": name, "id": id]).into(it)

        } as Handler
    }

    //TODO CREATE

}
