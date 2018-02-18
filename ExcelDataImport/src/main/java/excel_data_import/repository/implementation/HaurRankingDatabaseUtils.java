package excel_data_import.repository.implementation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HaurRankingDatabaseUtils {
	private static EntityManagerFactory entityManagerFactory;

	public static void closeEntityManagerFactory() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen())
			entityManagerFactory.close();

	}

	public static EntityManager createEntityManager() {
		if (entityManagerFactory == null || !entityManagerFactory.isOpen())
			initialize();
		return entityManagerFactory.createEntityManager();
	}

	public static void initialize() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("fi.haur_ranking.jpa");
		} catch (ExceptionInInitializerError e) {
			throw e;
		}
	}
}