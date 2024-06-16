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
	/**
	 * 根据给定的参数、应用密钥和签名类型，生成签名字符串。
	 * <p>
	 * 此方法支持三种签名算法：MD5、SM2 和 RSA。根据签名类型的不同，选择不同的签名算法进行签名。
	 * 在签名过程中，会首先移除参数中的"sign"字段，然后根据签名类型可能需要添加额外的字段。
	 * 最后，对排序后的参数字符串进行签名计算，生成签名结果。
	 *
	 * @param params 参数映射表，包含需要参与签名的参数。
	 * @param appkey 应用密钥，用于签名过程。
	 * @param signType 签名类型，支持"MD5"、"SM2"和其它（默认为RSA）。
	 * @return 返回生成的签名字符串。
	 * @throws Exception 如果签名过程中发生错误，则抛出异常。
	 */
	public static String unionSign(TreeMap<String, String> params,String appkey,
	        String signType) throws Exception {
	    // 移除参数中的"sign"字段，因为它不参与签名计算
	    params.remove("sign");
	    // 如果签名类型是MD5，则在参数中添加"key"字段，值为appkey
	    if ("MD5".equals(signType)) {
	        params.put("key", appkey);
	    }
	    // 构建排序后的参数字符串
	    StringBuilder sb = new StringBuilder();
	    for (Map.Entry<String, String> entry : params.entrySet()) {
	        // 忽略值为空的参数
	        if (entry.getValue() != null && entry.getValue().length() > 0) {
	            sb.append(entry.getKey()).append("=").append(entry.getValue())
	                    .append("&");
	        }
	    }
	    // 删除最后一个"&"字符，完成参数字符串的构建
	    if (sb.length() > 0) {
	        sb.deleteCharAt(sb.length() - 1);
	    }
	    // 初始化签名字符串
	    String sign = "";
	    // 根据签名类型选择不同的签名算法进行签名
	    if ("MD5".equals(signType)) {
	        // 使用MD5算法对参数字符串进行签名
	        System.out.println(sb.toString());
	        sign = md5(sb.toString().getBytes("UTF-8"));
	        // 签名完成后，移除用于签名的"key"字段
	        params.remove("key");
	    } else if ("SM2".equals(signType)) {
	        // 使用SM2算法对参数字符串进行签名
	        System.out.println(sb.toString());
	        PrivateKey privkey = SmUtil.privKeySM2FromBase64Str(appkey);
	        sign = SmUtil.signSM3SM2RetBase64(privkey, params.get("appid"), sb.toString().getBytes("UTF-8"));
	    } else {
	        // 默认使用RSA算法对参数字符串进行签名
	        System.out.println(sb.toString());
	        sign = rsaSign(sb.toString(), appkey, "UTF-8");
	    }
	    // 返回签名结果
	    return sign;
	}

	/**
	 * 使用RSA算法对给定内容进行签名。
	 *
	 * @param content 需要签名的原始内容。
	 * @param privateKey 私钥，用于签名，使用Base64编码。
	 * @param charset 字符编码格式，用于处理字符串。
	 * @return 签名后的字符串。
	 * @throws Exception 如果加密过程中出现错误，将抛出异常。
	 */
	public static String rsaSign(String content, String privateKey,
				String charset) throws Exception {
		// 从Base64编码的私钥字符串中获取PrivateKey对象
		PrivateKey priKey = getPrivateKeyFromPKCS8("RSA",
				Base64.decodeBase64(privateKey.getBytes()));
		// 使用获取的私钥对内容进行签名
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

	/**
	 * 从PKCS#8格式的编码密钥中获取私钥。
	 *
	 * PKCS#8是一种用于表示私钥的通用格式，此方法提供了从这种格式的字节数组中恢复私钥的能力。
	 * 它首先实例化一个密钥工厂，然后使用PKCS8EncodedKeySpec类从编码的密钥字节数组中生成私钥对象。
	 *
	 * @param algorithm 加密算法，用于指定密钥工厂的算法，例如RSA、DSA等。
	 * @param encodedKey PKCS#8格式的私钥字节数组。
	 * @return 从给定编码密钥中恢复的私钥对象。
	 * @throws Exception 如果密钥格式不正确或不支持指定的算法，则抛出异常。
	 */
	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
			byte[] encodedKey) throws Exception {

		// 根据指定的算法实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		// 使用PKCS8EncodedKeySpec从编码的密钥字节数组中生成私钥对象
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	public static PublicKey getPublicKeyFromX509(String algorithm,
			byte[] encodedKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}
}
