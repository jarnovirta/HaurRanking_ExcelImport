package excel_data_import.service;

import excel_data_import.domain.Competitor;
import excel_data_import.repository.CompetitorRepository;

public class CompetitorService {

	private static CompetitorRepository competitorRepository;

	public static void init(CompetitorRepository competitorRepo) {
		competitorRepository = competitorRepo;
	}

	public static Competitor find(String firstName, String lastName) {
		return competitorRepository.find(firstName, lastName);

	}

	public static void persist(Competitor competitor) {
		competitorRepository.persist(competitor);
	}

}
