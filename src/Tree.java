import java.io.Serializable;

public class Tree implements Serializable {
    private Node root;

    Tree() {

    }

    Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public String locate(Character c) {
        Node temp = root;
        String path = null;

        while (!temp.getCharacter().equals(c.toString())) {
            if (temp.getLeft().getCharacter().contains(c.toString())) {
                temp = temp.getLeft();
                if (path == null) {
                    path = "0";
                } else {
                    path += "0";
                }
            } else {
                temp = temp.getRight();
                if (path == null) {
                    path = "1";
                } else {
                    path += "1";
                }
            }
            if (temp == null) {
                break;
            }
        }
        return path;
    }



}


