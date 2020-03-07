import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Math.*;
//import java.util.PriorityQueue;

public class ShannonEncode {
    public static void Shannon(int i,int j,Node[] nodeArr,Node currRoot,String path){
        System.out.println(i+" "+j);
        if (j<=i){
            currRoot.setCharacter(nodeArr[i].getCharacter());
            nodeArr[i].setPath(path);
//            System.out.println(currRoot.getCharacter()+" "+i+" " +path);
            return;
        }
        int sum=0;
        for (int k =i;k<=j;k++){
            sum+=nodeArr[k].getValue();
        }
        int currDiff=sum-2*nodeArr[i].getValue();//Current Partition right before the first element

        int currpartition=i;
        while (currpartition!=j){
//            System.out.println(currDiff);
            currpartition++;
            int diff=currDiff-2*nodeArr[currpartition].getValue();
            if (abs(diff)>=currDiff){
                currpartition--;
                break;
            }
            else{
                currDiff=abs(diff);

            }

        }
//        System.out.println(currpartition);
        currRoot.setLeft(new Node());
        currRoot.setRight(new Node());
        Shannon(i,currpartition,nodeArr,currRoot.getLeft(),path+"0");
        Shannon(currpartition+1,j,nodeArr,currRoot.getRight(),path+"1");
    }
    public static void main(String[] args) {
        BufferedReader input;
        String line;
        HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();
        HashMap<Character, String> codes = new HashMap<Character, String>();
        String output = null;
//        PriorityQueue<Node> sorted = new PriorityQueue<Node>(new ValueComparator());
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

        Node nodeArr[] =new Node[max(frequency.keySet().size(),2)];
        if (frequency.keySet().size()==1){
            nodeArr[1]=new Node("\n",0);
        }
        int j=0;
        for (Character c : frequency.keySet()) {
            nodeArr[j]=new Node(c.toString(),frequency.get(c));
            j++;
        }
        System.out.println(Arrays.toString(nodeArr));

        Arrays.sort(nodeArr,new ValueComparator());
//        System.out.println(Arrays.toString(nodeArr));
        Node root=new Node();
        Shannon(0,nodeArr.length-1,nodeArr,root,"");
        Tree encoder = new Tree(root);

        for (int k=0;k<nodeArr.length;k++) {
            codes.put(nodeArr[k].getCharacter().charAt(0), nodeArr[k].getPath());
        }
        for (int i = 0; i < line.length(); i++) {
            Character to_encode = line.charAt(i);
            if (output == null) {
                output = codes.get(to_encode);
            } else {
                output += codes.get(to_encode);
            }
        }
//        System.out.println(output);
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
