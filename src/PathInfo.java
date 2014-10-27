/**
 * @author 		Saikat Gomes 
 * Email: 		saikatgomes@gmail.com 
 * Description: Store the Path information
 * 				List of Airport Traversed to reach this node
 * 				Amount of Time taken to reach this node
 */
public class PathInfo {

	private String path = "";
	private float hours = -1;

	PathInfo(String aPath, float hrs) {
		setInfo(aPath, hrs);
	}

	PathInfo(String p) {
		setInfo(p, -1);
	}

	PathInfo(float hrs) {
		setInfo("", hrs);
	}

	PathInfo() {
		setInfo("", -1);
	}

	public void setInfo(String aPath, float hrs) {
		path = aPath;
		hours = hrs;
	}

	public String getPath() {
		return path;
	}

	public float getHours() {
		return hours;
	}
	public void setPath(String aPath) {
		path=aPath;
	}

	public void setHours(float hrs) {
		hours=hrs;
	}

}
