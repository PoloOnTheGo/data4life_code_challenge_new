package com.data4life.code.challenge.generator;


import com.data4life.code.challenge.constant.FileConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TokenGenrator {

    /**
     * Loops for 'tokenListSize' times and call getRandomString
     * performing better than fork-join pool( due to my system configurations)
     * @param tokenListSize
     * @return list of tokens generated of size tokenListSize
     */
    public List<String> tokenGeneratorUsingForLoop(int tokenListSize){
        List<String> tokenList = new ArrayList<>();
        for(int i=0; i<tokenListSize; i++){
            TokenGenrator tokenGenrator = new TokenGenrator();
            String token = tokenGenrator.getRandomString(7);
            tokenList.add(token);
        }
        return tokenList;
    }

    /**
     * Uses Files.write as it creates or (delete and create) file with the given list of data(10 million)[tokenList] one per line in comparable time(660ms)
     * as done using File, createNewFile, FileWriter, BufferedWriter (654ms) or other file writing methods
     *
     * @param tokenList : required
     */
    public void tokenFileWriter(List<String> tokenList) {
        try {
            Files.write(Paths.get(FileConstants.SECURE_LOCATION + "/" + FileConstants.FILE_NAME), tokenList,
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace(); // can be improved using logger
        }
    }

    /**
     * Use SecureRandom to generate random string that are secure
     * get 7 random characters starting from position 97('a') to 122('z') thus covering the whole small letters
     * concatenating the randomly chosen letters to form the 7 letter long random string
     * @param length : length of the random string
     * @return one random string
     */
    public String getRandomString(int length) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(length, 97, 123);

        return specialChars.mapToObj(data -> (char) data)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * Create tokenListSize long random token list
     * creates new file and add the tokens to the file
     * @param tokenListSize
     */
    public void generateTokenListFile(int tokenListSize) {

        //taking 27 seconds to generate 10 million tokens
        long startTimeForLoop = System.currentTimeMillis();
        List<String> tokens = tokenGeneratorUsingForLoop(tokenListSize);
        long stopTimeForLoop = System.currentTimeMillis();
        System.out.println("For Loop time:" + (stopTimeForLoop - startTimeForLoop));

        // taking 36 seconds to generate 10 million token with task tree and threads from ForkJoinPool
//        long startTimeForkJoin = System.currentTimeMillis();
//        tokens = tokenWriterUsingForkJoinPool(tokenListSize);
//        long stopTimeForkJoin = System.currentTimeMillis();
//        System.out.println("Fork join time:" + (stopTimeForkJoin - startTimeForkJoin));

        long startTimeWriting = System.currentTimeMillis();
        tokenFileWriter(tokens); // 660ms for 10 million tokens
        long stopTimeWriting = System.currentTimeMillis();
        System.out.println("Write time:" + (stopTimeWriting - startTimeWriting));

        // can be improved using logger
    }

    /**
     * Use forkJoin pool to generate large number of tokens
     * (unfortunately taking longer time in my system as compared to for-loop)
     * @param tokenListSize
     * @return
     *//*
    public List<String> tokenWriterUsingForkJoinPool(int tokenListSize) {
        Task task = new Task(0, tokenListSize);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<String> tokenList =  pool.invoke(task);
        pool.shutdown();
        return tokenList;
    }*/

}


