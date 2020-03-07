import java.io.*;

public class Decode {
    public static void main(String[] args) {
        Tree map;
        String code;
        String message = null;

        try {
            ObjectInputStream file1 = new ObjectInputStream(new FileInputStream("./src/mapping.txt"));
            map = (Tree) file1.readObject();
            file1.close();
            BufferedReader file2 = new BufferedReader(new FileReader("./src/encoded.txt"));
            code = file2.readLine();
            file2.close();
        } catch (FileNotFoundException e) {
            System.out.println("mapping.txt/encode.txt not found");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Node temp = map.getRoot();
        for (int i = 0; i < code.length(); i++) {
            Character c = code.charAt(i);
            if (Character.getNumericValue(c) == 1) {

                if (temp.getRight() == null) {
                    if (message == null) {
                        message = temp.getCharacter();
                    } else {
                        message += temp.getCharacter();
                    }
                    temp = map.getRoot().getRight();


                } else {
                    temp = temp.getRight();
                }

            } else {

                if (temp.getLeft() == null) {
                    if (message == null) {
                        message = temp.getCharacter();
                    } else {
                        message += temp.getCharacter();
                    }
                    temp = map.getRoot().getLeft();

                } else {
                    temp = temp.getLeft();
                }

            }


        }

        message += temp.getCharacter();
        if (temp.getLeft() == null && temp.getRight() == null) {
            System.out.println(message);
        } else {
            System.out.println("ENCODED.TXT OR MAPPING.TXT ARE COURRPUTED OR CHANGED");
        }


    }
}
