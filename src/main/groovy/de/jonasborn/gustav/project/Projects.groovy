package de.jonasborn.gustav.project

class Projects extends ArrayList<Project> {

    public boolean delete(UUID id) {
        def r = this.contains(id)
        this.removeAll {it.id == id }
        return (r && ! this.contains(id))
    }

    public Project create(UUID id, String name, File workspace) {
        def p = new Project(id, name, workspace)
        this.add(p)
        return p
    }



}
