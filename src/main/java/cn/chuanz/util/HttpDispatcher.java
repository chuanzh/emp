package cn.chuanz.util;

import com.github.chuanzh.util.ConfigRead;
import com.github.chuanzh.webframe.FreemarkerBuilder;
import com.github.chuanzh.webframe.filter.HttpFilter;

public class HttpDispatcher extends HttpFilter {

	@Override
	public int getRunTimeLimit() {
		// TODO Auto-generated method stub
		return ConfigRead.readIntValue("run_time_limit");
	}

	@Override
	public String projectEncode() {
		// TODO Auto-generated method stub
		return "UTF-8";
	}

	@Override
	public String controlFolder() {
		// TODO Auto-generated method stub
		return "com.huoli.control";
	}

	@Override
	protected String alias() {
		// TODO Auto-generated method stub
		return "/mpqq";
	}

	@Override
	protected void initHtmlBuilder() {
		FreemarkerBuilder.init("com.huoli.view", false, new String[]{"common/macro.ftl"});
	}

}
