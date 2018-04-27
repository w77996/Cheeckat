package com.award.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {
	public static final int IMAGETYPE = 0;   //图片 .jpg
	public static final int VIDEOTYPE = 1;   //视频 .mp4
	public static final int ZIP = 2;  //客户端模版 .zip
	public static final int EXETYPE = 3;     //客户端更新包.exe
	public static final int APK = 4;     //android .apk
	public static final int IPA = 5;     //ios 安装包.ipa
	public static final int HEX = 6;     //硬件版本 .hex
	public static final int BIN = 7;     //硬件版本 .bin
	public static final String ITEM_IMAGE_PATH = "item.image.path";
	public static final String ADVICE_IMAGE_PATH = "advice.image.path";
	public static final int RAR = 8;
	
	/*
	 * 上传文件
	 */
	

    /**
     * base64字符串转化成图片  
     * @param imgStr   原图base64字符串
     * @param destPath  目标图路径
     * @return
     */
    public static boolean CreateImgBase64(String imgStr,String destPath)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            OutputStream out = new FileOutputStream(destPath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  
	// copy文件 fileFrom 需要copy的文件地址 fileTo 黏贴的地址
	public static boolean copy(String fileFrom, String fileTo) {
		try {
			FileInputStream in = new FileInputStream(fileFrom);
			FileOutputStream out = new FileOutputStream(fileTo);
			byte[] bt = new byte[1024];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			in.close();
			out.close();
			return true;
		} catch (IOException ex) {
			System.out.println("复制文件失败:" + ex);
			return false;
		}
	}

	/***
	 * 复制一个文件夹 oldPath 需要复制的文件夹 newPath 黏贴的地址
	 * 
	 * @str 文件名要添加的字符串，文件扩展名不变，一般情况下传null
	 * */
	public static boolean copyFolder(String oldPath, String newPath, String str) {
		File file = new File(newPath);
		if (!file.exists()) {
			file.mkdir();
		} else if (file.isDirectory()) {
			File[] oldfile = (new File(oldPath)).listFiles();
			String name = "";
			String na = "";
			for (int i = 0; i < oldfile.length; i++) {
				if (oldfile[i].isFile()) {
					name = newPath + oldfile[i].getName();
					if (null != str) {
						name = name.replace(File.separator, "/");
						na = name.substring(name.lastIndexOf("/") + 1,
								name.lastIndexOf("."));
						name = na + str;
						name = (newPath + oldfile[i].getName()).replace(na,
								name);
					}
					boolean isSuccess = copy(oldfile[i].getPath(), name);
					if (!isSuccess) {
						return false;
					}
				} else if (oldfile[i].isDirectory()) {
					// 复制目录
					String sourceDir = oldPath + File.separator
							+ oldfile[i].getName();
					String targetDir = oldPath + File.separator
							+ oldfile[i].getName();
					boolean isSuccess = copyFolder(sourceDir, targetDir, str);
					if (!isSuccess) {
						return isSuccess;
					}
				}
			}
		}
		return true;
	}

	/***
	 * 上传一个文件 file 需要上传的文件 path 写入文件地址 fileName 写入文件名
	 * */
	public static boolean uploadFile(File file, String path, String fileName,
			Map<String, Object> session, String uploadMark) {
		try {
			InputStream is = new FileInputStream(file);
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			File destFile = new File(path, fileName);
			System.out.println(path);
			java.io.OutputStream os = new FileOutputStream(destFile);
			byte[] buffer = new byte[400];
			int length = 0;
			double percent = 0;
			while ((length = is.read(buffer)) > 0) {
				double fleLenth = file.length();
				percent += length / fleLenth * 100D; // 计算上传文件的百分比
				os.write(buffer, 0, length);
				if (uploadMark != null) {
					session.put(uploadMark, Math.round(percent)); // 将上传百分比保存到Session中
				}
			}
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	// 下载文件
	public static void downloadFile(String filePath,
			HttpServletResponse response) throws Exception {
		File file = new File(filePath);
		if (file.exists()) {
			long len = file.length();
			byte[] bytes = new byte[(int) len];
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(file));
			bufferedInputStream.read(bytes);
			response.setContentType("application/x-msdownload");
			// 写明要下载的文件的大小
			response.setContentLength((int) file.length());
			// 设置附加文件名
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ new String(filePath.substring(
									filePath.lastIndexOf("/") + 1,
									filePath.length()).getBytes("UTF-8"),
									"ISO8859-1"));

			System.out.println(filePath.substring(
					filePath.lastIndexOf("/") + 1, filePath.length()));
			OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
			toClient.write(bytes); // 输出数据
			toClient.flush();
			toClient.close();

		}
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
