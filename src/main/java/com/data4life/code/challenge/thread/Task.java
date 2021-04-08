package com.data4life.code.challenge.thread;

import com.data4life.code.challenge.generator.TokenGenrator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<List<String>> {
    private int first;
    private int last;

    public Task(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected List<String> compute(){
        List<String> tokenList = new ArrayList<>();
        if(last-first <=5000) {
            tokenList.addAll(generateTokensAndWriteInFile());
        } else {
            int middle = (last + first)/2;
            Task t1 = new Task(first, middle);
            t1.fork();
            Task t2 = new Task(middle+1, last);
            t2.fork();
            tokenList.addAll(t1.join());
            tokenList.addAll(t2.join());
        }
        return tokenList;
    }

    private List<String> generateTokensAndWriteInFile() {
        TokenGenrator tokenGenrator = new TokenGenrator();
        return tokenGenrator.tokenGeneratorUsingForLoop((last-first)+1);
    }
}
