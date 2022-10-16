package se.kth.estebanmm.lab4.model;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class FileIO{
    public static void saveFile(File file, Board board) throws IOException {
        ObjectOutputStream oss = null;
        try{
            System.out.println("file: " + file);
            FileOutputStream fout = new FileOutputStream(file);
            oss = new ObjectOutputStream(fout);
            System.out.println("TESTSAVE");
            oss.writeObject(board);
            System.out.println("TESTSAVE");
        } finally {
            oss.close();
        }
    }

    public static Board loadFile(File file) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = null;
        Board newBoard;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ois = new ObjectInputStream(fileInputStream);
            System.out.println("TEST");
            newBoard = (Board) ois.readObject();
            System.out.println(newBoard.toMatrix());
            System.out.println("TEST");
        }finally {
            ois.close();
        }
        return newBoard;

    }
}