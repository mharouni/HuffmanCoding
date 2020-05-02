/*import Model.Node;
import Model.Tree;

import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Controller {

    public   String filename;
    public Controller(String name) throws IOException {
        this.filename = name;
    }

    public void encodesingleFile() throws IOException {
        File file = new File(this.filename);
        file.encodeSingleFile();
    }

    public void  decodeSingleFile(String name) throws  IOException
    {
        File file = new File(name);
        file.decodeSignleFile();
    }

}
*/
import Model.Node;
import Model.Tree;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Controller {

    public   String filename;
    public Controller(String name) throws IOException {
        this.filename = name;
    }

    public void encodesingleFile(String name) throws IOException {
        Filee file = new Filee(name);
        file.encodeSingleFile();
    }

    public void  decodeSingleFile(String name) throws  IOException
    {
        Filee file = new Filee(name);
        file.decodeSignleFile();
    }


    public void encodeMultipleFiles(String path) throws IOException
    {
        File folder = new File(path);

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println(listOfFiles[i].getName());
            if (listOfFiles[i].isFile()) {

               Filee file = new Filee(path + listOfFiles[i].getName());
               System.out.println(path  + listOfFiles[i].getName());
               file.encodeMultipleFiles();
                System.out.println(listOfFiles[i].getName());

            }
        }
    }



}