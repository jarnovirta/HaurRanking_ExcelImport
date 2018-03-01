package excel_data_import.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "StageScoreSheet")
public class StageScoreSheet {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	private Competitor competitor;
	@Transient
	private Long winMssPrimaryKey;
	@Transient
	private Long winMssStageId;
	@Transient
	private Long winMssMemberId;

	@ManyToOne
	private Stage stage;
	private int aHits;
	private int bHits;
	private int cHits;
	private int dHits;
	private int misses = -1;
	private int penalties = -1;
	private double time;
	private boolean disqualified = false;
	private double hitFactor;
	@Enumerated(EnumType.STRING)
	private IPSCDivision ipscDivision;

	@Transient
	private String lastModifiedInWinMSSDatabaseString;

	private int procedurals;
	boolean scoresZeroedForStage = false;

	public StageScoreSheet() {
	}

	public StageScoreSheet(Competitor competitor, double hitFactor, Stage stage, IPSCDivision division) {
		this.competitor = competitor;
		this.hitFactor = hitFactor;
		this.stage = stage;
		this.ipscDivision = division;
	}

	public int getaHits() {
		return aHits;
	}

	public int getbHits() {
		return bHits;
	}

	public int getcHits() {
		return cHits;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public int getdHits() {
		return dHits;
	}

	public double getHitFactor() {
		return hitFactor;
	}

	public Long getId() {
		return id;
	}

	public IPSCDivision getIpscDivision() {
		return ipscDivision;
	}

	public String getLastModifiedInWinMSSDatabaseString() {
		return lastModifiedInWinMSSDatabaseString;
	}

	public int getMisses() {
		return misses;
	}

	public int getPenalties() {
		return penalties;
	}

	public int getProcedurals() {
		return procedurals;
	}

	public Stage getStage() {
		return stage;
	}

	public double getTime() {
		return time;
	}

	public Long getWinMssMemberId() {
		return winMssMemberId;
	}

	public Long getWinMssPrimaryKey() {
		return winMssPrimaryKey;
	}

	public Long getWinMssStageId() {
		return winMssStageId;
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public boolean isScoresZeroedForStage() {
		return scoresZeroedForStage;
	}

	public void setaHits(int aHits) {
		this.aHits = -1;
	}

	public void setbHits(int bHits) {
		this.bHits = -1;
	}

	public void setcHits(int cHits) {
		this.cHits = -1;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public void setdHits(int dHits) {
		this.dHits = -1;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public void setHitFactor(double hitFactor) {
		this.hitFactor = hitFactor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIpscDivision(IPSCDivision ipscDivision) {
		this.ipscDivision = ipscDivision;
	}

	public void setLastModifiedInWinMSSDatabaseString(String lastModifiedInWinMSSDatabaseString) {
		this.lastModifiedInWinMSSDatabaseString = lastModifiedInWinMSSDatabaseString;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public void setProcedurals(int procedurals) {
		this.procedurals = procedurals;
	}

	public void setScoresZeroedForStage(boolean scoresZeroedForStage) {
		this.scoresZeroedForStage = scoresZeroedForStage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void setWinMssMemberId(Long winMssMemberId) {
		this.winMssMemberId = winMssMemberId;
	}

	public void setWinMssPrimaryKey(Long winMssPrimaryKey) {
		this.winMssPrimaryKey = winMssPrimaryKey;
	}

	public void setWinMssStageId(Long winMssStageId) {
		this.winMssStageId = winMssStageId;
	}

}
