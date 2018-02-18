package excel_data_import.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Stage")
public class Stage {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private Long winMssId;

	private String name;
	private Long winMssMatchId;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Match match;

	@OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<StageScoreSheet> stageScoreSheets;

	@Enumerated(EnumType.STRING)
	private ClassifierStage classifierStage;

	@Transient
	boolean newStage;

	@Transient
	int paperTargetCount;

	@Transient
	int popperCount;

	@Transient
	int plateCount;

	@Transient
	int minShotsCount;

	@Transient
	int penaltyTargetCount;

	@Transient
	ClassifierStage saveAsClassifierStage;

	@Transient
	int winMSSStandardStageSetupType;

	public Stage() {
	}

	public Stage(Match match, String name, ClassifierStage classifierStage) {
		this.match = match;
		this.name = name;
		this.classifierStage = classifierStage;
	}

	public ClassifierStage getClassifierStage() {
		return classifierStage;
	}

	public Long getId() {
		return id;
	}

	public Match getMatch() {
		return match;
	}

	public String getName() {
		return name;
	}

	public List<StageScoreSheet> getStageScoreSheets() {
		return stageScoreSheets;
	}

	public Long getWinMssId() {
		return winMssId;
	}

	public Long getWinMssMatchId() {
		return winMssMatchId;
	}

	public void setClassifierStage(ClassifierStage classifierStage) {
		this.classifierStage = classifierStage;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStageScoreSheets(List<StageScoreSheet> stageScoreSheets) {
		this.stageScoreSheets = stageScoreSheets;
	}

	public void setWinMssId(Long winMssId) {
		this.winMssId = winMssId;
	}

	public void setWinMssMatchId(Long winMssMatchId) {
		this.winMssMatchId = winMssMatchId;
	}

	public boolean isNewStage() {
		return newStage;
	}

	public void setNewStage(boolean newStage) {
		this.newStage = newStage;
	}

	public ClassifierStage getSaveAsClassifierStage() {
		return saveAsClassifierStage;
	}

	public void setSaveAsClassifierStage(ClassifierStage saveAsClassifierStage) {
		this.saveAsClassifierStage = saveAsClassifierStage;
	}

	public int getPaperTargetCount() {
		return paperTargetCount;
	}

	public void setPaperTargetCount(int paperTargetCount) {
		this.paperTargetCount = paperTargetCount;
	}

	public int getPopperCount() {
		return popperCount;
	}

	public void setPopperCount(int popperCount) {
		this.popperCount = popperCount;
	}

	public int getPlateCount() {
		return plateCount;
	}

	public void setPlateCount(int plateCount) {
		this.plateCount = plateCount;
	}

	public int getWinMSSStandardStageSetupType() {
		return winMSSStandardStageSetupType;
	}

	public void setWinMSSStandardStageSetupType(int winMSSStandardStageSetupType) {
		this.winMSSStandardStageSetupType = winMSSStandardStageSetupType;
	}

	public int getMinShotsCount() {
		return minShotsCount;
	}

	public void setMinShotsCount(int minShotsCount) {
		this.minShotsCount = minShotsCount;
	}

	public int getPenaltyTargetCount() {
		return penaltyTargetCount;
	}

	public void setPenaltyTargetCount(int penaltyTargetCount) {
		this.penaltyTargetCount = penaltyTargetCount;
	}
}
