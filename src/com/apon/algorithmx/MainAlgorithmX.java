package com.apon.algorithmx;

import com.apon.algorithmx.algorithm.AlgorithmX;

public class MainAlgorithmX {

    public static void main(String[] args) throws Exception {
        // Create example from wiki.
        IntegerMatrixBuilder integerMatrixBuilder = new IntegerMatrixBuilder(6, 7)
            .set(0,3,6)
            .set(0,3)
            .set(3,4,6)
            .set(2,4,5)
            .set(1,2,5,6)
            .set(1,6);
        AlgorithmX algorithmX = new AlgorithmX(integerMatrixBuilder.getMatrix());
        System.out.println(algorithmX.getAllSolutions());
    }


}
