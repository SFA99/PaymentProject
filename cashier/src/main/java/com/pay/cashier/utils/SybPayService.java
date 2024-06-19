package com.pay.cashier.utils;

import java.util.Map;
import java.util.TreeMap;

public class SybPayService {
	/**
	 *
	 * @param trxamt
	 * @param reqsn
	 * @param paytype
	 * @param body
	 * @param remark
	 * @param acct
	 * @param validtime
	 * @param notify_url
	 * @param limit_pay
	 * @param idno
	 * @param truename
	 * @param asinfo
	 * @param sub_appid
	 * @param goods_tag  单品优惠信息
	 * @param chnlstoreid
	 * @param subbranch
	 * @param extendparams具体见接口文档
	 * @param cusip   限云闪付JS支付业务
	 * @param fqnum   限支付宝分期业务
	 * @return
	 * @throws Exception
	 */
	/**
	 * 执行支付操作，通过向系统提交支付请求参数来完成。
	 *
	 * @param trxamt 交易金额，单位为分。
	 * @param reqsn 请求序列号，用于唯一标识每个支付请求。
	 * @param paytype 支付方式。
	 * @param body 商品描述。
	 * @param remark 备注信息。
	 * @param acct 收款方账号。
	 * @param validtime 交易有效时间，格式为yyyyMMddHHmmss。
	 * @param notify_url 异步通知地址。
	 * @param limit_pay 限制支付方式，如不传则不限制。
	 * @param idno 收款方身份证号码（如果有的话）。
	 * @param truename 收款方真实姓名（如果有的话）。
	 * @param asinfo 地域信息（如果有的话）。
	 * @param sub_appid 子应用ID（如果有的话）。
	 * @param goods_tag 商品标签。
	 * @param benefitdetail 利益明细（如果有的话）。
	 * @param chnlstoreid 渠道门店ID（如果有的话）。
	 * @param subbranch 分支机构（如果有的话）。
	 * @param extendparams 扩展参数。
	 * @param cusip 客户IP。
	 * @param fqnum 期货数量（如果有的话）。
	 * @return 返回一个包含支付结果的Map。
	 * @throws Exception 如果支付过程中出现错误，则抛出异常。
	 */
	public Map<String,String> pay(long trxamt,String reqsn,String paytype,String body,String remark,String acct,String validtime,String notify_url,String limit_pay,
			String signtype,String frontUrl,
	        String idno,String truename,String asinfo,String sub_appid,String goods_tag,String benefitdetail,String chnlstoreid,String subbranch,String extendparams,String cusip,String fqnum) throws Exception{
	    // 初始化HTTP连接工具，并指定支付接口URL
	    HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/pay");
	    http.init();
	    // 创建一个有序参数集合，确保参数的顺序
	    TreeMap<String,String> params = new TreeMap<String,String>();
	    // 根据配置，添加必要的请求参数
	    if(!SybUtil.isEmpty(SybConstants.SYB_ORGID))
	        params.put("orgid", SybConstants.SYB_ORGID);
	    params.put("cusid", SybConstants.SYB_CUSID);
	    params.put("appid", SybConstants.SYB_APPID);
	    params.put("version", "11");
	    params.put("trxamt", String.valueOf(trxamt));
	    params.put("reqsn", reqsn);
	    params.put("paytype", paytype);
	    params.put("randomstr", "123456");
	    params.put("body", body);
	    params.put("remark", remark);
	    params.put("validtime", validtime);
	    params.put("acct", acct);
	    params.put("notify_url", notify_url);
	    params.put("limit_pay", limit_pay);
	    params.put("sub_appid", sub_appid);
	    params.put("goods_tag", goods_tag);
	    params.put("benefitdetail", benefitdetail);
	    params.put("chnlstoreid", chnlstoreid);
	    params.put("subbranch", subbranch);
	    params.put("extendparams", extendparams);
	    params.put("cusip", cusip);
	    params.put("fqnum", fqnum);
	    params.put("idno", idno);
	    params.put("truename", truename);
	    params.put("asinfo", asinfo);
	    params.put("signtype", signtype);
		params.put("front_url",frontUrl);
	    // 根据签名类型选择合适的appkey进行签名
	    String appkey = "";
	    if(SybConstants.SIGN_TYPE.equals("RSA"))
	        appkey = SybConstants.SYB_RSACUSPRIKEY;
	    else if(SybConstants.SIGN_TYPE.equals("SM2"))
	        appkey = SybConstants.SYB_SM2PPRIVATEKEY;
	    else
	        appkey = SybConstants.SYB_MD5_APPKEY;
	    params.put("sign", SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE));
	    // 发送POST请求，提交支付参数
	    byte[] bys = http.postParams(params, true);
	    // 解析返回的支付结果
	    String result = new String(bys,"UTF-8");
	    Map<String,String> map = handleResult(result);
	    return map;
	}

	public Map<String,String> cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/cancel");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		if(!SybUtil.isEmpty(SybConstants.SYB_ORGID))
			params.put("orgid", SybConstants.SYB_ORGID);
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("oldreqsn", oldreqsn);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("signtype", SybConstants.SIGN_TYPE);
		String appkey = "";
		if(SybConstants.SIGN_TYPE.equals("RSA"))
			appkey = SybConstants.SYB_RSACUSPRIKEY;
		else if(SybConstants.SIGN_TYPE.equals("SM2"))
			appkey = SybConstants.SYB_SM2PPRIVATEKEY;
		else
			appkey = SybConstants.SYB_MD5_APPKEY;
		params.put("sign", SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}

	public Map<String,String> refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/refund");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		if(!SybUtil.isEmpty(SybConstants.SYB_ORGID))
			params.put("orgid", SybConstants.SYB_ORGID);
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldreqsn", oldreqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("signtype", SybConstants.SIGN_TYPE);
		String appkey = "";
		if(SybConstants.SIGN_TYPE.equals("RSA"))
			appkey = SybConstants.SYB_RSACUSPRIKEY;
		else if(SybConstants.SIGN_TYPE.equals("SM2"))
			appkey = SybConstants.SYB_SM2PPRIVATEKEY;
		else
			appkey = SybConstants.SYB_MD5_APPKEY;
		params.put("sign", SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}

	public Map<String,String> query(String reqsn,String trxid) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/query");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		if(!SybUtil.isEmpty(SybConstants.SYB_ORGID))
			params.put("orgid", SybConstants.SYB_ORGID);
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("reqsn", reqsn);
		params.put("trxid", trxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("signtype", SybConstants.SIGN_TYPE);
		String appkey = "";
		if(SybConstants.SIGN_TYPE.equals("RSA"))
			appkey = SybConstants.SYB_RSACUSPRIKEY;
		else if(SybConstants.SIGN_TYPE.equals("SM2"))
			appkey = SybConstants.SYB_SM2PPRIVATEKEY;
		else
			appkey = SybConstants.SYB_MD5_APPKEY;
		params.put("sign", SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,String> handleResult(String result) throws Exception{
		System.out.println("ret:"+result);
		Map map = SybUtil.json2Obj(result, Map.class);
		if(map == null){
			throw new Exception("返回数据错误");
		}
		if("SUCCESS".equals(map.get("retcode"))){
			TreeMap tmap = new TreeMap();
			tmap.putAll(map);
			String appkey = "";
			if(SybConstants.SIGN_TYPE.equals("RSA"))
				appkey = SybConstants.SYB_RSATLPUBKEY;
			else if(SybConstants.SIGN_TYPE.equals("SM2"))
				appkey = SybConstants.SYB_SM2TLPUBKEY;
			else
				appkey = SybConstants.SYB_MD5_APPKEY;
			if(SybUtil.validSign(tmap, appkey, SybConstants.SIGN_TYPE)){
				System.out.println("签名成功");
				return map;
			}else{
				throw new Exception("验证签名失败");
			}

		}else{
			throw new Exception(map.get("retmsg").toString());
		}
	}

	public Map<String, String> scanPay(long trxamt, String reqsn, String body, String remark, String authcode, String limit_pay, String idno, String truename, String asinfo, String s, String string, String s1) throws Exception{
		// TODO Auto-generated method stub
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/scanqrpay");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		if(!SybUtil.isEmpty(SybConstants.SYB_ORGID))
			params.put("orgid", SybConstants.SYB_ORGID);
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("body", body);
		params.put("remark", remark);
		params.put("authcode", authcode);
		params.put("limit_pay", limit_pay);
		params.put("asinfo", asinfo);
		params.put("signtype", SybConstants.SIGN_TYPE);
		String appkey = "";
		if(SybConstants.SIGN_TYPE.equals("RSA"))
			appkey = SybConstants.SYB_RSACUSPRIKEY;
		else if(SybConstants.SIGN_TYPE.equals("SM2"))
			appkey = SybConstants.SYB_SM2PPRIVATEKEY;
		else
			appkey = SybConstants.SYB_MD5_APPKEY;
		params.put("sign", SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}


}
