package de.jonasborn.gustav.web.proxy


import com.mashape.unirest.http.Unirest
import de.jonasborn.gustav.project.Project
import de.jonasborn.gustav.util.FileUtils
import de.jonasborn.gustav.web.MultiHandler
import io.javalin.Javalin
import io.javalin.http.Handler

import java.util.concurrent.CountDownLatch


class ProxyHandler implements MultiHandler {

    static CountDownLatch latch = new CountDownLatch(0)

    Project project
    String command

    String localhost


    String base

    Map<UUID, Thread> threads = [:]
    Map<UUID, Process> processes = [:]
    Map<UUID, List<String>> commands = [:]
    Map<UUID, String> hosts = [:]
    Map<UUID, Integer> ports = [:]

    public ProxyHandler(Project project, String name, String command) {
        this.project = project
        this.command = command
        base = "/projects/" + project.id.toString() + "/" + name + "/"
    }

    @Override
    void register(Javalin javalin) {
        javalin.get(base + "start", start())
        javalin.post(base + "route", route())
        javalin.post(base + "stop", stop())
    }

    private Handler start() {
        return {

            latch.await()
            latch = new CountDownLatch(1)

            def session = UUID.randomUUID()
            def socket = new ServerSocket(0)
            def port = socket.getLocalPort()
            socket.close()

            def parameter = it.queryParams("model_path") as String
            assert parameter != null

            def model = new File(project.workspace, parameter)
            assert FileUtils.isInside(project.workspace, model)

            def cmd = [command, "-p", port.toString(), "-H", localhost, "--MODEL_PATH", model.getCanonicalPath()]
            commands.put(session, cmd)

            def b = new ProcessBuilder(cmd as List<String>)
            def process = b.start()
            processes.put(session, process)

            def thread = Thread.start { b.start() }
            threads.put(session, thread)

            hosts.put(session, localhost)
            ports.put(session, port)

            latch.countDown()


        } as Handler
    }

    private Handler route() {
        return {

            def session = UUID.fromString(it.queryParam("s"))

            def host = hosts.get(session)
            def port = ports.get(session)

            def r = Unirest.post("http://" + host + ":" + port + "/predict").body(it.body()).asString().body
            it.result(r)

        } as Handler
    }

    private Handler stop() {
        return {
            def session = UUID.fromString(it.queryParam("s"))
            Thread.start {
                threads.get(session).interrupt()
                processes.get(session).waitForOrKill(1000)
            }
        } as Handler
    }
}
