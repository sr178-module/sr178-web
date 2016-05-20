package com.sr178.module.web.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.utils.ExportExcel;
import com.sr178.module.utils.FileCreatUtil;
/**
 * @author hzy
 * 2012-7-20
 */
public class BaseActionSupport extends ActionSupport {
	/**  */
	private static final long serialVersionUID = 1L;
	
	private String tokenId;
	
	private String userName;
	/** 错误码 */
	private int code;
	/** 错误描述 */
	private String desc;
	//出错返回到的页面结果
	private String errorResult;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String ip(){
		return ServletActionContext.getRequest().getRemoteAddr();
	}
	
	public <T> void writeExcel(String path,String title,String[] headers,List<T> data,String pattern){
		ExportExcel<T> export = new ExportExcel<T>();
		try
		{
			pattern = "yyyy-MM-dd HH:mm:ss";
			File file = FileCreatUtil.creatNewFile(path);
			OutputStream out = new FileOutputStream(file);
			export.exportExcel(title,headers, data, out,pattern);
			out.close();
			LogSystem.info(path+"excel导出成功！");
		}
		catch (FileNotFoundException e)
		{
			LogSystem.error(e, "");
		}
		catch (IOException e)
		{
			LogSystem.error(e, "");
		}
	}
	
	public void download(String path, HttpServletResponse response) {  
        try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            // 清空response  
            response.reset();  
            // 设置response的Header  
            response.addHeader("Content-Disposition", "attachment;filename="  
                    + new String(filename.getBytes(),"ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());  
            response.setContentType("application/vnd.ms-excel;charset=utf-8");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(String errorResult) {
		this.errorResult = errorResult;
	}
}
