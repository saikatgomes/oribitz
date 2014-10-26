public class PathInfo {

	private String path = "";
	private int hours = -1;

	PathInfo(String p, int h) {
		setInfo(p, h);
	}

	PathInfo(String p) {
		setInfo(p, -1);
	}

	PathInfo(int h) {
		setInfo("", h);
	}

	PathInfo() {
		setInfo("", -1);
	}

	public void setInfo(String p, int h) {
		path = p;
		hours = h;
	}

	public String getPath() {
		return path;
	}

	public int getHours() {
		return hours;
	}
	public void setPath(String p) {
		path=p;
	}

	public void setHours(int h) {
		hours=h;
	}

}
