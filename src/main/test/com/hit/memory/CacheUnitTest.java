package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class CacheUnitTest {
    @Test
    public void getDataModels() {
        IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<>(5);
        IDao<Long, DataModel<String>> dao = new DaoFileImpl<>("C:\\JAVA TEST\\TestFile.txt");
        CacheUnit<String> cacheUnit = new CacheUnit<>(algo, dao);

        Long[] ids = new Long[] {0L, 1L, 2L, 3L, 4L, 5L};
        DataModel<String>[] dataModels = new DataModel[6];

        for (int i = 0; i < dataModels.length; i++) {
            dataModels[i] = new DataModel<>(ids[i], ids.toString());
        }

        for (DataModel<String> dataModel : dataModels) {
            dao.save(dataModel);
        }

        DataModel<String>[] retDataModels = null;
        try {
            retDataModels = cacheUnit.getDataModels(ids);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < dataModels.length; i++) {
            assertEquals(dataModels[i], retDataModels[i]);
        }
    }
}