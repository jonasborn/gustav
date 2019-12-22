package de.jonasborn.gustav.web.file

import com.google.common.io.ByteStreams
import com.google.common.io.Files
import de.jonasborn.gustav.project.Project
import de.jonasborn.gustav.web.Results
import de.jonasborn.gustav.util.FileUtils
import de.jonasborn.gustav.web.MultiHandler
import io.javalin.Javalin
import io.javalin.http.Handler
import org.apache.tika.Tika
import org.zeroturnaround.zip.ZipUtil

class FileHandler implements MultiHandler {

    Tika tika = new Tika()
    File workspace
    String base

    FileHandler(Project project) {
        this.workspace = project.workspace
        base = "/projects/" + project.id.toString() + "/files/"
    }

    @Override
    void register(Javalin javalin) {
        javalin.get(base + "index", index())
        javalin.get(base + "delete", delete())
        javalin.get(base + "get", get())
        javalin.post(base + "add", add())
    }

    public Handler index() {
        return {
            def p = (it.queryParam("p") == null) ? workspace : new File(workspace, it.queryParam("p") as String)

            def m = (it.queryParam("m") == null) ? "d" : it.queryParam("m")

            if (!p.exists()) Results.of(
                    200,
                    "Directory not found",
                    ["parent": workspace.getCanonicalPath(), "child": p.getCanonicalPath()]
            ).into(it)

            if (!p.isDirectory()) Results.of(
                    400,
                    "This is not a directory",
                    ["parent": workspace.getCanonicalPath(), "child": p.getCanonicalPath()]
            ).into(it)

            if (p.exists() && !FileUtils.isInside(workspace, p)) Results.of(
                    200,
                    "Directory is not part of project",
                    ["parent": workspace.getCanonicalPath(), "child": p.getCanonicalPath()]
            ).into(it)

            if (p.exists() && FileUtils.isInside(workspace, p) && m == "d") Results.of(
                    200,
                    "Directory found and listed",
                    p.listFiles().collect { FileUtils.getRelative(workspace, it) }
            ).into(it)

            if (p.exists() && FileUtils.isInside(workspace, p) && m == "r") Results.of(
                    200,
                    "Elements found and listed",
                    FileUtils.getRecursive(workspace, p).collect { FileUtils.getRelative(workspace, it) }
            ).into(it)

        } as Handler
    }

    public Handler delete() {
        return {
            def f = new File(workspace, it.queryParam("p"))
            if (FileUtils.isInside(workspace, f)) {
                def r = new File(it.queryParam("f")).delete()
                if (r)
                    Results.of(200, "File/directory deleted", [
                            "parent": workspace.getCanonicalPath(),
                            "child" : f.getCanonicalPath()
                    ]).into(it)
                else
                    Results.of(400, "Unable to delete file/directory", [
                            "parent": workspace.getCanonicalPath(),
                            "child" : f.getCanonicalPath()
                    ]).into(it)
            } else {
                Results.of(400, "File/directory is not part of the project directory", info(workspace, f)).into(it)
            }
        } as Handler
    }

    public Handler get() {
        return {
            def f = new File(workspace, it.queryParam("p"))
            if (f.exists()) {
                if (f.isFile()) {
                    it.contentType(tika.detect(f))
                    ByteStreams.copy(new FileInputStream(f), it.res.getOutputStream()) //Read and serve the file
                }
                if (f.isDirectory()) {
                    it.contentType("application/zip")
                    ZipUtil.pack(f, it.res.outputStream) //Create and serve a new zip file, should work
                }
            } else {
                Results.of(404, "File/directory not found", info(workspace, f)).into(it)
            }
        } as Handler
    }

    public Handler add() {
        return {
            def p = new File(workspace, it.queryParam("p"))
            def m = (it.queryParam("m") == null) ? "k" : it.queryParam("u")

            def target = FileUtils.temp()
            ByteStreams.copy(it.req.inputStream, new FileOutputStream(target))
            def type = FileUtils.detect(target)
            if (type == "application/zip" && m == "u") {
                if (p.exists() && p.isDirectory()) p.deleteDir()
                if (p.exists() && p.isFile()) p.delete()
                if (!p.exists()) p.mkdirs()
                ZipUtil.unpack(target, p)
            } else {
                if (p.exists() && p.isDirectory()) p.deleteDir()
                if (p.exists() && p.isFile()) p.delete()
                Files.move(target, p)
            }
        }
    }

    private static Map<String, Object> info(File parent, File child) {
        return [
                "parent": [
                        "path"  : parent.getCanonicalPath(),
                        "exists": parent.exists(),
                        "hidden": parent.isHidden()
                ],
                "child" : [
                        "path"  : child.getCanonicalPath(),
                        "exists": child.exists(),
                        "hidden": child.isHidden(),
                        "inside": FileUtils.isInside(parent, child)
                ]
        ]
    }


}
