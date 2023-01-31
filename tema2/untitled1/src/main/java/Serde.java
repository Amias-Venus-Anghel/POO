import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serde {

    static String toJson(){
        Definition def1 = new Definition("aba", "cadabra", 2022, List.of("a", "b"));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rez = gson.toJson(def1);
        System.out.println(rez);
        return rez;
    }

    static Definition fromJson(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String input = "{\"dict\":\"aba\",\"dictType\":\"cadabra\",\"year\":2022,\"text\":[\"a\",\"b\"]}";
        return gson.fromJson(input, Definition.class);
    }

    static Definition fromJsonFile(String fileName){
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            StringBuilder sb = new StringBuilder();
            while(sc.hasNextLine()){
                sb.append(sc.nextLine());
            }
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(sb.toString(), Definition.class);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }
}
