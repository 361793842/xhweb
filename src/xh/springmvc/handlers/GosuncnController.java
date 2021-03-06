package xh.springmvc.handlers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinamobile.fsuservice.Test;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsstationService;

/**
 * 动环设备处理类
 * @author 12878
 *
 */
@Controller
@RequestMapping(value = "/gonsuncn")
public class GosuncnController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BsstationController.class);
	private FlexJSON json=new FlexJSON();
	private BsstationBean bsstationBean=new BsstationBean();
	private WebLogBean webLogBean=new WebLogBean();
	
	
	/**
	 * 获取动环相关信息
	 */
	@RequestMapping("/oneBsEmh")
	public void oneBsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String FSUID = "09201704160085";
		List<Map<String,String>> list = null;
		List<String> list1 = new ArrayList<String>();
		list1.add("170100000000001");
		list1.add("170200000000001");
		list1.add("170300000000001");
		list1.add("170400000000001");
		list1.add("170500000000001");
		list1.add("170700000000001");
		try {
			list = Test.getDataByList(FSUID,list1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
