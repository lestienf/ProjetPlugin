package mock;

import plugin.Plugin;

public class PluginMock implements Plugin{
	
	public PluginMock(){
		
	}

	@Override
	public String transform(String s) {
		return "MOCK";
	}

	@Override
	public String getLabel() {
		return "MockPlugin";
	}

	@Override
	public String helpMessage() {
		return "MockPlugin";
	}
	

}
