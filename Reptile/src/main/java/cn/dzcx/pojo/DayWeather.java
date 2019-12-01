package cn.dzcx.pojo;

public class DayWeather {

	private String time;
	private String info;
	private String sheshidu;
	private String fengxaing;
	private String dengji;

	public DayWeather() {
		super();
	}

	public DayWeather(String time, String info, String sheshidu, String fengxaing, String dengji) {
		super();
		this.time = time;
		this.info = info;
		this.sheshidu = sheshidu;
		this.fengxaing = fengxaing;
		this.dengji = dengji;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSheshidu() {
		return sheshidu;
	}

	public void setSheshidu(String sheshidu) {
		this.sheshidu = sheshidu;
	}

	public String getFengxaing() {
		return fengxaing;
	}

	public void setFengxaing(String fengxaing) {
		this.fengxaing = fengxaing;
	}

	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}

}
