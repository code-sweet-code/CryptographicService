package service;import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DataManager{
	private static DataManager instance = new DataManager();
	private Map<String,List<String>> data;
	private DataManager() {
		data = new HashMap<String, List<String>>();
		loadData();
	}
	
	public static DataManager getInstance() {
		return instance;
	}
	
	private String parse(String s) {
		return s.substring(s.indexOf(":")+1);
	}
	
	private void loadData() {
		FileOperator fo = FileOperator.getInstance();
		List<String> attr = new LinkedList<String>();
		
		while(fo.ready()) {
			String s = fo.read();
			if(s.startsWith("APPID")) {
				data.put(parse(s), attr);
			}else if(s.startsWith("TOKEN")){
				attr.add(parse(s));
			}else if(s.startsWith("PRI")){
				attr.add(parse(s));
			}else if(s.startsWith("PUB")){
				attr.add(parse(s));
			}else if(s.equals("")) {
				attr = new LinkedList<String>();
			}
		}
	}
	
	public void sync() throws IOException {
		FileOperator fo = FileOperator.getInstance();
		String prifix[] = {"TOKEN", "PRI", "PUB"};
		Iterator<Entry<String, List<String>>> iter = data.entrySet().iterator();
		while(iter.hasNext()) {
			Entry<String, List<String>> e = iter.next();
			String k = e.getKey();
			List<String> v = e.getValue();
			fo.write("APPID:"+k+"\n");
			for(int i=0; i<v.size()&&i<3; i++) {
				fo.write(prifix[i]+":"+v.get(i)+"\n");
			}
			fo.write("\n");
		}
	}
	
	public List<String> searchApp(String id){
		return data.get(id);
	}
	
	public void updateApp(String id, List<String> attr) {
		data.put(id, attr);
	}
}
