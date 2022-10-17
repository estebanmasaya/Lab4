package se.kth.estebanmm.lab4.model;

import java.io.*;


public class FileIO{
    public static void saveFile(File file, Board board) throws IOException {
        ObjectOutputStream oss = null;
        try{
            FileOutputStream fout = new FileOutputStream(file);
            oss = new ObjectOutputStream(fout);
            oss.writeObject(board);
        } finally {
            if(oss!=null) {
                oss.close();
            }
        }
    }

    public static Board loadFile(File file) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = null;
        Board newBoard;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ois = new ObjectInputStream(fileInputStream);
            newBoard = (Board) ois.readObject();
        }finally {
            if(ois!=null){
                ois.close();
            }
        }
        return newBoard;
    }
}