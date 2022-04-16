/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import edu.stanford.nlp.scenegraph.RuleBasedParser;
import edu.stanford.nlp.scenegraph.SceneGraph;

/**
 *
 * @author afzaal
 */
public class TestingCaption {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RuleBasedParser parser = new RuleBasedParser();
         SceneGraph sg = parser.parse("blue sky with clouds.");
         System.out.println(sg.toReadableString());
    }
    
}
