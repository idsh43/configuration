package idsh43.configuration;

import idsh43.configuration.Configuration;
import idsh43.configuration.ConfigurationException;

import java.util.List;
import java.util.Set;

public class Test {

    static enum LogMode { ERROR, DEBUG, WARNING }

    public static void main(String[] args) throws ConfigurationException {

        Configuration cfg = Configuration.load("test.conf");
        print("name = " + cfg.get("name"));
        print("version = " + cfg.getInteger("info.version"));
        print("float_step = " + cfg.getFloat("info.float_step"));
        print("secure = " + cfg.getBoolean("info.secure"));
        print("test = " + cfg.get("info.subgroup.test"));
        Set<LogMode> modes = cfg.getEnumSet(LogMode.class, "log");
        System.out.print("log: ");
        for (LogMode mode: modes)
            print(mode + " ");
    }

    static void print(String string) {
        System.out.println(string);
    }
}
