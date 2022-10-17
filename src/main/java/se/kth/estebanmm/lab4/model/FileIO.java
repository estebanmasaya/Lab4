package se.kth.estebanmm.lab4.model;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class FileIO{
    /**
     * Allows a file to be saved
     * @param file - which file to save
     * @param board - takes in a board to make it to a file
     * @throws IOException - the exception that might occur
     */
    public static void saveFile(File file, Board board) throws IOException {
        ObjectOutputStream oss = null;
        try{
            System.out.println("file: " + file);
            if (file == null){
                System.out.println("Hello");
            }
            FileOutputStream fout = new FileOutputStream(file);
            oss = new ObjectOutputStream(fout);
            System.out.println("TESTSAVE");
            oss.writeObject(board);
            System.out.println("TESTSAVE");
        } finally {
            if (oss != null){
                oss.close();
            }
        }
    }

    /**
     * Allows to load a file to the program
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
            if (ois != null){
                ois.close();
            }
        }
        return newBoard;

    }
}