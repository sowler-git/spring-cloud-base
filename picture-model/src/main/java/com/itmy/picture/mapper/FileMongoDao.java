package com.itmy.picture.mapper;

import com.itmy.picture.entity.FileMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: niusaibo
 * @date: 2023-06-29 14:38
 */
@Repository
public interface FileMongoDao extends MongoRepository<FileMongo,String> {

}
