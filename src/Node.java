import java.io.Serializable;

public class Node implements Serializable {

    private String character;
    private int value;
    private Node left;
    private Node right;

    Node() {
    }

    Node(String character, int value) {
        this.character = character;
        this.value = value;
    }

    public Node getLeft() {

        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        if (left == null && right == null) {
            System.out.println(character + " " + value);
            return "Done";
        }
        left.toString();
        right.toString();
        return "Done";

    }
}
