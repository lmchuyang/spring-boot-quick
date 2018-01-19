package com.purang.abstrant;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Solver {
    final int N;  
    final float[][] data;  
    final CyclicBarrier barrier;  
  
    class Worker implements Runnable {  
        int myRow;  
  
        Worker(int row) {  
            myRow = row;  
        }  
  
        public void run() {/*
            while (!done()) {  
             //   processRow(myRow); //执行某一行的逻辑  
  
                try {  
                    barrier.await(); //该行逻辑执行完毕后，告知一下  
                } catch (InterruptedException ex) {  
                    return;  
                } catch (BrokenBarrierException ex) {  
                    return;  
                }  
            }  
        */}  
    }  
    
    public Solver(float[][] matrix) {  
        data = matrix;  
        N = matrix.length;  
        barrier = new CyclicBarrier(N,  
                                    new Runnable() {  
                                      public void run() {  
                                       // mergeRows(...); //在每一行都处理完毕以后，执行一个merge操作  
                                      }  
                                    });  
        for (int i = 0; i < N; ++i)  
          new Thread(new Worker(i)).start();  
     
        //waitUntilDone();  
      }  
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
