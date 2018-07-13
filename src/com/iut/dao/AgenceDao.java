package com.iut.dao;

import com.iut.beans.Agence;
import com.iut.beans.Client;

public interface AgenceDao {
	Agence getAgenceById(int id);
	Agence getAgenceByClient(Client client);
}
