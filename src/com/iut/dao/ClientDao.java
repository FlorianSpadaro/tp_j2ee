package com.iut.dao;

import com.iut.beans.Client;

public interface ClientDao {
	Client connexion(String login, String password);
}
