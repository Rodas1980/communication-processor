package communicationprocessor.services.metric.msg;

import java.util.LinkedHashMap;

public class WordRanking {

    private String message;

    private LinkedHashMap<String,Integer> ranking = new LinkedHashMap<>();

    public WordRanking(String message){

        this.message = message;

        ranking.put("ARE",0);
        ranking.put("YOU",0);
        ranking.put("FINE",0);
        ranking.put("HELLO",0);
        ranking.put("NOT",0);
        buildRankings();
    }

    public WordRanking(){

        ranking.put("ARE",0);
        ranking.put("YOU",0);
        ranking.put("FINE",0);
        ranking.put("HELLO",0);
        ranking.put("NOT",0);

    }


    private void  buildRankings(){

        for (String word: ranking.keySet()){

            Integer value = ranking.get(word);
            value =  message.contains(word) ? value + 1: value;
            ranking.put(word,value);
        }

    }

    public LinkedHashMap<String, Integer> getRanking() {
        return ranking;
    }

    public void setRanking(LinkedHashMap<String, Integer> ranking) {
        this.ranking = ranking;
    }

    public void sum(WordRanking ranking){

        for (String word: this.ranking.keySet()){

            Integer value = this.ranking.get(word);
            value += ranking.getRanking().get(word);
            this.ranking.put(word,value);
        }

    }

    public String toString(){

        StringBuilder stringBuilder = new StringBuilder();

        for (String word: this.ranking.keySet()){


            Integer value = this.ranking.get(word);
            stringBuilder.append(word + ":" + value + "<br>");

        }

        return stringBuilder.toString();
    }

}
