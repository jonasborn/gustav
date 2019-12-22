package de.jonasborn.gustav.web.command

import de.jonasborn.gustav.project.Project
import de.jonasborn.gustav.util.FileUtils

class States {

    public static StartedStatus started(Project project, UUID session, List<String> base, File defLog, File errLog, Thread t, Process p) {
        return new StartedStatus(
                project.id,
                session,
                base,
                FileUtils.getRelative(project.workspace, defLog),
                FileUtils.getRelative(project.workspace, errLog),
                t.name,
                p.pid()
        )
    }

    public static finished(Project project, UUID session, List<String> base, File defLog, File errLog, Thread t, Process p, Integer code) {
        return new FinishedStatus(
                project.id,
                session,
                base,
                FileUtils.getRelative(project.workspace, defLog),
                FileUtils.getRelative(project.workspace, errLog),
                t.name,
                p.pid(),
                code
        )
    }

}
