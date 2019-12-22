package de.jonasborn.gustav.util

import de.jonasborn.gustav.exception.ParameterRequiredException


class AssertUtils {

    public static parameterPresent(String name, Object value) {
        if (value == null) throw new ParameterRequiredException("Parameter $name is required but not present")
    }

}
