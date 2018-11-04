package com.apon.usecases.queens;

import com.apon.dancinglinks.algorithm.DancingLinks;

public class MainQueens {
    private static final int N = 8;

    public static void main(String[] args) throws Exception {
        QueenMatrixBuilder queenMatrixBuilder = new QueenMatrixBuilder(N);
        queenMatrixBuilder.createMatrix();

        DancingLinks dancingLinks = new DancingLinks(queenMatrixBuilder.getMatrix());
        System.out.println(dancingLinks.getAllSolutions());
    }
}
