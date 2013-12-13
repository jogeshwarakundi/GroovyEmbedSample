package jogesh.jssample;

import jogesh.datastore.DataStoreObject;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.script.*;

class JSInvokerClass {
	
	private DataStoreObject m_dsObject;
	private java.util.Random m_rand;
	private Bindings m_bindings;
	private ScriptEngine m_engine;
	
	public JSInvokerClass() {
		
		m_dsObject = new DataStoreObject();
		m_rand = new java.util.Random();
		m_engine = new ScriptEngineManager().getEngineByName("javascript");
		m_bindings = m_engine.getBindings(ScriptContext.ENGINE_SCOPE);
		m_bindings.put("dataStore", m_dsObject);
	}
	
	public void runScript(java.io.Reader reader) {
		
		try {
			m_engine.eval(reader);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public boolean performAction() {
		
		for (int i = 0; i < 2; i++)
		{
			java.io.FileReader reader;
			try {
				 reader = new java.io.FileReader("/Users/jakundi/Code/js_samples/anomalydetection.js");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return false;
			}
			
			java.util.Map<String, Object> input = new java.util.HashMap<String, Object>();
			input.put("cpu", m_rand.nextInt(101));
			input.put("memory", m_rand.nextInt(101));
			input.put("deviceid", "123");
			input.put("timestamp", System.currentTimeMillis());
			m_bindings.put("inputEvent", input);
			
			//generateInputset(i);

			for (Map.Entry<String, Object> me : m_bindings.entrySet())
			{
				System.out.println("Bindings: Key: " + me.getKey() + ", Value: " + me.getValue());
			}
			
			runScript(reader);
		}
		
		return true;
	}
	
	private void generateInputset(int num)
	{
		java.util.Map<String, Object> input = new java.util.HashMap<String, Object>();
		input.put("cpu", m_rand.nextInt(101));
		input.put("memory", m_rand.nextInt(101));
		input.put("deviceid", "123");
		input.put("timestamp", System.currentTimeMillis());
		m_bindings.put("inputEvent", input);
	}
	
	public static void main(String[] args) {
		
		jogesh.jssample.JSInvokerClass jsInvoker = new jogesh.jssample.JSInvokerClass();
		boolean result = jsInvoker.performAction();

		System.out.println("Final result of execution: " + result);
	}
}