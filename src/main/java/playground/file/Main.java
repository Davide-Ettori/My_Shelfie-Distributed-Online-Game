package playground.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/playground/file/file.txt"));
            bw.write("ciao");
            bw.close();
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/playground/file/file.txt"));
            System.out.println(br.readLine());
        }
        catch(Exception e){System.out.println(e);}
    }
}