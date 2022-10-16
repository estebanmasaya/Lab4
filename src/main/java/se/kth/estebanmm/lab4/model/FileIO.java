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
            FileOutputStream fout = new FileOutputStream(file);
            oss = new ObjectOutputStream(fout);
            oss.writeObject(board);
        } finally {
            oss.close();
        }
    }

    public static Board loadFile(File file, Board board) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ois = new ObjectInputStream(fileInputStream);
            board = (Board) ois.readObject();
        }finally {
            ois.close();
        }
        return board;

    }
}