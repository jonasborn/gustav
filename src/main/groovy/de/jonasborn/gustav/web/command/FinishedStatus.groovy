package de.jonasborn.gustav.web.command

class FinishedStatus {

    /**
     * "state"     : State.FINISHED,
     *                 "session"   : session,
     *                 "defaultLog": FileUtils.getRelative(project.workspace, defLog),
     *                 "errorLog"  : FileUtils.getRelative(project.workspace, errLog),
     *                 "thread"    : t.id,
     *                 "pid"       : p.pid(),
     *                 "code"      : code,
     *                 "command"   : command,
     *                 "parameters": base.subList(1, base.size())
     */

    Mode mode = Mode.STOPPED
    UUID project
    UUID session
    List<String> command
    String defaultLog
    String errorLog
    String thread
    Long pid
    Integer code

    FinishedStatus(UUID project, UUID session, List<String> command, String defaultLog, String errorLog, String thread, Long pid, Integer code) {
        this.project = project
        this.session = session
        this.command = command
        this.defaultLog = defaultLog
        this.errorLog = errorLog
        this.thread = thread
        this.pid = pid
        this.code = code
    }
}
