package de.jonasborn.gustav.util

import groovy.io.FileType
import org.apache.tika.Tika

class FileUtils {

    static Tika tika = new Tika()

    public static String getRelative(File base, File file) {
        return base.toURI().relativize(file.toURI()).getPath();
    }

    public static List<File> getRecursive(File base, File file) {
        def r = []
        file.eachFileRecurse(FileType.ANY) {
            r.add(it)
        }
        return r
    }

    public static boolean isInside(File base, File file) {
        return file.getCanonicalPath().startsWith(base.getCanonicalPath());
    }

    public static File temp() {
        return new File("temp", UUID.randomUUID().toString())
    }

    public static String detect(File f) {
        return tika.detect(f)
    }


}
