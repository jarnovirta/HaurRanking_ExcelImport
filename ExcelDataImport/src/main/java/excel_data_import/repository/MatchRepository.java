package excel_data_import.repository;

import excel_data_import.domain.Match;

public interface MatchRepository {

	public void persist(Match match);

}
