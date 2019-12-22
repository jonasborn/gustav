package de.jonasborn.gustav.api



class EnumParameter extends Parameter {


    EnumParameter(Class<? extends Enum> type, String prefix, String name, String description) {
        super(type, prefix, name, description)
    }
}
