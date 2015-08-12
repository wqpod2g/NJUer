package nju.iip.utils;

import java.util.ArrayList;
import java.util.List;
import nju.iip.dto.Course;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseLogin {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseLogin.class);

	// 登录用户名
	private String txtusername;
	// 登录密码
	private String txtpassword;
	// post参数之一
	private String __EVENTVALIDATION;
	// post参数之一
	private String __VIEWSTATE;

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	/**
	 * 构造函数
	 * 
	 * @param txtusername
	 * @param txtpassword
	 */
	public CourseLogin(String txtusername, String txtpassword) {
		this.txtpassword = txtpassword;
		this.txtusername = txtusername;
	}

	/**
	 * 登录前的准备工作（获取一些必要的参数）
	 */
	public void getPostValue() {
		String loginUrl = "http://219.219.114.101/g_client/framework/login_admin.aspx#/G_Client/PersonalInformation/Prosonal_Score_Search_From.aspx";
		try {
			Document doc = Jsoup.connect(loginUrl).get();
			Element e1 = doc.select("input#__VIEWSTATE").first();
			__VIEWSTATE = e1.attr("value");
			Element e2 = doc.select("input#__EVENTVALIDATION").first();
			__EVENTVALIDATION = e2.attr("value");
			logger.info("__VIEWSTATE=" + __VIEWSTATE
					+ "\n__EVENTVALIDATION=" + __EVENTVALIDATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录，向服务器post一些登录params
	 * 
	 * @param localContext
	 * @return
	 */
	public boolean login() {
		getPostValue();
		boolean flag = false;
		String postUrl = "http://219.219.114.101/g_client/framework/login_admin.aspx";
		try {
			HttpPost httppost = new HttpPost(postUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtusername", txtusername));
			params.add(new BasicNameValuePair("txtpassword", txtpassword));
			params.add(new BasicNameValuePair("__EVENTVALIDATION",
					__EVENTVALIDATION));
			params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
			params.add(new BasicNameValuePair("btnLogin.x", "10"));
			params.add(new BasicNameValuePair("btnLogin.y", "10"));
			httppost.setEntity(new UrlEncodedFormEntity(params));
			CloseableHttpResponse response = httpclient.execute(httppost);
			int statuts_code = response.getStatusLine().getStatusCode();
			logger.info(statuts_code+"");
			if (statuts_code == 302)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
    /**
     * 获取课程页面html代码
     * @param url
     * @return
     */
	public String getPageHtml(String url) {
		String html = "";
		try {
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget); // 必须是同一个HttpClient！
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity);
			httpget.releaseConnection();
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return html;
		}
	}
	
    /**
     * 获取所有课程信息 
     * @return
     */
	public List<Course> getAllCourse() {
		List<Course> course_list = new ArrayList<Course>();
		String html = getPageHtml("http://219.219.114.101/G_Client/PersonalInformation/Prosonal_Score_Search_From.aspx");
        Document doc = Jsoup.parse(html);
        Elements es = doc.select("tr[align=center]");
        int size = es.size();//课程数量
        for(int i=1;i<size-1;i++) {
        	Course course = new Course();
        	Elements E = es.get(i).select("td");
        	course.setName(E.get(0).text());
        	course.setCredit(E.get(1).text());
        	course.setTerm(E.get(2).text());
        	course.setScore(E.get(3).text());
        	course_list.add(course);
        }
		return course_list;
	}

	public static void main(String[] args) {
		CourseLogin login = new CourseLogin("mf1433046", "7027887");
		login.login();
		logger.info(login.getAllCourse().size()+"");
	}

}
