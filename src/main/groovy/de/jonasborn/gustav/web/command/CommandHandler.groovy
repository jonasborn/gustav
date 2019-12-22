package de.jonasborn.gustav.web.command

import com.google.gson.Gson
import de.jonasborn.gustav.web.MultiHandler
import de.jonasborn.gustav.web.Results
import io.javalin.Javalin
import io.javalin.http.Handler

class CommandHandler implements MultiHandler {

    static Gson gson = new Gson()

    Map<UUID, Thread> threads = [:]
    Map<UUID, Process> processes = [:]
    Map<UUID, List<String>> commands = [:]


    de.jonasborn.gustav.project.Project project
    String name
    List<String> command
    List<de.jonasborn.gustav.api.Parameter> parameters
    String base

    CommandHandler(de.jonasborn.gustav.project.Project project, String name, List<String> command, List<de.jonasborn.gustav.api.Parameter> parameters) {
        this.project = project
        this.name = name
        this.command = command
        this.parameters = parameters
        base = "/projects/" + project.id.toString() + "/" + name + "/"
        println base
    }

    @Override
    void register(Javalin javalin) {
        println base + "start"
        javalin.post(base + "start", start())
        javalin.post(base + "stop", stop())
        javalin.get(base + "log", log())
        javalin.get(base + "status", status())
    }

    public Handler start() {

        return {
            UUID session = UUID.randomUUID()

            //Get status and log
            def defLog = project.getDefaultLog(session)
            def errLog = project.getDefaultLog(session)

            def status = project.getStatus(session)
            Map<String, Object> b = gson.fromJson(new String(it.bodyAsBytes(), "UTF-8"), Map.class)

            //Iterate all parameters from list and try to stringify each
            def params = parameters.collect { stringify(it, b) }.flatten().findAll { it != null } as List<String>
            List<String> base = [command, params].flatten() as List<String>
            println base
            commands.put(session, base)

            //Create process builder, redirect output and start
            ProcessBuilder builder = new ProcessBuilder(base as String[])
            builder.redirectOutput(defLog)
            builder.redirectError(errLog)

            def p = builder.start()
            processes.put(session, p)
            Thread t
            t = Thread.start {
                status.write(gson.toJson(States.started(project, session, base, defLog, errLog, t, p)))
                def s = p.waitFor()
                status.write(gson.toJson(States.finished(project, session, base, defLog, errLog, t, p, s)))
            }
            threads.put(session, t)

            it.result(gson.toJson(States.started(project, session, base, defLog, errLog, t, p)))

        } as Handler

    }

    public Handler log() {
        return {
            def session = UUID.fromString(it.queryParam("s"))
            it.contentType("application/json")
            it.result(project.getDefaultLog(session).text)
        } as Handler
    }

    public Handler status() {
        return {
            def session = UUID.fromString(it.queryParam("s"))
            it.contentType("application/json")
            it.result(project.getStatus(session).text)
        } as Handler
    }

    public Handler stop() {
        return {
            def session = UUID.fromString(it.queryParam("s"))
            def p = processes.remove(session)
            def t = threads.remove(session)
            def b = commands.remove(session)
            p.destroy()
            Thread.start {
                def s = p.waitFor()
                project.getStatus(session).write(gson.toJson(States.finished(project, session, b, project.getDefaultLog(session), project.getErrorLog(session), t, p, s)))
            }
            Results.of(200, "Stop registered", ["pid", p.pid()]).into(it)
        } as Handler
    }


    public static List<String> stringify(de.jonasborn.gustav.api.Parameter parameter, Map<String, Object> map) {
        def v = map.get(parameter.name)
        if (v != null) return [parameter.prefix + parameter.name, stringify(parameter, v)]
        return null
    }

    public static String stringify(de.jonasborn.gustav.api.Parameter parameter, Object o) {
        if (parameter instanceof de.jonasborn.gustav.api.EnumParameter) {
            def m = parameter.type.getMethod("valueOf", String.class)
            def e = m.invoke(null, o.toString())
            return e.toString()
        } else if (parameter instanceof de.jonasborn.gustav.api.ListParameter) {
            (o as List<String>).collect { stringify((parameter as de.jonasborn.gustav.api.ListParameter).listType, it) }.join(",")
        } else if (parameter instanceof de.jonasborn.gustav.api.Parameter) {
            return stringify(parameter.type, o)
        }
        return null
    }

    public static String stringify(Class<?> cls, Object o) {
        if (cls == File.class) return new File(o as String).getCanonicalPath()
        if (cls == String.class) return o as String
        if (cls == Boolean.class) return null
        return o.toString()
    }


    static enum State {
        UNKNOWN,
        RUNNING,
        FINISHED
    }
}
