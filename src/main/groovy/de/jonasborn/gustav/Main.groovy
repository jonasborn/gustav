package de.jonasborn.gustav

import de.jonasborn.gustav.exception.WebException
import de.jonasborn.gustav.project.Projects
import de.jonasborn.gustav.util.WebUtils
import de.jonasborn.gustav.web.ExceptionHandler
import de.jonasborn.gustav.web.project.ProjectsHandler
import io.javalin.Javalin

class Main {

    public static void main(String[] args) {

        /*  Map<String, ArgumentParser> m = [:]

          def p = ArgumentParsers.newFor("gustav-server").build()
          p.defaultHelp(true)
          m["gustav"] = p
          m["gustav.serve"] = m["gustav"].addSubparsers().addParser("serve").setDefault("path", "gustav.serve")
          m["gustav.serve"].addArgument("-s", "--schema").help("The application schema to serve written in json format")
          m["gustav.serve"].addArgument("-p", "--port").help("Sets the port to use for the server")
          m["gustav.serve"].addArgument("-w", "--workdir").help("Used to set the workdir")
          m["gustav.serve"].addArgument("-f", "--files").help("Activates the file system support routes, used to navigate within the rest api").setDefault(false)

          m["gustav.generate"] = m["gustav"].addSubparsers().addParser("generate").setDefault("path", "gustav.generate")
          m["gustav.generate"].addArgument("-s", "--schema").help("The application schema to use")
          m["gustav.generate"].addArgument("-o", "--output").help("The output file")
  */



        Javalin javalin = Javalin.create().start(8822)

        javalin.exception(WebException.class, new ExceptionHandler())

        new ProjectsHandler(new File("base"), new Projects()).register(javalin)

        String base = "http://127.0.0.1:8822"

        def creation = WebUtils.get("$base/project/create", ["name": "test1"])

        def id = creation.getAsJsonObject("value").get("id").getAsString()

        println "Id is $id"

        println WebUtils.post("$base/projects/$id/predict/start", ["help": ""])


    }

}
