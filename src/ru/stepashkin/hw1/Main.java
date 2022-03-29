package ru.stepashkin.hw1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 2, 3, 10);
        GameProgress save2 = new GameProgress(80, 4, 8, 40);
        GameProgress save3 = new GameProgress(90, 6, 15, 120);
        String strSave1 = "H:/Games/savegames/save1.dat";
        String strSave2 = "H:/Games/savegames/save2.dat";
        String strSave3 = "H:/Games/savegames/save3.dat";

        saveGame(strSave1, save1);
        saveGame(strSave2, save2);
        saveGame(strSave3, save3);
        List<String> saveList = new ArrayList<>();
        saveList.add(strSave1);
        saveList.add(strSave2);
        saveList.add(strSave3);

        String zip = "H:/Games/savegames/Zip.zip";
        zipFile(zip, saveList);
        File file1 = new File(strSave1);
        deleteFile(file1);
        File file2 = new File(strSave2);
        deleteFile(file2);
        File file3 = new File(strSave3);
        deleteFile(file3);
    }

    static void saveGame(String string, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(string); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Файл сохранен");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFile(String string, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(string))) {
            for (String l : list) {
                try (FileInputStream fis = new FileInputStream(l)) {
                    ZipEntry zipE = new ZipEntry(l);
                    zout.putNextEntry(zipE);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("Архив создан");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Файл удален");
        }
    }
}
