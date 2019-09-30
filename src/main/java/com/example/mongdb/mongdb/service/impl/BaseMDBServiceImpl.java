package com.example.mongdb.mongdb.service.impl;

import com.example.mongdb.mongdb.service.IBaseMDBService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础存储类型
 * @param <T>
 */
@Transactional(readOnly = true)
public class  BaseMDBServiceImpl<T>  implements IBaseMDBService<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;


    /**
     *
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveList(List<T> list) {
        mongoTemplate.insertAll(list);
    }

    /**
     *
     * @param entity 实体
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public T save(T entity) {
        T ey = mongoTemplate.insert(entity);
        return ey;
    }

    /**
     *
     * @param query
     * @return
     */
    @Override
    public List<T> getByQuery(Query query){
        Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public T getOneByQuery(Query query){
        Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return mongoTemplate.findOne(query,entityClass);
    }

    /**
     *
     * @param file
     * @param fileName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String saveFile(InputStream file, String fileName) {
        try {
            ObjectId store = gridFsTemplate.store(file, fileName);
            return store.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *
     * @param fielID
     * @return
     */
    @Override
    public InputStream getFileById(String fielID){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(fielID));
        GridFSFile file = gridFsTemplate.findOne(query);
        //打开下载流对象
        GridFSDownloadStream gridFS = gridFSBucket.openDownloadStream(file.getObjectId());
        //创建gridFsSource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(file,gridFS);
        //获取流中的数据
        InputStream res = null;
        try {
            res = gridFsResource.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param fileID
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteFileById(String fileID){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(fileID));
        gridFsTemplate.delete(query);
    }

}
