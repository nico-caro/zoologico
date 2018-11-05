package com.zoo.web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SectorDAO  {

	@Autowired
	private ISectorDAO crud;
	
	public ISectorDAO crud()
	{
		return this.crud;
	}
}
