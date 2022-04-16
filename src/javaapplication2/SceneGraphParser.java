/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.stanford.nlp.scenegraph.RuleBasedParser;
import edu.stanford.nlp.scenegraph.SceneGraph;
import edu.stanford.nlp.scenegraph.SceneGraphAttribute;
import edu.stanford.nlp.scenegraph.SceneGraphNode;
import edu.stanford.nlp.scenegraph.SceneGraphRelation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author afzaal
 */
public class SceneGraphParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("F:\\Users\\afzaal\\Desktop\\_sample.json"));

        int a = 10;

        // convert JSON array to list of users
        List<JsonDataSample> samples = new Gson().fromJson(reader, new TypeToken<List<JsonDataSample>>() {
        }.getType());

        // print users
//        samples.forEach(System.out::println);
        // close reader
        reader.close();

        //a black computer monitor
        //a man is in the water.
        //a white and red toothbrush
        String sentence = "a black beautiful computer monitor.";

        RuleBasedParser parser = new RuleBasedParser();

        int count = 1;
        for (JsonDataSample sample : samples) {
//            if (count==10)
//                break;
//            if (!"COCO_val2014_000000114481.jpg".equals(sample.img_name))
//                continue;
            System.out.print(count++ + ") ");
            System.out.println(sample.img_name);
            List<Fact> sceneGraphImage = new ArrayList<>();
            List<String> captions = Arrays.asList(sample.captions);
            List<String> uniqueCaptions = captions.stream().distinct().collect(Collectors.toList());
            List<String> top10Captions = new ArrayList<>();
            int capSize = 10;
            if (uniqueCaptions.size() < 10) {
                capSize = uniqueCaptions.size();
            }
            for (int i = 0; i < capSize; i++) {
                String caption = uniqueCaptions.get(i);
                SceneGraph sg = parser.parse(caption);
                sample.top10Caprions.add(caption);
    //                System.out.println(sg.toReadableString());
//                List<Fact> sceneGraphCaption;

                //printing the scene graph in JSON form
//                System.out.println("Relations");
                for (SceneGraphRelation rel : sg.relationListSorted()) {
//                    sceneGraphCaption = new ArrayList<>();
                    Fact fact = new Fact();
                    fact.e1_lable = rel.getSource().value().lemma();
                    fact.relation = rel.getRelation().toString();
                    fact.e2_lable = rel.getTarget().value().lemma();
                    boolean found = false;
                    for (Fact fact1 : sceneGraphImage) {
                        if (((fact.e1_lable.equals(fact1.e1_lable))
                                && (fact.e2_lable.equals(fact1.e2_lable))
                                && (fact.relation.equals(fact1.relation)))) {
                            found = true;

                        }

                    }
                    if (!found) {
                        sceneGraphImage.add(fact);
                    }
//                    sceneGraphCaption.add(fact);

                }
//                System.out.println("Attributes");
                for (SceneGraphNode node : sg.nodeListSorted()) {
                    if (node.getAttributes().size() > 0) {
                        for (SceneGraphAttribute attribute : node.getAttributes()) {
//                            System.out.println(node.value().lemma() + " Property " + attribute.toString());
//                            sceneGraphCaption = new ArrayList<>();
//                            sceneGraphCaption.add(node.value().lemma());
//                            sceneGraphCaption.add("Property");
//                            sceneGraphCaption.add(attribute.toString());
//                            sceneGraphImage.add(sceneGraphCaption);

                            Fact fact = new Fact();
                            fact.e1_lable = node.value().lemma();
                            fact.relation = "Property";
                            fact.e2_lable = attribute.toString();
                            //                    sceneGraphCaption.add(fact);
                            boolean found = false;
                            for (Fact fact1 : sceneGraphImage) {
                                if (((fact.e1_lable.equals(fact1.e1_lable))
                                        && (fact.e2_lable.equals(fact1.e2_lable))
                                        && (fact.relation.equals(fact1.relation)))) {
                                    found = true;

                                }

                            }
                            if (!found) {
                                sceneGraphImage.add(fact);
                            }

                        }
                    }

                }
            }
            sample.scenegraph = sceneGraphImage;
            a = 10;
        }

        try (Writer writer = new FileWriter("F:\\Users\\afzaal\\Desktop\\capotion_with_scenegraphs.json")) {
            Gson gson1 = new GsonBuilder().create();
            gson.toJson(samples, writer);
        }
//        gson.toJson(samples, new FileWriter("F:\\Users\\afzaal\\Desktop\\capotion_with_scenegraphs.json"));
        int abc = 20;

    }

}
