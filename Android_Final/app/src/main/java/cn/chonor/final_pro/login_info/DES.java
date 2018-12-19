package cn.chonor.final_pro.login_info;

/**
 * Created by Chonor on 2017/12/26.
 */
import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    //初始化向量，随意填写
    private static byte[]iv={1,2,3,4,5,6,7,8};
    private static String encryptKey="01234567";

    /**
     *
     * @param encryptString 明文
     * @return 加密后的密文
     */
    public static String encryptDES(String encryptString){

        try {
            //实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec zeroIv=new IvParameterSpec(iv);
            //实例化SecretKeySpec，根据传入的密钥获得字节数组来构造SecretKeySpec
            SecretKeySpec key =new SecretKeySpec(encryptKey.getBytes(),"DES");
            //创建密码器
            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE,key,zeroIv);
            //执行加密操作
            byte[]encryptedData=cipher.doFinal(encryptString.getBytes());
            return Base64.encodeToString(encryptedData,0);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密的过程与加密的过程大致相同
     * @param decryptString 密文
     * @return  返回明文
     */

    public static String decryptDES(String decryptString){

        try {
            //先使用Base64解密
            byte[]byteMi=Base64.decode(decryptString,0);
            //实例化IvParameterSpec对象使用指定的初始化向量
            IvParameterSpec zeroIv=new IvParameterSpec(iv);
            //实例化SecretKeySpec，根据传入的密钥获得字节数组来构造SecretKeySpec,
            SecretKeySpec key=new SecretKeySpec(encryptKey.getBytes(),"DES");
            //创建密码器
            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密钥初始化Cipher对象,上面是加密，这是解密模式
            cipher.init(Cipher.DECRYPT_MODE,key,zeroIv);
            //获取解密后的数据
            byte [] decryptedData=cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
