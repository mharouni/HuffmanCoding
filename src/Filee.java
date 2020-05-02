import Model.Node;
import Model.Tree;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.StreamSupport;

public class Filee {


    public PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
    public String fileName;
    // public String delHeader = "H^^";
    public String delHeaderEnd = "\nHE^\n";
    public String textDel = "T^^";
    public String textEndDel = "TER^^";

    public Filee(String fileName) {
        this.fileName = fileName;
    }

    public void encodeSingleFile() throws IOException {
        long execution;
        Instant start = Instant.now();
        HashMap<Character, Integer> charToFreq = new HashMap<>();
        InputStream inputStream1 = new FileInputStream(fileName);
        long input = ((FileInputStream) inputStream1).getChannel().size();
        int x;
        int counter = 0;
        char character;
        while ((x = inputStream1.read()) != -1) {
            if ((char) x != '\r') {
                character = (char) x;
                if (charToFreq.containsKey(character))
                    charToFreq.put(character, charToFreq.get(character) + 1);
                else
                    charToFreq.put(character, 1);
            }
        }
        inputStream1.close();
        //  System.out.println(charToFreq.toString());
        for (Map.Entry<Character, Integer> entry : charToFreq.entrySet()) {
            Node queueNode = new Node(entry.getKey(), entry.getValue());
            this.priorityQueue.add(queueNode);
        }
        Tree tree = new Tree(priorityQueue);
        tree.createTree();
        tree.generateCode();
        //System.out.println(tree.map.toString());
        InputStream inputStream2 = new FileInputStream(fileName);
        OutputStream outputStream = new FileOutputStream("compressed " + this.fileName, true);
        FileWriter fileWriter = new FileWriter(((FileOutputStream) outputStream).getFD());
        String header = tree.map.toString();
        header = header.substring(1, header.length() - 1);
        if (header.charAt(0) == ' ') {
            String tempo = header.substring(0, header.indexOf(','));
            header = header.substring(header.indexOf(',') + 2);
            header = header + ", " + tempo;

        }
        fileWriter.write(header);
        fileWriter.write(this.delHeaderEnd);
        fileWriter.write(this.textDel);
        fileWriter.flush();
        //System.out.println(tree.map.toString());
        //System.out.println(header);
        byte buffer = 0;
        while ((x = inputStream2.read()) != -1) {
            if ((char) x != '\r') {
                byte placeHolder;
                String s = tree.map.get((char) x);
                for (int i = 0; i < s.length(); i++) {
                    char tt = s.charAt(i);
                    placeHolder = Byte.parseByte(String.valueOf(tt));
                    buffer |= placeHolder;
                    if (++counter == 6) {
                        counter = 0;
                        // System.out.println();
                        //System.out.println(Integer.toBinaryString(buffer));
                        outputStream.write(buffer);

                        buffer = 0;
                    } else
                        buffer <<= 1;

                    //System.out.println(Character.toString((char)x));
                }

                outputStream.flush();
                //fileWriter.write(this.textEndDel);
            }
        }

        long output = ((FileOutputStream) outputStream).getChannel().size();
        fileWriter.flush();
        fileWriter.close();
        inputStream2.close();
        outputStream.close();
        Instant end = Instant.now();
        execution = Duration.between(start, end).toMillis();
        System.out.println("Compression execution time: " + execution + " ms");
        double dinput = (double) input;
        double doutput = (double) output;
        System.out.println("Compression Ratio is " + (dinput / doutput));
        for (Map.Entry<Character, String> entry : tree.map.entrySet()) {
            char key = entry.getKey();
            String str = entry.getValue();
            int ascii = (int) key;
            String temp = Integer.toBinaryString(ascii);
            System.out.println(ascii + "\t\t" + temp + "\t\t" + str);
            // do something with key and/or tab


        }

    }

