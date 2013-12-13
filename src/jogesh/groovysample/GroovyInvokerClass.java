package jogesh.groovysample;

import java.util.ArrayList;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

import jogesh.datastore.DataStoreObject;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;


public class GroovyInvokerClass
{
	private GroovyScriptEngine m_gse;
	private Binding m_binding;
	private CompilerConfiguration m_config;
//	private ImportCustomizer m_customizer;
	private SecureASTCustomizer m_security;
	
	private DataStoreObject m_dsObject;
	private java.util.Random m_rand;
	
	public GroovyInvokerClass()
	{
	}
	
	public void init()
	{
		try
		{
			//set up the groovy environments
			String[] roots = new String[] {"/Users/jakundi/Code/groovy_samples"};
			m_gse = new GroovyScriptEngine(roots);
			m_binding = new Binding();
			m_config = new CompilerConfiguration();

			m_security = new SecureASTCustomizer();
			ArrayList<String> importsWhiteList = new ArrayList<String>();
			importsWhiteList.add("java.lang.Math");
			m_security.setStarImportsWhitelist(importsWhiteList);
			m_config.addCompilationCustomizers(m_security);
			m_gse.setConfig(m_config);
			
			m_dsObject = new DataStoreObject();
			m_binding.setVariable("dataStore", m_dsObject);
			m_rand = new java.util.Random();
		}
		catch (Exception e)
		{
			System.out.println("Exception in init: " + e);
		}
	}

	private void runScript()
	{
		try
		{			
			m_gse.run("jogesh/anomalydetection/anomalydetection.groovy", m_binding);
		}
		catch(Exception e)
		{
			System.out.println("Oops: Exception: " + e.toString());
		}
	}
	
	public void performAction()
	{
		int i = 0;
		for (i = 0; i < 100; i++)
		{
			generateInputset(i);
			runScript();
		}
	}
	
	private void generateInputset(int num)
	{
		java.util.HashMap<String, Object> input = new java.util.HashMap<String, Object>();
		input.put("cpu", m_rand.nextInt(101));
		input.put("memory", m_rand.nextInt(101));
		input.put("deviceid", "123");
		input.put("timestamp", System.currentTimeMillis());
		m_binding.setVariable("input", input);
	}

	public static void main(String[] args)
	{
		GroovyInvokerClass c = new GroovyInvokerClass();
		c.init();
		c.performAction();
	}
}
