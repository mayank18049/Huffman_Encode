import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Encode {
    public static void main(String[] args) {
        BufferedReader input;
        String line;
        HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();
        HashMap<Character, String> codes = new HashMap<Character, String>();
        String output = null;
        PriorityQueue<Node> sorted = new PriorityQueue<Node>(new ValueComparator());
        try {
            input = new BufferedReader(new FileReader(new File("./src/input.txt")));
            line = input.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FILE NOT FOUND");
            return;
        } catch (IOException e) {
            return;
        }
        if (line == null) {
            System.out.println("Empty file nothing to encode");
            return;
        }
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (frequency.get(c) != null) {
                frequency.put(c, frequency.get(c) + 1);
            } else {
                frequency.put(c, 1);
            }

        }
        for (Character s : frequency.keySet()) {
            sorted.add(new Node(s.toString(), frequency.get(s)));
        }
        if (sorted.size() == 1) {
            sorted.add(new Node("\n", -1));
        }
        while (sorted.size() >= 2) {
            Node left = sorted.poll();
            Node right = sorted.poll();
            String combineChar = left.getCharacter() + right.getCharacter();
            int combineValue = left.getValue() + right.getValue();
            Node root = new Node(combineChar, combineValue);
            root.setLeft(left);
            root.setRight(right);
            sorted.add(root);
        }
        Tree encoder = new Tree(sorted.poll());

        for (Character c : frequency.keySet()) {
            String code = null;
            code = encoder.locate(c);
            codes.put(c, code);

        }
        for (int i = 0; i < line.length(); i++) {
            Character to_encode = line.charAt(i);
            if (output == null) {
                output = codes.get(to_encode);
            } else {
                output += codes.get(to_encode);
            }
        }
        try {
            File file = new File("./src/encoded.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter writer = new PrintWriter(new FileWriter(file, false));
            writer.write(output);
            writer.close();
            file = new File("./src/mapping.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream writer1 = new ObjectOutputStream(new FileOutputStream(file, false));
            writer1.writeObject(encoder);
            writer1.close();
            System.out.println("Files saved");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


    }
}
