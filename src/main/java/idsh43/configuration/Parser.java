package idsh43.configuration;

import idsh43.configuration.util.StringUtil;

import java.util.LinkedList;
import java.util.List;

class Parser {

    private static final char COMMENT_CHAR = '#';
    private static final char DEPTH_CHAR = '\t';
    private static final char OPEN_GROUP_CHAR = '{';
    private static final char CLOSE_GROUP_CHAR = '}';
    private static final char SEPARATOR_CHAR = ' ';

    static void parse(Group root, List<String> lines) throws ConfigurationException {
        Group currentGroup = root;
        int depth = 0;
        int lineIndex = 0;
        for (String line: lines) {
            lineIndex++;
            if (isComment(line)) continue;
            line = removeDepth(line);
            if (isGroupOpening(line)) {
                Group group = new Group(getGroupId(line));
                currentGroup.addGroup(group);
                currentGroup = group;
                depth++;
                continue;
            }
            if (isGroupClosing(line)) {
                currentGroup = currentGroup.getParent();
                depth--;
                continue;
            }
            if (isValue(line)) {
                currentGroup.addValue(getValueId(line), getValue(line));
                continue;
            }
            throw new ConfigurationException(lineIndex, "bad line");
        }
        if (depth != 0)
            throw new ConfigurationException("root group closing missed");
    }

    static List<String> printRoot(Group root) {
        List<String> lines = new LinkedList<>();
        for (Node node: root.getNodes())
            lines.addAll(printNode(node, 0));
        return lines;
    }

    private static List<String> printNode(Node node, int depth) {
        List<String> lines = new LinkedList<>();
        String indent = StringUtil.repeat(DEPTH_CHAR, depth);
        if (node.isGroup()) {
            Group group = (Group) node;
            lines.add(indent + group.getId() + SEPARATOR_CHAR + OPEN_GROUP_CHAR);
            for (Node child: group.getNodes()) {
                lines.addAll(printNode(child, depth + 1));
            }
            lines.add(indent + CLOSE_GROUP_CHAR);
        } else {
            Value value = (Value) node;
            lines.add(indent + value.getId() + SEPARATOR_CHAR + value.getValue());
        }
        return lines;
    }

    private static boolean isComment(String line) {
        return line.charAt(0) == COMMENT_CHAR;
    }

    private static boolean isGroupOpening(String line) {
        String[] parts = split(line);
        return parts.length == 2 && parts[1].equals("" + OPEN_GROUP_CHAR);
    }

    private static boolean isGroupClosing(String line) {
        return line.equals("" + CLOSE_GROUP_CHAR);
    }

    private static boolean isValue(String line) {
        return split(line).length > 1;
    }

    private static String getGroupId(String line) {
        return split(line)[0];
    }

    private static String getValueId(String line) {
        return split(line, 2)[0];
    }

    private static String getValue(String line) {
        return split(line, 2)[1];
    }

    private static String removeDepth(String line) {
        for(int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != DEPTH_CHAR) return line.substring(i);
        }
        return line;
    }

    private static String[] split(String target, int limit) {
        return target.split("" + SEPARATOR_CHAR, limit);
    }

    private static String[] split(String target) {
        return target.split("" + SEPARATOR_CHAR);
    }

}