    public void decodeSignleFile() throws IOException {
        Instant start = Instant.now();
        this.priorityQueue.clear();
        long execution;
        FileInputStream inputStream1 = new FileInputStream(this.fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream1);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        FileWriter fw = new FileWriter("decomp " + this.fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        HashMap<String, Character> charToCode = new HashMap<>();
        String buffer = "";
        String placeHolder;
        while (!(placeHolder = bufferedReader.readLine()).equals("HE^")) {
            buffer = buffer + placeHolder;
        }
        inputStream1.close();
        inputStreamReader.close();

        if (buffer.charAt(0) == '=' && buffer.charAt(1) != '=')
            buffer = '\n' + buffer;

        String seperated[] = buffer.split(",");
        char c = 0;
        String code = "";
        int freq;
        String entry;
        for (int i = 0; i < seperated.length; i++) {
            entry = seperated[i];
            if (i == 0) {
                c = entry.charAt(0);
                code = entry.substring(2);
            }
            if (entry.charAt(0) == '=' && entry.charAt(1) != '=') {
                c = ',';
                code = entry.substring(1);
            }
            if (entry.startsWith(" ") && entry.length() > 1) {
                entry = entry.substring(1);
                c = entry.charAt(0);
                code = entry.substring(2);
                if (entry.charAt(0) == '=' && entry.charAt(1) != '=') {
                    c = '\n';
                    code = entry.substring(1);
                }
            }
            charToCode.put(code, c);
        }
        InputStream input = new FileInputStream(this.fileName);
        int d;
        String zeroArray[] = {"", "00000", "0000", "000", "00", "0"};
        String str = "";
        StringBuilder strr = new StringBuilder(str);
        char cha[] = {};
        String stringToRead = "";
        StringBuilder stringToReadd = new StringBuilder(stringToRead);
        //System.out.println("hennaaaa");
        while ((d = input.read()) != -1) {
            //str = str + (char)d;
            strr.append((char) d);
        }
        for (int i = 0; i < strr.length(); i++) {
            //System.out.print(str);
            if (strr.charAt(i) == '^' && strr.charAt(i - 1) == '^') {
                str = strr.substring(i + 1);
                break;
            }
        }
        cha = str.toCharArray();
        // System.out.println(str);
        str = "";
        for (int i = 0; i < cha.length; i++) {
            str = Integer.toBinaryString(cha[i]);
            int length = str.length();
            if (length < 6) {
                str = zeroArray[length] + str;
            }
            stringToReadd = stringToReadd.append(str);
        }
        stringToRead = stringToReadd.substring(0);
        //System.out.println(stringToRead);
        String concat = "";
        for (int j = 0; j < stringToRead.length(); j++) {
            concat = concat + stringToRead.charAt(j);
            if (charToCode.containsKey(concat)) {
                //System.out.print(charToCode.get(concat));
                if (charToCode.get(concat) != '\r')
                    bw.write(charToCode.get(concat));
                concat = "";
            }

        }
        bw.close();
        fw.close();
        Instant end = Instant.now();
        execution = Duration.between(start, end).toMillis();
        System.out.println("Decompression execution time: " + execution + " ms");

    }


    public void encodeMultipleFiles() throws IOException {
        long execution;
        Instant start = Instant.now();
        HashMap<Character, Integer> charToFreq = new HashMap<>();
        InputStream inputStream1 = new FileInputStream(this.fileName);
        long input = ((FileInputStream) inputStream1).getChannel().size();
        int x;
        int counter = 0;
        char character;
        while ((x = inputStream1.read()) != -1) {
            if ((char) x != '\r') {
                character = (char) x;
                if (charToFreq.containsKey(character))
                    charToFreq.put(character, charToFreq.get(character) + 1);
                else
                    charToFreq.put(character, 1);
            }
        }
        inputStream1.close();
        //  System.out.println(charToFreq.toString());
        for (Map.Entry<Character, Integer> entry : charToFreq.entrySet()) {
            Node queueNode = new Node(entry.getKey(), entry.getValue());
            this.priorityQueue.add(queueNode);
        }
        Tree tree = new Tree(priorityQueue);
        tree.createTree();
        tree.generateCode();
        //System.out.println(tree.map.toString());
        InputStream inputStream2 = new FileInputStream(fileName);
        OutputStream outputStream = new FileOutputStream("compressedFolder.txt", true);
        FileWriter fileWriter = new FileWriter(((FileOutputStream) outputStream).getFD());
        String header = tree.map.toString();
        header = header.substring(1, header.length() - 1);
        if (header.charAt(0) == ' ') {
            String tempo = header.substring(0, header.indexOf(','));
            header = header.substring(header.indexOf(',') + 2);
            header = header + ", " + tempo;

        }
        fileWriter.write(header);
        fileWriter.write(this.delHeaderEnd);
        fileWriter.write(this.textDel);
        fileWriter.flush();
        //System.out.println(tree.map.toString());
        //System.out.println(header);
        byte buffer = 0;
        while ((x = inputStream2.read()) != -1) {
            if ((char) x != '\r') {
                byte placeHolder;
                String s = tree.map.get((char) x);
                for (int k = 0; k < s.length(); k++) {
                    char tt = s.charAt(k);
                    placeHolder = Byte.parseByte(String.valueOf(tt));
                    buffer |= placeHolder;
                    if (++counter == 6) {
                        counter = 0;
                        // System.out.println();
                        //System.out.println(Integer.toBinaryString(buffer));
                        outputStream.write(buffer);

                        buffer = 0;
                    } else
                        buffer <<= 1;

                    //System.out.println(Character.toString((char)x));
                }

                outputStream.flush();
                //fileWriter.write(this.textEndDel);
            }
        }

        long output = ((FileOutputStream) outputStream).getChannel().size();
        fileWriter.write(this.textEndDel);
        fileWriter.flush();
        fileWriter.close();
        inputStream2.close();
        outputStream.close();
        Instant end = Instant.now();
        execution = Duration.between(start, end).toMillis();
        System.out.println("Compression execution time: " + execution + " ms");
        double dinput = (double) input;
        double doutput = (double) output;
        System.out.println("Compression Ratio is " + (dinput / doutput));
        for (Map.Entry<Character, String> entry : tree.map.entrySet()) {
            char key = entry.getKey();
            String str = entry.getValue();
            int ascii = (int) key;
            String temp = Integer.toBinaryString(ascii);
            System.out.println(ascii + "\t\t" + temp + "\t\t" + str);
            // do something with key and/or tab


        }
    }

    public void decodeMultipleFiles() throws IOException
    {
        InputStream inputStream = new FileInputStream("compressedFolder.txt");
        int inputChar;
        int fileCounter= 0;
        String allFile = "";
        StringBuilder allBuilder = new StringBuilder(allFile);
        String[] stringArray=new String[1000000];
        while ( (inputChar = inputStream.read()) != -1)
        {

            allBuilder.append((char)inputChar);
        }
        inputStream.close();
        allFile = allBuilder.substring(0);
        while (allFile.length() >=4)
        {
            int index;
            index = allFile.indexOf("TER^^");
            stringArray[fileCounter] = allFile.substring(0, index );
            allFile = allFile.substring(index + 3);


            fileCounter++;
        }
        //System.out.println(stringArray[0]);
        for ( int v = 0 ; v < fileCounter ; v ++)
        {
            FileWriter fw = new FileWriter("decomp " + v+".txt");
            BufferedWriter bw = new BufferedWriter(fw);

            HashMap<String, Character> charToCode = new HashMap<>();
            String stringToRead = "";
            StringBuilder stringToReadd = new StringBuilder(stringToRead);
            char cha[] = {};
            String file = stringArray[v];
            //System.out.println("The File is"+file);
            String header = file.substring(0,file.indexOf("HE^") - 1 );
            String text = file.substring(file.indexOf("HE^") + 7);
            System.out.println("The text is "+text);
            if (header.charAt(0) == '=' && header.charAt(1) != '=')
                header = '\n' + header;

            String seperated[] = header.split(",");
            char c = 0;
            String code = "";
            String entry;
            String fady;
            String zeroArray[] = {"", "00000", "0000", "000", "00", "0"};
            for ( int i = 0; i < seperated.length; i++) {

                entry = seperated[i];
                if (i == 0) {
                    c = entry.charAt(0);
                    code = entry.substring(2);
                }
                if (entry.charAt(0) == '=' && entry.charAt(1) != '=') {
                    c = ',';
                    code = entry.substring(1);
                }
                if (entry.startsWith(" ") && entry.length() > 1) {
                    entry = entry.substring(1);
                    c = entry.charAt(0);
                    code = entry.substring(2);
                    if (entry.charAt(0) == '=' && entry.charAt(1) != '=') {
                        c = '\n';
                        code = entry.substring(1);
                    }
                }
                charToCode.put(code, c);
            }
            //tamam
            cha = text.toCharArray();
            for (int i = 0; i < cha.length; i++) {
                fady = Integer.toBinaryString(cha[i]);
                int length = fady.length();
                if (length < 6) {
                    fady = zeroArray[length] + fady;
                }
                stringToReadd = stringToReadd.append(fady);
            }
            stringToRead = stringToReadd.substring(0);
            System.out.println(stringToRead);
            String concat = "";
            for (int j = 0; j < stringToRead.length(); j++) {
                concat = concat + stringToRead.charAt(j);
                if (charToCode.containsKey(concat)) {
                    //System.out.print(charToCode.get(concat));
                    if (charToCode.get(concat) != '\r')
                        bw.write(charToCode.get(concat));
                    concat = "";
                }

            }
            bw.close();
            fw.close();


        }

    }


    }

