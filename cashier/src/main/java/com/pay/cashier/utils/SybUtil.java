package com.pay.cashier.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class SybUtil {
	/**
	 * js转化为实体
	 *
	 * @param <T>
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static <T> T json2Obj(String jsonstr, Class<T> cls) {
		JSONObject jo = JSONObject.fromObject(jsonstr);
		T obj = (T) JSONObject.toBean(jo, cls);
		return obj;
	}

	/**
	 * md5
	 *
	 * @param b
	 * @return
	 */
	public static String md5(byte[] b) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(b);
			byte[] hash = md.digest();
			StringBuffer outStrBuf = new StringBuffer(32);
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i] & 0xFF;
				if (v < 16) {
					outStrBuf.append('0');
				}
				outStrBuf.append(Integer.toString(v, 16).toLowerCase());
			}
			return outStrBuf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new String(b);
		}
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}

	/**
	 * 生成随机码
	 *
	 * @param n
	 * @return
	 */
	public static String getValidatecode(int n) {
		Random random = new Random();
		String sRand = "";
		n = n == 0 ? 4 : n;// default 4
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}



	public static boolean validSign(TreeMap<String, String> param,
									String appkey, String signType) throws Exception {
		if (param != null && !param.isEmpty()) {
			if (!param.containsKey("sign"))
				return false;
			String sign = param.remove("sign");
			if ("MD5".equals(signType)) {// 如果是md5则需要把md5的key加入到排序
				param.put("key", appkey);
			}
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : param.entrySet()) {
				if (entry.getValue() != null && entry.getValue().length() > 0) {
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			if ("MD5".equals(signType)) {
				return sign.toLowerCase().equals(
						md5(sb.toString().getBytes("UTF-8")).toLowerCase());
			} else if("SM2".equals(signType)){
				PublicKey publicKey = SmUtil.pubKeySM2FromBase64Str(appkey);
				return SmUtil.verifySM3SM2(publicKey, "Allinpay", Base64.decodeBase64(sign), sb.toString().getBytes("UTF-8"));
			}else {
				return rsaVerifyPublickey(sb.toString(), sign, appkey, "UTF-8");
			}
		}
		return false;
	}


	/**
	 * 使用RSA算法验证公钥签名的合法性。
	 *
	 * @param content 待验证的数据内容。
	 * @param sign 待验证的签名数据。
	 * @param publicKey RSA公钥，采用Base64编码。
	 * @param charset 编码格式，用于解码公钥和内容。
	 * @return 如果签名验证成功，则返回true；否则返回false。
	 * @throws Exception 如果公钥解析或签名验证过程中发生错误，则抛出异常。
	 */
	public static boolean rsaVerifyPublickey(String content, String sign,
											 String publicKey, String charset) throws Exception {
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA",
					Base64.decodeBase64(publicKey.getBytes()));
			return rsaVerifyPublickey(content, sign, pubKey, charset);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("RSAcontent = " + content + ",sign=" + sign
					+ ",charset = " + charset, e);
		}
	}

	public static boolean rsaVerifyPublickey(String content, String sign,
											 PublicKey pubKey, String charset) throws Exception {
		try {
			java.security.Signature signature = java.security.Signature
					.getInstance("SHA1WithRSA");

			signature.initVerify(pubKey);

			if (charset == null || "".equals(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			throw e;
		}
	}
	public static String unionSign(TreeMap<String, String> params,String appkey,
								   String signType) throws Exception {
		// TODO Auto-generated method stub

		params.remove("sign");
		if ("MD5".equals(signType)) {// 如果是md5则需要把md5的key加入到排序
			params.put("key", appkey);
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() != null && entry.getValue().length() > 0) {
				sb.append(entry.getKey()).append("=").append(entry.getValue())
						.append("&");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		String sign = "";
		if ("MD5".equals(signType)) {
			System.out.println(sb.toString());
			sign = md5(sb.toString().getBytes("UTF-8"));// 记得是md5编码的加签
			params.remove("key");
		}else if("SM2".equals(signType)){
			System.out.println(sb.toString());
			PrivateKey privkey = SmUtil.privKeySM2FromBase64Str(appkey);
			sign = SmUtil.signSM3SM2RetBase64(privkey, params.get("appid"), sb.toString().getBytes("UTF-8"));//签名
		} else {
			System.out.println(sb.toString());
			sign = rsaSign(sb.toString(), appkey, "UTF-8");
		}
		return sign;
	}

	public static String rsaSign(String content, String privateKey,
								 String charset) throws Exception {
		PrivateKey priKey = getPrivateKeyFromPKCS8("RSA",
				Base64.decodeBase64(privateKey.getBytes()));
		return rsaSign(content, priKey, charset);
	}

	public static String rsaSign(String content, byte[] privateKey,
								 String charset) throws Exception {
		PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", privateKey);
		return rsaSign(content, priKey, charset);
	}

	public static String rsaSign(String content, PrivateKey priKey,
								 String charset) throws Exception {
		java.security.Signature signature = java.security.Signature
				.getInstance("SHA1WithRSA");
		signature.initSign(priKey);
		if (charset == null || "".equals(charset)) {
			signature.update(content.getBytes());
		} else {
			signature.update(content.getBytes(charset));
		}
		byte[] signed = signature.sign();

		return new String(Base64.encodeBase64(signed));
	}

	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
													byte[] encodedKey) throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	/**
	 * 从X.509编码的公钥字节序列中获取公钥对象。
	 *
	 * 此方法用于将使用特定算法加密的X.509格式的公钥字节序列转换为Java密钥库中的PublicKey对象。
	 * 它支持不同算法的公钥提取，提供了灵活的加密技术支持。
	 *
	 * @param algorithm 加密算法名称，用于指定生成公钥对象的算法，例如RSA、DSA等。
	 * @param encodedKey X.509格式的公钥字节序列，用于表示公钥的二进制数据。
	 * @return 返回从给定字节序列解码得到的PublicKey对象。
	 * @throws Exception 如果解码过程中发生错误，将抛出异常。
	 */
	public static PublicKey getPublicKeyFromX509(String algorithm,
													 byte[] encodedKey) throws Exception {
		// 根据指定的算法获取KeyFactory实例，用于处理公钥。
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		// 使用X509EncodedKeySpec从公钥字节序列中生成公钥对象。
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}
}
