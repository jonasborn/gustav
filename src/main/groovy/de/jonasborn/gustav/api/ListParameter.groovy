package de.jonasborn.gustav.api


class ListParameter extends Parameter {

    Class<?> listType

    ListParameter(Class<?> type, String prefix, String name, String description) {
        super(type, prefix, name, description)
    }
}
