package excel_data_import.core;

import excel_data_import.domain.Match;
import excel_data_import.repository.implementation.MatchRepositoryImpl;
import excel_data_import.service.MatchService;

public class App {
	public static void main(String[] args) {
		System.out.println("Main called");
		MatchService.init(new MatchRepositoryImpl());
		Match match = new Match();
		MatchService.persist(match);
	}
}
