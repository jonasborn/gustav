package de.jonasborn.gustav.api

class Parameter {

    Class<?> type
    String prefix
    String name
    String description

    Parameter(Class<?> type, String prefix, String name, String description) {
        this.type = type
        this.prefix = prefix
        this.name = name
        this.description = description
    }


    @Override
    public String toString() {
        return "Parameter{" +
                "type=" + type +
                ", prefix='" + prefix + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
