package com.lsh.gank.json;


import com.google.gson.Gson;
import com.lsh.gank.bean.FavoriteData;
import com.lsh.gank.bean.ListData;
import com.lsh.gank.global.App;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {

    public static FavoriteData getData() throws IOException {
        Gson gson = new Gson();
        File cacheFile = App.cacheDorectory;
        File favorite = new File(cacheFile, "favorite");
        if (!favorite.exists()) {
            favorite.createNewFile();
        }
        FileReader fileReader = new FileReader(favorite);
        FavoriteData favoriteData = gson.fromJson(fileReader, FavoriteData.class);
        fileReader.close();
        return favoriteData;
    }

    public static void saveData(ListData.ListBean bean) throws IOException {
        Gson gson = new Gson();
        File cacheFile = App.cacheDorectory;
        File favorite = new File(cacheFile, "favorite");
        if (!favorite.exists()) {
            favorite.createNewFile();
        }
        // 先读取本地数据
        FileReader fileReader = new FileReader(favorite);
        FavoriteData favoriteData = gson.fromJson(fileReader, FavoriteData.class);
        fileReader.close();

        // 再将数据添加然后保存
        if (favoriteData == null) {
            favoriteData = new FavoriteData();
        }

        for (ListData.ListBean listBean : favoriteData.mListBeen) {
            // 去重
            if (listBean._id.equals(bean._id)) {
                return;
            }
        }

        favoriteData.mListBeen.add(bean);
        FileWriter fileWriter = new FileWriter(favorite);
        fileWriter.write(gson.toJson(favoriteData));
        fileWriter.flush();
        fileWriter.close();
    }

    public static void deleteData(ListData.ListBean bean) throws IOException {
        Gson gson = new Gson();
        File cacheFile = App.cacheDorectory;
        File favorite = new File(cacheFile, "favorite");
        if (!favorite.exists()) {
            favorite.createNewFile();
        }
        // 先读取本地数据
        FileReader fileReader = new FileReader(favorite);
        FavoriteData favoriteData = gson.fromJson(fileReader, FavoriteData.class);
        fileReader.close();

        // 如果是空的就创建
        if (favoriteData == null) {
            favoriteData = new FavoriteData();
        } else {
            // 不是空的时候需要遍历进行删除
            int deleteId = -1;
            for (int i = 0, size = favoriteData.mListBeen.size(); i < size; i++) {
                if (favoriteData.mListBeen.get(i)._id.equals(bean._id)) {
                    deleteId = i;
                    break;
                }
            }
            if (deleteId != -1) {
                favoriteData.mListBeen.remove(deleteId);
            }
        }
        FileWriter fileWriter = new FileWriter(favorite);
        fileWriter.write(gson.toJson(favoriteData));
        fileWriter.flush();
        fileWriter.close();
    }

}
