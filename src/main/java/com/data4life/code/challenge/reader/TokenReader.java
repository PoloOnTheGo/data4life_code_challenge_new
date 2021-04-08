package com.data4life.code.challenge.reader;

import com.data4life.code.challenge.constant.FileConstants;
import com.data4life.code.challenge.dao.DatabaseConnectionEstablisher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class TokenReader {

    /**
     * loads(reads) the token file and insert into DB without duplicate -- fastest way
     */
    public void loadAndSaveTokens(){
        DatabaseConnectionEstablisher daoManager = new DatabaseConnectionEstablisher();
        daoManager.saveTokens();
    }


    /**
     * reading all lines from the token file
     * ----------------------------------------------------------
     * run: BufferedReader.readLine() into LinkedList
     * lines: 9999999
     * estimatedTime: 11.568879936
     * -----------------------------------------------------------
     * run: BufferedReader.readLine() into ArrayList
     * lines: 9999999
     * estimatedTime: 7.320272837
     * -----------------------------------------------------------
     * run: Files.readAllLines()   //Best
     * lines: 9999999
     * estimatedTime: 5.908587528
     * -----------------------------------------------------------
     * run: Scanner.nextLine() into ArrayList
     * lines: 9999999
     * estimatedTime: 19.052771542
     * -----------------------------------------------------------
     * run: Scanner.nextLine() into LinkedList
     * lines: 9999999
     * estimatedTime: 13.468155686
     * -----------------------------------------------------------
     * run: RandomAccessFile.readLine() into ArrayList
     * lines: 9999999
     * estimatedTime: 96.277709596
     * -----------------------------------------------------------
     * run: RandomAccessFile.readLine() into LinkedList
     * lines: 9999999
     * estimatedTime: 95.783413338
     * -----------------------------------------------------------
     *
     * @param path - file path and fileName
     * @return List of tokens read from the file
     */
    public List<String> readAllLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * finds the unique tokens and frequency using most affective Collectors.groupingBy function
     * @return map of uniquie tokens and their frequency
     */
    public Map<String, Long> getUniqueTokenListAndFreq() {
        List<String> tokenList = readAllLines(FileConstants.SECURE_LOCATION + "/" + FileConstants.FILE_NAME);
        return tokenList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * find the n most frequent tokens using comparator and compareTo and is faster than sorting
     * Uses PriorityQueue to make it more efficient
     * @param uniqueTokensWithFreq
     * @param noOfMostFreq
     * @return List<String> n number of most frequent tokens
     */
    public  <K, V extends Comparable<? super V>> List<String> findMostFrequentTokens(Map<K, V> uniqueTokensWithFreq, int noOfMostFreq) {
        Comparator<? super Map.Entry<K, V>> comparator = new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1) {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }};
        PriorityQueue<Map.Entry<K, V>> highest = new PriorityQueue<Map.Entry<K,V>>(noOfMostFreq, comparator);

        for (Map.Entry<K, V> entry : uniqueTokensWithFreq.entrySet()) {
            highest.offer(entry);
            while (highest.size() > noOfMostFreq) {
                highest.poll();
            }
        }

        List<String> result = new ArrayList<>();
        while (highest.size() > 0) {
            result.add(highest.poll().getKey().toString());
        }
        return result;
    }

}
