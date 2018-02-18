package excel_data_import.repository.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import excel_data_import.domain.Competitor;
import excel_data_import.repository.CompetitorRepository;

public class CompetitorRepositoryImpl implements CompetitorRepository {
	@Override
	public Competitor find(String firstName, String lastName) {
		EntityManager entityManager = HaurRankingDatabaseUtils.createEntityManager();
		String queryString = "SELECT c FROM Competitor c WHERE c.firstName = :firstName AND c.lastName = :lastName";

		try {
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			List<Competitor> existingCompetitors = query.getResultList();
			if (existingCompetitors.size() > 0)
				return existingCompetitors.get(0);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public int getTotalCompetitorCount() {
		EntityManager entityManager = HaurRankingDatabaseUtils.createEntityManager();
		try {
			return ((Long) entityManager.createQuery("SELECT COUNT(c) from Competitor c").getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void persist(Competitor competitor) {
		EntityManager entityManager = HaurRankingDatabaseUtils.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(competitor);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

		}
		entityManager.close();
	}
}
