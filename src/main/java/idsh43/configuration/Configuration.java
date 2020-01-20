package idsh43.configuration;

import idsh43.configuration.util.FileUtil;

import java.io.IOException;
import java.util.List;

public class Configuration extends Group {

    private String path;

    public static Configuration create(List<String> data) throws ConfigurationException {
        Configuration cfg = new Configuration();
        Parser.parse(cfg, data);
        return cfg;
    }

    public static Configuration load(String path) throws ConfigurationException {
        try {
            return Configuration.create(FileUtil.read(path));
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    public Configuration() {
        super("root");
    }

    public Configuration(String path) {
        this();
        this.path = path;
    }

    public void save() throws ConfigurationException {
        try {
            FileUtil.write(path, Parser.printRoot(this));
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

}
