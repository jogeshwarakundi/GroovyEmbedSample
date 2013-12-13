package jogesh.datastore;

import java.util.HashMap;

public class DataStoreObject {
	
	private HashMap<String, Object> m_store;
	
	public DataStoreObject() {
		m_store = new HashMap<String, Object>();
	}

	public boolean storeData(String key, Object value) {
		if (key == null || value == null)
		{
			System.out.println("Invalid Input");
			return false;
		}
		m_store.put(key, value);
		return true;
	}
	
	public Object getData(String key) {
		return m_store.get(key);
	}
	
	public String toString() {
		return "sample data store containing a map: " + m_store;
	}
}
