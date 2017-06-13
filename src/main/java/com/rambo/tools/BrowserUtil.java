package com.rambo.tools;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * ClassName: Browser (主要是获取浏览器信息的一段代码，可用于 web 端，获取的信息包括浏览器版本、操作系统)
 */
@SuppressWarnings("serial")
public class BrowserUtil extends HttpServlet {
    protected HttpServletRequest request;
    protected HttpSession session;
    protected String userAgent;
    protected String company; //开发浏览器公司名称
    protected String name; //浏览器名称
    protected String version; //浏览器版本号
    protected String os; //电脑操作系统版本号
    @SuppressWarnings("rawtypes")
    private Hashtable supportedLanguages; // Untersttzte Sprachen

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * param request
     * param session
     */
    public BrowserUtil(HttpServletRequest request, HttpSession session) {
        this.initialize();
        this.request = request;
        this.session = session;
        this.setUserAgent(this.request.getHeader("User-Agent"));
        this.setCompany();
        this.setName();
        this.setVersion();
        this.setOs();
    }

    /**
     * Description: TODO(这里用一句话描述这个方法的作用)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void initialize() {
        this.supportedLanguages = new Hashtable(2);
        this.supportedLanguages.put("en", "");
        this.supportedLanguages.put("de", "");
    }

    /**
     * Description: TODO(设置用户的代理)
     */
    public void setUserAgent(String httpUserAgent) {
        this.userAgent = httpUserAgent.toLowerCase();
    }

    /**
     * Description: TODO(根据用户浏览器代理，来获取公司)
     */
    private void setCompany() {
        if (this.userAgent.indexOf("msie") > -1) {
            this.company = "Microsoft";
        } else if (this.userAgent.indexOf("opera") > -1) {
            this.company = "Opera Software";
        } else if (this.userAgent.indexOf("mozilla") > -1) {
            this.company = "Netscape Communications";
        } else {
            this.company = "unknown";
        }
    }

    public String getCompany() {
        return this.company;
    }

    /**
     * Description: 根据公司名称获取用户浏览器名称
     */
    private void setName() {
        if (this.company == "Microsoft") {
            this.name = "Microsoft Internet Explorer";
        } else if (this.company == "Netscape Communications") {
            this.name = "Netscape Navigator";
        } else if (this.company == "Operasoftware") {
            this.name = "Operasoftware Opera";
        } else {
            this.name = "unknown";
        }
    }

    public String getName() {
        return this.name;
    }

    /**
     * Description: TODO(这里用一句话描述这个方法的作用) 根据用户代理来获取浏览器版本
     * param: TODO (入参描述)
     * return: TODO (返回类型和参数描述)
     * date：2015年8月7日下午3:01:54
     */
    private void setVersion() {
        int tmpPos;
        String tmpString;
        if (this.company == "Microsoft") {
            String str = this.userAgent.substring(this.userAgent.indexOf("msie") + 5);
            this.version = str.substring(0, str.indexOf(";"));
        } else {
            tmpString = (this.userAgent.substring(tmpPos = (this.userAgent.indexOf("/")) + 1, tmpPos + this.userAgent.indexOf(" "))).trim();
            this.version = tmpString.substring(0, tmpString.indexOf(" "));
        }
    }

    public String getVersion() {
        return this.version;
    }

    /**
     * Description: TODO(这里用一句话描述这个方法的作用) 根据用户的代理来来来获取操作系统
     * param: TODO (入参描述)
     * return: TODO (返回类型和参数描述)
     * date：2015年8月7日下午3:02:48
     */
    private void setOs() {
        if (this.userAgent.indexOf("win") > -1) {
            if (this.userAgent.indexOf("windows 95") > -1 || this.userAgent.indexOf("win95") > -1) {
                this.os = "Windows 95";
            }
            if (this.userAgent.indexOf("windows 98") > -1 || this.userAgent.indexOf("win98") > -1) {
                this.os = "Windows 98";
            }
            if (this.userAgent.indexOf("windows nt") > -1 || this.userAgent.indexOf("winnt") > -1) {
                this.os = "Windows NT";
            }
            if (this.userAgent.indexOf("win16") > -1 || this.userAgent.indexOf("windows 3.") > -1) {
                this.os = "Windows 3.x";
            }
        }
    }

    public String getOs() {
        return this.os;
    }
}