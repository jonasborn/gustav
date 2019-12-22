package de.jonasborn.gustav.api

class ParameterList extends ArrayList<Parameter> {

    String prefix

    ParameterList(String prefix) {
        this.prefix = prefix
    }

    public void list(Class type, String name, String description) {
        add(new ListParameter(type, prefix, name, description))
    }

    public void norm(Class type, String name, String description) {
        add(new Parameter(type, prefix, name, description))
    }

    public void en(Class type, String name, String description) {
        add(new EnumParameter(type, prefix, name, description))
    }

}
