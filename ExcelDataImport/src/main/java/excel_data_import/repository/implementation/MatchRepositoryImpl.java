package excel_data_import.repository.implementation;

import javax.persistence.EntityManager;

import excel_data_import.domain.Match;
import excel_data_import.repository.MatchRepository;

public class MatchRepositoryImpl implements MatchRepository {

	@Override
	public void persist(Match match) {
		EntityManager entityManager = HaurRankingDatabaseUtils.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(match);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

		}
		entityManager.close();

	}

}
