package de.jonasborn.gustav.project

class Project {

    UUID id
    String name
    File workspace
    private File metaspace

    Project(UUID id, String name, File workspace) {
        this.id = id
        this.name = name
        this.workspace = workspace
        if (!workspace.exists()) workspace.mkdirs()
        metaspace = new File(workspace, "_meta")
        if (!metaspace.exists()) metaspace.mkdirs()
    }

    public File getMetaspace(UUID session) {
        def d = new File(metaspace, session.toString())
        if (!d.exists()) d.mkdirs()
        return d
    }

    public File getDefaultLog(UUID session) {
        return new File(getMetaspace(session), "defaultLog.txt")
    }

    public File getErrorLog(UUID session) {
        return new File(getMetaspace(session), "errorLog.txt")
    }

    public File getStatus(UUID session) {
        return new File(getMetaspace(session), "status.json")
    }
}
