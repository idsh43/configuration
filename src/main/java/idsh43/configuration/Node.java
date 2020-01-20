package idsh43.configuration;

class Node {

    private String id, value;

    Node(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public boolean isGroup() {
        return value == null;
    }

    String getValue() {
        return value;
    }

}
