package se.kth.estebanmm.lab4.model;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
}
