package idsh43.configuration;

import java.util.*;

public class Group extends Node {

    private Group parent;
    private Map<String, Node> nodes = new LinkedHashMap<>();
    private DataParser dataParser = new DataParser();

    public Group(String id) {
        super(id, null);
    }

    public Node getNode(String path) throws ConfigurationException {
        String[] pathIds = getPathIds(path);
        Node node = this;
        for (String id: pathIds) {
            if (!node.isGroup())
                throw new ConfigurationException("node \"" + node.getId() + "\" at path \"" + path + "\" is not a group");
            node = ((Group) node).nodes.get(id);
            if (node == null)
                throw new ConfigurationException("node \"" + id + "\" at path \"" + path + "\" not found");
        }
        return node;
    }

    public Group getGroup(String path) throws ConfigurationException {
        Node node = getNode(path);
        if (!node.isGroup())
            throw new ConfigurationException("node " + path + " is not a group");
        return (Group) node;
    }

    public String getValue(String path) throws ConfigurationException {
        Node node = getNode(path);
        if (node.isGroup())
            throw new ConfigurationException("node " + path + " is not a value");
        return node.getValue();
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        for (Node node: nodes.values()) {
            if (node.isGroup())
                groups.add((Group) node);
        }
        return groups;
    }

    public List<Value> getValues() {
        List<Value> values = new ArrayList<>();
        for (Node node: nodes.values()) {
            if (!node.isGroup())
                values.add((Value) node);
        }
        return values;
    }

    public void addGroup(Group group) {
        group.setParent(this);
        nodes.put(group.getId(), group);
    }

    public Group addGroup(String id) {
        Group group = new Group(id);
        addGroup(group);
        return group;
    }

    public void addValue(Value value) {
        nodes.put(value.getId(), value);
    }

    public void addValue(String id, String value) {
        addValue(new Value(id, value));
    }

    public String get(String path) throws ConfigurationException {
        return getValue(path);
    }

    public boolean getBoolean(String path) throws ConfigurationException {
        return dataParser.parseBoolean(get(path));
    }

    public int getInteger(String path) throws ConfigurationException {
        return dataParser.parseInteger(get(path));
    }

    public float getFloat(String path) throws ConfigurationException {
        return dataParser.parseFloat(get(path));
    }

    public List<String> getList(String path) throws ConfigurationException {
        return dataParser.parseList(get(path));
    }

    public <T extends Enum> List<T> getEnumList(Class<T> enumClass, String path) throws ConfigurationException {
        return dataParser.parseEnumList(enumClass, getList(path));
    }

    public <T extends Enum> Set<T> getEnumSet(Class<T> enumClass, String path) throws ConfigurationException {
        return dataParser.parseEnumSet(enumClass, getList(path));
    }

    public void set(String path, String value) throws ConfigurationException {
        String[] pathIds = getPathIds(path);
        Group node = this;
        for (int i = 0; i < pathIds.length - 1; i++) {
            Node next;
            try {
                next = node.getNode(pathIds[i]);
            } catch (ConfigurationException e) {
                Group group = new Group(pathIds[i]);
                node.addGroup(group);
                node = group;
                continue;
            }
            if (!next.isGroup())
                throw new ConfigurationException("node \"" + pathIds[i] + "\" at path \"" + path + "\" is not a group");
            node = (Group) next;
        }
        node.addValue(pathIds[pathIds.length - 1], value);
    }

    public <T> void set(String path, T value) throws ConfigurationException {
        set(path, value.toString());
    }

    Group getParent() {
        return parent;
    }

    void setParent(Group parent) {
        this.parent = parent;
    }

    private String[] getPathIds(String path) throws ConfigurationException {
        String[] pathIds = path.split("\\.");
        if (pathIds.length == 0)
            throw new ConfigurationException("invalid path");
        return pathIds;
    }

}
