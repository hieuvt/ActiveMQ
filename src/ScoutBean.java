

import java.io.Serializable;

/*
 * +----------------+--------------+------+-----+---------+-------+
| Field          | Type         | Null | Key | Default | Extra |
+----------------+--------------+------+-----+---------+-------+
| id             | int(11)      | NO   | PRI | NULL    |       |
| ce             | varchar(100) | YES  |     | NULL    |       |
| wn             | varchar(15)  | YES  |     | NULL    |       |
| submitTime     | mediumtext   | NO   |     | NULL    |       |
| arrivalTime    | mediumtext   | YES  |     | NULL    |       |
| lastReportTime | mediumtext   | YES  |     | NULL    |       |
| jobId          | varchar(100) | NO   |     | NULL    |       |
+----------------+--------------+------+-----+---------+-------+

 */
public class ScoutBean implements Serializable {

	private int id;
	private String ce;
	private String wn;
	private long submitTime;
	private long arrivalTime;
	private long lastReportTime;
	private String gridJobId;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str;
		if (getGridJobId() != null){
			str = "scoutNo: " + getId() + ", ce: " + getCe() + ", wn: " + getWn()
					+ ", arrivalTime: " + getArrivalTime()
					+ ", lastResponseTime: " + getLastReportTime()
					+ ", jobId: " + getGridJobId();
		} else { //jobId = null in scout report, in this case ignore jobId
			str = "scoutNo: " + getId() + ", ce: " + getCe() + ", wn: " + getWn()
					+ ", submitTime: " + getSubmitTime()
					+ ", arrivalTime: " + getArrivalTime()
					+ ", lastResponseTime: " + getLastReportTime();
		}
		
		return str;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCe() {
		return ce;
	}

	public void setCe(String ce) {
		this.ce = ce;
	}

	public String getWn() {
		return wn;
	}

	public void setWn(String wn) {
		this.wn = wn;
	}

	public long getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(long submitTime) {
		this.submitTime = submitTime;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivedTime) {
		this.arrivalTime = arrivedTime;
	}

	public long getLastReportTime() {
		return lastReportTime;
	}

	public void setLastReportTime(long lastReportTime) {
		this.lastReportTime = lastReportTime;
	}

	public String getGridJobId() {
		return gridJobId;
	}

	public void setGridJobId(String gridJobId) {
		this.gridJobId = gridJobId;
	}
}
