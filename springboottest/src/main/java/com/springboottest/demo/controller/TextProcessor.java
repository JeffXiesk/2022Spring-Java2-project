package com.springboottest.demo.controller;

import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

class union {
    String word;
    int start_index;
    int end_index;

    union(String word, int start_index, int end_index) {
        this.word = word;
        this.start_index = start_index;
        this.end_index = end_index;
    }
}

public class TextProcessor {
    TreeMap<String, Integer> treeMap;
    ArrayList<union> pack;
    String content;


    //Powerful Constructor
    public TextProcessor(String content) throws IOException {
        this.content = content;
        pack = new ArrayList<>();

        char c;
        int i;
        int l = -1;
        StringBuilder word = new StringBuilder();
        String the_one;
        int start_index = 0;

        int j;
        for (j = 0; j < content.length(); j++) {
            i = content.charAt(j);
            c = (char) i;

            if (i == 45 || (i >= 48 && i <= 57) || (i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
                if (word.toString().equals("")) {
                    start_index = j;
                }
                word.append(c);
            } else if (i == 39) {
                if ((l >= 65 && l <= 90) || (l >= 97 && l <= 122)) {
                    int next = content.charAt(++j);
                    if ((next >= 65 && next <= 90) || (next >= 97 && next <= 122)) {
                        word.append(c);
                        word.append((char) next);
                        l = next;
                        continue;
                    } else {
                        the_one = word.toString();
                        pack.add(new union(the_one, start_index, j - 1));
                        word.delete(0, word.length());
                    }
                }
            } else {
                the_one = word.toString();
                if (!the_one.equals("")) {
                    pack.add(new union(the_one, start_index, j));
                    word.delete(0, word.length());
                }
                if (i == 10) {
                    l = i;
                    continue;
                }
            }
            l = i;
        }

        //ensure that the last word is also safe
        the_one = word.toString();
        if (!the_one.equals("")) {
            pack.add(new union(the_one, start_index, j));
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<Pair<String, Integer>> getCommonWords() {
        treeMap = new TreeMap<>();

        //read, count and store the word to treemap
        for (union u : pack) {
            getCommonWords_helper(u.word.toLowerCase());
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(treeMap.entrySet());
        list.sort((first, second) -> {
            if (first.getValue() > second.getValue()) {
                return -1;
            } else if (first.getValue().equals(second.getValue())) {
                int a = first.getKey().length();
                int b = second.getKey().length();
                if (first.getKey().contains(second.getKey())) {
                    return 1;
                } else if (second.getKey().contains(first.getKey())) {
                    return -1;
                } else {
                    for (int j = 0; j < Math.min(a, b); j++) {
                        if ((int) first.getKey().charAt(j) < (int) second.getKey().charAt(j)) {
                            return -1;
                        } else if ((int) first.getKey().charAt(j) > (int) second.getKey().charAt(j)) {
                            return 1;
                        }
                    }
                    return 0;
                }
            } else {
                return 1;
            }
        });

        String[] stopwords = new String[]{"is", "the", "a", "in", "to", "how", "of", "i", "and", "with",
                "using", "-", "on", "for", "from", "not", "an", "when", "why", "it", "can", "my"};

        //start to filter and get the result
        HashSet<String> h = new HashSet<>(Arrays.asList(stopwords));
        List<Pair<String, Integer>> result = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<String, Integer> s : list) {
            if (!h.contains(s.getKey())) {
                result.add(new Pair<>(s.getKey(), s.getValue()));
                counter++;
            }
            if (counter == 10) {
                break;
            }
        }

        return result;
    }

    public void getCommonWords_helper(String the_one) {
        if (!treeMap.containsKey(the_one)) {
            treeMap.put(the_one, 1);
        } else {
            treeMap.put(the_one, treeMap.get(the_one) + 1);
        }
    }
}