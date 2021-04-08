package com.data4life.code.challenge;

import com.data4life.code.challenge.dao.DatabaseConnectionEstablisher;
import com.data4life.code.challenge.generator.TokenGenrator;
import com.data4life.code.challenge.reader.TokenReader;

import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {

        //token generation and token file creation with 10 million tokens
        TokenGenrator tokenGenrator = new TokenGenrator();
        tokenGenrator.generateTokenListFile(10000000);

        //Creating Token table
        DatabaseConnectionEstablisher daoManager = new DatabaseConnectionEstablisher();
        daoManager.createTokenTable();

        //token reader reads the file and write it in the DB
        TokenReader tokenReader = new TokenReader();
        tokenReader.loadAndSaveTokens();

        //produce a list of all non-unique tokens and their frequencies
        Map<String,Long> uniqueTokensWithFreq = tokenReader.getUniqueTokenListAndFreq();

        //create a list of the three most frequent tokens
        List<String> threeMostFrequentTokens = tokenReader.findMostFrequentTokens(uniqueTokensWithFreq,3);

        System.out.println("3 Most Frequent Tokens");
        threeMostFrequentTokens.forEach(System.out::println);

    }


}
