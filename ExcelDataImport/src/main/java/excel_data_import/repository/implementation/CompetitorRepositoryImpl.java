package excel_data_import.repository.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import excel_data_import.domain.Competitor;
import excel_data_import.repository.CompetitorRepository;

public class CompetitorRepositoryImpl implements CompetitorRepository {
	@Override
	public Competitor find(String firstName, String lastName, String winMSSComment, EntityManager entityManager) {
		String queryString = "SELECT c FROM Competitor c WHERE c.firstName = :firstName AND c.lastName = :lastName";
		if (winMSSComment != null)
			queryString += " AND c.winMSSComment = :winMSSComment";
		try {
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			if (winMSSComment != null)
				query.setParameter("winMSSComment", winMSSComment);
			List<Competitor> existingCompetitors = query.getResultList();
			if (existingCompetitors.size() > 0)
				return existingCompetitors.get(0);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Competitor> findByLastName(String lastName, EntityManager entityManager) {
		String queryString = "SELECT c FROM Competitor c WHERE c.lastName = :lastName";

		try {
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("lastName", lastName);
			List<Competitor> existingCompetitors = query.getResultList();
			if (existingCompetitors.size() > 0)
				return existingCompetitors;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Competitor> getCompetitorListPage(int page, int pageSize, EntityManager entityManager) {
		try {
			String queryString = "SELECT c FROM Competitor c ORDER BY c.lastName";
			final TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Competitor> findAll(EntityManager entityManager) {

		try {
			String queryString = "SELECT c FROM Competitor c ORDER BY c.lastName";
			final TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getTotalCompetitorCount(EntityManager entityManager) {
		try {
			return ((Long) entityManager.createQuery("SELECT COUNT(c) from Competitor c").getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Competitor persist(Competitor competitor) {
		Competitor persistedCompetitor = null;

		EntityManager entityManager = HaurRankingDatabaseUtils.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(competitor);

			persistedCompetitor = find(competitor.getFirstName(), competitor.getLastName(),
					competitor.getWinMSSComment(), entityManager);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

		}
		entityManager.close();
		return persistedCompetitor;
	}

	@Override
	public void delete(Competitor competitor, EntityManager entityManager) {
		try {
			if (!entityManager.contains(competitor))
				competitor = entityManager.merge(competitor);
			entityManager.remove(competitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
