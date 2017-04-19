package cn.chuanz.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.github.chuanzh.webframe.FreemarkerBuilder;
import com.github.chuanzh.webframe.HtmlBuilder;
import com.github.chuanzh.webframe.annotation.IjHttpServletRequest;

public class Dynamic {
	
	private HtmlBuilder htmlBuilder = new FreemarkerBuilder();

	@IjHttpServletRequest
	private HttpServletRequest request;
	private static Logger logger = Logger.getLogger(Dynamic.class);

	public String detail() {
		htmlBuilder.setValue("no","CA1234");
		htmlBuilder.setValue("dep","上海");
		htmlBuilder.setValue("arr","北京");
		
		String fenxiang_title = "我的分享信息";
		String fenxiang_img = "http://mp.ihnbc.cn/emp/emp.png";
		String fenxiang_desc = "点击查看我的分享信息吧";
		String fenxiang_link = "http://mp.ihnbc.cn/emp/dynamic.e?method=detail";
		htmlBuilder.setValue("fenxiang_title",fenxiang_title);
		htmlBuilder.setValue("fenxiang_img",fenxiang_img);
		htmlBuilder.setValue("fenxiang_desc",fenxiang_desc);
		htmlBuilder.setValue("fenxiang_link",fenxiang_link);
		
		htmlBuilder.setTemplatePath("dynamic/flydtdetail.ftl");
		
		return htmlBuilder.toString();
	}
	
}
