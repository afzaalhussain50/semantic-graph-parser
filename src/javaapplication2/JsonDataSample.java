/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afzaal
 */


public class JsonDataSample {
    public String img_name;
    public String[] scores;
    public String[] captions;
    public List<String> top10Caprions = new ArrayList<>();
    public List<Fact> scenegraph = new ArrayList<>();

    public JsonDataSample() {
    }

    public JsonDataSample(String imgName, String[] scores, String[] captions) {
        this.img_name = imgName;
        this.scores = scores;
        this.captions = captions;
    }
    
};

