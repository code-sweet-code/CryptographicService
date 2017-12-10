import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class JsonBuilder {
	private Map<String, String> json;
	public JsonBuilder() {
		json = new HashMap<String, String>();
	}
	public void put(String k, String v) {
		json.put(k, v);
	}
	
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, String>> iter = json.entrySet().iterator();
		builder.append("{");
		while(iter.hasNext()) {
			Entry e = iter.next();
			builder.append((String) e.getKey());
			builder.append(":");
			builder.append((String) e.getValue());
			builder.append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("}");
		return builder.toString();
	}
}
