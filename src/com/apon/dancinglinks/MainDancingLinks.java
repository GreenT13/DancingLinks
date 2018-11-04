package com.apon.dancinglinks;

import com.apon.dancinglinks.algorithm.DancingLinks;
import com.apon.dancinglinks.builder.MatrixBuilder;

public class MainDancingLinks {

    public static void main(String[] args) throws Exception {
        MatrixBuilder matrixBuilder = new MatrixBuilder(6, 3)
                .setRow(0)
                .setRow(1)
                .setRow(2)
                .setRow(0,1)
                .setRow(1,2)
                .setRow(0,2)
                .setRowIdentifiers("1", "2", "3", "12", "23", "13");
        DancingLinks dancingLinks = new DancingLinks(matrixBuilder.getMatrix());
        System.out.println(dancingLinks.getAllSolutions());
    }

    public static void exampleOne() throws Exception {
        MatrixBuilder matrixBuilder = new MatrixBuilder(6, 7)
                .setRow(0,3,6)
                .setRow(0,3)
                .setRow(3,4,6)
                .setRow(2,4,5)
                .setRow(1,2,5,6)
                .setRow(1,6)
                .setRowIdentifiers("A", "B", "C", "D", "E", "F");
        DancingLinks dancingLinks = new DancingLinks(matrixBuilder.getMatrix());
        System.out.println(dancingLinks.getAllSolutions());
    }
}
