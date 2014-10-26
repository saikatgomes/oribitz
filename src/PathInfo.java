public class PathInfo {

	private String path = "";
	private int hours = -1;

	PathInfo(String aPath, int hrs) {
		setInfo(aPath, hrs);
	}

	PathInfo(String p) {
		setInfo(p, -1);
	}

	PathInfo(int hrs) {
		setInfo("", hrs);
	}

	PathInfo() {
		setInfo("", -1);
	}

	public void setInfo(String aPath, int hrs) {
		path = aPath;
		hours = hrs;
	}

	public String getPath() {
		return path;
	}

	public int getHours() {
		return hours;
	}
	public void setPath(String aPath) {
		path=aPath;
	}

	public void setHours(int hrs) {
		hours=hrs;
	}

}
