package nju.iip.dto;

/**
 * 课程类
 * @author wangqiang
 *
 */
public class Course {
	
	//课程名
	private String name;
	//得分
	private String score;
	//学期
    private String term;
    //学分
    private String credit;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
}
