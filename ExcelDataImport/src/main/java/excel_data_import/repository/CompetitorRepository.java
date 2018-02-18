package excel_data_import.repository;

import excel_data_import.domain.Competitor;

public interface CompetitorRepository {
	public Competitor find(String firstName, String lastName);

	public int getTotalCompetitorCount();

	public void persist(Competitor competitor);

}
