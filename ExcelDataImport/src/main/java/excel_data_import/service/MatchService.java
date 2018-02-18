package excel_data_import.service;

import excel_data_import.domain.Match;
import excel_data_import.repository.MatchRepository;

public class MatchService {

	private static MatchRepository matchRepository;

	public static void init(MatchRepository matchRepo) {
		matchRepository = matchRepo;
	}

	public static void persist(Match match) {
		matchRepository.persist(match);

	}
}
