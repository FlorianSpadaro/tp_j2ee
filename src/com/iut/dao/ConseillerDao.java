package com.iut.dao;

import com.iut.beans.Conseiller;

public interface ConseillerDao {
	Conseiller connexion(String login, String password);
}