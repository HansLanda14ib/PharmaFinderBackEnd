package com.localisation.dao;

import java.util.List;

public interface IDao<T> {
	T save(T t);
	T update (T t,int id);
	List<T> findAll();
	void delete(T t);
	T findById(int id);
}
