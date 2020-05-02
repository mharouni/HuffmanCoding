/*import Model.Node;
import Model.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class main {


    public static void main(String args[]) throws IOException {

        Controller controller = null;
        try {
            controller = new Controller("file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.encodesingleFile();
        controller.decodeSingleFile("compressed file.txt");
    }

}*/
import Model.Node;
import Model.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class main {




    public static void main(String args[]) throws IOException {
        /*
        int select;
        Controller controller = null;
        try {
            controller = new Controller("file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Select which action you need to do :\n For compression select 1 \n For decompression select 2 ");
        Scanner sc= new Scanner(System.in);
        select= sc.nextInt();
        if(select==1){
            System.out.println("Please enter file name to compress:");
            Scanner sc2 = new Scanner(System.in);
            String inputFile= sc2.nextLine();
            controller.filename=inputFile;
            controller.encodesingleFile(inputFile);
        }
        else if (select==2){
            System.out.println("Please enter file name to decompress:");
            Scanner sc3 = new Scanner(System.in);
            String deFile= sc3.nextLine();
            controller.filename=deFile;
            controller.decodeSingleFile(deFile);
        }
        */
        //  controller.readAndGenerate();
        //  controller.stringToBinaryString();
        //  Set set=controller.map.entrySet();
        // Iterator aya=set.iterator();
        // while (((Iterator) aya).hasNext()){
        //    Map.Entry ayah=(Map.Entry)aya.next();
        //   System.out.println(ayah.getKey() + " :"+ayah.getValue());
        // }

        //  System.out.println(controller.binaryString);

//        Controller controller = null;
//        try {
//            controller = new Controller("file.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        controller.encodeMultipleFiles("/Users/harouni/Desktop/test/");

       Filee f = new Filee("dodo");
       f.decodeMultipleFiles();
    }

}