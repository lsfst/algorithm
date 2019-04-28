package com.algorithm.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler {
	
	private static HttpHandler service = null;
	private static Logger logger= LoggerFactory.getLogger( HttpHandler.class );
	
	private HttpHandler() {}
	
	public static HttpHandler getInstance () {
		if (service == null) {
			service = new HttpHandler();
		}
		return service;
	}
	
	private HttpUtils syncUtil = HttpUtils.getInstance();
	
	/**
	 * 同步到pms,post方式
	 * @param
	 */
	public JSONObject post ( String url, String outPutStr, String contentType, String authorization) {
		JSONObject obj=null;
		
//		Map<String, String> result = new HashMap<String, String>();
		
//		StringBuilder sb = new StringBuilder().append(title);
//		if (svn != -1) {
//			sb.append("-"+svn+"-"+exception);
//		}
//		
//		if (rptNum != -1) {
//			sb.append("-"+rptNum);
//		}
		try {
			 obj = syncUtil.doHttpClientPost( outPutStr, contentType,url,authorization);
			
//			if (obj != null) {
//				JSONObject gissue = obj.getJSONObject("issue");
//				if (gissue != null) {
//					pms.setId(Integer.parseInt(gissue.getString("id")));
//				}
//			}

		} catch (Exception e) {
            logger.info("HttpHandler post Exception : "+e.toString());
			e.printStackTrace();
		}
		
		return obj;
	}

	public JSONObject post ( String url, String outPutStr) {
		return post( url,outPutStr,"application/json;",null );
	}

	public JSONObject post ( String url, String outPutStr, String authorization ) {
		return post( url,outPutStr,"application/json;",authorization );
	}


	public JSONObject get ( String url, boolean addHeader) {
		JSONObject obj=null;

		try {
			obj = syncUtil.doHttpClientGet( url, addHeader);

		} catch (Exception e) {
			logger.info("HttpHandler get Exception : "+e.toString());
			e.printStackTrace();
		}

		return obj;
	}

	public JSONObject get ( String url, String header) {
		JSONObject obj=null;

		try {
			obj = syncUtil.doHttpClientGetJson( url,false, header);

		} catch (Exception e) {
			logger.info("HttpHandler get Exception : "+e.toString());
			e.printStackTrace();
		}

		return obj;
	}

	public String getStr ( String url, String header) {
		String res=null;

		try {
			 res = syncUtil.doHttpClientGet( url,false, header);

		} catch (Exception e) {
			logger.info("HttpHandler get Exception : "+e.toString());
			e.printStackTrace();
		}

		return res;
	}

//	public int puts (String url,String putData,String authorizaitionInfo) {
//		int obj=0;
//
//		try {
//			obj = syncUtil.doHttpClientPuts( url, putData,authorizaitionInfo);
//
//		} catch (Exception e) {
//			LogUtils.info("PMSService get Exception : "+e.toString());
//			e.printStackTrace();
//		}
//
//		return obj;
//	}

	public JSONObject put ( String url, String putData, String authorizaitionInfo) {
		JSONObject obj=null;

		try {
			obj = syncUtil.doHttpClientPut( url, putData,authorizaitionInfo);

		} catch (Exception e) {
			logger.info("HttpHandler get Exception : "+e.toString());
			e.printStackTrace();
		}

		return obj;
	}



	public void getDownloadImg ( String url,String savePath,String filename,String authorizaitionInfo) {
		JSONObject obj=null;

//		Map<String, String> result = new HashMap<String, String>();

//		StringBuilder sb = new StringBuilder().append(title);
//		if (svn != -1) {
//			sb.append("-"+svn+"-"+exception);
//		}
//
//		if (rptNum != -1) {
//			sb.append("-"+rptNum);
//		}
		try {
			syncUtil.doHttpClientDownloadFromUrl(  url,  savePath, filename, authorizaitionInfo ) ;

//			if (obj != null) {
//				JSONObject gissue = obj.getJSONObject("issue");
//				if (gissue != null) {
//					pms.setId(Integer.parseInt(gissue.getString("id")));
//				}
//			}

		} catch (Exception e) {
			logger.info("HttpHandler get Exception : "+e.toString());
			e.printStackTrace();
		}

	}

	
//	/**
//	 * 更新PMS
//	 * @param data
//	 */
//	public int put (PmsObject pms) {
//		try {
//			return syncUtil.doHttpClientPut(pms.getId(),pms.doPutStr());
//		} catch (Exception e) {
//			LogUtils.dbg("PMSService put exception..."+e.toString());
//			e.printStackTrace();
//			return -1;
//		}
//	}
//
//	/**
//	 * 获取PMS中的反馈状态,只支持JSON方式
//	 */
//	public int get (int pmsId) {
//		String url = EuFileUtil.getInstance().getDispositionValue("pms.getOrPut.url") + pmsId + ".json";
//		try {
//			JSONObject obj = syncUtil.doHttpClientGet(url, true);
//			if (obj != null) {
//				JSONObject issue = obj.getJSONObject("issue");
//				JSONObject status = issue.getJSONObject("status");
//				int id = status.getIntValue("id");
//				return id;
//			}
//		} catch (Exception e) {
//			LogUtils.dbg("PMSService get Exception : "+e.toString());
//			e.printStackTrace();
//			return -1;
//		}
//		return -1;
//	}
	
}
