package com.hit.memory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest 
{
	String pathFile="C:\\Users\\user\\Desktop\\MMUProjectEnd\\CacheUnitProject\\src\\main\\resource\\datasource.txt";
	String id;
	String name;
	ObjectOutputStream output;
	ObjectInputStream input;
	HashMap<String,String> hMap=new HashMap<String, String>();
	Scanner scan;
	@SuppressWarnings("rawtypes")
	IDao dao;
	IAlgoCache<Long,DataModel<String>> algo;
	CacheUnit<String> cache;
	

	@SuppressWarnings("unchecked")
	public CacheUnitTest() throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		try 
		{
			dao=new DaoFileImpl<String>(pathFile);
			algo=new LRUAlgoCacheImpl<Long,DataModel<String>>(3);
			cache= new CacheUnit<String>(algo,dao);
			dao.save(new DataModel<String>((long) 111,"walla"));
			dao.save(new DataModel<String>((long) 222,"google"));
			dao.save(new DataModel<String>((long) 333,"nana"));
			dao.save(new DataModel<String>((long) 444,"one"));
			dao.save(new DataModel<String>((long) 555,"sport5"));
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void getDataModelsTest() throws ClassNotFoundException, IOException
	{
		DataModel<String>[] dm=new DataModel[3];
		Long [] ids=new Long [3];
		ids[0]=(long) 111;
		ids[1]=(long) 222;
		ids[2]=(long) 333;
		dm=cache.getDataModels(ids);
		Assert.assertEquals("walla", dm[0].getContent());
		Assert.assertEquals("google", dm[1].getContent());
		Assert.assertEquals("nana", dm[2].getContent());
		
		Long [] ids2=new Long [2];
		ids2[0]=(long) 444;
		ids2[1]=(long) 555;
		dm=cache.getDataModels(ids2);
		Assert.assertEquals("one", dm[0].getContent());
		Assert.assertEquals("sport5", dm[1].getContent());
	}
}

