package de.jonasborn.gustav.web.command

class StartedStatus {
    /**
     * "state"     : State.RUNNING,
     *                 "session"   : session,
     *                 "defaultLog": FileUtils.getRelative(project.workspace, defLog),
     *                 "errorLog"  : FileUtils.getRelative(project.workspace, errLog),
     *                 "thread"    : t.id,
     *                 "pid"       : p.pid(),
     *                 "command"   : command,
     *                 "parameters": base.subList(1, base.size())
     */

    Mode mode = Mode.RUNNING
    UUID project
    UUID session
    List<String> command
    String defaultLog
    String errorLog
    String thread
    Long pid

    StartedStatus(UUID project, UUID session, List<String> command, String defaultLog, String errorLog, String thread, Long pid) {
        this.project = project
        this.session = session
        this.command = command
        this.defaultLog = defaultLog
        this.errorLog = errorLog
        this.thread = thread
        this.pid = pid
    }
}
